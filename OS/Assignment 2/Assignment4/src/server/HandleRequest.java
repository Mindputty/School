package server;

import java.io.*;
import java.net.Socket;


public class HandleRequest implements Runnable
{
	Database myDatabase;
	Socket mySocket;
	
	public HandleRequest( Socket socket, Database database )
	{
		myDatabase = database;
		mySocket = socket;
	}

	@Override
	public void run()
	{
		try
		{
			OutputStream outputStream = mySocket.getOutputStream();
			InputStream  inputStream  = mySocket.getInputStream();
			
			char[]            buffer = new char[4096];
			InputStreamReader reader = new InputStreamReader( inputStream );
			
			int count = reader.read( buffer );
			String text = new String( buffer, 0, count );
			
			String document = null;
			int endOfLine = text.indexOf( "\n" );
			if( endOfLine > 6 )
				document = text.substring( 6, endOfLine ).trim();
			
			if( text.startsWith( "read :" ) )
			{
				String docText = myDatabase.get( document );
				
				PrintWriter printer = new PrintWriter( outputStream );
				printer.write( docText );
				printer.flush();
			}
			else if( text.startsWith( "write:" ) )
			{
				String docText = text.substring( endOfLine+1 );
				
				count = reader.read( buffer );
				//while( reader.ready() )
				while( count > 0 )
				{
					docText += new String( buffer, 0, count );
					count = reader.read( buffer );
				}
				myDatabase.put( document, docText );
			}
			mySocket.close();
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}
		
	}
	
}
