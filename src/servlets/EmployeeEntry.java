/*
* Project: COMP3095_Insert_Team_Name
* Assignment:  Assignment 1
* Author(s): Jeff, Jullian, Roman, Kevin, Andrew
* Student Number: 100872220, 100998164, 100772900, 101015906, 101035265
* Date: Oct 20, 2017
* Description: Servlet that handles Employee entry form.
*/
package servlets;
import helpers.EmployeeHelper;
import helpers.ReportHelper;
import helpers.ValidationHelper;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utilities.DatabaseAccess;
import java.sql.*;
import java.util.Map;

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
		Map<String,String> departments = ReportHelper.getDepartmentList();
		request.setAttribute("departments", departments);
		request.getRequestDispatcher("/WEB-INF/jsp/employee/employee_entry_form.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//	!!Need to make helper method for validation and make form maintain valid inputs!!
		response.setContentType("text/html");
		Map<String,String> departments = ReportHelper.getDepartmentList();
		request.setAttribute("departments", departments);
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
			if(EmployeeHelper.insertEmployee(firstName, lastName, employeeNumber, email, hireYr, jobPos, department)) {
				request.setAttribute("confirmMessage", firstName + " " + lastName + " has been added to the database.");
				// Remove attributes to reset form
				request.removeAttribute("validFN");
				request.removeAttribute("validLN");
				request.removeAttribute("validEN");
				request.removeAttribute("validEmail");
			}
			request.getRequestDispatcher("/WEB-INF/jsp/employee/employee_entry_form.jsp").forward(request, response);
			return;	
		}
	}
}