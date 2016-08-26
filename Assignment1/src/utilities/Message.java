package utilities;

import java.io.*;
/**
 * 
 * @author 339250
 * @version 1.0
 * 
 *Class Description: Message class
 */
public class Message implements Serializable
{

	private static final long	serialVersionUID	= 6676712459799195433L;
	public String message, user;
	
	/**
	 * Non-default constructor that takes in message and username
	 * @param message - message being sent between Clients
	 * @param user - String representation of username of Client sending the message
	 */
	public Message(String message, String user)
	{
		setMessage(message);
		setUser(user);
	}
	/**
	 * Default constructor.
	 */
	public Message()
	{
		super();
	}
	/**
	 * Sets the message to a class level attribute
	 * @param message - String message Client wishes to send to other Client
	 */
	private void setMessage(String message)
	{
		this.message = message;
	}
	/**
	 * Sets the username of message sender to a class level attribute
	 * @param user - String representation of username of Client sending message
	 */
	private void setUser(String user)
	{
		this.user = user;
	}
	/**
	 * Gets the message body of the Message object
	 * @return message - String representation of message body
	 */
	public String getMessage()
	{
		return this.message;
	}
	/**
	 * Gets the username of the Client sending a message
	 * @return user - String representation of username of Client sending message
	 */
	public String getUser()
	{
		return this.user;
	}
}
