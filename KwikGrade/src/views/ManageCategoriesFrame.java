package views;

import helpers.FileManager;
import models.*;
import views.components.GradingSchemeGrid;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ManageCategoriesFrame extends JDialog {
	private final JPanel contentPanel = new JPanel();
	private JScrollPane gradingSchemeScrollPane;

	private GradingSchemeGrid gradingSchemeGrid;
	private OverallGrade overallGradeScheme;

	/**
	 * Create the dialog.
	 */
	public ManageCategoriesFrame(Course managedCourse) {
		// TODO: add support later for undergraduate vs. graduate scheme on Manage Categories page. Default to Undergrad for now.
		overallGradeScheme = managedCourse.getCourseUnderGradDefaultGradeScheme();

		setBounds(100, 100, 1000, 600);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		GridBagConstraints frameConstraints = new GridBagConstraints();

		JComboBox studentStatusDropdown = new JComboBox();
		studentStatusDropdown.addItem("Undergraduate");
		studentStatusDropdown.addItem("Graduate");

		studentStatusDropdown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (studentStatusDropdown.getSelectedItem().equals("Undergraduate")) {
					overallGradeScheme = managedCourse.getCourseUnderGradDefaultGradeScheme();
				}
				else {
					overallGradeScheme = managedCourse.getCourseGradDefaultGradeScheme();
				}
				// Rerenders a the grading scheme by removing/adding to the content panel.
				gradingSchemeGrid = new GradingSchemeGrid(overallGradeScheme);
				gradingSchemeGrid.configureGradingSchemeGrid(GradingSchemeGrid.GradingSchemeType.MANAGE_CATEGORIES);
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
				String gradUndergradStatus = (String) studentStatusDropdown.getSelectedItem();

				if (studentStatusDropdown.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(null, "Please make sure all required fields are filled!");
					return;
				}

				// TODO: Will need to iterate through all students and give them the new updated OverallGrade schema
//				if (gradUndergradStatus.equals("Undergraduate")) {
//					studentOverallGrade = gradingSchemeGrid.getOverallGradeFromFields();
//					newStudent = new UndergraduateStudent(fName, middleInitial, lName, buId, email, gradUndergradStatus, studentOverallGrade);
//				}
//				else {
//					studentOverallGrade = gradingSchemeGrid.getOverallGradeFromFields();
//					newStudent = new GraduateStudent(fName, middleInitial, lName, buId, email, gradUndergradStatus, studentOverallGrade);
//				}
//				// Add the student to the course.
//				managedCourse.addActiveStudents(newStudent);

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
		gradingSchemeGrid = new GradingSchemeGrid(overallGradeScheme);
		gradingSchemeGrid.configureGradingSchemeGrid(GradingSchemeGrid.GradingSchemeType.MANAGE_CATEGORIES);
		gradingSchemeScrollPane = gradingSchemeGrid.buildGradingSchemeGrid();
		contentPanel.add(gradingSchemeScrollPane);
	}

}
