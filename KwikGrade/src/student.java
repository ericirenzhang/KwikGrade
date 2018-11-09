
public class Student {

	private String fname;
	private String mid_i;
	private String lname;
	private String buid;
	private String email;
	
	public student() {
		this.fname = "none";
		this.mid_i = "none";
		this.lname = "none";
		this.buid = "none";
		this.email = "nnone";
	}
	
	public student(String firstname, String middlei, String lname, String BUIDnum, String emailaddress) {
		this.fname = firstname;
		this.mid_i = middlei;
		this.lname = lname;
		this.buid = BUIDnum;
		this.email = emailaddress;
	}
}
