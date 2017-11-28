/*
* Project: COMP3095_Insert_Team_Name
* Assignment:  Assignment 1
* Author(s): Jeff, Jullian, Roman, Kevin, Andrew
* Student Number: 100872220, 100998164, 100772900, 101015906, 101035265
* Date: Oct 20, 2017
* Description: Servlet that handles department entry form.
*/
package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helpers.ValidationHelper;
import utilities.DatabaseAccess;

/**
 * Servlet implementation class DepartmentEntry
 */
@WebServlet("/department_entry")
public class DepartmentEntry extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DepartmentEntry() {
        super();
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.getRequestDispatcher("/WEB-INF/jsp/department/department_entry_form.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		boolean formIsValid = true;
		
		String departmentName = request.getParameter("departmentName");
		if(!ValidationHelper.isNotNullOrEmpty(departmentName)) {
			request.setAttribute("errorMessage", "Name field cannot be empty");
			formIsValid = false;
		}
		else if(!ValidationHelper.isAlphabetic(departmentName)) {
			request.setAttribute("errorMessage", "Department name must contain only letters");	
			formIsValid = false;
		}
		else {
			//Store the valid departmentName value and send back if form is invalid
			request.setAttribute("validDepartmentName", departmentName);
		}
		
		String locationName = request.getParameter("locationName");
		if(!ValidationHelper.isNotNullOrEmpty(locationName)) {
			request.setAttribute("errorMessage2", "Location field cannot be empty");
			formIsValid = false;
		}
		else if(!ValidationHelper.isAlphanumeric(locationName)) {
			request.setAttribute("errorMessage2", "Location name must be alphanumeric");	
			formIsValid = false;
		}
		else {
			//Store the valid lastName and send back if form is invalid
			request.setAttribute("validLocation", locationName);
		}
		
		//Form is invalid send back to employeeEntryPage with error messages.
		if(formIsValid == false) {
			request.getRequestDispatcher("/WEB-INF/jsp/department/department_entry_form.jsp").forward(request, response);
			return;
		}
		//Form is valid -> CONNECT and INSERT into Database and confirm notice
		//	!!Need a way to check if employee already exists since id is auto incremented!!
		else {
			//Connect to mysql database
			try {
						
			java.sql.Connection connect = DatabaseAccess.connectDataBase();
						
			//INSERT query
			String queryString = "INSERT INTO department (name, location) VALUES "
					+ "(?, ?)";
						
			//Preparing insert statement using preparedStatement
			java.sql.PreparedStatement preparedStmt = connect.prepareStatement(queryString);
			preparedStmt.setString(1, departmentName.trim());
			preparedStmt.setString(2, locationName.trim());

						
			//Pass confirmMessage to page and load dialog if not null
			int rowsInserted = preparedStmt.executeUpdate();
			if(rowsInserted > 0) {
				request.setAttribute("confirmMessage", departmentName + " has been added to the database.");
				//Remove attributes to reset form
				request.removeAttribute("validDepartmentName");
				request.removeAttribute("validLocation");
			}
			connect.close();
			
			request.getRequestDispatcher("/WEB-INF/jsp/department/department_entry_form.jsp").forward(request, response);
			return;	
			}
			catch (Exception e)
			{
			 	System.err.println("Exception: " + e.getMessage());
			}
		}
	}

}