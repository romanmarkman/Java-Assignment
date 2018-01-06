/*
* Project: COMP3095_Insert_Team_Name
* Assignment:  Assignment 2
* Author(s): Jeff, Jullian, Roman, Kevin, Andrew
* Student Number: 100872220, 100998164, 100772900, 101015906, 101035265
* Date: Dec 29 2017
* Description: DB helper for group pages.
*/
package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import objects.Department;
import objects.GroupMember;
import utilities.DatabaseAccess;

public final class GroupHelper {
	
	
	public static ArrayList<GroupMember> getGroupMembers(Integer groupID){
		ArrayList<GroupMember> members = new ArrayList<GroupMember>();
		try {
			Connection connect = DatabaseAccess.connectDataBase();
			
			String queryString = "SELECT department.name, groups.name, employee.firstname, employee.lastname, employee.employee_number "
								+ " FROM employee "
								+ " INNER JOIN department ON department.department_id = employee.department_id "
								+ " INNER JOIN groups ON groups.department_id = department.department_id"
								+ " INNER JOIN employee_group ON groups.group_id = employee_group.group_id "
								+ " WHERE groups.group_id = ? AND employee_group.employee_id = employee.employee_id";
			
			PreparedStatement stmt = connect.prepareStatement(queryString);
			stmt.setInt(1, groupID);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {			
				GroupMember m = new GroupMember(rs.getString(1),rs.getString(2), rs.getString(3), rs.getString(4),Integer.toString(rs.getInt(5)));
				members.add(m);
			}
			
			
		} catch(Exception e) {
			System.out.println("Error in GroupHelper.getGroupMembers" + e);
		}
		
		
		return members;
	}
	
	public static Map<String,String> getDepartmentGroups(Integer depID){
		Map<String,String> group = new LinkedHashMap<>();
		
		try {
			Connection connect = DatabaseAccess.connectDataBase();
			
			String queryString = "SELECT groups.group_id,groups.name FROM groups"								
								+ " INNER JOIN department"
								+ " ON department.department_id = groups.department_id"
								+ " WHERE groups.department_id = ? ";
			PreparedStatement stmt = connect.prepareStatement(queryString);
			stmt.setInt(1,depID);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {				
				group.put(Integer.toString(rs.getInt(1)), rs.getString(2));
			}
			rs.close();
			connect.close();
		}catch(Exception e) {
			System.out.println("Error in getDepartmentEmployees: " + e);
			return null;
		}
		return group;
		
	}
	
	public static Map<String,String> getDepartments(){
		Map<String,String> departments = new LinkedHashMap<>();
		try {
			Connection connect = DatabaseAccess.connectDataBase();
					
				String query = "SELECT department_id,name FROM department";
				PreparedStatement stmt = connect.prepareStatement(query);
				ResultSet res = stmt.executeQuery();
				while(res.next()) {					
					departments.put(Integer.toString(res.getInt(1)),res.getString(2));
				}
				res.close();
				connect.close();
			}catch(Exception e) {
			System.out.println("exception in insert section" + e);
		}
		return departments;				
	}
	
	public static Map<String,String> getDepartmentEmployees(Integer depID){
		Map<String,String> employees = new LinkedHashMap<String,String>();
		
		try {
			Connection connect = DatabaseAccess.connectDataBase();
			
			String queryString = "SELECT employee_id,firstname,lastname FROM employee"								
								+ " INNER JOIN department"
								+ " ON employee.department_id = department.department_id"
								+ " WHERE employee.department_id = ? ";
			PreparedStatement stmt = connect.prepareStatement(queryString);
			stmt.setInt(1,depID);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				//System.out.println("RS feed: "+rs.getString(2) + ", " + rs.getString(1));
				employees.put(Integer.toString(rs.getInt(1)),rs.getString(3) + ", " + rs.getString(2));
			}
			rs.close();
			connect.close();
		}catch(Exception e) {
			System.out.println("Error in getDepartmentEmployees: " + e);
			return null;
		}
		return employees;
		
	}
	
	public static Boolean insertGroup(String groupName, Integer departmentID, String[] employee_ids) {
		
		try {
			Connection connect = DatabaseAccess.connectDataBase();
			Integer groupID = 0;
			Integer rowsInserted1 = 0;
			Integer rowsInserted2 = 0;
			String queryString = "INSERT INTO groups (name, department_id) VALUES "
					+ "(?, ?)";
			
			PreparedStatement pStatement = connect.prepareStatement(queryString,Statement.RETURN_GENERATED_KEYS);
			
			pStatement.setString(1, groupName.trim());
			pStatement.setInt(2, departmentID);
			pStatement.execute();
			ResultSet res = pStatement.getGeneratedKeys();
			if(res.next()) {
				groupID = res.getInt(1);
				rowsInserted1++;
			}
			res.close();	
										
			if(rowsInserted1 == 0) {
				return false;
			}
											
			queryString = "INSERT INTO employee_group (employee_id, group_id) VALUES"
						+ "(?, ?)";
			pStatement = connect.prepareStatement(queryString);
			for(int i=0; i<employee_ids.length; i++){
				pStatement.setInt(1, Integer.parseInt(employee_ids[i]));
				pStatement.setInt(2, groupID);
				pStatement.execute();
				rowsInserted2++;
			}
			
			if(rowsInserted2 == 0) {
				return false;
			}
				
			connect.close();
			return true;
		
		} catch(Exception e) {
			System.err.println("Something unexpected happened...");
			System.err.println(e.getMessage());
			return false;
		}
	
	}
	
	public static Boolean checkForGroupName(String groupName, Integer depID) {
		
		try {
			Integer counter = 0;
			Connection connect = DatabaseAccess.connectDataBase();
			String queryString = "SELECT * FROM groups WHERE name = ? AND department_id = ?";
			
			PreparedStatement stmt = connect.prepareStatement(queryString);
			stmt.setString(1, groupName);
			stmt.setInt(2, depID);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				counter++;
			}
			rs.close();
			connect.close();
			if(counter > 0) {
				return false;
			} else {
				return true;
			}
				
		}catch(Exception e) {
			System.out.println("Error in ");
			return false;
		}
		
	}
	
	
	
	
	
}
