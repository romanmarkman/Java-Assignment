/*
* Project: COMP3095_Insert_Team_Name
* Assignment:  Assignment 1
* Author(s): Jeff, Jullian, Roman, Kevin, Andrew
* Student Number: 100872220, 100998164, 100772900, 101015906, 101035265
* Date: Oct 20, 2017
* Description: Servlet that handles login page form.
*/
package servlets;

import helpers.ValidationHelper;
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
		
		// When page is first loaded, check whether the session is new
		if (session.isNew() || (ValidationHelper.isNotNullOrEmpty(request.getParameter("action"))
				&& request.getParameter("action").equals("logout"))) {
			// if the session is new, set loggedIn to false.
			session.setAttribute(loggedInKey, loggedIn);
			session.removeAttribute("loggedInUser");
			
			// Display the login page.
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
		} else if (ValidationHelper.isNotNull(session.getAttribute("loggedIn"))
				&& (Boolean)session.getAttribute("loggedIn") == true) {
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
		if (session.isNew() || (ValidationHelper.isNotNullOrEmpty(request.getParameter("action"))
				&& request.getParameter("action").equals("logout"))) {
			// if the session is new, set loggedIn to false.
			session.setAttribute(loggedInKey, loggedIn);
			if ((ValidationHelper.isNotNullOrEmpty(request.getParameter("action"))
					&& request.getParameter("action").equals("logout")))
				session.removeAttribute("loggedInUser");
			
			// Display the login page.
			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
		} else if (ValidationHelper.isNotNull(session.getAttribute("loggedIn"))
				&& (Boolean)session.getAttribute("loggedIn") == false
				|| ValidationHelper.isNotNull(session.getAttribute("loggedIn"))
				&& (Boolean)session.getAttribute("loggedIn") == true) {
			// otherwise if the session is not new, then this next segment will run for validating the login information
			try {
				// Storing user's login parameters to variables for ease of typing in code.
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				
				// Storing the user's login parameters to send back if login was not successful.
				request.setAttribute("username", username);
				request.setAttribute("password", password);
				
				// Creating Database Connection
				Connection connect = DatabaseAccess.connectDataBase();
				
				// Creating a SELECT query string for finding the user information,
				// and this will determine whether the info is valid or not
				String queryString = "SELECT firstname, username, password FROM users" + " "
								   + "WHERE username = '" + username + "' AND password = '" + password + "'";
				
				Statement stmt = connect.createStatement();
				ResultSet user = stmt.executeQuery(queryString);
				// Move the user result set to the last row, 
				// as there should only be one result.
				user.next();
				
				// Validate username and password by checking if any results
				// showed up via user.getRow(), and if value is 1 it means 
				// it is a success as it should give back only one result
				if (user.getRow() == 1) {
					// Set logged in to true for this session.
					session.setAttribute(loggedInKey, true);
					
					// Set the name of the user that has logged in, if the user has their first name in the database
					// we will store it for display, otherwise we will display their username instead.
					session.setAttribute("loggedInUser", ValidationHelper.isNotNullOrEmpty(user.getString("firstname")) 
														 ? user.getString("firstname")
														 : request.getParameter("username"));
					
					// Remove the login information from the request attributes
					// as this segment signifies a successful login
					request.removeAttribute("username");
					request.removeAttribute("password");
					
					// Redirect the user to the home page of the website.
					response.sendRedirect(request.getContextPath() + "/home");
				} else if (ValidationHelper.isNotNullOrEmpty(username)
						|| ValidationHelper.isNotNullOrEmpty(password)) {
					// Set an error message that either username or password is invalid.
					request.setAttribute("errorMessage", "Invalid Username or Password.");
					
					// Send the user back to the login page.
					request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
				} else {
					// If the user left login info blank, just return them to the login page.
					request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
				}
				// Close Database Connection
				connect.close();
			} catch (Exception e) {
				// Error logging to the console.
				System.err.println("Exception: " + e.getMessage());
				
				// Return the user to the login page as we do not want users seeing exception messages,
				// nor receiving a blank page due to an error which has occurred in code.
				
				request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			}
		}
	}
}