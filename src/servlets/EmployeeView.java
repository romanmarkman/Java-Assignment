/*
* Project: COMP3095_Insert_Team_Name
* Assignment:  Assignment 2
* Author(s): Jeff, Jullian, Roman, Kevin, Andrew
* Student Number: 100872220, 100998164, 100772900, 101015906, 101035265
* Date: Dec 29 2017
* Description: Servlet that handles Employee view
*/
package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helpers.EmployeeHelper;
import helpers.ReportHelper;
import helpers.ValidationHelper;
import utilities.DatabaseAccess;
import objects.Employee;

/**
 * Servlet implementation class EmployeeView
 */
@WebServlet("/employee_view")
public class EmployeeView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeView() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,String> departments = ReportHelper.getDepartmentList();
		request.setAttribute("departments", departments);
		request.getRequestDispatcher("/WEB-INF/jsp/employee/employeeView.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		Map<String,String> departments = ReportHelper.getDepartmentList();
		request.setAttribute("departments", departments);
		Integer depID = 0;
		boolean formIsValid = true;
		String departmentId = request.getParameter("department");
		if (!ValidationHelper.isNotNullOrEmpty(departmentId)) {
			// Set error message letting user know to select a option
			request.setAttribute("errorMessage8", "Please select a department");
			formIsValid = false;
		}	
	
		if (formIsValid == false) {
			request.getRequestDispatcher("/WEB-INF/jsp/employee/employeeView.jsp").forward(request, response);
			return;
		} else {
			
			// Connect to MySQL database
			ArrayList<Employee> employee = EmployeeHelper.getEmployees(departmentId);
			request.setAttribute("employeeL", employee);
			request.getRequestDispatcher("/WEB-INF/jsp/employee/employeeView.jsp").forward(request, response);
		}
	}
}