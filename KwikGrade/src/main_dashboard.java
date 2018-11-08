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
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JEditorPane;
import java.awt.Choice;
import java.awt.List;

public class main_dashboard extends JFrame {

	private JPanel contentPane;
	public ArrayList<course> list_of_courses = new ArrayList<course>();

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
		
		JLabel lblSecondForm = new JLabel("Courses");
		lblSecondForm.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSecondForm.setBounds(12, 0, 128, 42);
		contentPane.add(lblSecondForm);
		
		JButton btnAddCourse = new JButton("Add Course");
		btnAddCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				course course = new course("RAWR");
//				list_of_courses.add(course);
//				System.out.println(course.coursename);
//				System.out.println(list_of_courses);
				
				create_course_from createcourseform = new create_course_from();
				createcourseform.setVisible(true);
				

				
			}
		});
		btnAddCourse.setBounds(301, 24, 119, 42);
		contentPane.add(btnAddCourse);
		
		JButton btnCloseCourse = new JButton("Close Course");
		btnCloseCourse.setBounds(301, 80, 119, 42);
		contentPane.add(btnCloseCourse);
		
		List list = new List();
		list.setBounds(22, 48, 209, 175);
		contentPane.add(list);
	}
}
