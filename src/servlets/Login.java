/*
* Project: COMP3095_Insert_Team_Name
* Assignment:  Assignment 1
* Author(s): Jeff, Jullian, Roman, Kevin, Andrew
* Student Number: 100872220, 100998164, 100772900, 101015906, 101035265
* Date: Oct 20, 2017
* Description: Servlet that handles login page form.
*/
package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utilities.DatabaseAccess;
import java.sql.*;
/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		// Create a session object if it is already not created.
		HttpSession session = request.getSession(true);
			
		// Declaring Default Values For Session Keys
		String loggedInKey = new String("loggedIn");
		Boolean loggedIn = false;
		
		if (session.isNew() || (request.getParameter("action") != null && request.getParameter("action").equals("logout"))) {
			// if the session is new set loggedIn to false.
			session.setAttribute(loggedInKey, loggedIn);
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
		} else if (session.getAttribute("loggedIn") != null && (Boolean)session.getAttribute("loggedIn").equals(true)) {
			response.sendRedirect(request.getContextPath() + "/home");
		} else {
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		// Create a session object if it is already not created.
		HttpSession session = request.getSession(true);
		
		// Declaring Default Values For Session Keys
		String loggedInKey = new String("loggedIn");
		Boolean loggedIn = false;
		
		// When page is first loaded, check whether the session is new
		if (session.isNew() || (request.getParameter("action") != null && request.getParameter("action").equals("logout"))) {
			// if the session is new set loggedIn to false.
			session.setAttribute(loggedInKey, loggedIn);
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
		} else if (session.getAttribute("loggedIn") != null && (Boolean)session.getAttribute("loggedIn") == false) {
			// otherwise if the session is not new, then this next segment will run for validating the login information
			try {
				// Storing the user's login parameters to send back if login was not successful.
				request.setAttribute("username", request.getParameter("username"));
				request.setAttribute("password", request.getParameter("password"));
				
				// Creating Database Connection
				Connection connect = DatabaseAccess.connectDataBase();
				
				// SELECT query
				String queryString = "SELECT username, password FROM users WHERE username = 'admin' AND password = 'admin'";
				
				Statement stmt = connect.createStatement();
				ResultSet admin = stmt.executeQuery(queryString);
				admin.next();
				
				// Validate username and password
				if (request.getParameter("username").toLowerCase().equals(admin.getString("username"))
					&& request.getParameter("password").equals(admin.getString("password"))) {
					session.setAttribute(loggedInKey, true);
					request.removeAttribute("username");
					request.removeAttribute("password");
					response.sendRedirect(request.getContextPath() + "/home");;
				} else if((request.getParameter("username") != null || request.getParameter("password") != null)
					&& !(request.getParameter("username").isEmpty() || request.getParameter("username").isEmpty())) {
					request.setAttribute("errorMessage", "Invalid Username or Password.");
					request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
				} else {
					request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
				}
				
				connect.close();
				
			} catch (Exception e) {
				System.err.println("Exception: " + e.getMessage());
				request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			}
		}
	}
}