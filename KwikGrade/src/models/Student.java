package models;

import java.io.Serializable;

public class Student implements Serializable {
	private String fName;
	private String middleInitial;
	private String lName;
	private String buId;
	private String email;
	protected OverallGrade overallGrade;
	private String status;
	private String comment;
	
	public Student(String fName, String middleInitial, String lName, String buId, String email) {
		this.fName = fName;
		this.middleInitial = middleInitial;
		this.lName = lName;
		this.buId = buId;
		this.email = email;
		overallGrade = new OverallGrade();
		this.status = "";
		this.comment = "";
	}

	// ========================================
	// Getters
	// ========================================
	
	public String getfName() {
		return this.fName;
	}

	public String getMiddleInitial() {
		return this.middleInitial;
	}

	public String getlName() {
		return this.lName;
	}

	public String getBuId() {
		return this.buId;
	}

	public String getEmail() {
		return this.email;
	}
	
	public String getStatus() {
		return this.status;
	}

	public String getFullName() {
		return String.format("%s %s. %s", this.fName, this.middleInitial, this.lName);
	}
	
	public String getComment() {
		return this.comment;
	}
	
	public OverallGrade getOverallGrade() {
		return this.overallGrade;
	}
	
	public OverallGrade getOverallGradeObject() {
		return this.overallGrade;
	}
	
	// ========================================
	// Setters
	// ========================================
	public void setfName(String fName) {
		this.fName = fName;
	}

	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public void setBuId(String buId) {
		this.buId = buId;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setOverallGrade(OverallGrade overallGrade) {
		this.overallGrade = overallGrade;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}

}
