package serverSide;

import java.io.IOException;
import java.net.*;
import java.util.*;

import clientSide.ClientGUI;

/**
 * 
 * @author 339250
 * @version 1.0
 * 
 *Class Description: Server class. Creates a new ServerSocket and socketList. Waits to accept a connection to a Client socket then waits
 *for a second socket. When a second socket connects the Server creates a new instance of the ClientHandler and clears the socketList. The server
 *waits for more sockets to the connect to the ServerSocket.
 */
public class Server
{
	
	public static void main(String[] args)
	{
		ServerSocket ss = null;
		ArrayList<Socket> socketList = new ArrayList<Socket>();
		Socket cs = null;
		
		try
		{
			ss = new ServerSocket(5555);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		while(true)
		{
			try
			{
				System.out.println("Server is up and running!");
				cs = ss.accept();
				socketList.add(cs);
				
				if(socketList.size() == 2)
				{
					System.out.println("Two connections");
					ClientHandler ch = new ClientHandler(socketList.get(0), socketList.get(1));
					Thread chThread = new Thread(ch);
					chThread.start();
					socketList.clear();
				}
				
			}
			catch (IOException e)
			{
				
			}
		}
	}		
}
