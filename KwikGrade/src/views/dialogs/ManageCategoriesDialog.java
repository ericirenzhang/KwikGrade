package views.dialogs;

import helpers.FileManager;
import helpers.KwikGradeUIManager;
import models.*;
import views.components.GradingSchemeGrid;
import views.frames.MainDashboardFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class ManageCategoriesDialog extends JDialog {
	private final JPanel contentPanel = new JPanel();
	private JScrollPane gradingSchemeScrollPane;

	private GradingSchemeGrid gradingSchemeGrid;
	private OverallGrade overallGradeScheme;

	/**
	 * Create the dialog to manage categories.
	 *
	 * @param managedCourse
	 */
	public ManageCategoriesDialog(Course managedCourse) {
		// The dropdown menu defaults to "Undergrad"
		overallGradeScheme = managedCourse.getCourseUnderGradDefaultGradeScheme();

		KwikGradeUIManager.setUpUI(this, contentPanel, 1000, 600);

		// Labels
		JLabel titleLabel = new JLabel("Manage Categories");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		titleLabel.setBounds(42, 31, 212, 33);
		contentPanel.add(titleLabel);

		JLabel selectStudentLabel = new JLabel("Select the student scheme that you'd like to modify:");
		selectStudentLabel.setHorizontalAlignment(SwingConstants.CENTER);
		selectStudentLabel.setBounds(42, 76, 348, 16);
		contentPanel.add(selectStudentLabel);

		// Buttons
		JButton addCategoryButton = new JButton("Add a Category");
		addCategoryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddCategoryDialog addCategory = new AddCategoryDialog(getOverallGradeScheme());
				addCategory.setModal(true);
				addCategory.setVisible(true);

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
		addCategoryButton.setBounds(801, 41, 179, 29);
		contentPanel.add(addCategoryButton);

		JButton btnDeleteCategory = new JButton("Delete a Category");
		btnDeleteCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteCategoryDialog deleteCategory = new DeleteCategoryDialog(getOverallGradeScheme());
				deleteCategory.setModal(true);
				deleteCategory.setVisible(true);

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
		btnDeleteCategory.setBounds(801, 71, 179, 29);
		contentPanel.add(btnDeleteCategory);

		// Dropdown menu for type of Student schema to modify
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

		studentStatusDropdown.setBounds(52, 103, 148, 26);
		contentPanel.add(studentStatusDropdown);

		// Save and Cancel buttons
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton saveButton = new JButton("Save and Add");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String gradUndergradStatus = (String) studentStatusDropdown.getSelectedItem();

				if (studentStatusDropdown.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(null, "Please make sure all required fields are filled!");
					return;
				}

				// Go through all Students in Course to set their weightings based on TextField inputs.
				updateStudentAndDefaultOverallGrades(gradUndergradStatus, managedCourse);

				// Save the changes.
				FileManager.saveFile(MainDashboardFrame.getKwikGrade().getActiveCourses(), MainDashboardFrame.getActiveSaveFileName());
				FileManager.saveFile(MainDashboardFrame.getKwikGrade().getClosedCourses(), MainDashboardFrame.getClosedSaveFileName());
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
	 * @param managedCourse
	 */
	private void updateStudentAndDefaultOverallGrades(String gradUndergradStatus, Course managedCourse) {
		// For all students in the class, update their OverallGrade schema with the weighting from this frame
		ArrayList<Student> students = managedCourse.getActiveStudents();
		OverallGrade studentOverallGrade = new OverallGrade();

		OverallGrade overallGradeFromFields;

		for(int studentIndex = 0; studentIndex < students.size(); studentIndex++) {
			Student currStudent = students.get(studentIndex);

			// Only update the weighting of relevant status students (i.e. if Undergrad selected, only update weightings for Undergrad students).
			if(currStudent.getStatus().equals(gradUndergradStatus)) {
				studentOverallGrade = currStudent.getOverallGradeObject();

				ArrayList<CourseCategory> studentCourseCategoryList = studentOverallGrade.getCourseCategoryList();
				overallGradeFromFields = gradingSchemeGrid.getOverallGradeFromFields();
				ArrayList<CourseCategory> courseCategoriesFromField = overallGradeFromFields.getCourseCategoryList();

				// If the number of CourseCategory fields are less than a Student's, then that means Categories were deleted.
				if(courseCategoriesFromField.size() < studentCourseCategoryList.size()) {
					for (int courseIndex = 0; courseIndex < studentCourseCategoryList.size(); courseIndex++) {
						CourseCategory currCategory = studentCourseCategoryList.get(courseIndex);
						if(!isCategoryInCategoryList(currCategory, courseCategoriesFromField)) {
							studentOverallGrade.deleteCategory(currCategory.getName());
						}
					}
				}

				// By this point, if a Category was deleted, the Student should have matching indices with overallGradeFromFields.
				// Iterate through all categories and update weights in the Student.
				for(int courseIndex = 0; courseIndex < overallGradeFromFields.getCourseCategoryList().size(); courseIndex++) {
					// Necessary to refetch the overallGradeFromFields so that not each Student is assigned a unique copy of OverallGrade object and not all pointing to the same OverallGrade object.
					overallGradeFromFields = gradingSchemeGrid.getOverallGradeFromFields();

					// If the number of CourseCategory fields are greater than a Student's, then that means Categories were added.
					if(courseIndex > studentCourseCategoryList.size() - 1) {
						CourseCategory currCategory = overallGradeFromFields.getCourseCategoryList().get(courseIndex);
						studentOverallGrade.addCourseCategory(currCategory);
					} else {
						// Otherwise Update Course Categories
						CourseCategory currCategory = studentCourseCategoryList.get(courseIndex);
						double newCategoryWeight = overallGradeFromFields.getCourseCategoryList().get(courseIndex).getWeight();
						currCategory.setWeight(newCategoryWeight);

						// Iterate through all SubCategories and update weights.
						ArrayList<SubCategory> studentSubCategoryList = currCategory.getSubCategoryList();
						for(int subCategoryIndex = 0; subCategoryIndex < studentSubCategoryList.size(); subCategoryIndex++) {
							overallGradeFromFields = gradingSchemeGrid.getOverallGradeFromFields();

							SubCategory currSubCategory = studentSubCategoryList.get(subCategoryIndex);

							SubCategory subCategoryFromFields = overallGradeFromFields.getCourseCategoryList().get(courseIndex).getSubCategoryList().get(subCategoryIndex);
							currSubCategory.setWeight(subCategoryFromFields.getWeight());
						}
						currCategory.setSubCategoryList(studentSubCategoryList);
					}
				}
				studentOverallGrade.setCategoryList(studentCourseCategoryList);
				currStudent.setOverallGrade(studentOverallGrade);

				// Because Student list of ActiveStudents is casted, we need to re-assign the status.
				currStudent.setStatus(gradUndergradStatus);
			}
		}
		// Update the Students weightings.
		managedCourse.setActiveStudents(students);

		// Create a clone of the category with 0 points for all SubCategory items to assign back to the default schema
		double overallGradeNew = studentOverallGrade.getOverallGradeValue();
		ArrayList<CourseCategory> newCategoryList = new ArrayList<CourseCategory>();
		for(int i = 0; i < studentOverallGrade.getCourseCategoryList().size(); i++) {
			String newCategoryName = studentOverallGrade.getCourseCategoryList().get(i).getName();
			double newCategoryWeight = studentOverallGrade.getCourseCategoryList().get(i).getWeight();
			CourseCategory clonedCategory = new CourseCategory(newCategoryName, newCategoryWeight, new ArrayList<SubCategory>());

			// Clone the subcategories
			for(int j = 0; j < studentOverallGrade.getCourseCategoryList().get(i).getSubCategoryList().size(); j++) {
				SubCategory originalSubCategory = studentOverallGrade.getCourseCategoryList().get(i).getSubCategoryList().get(j);
				String newSubCategoryName = originalSubCategory.getName();
				double newSubCategoryWeight = originalSubCategory.getWeight();
				double newSubCategoryTotalPoints = originalSubCategory.getTotalPoints();
				SubCategory clonedSubCategory = new SubCategory(newSubCategoryName, newSubCategoryWeight, 0, newSubCategoryTotalPoints);
				clonedCategory.addSubCategory(clonedSubCategory);
			}
			newCategoryList.add(clonedCategory);
		}
		OverallGrade clonedOverallGrade = new OverallGrade(overallGradeNew, newCategoryList);

		if (gradUndergradStatus.equals("Undergraduate")) {
			managedCourse.setCourseUnderGradDefaultGradeScheme(clonedOverallGrade);
		}
		else {
			managedCourse.setCourseGradDefaultGradeScheme(clonedOverallGrade);
		}
	}

	/**
	 * Given a courseCategory, check if it's in courseCategories by checking on the name.
	 * @param courseCategory
	 * @param courseCategories
	 * @return
	 */
	public boolean isCategoryInCategoryList(CourseCategory courseCategory, ArrayList<CourseCategory> courseCategories) {
		for(int i = 0; i < courseCategories.size(); i++) {
			if(courseCategory.getName().equals(courseCategories.get(i).getName())) {
				return true;
			}
		}
		return false;
	}

	public OverallGrade getOverallGradeScheme() {
		return this.overallGradeScheme;
	}
}
