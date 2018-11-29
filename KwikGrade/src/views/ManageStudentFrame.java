package views;

import models.*;
import views.components.GradingSchemeGrid;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageStudentFrame extends JDialog {
	private static int GRADING_SCHEME_ROW_COUNT = 5;
	private static int GRADING_SCHEME_WIDTH = 900;
	private static int GRADING_SCHEME_HEIGHT = 300;
	private static int GRADING_SCHEME_COL_OFFSET = 2;

	private static Color LIGHT_GRAY_COLOR = new Color(0xF0F0F0);
	private static Color DARK_GRAY_COLOR = new Color(0xE0E0E0);
	private static Color LIGHT_GREEN_COLOR = new Color(0x97FFBF);

	private final JPanel contentPanel = new JPanel();
	private JTextField fNameField;
	private JTextField lNameField;
	private JTextField buIdField;
	private JTextField emailField;
	private JTextField statusField;
	private JTextField mInitialField;
	private JLabel lNameLabel;
	private JLabel buIdLabel;
	private JLabel emailLabel;
	private JLabel statusLabel;
	private JLabel mInitialLabel;
	private JButton saveButton;
	private JButton backButton;

	private Student newStudent;
	private GradingSchemeGrid gradingSchemeGrid;

	private boolean didSave;

	/**
	 * Create the dialog.
	 */
	public ManageStudentFrame(Student managedStudent, Course managedCourse) {
		OverallGrade overallGradeScheme;

		//TODO: add if-else logic on which overall grade scheme to use. By default we will use the Undergraduate schema
		overallGradeScheme = managedCourse.getCourseUnderGradDefaultGradeScheme();

		setBounds(100, 100, 1000, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
//		GridBagConstraints frameConstraints = new GridBagConstraints();
		
		fNameField = new JTextField(managedStudent.getfName());
		fNameField.setBounds(53, 60, 130, 26);
		contentPanel.add(fNameField);
		fNameField.setColumns(10);

		lNameField = new JTextField(managedStudent.getlName());
		lNameField.setBounds(382, 60, 130, 26);
		lNameField.setColumns(10);
		contentPanel.add(lNameField);

		buIdField = new JTextField(managedStudent.getBuId());
		buIdField.setBounds(53, 124, 130, 26);
		buIdField.setColumns(10);
		contentPanel.add(buIdField);

		emailField = new JTextField(managedStudent.getEmail());
		emailField.setBounds(217, 124, 130, 26);
		emailField.setColumns(10);
		contentPanel.add(emailField);

		// TODO: update this with a dropdown menu instead.
		statusField = new JTextField();
		statusField.setBounds(382, 124, 130, 26);
		statusField.setColumns(10);
		contentPanel.add(statusField);

		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(53, 45, 98, 16);
		contentPanel.add(lblFirstName);

		lNameLabel = new JLabel("Last Name");
		lNameLabel.setBounds(382, 45, 81, 16);
		contentPanel.add(lNameLabel);

		buIdLabel = new JLabel("BU ID");
		buIdLabel.setBounds(53, 109, 61, 16);
		contentPanel.add(buIdLabel);

		emailLabel = new JLabel("Email");
		emailLabel.setBounds(217, 109, 61, 16);
		contentPanel.add(emailLabel);

		statusLabel = new JLabel("Status");
		statusLabel.setBounds(382, 109, 61, 16);
		contentPanel.add(statusLabel);

		mInitialField = new JTextField();
		mInitialField.setBounds(217, 60, 130, 26);
		mInitialField.setColumns(10);
		contentPanel.add(mInitialField);

		mInitialLabel = new JLabel("Middle Initial");
		mInitialLabel.setBounds(217, 45, 98, 16);
		contentPanel.add(mInitialLabel);

		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Student newStudent;
				if(mInitialField.getText() != null)
					newStudent = new Student(fNameField.getText(), mInitialField.getText(), lNameField.getText(), buIdField.getText(), emailField.getText());
				else
					newStudent = new Student(fNameField.getText(),"", lNameField.getText(), buIdField.getText(), emailField.getText());

				managedCourse.addActiveStudents(newStudent);
				didSave = true;
			}
		});
		saveButton.setBounds(597, 85, 117, 29);
		contentPanel.add(saveButton);

		backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 //new MainDashboard().setVisible(true); //This actually has to go back to Course Management page
				dispose();
			}
		});
		backButton.setBounds(597, 124, 117, 29);
		contentPanel.add(backButton);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO: add save functionality, currently just filler
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

		// ======================================
		// Build Grading Scheme Grid
		// ======================================
		gradingSchemeGrid = new GradingSchemeGrid(overallGradeScheme);
		gradingSchemeGrid.configureGradingSchemeGrid(GradingSchemeGrid.GradingSchemeType.MANAGE_STUDENT);
		JScrollPane gradingSchemeScrollPane = gradingSchemeGrid.buildGradingSchemeGrid();
		contentPanel.add(gradingSchemeScrollPane);
	}

	public boolean didSave() {
		return this.didSave;
	}
}
