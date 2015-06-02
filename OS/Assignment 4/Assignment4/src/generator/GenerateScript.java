package generator;

import generator.User.Type;

import java.io.*;
import java.util.*;

public class GenerateScript
{
	ArrayList<String>  myScript = new ArrayList<String>();
	ArrayList<User>    myUsers  = new ArrayList<User>();
	
	public void add( User user )
	{
		myUsers.add( user );
	}
	
	public void generate( int end )
	{
		for( int i = 0; i < end; i++ )
		{
			for( User user : myUsers )
			{
				if( user.time() <= i )
				{
					String line = user.nextAction( i );
					if( line != null )
						myScript.add( line );
				}
			}
		}
	}
	
	public void save( File file )
	{
		try
		{
			PrintWriter writer = new PrintWriter( file );
			for( String line : myScript )
			{
				writer.write( line );
			}
			writer.close();
		}
		catch( FileNotFoundException e )
		{
			e.printStackTrace();
		}
	}
	
	static public void main( String[] args )
	{
		if( args.length < 2 )
		{
			System.err.println( "arguments: file endtime" );
		}
		GenerateScript generator = new GenerateScript();
		
		addUsersFixed( generator );
		
		generator.generate( Integer.parseInt( args[1] ) );
		generator.save( new File( args[0] ) );
	}

	private static void addUsersFixed( GenerateScript generator )
	{
		generator.add( new User( 20, Type.Contributor) );
		generator.add( new User( 10, Type.Contributor) );
		generator.add( new User( 10, Type.Contributor) );
		generator.add( new User( 5, Type.Editor ) );
		generator.add( new User( 30, Type.Editor) );
		generator.add( new User( 30, Type.Editor) );
		generator.add( new User( 20, Type.Editor) );
		generator.add( new User( 5, Type.Reader) );
		generator.add( new User( 10, Type.Reader) );
		generator.add( new User( 10, Type.Reader) );
		generator.add( new User( 5, Type.Reader) );
		generator.add( new User( 5, Type.Reader) );
		generator.add( new User( 10, Type.Reader) );
		generator.add( new User( 10, Type.Reader) );
		generator.add( new User( 5, Type.Reader) );
	}
	
	private static void addUsersVariable( GenerateScript generator, int contributors, int editors, int readers )
	{
		for( int i = 0; i < contributors; i++ )
			generator.add( new User( (int) (10 + Math.random()*20.0), Type.Contributor) );
		

		for( int i = 0; i < editors; i++ )
			generator.add( new User( (int) (5 + Math.random()*20.0), Type.Editor ) );
		
		for( int i = 0; i < readers; i++ )
			generator.add( new User( (int) (5 + Math.random()*10.0), Type.Reader) );
	}
}
