package views;

import java.awt.BorderLayout;

import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import helpers.FileManager;
import helpers.ModelGenerators;
import models.Course;
import models.CourseCategory;
import models.OverallGrade;
import models.Student;
import models.SubCategory;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.awt.GridBagConstraints;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class AddGradeFrame extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField assignNameText;
	private JTable studentGradeTable;
	private DefaultTableModel addGradeTableModel;

	private OverallGrade ugOverallGrade;
	private OverallGrade gradOverallGrade;
	private boolean pointsGained;
	
	private static Color LIGHT_GREEN_COLOR = new Color(0x97FFBF);
	private static Color LIGHT_RED_COLOR = new Color(0xFFA3A3);
	
	private String assignName;
	
	//list of all students
	private ArrayList<Student> studentList;
		
	//pointer array, shows how a student in the grad/undergrad student list relates to the entire student list
	private ArrayList<Integer> ugLocationPointer = new ArrayList<Integer>();
	private ArrayList<Integer> gradLocationPointer = new ArrayList<Integer>();
	
	private DefaultComboBoxModel catNameDropdownModel; 
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField assignValueText;
	
	/**
	 * creates a pointer array that tells you, where each undergrad and grad student is located in the course's enrolled students
	 * @param course
	 */
	public void createPointerArray(Course course) {
		for(int i = 0; i < course.getActiveStudents().size(); i++) {
			if(course.getActiveStudents().get(i).getStatus().equals("Graduate")) {
				gradLocationPointer.add(i);
			}
			else if (course.getActiveStudents().get(i).getStatus().equals("Undergraduate")) {
				ugLocationPointer.add(i);
			}
		}
	}
	
	/**
	 * resets the dropdown menu to update the display of categories within a course
	 * @param course
	 * @param undergrad
	 */
	public DefaultComboBoxModel catNameDropdownUpdater(Course course, boolean undergrad) {
		catNameDropdownModel = new DefaultComboBoxModel();
		if(undergrad == true) {
			for(int i = 0; i < course.getCourseUnderGradDefaultGradeScheme().getCourseCategoryList().size(); i++) {
				catNameDropdownModel.addElement(course.getCourseUnderGradDefaultGradeScheme().getCourseCategoryList().get(i).getName());
			}
			return catNameDropdownModel;
		}
		else {
			for(int i = 0; i < course.getCourseGradDefaultGradeScheme().getCourseCategoryList().size(); i++) {
				catNameDropdownModel.addElement(course.getCourseGradDefaultGradeScheme().getCourseCategoryList().get(i).getName());
			}
			return catNameDropdownModel;
		}
	}
	
	/**
	 * determines if both the UG and Grad OverallGrades (Grading Schemes) have common categories
	 * @param course
	 */
	public DefaultComboBoxModel commonCourseCategories(Course course) {
		catNameDropdownModel = new DefaultComboBoxModel();
		ArrayList<String> ugOverallGradeNames = new ArrayList<String>();
		ArrayList<String> gradOverallGradeNames = new ArrayList<String>();
		for(int i = 0; i < ugOverallGrade.getCourseCategoryList().size(); i++) {
			ugOverallGradeNames.add(ugOverallGrade.getCourseCategoryList().get(i).getName());
		}
		for(int i = 0; i < gradOverallGrade.getCourseCategoryList().size(); i++) {
			gradOverallGradeNames.add(gradOverallGrade.getCourseCategoryList().get(i).getName());
		}
		ugOverallGradeNames.retainAll(gradOverallGradeNames);
		for(int i = 0; i<ugOverallGradeNames.size(); i++) {
			catNameDropdownModel.addElement(ugOverallGradeNames.get(i));
		}
		return catNameDropdownModel;
	}
	
	/**
	 * determines whether the records are points gained, or lost
	 * @param pointsGainedOrLost
	 * @param totalAssignPoints
	 * @param pointsGained
	 * 
	 */
	public double togglePoints(double pointsGainedOrLost, double totalAssignPoints, boolean pointsGained) {
		// if isPointsLost is true, it means user uploads points lost
		if(pointsGained == false) {
			double totalPointsEarned = totalAssignPoints - pointsGainedOrLost;
			return totalPointsEarned;
		}
		else {
			//having it return TotalPointsEarned for consistancy
			double totalPointsEarned = pointsGainedOrLost;
			return totalPointsEarned;
		}
	}

	/**
	 * Create the dialog.
	 */
	public AddGradeFrame(Course managedCourse) {
		createPointerArray(managedCourse);
		ugOverallGrade = managedCourse.getCourseUnderGradDefaultGradeScheme();
		gradOverallGrade = managedCourse.getCourseGradDefaultGradeScheme();
		studentList = managedCourse.getActiveStudents();
		
		setBounds(100, 100, 664, 503);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		assignNameText = new JTextField();
		assignNameText.setFont(new Font("Tahoma", Font.PLAIN, 14));
		assignNameText.setColumns(10);
		
		JLabel assignNameLabel = new JLabel("Assignment Name");
		assignNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JComboBox catNameDropdown = new JComboBox();
		
		JLabel lblCategoryName = new JLabel("Category Name");
		lblCategoryName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel gradeSchemeLabel = new JLabel("Select Grade Scheme");
		gradeSchemeLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JComboBox gradeSchemeDropdown = new JComboBox();
		catNameDropdown.setModel(commonCourseCategories(managedCourse));
		gradeSchemeDropdown.addItem("All Students");
		gradeSchemeDropdown.addItem("Undergraduate");
		gradeSchemeDropdown.addItem("Graduate");
		gradeSchemeDropdown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//displays only the course categories that are relevant depending on if grad, UG, or all students are selected
				if (gradeSchemeDropdown.getSelectedItem().equals("Undergraduate")) {
					catNameDropdown.setModel(catNameDropdownUpdater(managedCourse, true));
					studentGradeTable.setModel(ModelGenerators.dispGradUGStudents(studentList, true));
				}
				else if (gradeSchemeDropdown.getSelectedItem().equals("Graduate")){
					catNameDropdown.setModel(catNameDropdownUpdater(managedCourse, false));
					studentGradeTable.setModel(ModelGenerators.dispGradUGStudents(studentList, false));
				}
				else {
					studentGradeTable.setModel(ModelGenerators.displayAllStudents(managedCourse.getActiveStudents()));
					catNameDropdown.setModel(commonCourseCategories(managedCourse));
				}
			}
		});
		
		JRadioButton ptsEarnedRadio = new JRadioButton("Points Gained");
		ptsEarnedRadio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				studentGradeTable.setBackground(LIGHT_GREEN_COLOR);
			}
		});
		buttonGroup.add(ptsEarnedRadio);
		
		JRadioButton ptsLostRadio = new JRadioButton("Points Lost");
		ptsLostRadio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				studentGradeTable.setBackground(LIGHT_RED_COLOR);
			}
		});
		buttonGroup.add(ptsLostRadio);
		
		JLabel ptsGainedLostLabel = new JLabel("Points Tracking Method");
		ptsGainedLostLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel assignValueLabel = new JLabel("Total Value");
		assignValueLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		assignValueText = new JTextField();
		assignValueText.setFont(new Font("Tahoma", Font.PLAIN, 14));
		assignValueText.setColumns(10);
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(32)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(gradeSchemeLabel, GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
									.addComponent(assignValueLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
									.addComponent(assignNameLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
									.addComponent(lblCategoryName, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
									.addComponent(ptsLostRadio)
									.addComponent(ptsEarnedRadio)
									.addComponent(ptsGainedLostLabel, GroupLayout.PREFERRED_SIZE, 197, GroupLayout.PREFERRED_SIZE)
									.addComponent(catNameDropdown, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
									.addComponent(assignNameText, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
									.addComponent(assignValueText, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(gradeSchemeDropdown, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
									.addGap(60)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 373, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(gradeSchemeLabel, GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
					.addGap(6)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(gradeSchemeDropdown, GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(lblCategoryName, GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(catNameDropdown, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(assignNameLabel, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(assignNameText, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(assignValueLabel, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
							.addGap(1)
							.addComponent(assignValueText, GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(ptsGainedLostLabel, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(ptsEarnedRadio)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(ptsLostRadio))
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE))
					.addContainerGap())
		);

		studentGradeTable = new JTable();
		studentGradeTable.setFont(new Font("Tahoma", Font.BOLD, 14));
		scrollPane.setViewportView(studentGradeTable);
		contentPanel.setLayout(gl_contentPanel);
		studentGradeTable.setModel(ModelGenerators.displayAllStudents(managedCourse.getActiveStudents()));
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Save New Grades");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						assignName = assignNameText.getText();
						double totalAssignValue = Double.parseDouble(String.valueOf(assignValueText.getText()));
						
						//checks if assignment name is empty
						if(assignName.equals("")) {
							JOptionPane.showMessageDialog(null, "Specify an Assignment Name!");
							return;
						}
						
						//makes sure you are not editing the cell anymore
						if(null != studentGradeTable.getCellEditor()) {
							studentGradeTable.getCellEditor().stopCellEditing();
						}
						
						//toggles values depending on radio button
						if(ptsEarnedRadio.isSelected()) {
							pointsGained = true;
						}
						else if(ptsLostRadio.isSelected()) {
							pointsGained = false;
						}
						else {
							JOptionPane.showMessageDialog(null, "Select either Points Gained or Points Lost!");
							return;
						}

						for(int gradeAddIndex = 0; gradeAddIndex < studentGradeTable.getRowCount(); gradeAddIndex++) {
							int categoryIndex = 0;

							int studentIndex = gradeAddIndex;

							
							if (gradeSchemeDropdown.getSelectedIndex() == 1) {
								studentIndex = ugLocationPointer.get(gradeAddIndex);
							}
							else if (gradeSchemeDropdown.getSelectedIndex() == 2) {
								studentIndex = gradLocationPointer.get(gradeAddIndex);
							}
							
							ArrayList<CourseCategory> currentStudentCourseCat = studentList.get(studentIndex).getOverallGrade().getCourseCategoryList();

							for (int courseCatIndex = 0; courseCatIndex < currentStudentCourseCat.size(); courseCatIndex ++) {
								if (currentStudentCourseCat.get(courseCatIndex).getName().equals(catNameDropdown.getSelectedItem())) {
									categoryIndex = courseCatIndex;
								}
							}
							
							double studentPoints = togglePoints(Double.parseDouble(String.valueOf(studentGradeTable.getValueAt(gradeAddIndex, 1))), totalAssignValue, pointsGained);
							OverallGrade studentOverallGrade = studentList.get(studentIndex).getOverallGrade();
							studentOverallGrade.getCourseCategoryList().get(categoryIndex).addSubCategory(new SubCategory(assignNameText.getText(), 1, studentPoints, totalAssignValue));
							
							//function that balances the assignment weights
							studentOverallGrade.balanceAssignWeights();
							studentOverallGrade.updateOverallGrade();

						}
						managedCourse.setActiveStudents(studentList);

						// Updates the KwikGrade object's copy of the OverallGrade so it also has the updated scheme to be used for other frames.
						if (gradeSchemeDropdown.getSelectedIndex() == 1) {
							updateKwikGradeScheme(catNameDropdown, ugOverallGrade, totalAssignValue);
						}
						else if (gradeSchemeDropdown.getSelectedIndex() == 2) {
							updateKwikGradeScheme(catNameDropdown, gradOverallGrade, totalAssignValue);
						} else {
							// Otherwise, "All students" was selected, so update both undergraduate and graduate schema
							updateKwikGradeScheme(catNameDropdown, ugOverallGrade, totalAssignValue);
							updateKwikGradeScheme(catNameDropdown, gradOverallGrade, totalAssignValue);
						}
						FileManager.saveFile(MainDashboard.getKwikGrade().getActiveCourses(), MainDashboard.getActiveSaveFileName());
						FileManager.saveFile(MainDashboard.getKwikGrade().getClosedCourses(), MainDashboard.getClosedSaveFileName());
						
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	/**
	 * Given the dropdown object and the KwikGrade OverallGrade object, modifies the OverallGrade object scheme to be handed
	 * back to the previous frame. Used to update the default ugrad/grad schemes of the KwikGrade object.
	 * @param catNameDropdown
	 * @param overallGradeScheme
	 */
	private void updateKwikGradeScheme(JComboBox catNameDropdown, OverallGrade overallGradeScheme, Double totalAssignValue) {
		// Find the category index to add the SubCategory to.
		int categoryIndex = 0;
		ArrayList<CourseCategory> currentStudentCourseCat = overallGradeScheme.getCourseCategoryList();
		for (int courseCatIndex = 0; courseCatIndex < currentStudentCourseCat.size(); courseCatIndex++) {
			if (currentStudentCourseCat.get(courseCatIndex).getName().equals(catNameDropdown.getSelectedItem())) {
				categoryIndex = courseCatIndex;
			}
		}

		// Add a blank SubCategory.
		overallGradeScheme.getCourseCategoryList().get(categoryIndex).addSubCategory(new SubCategory(assignNameText.getText(), 1, 0, totalAssignValue));
		overallGradeScheme.balanceAssignWeights();
		overallGradeScheme.updateOverallGrade();
	}
}
