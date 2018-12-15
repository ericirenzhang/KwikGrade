package models;

import java.io.*;
import java.util.*;

public class Course implements Serializable {
	public String courseNum;
	private String courseTerm;
	private String courseTitle;
	private boolean isOpen;

	// Default schemes for the Course for when a new student is created
	private OverallGrade courseUnderGradDefaultGradeScheme = new OverallGrade();
	private OverallGrade courseGradDefaultGradeScheme = new OverallGrade();

	public ArrayList<Student> activeStudents;
	private ArrayList<Student> inactiveStudents;

	// Course constructor for when user chooses not to import
	public Course(String courseNum, String courseTerm, String courseTitle, OverallGrade ugCourseCategory, OverallGrade gradCourseCategory) {
		this.courseNum = courseNum;
		this.courseTerm = courseTerm;
		this.courseTitle = courseTitle;
		this.isOpen = true;
		this.activeStudents = new ArrayList<>();
		this.inactiveStudents = new ArrayList<>();
		this.courseUnderGradDefaultGradeScheme = ugCourseCategory;
		this.courseGradDefaultGradeScheme = gradCourseCategory;
	}
	
	// Course constructor for when user chooses to import students from a file
	public Course(String courseNum, String courseTerm, String courseTitle, ArrayList<Student> importedStudentsList, OverallGrade ugCourseCategory, OverallGrade gradCourseCategory) {
		this.courseNum = courseNum;
		this.courseTerm = courseTerm;
		this.courseTitle = courseTitle;
		this.isOpen = true;
		this.activeStudents = importedStudentsList;
		this.inactiveStudents = new ArrayList<Student>();
		this.courseUnderGradDefaultGradeScheme = ugCourseCategory;
		this.courseGradDefaultGradeScheme = gradCourseCategory;
	}

	public void addActiveStudents(Student activeStudents) {
		this.activeStudents.add(activeStudents);
	}

	// Removes a student from active list, moves them to inactive student list
	public void removeStudent(Student studentToRemove) {
		if(this.activeStudents.contains(studentToRemove)){
			this.activeStudents.remove(studentToRemove);
		}
		this.inactiveStudents.add(studentToRemove);
	}

	//==========================
	// Calculates Advanced Stats
	//==========================
	public double calcMean() {
		if(activeStudents.size() == 0) {
			return 0;
		}
		int num = activeStudents.size();
		double classTotal = 0.0;
		for(int i = 0;i<num;i++) {
			classTotal += activeStudents.get(i).getOverallGradeObject().getOverallGradeValue();
		}
		return Math.round((classTotal/num) * 100.0)/100.0;
	}

	public double calcMedian() {
		if(activeStudents.size() == 0) {
			return 0;
		}

		List<Double> gradeList = new ArrayList<Double>();
		for(int i=0;i<activeStudents.size();i++) {
			gradeList.add(activeStudents.get(i).getOverallGradeObject().getOverallGradeValue());
		}
		
		Collections.sort(gradeList);

		// Find the median value after the list has been sorted.
		int length = gradeList.size();
		if(length%2 == 0) {
			return (gradeList.get((length / 2) - 1) + gradeList.get((length / 2))) / 2;
		} else {
			return Math.round(gradeList.get(length/2) * 100.0)/100.0;
		}
	}
	
	public double calcStandardDeviation() {
		if(activeStudents.size() == 0) {
			return 0;
		}

		double standardDeviation = 0.0;
        int length = activeStudents.size();

        double mean = calcMean();

        for(int i=0;i<length;i++) {
            standardDeviation += Math.pow(activeStudents.get(i).getOverallGradeObject().getOverallGradeValue() - mean, 2);
        }

        return Math.round(Math.sqrt(standardDeviation/length) * 100.0)/100.0;
	}
	
	//==========================
	// Getters
	//==========================
	public String getCourseNum() {
		return this.courseNum;
	}
	
	public String getCourseTerm() {
		return this.courseTerm;
	}
	
	public String getCourseTitle() {
		return this.courseTitle;
	}

	public ArrayList<Student> getActiveStudents() {
		return this.activeStudents;
	}

	public ArrayList<Student> getInactiveStudents() {
		return this.inactiveStudents;
	}
	
	public OverallGrade getCourseUnderGradDefaultGradeScheme() {
		return this.courseUnderGradDefaultGradeScheme;
	}
	
	public OverallGrade getCourseGradDefaultGradeScheme() {
		return this.courseGradDefaultGradeScheme;
	}
	
	//==========================
	// Setters
	//==========================
	public void setIsOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	public void setCourseUnderGradDefaultGradeScheme(OverallGrade gradingScheme) {
		this.courseUnderGradDefaultGradeScheme = gradingScheme;
	}
	public void setCourseGradDefaultGradeScheme(OverallGrade gradingScheme) {
		this.courseGradDefaultGradeScheme = gradingScheme;
	}
	
	public void setActiveStudents(ArrayList<Student> activeStudents) {
		this.activeStudents = activeStudents;
	}


}