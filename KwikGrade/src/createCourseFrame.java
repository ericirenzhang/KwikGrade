import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class createCourseFrame extends JFrame {

	private String courseNum;
	private String courseTerm;
	private String courseTitle;
	
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					createCourseFrame frame = new createCourseFrame();
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
	public createCourseFrame() {
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
		from_new.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createFromNewFrame create_fromnew = new createFromNewFrame();
				create_fromnew.setModal(true);
				create_fromnew.setVisible(true);
				System.out.println("RAWRRRRRR");
				System.out.println(create_fromnew.get_num());
				System.out.println(create_fromnew.get_term());
				System.out.println(create_fromnew.get_title());
				
				courseNum = create_fromnew.get_num();
				courseTerm = create_fromnew.get_term();
				courseTitle = create_fromnew.get_title();
			}
		});
		from_new.setFont(new Font("Tahoma", Font.PLAIN, 14));
		from_new.setBounds(243, 63, 181, 144);
		contentPane.add(from_new);	
	}
	
	//==========================
	// Getters
	//==========================
	
	public String getCourseNum() {
		return this.courseNum;
	}
	
	public String getCourseTerm() {
		return this.courseTerm;
	}

	public String getCourseTitle() {
		return this.courseTitle;
	}
	
	//==========================
	// Setters
	//==========================
	public void setCourseNum(String courseNum) {
		this.courseNum = courseNum;
	}
	
	public void setCourseTerm(String courseTerm) {
		this.courseTerm = courseTerm;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

}
