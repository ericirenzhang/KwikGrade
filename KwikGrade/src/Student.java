
public class Student {
	private String fName;
	private String middleInitial;
	private String lName;
	private String buId;
	private String email;

	// TODO: add OverallGrade objects once implemented

	// TODO: add comments string as attribute
	
	public Student(String fName, String middleInitial, String lName, String buId, String email) {
		this.fName = fName;
		this.middleInitial = middleInitial;
		this.lName = lName;
		this.buId = buId;
		this.email = email;
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

	public String getFullName() {
		return String.format("%s %s. %s", this.fName, this.middleInitial, this.lName);
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

}
