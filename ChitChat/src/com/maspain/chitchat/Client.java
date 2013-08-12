package com.maspain.chitchat;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JTextArea;
import java.awt.GridBagConstraints;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;


public class Client extends JFrame {

	private static final long	serialVersionUID	= 1L;
	
	private JPanel	contentPane;
	private String name, address;
	private int port;
	private JTextArea txtrHistory;
	private JTextArea txtrMessage;
	private JButton btnSend;

	public Client(String name, String address, int port) {
		setTitle("ChitChat Client v0.9");
		this.name = name;
		this.address = address;
		this.port = port;
		createWindow();
	}
	
	private void createWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 400);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.001};
		contentPane.setLayout(gbl_contentPane);
		
		txtrHistory = new JTextArea();
		txtrHistory.setEditable(false);
		txtrHistory.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
		GridBagConstraints gbc_txtrHistory = new GridBagConstraints();
		gbc_txtrHistory.fill = GridBagConstraints.BOTH;
		gbc_txtrHistory.gridwidth = 2;
		gbc_txtrHistory.gridx = 0;
		gbc_txtrHistory.gridy = 0;
		gbc_txtrHistory.insets = new Insets(10, 10, 5, 10);
		gbc_txtrHistory.weightx = 0.0;
		contentPane.add(txtrHistory, gbc_txtrHistory);
		
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
