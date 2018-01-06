/*
* Project: COMP3095_Insert_Team_Name
* Assignment:  Assignment 2
* Author(s): Jeff, Jullian, Roman, Kevin, Andrew
* Student Number: 100872220, 100998164, 100772900, 101015906, 101035265
* Date: Dec 29 2017
* Description: Db functions for department pages..
*/
package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import objects.Department;
import utilities.DatabaseAccess;

public final class DepartmentHelper {
	
	public static ArrayList<Department> getDepartments(){
		ArrayList<Department> departments = new ArrayList<>();
		try {
			Connection connect = DatabaseAccess.connectDataBase();
					
				String query = "SELECT name,location FROM department";
				PreparedStatement stmt = connect.prepareStatement(query);
				ResultSet res = stmt.executeQuery();
				while(res.next()) {
					Department dep = new Department(res.getString(1),res.getString(2));
					departments.add(dep);
				}
				res.close();
				connect.close();
			}catch(Exception e) {
			//System.out.println("exception in insert section" + e);
		}
		return departments;				
	}

}
