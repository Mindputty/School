package server;
import java.io.*;
import java.net.*;


public class Server implements Runnable
{
	int myPortNumber;
	Database myDatabase;
	ServerSocket myServerSocket = null;
	
	public Server( int port, String location )
	{
		myDatabase = new Database( location );
		myPortNumber = port;
	}
	
	public void connect()
	{
		if( myPortNumber > 0 )
		{
			try
			{
				myServerSocket = new ServerSocket( myPortNumber );
				myServerSocket.setSoTimeout( 60000 ); // wait 1 minute
			} 
			catch (IOException e)
			{
				System.err.println("Create Socket failed: " + myPortNumber );
				System.exit(-1);
			}
		}
	}
	
	public void disconnect()
	{
		try
		{
			if( myServerSocket != null )
			{
				myServerSocket.close();
				myServerSocket = null;
			}
		} 
		catch (IOException e)
		{
			System.err.println("Close failed");
			System.exit(-1);
		}
	}
	
	public void run()
	{
		connect();
		
		boolean done = false;
		while( myServerSocket != null && !done )
		{
			try
			{	
				Socket clientSocket = myServerSocket.accept();
				
				HandleRequest handler = new HandleRequest( clientSocket, myDatabase );
				handler.run();
			}
			catch( java.net.SocketTimeoutException e )
			{
				System.err.println( "server timeout" );
				Database.outStats();
				System.exit(0);
			}
			catch( Exception e )
			{
				e.printStackTrace();
			}
		}
		disconnect();
	}
	
	static public void main( String[] args )
	{
		int port = 4444;
		if( args.length > 1 )
		{
			port = Integer.parseInt( args[0] );
		
			Server server = new Server( port, args[1] );
			server.run();
			Database.outStats();
		}
		else
		{
			System.out.println( "port-number database-location" );
		}
	}
}
