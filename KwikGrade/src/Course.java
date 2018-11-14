import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Course{
	public String courseNum;
	private String courseTerm;
	private String courseTitle;
	private boolean isOpen;

	private List<Student> activeStudent;
	private List<Student> inactiveStudent;
	
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
	
	public double calcMean() {
		int num = activeStudent.size();
		double x = 0.0;
		for(int i = 0;i<num;i++) {
			x += activeStudent.get(i).grade.getOverallGrade();
		}
		return x/num;
	}
	
	public double calcMedian() {
		List<Double> gradeList = new ArrayList<Double>();
		for(int i=0;i<activeStudent.size();i++)
			gradeList.add(activeStudent.get(i).grade.getOverallGrade());
		
		Collections.sort(gradeList);
		
		int length = gradeList.size();
		if(length%2 == 0)
			return (gradeList.get(length/2) + gradeList.get((length/2)+1))/2;
		return gradeList.get(length/2);
	}
	
	public double calcStandardDeviation() {
		double standardDeviation = 0.0;
        int length = activeStudent.size();

        double mean = calcMean();

        for(int i=0;i<length;i++) {
            standardDeviation += Math.pow(activeStudent.get(i).grade.getOverallGrade() - mean, 2);
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