package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helpers.ReportHelper;
import helpers.ValidationHelper;
import utilities.DatabaseAccess;

/**
 * Servlet implementation class ReportTemplate
 */
@WebServlet("/report_template")
public class ReportTemplate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportTemplate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,String> depList = ReportHelper.getDepartmentList();
		request.setAttribute("departmentList", depList);
		request.getRequestDispatcher("/WEB-INF/jsp/reports/reports_template.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		Map<String,String> depList = ReportHelper.getDepartmentList();
		request.setAttribute("departmentList", depList);
		
		boolean formIsValid = true;
		
		String templateName = request.getParameter("templateName");
		if(!ValidationHelper.isNotNullOrEmpty(templateName)) {
			//request.setAttribute("errorMessageTemplateName", "Section Name cannot be empty");
			formIsValid = false;
		}
		String sectionOne = request.getParameter("sectionOne");
		if(!ValidationHelper.isNotNullOrEmpty(sectionOne)) {
			//request.setAttribute("errorMessageTemplateName", "Section Name cannot be empty");
			formIsValid = false;
		}
		String sectionTwo = request.getParameter("sectionTwo");
		if(!ValidationHelper.isNotNullOrEmpty(sectionTwo)) {
			//request.setAttribute("errorMessageTemplateName", "Section Name cannot be empty");
			formIsValid = false;
		}
		String sectionThree = request.getParameter("sectionThree");
		if(!ValidationHelper.isNotNullOrEmpty(sectionThree)) {
			//request.setAttribute("errorMessageTemplateName", "Section Name cannot be empty");
			formIsValid = false;
		}
		String department = request.getParameter("selectDepartment");
		if (!ValidationHelper.isNotNullOrEmpty(department)) {
			// Set error message letting user know to select a option
			//request.setAttribute("errorMessage7", "Please select a department");
			formIsValid = false;
		}
		if (formIsValid == false) {
			request.getRequestDispatcher("/WEB-INF/jsp/reports/reports_template.jsp").forward(request, response);
			return;
		} else {
			String date = request.getParameter("templateDate");
//		    System.out.println(sectionOne);
			String[] sectionOneCriteriaNames 	= request.getParameterValues("section_1_criteria_name");
			String[] sectionTwoCriteriaNames 	= request.getParameterValues("section_2_criteria_name");
			String[] sectionThreeCriteriaNames 	= request.getParameterValues("section_3_criteria_name");
			Integer sectionOneId 	= ReportHelper.insertSection(sectionOne);	
			Integer sectionTwoId 	= ReportHelper.insertSection(sectionTwo);
			Integer sectionThreeId 	= ReportHelper.insertSection(sectionThree);
//			for(String names : sectionOneCriteriaNames) {
//				System.out.println(names);
//			}
			//String[] sectionOneCriteriaValues = request.getParameterValues("section_1_criteria_value");
			
			ArrayList<Integer> criteriaIds = ReportHelper.insertCriteria(sectionOneCriteriaNames);
//			for(Integer names : criteriaIds) {
//				System.out.println(names);
//			}
			
			ReportHelper.insertSectionCriteria(criteriaIds, sectionOneId);
			criteriaIds = ReportHelper.insertCriteria(sectionTwoCriteriaNames);
			ReportHelper.insertSectionCriteria(criteriaIds, sectionTwoId);
			criteriaIds = ReportHelper.insertCriteria(sectionThreeCriteriaNames);
			ReportHelper.insertSectionCriteria(criteriaIds, sectionThreeId);
			
			ReportHelper.insertTemplate(sectionOneId, sectionTwoId, sectionThreeId, templateName, date, Integer.parseInt(department));
			request.setAttribute("confirmMessage", templateName + " has been created.");
			request.getRequestDispatcher("/WEB-INF/jsp/reports/reports_template.jsp").forward(request, response);	
		}
	}
}
