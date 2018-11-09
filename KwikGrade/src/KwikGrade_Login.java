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
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KwikGrade_Login {

	private JFrame frame;
	private JTextField UID;
	private JPasswordField PW;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KwikGrade_Login window = new KwikGrade_Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public KwikGrade_Login() {
		initialize();
	}
	
	public void login(String username, String password) {
		
		if(username.equals("rawr") && password.equals("superman")) {
			JOptionPane.showMessageDialog(null, "Successful Login!");
			frame.dispose();
			main_dashboard maindashboard = new main_dashboard();
			maindashboard.setVisible(true);
		}
		else {
			JOptionPane.showMessageDialog(null, "Incorrect Login!");
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
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
		
		JLabel UID_label = new JLabel("Username");
		UID_label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		UID_label.setBounds(454, 92, 78, 16);
		frame.getContentPane().add(UID_label);
		
		JLabel PW_label = new JLabel("Password");
		PW_label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		PW_label.setBounds(454, 177, 78, 27);
		frame.getContentPane().add(PW_label);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login(UID.getText(), PW.getText());
			}
		});
		
		btnNewButton.setBounds(419, 254, 150, 64);
		frame.getContentPane().add(btnNewButton);
		
		JLabel gradePic = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("grading_login.jpg")).getImage();
		gradePic.setIcon(new ImageIcon(img));
		gradePic.setBounds(0, 0, 379, 365);
		frame.getContentPane().add(gradePic);
		
		JLabel lblKwikgrade = new JLabel("KwikGrade");
		lblKwikgrade.setHorizontalAlignment(SwingConstants.CENTER);
		lblKwikgrade.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblKwikgrade.setBounds(419, 13, 150, 38);
		frame.getContentPane().add(lblKwikgrade);
	}
}
