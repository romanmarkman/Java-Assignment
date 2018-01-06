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
		if(request.getParameter("formselect").equals("selectDepartment")) {
			String departmentId = request.getParameter("department");
			 depID = Integer.parseInt(request.getParameter("department"));
			
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
				request.setAttribute("depID", depID);
				request.getRequestDispatcher("/WEB-INF/jsp/employee-attendance/attendance_entry.jsp").forward(request, response);
			}
		}
		if(request.getParameter("reportDate") != null){
			formIsValid = ValidationHelper.dateValidation(request.getParameter("reportDate"));
			if(formIsValid == false){
				request.setAttribute("invalidDate", "This is not a valid date, please try again");
				request.getRequestDispatcher("/WEB-INF/jsp/employee-attendance/attendance_entry.jsp").forward(request, response);
			}
		}
		
		if (formIsValid == true){
			if(request.getParameter("formselect").equals("enterAttendance")) {
				
				if(request.getParameter("present") != null){
					String departmentId = request.getParameter("departmentID");
					String date = request.getParameter("reportDate");
					ArrayList<Employee> employee = EmployeeHelper.getEmployees(departmentId);
					depID = Integer.parseInt(request.getParameter("departmentID"));
					String[] present = request.getParameterValues("present");
					AttendanceHelper.insertAttendance(present, employee, depID, date);
					request.setAttribute("employeeL", employee);
					request.setAttribute("depID", depID);
					request.setAttribute("confirmMessage", " Attendance sheet has been submitted.");
					request.getRequestDispatcher("/WEB-INF/jsp/employee-attendance/attendance_entry.jsp").forward(request, response);
				}
				else
				{
					request.setAttribute("noBueno", "No employees were selected, please check off at least one employee");
					request.getRequestDispatcher("/WEB-INF/jsp/employee-attendance/attendance_entry.jsp").forward(request, response);
				}
			}
		}
		

	}

}
