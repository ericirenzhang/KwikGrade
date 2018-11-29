package models;

public class GraduateStudent extends Student {
	String status;

	public GraduateStudent(String fName, String middleInitial, String lName, String buId, String email,String status) {
		super(fName, middleInitial, lName, buId, email);
		this.status  = status;
	}
	
	public GraduateStudent(String fName, String middleInitial, String lName, String buId, String email, String status, OverallGrade overallGrade) {
		super(fName, middleInitial, lName, buId, email);
		this.overallGrade = overallGrade.copyOverallGrade(overallGrade);
		this.status  = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

}