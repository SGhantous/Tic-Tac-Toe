package serverSide;

import java.io.*;
import java.net.*;
import java.util.*;

import utilities.InputListener;
import utilities.Message;

/**
 * 
 * @author 339250
 * @version 1.0
 * 
 *Class Description: ClientHandler class that implements Observer and Runnable interfaces.
 */
public class ClientHandler implements Observer, Runnable
{

	private ObjectOutputStream	oos1, oos2;
	private Socket				cs1, cs2;
	private InputListener		lis1, lis2;
	
	/**
	 * Non-default constructor that takes in two client sockets and assigns to class level attributes.
	 * @param client1 - Socket of first ClientGUI
	 * @param client2 - Socket of second ClientGUI
	 */
	public ClientHandler(Socket client1, Socket client2)
	{
		this.cs1 = client1;
		this.cs2 = client2;
	}
	
	public void run()
	{
		try
		{
			OutputStream os1 = cs1.getOutputStream();
			OutputStream os2 = cs2.getOutputStream();
			
			oos1 = new ObjectOutputStream(os1);
			oos2 = new ObjectOutputStream(os2);
			
			oos1.writeObject(new Message("1", "ClientHandler"));
			oos2.writeObject(new Message("2", "ClientHandler"));
			
			lis1 = new InputListener(cs1, this);
			lis2 = new InputListener(cs2, this);
			
			Thread t1 = new Thread(lis1);
			Thread t2 = new Thread(lis2);
			
			t1.start();
			t2.start();
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void update(Observable obs, Object o)
	{
		try
		{
			if(obs == lis1)
			{
				oos2.writeObject(o);
			}
			else if(obs == lis2)
			{
				oos1.writeObject(o);
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
