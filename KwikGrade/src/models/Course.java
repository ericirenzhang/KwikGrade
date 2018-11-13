package models;
import java.io.*;
import java.util.*;
import java.io.File;

public class Course {
	public String courseNum;
	private String courseTerm;
	private String courseTitle;
	private boolean isOpen;
	
	private Scanner rawStudentData;

	private ArrayList<Student> activeStudent;
	private ArrayList<Student> inactiveStudent;
	
// course constructor for not adding bulk students
	public Course(String courseNum, String courseTerm, String courseTitle) {
		this.courseNum = courseNum;
		this.courseTerm = courseTerm;
		this.courseTitle = courseTitle;
		this.isOpen = true;
		this.activeStudent = new ArrayList<Student>();
		this.inactiveStudent = new ArrayList<Student>();
	}
	
//	course constructor for adding bulk students from a file
	public Course(String courseNum, String courseTerm, String courseTitle, String filePath) {
		this.courseNum = courseNum;
		this.courseTerm = courseTerm;
		this.courseTitle = courseTitle;
		this.isOpen = true;
		this.activeStudent = new ArrayList<Student>();
		this.inactiveStudent = new ArrayList<Student>();
		bulkStudentAdd(filePath);
	}
	
	public void closeCourse() {
		this.isOpen = false;
	}
	
	//==========================
	// File Handling to bulk add students
	//==========================
	
	// Currently standing not used, will be used once Grad and Undergrad student classes are implemented
	public void bulkStudentAdd(String filePath) {
		openFile(filePath);
		while(rawStudentData.hasNext()) {
			String line = rawStudentData.nextLine();
			List<String> splitLine = Arrays.asList(line.split(","));
			if(splitLine.size()==6) { //checks for middle initial, if there's middle initial, there will be 6 items in string
				String fName = splitLine.get(0);
				String middleInitial = splitLine.get(1);
				String lName = splitLine.get(2);
				String buId = splitLine.get(3);
				String email = splitLine.get(4);
				String standing = splitLine.get(5);
				activeStudent.add(new Student(fName, middleInitial, lName, buId, email));
			}
			else { //if no middle initial, then 5 items in string
				String fName = splitLine.get(0);
				String middleInitial = "";
				String lName = splitLine.get(1);
				String buId = splitLine.get(2);
				String email = splitLine.get(3);
				String standing = splitLine.get(4);
				activeStudent.add(new Student(fName, middleInitial, lName, buId, email));
			}
		}
		closeFile();
		System.out.println("Student Import Complete!");
		
		//testing code, to verify output, keep for future iterations
//		System.out.println(activeStudent);
//		System.out.println(activeStudent.get(2).getfName());
//		System.out.println(activeStudent.get(2).getMiddleInitial());
//		System.out.println(activeStudent.get(2).getlName());
//		System.out.println(activeStudent.get(2).getBuId());
//		System.out.println(activeStudent.get(3).getfName());
//		System.out.println(activeStudent.get(3).getMiddleInitial());
//		System.out.println(activeStudent.get(3).getlName());
//		System.out.println(activeStudent.get(3).getBuId());
	}
	
	public void openFile(String filePath) {
		try {
			System.out.println("Loading Students");
			rawStudentData = new Scanner(new File(filePath));
		}
		//will change this to prompt user for another file
		catch(Exception e) {
			System.out.println("COULD NOT FIND FILE!!!!");
		}
	}
	
	public void closeFile() {
		rawStudentData.close();
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