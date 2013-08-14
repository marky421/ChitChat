package com.maspain.chitchat;

import java.awt.EventQueue;

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

	/**
	 * Create the frame.
	 */
	public Login() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		setResizable(false);
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtName = new JTextField();
		txtName.setBounds(77, 36, 185, 28);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(36, 42, 46, 16);
		contentPane.add(lblName);
		
		txtAddress = new JTextField();
		txtAddress.setText("maspain.dyndns-web.com");
		txtAddress.setBounds(77, 104, 185, 28);
		contentPane.add(txtAddress);
		txtAddress.setColumns(10);
		
		lblIpAddress = new JLabel("Host:");
		lblIpAddress.setBounds(42, 110, 40, 16);
		contentPane.add(lblIpAddress);
		
		lblPort = new JLabel("Channel:");
		lblPort.setBounds(21, 178, 61, 16);
		contentPane.add(lblPort);
		
		lblIPAddressDescription = new JLabel("(eg. 67.183.210.5)");
		lblIPAddressDescription.setBounds(86, 132, 128, 16);
		contentPane.add(lblIPAddressDescription);
		
		channelList = new JComboBox<String>();
		channelList.setModel(new DefaultComboBoxModel<String>(new String[] {"Channel 1", "Channel 2", "Channel 3", "Channel 4", "Channel 5", "Channel 6", "Channel 7", "Channel 8", "Channel 9", "Channel 10"}));
		channelList.setSelectedIndex(0);
		channelList.setBounds(77, 174, 185, 27);
		contentPane.add(channelList);
		
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtName.getText();
				String address = txtAddress.getText();
				int port = 50000 + channelList.getSelectedIndex();
				login(name, address, port);
			}
		});
		btnLogin.setBounds(91, 223, 117, 29);
		contentPane.add(btnLogin);
		
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

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
