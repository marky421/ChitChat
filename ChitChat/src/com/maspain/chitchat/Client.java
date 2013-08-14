package com.maspain.chitchat;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JTextArea;
import java.awt.GridBagConstraints;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Client extends JFrame implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private String name, address;
	private int port;
	private JScrollPane scrollPane;
	private JTextArea txtrHistory;
	private JTextArea txtrMessage;
	private JButton btnSend;
	
	// The Socket connection us to the server
	private Socket socket;
	
	// The streams we communicate to the server; these come from the socket
	private DataInputStream din;
	private DataOutputStream dout;
	
	public Client(String name, String address, int port) {

		setTitle("ChitChat Client v1.0");
		
		this.name = name;
		this.address = address;
		this.port = port;

		createWindow();
		connectToServer();

		// Add new functionality by adding a new key binding to "shift ENTER"
		// We want to be able to press "shift ENTER" to generate a new line in the message
		Action newLineOnShiftEnter = new AbstractAction() {
			
			private static final long serialVersionUID = 1L;
			
			public void actionPerformed(ActionEvent e) {
				txtrMessage.append("\n");
			}
		};
		String newLineKeyStrokeAndKey = "shift ENTER";
		KeyStroke newLineKeyStroke = KeyStroke.getKeyStroke(newLineKeyStrokeAndKey);
		txtrMessage.getInputMap(JComponent.WHEN_FOCUSED).put(newLineKeyStroke,
				newLineKeyStrokeAndKey);
		txtrMessage.getActionMap().put(newLineKeyStrokeAndKey, newLineOnShiftEnter);
		
		// Replace the functionality of the "ENTER" key stroke so that it sends the message
		// rather than generate a new line in the message to be sent
		Action sendOnEnter = new AbstractAction() {
			
			private static final long serialVersionUID = 1L;
			
			public void actionPerformed(ActionEvent e) {
				try {
					processMessage(txtrMessage.getText());
				}
				catch (NullPointerException ne) {
					System.out.println("message is empty. exception: " + ne);
				}
			}
		};
		KeyStroke sendKeyStroke = KeyStroke.getKeyStroke("ENTER");
		InputMap im = txtrMessage.getInputMap(JComponent.WHEN_FOCUSED);
		txtrMessage.getActionMap().put(im.get(sendKeyStroke), sendOnEnter);
		
		// Attach the send action to the send button
		btnSend.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					processMessage(txtrMessage.getText());
				}
				catch (NullPointerException ne) {
					System.out.println("message is empty. exception: " + ne);
				}
			}
		});
		
	}
	
	private void connectToServer() {
		try {

			socket = new Socket(InetAddress.getByName(address), port);

			// We got a connection! Tell the world
			System.out.println("connected to " + socket);
			
			// Let's grab the streams and create DataInput/Output streams from
			// them
			din = new DataInputStream(socket.getInputStream());
			dout = new DataOutputStream(socket.getOutputStream());
			
			// Start a background thread for receiving messages
			new Thread(this).start();
		} catch (IOException ie) {
			System.out.println(ie + " ------- in method: connectToServer()");
		}
	}
	
	// Gets called when the user types something
	private void processMessage(String message) {
		try {
			
			// Create a time stamp for the current message
			String timeStamp = new SimpleDateFormat("MMM dd @ hh:mm:ss aaa").format(Calendar
					.getInstance().getTime());
			
			// Send the message with the time stamp and the name of the sender to the server
			dout.writeUTF(name + " (" + timeStamp + "): " + message);
			
			// Clear out the text input field
			txtrMessage.setText("");
		} catch (IOException ie) {
			System.out.println(ie);
		}
	}
	
	// Background thread runs this: show messages from other window
	public void run() {
		try {
			
			// Receive messages one-by-one, forever
			while (true) {
				
				// Get the next message
				String message = din.readUTF();
				
				// Print it to our text window
				txtrHistory.append(message + "\n");
				txtrHistory.setCaretPosition(txtrHistory.getDocument().getLength());
			}
		}
		catch (IOException ie) {
			ie.printStackTrace();
		}
	}
	
	private void createWindow() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 400);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 0.0 };
		gbl_contentPane.rowWeights = new double[] { 1.0, 0.001 };
		contentPane.setLayout(gbl_contentPane);
		
		txtrHistory = new JTextArea();
		txtrHistory.setWrapStyleWord(true);
		txtrHistory.setLineWrap(true);
		txtrHistory.setEditable(false);
		txtrHistory.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
		GridBagConstraints gbc_txtrHistory = new GridBagConstraints();
		gbc_txtrHistory.fill = GridBagConstraints.BOTH;
		gbc_txtrHistory.gridwidth = 2;
		gbc_txtrHistory.gridx = 0;
		gbc_txtrHistory.gridy = 0;
		gbc_txtrHistory.insets = new Insets(10, 10, 5, 10);
		gbc_txtrHistory.weightx = 0.0;
		scrollPane = new JScrollPane(txtrHistory);
		contentPane.add(scrollPane, gbc_txtrHistory);
		
		txtrMessage = new JTextArea();
		txtrMessage.setWrapStyleWord(true);
		txtrMessage.setLineWrap(true);
		txtrMessage.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
		GridBagConstraints gbc_txtrMessage = new GridBagConstraints();
		gbc_txtrMessage.insets = new Insets(5, 10, 10, 5);
		gbc_txtrMessage.fill = GridBagConstraints.BOTH;
		gbc_txtrMessage.gridx = 0;
		gbc_txtrMessage.gridy = 1;
		contentPane.add(txtrMessage, gbc_txtrMessage);
		
		btnSend = new JButton("Send");
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.fill = GridBagConstraints.BOTH;
		gbc_btnSend.insets = new Insets(5, 5, 10, 10);
		gbc_btnSend.gridx = 1;
		gbc_btnSend.gridy = 1;
		contentPane.add(btnSend, gbc_btnSend);
		
		setVisible(true);
	}
}
