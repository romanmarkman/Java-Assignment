package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.ReportHelper;

/**
 * Servlet implementation class ReportEntry
 */
@WebServlet("/report_entry")
public class ReportEntry extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportEntry() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/reports/reports_entry.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("formselect").equals("selectTemplate")) {
			HttpSession session = request.getSession();
			
			Integer tempID = Integer.parseInt(request.getParameter("selectTemplate"));
			ArrayList<String> departments = ReportHelper.getTemplateDepartments(tempID);
			ArrayList<String> sectionNames = ReportHelper.getSectionNames(tempID);
			ArrayList<String> employeeNames = ReportHelper.getDepartmentEmployees(tempID);
			ArrayList<String> groups = ReportHelper.getDepartmentGroups(tempID);
			
			Map<String,String> sectionOneCriteria = ReportHelper.getCriteriaNames(tempID, 1);
			Map<String,String> sectionTwoCriteria = ReportHelper.getCriteriaNames(tempID, 2);
			Map<String,String> sectionThreeCriteria = ReportHelper.getCriteriaNames(tempID, 3);
			
			request.setAttribute("section1", sectionNames.get(0));
			request.setAttribute("section2", sectionNames.get(1));
			request.setAttribute("section3", sectionNames.get(2));
			
			session.setAttribute("groups", groups);
			session.setAttribute("employees", employeeNames);
			session.setAttribute("departmentList", departments);
			session.setAttribute("sectionOneCrit", sectionOneCriteria);
			session.setAttribute("sectionTwoCrit", sectionTwoCriteria);
			session.setAttribute("sectionThreeCrit", sectionThreeCriteria);
			
			request.getRequestDispatcher("/WEB-INF/jsp/reports/reports_entry.jsp").forward(request, response);
		}
	}

}
