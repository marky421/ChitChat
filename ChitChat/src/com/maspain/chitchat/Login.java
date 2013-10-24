package com.maspain.chitchat;

//import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;


public class Login extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField txtName;
	private JTextField txtAddress;
	private JLabel lblIpAddress;
	private JButton btnLogin;
	private JLabel lblPort;
	private JLabel lblIPAddressDescription;
	private JComboBox<String> channelList;
	
	private String defaultServer = "server.maspain.com";
	
	public Login() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		setResizable(true);
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{61, 176, 0};
		gbl_contentPane.rowHeights = new int[]{36, 28, 40, 28, 16, 27, 29, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblName = new JLabel("Name:");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.EAST;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 0;
		contentPane.add(lblName, gbc_lblName);
		
		txtName = new JTextField();
		GridBagConstraints gbc_txtName = new GridBagConstraints();
		gbc_txtName.fill = GridBagConstraints.BOTH;
		gbc_txtName.insets = new Insets(0, 0, 5, 0);
		gbc_txtName.gridwidth = 2;
		gbc_txtName.gridx = 1;
		gbc_txtName.gridy = 0;
		contentPane.add(txtName, gbc_txtName);
		txtName.setColumns(10);
		
		lblIpAddress = new JLabel("Host:");
		GridBagConstraints gbc_lblIpAddress = new GridBagConstraints();
		gbc_lblIpAddress.anchor = GridBagConstraints.EAST;
		gbc_lblIpAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblIpAddress.gridx = 0;
		gbc_lblIpAddress.gridy = 1;
		contentPane.add(lblIpAddress, gbc_lblIpAddress);
		
		txtAddress = new JTextField();
		txtAddress.setText(defaultServer);
		GridBagConstraints gbc_txtAddress = new GridBagConstraints();
		gbc_txtAddress.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAddress.insets = new Insets(0, 0, 5, 0);
		gbc_txtAddress.gridwidth = 2;
		gbc_txtAddress.gridx = 1;
		gbc_txtAddress.gridy = 1;
		contentPane.add(txtAddress, gbc_txtAddress);
		txtAddress.setColumns(10);
		
		lblIPAddressDescription = new JLabel("(eg. 67.183.210.5)");
		GridBagConstraints gbc_lblIPAddressDescription = new GridBagConstraints();
		gbc_lblIPAddressDescription.anchor = GridBagConstraints.WEST;
		gbc_lblIPAddressDescription.insets = new Insets(0, 0, 5, 0);
		gbc_lblIPAddressDescription.gridx = 1;
		gbc_lblIPAddressDescription.gridy = 2;
		contentPane.add(lblIPAddressDescription, gbc_lblIPAddressDescription);
		
		lblPort = new JLabel("Channel:");
		GridBagConstraints gbc_lblPort = new GridBagConstraints();
		gbc_lblPort.insets = new Insets(0, 0, 5, 5);
		gbc_lblPort.gridx = 0;
		gbc_lblPort.gridy = 3;
		contentPane.add(lblPort, gbc_lblPort);
		
		channelList = new JComboBox<String>();
		channelList.setModel(new DefaultComboBoxModel<String>(new String[] {"Channel 1", "Channel 2", "Channel 3", "Channel 4", "Channel 5", "Channel 6", "Channel 7", "Channel 8", "Channel 9", "Channel 10"}));
		channelList.setSelectedIndex(0);
		GridBagConstraints gbc_channelList = new GridBagConstraints();
		gbc_channelList.anchor = GridBagConstraints.WEST;
		gbc_channelList.fill = GridBagConstraints.VERTICAL;
		gbc_channelList.insets = new Insets(0, 0, 5, 0);
		gbc_channelList.gridwidth = 2;
		gbc_channelList.gridx = 1;
		gbc_channelList.gridy = 3;
		contentPane.add(channelList, gbc_channelList);
		
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtName.getText();
				String address = txtAddress.getText();
				int port = 50000 + channelList.getSelectedIndex();
				login(name, address, port);
			}
		});
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.insets = new Insets(0, 0, 5, 0);
		gbc_btnLogin.gridx = 1;
		gbc_btnLogin.gridy = 5;
		contentPane.add(btnLogin, gbc_btnLogin);
		
		// Create the functionality for the "ENTER" key stroke so that it logs in when pressed
		Action loginOnEnter = new AbstractAction() {
			private static final long	serialVersionUID	= 1L;

			public void actionPerformed(ActionEvent e) {
				String name = txtName.getText();
				String address = txtAddress.getText();
				int port = 50000 + channelList.getSelectedIndex();
				login(name, address, port);
			}
		};
		KeyStroke loginKeyStroke = KeyStroke.getKeyStroke("ENTER");
		String aCommand = "login";
		contentPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(loginKeyStroke, aCommand);
		contentPane.getActionMap().put(aCommand, loginOnEnter);
	}
	
	private void login(String name, String address, int port) {
		dispose();
		new Client(name, address, port);
	}
}
