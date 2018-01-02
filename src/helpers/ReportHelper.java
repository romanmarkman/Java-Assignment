package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import utilities.DatabaseAccess;

public final class ReportHelper {
	
	public static String getCurrentDate() {
		
		String timeStamp = new SimpleDateFormat("MM / dd / yyyy").format(Calendar.getInstance().getTime());
		return timeStamp;
	}
	public static ArrayList<Integer> insertCriteria(String[] criteria){
		ArrayList<Integer> criteriaIds = new ArrayList<>();
		try {
			Connection connect = DatabaseAccess.connectDataBase();
			
			for(String names: criteria) {
				String queryString = "INSERT INTO criteria (criteria_name) VALUES(?)";
				
				PreparedStatement stmt = connect.prepareStatement(queryString,Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, names);
				stmt.execute();
				ResultSet res = stmt.getGeneratedKeys();
				if(res.next()) {
					criteriaIds.add( res.getInt(1));
				}
				res.close();
			}
			connect.close();
		}catch(Exception e) {
			System.out.println("exception in insert section" + e);
		}
		return criteriaIds;					
	}
	
	public static Integer insertSection(String name) {
		Integer id = null;
		try {
			Connection connect = DatabaseAccess.connectDataBase();
	
				String queryString = "INSERT INTO section (section_name) VALUES(?)";

				PreparedStatement stmt = connect.prepareStatement(queryString,Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, name);
				stmt.execute();
				ResultSet res = stmt.getGeneratedKeys();
				
				if(res.next()) {
					id = res.getInt(1);
				}
				
				res.close();
				connect.close();
				
			} catch(Exception e) {
				System.out.println("exception in insert section" + e);
			}
		return id;
	}
	
	public static void insertSectionCriteria(ArrayList<Integer> criteria, Integer section) {
		try {
			Connection connect = DatabaseAccess.connectDataBase();
				for(Integer criteriaId : criteria) {
					String queryString = "INSERT INTO criteria_section (criteria_id,section_id) VALUES(?,?)";
	
					PreparedStatement stmt = connect.prepareStatement(queryString);
					stmt.setInt(1, criteriaId);
					stmt.setInt(2, section);
					stmt.execute();
					
				}
				connect.close();
				
			} catch(Exception e) {
				System.out.println("exception in insert criteriaSec" + e);
			}
	}
	
	public static void insertTemplate(Integer sectionOne, Integer sectionTwo, Integer sectionThree, String tempName, 
										String date, Integer department_id) {
		
		try {
			Connection connect = DatabaseAccess.connectDataBase();
				
			String queryString = "INSERT INTO template (department_id, date, template_name, section_1, section_2, section_3) "
					+ "VALUES(?,NOW(),?,?,?,?)";

			PreparedStatement stmt = connect.prepareStatement(queryString);
			stmt.setInt(1, department_id);
			stmt.setString(2, tempName);
			stmt.setInt(3, sectionOne);
			stmt.setInt(4, sectionTwo);
			stmt.setInt(5, sectionThree);
			stmt.execute();
				
			connect.close();
				
		} catch(Exception e) {
			System.out.println("exception in insert insertTemplate" + e);
		}
		
	}
}

