import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class login_page extends JFrame {

	private JPanel contentPane;
	private JPasswordField login_pw;
	private JTextField login_uid;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login_page frame = new login_page();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public login_page() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 545, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton login_btn = new JButton("Login");
		login_btn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		login_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username, password, dummyuser, dummypw;
				username = login_uid.getText();
				password = login_pw.getText();
				
				if(username.equals("rawr") && password.equals("superman")) {
					JOptionPane.showMessageDialog(null, "Correct Info! Everyone gets an A");
				}
				else {
					JOptionPane.showMessageDialog(null, "WRONG USERNAME!!!! Everyone gets a F");
				}
				
			}
		});
		login_btn.setBounds(362, 225, 134, 54);
		contentPane.add(login_btn);
		
		login_pw = new JPasswordField();
		login_pw.setBounds(360, 172, 136, 20);
		contentPane.add(login_pw);
		
		login_uid = new JTextField();
		login_uid.setBounds(362, 115, 134, 20);
		contentPane.add(login_uid);
		login_uid.setColumns(10);
		
		JLabel username_lbl = new JLabel("User Name");
		username_lbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		username_lbl.setBounds(389, 90, 89, 14);
		contentPane.add(username_lbl);
		
		JLabel pw_lbl = new JLabel("Password");
		pw_lbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pw_lbl.setBounds(398, 147, 66, 14);
		contentPane.add(pw_lbl);
		
		JLabel grade_pic = new JLabel("New label");
		Image img = new ImageIcon(this.getClass().getResource("grading_login.jpg")).getImage();
		grade_pic.setIcon(new ImageIcon(img));
		grade_pic.setBounds(0, 0, 315, 361);
		contentPane.add(grade_pic);
		
		JLabel lblKwikgrade = new JLabel("KwikGrade");
		lblKwikgrade.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblKwikgrade.setBounds(377, 11, 119, 43);
		contentPane.add(lblKwikgrade);
	}
}
