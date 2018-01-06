/*
* Project: COMP3095_Insert_Team_Name
* Assignment:  Assignment 2
* Author(s): Jeff, Jullian, Roman, Kevin, Andrew
* Student Number: 100872220, 100998164, 100772900, 101015906, 101035265
* Date: Dec 29 2017
* Description: Servlet that handles Viewing reports
*/
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
		Map<String,String> tempList = ReportHelper.getTemplateList();
		request.setAttribute("templateList", tempList);
		request.getRequestDispatcher("/WEB-INF/jsp/reports/reports_view.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer tempID = 0;
		Integer reportID = 0;
		Map<String,String> tempList = ReportHelper.getTemplateList();
		request.setAttribute("templateList", tempList);
		//If switch for the forms. This one handles template selection	
		if(request.getParameter("formselect").equals("selectTemplate")) {
			tempID = Integer.parseInt(request.getParameter("selectTemplate"));
			Map<String,String> reports = ReportHelper.getReportTitle(tempID);
			ArrayList<String> departments 	= ReportHelper.getTemplateDepartments(tempID);
			request.setAttribute("reports", reports);
			request.setAttribute("departmentList", departments);
			request.setAttribute("tempID", tempID);
			
			request.getRequestDispatcher("/WEB-INF/jsp/reports/reports_view.jsp").forward(request, response);
			
		}
		//handles report selection form.
		if(request.getParameter("formselect").equals("selectReport")) {
			if(request.getParameter("reports") == null) {
				request.getRequestDispatcher("/WEB-INF/jsp/reports/reports_view.jsp").forward(request, response);
			}
			tempID = Integer.parseInt(request.getParameter("templateIDselect"));
			reportID = Integer.parseInt(request.getParameter("reports"));
			getFormData(request,response,tempID,reportID);
			request.setAttribute("reportID", reportID);
			
			request.getRequestDispatcher("/WEB-INF/jsp/reports/reports_view.jsp").forward(request, response);
		}
		//handles update form.
		if(request.getParameter("formselect").equals("updateForm")) {
			reportID = Integer.parseInt(request.getParameter("reportID"));
			tempID = Integer.parseInt(request.getParameter("templateIDselect"));
			//Get comments and criteria grades.
			String sectionOneCom 				= request.getParameter("section_1_comment");
			String sectionTwoCom 				= request.getParameter("section_2_comment");
			String sectionThreeCom 				= request.getParameter("section_3_comment");
			String[] sectionOneCriteriaIDs 		= request.getParameterValues("section_1_criteria_id");
			String[] sectionTwoCriteriaIDs 		= request.getParameterValues("section_2_criteria_id");
			String[] sectionThreeCriteriaIDs 	= request.getParameterValues("section_3_criteria_id");
			String[] sectionOneCriteriaValues 	= request.getParameterValues("section_1_criteria_value");
			String[] sectionTwoCriteriaValues 	= request.getParameterValues("section_2_criteria_value");
			String[] sectionThreeCriteriaValues = request.getParameterValues("section_3_criteria_value");
			//get report details for this report.
			ArrayList<String> reportDetails = ReportHelper.getReportDetails(reportID);
			//update all fields.
			ReportHelper.updateCriteriaGrade(reportID, sectionOneCriteriaValues, sectionOneCriteriaIDs);
			ReportHelper.updateCriteriaGrade(reportID, sectionTwoCriteriaValues, sectionTwoCriteriaIDs);
			ReportHelper.updateCriteriaGrade(reportID, sectionThreeCriteriaValues, sectionThreeCriteriaIDs);
			ReportHelper.updateReportComments(reportID, sectionOneCom, 1);
			ReportHelper.updateReportComments(reportID, sectionTwoCom, 2);
			ReportHelper.updateReportComments(reportID, sectionThreeCom, 3);
			//get current form data for postback.
			getFormData(request,response,tempID,reportID);
			request.setAttribute("reportID", reportID);
			request.setAttribute("confirmMessage", reportDetails.get(1) + " has been successfully updated." );
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
