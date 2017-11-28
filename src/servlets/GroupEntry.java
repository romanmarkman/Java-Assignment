package servlets;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import helpers.ValidationHelper;
import utilities.DatabaseAccess;
/**
 * Servlet implementation class GroupEntryServlet
 */
@WebServlet("/GroupEntryServlet")
public class GroupEntry extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupEntry() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
			boolean formIsValid = true;
			HttpSession session = request.getSession(true); //Session Request
			
			String [] employee_ids = request.getParameterValues("employee");
			
			String department_id = (String)request.getParameter("department");
			//Check is department is empty
			if(session.getAttribute("department_id") == null){
				if(!ValidationHelper.isNotNullOrEmpty(department_id)) {
					//Set error message letting user know to select a option
					request.setAttribute("departmentError", "Please select a department");
					formIsValid = false;
				}
			}
			
			//On Reset button click set department_id to null and postback
			if(request.getParameter("Reset") != null){
				session.setAttribute("department_id", null);
				request.getRequestDispatcher("/GroupEntryPage.jsp").forward(request, response);
				return;
				
			}
			
			//If department is selected
			if(session.getAttribute("department_id") != null){
				//Validate groupName
				//DB connection to check for group name matches
				
				String group_name = request.getParameter("groupName");
				if(!ValidationHelper.isNotNullOrEmpty(group_name)) {
					request.setAttribute("groupError", "Group Name field cannot be empty");
					formIsValid = false;
				}
				else if(!ValidationHelper.isAlphabeticAndNumerical(group_name)) {
					request.setAttribute("groupError", "Group Name must only contain letters and numbers");	
					formIsValid = false;
				}
				else if(!ValidationHelper.length(request.getParameter("groupName").length())){
					request.setAttribute("groupError", "Group Name cannot exceed 30 characters");
					formIsValid = false;
				}
				//If group name exists in the DB
				else if(!ValidationHelper.compare(group_name)){
					request.setAttribute("groupError", "Group Name already exists");
					formIsValid = false;
				}
				
				else {
					//Store Valid GroupName value if the form is invalid
					request.setAttribute("validGN", group_name);
				}
				
				//Check for at least one employee selected
				String employee = request.getParameter("employee");
				if(!ValidationHelper.isNotNullOrEmpty(employee)) {
					request.setAttribute("employeeError", "Please include at least one employee into the group");
					formIsValid = false;
				}
				else if(ValidationHelper.isNotNullOrEmpty(employee)){
					int count = 0;
					for(int i=0; i<employee_ids.length; i++){
						for(int j=i+1; j<employee_ids.length; j++){
							if(employee_ids[i].equals(employee_ids[j])){
								request.setAttribute("dubError", "Error: Dublicated Employee detected");
								request.getRequestDispatcher("/GroupEntryPage.jsp").forward(request, response);
								return;
							}
						}
					}
				}
				
			}
			String group_name = request.getParameter("groupName");
			//If form is invalid
			if(formIsValid == false) {
				request.getRequestDispatcher("/GroupEntryPage.jsp").forward(request, response);		
				return;
			}
			
			else { //Form is valid set department_id to selected and postback
				if(session.getAttribute("department_id") == null){
					session.setAttribute("department_id", department_id);
					request.getRequestDispatcher("/GroupEntryPage.jsp").forward(request, response);
				}
				
				else{ //Form is valid and able to submit
					try{
						//Insert into groups table
						//Fetch data	
						department_id = (String)session.getAttribute("department_id");
						
						//Connect to the DB
						java.sql.Connection connect = DatabaseAccess.connectDataBase();
						
						String queryString = "INSERT INTO groups (name, department_id) VALUES "
								+ "(?, ?)";
						
						java.sql.PreparedStatement pStatement = connect.prepareStatement(queryString);
						
						pStatement.setString(1, group_name.trim());
						pStatement.setString(2, department_id);

						Statement statement = connect.createStatement();
						int rowsInserted = pStatement.executeUpdate();
						
						String message = "";
						if(rowsInserted > 0) {
							message = group_name + " has been added to the database.";
						}
						
						connect.close();
						
						//Insert into employee_group table
						//Compare group name with existing groupname
						connect = DatabaseAccess.connectDataBase();
						//queryString = "SELECT group_id FROM groups WHERE name = " + group_name;
						queryString = "SELECT * FROM groups WHERE name = '" + group_name + "'";
						Statement st = connect.createStatement();
						
						ResultSet resSet = st.executeQuery(queryString);
						resSet.next();
						String group_id = resSet.getString(1);
						
						queryString = "INSERT INTO employee_group (employee_id, group_id) VALUES"
									+ "(?, ?)";
						pStatement = connect.prepareStatement(queryString);
						for(int i=0; i<employee_ids.length; i++){
							pStatement.setString(1, employee_ids[i]);
							pStatement.setString(2, group_id);
						}
						statement = connect.createStatement();
						rowsInserted = pStatement.executeUpdate();
						
						if(rowsInserted > 0) {
							request.setAttribute("confirmMessage", message + " \n\nEmployee group has been added to the database.");
							request.removeAttribute("validGN");
						}
							
						connect.close();
						request.getRequestDispatcher("/GroupEntryPage.jsp").forward(request, response);
					}
					catch(Exception e){
						System.err.println("Something unexpected happened...");
						System.err.println(e.getMessage());
					}
				}
			}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
