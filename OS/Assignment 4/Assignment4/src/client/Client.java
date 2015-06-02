package client;

import java.io.*;
import java.net.*;



public class Client implements Runnable
{
	Socket mySocket = null;
	PrintWriter        myOut = null;
	InputStreamReader  myIn  = null;
	boolean            myConnected = false;
	long               myStart = 0;
	
	int myPort;
	Script.Action myAction;
	
	static double        myReadTime = 0;
	static double        myWriteTime = 0;
	static double        myReadCount = 0;
	static double        myWriteCount = 0;
	static double        myFailedTextResponseCount = 0;
	
	public Client( int port, Script.Action action )
	{
		myPort = port;
		myAction = action;
	}
	
	public boolean connect()
	{
		myStart = System.currentTimeMillis();
		boolean connection = false;
		try
		{
			mySocket = new Socket( "localhost", myPort );
			mySocket.setKeepAlive( true );
			
			mySocket.setSoTimeout( 60000 ); // set a timeout so setup of streams can timeout.
			
			myOut = new PrintWriter( mySocket.getOutputStream() );
			myIn  = new InputStreamReader( mySocket.getInputStream() );
			connection = true;
		}
		catch( java.net.ConnectException e )
		{
			System.out.println( "Failed to connect to server." );
		}
		catch( SocketTimeoutException e )
		{
			e.printStackTrace();
		}
		catch( UnknownHostException e )
		{
			e.printStackTrace();
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		myConnected = connection;
		return connection;
	}

	private void write( String file, String docText )
	{
		long end = myStart;
		
		myOut.println( "write:" + file + "\n" + docText );
		myOut.flush();
		
		try
		{
			mySocket.shutdownOutput();
			int value = myIn.read();
			mySocket.close();
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}
		
		end = System.currentTimeMillis();
		
		myWriteTime += (end - myStart);
		myWriteCount++;
		
		System.out.printf( "Response time %d ms writing %s\n", (end - myStart), myAction.article );
	}

	private void read( String file )
	{
		long end = myStart;
		myOut.println( "read :" + file );
		
		char[] buffer = new char[4096];
		try
		{
			myOut.flush();
			mySocket.shutdownOutput();
			
			StringBuilder builder = new StringBuilder();
			int count = myIn.read( buffer ); 
			while( count > 0 )
			{
				builder.append( new String( buffer, 0, count ) );
				count = myIn.read( buffer );
			}
			count = myIn.read( buffer );
			if( builder.length() == 0 )
			{
				myFailedTextResponseCount++;
			}
			end = System.currentTimeMillis();
			myIn.close();
			mySocket.close();
		}
		catch( IOException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		myReadTime += (end - myStart);
		myReadCount++;
		
		System.out.printf( "Response time %d ms reading %s\n", (end - myStart), myAction.article );
	}

	@Override
	public void run()
	{
		if( connect() )
		{
			if( myAction.read )
			{
				read( myAction.article );
			}
			else
			{
				write( myAction.article, myAction.text );
			}
		}
		else
			System.err.println( "Connection Failed." );
	}
	
	static public void outStats()
	{
		double averageReadTime = myReadTime / myReadCount;
		double averageWriteTime = myWriteTime / myWriteCount;
		
		System.out.printf( "Average read response: %f ms\n", averageReadTime );
		System.out.printf( "Average write response: %f ms\n", averageWriteTime );
		System.out.printf( "Failed read response: %f\n", myFailedTextResponseCount );
	}
}
