package views;

import models.Course;
import models.Student;

import java.io.*;

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
	private static final String SERIALIZED_FILE_NAME = "serializedCourseSaveData.ser";
	
	public void addCourse(String courseNum, String courseTerm, String courseTitle, ArrayList<Student> importedStudentsList) {
		if(importedStudentsList.size() == 0) {
			Course courseToAdd = new Course(courseNum, courseTerm, courseTitle);
			this.listOfCourses.add(courseToAdd);
		}
		else {
			Course courseToAdd = new Course(courseNum, courseTerm, courseTitle, importedStudentsList);
			this.listOfCourses.add(courseToAdd);
		}
		System.out.println(listOfCourses);
	}

	/**
	 * Saves all courses and their state to a local file.
	 * @param courseList
	 */
	public void saveFile(ArrayList<Course> courseList, String saveFileName) {
		FileOutputStream fileOutputStream;
		ObjectOutputStream objectOutputStream;

		try {
			fileOutputStream = new FileOutputStream(saveFileName);
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			// Write Course list to file.
			objectOutputStream.writeObject(courseList);
			objectOutputStream.close();

			System.out.println("Successfully saved data.");
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * Load courses from saved local file.
	 * @param saveFileName
	 * @return
	 */
	public ArrayList<Course> loadFile(String saveFileName) {
		ArrayList<Course> savedCoursesList = new ArrayList<>();
		FileInputStream fileInputStream;
		ObjectInputStream objectInputStream;

		try {
			fileInputStream = new FileInputStream(saveFileName);
			objectInputStream = new ObjectInputStream(fileInputStream);
			savedCoursesList = (ArrayList<Course>) objectInputStream.readObject();
			objectInputStream.close();

			System.out.println("Successfully loaded save data.");
			return savedCoursesList;
		}
		catch(FileNotFoundException fnfe) {
			System.out.println("File not found: " + fnfe.getMessage());
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
		catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}

		return savedCoursesList;
	}

	/**
	 * Create the frame.
	 */
	public MainDashboard() {
		// Load from saved file.
		this.listOfCourses = loadFile(SERIALIZED_FILE_NAME);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 687, 517);
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
				addCourse(createCourse.getCourseNum(), createCourse.getCourseTerm(), createCourse.getCourseTitle(), createCourse.getImportedStudentsList());
			}
		});
		addCourseButton.setBounds(480, 13, 166, 71);
		contentPane.add(addCourseButton);
		
		JButton closeCourseButton = new JButton("Close Course");
		closeCourseButton.setBounds(480, 97, 166, 71);
		contentPane.add(closeCourseButton);

		JButton saveCourseButton = new JButton("Save");
		saveCourseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveFile(listOfCourses, SERIALIZED_FILE_NAME);
			}
		});

		saveCourseButton.setBounds(480, 181, 166, 71);
		contentPane.add(saveCourseButton);

		List list = new List();
		list.setBounds(22, 48, 407, 359);
		contentPane.add(list);
	}
}
