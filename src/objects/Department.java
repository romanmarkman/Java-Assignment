package objects;

public class Department {

	private String name;
	private String location;
	
	
	public Department(String name, String location) {
		this.name = name;
		this.location = location;
	}
	
	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}
}
