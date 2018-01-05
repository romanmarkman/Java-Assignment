package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
				System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getInt(5));
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
				System.out.println("RS feed: "+rs.getString(2) + ", " + rs.getString(1));
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
			//System.out.println("exception in insert section" + e);
		}
		return departments;				
	}

}
