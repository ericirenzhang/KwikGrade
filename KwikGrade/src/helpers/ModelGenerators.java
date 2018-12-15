package helpers;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import models.Course;
import models.Student;

public class ModelGenerators {
	
	/**
	 * Iterates through and creates a DefaultTableModel that summarizes the student and Grades for the Course Overview
	 * @param Students
	 */
	public static DefaultTableModel generateStudentTableModel(ArrayList<Student> Students, boolean detailedView) {
		DefaultTableModel studentTableModel = new DefaultTableModel();
		if (detailedView == false) {
			Object[] title = {"First Name", "Middle Initial", "Last Name", "Status", "Grade"};
			studentTableModel.setColumnIdentifiers(title);
			for(int i = 0; i < Students.size(); i++) {
				Student currStudent = Students.get(i);
				studentTableModel.addRow(new Object[] {currStudent.getfName(),currStudent.getMiddleInitial(), currStudent.getlName(), currStudent.getStatus(), currStudent.getOverallGradeObject().getOverallGradeValue()} );
			}
			return studentTableModel;
		}
		else {
			Object[] title = {"First Name", "Middle Initial", "Last Name", "Status", "BUID", "Email", "Grade"};
			studentTableModel.setColumnIdentifiers(title);
			for(int i = 0; i < Students.size(); i++) {
				Student currStudent = Students.get(i);
				studentTableModel.addRow(new Object[] {currStudent.getfName(),currStudent.getMiddleInitial(), currStudent.getlName(), currStudent.getStatus(), currStudent.getBuId(), currStudent.getEmail(), currStudent.getOverallGradeObject().getOverallGradeValue()} );
			}
			return studentTableModel;
		}

	}
	
	/**
	 * creates JTable for students, for use in course overview
	 * @param table
	 */
	public static JTable setDefaultAttributes(JTable table) {
		table.setGridColor(Color.BLACK); // set lines to black for Mac
		table.setRowHeight(30);
		table.setFont(new Font("Tahoma", Font.PLAIN, 18));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoscrolls(true);
		return table;
	}
	
	/**
	 * resizes the JTable columns if 
	 * @param table
	 * @param detailedView
	 */
	public static JTable setTableSizing(JTable table, boolean detailedView) {
		TableColumnModel columnModel = table.getColumnModel();
		if(detailedView == true) {
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			columnModel.getColumn(0).setPreferredWidth(90);
			columnModel.getColumn(1).setPreferredWidth(80);						
			columnModel.getColumn(2).setPreferredWidth(100);						
			columnModel.getColumn(3).setPreferredWidth(120);						
			columnModel.getColumn(4).setPreferredWidth(110);						
			columnModel.getColumn(5).setPreferredWidth(200);						
			columnModel.getColumn(6).setPreferredWidth(50);						
			return table;
		}
		else {
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);						
			return table;
		}
	}
	
	/**
	 * Iterates through and creates a DefaultTableModel of all students to Add a grade for
	 * @param Students
	 */
	public static DefaultTableModel generateAddGradeTableModel(ArrayList<Student> Students) {
		DefaultTableModel addGradeTableModel = new DefaultTableModel();
		Object[] title = {"Name", "Points"};
		addGradeTableModel.setColumnIdentifiers(title);
		for(int i = 0; i < Students.size(); i++) {
			addGradeTableModel.addRow(new Object[] {Students.get(i).getFullName()} );
		}
		return addGradeTableModel;
	}
	
	/**
	 * Creates a DefaultTableModel that displays either only undergrad or only grad students
	 * @param Students
	 * @param undergrad
	 */
	public static DefaultTableModel generateGradUndergradTableModel(ArrayList<Student> Students, boolean undergrad) {
		String statusFlag;
		if(undergrad == true){
			statusFlag = "Undergraduate";
		}
		else {
			statusFlag = "Graduate";
		}
		
		DefaultTableModel addGradeTableModel = new DefaultTableModel();
		Object[] title = {"Name", "Points"};
		addGradeTableModel.setColumnIdentifiers(title);
		for(int i = 0; i < Students.size(); i++) {
			if (Students.get(i).getStatus().equals(statusFlag)) {
				addGradeTableModel.addRow(new Object[] { Students.get(i).getFullName() } );
			}
		}
		return addGradeTableModel;
	}
	
	
	/**
	 * Updates a DefaultTableModel that updates the live statistics table
	 * @param course
	 * @param statsTableModel
	 */
	public static void updateStatsModel(Course course, DefaultTableModel statsTableModel) {
		statsTableModel.setValueAt(String.format("Mean: %.2f%%", course.calcMean()), 0, 0);
		statsTableModel.setValueAt(String.format("Median: %.2f%%", course.calcMedian()), 1, 0);
		statsTableModel.setValueAt(String.format("Std Dev: %.2f%%", course.calcStandardDeviation()), 2, 0);
	}
	
	/**
	 * Creates a DefaultTableModel of the course list.
	 * @param courseList
	 */
	public static DefaultListModel generateCourseTableModel(ArrayList<Course> courseList) {
		DefaultListModel DLM = new DefaultListModel();
		for(int i = 0; i < courseList.size(); i++) {
			DLM.addElement(courseList.get(i).getCourseNum()+" "+courseList.get(i).getCourseTerm()+" "+courseList.get(i).getCourseTitle());
		}
		return DLM;
	}

}
