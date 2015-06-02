package generator;

import java.io.*;
import java.util.*;

public class User
{
	private static final String   myContentFile = "dummy_content.txt"; 
	private static BufferedReader myReader = null;
	
	enum Type
	{
		Reader,
		Contributor,
		Editor
	}
	
	static double myLikelyAccessRecent = 0.5;
	static int myMaxRecentChanges = 20;
	static int myUserId = 0;
	static int myDocumentId = 0;
	static ArrayList<String> myDocuments = new ArrayList<String>();
	static ArrayList<String> myDocumentContents = new ArrayList<String>();
	static LinkedList<Integer> myRecentChanges = new LinkedList<Integer>();
	
	
	int    myLastDocument = -1;
	String myUser = "User " + ++myUserId;
	int    myAverageTime = 10;
	int    myLastAction = 0;
	Type   myType = Type.Reader;
	
	public User( int averageTime, Type type )
	{
		myAverageTime = averageTime;
		myType = type;
	}
	
	synchronized private String loadText( int length )
	{
		StringBuilder builder = new StringBuilder();
		while( builder.length() < length )
		{
			try
			{
				if( myReader == null )
					myReader = new BufferedReader( new FileReader( myContentFile ) );
				
				String line = myReader.readLine();
				if( line == null )
				{
					myReader = new BufferedReader( new FileReader( myContentFile ) );
					line = myReader.readLine();
				}
				builder.append( line );
			}
			catch( FileNotFoundException e )
			{
				break;
			}
			catch( IOException e )
			{
				break;
			}
		}
		while( builder.length() < length )
			builder.append( "dummy string. no content file provided." );
		
		String content = builder.toString();
		content = content.replaceAll( ",", " " );
		content = content.replaceAll( "\n", "\\n" );
		return content;
	}
	
	private String generateContent( int time, String docName )
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append( String.format( "Document %s contents at time %d. ", docName, time ) );
		int length = (int) (Math.random() * 2000) + 500;
		builder.append( loadText( length ) );
		
		return builder.toString();
	}
	
	public int time()
	{
		return myLastAction;
	}
	
	public String nextAction( int time )
	{
		String line = null;
		switch( myType )
		{
			case Contributor:
				if( (Math.random() * 3) == 0 )
				{
					line = readDocument( time );
				}
				else
				{
					String docName = "docment " + ++myDocumentId;
					String contents = generateContent( time, docName );
					myDocuments.add( docName );
					myDocumentContents.add( contents );
					
					line = String.format( "%d,%s,%s,%s,%s\n", time, myUser, "write", docName, contents );
					myRecentChanges.addLast( myDocuments.size() - 1 );
				}
				break;
				
			case Editor:
				{
					if( (Math.random() * 3) == 0 && myLastDocument >= 0 )
					{
						String docName = myDocuments.get( myLastDocument );
						String contents = String.format( "Updating Document %s contents at time %d. ", docName, time ) + myDocumentContents.get( myLastDocument );
						myDocumentContents.set( myLastDocument, contents );
						
						line = String.format( "%d,%s,%s,%s,%s\n", time, myUser, "write", docName, contents );
						
						myRecentChanges.addLast( myLastDocument );
					}
					else
					{
						line = readDocument( time );
					}
				}
				break;
				
			default:
			case Reader:
				line = readDocument( time );
				break;
		}
		if( line != null )
			myLastAction = (int) (time + (Math.random() * myAverageTime));
		return line;
	}
	
	private String readDocument( int time )
	{
		while( myRecentChanges.size() > myMaxRecentChanges )
		{
			myRecentChanges.removeFirst();
		}
		
		String line = null;
		if( myDocuments.size() > 0 )
		{
			if( Math.random() < myLikelyAccessRecent )
			{
				myLastDocument = myRecentChanges.get( (int) (myRecentChanges.size() * Math.random()) );
			}
			else
			{
				myLastDocument = (int) (myDocuments.size() * Math.random());
			}
			
			String docName = myDocuments.get( myLastDocument );
			String contents = myDocumentContents.get( myLastDocument );
			
			if( myLastDocument >= 0 )
				line = String.format( "%d,%s,%s,%s,%s\n", time, myUser, "read", docName, contents );
		}
		
		return line;
	}
}
