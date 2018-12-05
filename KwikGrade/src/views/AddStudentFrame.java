package views;

import views.components.GradingSchemeGrid;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import models.Course;
import models.CourseCategory;
import models.GraduateStudent;
import models.OverallGrade;
import models.Student;
import models.SubCategory;
import models.UndergraduateStudent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddStudentFrame extends JDialog {
	private static int GRADING_SCHEME_ROW_COUNT = 3;
	private static int GRADING_SCHEME_WIDTH = 900;
	private static int GRADING_SCHEME_HEIGHT = 300;
	private static int GRADING_SCHEME_COL_OFFSET = 1;

	private static Color LIGHT_GRAY_COLOR = new Color(0xF0F0F0);
	private static Color DARK_GRAY_COLOR = new Color(0xE0E0E0);

	private final JPanel contentPanel = new JPanel();
	private JTextField fNameField;
	private JTextField lNameField;
	private JTextField buIdField;
	private JTextField emailField;
	private JTextField mInitialField;
	private JLabel lNameLabel;
	private JLabel buIdLabel;
	private JLabel emailLabel;
	private JLabel statusLabel;
	private JLabel mInitialLabel;
	private JButton saveButton;
	private JButton backButton;

	private Student newStudent;
	private OverallGrade studentOverallGrade;
	private OverallGrade overallGradeScheme;
	private GradingSchemeGrid gradingSchemeGrid;

	private boolean didSave;

	/**
	 * Create the dialog.
	 */
	public AddStudentFrame(Course currCourse) {
		overallGradeScheme = currCourse.getCourseUnderGradDefaultGradeScheme();

		setBounds(100, 100, 1000, 600);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		GridBagConstraints frameConstraints = new GridBagConstraints();

		fNameField = new JTextField();
		fNameField.setBounds(53, 60, 130, 26);
		contentPanel.add(fNameField);
		fNameField.setColumns(10);

		lNameField = new JTextField();
		lNameField.setBounds(382, 60, 130, 26);
		lNameField.setColumns(10);
		contentPanel.add(lNameField);

		buIdField = new JTextField();
		buIdField.setBounds(53, 124, 130, 26);
		buIdField.setColumns(10);
		contentPanel.add(buIdField);

		emailField = new JTextField();
		emailField.setBounds(217, 124, 130, 26);
		emailField.setColumns(10);
		contentPanel.add(emailField);

		JLabel lblFirstName = new JLabel("First Name (Required)");
		lblFirstName.setBounds(53, 45, 130, 16);
		contentPanel.add(lblFirstName);

		lNameLabel = new JLabel("Last Name (Required)");
		lNameLabel.setBounds(382, 45, 130, 16);
		contentPanel.add(lNameLabel);

		buIdLabel = new JLabel("BU ID (Required)");
		buIdLabel.setBounds(53, 109, 130, 16);
		contentPanel.add(buIdLabel);

		emailLabel = new JLabel("Email (Required)");
		emailLabel.setBounds(217, 109, 130, 16);
		contentPanel.add(emailLabel);

		statusLabel = new JLabel("Status (Required)");
		statusLabel.setBounds(382, 109, 130, 16);
		contentPanel.add(statusLabel);

		mInitialField = new JTextField();
		mInitialField.setBounds(217, 60, 130, 26);
		mInitialField.setColumns(10);
		contentPanel.add(mInitialField);

		mInitialLabel = new JLabel("Middle Initial (Optional)");
		mInitialLabel.setBounds(217, 45, 130, 16);
		contentPanel.add(mInitialLabel);
		
		JComboBox gradUndergradDropdown = new JComboBox();
		gradUndergradDropdown.addItem("Undergraduate");
		gradUndergradDropdown.addItem("Graduate");
		
		//trying to get the grid to dynamiclly generate the grade scheme...need help on how the grid generator actually works
		//TODO: need Sean's support on this, to get it to work
		
//		gradUndergradDropdown.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				if (gradUndergradDropdown.getSelectedItem().equals("Undergraduate")) {
//					overallGradeScheme = currCourse.getCourseUnderGradDefaultGradeScheme();
//				}
//				else {
//					overallGradeScheme = currCourse.getCourseGradDefaultGradeScheme();
//				}
//			}
//		});
		gradUndergradDropdown.setBounds(382, 127, 130, 26);
		contentPanel.add(gradUndergradDropdown);

		// Add panel to frame
		frameConstraints.gridx = 0;
		frameConstraints.gridy = 1;
		frameConstraints.weighty = 1;

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton saveButton = new JButton("Save and Add");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Create a new student from the TextFields.
				
				//pulls data into variables to make code easier to read/follow, and to allow for a check of required fields
				String fName = fNameField.getText();
				String middleInitial;
				String lName = lNameField.getText();
				String buId = buIdField.getText();
				String email = emailField.getText();
				String gradUndergrad = (String) gradUndergradDropdown.getSelectedItem();
				
				if (fName.equals("")||lName.equals("")||buId.equals("")||email.equals("")||gradUndergradDropdown.getSelectedIndex()==-1) {
					JOptionPane.showMessageDialog(null, "Please make sure all required fields are filled!");
					return;
				}
				
				//sets middle initial, if it is not specified
				if (mInitialField.getText() == null) {
					middleInitial = "";
				}
				else {
					middleInitial = mInitialField.getText();
				}
								
				//creates undergraduate or graduate students, and creates copies of default grading schemes
				if (gradUndergrad.equals("Undergraduate")) {
					studentOverallGrade = gradingSchemeGrid.getOverallGradeFromFields();
					newStudent = new UndergraduateStudent(fName, middleInitial, lName, buId, email, gradUndergrad, studentOverallGrade);
				}
				else {
					studentOverallGrade = gradingSchemeGrid.getOverallGradeFromFields();
					newStudent = new GraduateStudent(fName, middleInitial, lName, buId, email, gradUndergrad, studentOverallGrade);
				}

				// Add the student to the course.
				currCourse.addActiveStudents(newStudent);
				didSave = true;
				dispose();
			}
		});
		buttonPane.add(saveButton);
		getRootPane().setDefaultButton(saveButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
		
		//TODO: Need Sean's help in figuring out how to make this dynamiclly display the overall grade scheme based on the dropdown

		// ======================================
		// Build Grading Scheme Grid Layout
		// ======================================
		gradingSchemeGrid = new GradingSchemeGrid(overallGradeScheme);
		gradingSchemeGrid.configureGradingSchemeGrid(GradingSchemeGrid.GradingSchemeType.ADD_STUDENT);
		JScrollPane gradingSchemeScrollPane = gradingSchemeGrid.buildGradingSchemeGrid();
		contentPanel.add(gradingSchemeScrollPane);

	}

	public boolean didSave() {
		return this.didSave;
	}
}
