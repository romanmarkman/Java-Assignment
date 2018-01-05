package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helpers.ValidationHelper;
import utilities.DatabaseAccess;
import objects.Employee;

/**
 * Servlet implementation class EmployeeView
 */
@WebServlet("/employee_view")
public class EmployeeView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeView() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/WEB-INF/jsp/employee/employeeView.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		Integer depID = 0;
		boolean formIsValid = true;
		String department = request.getParameter("department");
		if (!ValidationHelper.isNotNullOrEmpty(department)) {
			// Set error message letting user know to select a option
			request.setAttribute("errorMessage8", "Please select a department");
			formIsValid = false;
		}
		int departmentId = 0;
		
	
	
		if (formIsValid == false) {
			request.getRequestDispatcher("/WEB-INF/jsp/employee/employeeView.jsp").forward(request, response);
			return;
		} else {
			
			// Connect to MySQL database
			try {
				Connection connect = DatabaseAccess.connectDataBase();
				// Change selected department name to its corresponding id.
				
				String queryString1 = "SELECT department_id FROM department WHERE name = ?";
				PreparedStatement stmt = connect.prepareStatement(queryString1);
				stmt.setString(1, department );
				ResultSet idResult = stmt.executeQuery();
				if(idResult.next()) {
					departmentId = idResult.getInt("department_id");
				}
				idResult.close();
				//Retrieve query
				String queryString2 = "SELECT * FROM employee INNER JOIN department ON department.department_id = employee.department_id"
										+ " WHERE employee.department_id = ? ";
				PreparedStatement stmt2 = connect.prepareStatement(queryString2);
				stmt2.setInt(1, departmentId);
				ResultSet rs = stmt2.executeQuery();
				ArrayList<Employee> employeeList = new ArrayList<>();
				while(rs.next()) {
					
					Employee employee = new Employee(rs.getString("firstname"), rs.getString("lastname"),rs.getInt("employee_number"),rs.getDate("hire_date"),
							rs.getString("email"), rs.getString("job_position"));
					employeeList.add(employee);
				}
				rs.close();
				request.setAttribute("employeeL", employeeList);
				
				connect.close();
				
				request.getRequestDispatcher("/WEB-INF/jsp/employee/employeeView.jsp").forward(request, response);
				return;	
			} catch (Exception e) {
				System.err.println("Exception: " + e.getMessage());
			}
		}
	}
}