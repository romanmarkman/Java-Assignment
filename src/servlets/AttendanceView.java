package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helpers.AttendanceHelper;
import helpers.EmployeeHelper;
import helpers.ReportHelper;
import objects.Employee;

/**
 * Servlet implementation class AttendanceView
 */
@WebServlet("/attendance_view")
public class AttendanceView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AttendanceView() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,String> departments = ReportHelper.getDepartmentList();
		request.setAttribute("departments", departments);
		request.getRequestDispatcher("/WEB-INF/jsp/employee-attendance/attendance_view.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,String> departments = ReportHelper.getDepartmentList();
		request.setAttribute("departments", departments);
		
		String departmentId = request.getParameter("department");
		Integer depID = Integer.parseInt(request.getParameter("department"));
		ArrayList<Employee> employee = EmployeeHelper.getEmployees(departmentId);
		ArrayList<String> attenDates = AttendanceHelper.getAttendanceDates(depID);
		ArrayList<ArrayList<String>> attendanceCheck = AttendanceHelper.getEmployeeAttendance(depID, employee);
		request.setAttribute("attenCheck", attendanceCheck);
		request.setAttribute("attenDates", attenDates);
		request.setAttribute("employeeL", employee);
		request.getRequestDispatcher("/WEB-INF/jsp/employee-attendance/attendance_view.jsp").forward(request, response);
	}

}
