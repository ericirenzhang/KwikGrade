package views;

import models.Course;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.List;

public class MainDashboard extends JFrame {

	private JPanel contentPane;
	public ArrayList<Course> listOfCourses = new ArrayList<>();
	
	public void addCourse( String courseNum, String courseTerm, String courseTitle, boolean bulkAddStudents, String filePath) {
		if(bulkAddStudents == true) {
			Course courseToAdd = new Course(courseNum, courseTerm, courseTitle, filePath);
			this.listOfCourses.add(courseToAdd);
		}
		else {
			Course courseToAdd = new Course(courseNum, courseTerm, courseTitle);
			this.listOfCourses.add(courseToAdd);
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainDashboard frame = new MainDashboard();
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
	public MainDashboard() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel coursesLabel = new JLabel("Courses");
		coursesLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		coursesLabel.setBounds(12, 0, 128, 42);
		contentPane.add(coursesLabel);
		
		JButton addCourseButton = new JButton("Add Course");
		addCourseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				CreateCourseFrame createCourse = new CreateCourseFrame();
				createCourse.setModal(true);
				createCourse.setVisible(true);
				addCourse(createCourse.getCourseNum(), createCourse.getCourseTerm(), createCourse.getCourseTitle(), createCourse.getBulkAddStudents(), createCourse.getFilePath());
				
				//testing code to verify input
				//System.out.println(listOfCourses);
				
			}
		});
		addCourseButton.setBounds(301, 24, 119, 42);
		contentPane.add(addCourseButton);
		
		JButton closeCourseButton = new JButton("Close Course");
		closeCourseButton.setBounds(301, 80, 119, 42);
		contentPane.add(closeCourseButton);
		
		List list = new List();
		list.setBounds(22, 48, 209, 175);
		contentPane.add(list);
	}
}
