package helpers;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;

import models.Course;
import models.Student;

public class ModelGenerators {
	
	/**
	 * iterates through and creates a tablemodel that summarizes the student and Grades for the Course Overview
	 * @param Students
	 */
	public static DefaultTableModel generateStudentTableModel(ArrayList<Student> Students) {
		DefaultTableModel studentTableModel = new DefaultTableModel();
		Object[] title = {"First Name", "Middle Initial", "Last Name", "Status", "Grade"};
		studentTableModel.setColumnIdentifiers(title);
		for(int i = 0; i < Students.size(); i++) {
			Student currStudent = Students.get(i);
			studentTableModel.addRow(new Object[] {currStudent.getfName(),currStudent.getMiddleInitial(), currStudent.getlName(), currStudent.getStatus(), currStudent.getOverallGradeObject().getOverallGrade()} );
		}
		return studentTableModel;
	}
	
	/**
	 * iterates through and creates a tablemodel of all students to add a grade for
	 * @param Students
	 */
	public static DefaultTableModel generateAddGradeTableModel(ArrayList<Student> Students) {
		DefaultTableModel addGradeTableModel = new DefaultTableModel();
		Object[] title = {"Name", "Points"};
		addGradeTableModel.setColumnIdentifiers(title);
		for(int i = 0; i < Students.size(); i++) {
			addGradeTableModel.addRow(new Object[] {Students.get(i).getfName()+" "+Students.get(i).getMiddleInitial()+" "+Students.get(i).getlName()} );
		}
		return addGradeTableModel;
	}
	
	/**
	 * creates a tablemodel that displays either only undergrad or only grad students
	 * @param Students
	 * @param undergrad
	 */
	public static DefaultTableModel dispGradUGStudents(ArrayList<Student> Students, boolean undergrad) {
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
			addGradeTableModel.addRow(new Object[] {Students.get(i).getfName()+" "+Students.get(i).getMiddleInitial()+" "+Students.get(i).getlName()} );
			}
		}
		return addGradeTableModel;
	}
	
	
	/**
	 * creates a tablemodel that updates the live statistics table
	 * @param Course
	 * @param statsTableModel
	 */
	public static void updateStatsModel(Course course, DefaultTableModel statsTableModel) {
		statsTableModel.setValueAt(String.format("Mean: %.2f%%", course.calcMean()), 0, 0);
		statsTableModel.setValueAt(String.format("Median: %.2f%%", course.calcMedian()), 1, 0);
		statsTableModel.setValueAt(String.format("Std Dev: %.2f%%", course.calcStandardDeviation()), 2, 0);
	}
	
	/**
	 * creates a listmodel that updates the course arraylists
	 * @param Course
	 * @param statsTableModel
	 */
	public static DefaultListModel loadCourseList(ArrayList<Course> courseList) {
		DefaultListModel DLM = new DefaultListModel();
		for(int i = 0; i < courseList.size(); i++) {
			DLM.addElement(courseList.get(i).getCourseNum()+" "+courseList.get(i).getCourseTerm()+" "+courseList.get(i).getCourseTitle());
		}
		return DLM;
	}

}
