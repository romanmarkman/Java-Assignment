package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

import com.mysql.jdbc.Statement;

import objects.Employee;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import utilities.DatabaseAccess;

public class AttendanceHelper {

	public static Map<String, String> getDepartmentList(){
		Map<String, String> deptList = new LinkedHashMap<>();
		try{
			Connection conn = DatabaseAccess.connectDataBase();
			
			String query = "SELECT department_id, name FROM department";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				//System.out.println(Integer.toString(rs.getInt(1)) + " " + rs.getString(2));
				deptList.put(Integer.toString(rs.getInt(1)), rs.getString(2));
			}
			rs.close();
			conn.close();
			
		}
		catch(Exception e){}
		return deptList;
	}
	
	public static void insertAttendance(String[] presentIDs, ArrayList<Employee> employees, Integer departmentID, String date) {
		try{
			Connection conn = DatabaseAccess.connectDataBase();
			Integer attenID = 0;
			String query = "INSERT INTO attendance (department_id,date) VALUES(?,?)";
			
			PreparedStatement stmt = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, departmentID);
			stmt.setString(2, date);
			stmt.execute();
			ResultSet res = stmt.getGeneratedKeys();
			while (res.next()){
				attenID = res.getInt(1);
			}
			res.close();
			
			String insertAE = "INSERT INTO atten_employee (employee_id,atten_id,attended) VALUES(?,?,?)";
			
			PreparedStatement stmt2 = conn.prepareStatement(insertAE);
			for(Employee e : employees) {
				Boolean entered = false;
				for(String present : presentIDs) {
					if(e.geteId().equals(Integer.parseInt(present))) {
						stmt2.setInt(1, e.geteId());
						stmt2.setInt(2, attenID);
						stmt2.setInt(3, 1);
						stmt2.execute();
						entered = true;
						break;
					} 			
				}
				if(!entered) {
					stmt2.setInt(1, e.geteId());
					stmt2.setInt(2, attenID);
					stmt2.setInt(3, 0);
					stmt2.execute();
				}
			}
			conn.close();		
		}
		catch(Exception e){
			System.out.println("Error in insertAttendance: " + e);
		}
		
		
	}
	

}
