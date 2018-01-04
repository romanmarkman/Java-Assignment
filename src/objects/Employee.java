package objects;

import java.sql.Date;

public class Employee {
	
	private String fName;
	private String lName;
	private int employeeNum;
	private Date hireDate;
	private String email;
	private String jobPos;
	
	public Employee(String firstName, String lastName, int employeeNumber, Date dateOfHire, String Eemail, String jobPosition) {
		// TODO Auto-generated constructor stub
		fName = firstName;
		lName = lastName;
		employeeNum = employeeNumber;
		hireDate = dateOfHire;
		email = Eemail;
		jobPos = jobPosition;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public int getEmployeeNum() {
		return employeeNum;
	}
	public void setEmployeeNum(int employeeNum) {
		this.employeeNum = employeeNum;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getHireDate() {
		return hireDate;
	}
	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}
	public String getJobPos() {
		return jobPos;
	}
	public void setJobPos(String jobPos) {
		this.jobPos = jobPos;
	}
}