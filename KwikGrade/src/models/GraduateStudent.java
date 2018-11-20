package models;

public class GraduateStudent extends Student {
	String status;

	public GraduateStudent(String fName, String middleInitial, String lName, String buId, String email,String status) {
		super(fName, middleInitial, lName, buId, email);
		this.status  = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

}