package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helpers.AttendanceHelper;
import helpers.EmployeeHelper;
import helpers.ReportHelper;
import helpers.ValidationHelper;
import objects.Employee;

import java.util.ArrayList;
import java.util.Map;

/**
 * Servlet implementation class AttendanceEntry
 */
@WebServlet("/attendance_entry")
public class AttendanceEntry extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AttendanceEntry() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Map<String,String> departments = ReportHelper.getDepartmentList();
		request.setAttribute("departments", departments);
		request.getRequestDispatcher("/WEB-INF/jsp/employee-attendance/attendance_entry.jsp").forward(request, response);
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
			request.setAttribute("errorMessage", "Please select a department");
			formIsValid = false;
		}	
		
		if (formIsValid == false) {
			request.getRequestDispatcher("/WEB-INF/jsp/employee-attendance/attendance_entry.jsp").forward(request, response);
			return;
		} else {
			
			// Connect to MySQL database
			ArrayList<Employee> employee = EmployeeHelper.getEmployees(departmentId);
			request.setAttribute("employeeL", employee);
			request.getRequestDispatcher("/WEB-INF/jsp/employee-attendance/attendance_entry.jsp").forward(request, response);
		}

	}

}
