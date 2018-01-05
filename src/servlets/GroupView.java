package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helpers.GroupHelper;
import objects.Department;
import objects.GroupMember;

/**
 * Servlet implementation class GroupView
 */
@WebServlet("/group_view")
public class GroupView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupView() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//ArrayList<GroupMember> members = GroupHelper.getGroupMembers(12);
		Map<String,String> departments = GroupHelper.getDepartments();
		request.setAttribute("departments", departments);
		request.getRequestDispatcher("/WEB-INF/jsp/group/group_view.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,String> departments = GroupHelper.getDepartments();
		Integer depID = 0;
		request.setAttribute("departments", departments);
		
		if(request.getParameter("formselect").equals("selectDepartment")) {
			depID = Integer.parseInt(request.getParameter("selectDepartment"));
			Map<String,String> groups = GroupHelper.getDepartmentGroups(depID);
			request.setAttribute("groups", groups);
			request.setAttribute("depID", depID);
			request.getRequestDispatcher("/WEB-INF/jsp/group/group_view.jsp").forward(request, response);			
		}
		if(request.getParameter("formselect").equals("selectGroup")) {
			depID = Integer.parseInt(request.getParameter("departmentIDselect"));
			if(request.getParameter("selectGroupForm") == null) {
				request.getRequestDispatcher("/WEB-INF/jsp/group/group_view.jsp").forward(request, response);
			}
			Integer gID = Integer.parseInt(request.getParameter("selectGroupForm"));
			ArrayList<GroupMember> members = GroupHelper.getGroupMembers(gID);
			request.setAttribute("members", members);
			request.setAttribute("depID", depID);
			request.getRequestDispatcher("/WEB-INF/jsp/group/group_view.jsp").forward(request, response);
		}
		
	}

}
