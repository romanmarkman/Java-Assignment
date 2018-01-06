/*
* Project: COMP3095_Insert_Team_Name
* Assignment:  Assignment 2
* Author(s): Jeff, Jullian, Roman, Kevin, Andrew
* Student Number: 100872220, 100998164, 100772900, 101015906, 101035265
* Date: Dec 29 2017
* Description: Servlet that handles Report entry
*/
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
       
    public ReportEntry() {
        super();      
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,String> tempList = ReportHelper.getTemplateList();
		request.setAttribute("templateList", tempList);
		request.getRequestDispatcher("/WEB-INF/jsp/reports/reports_entry.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer tempID;
		Map<String,String> tempList = ReportHelper.getTemplateList();
		request.setAttribute("templateList", tempList);
		//conditional for handling template selection form
		if(request.getParameter("formselect").equals("selectTemplate")) {
						
			tempID = Integer.parseInt(request.getParameter("selectTemplate"));
			getFormData(request,response,tempID);
			
			request.getRequestDispatcher("/WEB-INF/jsp/reports/reports_entry.jsp").forward(request, response);
		}
		//handles enter report form
		if(request.getParameter("formselect").equals("enterReport")) {
			tempID = Integer.parseInt(request.getParameter("templateIDselect"));
			String reportTarget = "";
			//Selects Group selected name or Employee Selected name.
			if(request.getParameter("reportType").equals("group")) {
				reportTarget = request.getParameter("selectGroup");
			}else {
				reportTarget = request.getParameter("selectEmployee");
			}
			//get all form values.
			String reportTitle 					= request.getParameter("reportTitle");
			String reportDate 					= request.getParameter("reportDate");
			String sectionOneCom 				= request.getParameter("section_1_comment");
			String sectionTwoCom 				= request.getParameter("section_2_comment");
			String sectionThreeCom 				= request.getParameter("section_3_comment");
			String[] sectionOneCriteriaIDs 		= request.getParameterValues("section_1_criteria_id");
			String[] sectionTwoCriteriaIDs 		= request.getParameterValues("section_2_criteria_id");
			String[] sectionThreeCriteriaIDs 	= request.getParameterValues("section_3_criteria_id");
			String[] sectionOneCriteriaValues 	= request.getParameterValues("section_1_criteria_value");
			String[] sectionTwoCriteriaValues 	= request.getParameterValues("section_2_criteria_value");
			String[] sectionThreeCriteriaValues = request.getParameterValues("section_3_criteria_value");
			
			Boolean formValid = true;
			//basic textfield checks
			if(reportTitle.trim().equals("") ) {
				formValid = false;
			}
			if(reportDate.trim().equals("") ) {
				formValid = false;
			}
			if(formValid) {
				Integer reportID = ReportHelper.insertReport(tempID, reportTarget, reportTitle,
																reportDate, sectionOneCom, sectionTwoCom, sectionThreeCom);
				ReportHelper.insertCriteriaGrade(reportID, sectionOneCriteriaIDs, sectionOneCriteriaValues);
				ReportHelper.insertCriteriaGrade(reportID, sectionTwoCriteriaIDs, sectionTwoCriteriaValues);
				ReportHelper.insertCriteriaGrade(reportID, sectionThreeCriteriaIDs, sectionThreeCriteriaValues);
				request.setAttribute("confirmMessage", reportTitle + " has been created.");
				request.getRequestDispatcher("/WEB-INF/jsp/reports/reports_entry.jsp").forward(request, response);
			} else {

				getFormData(request,response,tempID);
				request.getRequestDispatcher("/WEB-INF/jsp/reports/reports_entry.jsp").forward(request, response);
			}
		}
				
	}
	//get all form data for postback.
	private void getFormData(HttpServletRequest request, HttpServletResponse response, Integer tempID) {
		ArrayList<String> departments 	= ReportHelper.getTemplateDepartments(tempID);
		ArrayList<String> sectionNames 	= ReportHelper.getSectionNames(tempID);
		ArrayList<String> employeeNames = ReportHelper.getDepartmentEmployees(tempID);
		ArrayList<String> groups 		= ReportHelper.getDepartmentGroups(tempID);
		
		Map<String,String> sectionOneCriteria 	= ReportHelper.getCriteriaNames(tempID, 1);
		Map<String,String> sectionTwoCriteria 	= ReportHelper.getCriteriaNames(tempID, 2);
		Map<String,String> sectionThreeCriteria = ReportHelper.getCriteriaNames(tempID, 3);
		
		request.setAttribute("section1", sectionNames.get(0));
		request.setAttribute("section2", sectionNames.get(1));
		request.setAttribute("section3", sectionNames.get(2));
		request.setAttribute("tempID", tempID);
		
		request.setAttribute("groups", groups);
		request.setAttribute("employees", employeeNames);
		request.setAttribute("departmentList", departments);
		request.setAttribute("sectionOneCrit", sectionOneCriteria);
		request.setAttribute("sectionTwoCrit", sectionTwoCriteria);
		request.setAttribute("sectionThreeCrit", sectionThreeCriteria);
	}
	
	

}
