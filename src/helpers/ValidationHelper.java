/*
* Project: COMP3095_Insert_Team_Name
* Assignment:  Assignment 1
* Author(s): Jeff, Jullian, Roman, Kevin, Andrew
* Student Number: 100872220, 100998164, 100772900, 101015906, 101035265
* Date: Oct 20, 2017
* Description: Class that holds regex functions.
*/
package helpers;

import utilities.DatabaseAccess;
import java.sql.*;

public final class ValidationHelper {
	public static boolean isNotNull(Object obj) {
		return (obj != null);
	}
	
	public static boolean isNotNullOrEmpty(String str) {
		return (str != null && !str.isEmpty());
	}
	
	public static boolean isAlphabetic(String str) {
		return str.matches( "[A-Z][a-zA-Z ]*" );
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
	
	public static boolean isAlphanumeric(String input){
		return input.matches( "[A-Z][a-zA-Z0-9 ]*" );
	}

	public static boolean length(int input){
		if(input < 30)
			return true;
		else
			return false;
	}
  
  	public static boolean compare(String input) {
		//Connect to DB and set up Query
		try {
		java.sql.Connection connect = DatabaseAccess.connectDataBase();
		String queryString = "Select name from groups";
		Statement st =  connect.createStatement();
		ResultSet resSet = st.executeQuery(queryString);
		
			while(resSet.next()) {
				if(resSet.getString(1).equals(input)) {
						connect.close();
						return false;
				}
			}
			connect.close();
		} catch(Exception e) {
			System.err.println("Something unexpected happened...");
			System.err.println(e.getMessage());
		}
		return true;
	}
  	
	public static boolean dateValidation(String date){
		return date.matches( "[0-9]{2}/[0-9]{2}/[0-9]{4}" );
	}
}
