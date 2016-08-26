package utilities;

import java.util.*;
import java.io.*;
import java.net.*;
/**
 * 
 * @author 339250
 * @version 1.0
 * 
 *Class Description: InputListener class that reads objects and notifies Client or ClientHandler of changes.
 */
public class InputListener extends Observable implements Runnable
{
	private ObjectInputStream ois;
	private Socket socket;
	public int listenNumber;
	
	/**
	 * Non-default constructor that takes in socket and observer Objects or class that is observing the InputListener
	 * @param cs - Socket Object of InputListener observer.
	 * @param o - InputListener observer
	 */
	public InputListener (Socket cs, Observer o)
	{
		this.socket = cs;
		this.listenNumber = 0;
		this.addObserver(o);
	}
	/**
	 * Sets the Socket attribute.
	 * @param chs - Socket Object of InputListener observer
	 */
	public void setSocket(Socket chs)
	{
		this.socket = chs;
	}
	/**
	 * Sets the listener number of instance of InputListener.
	 * @param listenNumber - listener number of InputListener
	 */
	public void setListenNumber (int listenNumber)
	{
		this.listenNumber = listenNumber;
	}
	/**
	 * Gets the socket of the InputListener observer
	 * @return Socket - Socket of InputListener observer
	 */
	public Socket getSocket()
	{
		return this.socket;
	}
	/**
	 * Gets the InputListener listener number
	 * @return listenNumber - Integer value of InputListener listenNumber.
	 */
	public int getListenNumber()
	{
		return this.listenNumber;
	}

	public void run()
	{
		try
		{
			ois = new ObjectInputStream(socket.getInputStream());
			
			while(true)
			{
				Object o = ois.readObject();
				setChanged();
				notifyObservers(o);				
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		} catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
