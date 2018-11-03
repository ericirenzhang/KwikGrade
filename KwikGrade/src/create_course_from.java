import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;

public class create_course_from extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					create_course_from frame = new create_course_from();
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
	public create_course_from() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblHowWouldYou = new JLabel("How would you like to create your course?");
		lblHowWouldYou.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblHowWouldYou.setBounds(84, 11, 268, 41);
		contentPane.add(lblHowWouldYou);
		
		JButton from_existing = new JButton("Create From Existing");
		from_existing.setFont(new Font("Tahoma", Font.PLAIN, 14));
		from_existing.setBounds(10, 63, 181, 144);
		contentPane.add(from_existing);
		
		JButton from_new = new JButton("Create From New");
		from_new.setFont(new Font("Tahoma", Font.PLAIN, 14));
		from_new.setBounds(243, 63, 181, 144);
		contentPane.add(from_new);
	}

}
