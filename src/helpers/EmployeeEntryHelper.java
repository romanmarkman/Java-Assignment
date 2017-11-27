/*
* Project: COMP3095_Insert_Team_Name
* Assignment:  Assignment 1
* Author(s): Jeff, Jullian, Roman, Kevin, Andrew
* Student Number: 100872220, 100998164, 100772900, 101015906, 101035265
* Date: Oct 20, 2017
* Description: Class that holds regex functions.
*/
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
		return input.matches( "[A-Z][a-zA-Z]*" );
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
