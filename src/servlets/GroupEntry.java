/*
* Project: COMP3095_Insert_Team_Name
* Assignment:  Assignment 2
* Author(s): Jeff, Jullian, Roman, Kevin, Andrew
* Student Number: 100872220, 100998164, 100772900, 101015906, 101035265
* Date: Dec 29 2017
* Description: Servlet that handles Group creation
*/
package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.GroupHelper;
import helpers.ReportHelper;
import helpers.ValidationHelper;
import utilities.DatabaseAccess;
/**
 * Servlet implementation class GroupEntryServlet
 */
@WebServlet("/group_entry")
public class GroupEntry extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupEntry() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,String> departments = ReportHelper.getDepartmentList();
		request.setAttribute("departments", departments);
		request.getRequestDispatcher("/WEB-INF/jsp/group/group_entry_form.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		Map<String,String> departments = ReportHelper.getDepartmentList();
		request.setAttribute("departments", departments);
		boolean formIsValid = true;
		Integer depID = 0;
		System.out.println(request.getParameter("formselect"));
		
		if(request.getParameter("formselect").equals("departmentSelect")) {
			
			depID = Integer.parseInt(request.getParameter("department"));
			Map<String,String> employees = GroupHelper.getDepartmentEmployees(depID);
			request.setAttribute("employees", employees);
			request.setAttribute("depID", depID);
			request.getRequestDispatcher("/WEB-INF/jsp/group/group_entry_form.jsp").forward(request, response);
		}
		if( request.getParameter("formselect").equals("groupSelect")) {
			
			depID = Integer.parseInt(request.getParameter("departmentID"));
			
			Map<String,String> employees = GroupHelper.getDepartmentEmployees(depID);
			request.setAttribute("employees", employees);
			request.setAttribute("depID", depID);
			
			String[] employee_ids = request.getParameterValues("employee");			
			if(employee_ids == null) {
				request.setAttribute("employeeError", "Please include at least one employee into the group");
				formIsValid = false;
			} else if(ValidationHelper.isNotNullOrEmpty(employee_ids[0])){
				int count = 0;
				for(int i=0; i<employee_ids.length; i++){
					for(int j=i+1; j<employee_ids.length; j++){
						if(employee_ids[i].equals(employee_ids[j])){
							request.setAttribute("dubError", "Error: Duplicated Employee Detected");
							
							formIsValid = false;
						}
					}
				}
			}
			
			
			String group_name = request.getParameter("groupName");
			if(!ValidationHelper.isNotNullOrEmpty(group_name)) {
				request.setAttribute("groupError", "Group Name field cannot be empty");
				formIsValid = false;
			}
			else if(!ValidationHelper.isAlphanumeric(group_name)) {
				request.setAttribute("groupError", "Group Name must only contain letters and numbers");	
				formIsValid = false;
			}
			else if(!ValidationHelper.length(request.getParameter("groupName").length())){
				request.setAttribute("groupError", "Group Name cannot exceed 30 characters");
				formIsValid = false;
			}
			//If group name exists in the DB
			else if(!ValidationHelper.compare(group_name)){
				request.setAttribute("groupError", "Group Name already exists");
				formIsValid = false;
			} else {
				//Store Valid GroupName value if the form is invalid
				request.setAttribute("validGN", group_name);
			}
			
			if(formIsValid == false) {
				request.getRequestDispatcher("/WEB-INF/jsp/group/group_entry_form.jsp").forward(request, response);
				return;
			} else {
				GroupHelper.insertGroup(group_name, depID, employee_ids);
				String message = group_name + " has been added to the database.";
				request.setAttribute("confirmMessage", message + " \n\nEmployee group has been added to the database.");
				request.removeAttribute("validGN");
				request.getRequestDispatcher("/WEB-INF/jsp/group/group_entry_form.jsp").forward(request, response);
			}
			
		} 
		
		
		
		
//		String department_id = (String)request.getParameter("department");
//		//Check is department is empty
//		if(request.getAttribute("department_id") == null){
//			if(!ValidationHelper.isNotNullOrEmpty(department_id)) {
//				//Set error message letting user know to select a option
//				request.setAttribute("departmentError", "Please select a department");
//				formIsValid = false;
//			}
//		}
		
		//On Reset button click set department_id to null and postback
		
		
		//If department is selected
		//if(request.getAttribute("department_id") != null){
			//Validate groupName
			//DB connection to check for group name matches
			
//			String group_name = request.getParameter("groupName");
//			if(!ValidationHelper.isNotNullOrEmpty(group_name)) {
//				request.setAttribute("groupError", "Group Name field cannot be empty");
//				formIsValid = false;
//			}
//			else if(!ValidationHelper.isAlphanumeric(group_name)) {
//				request.setAttribute("groupError", "Group Name must only contain letters and numbers");	
//				formIsValid = false;
//			}
//			else if(!ValidationHelper.length(request.getParameter("groupName").length())){
//				request.setAttribute("groupError", "Group Name cannot exceed 30 characters");
//				formIsValid = false;
//			}
//			//If group name exists in the DB
//			else if(!ValidationHelper.compare(group_name)){
//				request.setAttribute("groupError", "Group Name already exists");
//				formIsValid = false;
//			}
//			
//			else {
//				//Store Valid GroupName value if the form is invalid
//				request.setAttribute("validGN", group_name);
//			}
			
			//Check for at least one employee selected	
	}
}