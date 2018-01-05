package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
@WebServlet("/group_entry")
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
		request.getRequestDispatcher("/WEB-INF/jsp/group/group_entry_form.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			request.getRequestDispatcher("/WEB-INF/jsp/group/group_entry_form.jsp").forward(request, response);
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
			else if(!ValidationHelper.isAlphanumeric(group_name)) {
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
			String employee[] = request.getParameterValues("employee");
			if(!ValidationHelper.isNotNullOrEmpty(employee[0])) {
				request.setAttribute("employeeError", "Please include at least one employee into the group");
				formIsValid = false;
			}
			else if(ValidationHelper.isNotNullOrEmpty(employee[0])){
				int count = 0;
				for(int i=0; i<employee_ids.length; i++){
					for(int j=i+1; j<employee_ids.length; j++){
						if(employee_ids[i].equals(employee_ids[j])){
							request.setAttribute("dubError", "Error: Duplicated Employee Detected");
							request.getRequestDispatcher("/WEB-INF/jsp/group/group_entry_form.jsp").forward(request, response);
							return;
						}
					}
				}
			}
			
		}
		String group_name = request.getParameter("groupName");
		//If form is invalid
		if(formIsValid == false) {
			request.getRequestDispatcher("/WEB-INF/jsp/group/group_entry_form.jsp").forward(request, response);
			return;
		}
		
		else { //Form is valid set department_id to selected and postback
			if(session.getAttribute("department_id") == null){
				session.setAttribute("department_id", department_id);
				request.getRequestDispatcher("/WEB-INF/jsp/group/group_entry_form.jsp").forward(request, response);
			}
			
			else{ //Form is valid and able to submit
				try{
					//Insert into groups table
					//Fetch data	
					department_id = (String)session.getAttribute("department_id");
					
					//Connect to the DB
					Connection connect = DatabaseAccess.connectDataBase();
					Integer groupID = 0;
					Integer rowsInserted1 = 0;
					Integer rowsInserted2 = 0;
					String queryString = "INSERT INTO groups (name, department_id) VALUES "
							+ "(?, ?)";
					
					PreparedStatement pStatement = connect.prepareStatement(queryString,Statement.RETURN_GENERATED_KEYS);
					
					pStatement.setString(1, group_name.trim());
					pStatement.setString(2, department_id);
					pStatement.execute();
					ResultSet res = pStatement.getGeneratedKeys();
					if(res.next()) {
						groupID = res.getInt(1);
						rowsInserted1++;
					}
					res.close();	
										
					String message = "";
					if(rowsInserted1 > 0) {
						message = group_name + " has been added to the database.";
					}
									
					//Insert into employee_group table
					//Compare group name with existing groupname
					
					//queryString = "SELECT group_id FROM groups WHERE name = " + group_name;
					queryString = "SELECT * FROM groups WHERE name = '" + group_name + "'";
					Statement st = connect.createStatement();
					
					ResultSet resSet = st.executeQuery(queryString);
					String group_id = "";
					if(resSet.next()) {
						group_id = resSet.getString(1);
					}
					queryString = "INSERT INTO employee_group (employee_id, group_id) VALUES"
								+ "(?, ?)";
					pStatement = connect.prepareStatement(queryString);
					for(int i=0; i<employee_ids.length; i++){
						pStatement.setInt(1, Integer.parseInt(employee_ids[i]));
						pStatement.setInt(2, groupID);
						pStatement.execute();
						rowsInserted2++;
					}
					//statement = connect.createStatement();
					
					
					if(rowsInserted2 > 0) {
						request.setAttribute("confirmMessage", message + " \n\nEmployee group has been added to the database.");
						request.removeAttribute("validGN");
					}
						
					connect.close();
					request.getRequestDispatcher("/WEB-INF/jsp/group/group_entry_form.jsp").forward(request, response);
				}
				catch(Exception e){
					System.err.println("Something unexpected happened...");
					System.err.println(e.getMessage());
				}
			}
		}
	}
}