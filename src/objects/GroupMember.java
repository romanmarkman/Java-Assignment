package objects;

public class GroupMember {
	
	private String departmentName;
	private String groupName;
	private String firstName;
	private String lastName;
	private String eID;
	
	public GroupMember(String depName, String groupName, String fn, String ln, String number) {
		this.departmentName = depName;
		this.groupName = groupName;
		this.firstName = fn;
		this.lastName = ln;
		this.eID = number;
	}
	
	public String getDepartmentName() {
		return departmentName;
	}


	public String getGroupName() {
		return groupName;
	}


	public String getFirstName() {
		return firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public String geteID() {
		return eID;
	}


	

}
