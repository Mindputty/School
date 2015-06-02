package client;
import java.io.*;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;



public class Script
{
	class Action
	{
		Action( String article, boolean read, String text )
		{
			this.article = article;
			this.read = read;
			this.text = text;
		}
		
		String  article;
		boolean read = true;
		String  text;
	}
	
	class AtTime
	{
		AtTime( int time )
		{
			this.time = time;
		}
		int time = 0;
		ArrayList<Action> actions = new ArrayList<Action>();
	}
	
	TreeMap<Integer,AtTime> commands = new TreeMap<Integer,AtTime>();
	
	
	private AtTime getAtTime( String time )
	{
		Integer start = Integer.parseInt( time );
		AtTime atTime = commands.get( start );
		if( atTime == null )
		{
			atTime = new AtTime( start );
			commands.put( start, atTime );
		}
		return atTime;
	}
	
	
	public void load( File file )
	{
		BufferedReader reader;
		try
		{
			reader = new BufferedReader( new FileReader( file ) );
			String line = reader.readLine();
			while( line != null )
			{
				String[] values = line.split( "," );
				AtTime atTime = getAtTime( values[0] );
				
				// User values[1]
				atTime.actions.add( new Action( values[3], values[2].equals( "read" ), values[4] ) );
				
				line = reader.readLine();
			}
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
	}
	
	public void run()
	{
		ExecutorService executor = Executors.newFixedThreadPool( 8 );
		
		int time = 0;
		for( Map.Entry<Integer,AtTime> atTime : commands.entrySet() )
		{
			// Wait until the right moment
			while( time < atTime.getKey() )
			{
				try
				{
					Thread.sleep( 1 );
				}
				catch( InterruptedException e )
				{
					e.printStackTrace();
				}
				time++;
			}
			
			// Take the action the time has come
			for( Action action : atTime.getValue().actions )
			{
				executor.execute( new Client( port, action ) );
			}
		}

		try
		{
			executor.shutdown();
			executor.awaitTermination( 5, TimeUnit.MINUTES );
		}
		catch( InterruptedException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static private int port = 4444;
	static public void main( String[] args )
	{
		if( args.length > 1 )
		{
			port = Integer.parseInt( args[0] );
			Script script = new Script();
			script.load( new File( args[1] ) );
			
			script.run();
			
			Client.outStats();
		}
		else
		{
			System.err.println( "java Client <port> <script_file>" );
		}
	}
}
