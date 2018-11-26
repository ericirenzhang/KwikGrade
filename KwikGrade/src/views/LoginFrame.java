package views;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.io.File;
import java.util.Scanner;
import java.util.*;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LoginFrame {
	private static final String GRADING_IMAGE_STRING = "grading_login.jpg";
	private JFrame frame;
	private JTextField UID;
	private JPasswordField PW;
	private Scanner rawCreds;

	/**
	 * Create the application.
	 */
	public LoginFrame() {
		initializeFrameContents();
	}
	
	//checks if a file to store credentials exists
	public void checkForCreds() {
		File credFile = new File("logincredentials.txt");
		if (credFile.exists()) {
		}
		else {
			JOptionPane.showMessageDialog(null, "Credentials have not been set yet.");
			File file = new File("logincredentials.txt");
			//re-using code, uses ChangeCreds frame to get new credentials
			ChangeCreds newCredsFrame = new ChangeCreds();
			newCredsFrame.setModal(true);
			newCredsFrame.setVisible(true);
		}
	}

	//generic function to verify if the credentials match what is on the file
	public boolean verifyCreds(String username, String password) {
		System.getProperty("user.dir");
		try {
			rawCreds = new Scanner(new File("logincredentials.txt"));
			String line = rawCreds.nextLine();
			List<String> splitLine = Arrays.asList(line.split(","));
			if( username.equals(splitLine.get(0)) && password.equals(splitLine.get(1)) ) {
				rawCreds.close();
				return true;
			}
			else {
				rawCreds.close();
				return false;
			}
		}
		catch(Exception e) {
			//if file is not found, it calls checkForCreds again to create the credential file
			checkForCreds();
			return false;
		}
	}
	
	//method to create a main dashboard object if username and password match
	public void login(String username, String password) {
		if(verifyCreds(username, password)) {
			frame.dispose();
			MainDashboard maindashboard = new MainDashboard();
			maindashboard.setVisible(true);
		}
		else {
			JOptionPane.showMessageDialog(null, "Incorrect Login!");
		}
	}
	
	//method to change user credentials
	public void credChange(String username, String password) {
		if(verifyCreds(username, password)) {
			ChangeCreds changeCredsFrame = new ChangeCreds();
			changeCredsFrame.setModal(true);
			changeCredsFrame.setVisible(true);
		}
		else {
			JOptionPane.showMessageDialog(null, "Incorrect Login!");
		}
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initializeFrameContents() {
		frame = new JFrame();
		frame.setBounds(100, 100, 617, 412);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		UID = new JTextField();
		UID.setBounds(410, 119, 170, 22);
		frame.getContentPane().add(UID);
		UID.setColumns(10);
		
		PW = new JPasswordField();
		PW.setBounds(410, 204, 170, 22);
		frame.getContentPane().add(PW);
		
		PW.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					login(UID.getText(), PW.getText());
				}
			}
		});
		
		JLabel usernameLabel = new JLabel("Username");
		usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		usernameLabel.setBounds(454, 92, 78, 16);
		frame.getContentPane().add(usernameLabel);
		
		JLabel pwLabel = new JLabel("Password");
		pwLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pwLabel.setBounds(454, 177, 78, 27);
		frame.getContentPane().add(pwLabel);
		
		JButton loginButton = new JButton("Login");
		loginButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login(UID.getText(), PW.getText());
			}
		});
		
		loginButton.setBounds(410, 254, 170, 64);
		frame.getContentPane().add(loginButton);
		
		JLabel gradePic = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource(GRADING_IMAGE_STRING)).getImage();
		gradePic.setIcon(new ImageIcon(img));
		gradePic.setBounds(0, 0, 379, 365);
		frame.getContentPane().add(gradePic);
		
		JLabel kwikGradeLabel = new JLabel("KwikGrade");
		kwikGradeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		kwikGradeLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		kwikGradeLabel.setBounds(419, 13, 150, 38);
		frame.getContentPane().add(kwikGradeLabel);
		
		JButton changeCredsButton = new JButton("Change Credentials");
		changeCredsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				credChange(UID.getText(), PW.getText());
			}
		});
		changeCredsButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		changeCredsButton.setBounds(410, 329, 170, 33);
		frame.getContentPane().add(changeCredsButton);

		frame.setVisible(true);
		
		//checks for user credentials upon startup
		checkForCreds();
	}
}
