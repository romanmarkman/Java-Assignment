package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
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
}
