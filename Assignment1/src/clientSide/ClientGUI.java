package clientSide;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import utilities.*;

import javax.swing.border.BevelBorder;

public class ClientGUI extends JFrame implements Observer
{
	private JLabel lblPanel1, lblPanel2, lblPanel3, lblPanel4, lblPanel5,
					lblPanel6, lblPanel7, lblPanel8, lblPanel9, lblTurn, lblYourImg, lblOpponentImg;
	private MyMouseListener	mouseListener;
	private	Image imgBoard = new ImageIcon(this.getClass().getResource("/gameBoard.png")).getImage();
	private	Image yourImg, opponentImg;
	private JPanel	contentPane;
	private JTextField txtMessageInput;
	private JList	lstMessages;
	private JButton btnSend, btnDisconnect;
	private DefaultListModel	listModel;
	private JScrollPane	scrollPane;
	private int	lastIndex, winner;
	private JButton btnConnect;
	private MyActionListener actionListener;
	private boolean connected, gameOver;
	private ObjectOutputStream oos;
	private Socket cs;
	private String username, symbol, address, port;
	private String panel1, panel2, panel3, panel4, panel5, panel6, panel7, panel8, panel9;
	private Boolean active;
	private String playAgainChoice;

	/**
	 * Non-default constructor that takes in a username from the Client, as well as the address and port number of the game server.
	 * It then creates a frame that displays the ClientGUI.
	 * @param username - a String value of the user's name they wish to use during the game.
	 * @param address - the address (IP) of the server being connected to.
	 * @param port - the port number of the ServerSocket that the Client socket will connect to.
	 */
	public ClientGUI(String username, String address, String port)
	{
		this.username = username;
		this.address = address;
		this.port = port;
		this.active = active;
		this.symbol = symbol;
		actionListener = new MyActionListener();
		listModel = new DefaultListModel();
		mouseListener = new MyMouseListener();
		connected = false;
		gameOver = false;
		
		
		
		setTitle("Tic-Tac-Toe");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 792);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtMessageInput = new JTextField();
		txtMessageInput.setBackground(Color.LIGHT_GRAY);
		txtMessageInput.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
		txtMessageInput.setBounds(30, 635, 689, 43);
		contentPane.add(txtMessageInput);
		txtMessageInput.setColumns(10);
		
