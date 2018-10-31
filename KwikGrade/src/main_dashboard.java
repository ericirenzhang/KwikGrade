import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class main_dashboard extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					main_dashboard frame = new main_dashboard();
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
	public main_dashboard() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSecondForm = new JLabel("SECOND FORM");
		lblSecondForm.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSecondForm.setBounds(12, 13, 128, 42);
		contentPane.add(lblSecondForm);
		
		JButton btnNewButton = new JButton("CS591");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JOptionPane.showMessageDialog(null, "THESE ARE THE GRADES FOR CS591");
			}
		});
		btnNewButton.setBounds(12, 57, 171, 89);
		contentPane.add(btnNewButton);
	}
}
