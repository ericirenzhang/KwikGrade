package views;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginFrame {
	private static final String GRADING_IMAGE_STRING = "grading_login.jpg";
	private JFrame frame;
	private JTextField UID;
	private JPasswordField PW;

	/**
	 * Create the application.
	 */
	public LoginFrame() {
		initializeFrameContents();
	}
	
	public void login(String username, String password) {
		if(username.equals("rawr") && password.equals("superman")) {
			JOptionPane.showMessageDialog(null, "Successful Login!");
			frame.dispose();
			MainDashboard maindashboard = new MainDashboard();
			maindashboard.setVisible(true);
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
		UID.setBounds(419, 121, 150, 22);
		frame.getContentPane().add(UID);
		UID.setColumns(10);
		
		PW = new JPasswordField();
		PW.setBounds(419, 206, 150, 22);
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
		
		loginButton.setBounds(419, 254, 150, 64);
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

		frame.setVisible(true);
	}
}
