package views;

import helpers.FileManager;
import models.*;
import views.components.GradingSchemeGrid;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageStudentFrame extends JDialog {
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

	private GradingSchemeGrid gradingSchemeGrid;
	private OverallGrade managedOverallGradeScheme;
	private JScrollPane gradingSchemeScrollPane;

	/**
	 * Create the dialog.
	 */
	public ManageStudentFrame(Student managedStudent, Course managedCourse) {
		//TODO: add if-else logic on which overall grade scheme to use. By default we will use the Undergraduate schema
		managedOverallGradeScheme = managedStudent.getOverallGradeObject();

		setBounds(100, 100, 1000, 600);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		GridBagConstraints frameConstraints = new GridBagConstraints();

		// ===========================
		// Student information/Fields
		// ===========================
		fNameField = new JTextField(managedStudent.getfName());
		fNameField.setBounds(53, 60, 130, 26);
		contentPanel.add(fNameField);
		fNameField.setColumns(10);

		mInitialField = new JTextField(managedStudent.getMiddleInitial());
		mInitialField.setBounds(217, 60, 130, 26);
		mInitialField.setColumns(10);
		contentPanel.add(mInitialField);

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


		mInitialLabel = new JLabel("Middle Initial (Optional)");
		mInitialLabel.setBounds(217, 45, 130, 16);
		contentPanel.add(mInitialLabel);

		JComboBox studentStatusDropdown = new JComboBox();
		studentStatusDropdown.addItem("Undergraduate");
		studentStatusDropdown.addItem("Graduate");
		// Dropdown defaults to Undergraduate. If student is graduate, set the dropdown to Graduate instead.
		if(managedStudent.getStatus().equals("Graduate")) {
			studentStatusDropdown.setSelectedItem("Graduate");
		}

		studentStatusDropdown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (studentStatusDropdown.getSelectedItem().equals("Undergraduate")) {
					managedOverallGradeScheme = managedCourse.getCourseUnderGradDefaultGradeScheme();
				}
				else {
					managedOverallGradeScheme = managedCourse.getCourseGradDefaultGradeScheme();
				}
				// Rerenders a the grading scheme by removing/adding to the content panel.
				gradingSchemeGrid = new GradingSchemeGrid(managedOverallGradeScheme);
				gradingSchemeGrid.configureGradingSchemeGrid(GradingSchemeGrid.GradingSchemeType.MANAGE_STUDENT);
				contentPanel.remove(gradingSchemeScrollPane);
				gradingSchemeScrollPane = gradingSchemeGrid.buildGradingSchemeGrid();
				contentPanel.add(gradingSchemeScrollPane);
				contentPanel.revalidate();
				contentPanel.repaint();
			}
		});

		studentStatusDropdown.setBounds(382, 127, 130, 26);
		contentPanel.add(studentStatusDropdown);

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
				String gradUndergradStatus = (String) studentStatusDropdown.getSelectedItem();

				if (fName.equals("")||lName.equals("")||buId.equals("")||email.equals("")||studentStatusDropdown.getSelectedIndex()==-1) {
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

				// Update this student object. Have to use setters because this is in an inner function call
				OverallGrade gradeFromFields = gradingSchemeGrid.getOverallGradeFromFields();
				managedStudent.setfName(fName);
				managedStudent.setMiddleInitial(middleInitial);
				managedStudent.setlName(lName);
				managedStudent.setBuId(buId);
				managedStudent.setEmail(email);
				managedStudent.setStatus(gradUndergradStatus);
				managedStudent.setOverallGrade(gradeFromFields);

				// Save the changes.
				FileManager.saveFile(MainDashboard.getKwikGrade().getActiveCourses(), MainDashboard.getActiveSaveFileName());
				FileManager.saveFile(MainDashboard.getKwikGrade().getClosedCourses(), MainDashboard.getClosedSaveFileName());

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
		// Build Grading Scheme Grid Layout
		// ======================================
		gradingSchemeGrid = new GradingSchemeGrid(managedOverallGradeScheme);
		gradingSchemeGrid.configureGradingSchemeGrid(GradingSchemeGrid.GradingSchemeType.MANAGE_STUDENT);
		gradingSchemeScrollPane = gradingSchemeGrid.buildGradingSchemeGrid();
		contentPanel.add(gradingSchemeScrollPane);
	}
}
