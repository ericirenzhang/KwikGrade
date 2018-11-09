import java.util.ArrayList;

public class Course {
	public String courseNum;
	private String courseTerm;
	private String courseTitle;
	private boolean isOpen;

	private ArrayList<Student> activeStudent;
	private ArrayList<Student> inactiveStudent;
	
	public Course() {
		this.courseNum = "NO NAME";
		this.courseTerm = "NO TERM";
		this.courseTitle = "NO TITLE";
		this.isOpen = true;
		this.activeStudent = new ArrayList<Student>();
		this.inactiveStudent = new ArrayList<Student>();
	}
	
	public Course(String courseNum, String courseTerm, String courseTitle) {
		this.courseNum = courseNum;
		this.courseTerm = courseTerm;
		this.courseTitle = courseTitle;
		this.isOpen = true;
		this.activeStudent = new ArrayList<Student>();
		this.inactiveStudent = new ArrayList<Student>();
	}
	
	public void closeCourse() {
		this.isOpen = false;
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
	
	public void getCourseTerm(String courseTerm) {
		this.courseTerm = courseTerm;
	}
	
	public void getCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}
	public void getIsOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

}