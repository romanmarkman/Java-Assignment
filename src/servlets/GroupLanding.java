/*
* Project: COMP3095_Insert_Team_Name
* Assignment:  Assignment 1
* Author(s): Jeff, Jullian, Roman, Kevin, Andrew
* Student Number: 100872220, 100998164, 100772900, 101015906, 101035265
* Date: Oct 20, 2017
* Description: Servlet that handles group landing.
* 
*/
package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GroupLanding
 */
@WebServlet("/group")
public class GroupLanding extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupLanding() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/group/group_landing_page.jsp").forward(request, response);
	}

}
