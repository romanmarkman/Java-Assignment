package helpers;

public final class EmployeeEntryHelper {
	
	public static boolean isNotNullOrEmpty(String input) {
		if(input != null && !input.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static boolean isAlphabetic(String input) {
		return input.matches( "[a-zA-Z]*" );
	}
	
	public static boolean isEmail(String input) {
		return input.matches("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b");
	}
	
	public static boolean isInteger(String input) {
		try
		{
			Integer.parseInt(input);
			return true;
		}
		catch(Exception e) 
		{
			return false;
		}
	}
	
	
}
