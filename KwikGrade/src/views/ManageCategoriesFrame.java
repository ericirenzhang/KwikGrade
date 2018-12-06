package views;

import helpers.FileManager;
import models.*;
import views.components.GradingSchemeGrid;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

				OverallGrade overallGradeFromFields;
				overallGradeFromFields = gradingSchemeGrid.getOverallGradeFromFields();

				// Go through all Students in Course to set their weightings based on TextField inputs.
				updateStudentAndDefaultOverallGrades(gradUndergradStatus, overallGradeFromFields, managedCourse);

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

	/**
	 * Iterates through all Students in a Course and updates:
	 * 	- Each student's OverallGrade object with the new weightings from the TextFields.
	 * 	- Default ugrad and grad OverallGrade schemes based on what the user wanted to update.
	 * @param gradUndergradStatus
	 * @param overallGradeFromFields
	 * @param managedCourse
	 */
	private void updateStudentAndDefaultOverallGrades(String gradUndergradStatus, OverallGrade overallGradeFromFields, Course managedCourse) {
		// For all students in the class, update their OverallGrade schema with the weighting from this frame
		ArrayList<Student> students = managedCourse.getActiveStudents();
		OverallGrade studentOverallGrade = new OverallGrade();
		for(int studentIndex = 0; studentIndex < students.size(); studentIndex++) {
			Student currStudent = students.get(studentIndex);

			// Only update the weighting of relevant status students (i.e. if Undergrad selected, only update weightings for Undergrad students).
			if(currStudent.getStatus().equals(gradUndergradStatus)) {
				studentOverallGrade = currStudent.getOverallGradeObject();

				// Iterate through all categories and update weights.
				ArrayList<CourseCategory> studentCourseCategoryList = studentOverallGrade.getCourseCategoryList();
				for(int courseIndex = 0; courseIndex < studentCourseCategoryList.size(); courseIndex++) {
					CourseCategory currCategory = studentCourseCategoryList.get(courseIndex);

					// TODO: index will be the same for now but after "Add Category" button, this may need to be changed
					double newCategoryWeight = overallGradeFromFields.getCourseCategoryList().get(courseIndex).getWeight();
					currCategory.setWeight(newCategoryWeight);

					// Iterate through all SubCategories and update weights.
					ArrayList<SubCategory> studentSubCategoryList = currCategory.getSubCategoryList();
					for(int subCategoryIndex = 0; subCategoryIndex < studentSubCategoryList.size(); subCategoryIndex++) {
						SubCategory currSubCategory = studentSubCategoryList.get(subCategoryIndex);

						// TODO: index will be the same for now but after "Add Category" button, this may need to be changed? Maybe not cause it's SubCategory
						SubCategory subCategoryFromFields = overallGradeFromFields.getCourseCategoryList().get(courseIndex).getSubCategoryList().get(subCategoryIndex);
						currSubCategory.setWeight(subCategoryFromFields.getWeight());
					}
					currCategory.setSubCategoryList(studentSubCategoryList);
				}
				studentOverallGrade.setCategoryList(studentCourseCategoryList);
				currStudent.setOverallGrade(studentOverallGrade);
				// Because Student list of ActiveStudents is casted, we need to re-assign the status.
				currStudent.setStatus(gradUndergradStatus);
			}
		}
		// Update the Students weightings.
		managedCourse.setActiveStudents(students);

		// Update the default schema as well
		if (gradUndergradStatus.equals("Undergraduate")) {
			managedCourse.setCourseUnderGradDefaultGradeScheme(studentOverallGrade);
		}
		else {
			managedCourse.setCourseGradDefaultGradeScheme(studentOverallGrade);
		}
	}

}
