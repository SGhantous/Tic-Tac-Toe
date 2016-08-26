package clientSide;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JTextField;

public class AppDriver extends JFrame
{

	private JPanel	contentPane;
	private JTextField txtServer;
	private JTextField txtPort;
	private JTextField txtUser;
	private MyActionListener actionListener;
	private JButton btnConnect, btnExit;
	

	//Constants
	//Attributes
	//Constructors
	//Getter and Setter Methods
	//Operational Methods

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					AppDriver frame = new AppDriver();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AppDriver()
	{
		actionListener = new MyActionListener();
		setTitle("Tic-Tac-Toe Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 320);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTictactoe = new JLabel("Tic-Tac-Toe");
		lblTictactoe.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 40));
		lblTictactoe.setBounds(85, 13, 286, 61);
		contentPane.add(lblTictactoe);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panel.setBounds(33, 87, 357, 141);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblServer = new JLabel("Server:");
		lblServer.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblServer.setBounds(39, 36, 66, 16);
		panel.add(lblServer);
		
		txtServer = new JTextField();
		txtServer.setBounds(103, 35, 202, 22);
		panel.add(txtServer);
		txtServer.setColumns(10);
		
		JLabel lblPort = new JLabel("Port:");
		lblPort.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPort.setBounds(56, 70, 38, 16);
		panel.add(lblPort);
		
		txtPort = new JTextField();
		txtPort.setColumns(10);
		txtPort.setBounds(103, 69, 202, 22);
		panel.add(txtPort);
		
		JLabel lblUser = new JLabel("Username:");
		lblUser.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblUser.setBounds(10, 103, 117, 16);
		panel.add(lblUser);
		
		txtUser = new JTextField();
		txtUser.setColumns(10);
		txtUser.setBounds(103, 102, 202, 22);
		panel.add(txtUser);
		
		btnConnect = new JButton("Connect");
		btnConnect.setBounds(33, 241, 97, 25);
		btnConnect.addActionListener(actionListener);
		contentPane.add(btnConnect);
		
		btnExit = new JButton("Exit");
		btnExit.setBounds(293, 241, 97, 25);
		contentPane.add(btnExit);
	}
	/**********************************INNER CLASSES****************************************/
	private class MyActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == btnConnect)
			{
				String username = txtUser.getText();
				String address = txtServer.getText();
				String port = txtPort.getText();
				if(username != null && !username.equals(""))
					{
						ClientGUI client = new ClientGUI(username, address, port);
						client.setVisible(true);
						setVisible(false);
					}
				
			}
		}
	}
}
