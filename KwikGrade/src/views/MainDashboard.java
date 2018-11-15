package views;

import models.Course;
import models.Student;

import java.io.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.List;
import javax.swing.JList;
import javax.swing.JScrollBar;

public class MainDashboard extends JFrame {
	DefaultListModel DLMActiveCourse;
	DefaultListModel DLMClosedCourse;

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
	 * Pulls active course names for display in dynamic Jlist
	 * @param courseList
	 */
	public DefaultListModel loadActiveCourseList() {
		DLMActiveCourse = new DefaultListModel();
		for(int i = 0; i < listOfCourses.size(); i++) {
			//if statement used to check if course is open
			if(listOfCourses.get(i).getIsOpen() == true) {
				DLMActiveCourse.addElement(listOfCourses.get(i).getCourseNum()+" "+listOfCourses.get(i).getCourseTerm()+" "+listOfCourses.get(i).getCourseTitle());
			}
		}
		return DLMActiveCourse;
	}
	
	/**
	 * Pulls closed course names for display in dynamic Jlist
	 * @param courseList
	 */
	public DefaultListModel loadClosedCourseList() {
		DLMClosedCourse = new DefaultListModel();
		for(int i = 0; i < listOfCourses.size(); i++) {
			//if statement used to check if course is open
			if(listOfCourses.get(i).getIsOpen() == false) {
				DLMClosedCourse.addElement(listOfCourses.get(i).getCourseNum()+" "+listOfCourses.get(i).getCourseTerm()+" "+listOfCourses.get(i).getCourseTitle());
			}
		}
		return DLMClosedCourse;
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
		
		JLabel coursesLabel = new JLabel("Active Courses");
		coursesLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		coursesLabel.setBounds(12, 0, 128, 42);
		contentPane.add(coursesLabel);
		
		//jlist for dynamic display of courses
		JList activeCourseDisplayList = new JList();
		activeCourseDisplayList.setFont(new Font("Tahoma", Font.PLAIN, 18));
		activeCourseDisplayList.setBounds(12, 41, 456, 229);
		contentPane.add(activeCourseDisplayList);
		
		JLabel closedCourseLabel = new JLabel("Closed Courses");
		closedCourseLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		closedCourseLabel.setBounds(12, 281, 128, 42);
		contentPane.add(closedCourseLabel);
		
		JList closedCourseDisplayList = new JList();
		closedCourseDisplayList.setFont(new Font("Tahoma", Font.PLAIN, 18));
		closedCourseDisplayList.setBounds(12, 334, 456, 133);
		contentPane.add(closedCourseDisplayList);

		//loads courses upon login for display in dynamic list
		activeCourseDisplayList.setModel(loadActiveCourseList());
		closedCourseDisplayList.setModel(loadClosedCourseList());
		
		JButton addCourseButton = new JButton("Add Course");
		addCourseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				CreateCourseFrame createCourse = new CreateCourseFrame();
				createCourse.setModal(true);
				createCourse.setVisible(true);
				addCourse(createCourse.getCourseNum(), createCourse.getCourseTerm(), createCourse.getCourseTitle(), createCourse.getImportedStudentsList());
				
				//saves file upon course creation
				saveFile(listOfCourses, SERIALIZED_FILE_NAME);
				activeCourseDisplayList.setModel(loadActiveCourseList());
			}
		});
		addCourseButton.setBounds(480, 13, 166, 71);
		contentPane.add(addCourseButton);
		
		JButton closeCourseButton = new JButton("Close Course");
		closeCourseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int closeIndex = activeCourseDisplayList.getSelectedIndex();
				listOfCourses.get(closeIndex).setIsOpen(false);
				closedCourseDisplayList.setModel(loadClosedCourseList());
				activeCourseDisplayList.setModel(loadActiveCourseList());
				
			}
		});
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
    
		//loads courses upon login with button, for display in dynamic list
		JButton refreshCourseButton = new JButton("Refresh");
		refreshCourseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				activeCourseDisplayList.setModel(loadActiveCourseList());
			}
		});
		refreshCourseButton.setBounds(480, 268, 166, 71);
		contentPane.add(refreshCourseButton);
		

	}
}
