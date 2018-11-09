import java.util.ArrayList;

public class course {
	public String coursename;
	private ArrayList<Student> students_enrolled;
	
	public course() {
		this.coursename = "NO NAME";
		this.students_enrolled = new ArrayList<Student>();
	}
	
	public course(String coursename) {
		this.coursename = coursename;
		this.students_enrolled = new ArrayList<Student>();
	}


}