		btnSend = new JButton("Send");
		btnSend.setBackground(Color.LIGHT_GRAY);
		btnSend.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 30));
		btnSend.setForeground(Color.DARK_GRAY);
		btnSend.setBounds(734, 635, 110, 43);
		btnSend.addActionListener(actionListener);
		contentPane.add(btnSend);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 448, 813, 174);
		contentPane.add(scrollPane);
		
		lstMessages = new JList(listModel);
		lstMessages.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 17));
		lstMessages.setBackground(Color.LIGHT_GRAY);
		lstMessages.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lstMessages.enable(false);
		scrollPane.setViewportView(lstMessages);
		
		
		lblPanel1 = new JLabel("");
		lblPanel1.setToolTipText("");
		lblPanel1.setBounds(249, 13, 108, 108);
		lblPanel1.addMouseListener(mouseListener);
		contentPane.add(lblPanel1);
		
		lblPanel2 = new JLabel("");
		lblPanel2.setBounds(379, 13, 108, 108);
		lblPanel2.addMouseListener(mouseListener);
		contentPane.add(lblPanel2);
		
		lblPanel3 = new JLabel("");
		lblPanel3.setBounds(510, 13, 108, 108);
		lblPanel3.addMouseListener(mouseListener);
		contentPane.add(lblPanel3);
		
		lblPanel4 = new JLabel("");
		lblPanel4.setBounds(249, 142, 108, 108);
		lblPanel4.addMouseListener(mouseListener);
		contentPane.add(lblPanel4);
		
		lblPanel5 = new JLabel("");
		lblPanel5.setBounds(379, 142, 108, 108);
		lblPanel5.addMouseListener(mouseListener);
		contentPane.add(lblPanel5);
		
		lblPanel6 = new JLabel("");
		lblPanel6.setBounds(510, 142, 108, 108);
		lblPanel6.addMouseListener(mouseListener);
		contentPane.add(lblPanel6);
		
		lblPanel7 = new JLabel("");
		lblPanel7.setBounds(249, 276, 108, 108);
		lblPanel7.addMouseListener(mouseListener);
		contentPane.add(lblPanel7);
		
		lblPanel8 = new JLabel("");
		lblPanel8.setBounds(379, 276, 108, 108);
		lblPanel8.addMouseListener(mouseListener);
		contentPane.add(lblPanel8);
		
		lblPanel9 = new JLabel("");
		lblPanel9.setBounds(510, 276, 108, 108);
		lblPanel9.addMouseListener(mouseListener);
		contentPane.add(lblPanel9);
		
		JLabel lblBoard = new JLabel("");
		lblBoard.setBounds(232, 13, 468, 369);
		lblBoard.setIcon(new ImageIcon(imgBoard));
		contentPane.add(lblBoard);
		
		btnDisconnect = new JButton("Disconnect");
		btnDisconnect.setForeground(Color.DARK_GRAY);
		btnDisconnect.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
		btnDisconnect.setBackground(Color.LIGHT_GRAY);
		btnDisconnect.setBounds(352, 691, 170, 43);
		btnDisconnect.addActionListener(actionListener);
		contentPane.add(btnDisconnect);
		
		lblTurn = new JLabel("");
		lblTurn.setHorizontalAlignment(SwingConstants.CENTER);
		lblTurn.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 25));
		lblTurn.setForeground(Color.YELLOW);
		lblTurn.setBounds(30, 384, 814, 61);
		contentPane.add(lblTurn);
		
		JLabel lblYou = new JLabel("YOU");
		lblYou.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 25));
		lblYou.setForeground(Color.BLUE);
		lblYou.setBounds(52, 13, 56, 30);
		contentPane.add(lblYou);
		
		JLabel lblOpponent = new JLabel("OPPONENT");
		lblOpponent.setForeground(Color.BLUE);
		lblOpponent.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 25));
		lblOpponent.setBounds(715, 13, 145, 30);
		contentPane.add(lblOpponent);
		
		lblYourImg = new JLabel("");
		lblYourImg.setToolTipText("");
		lblYourImg.setBounds(30, 41, 108, 108);
		contentPane.add(lblYourImg);
		
		lblOpponentImg = new JLabel("");
		lblOpponentImg.setToolTipText("");
		lblOpponentImg.setBounds(736, 41, 108, 108);
		contentPane.add(lblOpponentImg);
		
		connectToServer();
		
	}
