/*
* Project: COMP3095_Insert_Team_Name
* Assignment:  Assignment 2
* Author(s): Jeff, Jullian, Roman, Kevin, Andrew
* Student Number: 100872220, 100998164, 100772900, 101015906, 101035265
* Date: Dec 29 2017
* Description: Db functions for Employee pages.
*/
package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import objects.Employee;
import utilities.DatabaseAccess;

public final class EmployeeHelper {
	
	public static Boolean insertEmployee(String firstName, String lastName, String employeeNumber, String email, String hireYr, String jobPos, String department) {
		try {
			Connection connect = DatabaseAccess.connectDataBase();
			
			// INSERT query
			String queryString = "INSERT INTO employee (firstname, lastname, employee_number, email, hire_date, job_position, department_id) VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?)";
			
			// Preparing insert statement using preparedStatement
			PreparedStatement stmt = connect.prepareStatement(queryString);
			stmt.setString(1, firstName);
			stmt.setString(2, lastName);
			stmt.setString(3, employeeNumber);
			stmt.setString(4, email);		
			// Set hireDate in database to 1st day of january for whatever year is selected
			stmt.setDate(5, java.sql.Date.valueOf(hireYr + "-01-01"));
			stmt.setString(6, jobPos);							
			stmt.setInt(7, Integer.parseInt(department));
			
			// Pass confirmMessage to page and load dialog if not null
			int rowsInserted = stmt.executeUpdate();
			
			connect.close();
			if(rowsInserted == 0) {
				return false;
			}
			return true;
			
		} catch (Exception e) {
			//System.err.println("Exception: " + e.getMessage());
			return false;
		}
	}
	
	public static ArrayList<Employee> getEmployees(String departmentId){
		ArrayList<Employee> employees = new ArrayList<>();
		try {
			Connection connect = DatabaseAccess.connectDataBase();
			// Change selected department name to its corresponding id.
			
			
			//Retrieve query
			String queryString2 = "SELECT * FROM employee INNER JOIN department ON department.department_id = employee.department_id"
									+ " WHERE employee.department_id = ? ";
			PreparedStatement stmt2 = connect.prepareStatement(queryString2);
			stmt2.setInt(1, Integer.parseInt(departmentId));
			ResultSet rs = stmt2.executeQuery();
			ArrayList<Employee> employeeList = new ArrayList<>();
			while(rs.next()) {
				
				Employee employee = new Employee(rs.getString("firstname"),   rs.getString("lastname"),
												 rs.getInt("employee_number"),rs.getDate("hire_date"),
												 rs.getString("email"),       rs.getString("job_position"),
												 rs.getInt("employee_id"));
				employees.add(employee);
			}
			rs.close();
			
			
			connect.close();
									
		} catch (Exception e) {
			//System.err.println("Exception: " + e.getMessage());
		}
		return employees;
	}
	
	

}
