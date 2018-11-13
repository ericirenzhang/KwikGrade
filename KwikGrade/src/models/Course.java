package models;

import java.io.*;
import java.util.*;

public class Course implements Serializable {
	public String courseNum;
	private String courseTerm;
	private String courseTitle;
	private boolean isOpen;

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

}