/*******************************INNER CLASSES*************************/
	/**
	 * 
	 * @author 339250
	 * @version 1.0
	 * 
	 *Class Description: A private class that implements ActionListener.
	 */
	private class MyActionListener implements ActionListener
	{
		
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == btnSend)
			{
				String messageInput = txtMessageInput.getText();
				if(!messageInput.equals("") && messageInput != null)
				{
					listModel.addElement(username+ ": "+ messageInput);
					Message msgSent = new Message(messageInput, username);
					txtMessageInput.setText("");
				
					try
					{
						oos.writeObject(msgSent);
					} 
					catch (IOException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			else if(e.getSource() == btnDisconnect)
			{
				if(JOptionPane.showConfirmDialog(null, "Are you sure you wish to disconnect?", "WARNING", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				{
					Message msg = new Message("Disconnected", "System");
					try
					{
						oos.writeObject(msg);
					}
					catch(IOException e2)
					{
						e2.printStackTrace();
					}
					setVisible(false);
					dispose();
				}
			}
		}
	}
	
	/**
	 * 
	 * @author 339250
	 * @version 1.0
	 * 
	 *Class Description: A private class that extends MouseAdapter
	 */
	private class MyMouseListener extends MouseAdapter
	{
		public void mousePressed(MouseEvent e)
		{
			if(e.getSource() == lblPanel1)
			{
				if(lblPanel1.getIcon() == null && active == true && gameOver ==false)
				{
					GameMove move = new GameMove(1, username);
					active = false;
					lblPanel1.setIcon(new ImageIcon(yourImg));
					lblTurn.setText("Opponent's move");
					panel1 = "Yours";
					makeMove(move);
					checkWin();					
				}
				else if(gameOver == true)
				{
					listModel.addElement("Game over");
				}
				else if(lblPanel1.getIcon() != null)
				{
					listModel.addElement("Panel 1 already taken!");
				}
				else if(active == false)
				{
					listModel.addElement("Wait for your turn");
				}
			}
			if(e.getSource() == lblPanel2)
			{
				if(lblPanel2.getIcon() == null && active == true && gameOver ==false)
				{
					GameMove move = new GameMove(2, username);
					active = false;
					lblPanel2.setIcon(new ImageIcon(yourImg));
					lblTurn.setText("Opponent's move");
					panel2 = "Yours";
					makeMove(move);
					checkWin();					
				}
				else if(gameOver == true)
				{
					listModel.addElement("Game over");
				}
				else if(lblPanel2.getIcon() != null)
				{
					listModel.addElement("Panel 2 already taken!");
				}
				else if(active == false)
				{
					listModel.addElement("Wait for your turn");
				}
			}
			if(e.getSource() == lblPanel3)
			{
				if(lblPanel3.getIcon() == null && active == true && gameOver ==false)
				{
					GameMove move = new GameMove(3, username);
					active = false;
					lblPanel3.setIcon(new ImageIcon(yourImg));
					lblTurn.setText("Opponent's move");
					panel3 = "Yours";
					makeMove(move);
					checkWin();					
				}
				else if(gameOver == true)
				{
					listModel.addElement("Game over");
				}
				else if(lblPanel3.getIcon() != null)
				{
					listModel.addElement("Panel 3 already taken!");
				}
				else if(active == false)
				{
					listModel.addElement("Wait for your turn");
				}
			}
			if(e.getSource() == lblPanel4)
			{
				if(lblPanel4.getIcon() == null && active == true && gameOver ==false)
				{
					GameMove move = new GameMove(4, username);
					active = false;
					lblPanel4.setIcon(new ImageIcon(yourImg));
					lblTurn.setText("Opponent's move");
					panel4 = "Yours";
					makeMove(move);
					checkWin();					
				}
				else if(gameOver == true)
				{
					listModel.addElement("Game over");
				}
				else if(lblPanel4.getIcon() != null)
				{
					listModel.addElement("Panel 4 already taken!");
				}
				else if(active == false)
				{
					listModel.addElement("Wait for your turn");
				}
			}
			if(e.getSource() == lblPanel5)
			{
				if(lblPanel5.getIcon() == null && active == true && gameOver ==false)
				{
					GameMove move = new GameMove(5, username);
					active = false;
					lblPanel5.setIcon(new ImageIcon(yourImg));
					lblTurn.setText("Opponent's move");
					panel5 = "Yours";
					makeMove(move);
					checkWin();					
				}
				else if(gameOver == true)
				{
					listModel.addElement("Game over");
				}
				else if(lblPanel5.getIcon() != null)
				{
					listModel.addElement("Panel 5 already taken!");
				}
				else if(active == false)
				{
					listModel.addElement("Wait for your turn");
				}
			}
			if(e.getSource() == lblPanel6)
			{
				if(lblPanel6.getIcon() == null && active == true && gameOver ==false)
				{
					GameMove move = new GameMove(6, username);
					active = false;
					lblPanel6.setIcon(new ImageIcon(yourImg));
					lblTurn.setText("Opponent's move");
					panel6 = "Yours";
					makeMove(move);
					checkWin();					
				}
				else if(gameOver == true)
				{
					listModel.addElement("Game over");
				}
				else if(lblPanel6.getIcon() != null)
				{
					listModel.addElement("Panel 6 already taken!");
				}
				else if(active == false)
				{
					listModel.addElement("Wait for your turn");
				}
			}
			if(e.getSource() == lblPanel7)
			{
				if(lblPanel7.getIcon() == null && active == true && gameOver ==false)
				{
					GameMove move = new GameMove(7, username);
					active = false;
					lblPanel7.setIcon(new ImageIcon(yourImg));
					lblTurn.setText("Opponent's move");
					panel7 = "Yours";
					makeMove(move);	
					checkWin();									
				}
				else if(gameOver == true)
				{
					listModel.addElement("Game over");
				}
				else if(lblPanel7.getIcon() != null)
				{
					listModel.addElement("Panel 7 already taken!");
				}
				else if(active == false)
				{
					listModel.addElement("Wait for your turn");
				}
			}
			if(e.getSource() == lblPanel8)
			{
				if(lblPanel8.getIcon() == null && active == true && gameOver ==false)
				{
					GameMove move = new GameMove(8, username);
					active = false;
					lblPanel8.setIcon(new ImageIcon(yourImg));
					lblTurn.setText("Opponent's move");
					panel8 = "Yours";
					makeMove(move);
					checkWin();					
				}
				else if(gameOver == true)
				{
					listModel.addElement("Game over");
				}
				else if(lblPanel8.getIcon() != null)
				{
					listModel.addElement("Panel 8 already taken!");
				}
				else if(active == false)
				{
					listModel.addElement("Wait for your turn");
				}
			}
			if(e.getSource() == lblPanel9)
			{
				if(lblPanel9.getIcon() == null && active == true && gameOver ==false)
				{
					GameMove move = new GameMove(9, username);
					active = false;
					lblPanel9.setIcon(new ImageIcon(yourImg));
					lblTurn.setText("Opponent's move");
					panel9 = "Yours";
					makeMove(move);
					checkWin();					
				}
				else if(gameOver == true)
				{
					listModel.addElement("Game over");
				}
				else if(lblPanel9.getIcon() != null)
				{
					listModel.addElement("Panel 9 already taken!");
				}
				else if(active == false)
				{
					listModel.addElement("Wait for your turn");
				}
			}
			lastIndex = listModel.getSize() - 1;
			if (lastIndex >= 0) 
			{
				lstMessages.ensureIndexIsVisible(lastIndex);
			}
			if(gameOver == true)
			{
				checkPlayAgain();
			}
		}
	}
	/**
	 * Private method used to connect to the game server. Connects Client socket to ServerSocket located at the
	 * address and port number passed into constructor.
	 * Catches an IO Exception when connection cannot be made.
	 */
	private void connectToServer()
	{
		try
		{
			panel1 = "";
			panel2 = "";
			panel3 = "";
			panel4 = "";
			panel5 = "";
			panel6 = "";
			panel7 = "";
			panel8 = "";
			panel9 = "";
			gameOver = false;
			active = false;
			int portNo = Integer.parseInt(port);
			cs = new Socket(address, portNo);
			connected =true;
			OutputStream os = cs.getOutputStream();
			oos = new ObjectOutputStream(os);
			InputListener listener = new InputListener(cs, ClientGUI.this);
			Thread guiThread = new Thread(listener);
			guiThread.start();
			listModel.removeAllElements();
			listModel.addElement("Connected to server");
		
		} 
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	/**
	 * A private method that refreshes the game board when the user wants to play again.
	 */
	private void playAgain()
	{
		panel1 = "";
		panel2 = "";
		panel3 = "";
		panel4 = "";
		panel5 = "";
		panel6 = "";
		panel7 = "";
		panel8 = "";
		panel9 = "";
		lblPanel1.setIcon(null);
		lblPanel2.setIcon(null);
		lblPanel3.setIcon(null);
		lblPanel4.setIcon(null);
		lblPanel5.setIcon(null);
		lblPanel6.setIcon(null);
		lblPanel7.setIcon(null);
		lblPanel8.setIcon(null);
		lblPanel9.setIcon(null);
		gameOver = false;
		if(winner == 1)
		{
			active = true;
			lblTurn.setText("Your move");
			yourImg = new ImageIcon(this.getClass().getResource("/x.png")).getImage();
			opponentImg = new ImageIcon(this.getClass().getResource("/o.png")).getImage();
		}
		else if(winner ==2)
		{
			active = false;
			lblTurn.setText("Opponent's move");
			yourImg = new ImageIcon(this.getClass().getResource("/o.png")).getImage();
			opponentImg = new ImageIcon(this.getClass().getResource("/x.png")).getImage();
		}
		else if(winner ==3)
		{
			if(yourImg.equals("/x.png"))
			{
				active = true;
			}
			else
			{
				active = false;
			}
		}
		lblYourImg.setIcon(new ImageIcon(yourImg));
		lblOpponentImg.setIcon(new ImageIcon(opponentImg));
	}
	/**
	 * A private method that checks if any of the win conditions have been met.
	 * If win condition is met the game is over and the board is disabled.
	 */
	private void checkWin()
	{
		winner = 0;
		if(panel1.equals("Yours") && panel2.equals("Yours") && panel3.equals("Yours"))
		{
			gameOver = true;
			winner = 1;
		}
		else if(panel1.equals("Opponent") && panel2.equals("Opponent") && panel3.equals("Opponent"))
		{
			gameOver = true;
			winner = 2;
		}
		else if(panel1.equals("Yours") && panel4.equals("Yours") && panel7.equals("Yours"))
		{
			gameOver = true;
			winner = 1;
		}
		else if(panel1.equals("Opponent") && panel4.equals("Opponent") && panel7.equals("Opponent"))
		{
			gameOver = true;
			winner = 2;
		}
		else if(panel1.equals("Yours") && panel5.equals("Yours") && panel9.equals("Yours"))
		{
			gameOver = true;
			winner = 1;
		}
		else if(panel1.equals("Opponent") && panel5.equals("Opponent") && panel9.equals("Opponent"))
		{
			gameOver = true;
			winner = 2;
		}
		else if(panel2.equals("Yours") && panel5.equals("Yours") && panel8.equals("Yours"))
		{
			gameOver = true;
			winner = 1;
		}
		else if(panel2.equals("Opponent") && panel5.equals("Opponent") && panel8.equals("Opponent"))
		{
			gameOver = true;
			winner = 2;
		}
		else if(panel3.equals("Yours") && panel6.equals("Yours") && panel9.equals("Yours"))
		{
			gameOver = true;
			winner = 1;
		}
		else if(panel3.equals("Opponent") && panel6.equals("Opponent") && panel9.equals("Opponent"))
		{
			gameOver = true;
			winner = 2;
		}
		else if(panel3.equals("Yours") && panel5.equals("Yours") && panel7.equals("Yours"))
		{
			gameOver = true;
			winner = 1;
		}
		else if(panel3.equals("Opponent") && panel5.equals("Opponent") && panel7.equals("Opponent"))
		{
			gameOver = true;
			winner = 2;
		}
		else if(panel4.equals("Yours") && panel5.equals("Yours") && panel6.equals("Yours"))
		{
			gameOver = true;
			winner = 1;
		}
		else if(panel4.equals("Opponent") && panel5.equals("Opponent") && panel6.equals("Opponent"))
		{
			gameOver = true;
			winner = 2;
		}
		else if(panel7.equals("Yours") && panel8.equals("Yours") && panel9.equals("Yours"))
		{
			gameOver = true;
			winner = 1;
		}
		else if(panel7.equals("Opponent") && panel8.equals("Opponent") && panel9.equals("Opponent"))
		{
			gameOver = true;
			winner = 2;
		}
		else if(!panel1.equals("") && !panel2.equals("") && !panel3.equals("") && !panel4.equals("") && !panel5.equals("")
				&& !panel6.equals("") && !panel7.equals("") && !panel8.equals("") && !panel9.equals(""))
		{
			gameOver = true;
			winner = 3;
		}		
		
		if(gameOver == true)
		{
			active = false;
			if(winner == 1)
			{
				listModel.addElement("You win!");
				lblTurn.setText("Winner!!");
			}
			else if(winner == 2)
			{
				listModel.addElement("Opponent wins!");
				lblTurn.setText("Loser!!");
			}
			else
			{
				listModel.addElement("Tie game!");
				lblTurn.setText("Draw!!");
			}
		}
	}
	/**
	 * Method that asks the user if they wish to play again. Gets called when the game is over or the opponent
	 * has disconnected from the game.
	 */
	private void checkPlayAgain()
	{
		if(JOptionPane.showConfirmDialog(null, "Do you wish to play again?", "Play Again?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
		{
			playAgain();
		}
		else
		{
			Message msg = new Message("Disconnected", "System");
			try
			{
				oos.writeObject(msg);
			}
			catch(IOException e2)
			{
				e2.printStackTrace();
			}
			setVisible(false);
			dispose();
		}
	}
	
	/**
	 * private method that passes the information of the game move made by the user to their opponent.
	 * @param m - GameMove made by user
	 */
	private void makeMove(GameMove m)
	{
		try
		{
			oos.writeObject(m);
		}
		catch(IOException e2)
		{
			e2.printStackTrace();
		}
	}
	
	
	public void update(Observable obs, Object o)
	{
		if(o instanceof Message)
		{
			Message msgReceived = (Message)o;
			if (msgReceived.getUser().equals("ClientHandler"))
			{
				if(msgReceived.getMessage().equals("1"))
				{
					active = true;
					yourImg = new ImageIcon(this.getClass().getResource("/x.png")).getImage();
					opponentImg = new ImageIcon(this.getClass().getResource("/o.png")).getImage();
					lblTurn.setText("Your move");					
				}
				else
				{
					active = false;
					yourImg = new ImageIcon(this.getClass().getResource("/o.png")).getImage();
					opponentImg = new ImageIcon(this.getClass().getResource("/x.png")).getImage();
					lblTurn.setText("Opponent's move");
				}
				lblYourImg.setIcon(new ImageIcon(yourImg));
				lblOpponentImg.setIcon(new ImageIcon(opponentImg));
			}
			else if(msgReceived.getUser().equals("System"))
			{
				if(msgReceived.getMessage().equals("Disconnected"))
				{
					checkPlayAgain();
					connectToServer();
				}
			}
			else
				listModel.addElement(msgReceived.getUser() + ": " + msgReceived.getMessage());
			lastIndex = listModel.getSize() - 1;
			if (lastIndex >= 0) 
			{
				lstMessages.ensureIndexIsVisible(lastIndex);
			}
		}
		if(o instanceof GameMove)
		{
			GameMove opponentMove = (GameMove)o;
			active = true;
			lblTurn.setText("Your move");
			switch(opponentMove.getPanel())
			{
				case(1):
				{
					lblPanel1.setIcon(new ImageIcon(opponentImg));
					panel1 = "Opponent";
					checkWin();
					break;
				}
				case(2):
				{
					lblPanel2.setIcon(new ImageIcon(opponentImg));
					panel2 = "Opponent";
					checkWin();
					break;
				}
				case(3):
				{
					lblPanel3.setIcon(new ImageIcon(opponentImg));
					panel3 = "Opponent";
					checkWin();
					break;
				}
				case(4):
				{
					lblPanel4.setIcon(new ImageIcon(opponentImg));
					panel4 = "Opponent";
					checkWin();
					break;
				}
				case(5):
				{
					lblPanel5.setIcon(new ImageIcon(opponentImg));
					panel5 = "Opponent";
					checkWin();
					break;
				}
				case(6):
				{
					lblPanel6.setIcon(new ImageIcon(opponentImg));
					panel6 = "Opponent";
					checkWin();
					break;
				}
				case(7):
				{
					lblPanel7.setIcon(new ImageIcon(opponentImg));
					panel7 = "Opponent";
					checkWin();
					break;
				}
				case(8):
				{
					lblPanel8.setIcon(new ImageIcon(opponentImg));
					panel8 = "Opponent";
					checkWin();
					break;
				}
				case(9):
				{
					lblPanel9.setIcon(new ImageIcon(opponentImg));
					panel9 = "Opponent";
					checkWin();
					break;
				}
			}
		}
	}
}
