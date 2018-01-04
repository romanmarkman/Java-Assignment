package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helpers.ReportHelper;
import helpers.ValidationHelper;

/**
 * Servlet implementation class ReportsView
 */
@WebServlet("/report_view")
public class ReportsView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsView() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/reports/reports_view.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer tempID = 0;
		Integer reportID = 0;
		
		
		if(request.getParameter("formselect").equals("selectTemplate")) {
			tempID = Integer.parseInt(request.getParameter("selectTemplate"));
			Map<String,String> reports = ReportHelper.getReportTitle(tempID);
			ArrayList<String> departments 	= ReportHelper.getTemplateDepartments(tempID);
			request.setAttribute("reports", reports);
			request.setAttribute("departmentList", departments);
			request.setAttribute("tempID", tempID);
			
			request.getRequestDispatcher("/WEB-INF/jsp/reports/reports_view.jsp").forward(request, response);
			
		}
		if(request.getParameter("formselect").equals("selectReport")) {
			if(request.getParameter("reports") == null) {
				request.getRequestDispatcher("/WEB-INF/jsp/reports/reports_view.jsp").forward(request, response);
			}
			tempID = Integer.parseInt(request.getParameter("templateIDselect"));
			reportID = Integer.parseInt(request.getParameter("reports"));
			getFormData(request,response,tempID,reportID);
			
			
			request.getRequestDispatcher("/WEB-INF/jsp/reports/reports_view.jsp").forward(request, response);
		}
		
	}
	private void getFormData(HttpServletRequest request, HttpServletResponse response, Integer tempID, Integer reportID) {
		
		ArrayList<String> comments = ReportHelper.getComments(reportID);
		ArrayList<String> critValues	= ReportHelper.getCritValues(reportID);
		ArrayList<String> departments 	= ReportHelper.getTemplateDepartments(tempID);
		ArrayList<String> sectionNames 	= ReportHelper.getSectionNames(tempID);		
		ArrayList<String> reportDetails = ReportHelper.getReportDetails(reportID);
		Map<String,String> reports 				= ReportHelper.getReportTitle(tempID);
		Map<String,String> sectionOneCriteria 	= ReportHelper.getCriteriaNames(tempID, 1);
		Map<String,String> sectionTwoCriteria 	= ReportHelper.getCriteriaNames(tempID, 2);
		Map<String,String> sectionThreeCriteria = ReportHelper.getCriteriaNames(tempID, 3);
		
		request.setAttribute("reportDetails", reportDetails);
		request.setAttribute("comment1", comments.get(0));
		request.setAttribute("comment2", comments.get(1));
		request.setAttribute("comment3", comments.get(2));
		request.setAttribute("section1", sectionNames.get(0));
		request.setAttribute("section2", sectionNames.get(1));
		request.setAttribute("section3", sectionNames.get(2));
		request.setAttribute("tempID", tempID);		
		request.setAttribute("reports", reports);
		request.setAttribute("critValues", critValues);
		request.setAttribute("departmentList", departments);
		request.setAttribute("sectionOneCrit", sectionOneCriteria);
		request.setAttribute("sectionTwoCrit", sectionTwoCriteria);
		request.setAttribute("sectionThreeCrit", sectionThreeCriteria);
	}

}
