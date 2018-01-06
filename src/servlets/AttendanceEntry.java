package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helpers.AttendanceHelper;
import helpers.ValidationHelper;

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
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		
		Integer tempID = 0;
		
		Map<String,String> deptList = AttendanceHelper.getDepartmentList();
		request.setAttribute("departmentList", deptList);
		request.getRequestDispatcher("/WEB-INF/jsp/employee-attendance/attendance_entry.jsp").forward(request, response);
	}

}
