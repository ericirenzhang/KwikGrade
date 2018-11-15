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
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;

public class MainDashboard extends JFrame {
	DefaultListModel DLM;

	private JPanel contentPane;
	public ArrayList<Course> activeCourses = new ArrayList<>();
	public ArrayList<Course> closedCourses = new ArrayList<>();
	private static final String SERIALIZED_FILE_NAME_ACTIVE = "serializedActiveCourseSaveData.ser";
	private static final String SERIALIZED_FILE_NAME_CLOSED = "serializedClosedCourseSaveData.ser";

	
	/**
	 * Adds courses to the active list. Only active course list can have courses added to it
	 * @param courseList
	 */
	public void addCourse(String courseNum, String courseTerm, String courseTitle, ArrayList<Student> importedStudentsList) {
		if(importedStudentsList.size() == 0) {
			Course courseToAdd = new Course(courseNum, courseTerm, courseTitle);
			this.activeCourses.add(courseToAdd);
		}
		else {
			Course courseToAdd = new Course(courseNum, courseTerm, courseTitle, importedStudentsList);
			this.activeCourses.add(courseToAdd);
		}
		System.out.println(activeCourses);
	}
	
	
	/**
	 * Pulls active course names for display in dynamic Jlist
	 * @param courseList
	 * @return Default List Model
	 */
	public DefaultListModel loadCourseList(ArrayList<Course> courseList) {
		DLM = new DefaultListModel();
		for(int i = 0; i < courseList.size(); i++) {
			DLM.addElement(courseList.get(i).getCourseNum()+" "+courseList.get(i).getCourseTerm()+" "+courseList.get(i).getCourseTitle());
		}
		return DLM;
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
		this.activeCourses = loadFile(SERIALIZED_FILE_NAME_ACTIVE);
		this.closedCourses = loadFile(SERIALIZED_FILE_NAME_CLOSED);

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 687, 517);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/**
		 * elements to create the active course list display panel
		 */		
		JLabel activeCoursesLabel = new JLabel("Active Courses");
		activeCoursesLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		activeCoursesLabel.setBounds(12, 0, 128, 42);
		contentPane.add(activeCoursesLabel);
		
		JScrollPane activeCourseScrollPane = new JScrollPane();
		activeCourseScrollPane.setBounds(12, 41, 456, 229);
		contentPane.add(activeCourseScrollPane);
		
		//jlist for dynamic display of courses
		JList activeCourseDisplayList = new JList();
		activeCourseScrollPane.setViewportView(activeCourseDisplayList);
		activeCourseDisplayList.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
				//loads courses upon login for display in dynamic list
				activeCourseDisplayList.setModel(loadCourseList(activeCourses));
		
		/**
		 * elements to create the closed course list display panel
		 */	
		JLabel closedCourseLabel = new JLabel("Closed Courses");
		closedCourseLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		closedCourseLabel.setBounds(12, 281, 128, 42);
		contentPane.add(closedCourseLabel);
		
		JScrollPane closedCourseScrollPane = new JScrollPane();
		closedCourseScrollPane.setBounds(12, 334, 456, 133);
		contentPane.add(closedCourseScrollPane);
		
		JList closedCourseDisplayList = new JList();
		closedCourseScrollPane.setViewportView(closedCourseDisplayList);
		closedCourseDisplayList.setFont(new Font("Tahoma", Font.PLAIN, 18));
		closedCourseDisplayList.setModel(loadCourseList(closedCourses));
		
		
		//button to add courses
		JButton addCourseButton = new JButton("Add Course");
		addCourseButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		addCourseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				CreateCourseFrame createCourse = new CreateCourseFrame();
				createCourse.setModal(true);
				createCourse.setVisible(true);
				addCourse(createCourse.getCourseNum(), createCourse.getCourseTerm(), createCourse.getCourseTitle(), createCourse.getImportedStudentsList());
				
				//saves file upon course creation
				saveFile(activeCourses, SERIALIZED_FILE_NAME_ACTIVE);
				activeCourseDisplayList.setModel(loadCourseList(activeCourses));
				closedCourseDisplayList.setModel(loadCourseList(closedCourses));
			}
		});
		addCourseButton.setBounds(480, 13, 166, 71);
		contentPane.add(addCourseButton);
		
		//button that closes the course
		JButton closeCourseButton = new JButton("Close Course");
		closeCourseButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		closeCourseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int closeIndex = activeCourseDisplayList.getSelectedIndex();
				try {
					//to close a course, it first copies the course to the closedCourses arraylist
					//then deletes the course from the activeCOurses arraylist
				System.out.println(closeIndex);
				activeCourses.get(closeIndex).setIsOpen(false);
				closedCourses.add(activeCourses.get(closeIndex));
				activeCourses.remove(closeIndex);
				}
				catch (Exception eClose) {
					//exception for if an invalid course is selected
					JOptionPane.showMessageDialog(null, "You have not selected a course!");
				}
				activeCourseDisplayList.setModel(loadCourseList(activeCourses));
				closedCourseDisplayList.setModel(loadCourseList(closedCourses));
				
			}
		});
		closeCourseButton.setBounds(480, 97, 166, 71);
		contentPane.add(closeCourseButton);

		//saves course list to serialized panes
		JButton saveCourseButton = new JButton("Save");
		saveCourseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveFile(activeCourses, SERIALIZED_FILE_NAME_ACTIVE);
				saveFile(closedCourses, SERIALIZED_FILE_NAME_CLOSED);
				JOptionPane.showMessageDialog(null, "Successfully Saved!");
			}
		});

		saveCourseButton.setBounds(480, 380, 166, 32);
		contentPane.add(saveCourseButton);
    
		//loads courses upon login with button, for display in dynamic list
		JButton refreshCourseButton = new JButton("Refresh");
		refreshCourseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				activeCourseDisplayList.setModel(loadCourseList(activeCourses));
				activeCourseDisplayList.setModel(loadCourseList(closedCourses));
			}
		});
		refreshCourseButton.setBounds(480, 425, 166, 32);
		contentPane.add(refreshCourseButton);
		
		//deleting a course, can only be done to active courses
		JButton deleteCourseButton = new JButton("Delete Course");
		deleteCourseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int deleteIndex = activeCourseDisplayList.getSelectedIndex();
				try {
					activeCourses.remove(deleteIndex);
				}
				catch (Exception eDelete) {
					JOptionPane.showMessageDialog(null, "No course is selected!");
				}
				saveFile(activeCourses, SERIALIZED_FILE_NAME_ACTIVE);
				activeCourseDisplayList.setModel(loadCourseList(activeCourses));
				closedCourseDisplayList.setModel(loadCourseList(closedCourses));
			}
		});
		deleteCourseButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		deleteCourseButton.setBounds(480, 186, 166, 71);
		contentPane.add(deleteCourseButton);
		

	}
}
