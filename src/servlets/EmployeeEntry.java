/*
* Project: COMP3095_Insert_Team_Name
* Assignment:  Assignment 1
* Author(s): Jeff, Jullian, Roman, Kevin, Andrew
* Student Number: 100872220, 100998164, 100772900, 101015906, 101035265
* Date: Oct 20, 2017
* Description: Servlet that handles Employee entry form.
*/
package servlets;
import helpers.ValidationHelper;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utilities.DatabaseAccess;
import java.sql.*;

/**
 * Servlet implementation class EmployeeEntryServlet
 */
@WebServlet("/employee_entry")
public class EmployeeEntry extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeEntry() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//	!!Need to make helper method for validation and make form maintain valid inputs!!
		response.setContentType("text/html");
		boolean formIsValid = true;
		
		// Getting the employee first name input
		String firstName = request.getParameter("firstName");
		if(!ValidationHelper.isNotNullOrEmpty(firstName)) {
			request.setAttribute("errorMessage", "First name field cannot be empty");
			formIsValid = false;
		} else if(!ValidationHelper.isAlphabetic(firstName)) {
			request.setAttribute("errorMessage", "First name must contain only letters");	
			formIsValid = false;
		} else {
			//Store the valid firstName value and send back if form is invalid
			request.setAttribute("validFN", firstName);
		}
		
		// Getting the employee last name input
		String lastName = request.getParameter("lastName");
		if(!ValidationHelper.isNotNullOrEmpty(lastName)) {
			request.setAttribute("errorMessage2", "Last name field cannot be empty");
			formIsValid = false;
		} else if(!ValidationHelper.isAlphabetic(lastName)) {
			request.setAttribute("errorMessage2", "Last name must contain only letters");	
			formIsValid = false;
		} else {
			//Store the valid lastName and send back if form is invalid
			request.setAttribute("validLN", lastName);
		}
		
		// Getting the employee number input
		String employeeNumber = request.getParameter("employeeNumber");
		if(!ValidationHelper.isNotNullOrEmpty(employeeNumber)) {
			request.setAttribute("errorMessage3", "Employee Number cannot be empty");
			formIsValid = false;
		} else if ((!ValidationHelper.isInteger(employeeNumber)) || (employeeNumber.length() != 6)) {
			// Employee number must be exactly 6 digits?
			request.setAttribute("errorMessage3", "Employee Number must be 6 integers long");
			formIsValid = false;
		} else {
			//Store the valid employeeNumber and send back if form is invalid
			request.setAttribute("validEN", employeeNumber);
		}
		
		// Getting the employee email input
		String email = request.getParameter("email");
		if (!ValidationHelper.isNotNullOrEmpty(email)) {
			request.setAttribute("errorMessage4", "Email field cannot be empty");
			formIsValid = false;
		} else if (!ValidationHelper.isEmail(email)) {
			request.setAttribute("errorMessage4", "Invalid email");
			formIsValid = false;
		} else {
			//Store the valid email and send back if form is invalid
			request.setAttribute("validEmail", email);
		}
		
		// Getting the employee hire year input
		// Store the selected option and send back if form is invalid
		String hireYr = request.getParameter("hireYear");
		if (!ValidationHelper.isNotNullOrEmpty(hireYr)) {
			request.setAttribute("errorMessage5", "Please select a hire year");
			formIsValid = false;
		}
		
		// Getting the employee job position input
		String jobPos = request.getParameter("jobPosition");
		if (!ValidationHelper.isNotNullOrEmpty(jobPos)) {
			//Set error message letting user know to select a option
			request.setAttribute("errorMessage6", "Please select a job position");
			formIsValid = false;
		}
		
		// Getting the employee department
		String department = request.getParameter("department");
		if (!ValidationHelper.isNotNullOrEmpty(department)) {
			// Set error message letting user know to select a option
			request.setAttribute("errorMessage7", "Please select a department");
			formIsValid = false;
		}
		
		//Form is invalid send back to employeeEntryPage with error messages.
		if (formIsValid == false) {
			request.getRequestDispatcher("/WEB-INF/jsp/employee/employee_entry_form.jsp").forward(request, response);
			return;
		} else {
			// Connect to MySQL database
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
				
				// Change selected department name to its corresponding id.
				Statement statement = connect.createStatement();
				ResultSet idResult = statement.executeQuery("Select department_id from department where name = '" + department + "'");
				if(idResult.next()) {
					int departmentId = idResult.getInt("department_id");
					stmt.setInt(7, departmentId);
				}
				
				// Pass confirmMessage to page and load dialog if not null
				int rowsInserted = stmt.executeUpdate();
				if(rowsInserted > 0) {
					request.setAttribute("confirmMessage", firstName + " " + lastName + " has been added to the database.");
					// Remove attributes to reset form
					request.removeAttribute("validFN");
					request.removeAttribute("validLN");
					request.removeAttribute("validEN");
					request.removeAttribute("validEmail");
				}
				connect.close();
				
				request.getRequestDispatcher("/WEB-INF/jsp/employee/employee_entry_form.jsp").forward(request, response);
				return;	
			} catch (Exception e) {
				System.err.println("Exception: " + e.getMessage());
			}
		}
	}
}
