package models;

import java.io.*;
import java.util.*;

public class Course implements Serializable {
	public String courseNum;
	private String courseTerm;
	private String courseTitle;
	private boolean isOpen;
	private OverallGrade courseDefaultGradeScheme = new OverallGrade();

	private ArrayList<Student> activeStudents;
	private ArrayList<Student> inactiveStudents;
	
// course constructor for not adding bulk students
	public Course(String courseNum, String courseTerm, String courseTitle) {
		this.courseNum = courseNum;
		this.courseTerm = courseTerm;
		this.courseTitle = courseTitle;
		this.isOpen = true;
		this.activeStudents = new ArrayList<>();
		this.inactiveStudents = new ArrayList<>();
	}
	
	//	course constructor for adding bulk students from a file
	public Course(String courseNum, String courseTerm, String courseTitle, ArrayList<Student> importedStudentsList) {
		this.courseNum = courseNum;
		this.courseTerm = courseTerm;
		this.courseTitle = courseTitle;
		this.isOpen = true;
		this.activeStudents = importedStudentsList;
		this.inactiveStudents = new ArrayList<Student>();
	}
	
	public void closeCourse() {
		this.isOpen = false;
	}

	// TODO: may need to accomodate for undergrad vs grad student later on
	public void addStudent(Student studentToAdd) {
		activeStudents.add(studentToAdd);
	}

	public void removeStudent(Student studentToRemove) {
		if(this.activeStudents.contains(studentToRemove)){
			this.activeStudents.remove(studentToRemove);
		}
		this.inactiveStudents.add(studentToRemove);
	}
	
		public double calcMean() {
		int num = activeStudents.size();
		double x = 0.0;
		for(int i = 0;i<num;i++) {
			x += activeStudents.get(i).grade.getOverallGrade();
		}
		return x/num;
	}
	
	//==========================
	// Calculates Advanced Stats
	//==========================
	
	public double calcMedian() {
		List<Double> gradeList = new ArrayList<Double>();
		for(int i=0;i<activeStudents.size();i++)
			gradeList.add(activeStudents.get(i).grade.getOverallGrade());
		
		Collections.sort(gradeList);
		
		int length = gradeList.size();
		if(length%2 == 0)
			return (gradeList.get(length/2) + gradeList.get((length/2)+1))/2;
		return gradeList.get(length/2);
	}
	
	public double calcStandardDeviation() {
		double standardDeviation = 0.0;
        int length = activeStudents.size();

        double mean = calcMean();

        for(int i=0;i<length;i++) {
            standardDeviation += Math.pow(activeStudents.get(i).grade.getOverallGrade() - mean, 2);
        }

        return Math.sqrt(standardDeviation/length);
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
	public boolean getIsOpen() {
		return this.isOpen;
	}

	public ArrayList<Student> getActiveStudents() {
		return this.activeStudents;
	}

	public ArrayList<Student> getInactiveStudents() {
		return this.inactiveStudents;
	}
	
	public OverallGrade getCourseDefaultGradeScheme() {
		return this.courseDefaultGradeScheme;
	}
	
	//==========================
	// Setters
	//==========================
	public void setCourseNum(String courseNum) {
		this.courseNum = courseNum;
	}
	
	public void setCourseTerm(String courseTerm) {
		this.courseTerm = courseTerm;
	}
	
	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}
	public void setIsOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	public void setCourseDefaultGradeScheme(OverallGrade gradingScheme) {
		this.courseDefaultGradeScheme = gradingScheme;
	}
	
	public void setActiveStudents(Student activeStudents) {
		this.activeStudents.add(activeStudents);
	}

}