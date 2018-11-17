package views;

import models.Student;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CreateFromNewFrame extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField courseNumField;
	private JTextField courseTermField;
	private JTextField courseTitleField;
	private JTextField studentFilepathField;
	private String courseNum;
	private String courseTerm;
	private String courseTitle;
	private String filePath;

	private boolean hasCreatedNewCourse = false;

	private ArrayList<Student> importedStudentList = new ArrayList<>();
	private JPanel panel1;

	/**
	 * Create the dialog.
	 */
	public CreateFromNewFrame() {
		setBounds(100, 100, 583, 509);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		courseNumField = new JTextField();
		courseNumField.setBounds(250, 29, 300, 36);
		contentPanel.add(courseNumField);
		courseNumField.setColumns(10);

		courseTermField = new JTextField();
		courseTermField.setColumns(10);
		courseTermField.setBounds(250, 78, 300, 36);
		contentPanel.add(courseTermField);

		courseTitleField = new JTextField();
		courseTitleField.setColumns(10);
		courseTitleField.setBounds(250, 127, 300, 36);
		contentPanel.add(courseTitleField);

		JLabel courseNumberLabel = new JLabel("Course Number (required)");
		courseNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		courseNumberLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		courseNumberLabel.setBounds(12, 33, 200, 26);
		contentPanel.add(courseNumberLabel);

		JLabel courseTermLabel = new JLabel("Course Term (required)");
		courseTermLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		courseTermLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		courseTermLabel.setBounds(12, 82, 200, 26);
		contentPanel.add(courseTermLabel);

		JLabel courseTitleLabel = new JLabel("Course Title (required)");
		courseTitleLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		courseTitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		courseTitleLabel.setBounds(12, 131, 200, 26);
		contentPanel.add(courseTitleLabel);

		// Hacky fix to get JLabel to go across multiple lines using HTML.
		JLabel importNowLabel = new JLabel("<html>Add students by importing now.<br/>(Or add them manually later)</html>");
		importNowLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		importNowLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		importNowLabel.setBounds(0, 200, 500, 36);
		contentPanel.add(importNowLabel);

		contentPanel.add(new JSeparator());
		JButton browseButton = new JButton("Browse File Path of Student Text File...");
		browseButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		browseButton.setBounds(152, 250, 405, 36);
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
		studentFilepathField.setBounds(152, 280, 405, 36);
		studentFilepathField.setEditable(false);
		contentPanel.add(studentFilepathField);

		JLabel filePathLabel = new JLabel("Filepath");
		filePathLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		filePathLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		filePathLabel.setBounds(12, 280, 89, 36);
		contentPanel.add(filePathLabel);

		// Add OK and Cancel buttons
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				courseNum = courseNumField.getText();
				courseTerm = courseTermField.getText();
				courseTitle = courseTitleField.getText();
				filePath = studentFilepathField.getText();

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
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonPane.add(cancelButton);
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