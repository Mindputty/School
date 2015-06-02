package server;
import java.io.*;
import java.util.*;


public class Database
{
	
	
	private File myDBLocation;
	private static HashMap<String,Integer> myReadHash = new HashMap<String,Integer>( 512 );
	private static HashMap<String,Integer> myWriteHash = new HashMap<String,Integer>( 512 );
	private static int myWriteErrors = 0;
	private static int myReadErrors = 0;
	
	public Database( String location )
	{
		myDBLocation = new File( location );
	}

	public String get( String document )
	{
		increment( document );
		String docText = "";
		File file = new File( myDBLocation, document );
		try
		{	
			FileReader reader = new FileReader( file );
			
			char[] buffer = new char[1024];
			int count = reader.read( buffer );
			while( count >= 0 )
			{
				increment( document );
				
				docText += new String( buffer, 0, count );
				count = reader.read( buffer );
			}
			reader.close();
			Thread.currentThread().sleep( 8 );
			increment( document );
		}
		catch( FileNotFoundException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch( IOException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch( InterruptedException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return docText;
	}

	public void put( String document, String text )
	{
		try
		{
			int postRead, postWrite;
			int readCount = 0, writeCount = 0;
			synchronized( myReadHash )
			{
				Integer hashValue = myReadHash.get( document );
				if( hashValue == null )
				{
					readCount = 0;
					myReadHash.put( document, readCount );
					myWriteHash.put( document, 0 );
				}
				else
				{
					readCount = hashValue;
					hashValue = myWriteHash.get( document );
					if( hashValue == null )
						myWriteHash.put( document, 0 );
					else
						writeCount = hashValue;
				}
			}
			
			text.replaceAll( "\\n", "\n" );
			
			File file = new File( myDBLocation, document );
			PrintWriter writer = new PrintWriter( file );
			
			incrementWrite( document );
			Thread.currentThread().sleep( 8 );
			incrementWrite( document );
			
			writer.write( text );
			writer.close();
			
			synchronized( myReadHash )
			{
				postRead = myReadHash.get( document );
				postWrite = myWriteHash.get( document );
			}
			if( postRead != readCount)
			{
				System.err.printf( "ERROR: %d: Attempt to read during write\n", ++myReadErrors );
			}
			if( postWrite != (writeCount+2))
			{
				System.err.printf( "ERROR: %d: Attempt to write during write\n", ++myWriteErrors );
			}
		}
		catch( FileNotFoundException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch( InterruptedException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	final private void increment( String document )
	{
		synchronized( myReadHash )
		{
			Integer hashValue = myReadHash.get( document );
			if( hashValue == null )
				hashValue = 0;
			myReadHash.put( document, ++hashValue );
		}
	}
	
	final private void incrementWrite( String document )
	{
		synchronized( myWriteHash )
		{
			int hashValue = myWriteHash.get( document );
			myWriteHash.put( document, ++hashValue );
		}
	}

	static public void outStats()
	{

		System.err.printf( "ERRORS: Reading during write %d\n", myReadErrors );
		System.err.printf( "ERRORS: Writing during write %d\n", myWriteErrors );
	}
}
