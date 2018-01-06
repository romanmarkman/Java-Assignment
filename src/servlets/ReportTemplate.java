/*
* Project: COMP3095_Insert_Team_Name
* Assignment:  Assignment 2
* Author(s): Jeff, Jullian, Roman, Kevin, Andrew
* Student Number: 100872220, 100998164, 100772900, 101015906, 101035265
* Date: Dec 29 2017
* Description: Servlet that handles login page form.
*/
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
		//get department list and set it to request for postback
		Map<String,String> depList = ReportHelper.getDepartmentList();
		request.setAttribute("departmentList", depList);
		
		boolean formIsValid = true;
		//form validations		
		String templateName = request.getParameter("templateName");
		if(!ValidationHelper.isNotNullOrEmpty(templateName)) {
			request.setAttribute("errTemplateName", "Template Name cannot be empty");
			formIsValid = false;
		}
		String sectionOne = request.getParameter("sectionOne");
		if(!ValidationHelper.isNotNullOrEmpty(sectionOne)) {
			request.setAttribute("errSectionOne", "Section Name cannot be empty");
			formIsValid = false;
		}
		String sectionTwo = request.getParameter("sectionTwo");
		if(!ValidationHelper.isNotNullOrEmpty(sectionTwo)) {
			request.setAttribute("errSectionTwo", "Section Name cannot be empty");
			formIsValid = false;
		}
		String sectionThree = request.getParameter("sectionThree");
		if(!ValidationHelper.isNotNullOrEmpty(sectionThree)) {
			request.setAttribute("errSectionThree", "Section Name cannot be empty");
			formIsValid = false;
		}
		String department = request.getParameter("selectDepartment");
		if (!ValidationHelper.isNotNullOrEmpty(department)) {			
			request.setAttribute("errDepartSelect", "Please select a department");
			formIsValid = false;
		}
		if (formIsValid == false) {
			request.getRequestDispatcher("/WEB-INF/jsp/reports/reports_template.jsp").forward(request, response);
			return;
		} else {
			//Save to Database.
			String date = request.getParameter("templateDate");
			//Get criteria names
			String[] sectionOneCriteriaNames 	= request.getParameterValues("section_1_criteria_name");
			String[] sectionTwoCriteriaNames 	= request.getParameterValues("section_2_criteria_name");
			String[] sectionThreeCriteriaNames 	= request.getParameterValues("section_3_criteria_name");
			//Insert section, returns generated ID
			Integer sectionOneId 	= ReportHelper.insertSection(sectionOne);	
			Integer sectionTwoId 	= ReportHelper.insertSection(sectionTwo);
			Integer sectionThreeId 	= ReportHelper.insertSection(sectionThree);
			//Insert criteria names, get back generated Keys.
			ArrayList<Integer> criteriaIds = ReportHelper.insertCriteria(sectionOneCriteriaNames);
			
			//Insert to composite table. Repeat for sections  2 and 3
			ReportHelper.insertSectionCriteria(criteriaIds, sectionOneId);
			criteriaIds = ReportHelper.insertCriteria(sectionTwoCriteriaNames);
			ReportHelper.insertSectionCriteria(criteriaIds, sectionTwoId);
			criteriaIds = ReportHelper.insertCriteria(sectionThreeCriteriaNames);
			ReportHelper.insertSectionCriteria(criteriaIds, sectionThreeId);
			//Insert template.
			ReportHelper.insertTemplate(sectionOneId, sectionTwoId, sectionThreeId, templateName, date, Integer.parseInt(department));
			request.setAttribute("confirmMessage", templateName + " has been created.");
			request.getRequestDispatcher("/WEB-INF/jsp/reports/reports_template.jsp").forward(request, response);	
		}
	}
}
