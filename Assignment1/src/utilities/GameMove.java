package utilities;

import java.io.Serializable;
/**
 * 
 * @author 339250
 * @version 1.0
 * 
 *Class Description: GameMove class. 
 */
public class GameMove implements Serializable
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 2781808724289028114L;
	private String username;
	private int panel;
	/**
	 * Non-default constructor that takes in game board panel number and username of Client making game move.
	 * Calls getters and setters for panel and username.
	 * @param panel - panel number that game move was made in
	 * @param username - username of the client making the game move.
	 */
	public GameMove(int panel, String username)
	{
		setPanel(panel);
		setUser(username);
	}
	/**
	 * Method that assigns the panel number to a class level attribute
	 * @param panel - panel number of game move.
	 */
	private void setPanel(int panel)
	{
		this.panel = panel;
	}
	
	/**
	 * Method that assigns the username of the Client making the game move to a class level attribute
	 * @param username - username of Client making the game move.
	 */
	private void setUser(String username)
	{
		this.username = username;
	}
	
	/**
	 * Method to get the panel number the game move was made in.
	 * @return panel - integer value of panel game move was made in (1-9)
	 */
	public int getPanel()
	{
		return this.panel;
	}
	
	/**
	 * Method to get the username of the client that made the game move.
	 * @return username - String value of the username of the client that made the game move.
	 */
	public String getUser()
	{
		return this.username;
	}
}
