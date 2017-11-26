package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import HelperClasses.EmployeeEntryHelper;
import utilities.DatabaseAccess;
import java.sql.*;
import java.sql.*;

/**
 * Servlet implementation class EmployeeEntryServlet
 */
@WebServlet("/EmployeeEntryServlet")
public class EmployeeEntryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeEntryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//	!!Need to make helper method for validation and make form maintain valid inputs!!
		response.setContentType("text/html");
		boolean formIsValid = true;
		
		String firstName = request.getParameter("firstName");
		if(!EmployeeEntryHelper.isNotNullOrEmpty(firstName)) {
			request.setAttribute("errorMessage", "First name field cannot be empty");
			formIsValid = false;
		}
		else if(!EmployeeEntryHelper.isAlphabetic(firstName)) {
			request.setAttribute("errorMessage", "First name must contain only letters");	
			formIsValid = false;
		}
		else {
			//Store the valid firstName value and send back if form is invalid
			request.setAttribute("validFN", firstName);
		}
		String lastName = request.getParameter("lastName");
		if(!EmployeeEntryHelper.isNotNullOrEmpty(lastName)) {
			request.setAttribute("errorMessage2", "Last name field cannot be empty");
			formIsValid = false;
		}
		else if(!EmployeeEntryHelper.isAlphabetic(lastName)) {
			request.setAttribute("errorMessage2", "Last name must contain only letters");	
			formIsValid = false;
		}
		else {
			//Store the valid lastName and send back if form is invalid
			request.setAttribute("validLN", lastName);
		}
		//Employee number must be exactly 6 digits?
		String employeeNumber = request.getParameter("employeeNumber");
		if(!EmployeeEntryHelper.isNotNullOrEmpty(employeeNumber)) {
			request.setAttribute("errorMessage3", "Employee Number cannot be empty");
			formIsValid = false;
		}
		else if((!EmployeeEntryHelper.isInteger(employeeNumber)) || (employeeNumber.length() != 6)) {
			request.setAttribute("errorMessage3", "Employee Number must be 6 integers long");
			formIsValid = false;
		}
		else {
			//Store the valid employeeNumber and send back if form is invalid
			request.setAttribute("validEN", employeeNumber);
		}
		String email = request.getParameter("email");
		if(!EmployeeEntryHelper.isNotNullOrEmpty(email)) {
			request.setAttribute("errorMessage4", "Email field cannot be empty");
			formIsValid = false;
		}
		else if(!EmployeeEntryHelper.isEmail(email)) {
			request.setAttribute("errorMessage4", "Invalid email");
			formIsValid = false;
		}
		else {
			//Store the valid email and send back if form is invalid
			request.setAttribute("validEmail", email);
		}
		String hireYr = request.getParameter("hireYear");
		if(!EmployeeEntryHelper.isNotNullOrEmpty(hireYr)) {
			request.setAttribute("errorMessage5", "Please select a hire year");
			formIsValid = false;
		}
		//Store the selected option and send back if form is invalid
		else {
			
		}
		
		String jobPos = request.getParameter("jobPosition");
		if(!EmployeeEntryHelper.isNotNullOrEmpty(jobPos)) {
			//Set error message letting user know to select a option
			request.setAttribute("errorMessage6", "Please select a job position");
			formIsValid = false;
		}
		String department = request.getParameter("department");
		if(!EmployeeEntryHelper.isNotNullOrEmpty(department)) {
			//Set error message letting user know to select a option
			request.setAttribute("errorMessage7", "Please select a department");
			formIsValid = false;
		}
		
		//Form is invalid send back to employeeEntryPage with error messages.
		if(formIsValid == false) {
			request.getRequestDispatcher("/EmployeeEntryPage.jsp").forward(request, response);
			return;
		}
		
		//Form is valid -> CONNECT and INSERT into Database and confirm notice
		//	!!Need a way to check if employee already exists since id is auto incremented!!
			else {
				//Connect to mysql database
				try {
				
				java.sql.Connection connect = DatabaseAccess.connectDataBase();
				
				//INSERT query
				String queryString = "INSERT INTO employee (firstname, lastname, employee_number, email, hire_date, job_position, department_id) VALUES "
						+ "(?, ?, ?, ?, ?, ?, ?)";
				
				//Preparing insert statement using preparedStatement
				java.sql.PreparedStatement preparedStmt = connect.prepareStatement(queryString);
				preparedStmt.setString(1, firstName);
				preparedStmt.setString(2, lastName);
				preparedStmt.setString(3, employeeNumber);
				preparedStmt.setString(4, email);
				
				
				//Set hireDate in database to 1st day of january for whatever year is selected
				preparedStmt.setDate(5, java.sql.Date.valueOf(hireYr + "-01-01"));
				preparedStmt.setString(6, jobPos);
				
				//Change selected department name to its corresponding id.
				Statement statement = connect.createStatement();
				ResultSet idResult = statement.executeQuery("Select department_id from department where name = '" + department + "'");
				if(idResult.next()) {
					int departmentId = idResult.getInt("department_id");
					preparedStmt.setInt(7, departmentId);
				}
				
				//Pass confirmMessage to page and load dialog if not null
				int rowsInserted = preparedStmt.executeUpdate();
				if(rowsInserted > 0) {
					request.setAttribute("confirmMessage", firstName + " " + lastName + " has been added to the database.");
					//Remove attributes to reset form
					request.removeAttribute("validFN");
					request.removeAttribute("validLN");
					request.removeAttribute("validEN");
					request.removeAttribute("validEmail");
				}
				connect.close();
				
				request.getRequestDispatcher("/EmployeeEntryPage.jsp").forward(request, response);
				return;	
			}
			 catch (Exception e)
				{
				 	System.err.println("Got an exception!");
				 	System.err.println(e.getMessage());
				}
			}
		
			
		
	}

}
