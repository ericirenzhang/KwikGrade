package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import views.MainDashboard;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import models.Course;
import models.KwikGrade;
import models.OverallGrade;
import models.Student;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

public class CreateFromExistingFrame extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField courseNumField;
	private JTextField courseTermField;
	private JTextField courseTitleField;
	private JTextField studentFilepathField;
	private String courseNum;
	private String courseTerm;
	private String courseTitle;
	private String filePath;
	private OverallGrade clonedGradingScheme;
	private DefaultListModel DLM;
	private JList cloneCourseList;
	
	private boolean hasCreatedNewCourse = false;

	private ArrayList<Student> importedStudentList = new ArrayList<>();
	
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
	
	public OverallGrade getGradeScheme(ArrayList<Course> courseList, int selectedCourse) {
		OverallGrade gradeScheme = courseList.get(selectedCourse).getCourseDefaultGradeScheme();
		return gradeScheme;
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CreateFromExistingFrame dialog = new CreateFromExistingFrame();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public CreateFromExistingFrame() {
		setBounds(100, 100, 595, 748);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		courseNumField = new JTextField();
		courseNumField.setBounds(222, 29, 343, 36);
		contentPanel.add(courseNumField);
		courseNumField.setColumns(10);
		
		courseTermField = new JTextField();
		courseTermField.setColumns(10);
		courseTermField.setBounds(222, 78, 343, 36);
		contentPanel.add(courseTermField);
		
		courseTitleField = new JTextField();
		courseTitleField.setColumns(10);
		courseTitleField.setBounds(222, 127, 343, 36);
		contentPanel.add(courseTitleField);
		
		JLabel courseNumberLabel = new JLabel("Course Number (required)");
		courseNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		courseNumberLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		courseNumberLabel.setBounds(12, 33, 187, 26);
		contentPanel.add(courseNumberLabel);
		
		JLabel courseTermLabel = new JLabel("Course Term (required)");
		courseTermLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		courseTermLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		courseTermLabel.setBounds(12, 82, 187, 26);
		contentPanel.add(courseTermLabel);
		
		JLabel courseTitleLabel = new JLabel("Course Title (required)");
		courseTitleLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		courseTitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		courseTitleLabel.setBounds(12, 131, 187, 26);
		contentPanel.add(courseTitleLabel);
		
		// Hacky fix to get JLabel to go across multiple lines using HTML.
		JLabel importNowLabel = new JLabel("<html>Add students by importing now.<br/>(Or add them manually later)</html>");
		importNowLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		importNowLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		importNowLabel.setBounds(167, 533, 243, 36);
		contentPanel.add(importNowLabel);
		
		// Bottom half of JFrame for File browsing/importing Students.
		contentPanel.add(new JSeparator());
		JButton browseButton = new JButton("Browse File Path of Student Text File...");
		browseButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		browseButton.setBounds(79, 573, 405, 36);
		browseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();

				// For File
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileChooser.setAcceptAllFileFilterUsed(false);

				int userChoice = fileChooser.showOpenDialog(null);
				if (userChoice == JFileChooser.APPROVE_OPTION) {
					String studentTextFilePath = fileChooser.getSelectedFile().toString();
					studentFilepathField.setText(studentTextFilePath);
				}
			}
		});
		contentPanel.add(browseButton);
		
		studentFilepathField = new JTextField();
		studentFilepathField.setColumns(10);
		studentFilepathField.setBounds(79, 617, 405, 36);
		studentFilepathField.setEditable(false);
		contentPanel.add(studentFilepathField);
		
		
		JLabel courseCloneSelectLabel = new JLabel("Select a Course to Clone");
		courseCloneSelectLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		courseCloneSelectLabel.setBounds(193, 192, 217, 25);
		contentPanel.add(courseCloneSelectLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(34, 216, 499, 304);
		contentPanel.add(scrollPane);
		
		JList cloneCourseList = new JList();
		scrollPane.setViewportView(cloneCourseList);
		cloneCourseList.setModel(loadCourseList(MainDashboard.getKwikGrade().getActiveCourses()));
		System.out.println("HELLO");
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				//TODO: Need to implement feature to actually clone the courses
				//Need to implement methods to get the grading scheme
				JButton okButton = new JButton("OK"); 
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						courseNum = courseNumField.getText();
						courseTerm = courseTermField.getText();
						courseTitle = courseTitleField.getText();
						filePath = studentFilepathField.getText();
						
						int cloneIndex = cloneCourseList.getSelectedIndex();
						if(cloneIndex == -1) {
							JOptionPane.showMessageDialog(null, "You have not selected a course!");
							return;
						}
						
						clonedGradingScheme = getGradeScheme(MainDashboard.getKwikGrade().getActiveCourses(), cloneIndex);

						if(courseNum.equals("") || courseTerm.equals("") || courseTitle.equals("")) {
							JOptionPane.showMessageDialog(null, "Must enter in required information!");
							return;
						}

						if(!filePath.equals("")) {
							addImportedStudents(filePath);
						}

						hasCreatedNewCourse = true;

						dispose();
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}

	public void addImportedStudents(String filePath) {
		Scanner rawStudentData;

		try {
			System.out.println("Loading Students");
			rawStudentData = new Scanner(new File(filePath));

			while(rawStudentData.hasNext()) {
				String line = rawStudentData.nextLine();
				List<String> splitLine = Arrays.asList(line.split(","));
				if(splitLine.size()==6) { //checks for middle initial, if there's middle initial, there will be 6 items in string
					String fName = splitLine.get(0);
					String middleInitial = splitLine.get(1);
					String lName = splitLine.get(2);
					String buId = splitLine.get(3);
					String email = splitLine.get(4);
					String standing = splitLine.get(5);
					this.importedStudentList.add(new Student(fName, middleInitial, lName, buId, email));
				}
				else { //if no middle initial, then 5 items in string
					String fName = splitLine.get(0);
					String middleInitial = "";
					String lName = splitLine.get(1);
					String buId = splitLine.get(2);
					String email = splitLine.get(3);
					String standing = splitLine.get(4);
					this.importedStudentList.add(new Student(fName, middleInitial, lName, buId, email));
				}
			}

			rawStudentData.close();
			System.out.println("Student Import Complete!");
		}
		//will change this to prompt user for another file
		catch(Exception e) {
			System.out.println("COULD NOT FIND FILE!!!!");
		}
	}


	//=============================
	// Getters
	//=============================
	
	public String getCourseNum() {
		return courseNum;
	}
	public String getCourseTerm() {
		return courseTerm;
	}
	public String getCourseTitle() {
		return courseTitle;
	}
	public ArrayList<Student> getImportedStudentsList() {
		return this.importedStudentList;
	}
	public String getFilePath() {
		return filePath;
	}
	public boolean getHasCreatedNewCourse() {
		return this.hasCreatedNewCourse;
	}
	
	//=============================
	// Setters
	//=============================
	
	public void setCourseNum(String courseNum) {
		this.courseNum = courseNum;
	}
	public void setCourseTerm(String courseTerm) {
		this.courseTerm = courseTerm;
	}
	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
