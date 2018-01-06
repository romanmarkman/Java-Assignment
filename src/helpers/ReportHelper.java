/*
* Project: COMP3095_Insert_Team_Name
* Assignment:  Assignment 2
* Author(s): Jeff, Jullian, Roman, Kevin, Andrew
* Student Number: 100872220, 100998164, 100772900, 101015906, 101035265
* Date: Dec 29 2017
* Description: DB functions for Report pages.
*/
package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import utilities.DatabaseAccess;

public final class ReportHelper {
	
	public static String getCurrentDate() {
		
		String timeStamp = new SimpleDateFormat("MM / dd / yyyy").format(Calendar.getInstance().getTime());
		return timeStamp;
	}
	
	public static void updateCriteriaGrade(Integer reportID, String[] criteria, String[] critID) {
		try {
			Connection connect = DatabaseAccess.connectDataBase();
			
			String query = "UPDATE criteria_grade "
							+ "SET grade = ? "
							+ "WHERE report_id = ? AND criteria_id = ? ";
			Integer count = 0;
			for(String crit : criteria) {
				PreparedStatement stmt = connect.prepareStatement(query);
				stmt.setInt(1, Integer.parseInt(crit));
				stmt.setInt(2, reportID);
				stmt.setInt(3, Integer.parseInt(critID[count]));
				stmt.executeUpdate();
				count++;
			}
		}catch(Exception e) {
			System.out.println("Error in updateCritGrade "+e);
		}
	}
	public static void updateReportComments(Integer reportID, String comment, Integer section) {
		try {
			Connection connect = DatabaseAccess.connectDataBase();
			String  query = "";
			if(section == 1) {
				query = "UPDATE report "
						+ "SET section_1_comment = ? "
						+ "WHERE report_id = ? ";
			} else if (section == 2) {
				query = "UPDATE report"
						+ " SET section_2_comment = ? "
						+ " WHERE report_id = ? ";
			} else if (section == 3) {
				query = "UPDATE report"
						+ " SET section_3_comment = ? "
						+ " WHERE report_id = ? ";
			}
				PreparedStatement stmt = connect.prepareStatement(query);
				stmt.setString(1, comment);
				stmt.setInt(2, reportID);
				stmt.executeUpdate();
				
		}catch(Exception e) {
			//System.out.println("Error in updateComments "+e);
		}
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
			//System.out.println("exception in insert section" + e);
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
				//System.out.println("exception in insert section" + e);
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
				//System.out.println("exception in insert criteriaSec" + e);
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
			//System.out.println("exception in insert insertTemplate" + e);
		}
		
	}
	public static Integer insertReport(Integer tempID, String target ,
										String reportName, String reportDate, 
										String com1, String com2, String com3) {
		 Integer reportID = 0;
		try {
			Connection connect = DatabaseAccess.connectDataBase();
			
			String queryString = "INSERT INTO report (template_id, report_name, target_name, report_date, section_1_comment, section_2_comment, section_3_comment)"					
								+ "VALUES(?,?,?,?,?,?,?)";
			PreparedStatement stmt = connect.prepareStatement(queryString,Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, tempID);
			stmt.setString(2, reportName);
			stmt.setString(3, target);
			stmt.setString(4, reportDate);
			stmt.setString(5, com1);
			stmt.setString(6, com2);
			stmt.setString(7, com3);
			stmt.execute();
			ResultSet res = stmt.getGeneratedKeys();
			
			if(res.next()) {
				reportID = res.getInt(1);
			}
			res.close();
			connect.close();
		}catch(Exception e) {
			//System.out.println("exception in insert insertReport" + e);
		}
		return reportID;
	}
	
	public static void insertCriteriaGrade(Integer reportID, String[] IDs, String[] values) {
		try {
			Connection connect = DatabaseAccess.connectDataBase();
			
			String queryString = "INSERT INTO criteria_grade (report_id, criteria_id, grade) "
								+ "VALUES(?,?,?) ";
			Integer cnt = 0;
			for(String ID : IDs) {
				PreparedStatement stmt = connect.prepareStatement(queryString);
				stmt.setInt(1, reportID);
				stmt.setInt(2, Integer.parseInt(ID));
				stmt.setString(3, values[cnt]);
				stmt.execute();
				cnt++;
			}
			connect.close();
			
		}catch(Exception e) {
			//System.out.println("exception in insert insertCriteriaGrade" + e);
		}
	}
	
	
	public static ArrayList<String> getTemplateDepartments(Integer templateID){
		ArrayList<String> departments = new ArrayList<>();
		try {
			Connection connect = DatabaseAccess.connectDataBase();
				
			String queryString = "SELECT name FROM department"
								+ " INNER JOIN template"
								+ " ON template.department_id = department.department_id"
								+ " WHERE template.template_id = ?";

			PreparedStatement stmt = connect.prepareStatement(queryString);
			stmt.setInt(1,templateID);
			ResultSet departmentNames = stmt.executeQuery();
			if(departmentNames.next()) {
				departments.add(departmentNames.getString(1));
				//System.out.println(departmentNames.getString(1));
			}
			departmentNames.close();
			connect.close();
				
		} catch(Exception e) {
			//System.out.println("exception in insert insertTemplate" + e);
			return null;
		}
		return departments;
	}
	
	public static ArrayList<String> getDepartmentEmployees(Integer tempID){
		ArrayList<String> employees = new ArrayList<String>();
		
		try {
			Connection connect = DatabaseAccess.connectDataBase();
			
			String queryString = "SELECT firstname,lastname FROM employee"								
								+ " INNER JOIN template"
								+ " ON template.department_id = employee.department_id"
								+ " WHERE template.template_id = ? ";
			PreparedStatement stmt = connect.prepareStatement(queryString);
			stmt.setInt(1,tempID);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				//System.out.println("RS feed: "+rs.getString(2) + ", " + rs.getString(1));
				employees.add(rs.getString(2) + ", " + rs.getString(1));
			}
			rs.close();
			connect.close();
		}catch(Exception e) {
			//System.out.println("Error in getDepartmentEmployees: " + e);
			return null;
		}
		return employees;
		
	}
	
	public static ArrayList<String> getDepartmentGroups(Integer tempID){
		ArrayList<String> group = new ArrayList<String>();
		
		try {
			Connection connect = DatabaseAccess.connectDataBase();
			
			String queryString = "SELECT name FROM groups"								
								+ " INNER JOIN template"
								+ " ON template.department_id = groups.department_id"
								+ " WHERE template.template_id = ? ";
			PreparedStatement stmt = connect.prepareStatement(queryString);
			stmt.setInt(1,tempID);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				//System.out.println("RS feed: "+rs.getString(2) + ", " + rs.getString(1));
				group.add(rs.getString(1));
			}
			rs.close();
			connect.close();
		}catch(Exception e) {
			//System.out.println("Error in getDepartmentEmployees: " + e);
			return null;
		}
		return group;
		
	}
	
	public static ArrayList<String> getSectionNames(Integer templateID){
		ArrayList<String> sectionNames = new ArrayList<>();
		ArrayList<Integer> sectionIDs = new ArrayList<>();
		try {
			Connection connect = DatabaseAccess.connectDataBase();
				
			String queryString = "SELECT section_1, section_2, section_3 FROM template"								
								+ " WHERE template.template_id = ?";

			PreparedStatement stmt = connect.prepareStatement(queryString);
			stmt.setInt(1,templateID);
			ResultSet sectionID = stmt.executeQuery();
			if(sectionID.next()) {
				sectionIDs.add(sectionID.getInt(1));
				sectionIDs.add(sectionID.getInt(2));
				sectionIDs.add(sectionID.getInt(3));
			}
			sectionID.close();
			
			String secNames = "SELECT section_name FROM section WHERE section_id = ? ";
			for(Integer ID : sectionIDs) {
				PreparedStatement stmt2 = connect.prepareStatement(secNames);
				stmt2.setInt(1,ID);
				ResultSet sectionN = stmt2.executeQuery();
				if(sectionN.next()) {
					sectionNames.add(sectionN.getString(1));
					//System.out.println(sectionN.getString(1));
				}
				sectionN.close();
			}
			connect.close();
		} catch(Exception e) {
			//System.out.println("exception in insert getSectionNames" + e);
			return null;
		}
		return sectionNames;
	}
	
	public static Map<String,String> getCriteriaNames(Integer tempID, Integer tier){
		Map<String,String> cIDcName = new LinkedHashMap<String,String>();
		ArrayList<Integer> criteriaIDs = new ArrayList<>();
		ArrayList<String> criteriaNames = new ArrayList<>();
		try {
			Connection connect = DatabaseAccess.connectDataBase();
			Integer sectionID = getSectionID(tempID,tier);
			String queryString = "SELECT criteria_id FROM criteria_section"
								+ " WHERE section_id = ? ";
			
			PreparedStatement stmt = connect.prepareStatement(queryString);
			stmt.setInt(1,sectionID);
			ResultSet rsID = stmt.executeQuery();
			while(rsID.next()) {
				//System.out.println("Getting id: " + rsID.getString(1));
				criteriaIDs.add(rsID.getInt(1));
			}
			rsID.close();
			
			String critNamesQuery = "SELECT criteria_name FROM criteria"
									+" WHERE criteria_id = ?";
			for(Integer critID : criteriaIDs) {
				PreparedStatement stmt2 = connect.prepareStatement(critNamesQuery);
				stmt2.setInt(1, critID);
				ResultSet critRsId = stmt2.executeQuery();
				while(critRsId.next()) {
					//System.out.println("Getting name: " +critRsId.getString(1));
					criteriaNames.add(critRsId.getString(1));
				}
				critRsId.close();
			}
			connect.close();
			
		}catch(Exception e) {
			//System.out.println("exception in getCriteriaNames" + e);
			return null;
		} 
		Integer cnt = 0;
		for(String name : criteriaNames) {
			//System.out.println("Setting up Array: " + name + " : " + criteriaIDs.get(cnt).toString());
			cIDcName.put(name, criteriaIDs.get(cnt).toString());
			cnt++;
		}
		return cIDcName;
	}
	
	private static Integer getSectionID(Integer tempID, Integer tier){
		Integer sectionID = 0;
		try {
			Connection connect = DatabaseAccess.connectDataBase();
			String queryString = "";
			if(tier == 1) {	
				queryString = "SELECT section_1 FROM template"								
								+ " WHERE template.template_id = ?";
			}
			if(tier == 2) {
				queryString = "SELECT section_2 FROM template"								
						+ " WHERE template.template_id = ?";
			}
			if(tier == 3) {
				queryString = "SELECT section_3 FROM template"								
						+ " WHERE template.template_id = ?";
			}
			PreparedStatement stmt = connect.prepareStatement(queryString);
			stmt.setInt(1,tempID);
			ResultSet rsID = stmt.executeQuery();
			if(rsID.next()) {
				sectionID=rsID.getInt(1);				
			}
			rsID.close();
			connect.close();
		} catch(Exception e) {
			//System.out.println("exception in getSectionID" + e);
			return null;
		}
		return sectionID;		
	}
	
	public static Map<String,String> getReportTitle(Integer tempID) {
		Map<String,String> reports = new LinkedHashMap<String,String>();
		try {
			Connection connect = DatabaseAccess.connectDataBase();
			
			String queryString = "SELECT report_name, report_id FROM report WHERE template_id = ?";
			PreparedStatement stmt = connect.prepareStatement(queryString);
			stmt.setInt(1, tempID);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				reports.put(Integer.toString(rs.getInt(2)), rs.getString(1));
			}
			rs.close();
			connect.close();
		}catch(Exception e) {
			
		}
		return reports;
	}
	
	public static ArrayList<String> getCritValues(Integer reportID){
		ArrayList<String> values = new ArrayList<>();
		try {
			Connection connect = DatabaseAccess.connectDataBase();
			
			String queryString = "SELECT grade FROM criteria_grade WHERE report_id = ?";
			PreparedStatement stmt = connect.prepareStatement(queryString);
			stmt.setInt(1, reportID);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				values.add(rs.getString(1));
			}
			rs.close();
			connect.close();
		}catch(Exception e) {
			
		}
		return values;
	}
	
	public static ArrayList<String> getComments(Integer reportID){
		ArrayList<String> values = new ArrayList<>();
		try {
			Connection connect = DatabaseAccess.connectDataBase();
			
			String queryString = "SELECT section_1_comment,section_2_comment,section_3_comment FROM report WHERE report_id = ?";
			PreparedStatement stmt = connect.prepareStatement(queryString);
			stmt.setInt(1, reportID);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				values.add(rs.getString(1));
				values.add(rs.getString(2));
				values.add(rs.getString(3));
			}
			rs.close();
			connect.close();
		}catch(Exception e) {
			
		}
		return values;
	}
	
	public static ArrayList<String> getReportDetails(Integer reportID) {
		ArrayList<String> values = new ArrayList<>();
		try {
			Connection connect = DatabaseAccess.connectDataBase();
			
			String queryString = "SELECT template.template_name, report.report_name, report.report_date "
								+ "FROM  report INNER JOIN template ON template.template_id = report.template_id WHERE report_id = ?";
			PreparedStatement stmt = connect.prepareStatement(queryString);
			stmt.setInt(1, reportID);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				values.add(rs.getString(1));
				values.add(rs.getString(2));
				values.add(rs.getString(3));
			}
			rs.close();
			connect.close();
		}catch(Exception e) {
			//System.out.println("exception in getReportDetails" + e);
		}
		return values;
	}
	
	public static Map<String,String> getTemplateList(){
		Map<String,String> tempList = new LinkedHashMap<>();
		try {
			Connection connect = DatabaseAccess.connectDataBase();
			
			String query = "SELECT template_id,template_name FROM template";
			
			PreparedStatement stmt = connect.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				tempList.put(Integer.toString(rs.getInt(1)), rs.getString(2));
			}
			rs.close();
			connect.close();
		}catch(Exception e) {
			
		}
		return tempList;
	}
	
	public static Map<String,String> getDepartmentList(){
		Map<String,String> tempList = new LinkedHashMap<>();
		try {
			Connection connect = DatabaseAccess.connectDataBase();
			
			String query = "SELECT department_id,name FROM department";
			
			PreparedStatement stmt = connect.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {				
				tempList.put(Integer.toString(rs.getInt(1)), rs.getString(2));
			}
			rs.close();
			connect.close();
		}catch(Exception e) {
			
		}
		return tempList;
	}
	
	
	
}

