/*
* Project: COMP3095_Insert_Team_Name
* Assignment:  Assignment 1
* Author(s): Jeff, Jullian, Roman, Kevin, Andrew
* Student Number: 100872220, 100998164, 100772900, 101015906, 101035265
* Date: Oct 20, 2017
* Description: Class that holds regex functions.
*/
package helpers;

public final class ValidationHelper {
	
	public static boolean isNotNull(Object obj) {
		return (obj != null);
	}
	
	public static boolean isNotNullOrEmpty(String str) {
		return (str != null && !str.isEmpty());
	}
	
	public static boolean isAlphabetic(String str) {
		return str.matches( "[A-Z][a-zA-Z]*" );
	}
	
	public static boolean isEmail(String str) {
		return str.matches("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b");
	}
	
	public static boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	
}
