package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Scanner;
import java.util.*;
import java.util.List;

import javax.swing.*;

public class LoginFrame {
	private static final String GRADING_IMAGE_STRING = "grading_login.jpg";
	private static final String LOGIN_FILE_PATH = "logincredentials.txt";
	private JFrame frame;
	private JTextField usernameField;
	private JPasswordField pwField;
	private Scanner rawCreds;

	/**
	 * Create the application.
	 */
	public LoginFrame() {
		frame = new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridBagLayout());

		// Set up left and right panels.
		JPanel picturePanel = createPicturePanel();
		JPanel loginPanel = createLoginPanel();

		JPanel contentPanel = new JPanel(new BorderLayout(8,8));
		contentPanel.add(picturePanel, BorderLayout.WEST);
		contentPanel.add(loginPanel, BorderLayout.EAST);
		frame.getContentPane().add(contentPanel);
		frame.setVisible(true);

		// Checks for user credentials upon startup.
		checkForCreds();
	}

	/**
	 * Checks if a file to store credentials exists.
	 */
	public void checkForCreds() {
		File credFile = new File(LOGIN_FILE_PATH);
		if (!credFile.exists()) {
			JOptionPane.showMessageDialog(null, "Credentials have not been set yet.");

			// Re-using code, uses ChangeCredsFrame frame to get new credentials
			ChangeCredsFrame newCredsFrame = new ChangeCredsFrame();
			newCredsFrame.setModal(true);
			newCredsFrame.setVisible(true);
		}
	}

	/**
	 * Verifies if entered credentials match what is on the file.
	 *
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean verifiedCredentials(String username, String password) {
		System.getProperty("user.dir");
		try {
			rawCreds = new Scanner(new File(LOGIN_FILE_PATH));
			String line = rawCreds.nextLine();
			List<String> splitLine = Arrays.asList(line.split(","));
			if(username.equals(splitLine.get(0)) && password.equals(splitLine.get(1)) ) {
				rawCreds.close();
				return true;
			}
			else {
				rawCreds.close();
				return false;
			}
		}
		catch(Exception e) {
			// if file is not found, it calls checkForCreds again to create the credential file
			checkForCreds();
			return false;
		}
	}
	
	//

	/**
	 * Method to create a MainDashboard object if username and password match.
	 * @param username
	 * @param password
	 */
	public void verifyAndOpenDashboard(String username, String password) {
		if(verifiedCredentials(username, password)) {
			frame.dispose();
			MainDashboard mainDashboard = new MainDashboard();
			mainDashboard.setVisible(true);
		}
		else {
			JOptionPane.showMessageDialog(null, "Incorrect Login!");
		}
	}

	/**
	 * Method to change user credentials.
	 * @param username
	 * @param password
	 */
	public void credChange(String username, String password) {
		if(verifiedCredentials(username, password)) {
			ChangeCredsFrame changeCredsFrame = new ChangeCredsFrame();
			changeCredsFrame.setModal(true);
			changeCredsFrame.setVisible(true);
		}
		else {
			JOptionPane.showMessageDialog(null, "Incorrect Login!");
		}
	}

	/**
	 * Creates the verifyAndOpenDashboard components (relevant JLabels and JTextFields) and adds them to a Panel.
	 *
	 * @return JPanel containing all the verifyAndOpenDashboard components
	 */
	private JPanel createLoginPanel() {
		JLabel kwikGradeLabel = new JLabel("KwikGrade");
		kwikGradeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		kwikGradeLabel.setFont(new Font("Tahoma", Font.BOLD, 22));

		JLabel usernameLabel = new JLabel("Username");
		usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		usernameField = new JTextField();
		usernameField.setPreferredSize(new Dimension(170, 20));

		JLabel pwLabel = new JLabel("Password");
		pwLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pwField = new JPasswordField();
		pwField.setPreferredSize(new Dimension(170, 20));
		pwField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					verifyAndOpenDashboard(usernameField.getText(), pwField.getText());
				}
			}
		});

		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				verifyAndOpenDashboard(usernameField.getText(), pwField.getText());
			}
		});
		loginButton.setPreferredSize(new Dimension(170, 20));


		JButton changeCredsButton = new JButton("Change Credentials");
		changeCredsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				credChange(usernameField.getText(), pwField.getText());
			}
		});
		changeCredsButton.setPreferredSize(new Dimension(170, 20));

		// Add all components into a JPanel with respective constraints.
		JPanel loginPanel = new JPanel();
		loginPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// Add some bottom padding between "KwikGrade" and "Username".
		c.insets = new Insets(0,0,10,0);
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 1;
		loginPanel.add(kwikGradeLabel, c);

		c.insets = new Insets(0,0,0,0);
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 2;
		loginPanel.add(usernameLabel, c);

		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 3;
		loginPanel.add(usernameField, c);

		c.insets = new Insets(0,0,10,0);
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 4;
		loginPanel.add(pwLabel, c);

		// Add some bottom padding between pwField and loginButton
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 5;
		loginPanel.add(pwField, c);

		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 170;
		loginPanel.add(loginButton, c);

		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 170;
		loginPanel.add(changeCredsButton, c);
		return loginPanel;
	}

	private JPanel createPicturePanel() {
		JLabel gradePic = new JLabel();
		Image img = new ImageIcon(this.getClass().getResource(GRADING_IMAGE_STRING)).getImage();
		gradePic.setIcon(new ImageIcon(img));

		JPanel picturePanel = new JPanel(new FlowLayout((FlowLayout.LEFT)));
		picturePanel.add(gradePic);
		return picturePanel;
	}
}