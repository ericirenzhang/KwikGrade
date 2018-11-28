package views;

import java.awt.BorderLayout;

import java.awt.Button;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import models.Course;
import models.OverallGrade;
import models.Student;

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
	private ArrayList<Student> studentList;
	private OverallGrade ugOverallGrade;
	private OverallGrade gradOverallGrade;
	private static Course managedCourse;
	private boolean pointsGained;
	
	private String assignName;
	
	//list of only grad or undergrad students
	private ArrayList<Student> ugStudentList;
	private ArrayList<Student> gradStudentList;
	
	//pointer array, shows how a student in the grad/undergrad student list relates to the entire student list
	private ArrayList<Integer> ugLocationPointer = new ArrayList<Integer>();
	private ArrayList<Integer> gradLocationPointer = new ArrayList<Integer>();
	
	private DefaultComboBoxModel catNameDropdownModel; 
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	//iterates through and creates a tablemodel of students dynamiclly
	public DefaultTableModel displayAllStudents(ArrayList<Student> Students) {
		addGradeTableModel = new DefaultTableModel();
		Object[] title = {"Name", "Points"};
		addGradeTableModel.setColumnIdentifiers(title);
		for(int i = 0; i < Students.size(); i++) {
			addGradeTableModel.addRow(new Object[] {Students.get(i).getfName()+" "+Students.get(i).getMiddleInitial()+" "+Students.get(i).getlName()} );
		}
		return addGradeTableModel;
	}
	
	public DefaultTableModel dispGradUGStudents(ArrayList<Student> Students, boolean undergrad) {
		String statusFlag;
		if(undergrad == true){
			statusFlag = "Undergraduate";
		}
		else {
			statusFlag = "Graduate";
		}
		
		addGradeTableModel = new DefaultTableModel();
		Object[] title = {"Name", "Points"};
		addGradeTableModel.setColumnIdentifiers(title);
		for(int i = 0; i < Students.size(); i++) {
			if (Students.get(i).getStatus().equals(statusFlag)) {
			addGradeTableModel.addRow(new Object[] {Students.get(i).getfName()+" "+Students.get(i).getMiddleInitial()+" "+Students.get(i).getlName()} );
			}
		}
		return addGradeTableModel;
	}
	
//	//method that updates the student table
//	public void updateStudentTable() {
//		studentGradeTable.setModel(displayAllStudents(managedCourse.getActiveStudents()));
//	}
	
	//creates a pointer array that tells you, where each undergrad and grad student is located in the course's enrolled students
	public void createPointerArray(Course course) {
		for(int i = 0; i < course.getActiveStudents().size(); i++) {
			if(course.getActiveStudents().get(i).getStatus().equals("Graduate")) {
				gradLocationPointer.add(i);
				System.out.println("Grad Student Index "+i);
			}
			else if (course.getActiveStudents().get(i).getStatus().equals("Undergraduate")) {
				ugLocationPointer.add(i);
				System.out.println("UG Student Index "+i);

			}
		}
	}
	
	//resets the dropdown menu to update the display of categories within a course
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
	
	//determines if both the UG and Grad OverallGrades (Grading Schemes) have common categories
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
	
	//determines whether the records are points gained, or lost
	public double togglePoints(double pointsGainedOrLost, double totalAssignPoints, boolean isPointsLost) {
		// if isPointsLost is true, it means user uploads points lost
		if(isPointsLost == true) {
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
		this.managedCourse = managedCourse;
		createPointerArray(managedCourse);
		ugOverallGrade = managedCourse.getCourseUnderGradDefaultGradeScheme();
		gradOverallGrade = managedCourse.getCourseGradDefaultGradeScheme();
		
		setBounds(100, 100, 664, 434);
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
					studentGradeTable.setModel(dispGradUGStudents(managedCourse.getActiveStudents(), true));
				}
				else if (gradeSchemeDropdown.getSelectedItem().equals("Graduate")){
					catNameDropdown.setModel(catNameDropdownUpdater(managedCourse, false));
					studentGradeTable.setModel(dispGradUGStudents(managedCourse.getActiveStudents(), false));
				}
				else {
					studentGradeTable.setModel(displayAllStudents(managedCourse.getActiveStudents()));
					catNameDropdown.setModel(commonCourseCategories(managedCourse));
				}
			}
		});
		
		JRadioButton ptsEarnedRadio = new JRadioButton("Points Gained");
		buttonGroup.add(ptsEarnedRadio);
		
		JRadioButton ptsLostRadio = new JRadioButton("Points Lost");
		buttonGroup.add(ptsLostRadio);
		
		JLabel ptsGainedLostLabel = new JLabel("Points Tracking Method");
		ptsGainedLostLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
	
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(32)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(gradeSchemeDropdown, 0, 156, Short.MAX_VALUE)
									.addGap(79))
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(gradeSchemeLabel, GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
									.addGap(88))
								.addComponent(lblCategoryName, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
								.addComponent(catNameDropdown, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
								.addComponent(assignNameText, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
								.addComponent(assignNameLabel, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
								.addComponent(ptsLostRadio)
								.addComponent(ptsEarnedRadio)))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(ptsGainedLostLabel, GroupLayout.PREFERRED_SIZE, 235, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
					.addGap(87))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(12)
							.addComponent(gradeSchemeLabel, GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(gradeSchemeDropdown, GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(lblCategoryName, GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(catNameDropdown, GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(assignNameLabel, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(assignNameText, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
							.addGap(35)
							.addComponent(ptsGainedLostLabel, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(ptsEarnedRadio)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(ptsLostRadio))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap(12, Short.MAX_VALUE)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 329, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		

		
		studentGradeTable = new JTable();
		scrollPane.setViewportView(studentGradeTable);
		contentPanel.setLayout(gl_contentPanel);
		studentGradeTable.setModel(displayAllStudents(managedCourse.getActiveStudents()));
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Add New Grades");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						assignName = assignNameText.getText();
						
						if(assignName.equals("")) {
							JOptionPane.showMessageDialog(null, "Specify an Assignment Name!");
						}
						
						if(ptsEarnedRadio.isSelected()) {
							pointsGained = true;
							JOptionPane.showMessageDialog(null, "Points Gained!");

						}
						else if(ptsLostRadio.isSelected()) {
							pointsGained = false;
							JOptionPane.showMessageDialog(null, "Points Lost!");

						}
						else {
							JOptionPane.showMessageDialog(null, "Select either Points Gained or Points Lost!");
						}
						
						for(int gradeAddIndex = 0; gradeAddIndex < studentGradeTable.getRowCount(); gradeAddIndex++) {
							System.out.println(gradeAddIndex);
						}
						
						
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
