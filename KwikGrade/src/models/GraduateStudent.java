package models;

public class GraduateStudent extends Student {
	String status;
	String comment;

	public GraduateStudent(String fName, String middleInitial, String lName, String buId, String email,String status) {
		super(fName, middleInitial, lName, buId, email);
		this.status  = status;
	}
	
	public GraduateStudent(String fName, String middleInitial, String lName, String buId, String email, String status, OverallGrade overallGrade) {
		super(fName, middleInitial, lName, buId, email);
		this.overallGrade = overallGrade.copyOverallGrade(overallGrade);
		this.status  = status;
		this.comment = "";
	}
	
	public GraduateStudent(String fName, String middleInitial, String lName, String buId, String email, String status, OverallGrade overallGrade, String comment) {
		super(fName, middleInitial, lName, buId, email);
		this.overallGrade = overallGrade.copyOverallGrade(overallGrade);
		this.status  = status;
		this.comment = comment;
	}
	
	// ========================================
	// Getters
	// ========================================
	
	public String getStatus() {
		return this.status;
	}
	
	public String getComment() {
		return this.comment;
	}
	
	// ========================================
	// Setters
	// ========================================
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}

}