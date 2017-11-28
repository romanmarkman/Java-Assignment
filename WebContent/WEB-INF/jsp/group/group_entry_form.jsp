<%@ page import="helpers.ValidationHelper"%>
<%@ page import="utilities.DatabaseAccess"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import ="java.sql.*" %>
<%@ page import ="javax.sql.*" %>
<% if (ValidationHelper.isNotNull(session.getAttribute("loggedIn"))
	   && ((Boolean)session.getAttribute("loggedIn")) == true) { %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%! String title = "Group Entry"; %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/group-page.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/modal.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= title %></title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/header.jsp" />
	<%  //If null in session or the page was loaded for the first time
		if(session.getAttribute("department_id") != null && !"POST".equalsIgnoreCase(request.getMethod()))
			session.removeAttribute( "department_id" );
				
		HttpSession sess = request.getSession(true);
		
		//Conn to DB
		DatabaseAccess db = new DatabaseAccess();
		java.sql.Connection connect = DatabaseAccess.connectDataBase();
		Statement statement = connect.createStatement();
		ResultSet resultSet = statement.executeQuery("Select department_id, name from department");
		
		String department_id = (String)session.getAttribute("department_id");
	%>
	<div id="group-div" class="centered">
		<form name="GroupSelection" action="group_entry" method="GET" name="GroupEntry" id="groupForm" class="container">
			<h1>Group Entry</h1>
			<%//Check for department_id session value %>
			<%if(department_id == null) { //Check for department_id session value %>
			<label for="department">Department: </label><select id="department" name="department">
							<option selected disabled value=none>Select</option>
						<% while(resultSet.next()){ %>
							<option value=<%=resultSet.getString(1)%>><%= resultSet.getString(2) %></option>
						<%} %>	
						</select>
						<br><div class="errorMsg">${departmentError}</div><br>
			<% //If department is selected
				} else { %>
			
			<%//Confirmation Message %>
				<%
					String message = (String)request.getAttribute("confirmMessage"); 
					if(message != null){
				%>
					<!-- Modal from https://www.w3schools.com/howto/howto_css_modals.asp -->
						<div id="dialogBox" class ="dialog">
							<div class="dialogContent centered">
								<div class="dialogHeader">
									<span class="close">&times;</span>
									<h3>New Employee Added</h3>
								</div>
								<div class="dialogBody">
									<p>${confirmMessage}</p>
								</div>
							</div>
						</div>
					<%
						}
					%>
			<script>
			dialog = document.getElementById("dialogBox");
			var span = document.getElementsByClassName("close")[0];
			
			// When the user clicks on <span> (x), close the modal
			span.onclick = function() {
			    dialog.style.display = "none";
			}
			// When the user clicks anywhere outside of the modal, close it
			window.onclick = function(event) {
			    if (event.target == modal) {
			        dialog.style.display = "none";
			    }
			}
			</script>
			<%//Get department name from dep_id %>
			<% resultSet = statement.executeQuery("Select name from department where department_id = " + department_id);
			   resultSet.next();%>
			<label>Department: </label><select disabled>
							<option selected value=${sessionScope.department_id}><%=resultSet.getString(1) %></option>
						</select><br><br>
			
			<%//Get first and last names of all employees with the same department_id %>
			<% resultSet = statement.executeQuery("Select employee_id, lastname, firstname from employee where department_id = " + department_id); %>
			
			Group Name: <input type="text" name="groupName" value="${validGN}"/><br>
			<div class="errorMsg">${groupError}</div><br>
			
			<label>Employee 1: </label><select id="emp1" name="employee">
							<option selected disabled>Select</option>
						<% 
						while(resultSet.next()){ %>
							<option value=<%= resultSet.getString(1) %>><%= resultSet.getString(2) + ", " + resultSet.getString(3) %></option>
						<%} %>	
						</select>
			<label>Employee 2: </label><select id="emp2" name="employee">
							<option selected disabled>Select</option>
						<%	resultSet.beforeFirst(); 
						while(resultSet.next()){ %>
							<option value=<%= resultSet.getString(1) %>><%= resultSet.getString(2) + ", " + resultSet.getString(3) %></option>
						<%} %>	
						</select>
			<label>Employee 3: </label><select id="emp3" name="employee">
							<option selected disabled>Select</option>
						<%	resultSet.beforeFirst(); 
						while(resultSet.next()){ %>
							<option value=<%= resultSet.getString(1) %>><%= resultSet.getString(2) + ", " + resultSet.getString(3) %></option>
						<%} %>	
						</select><br><br>
			<label>Employee 4: </label><select id="emp4" name="employee">
							<option selected disabled>Select</option>
						<%	resultSet.beforeFirst(); 
						while(resultSet.next()){ %>
							<option value=<%= resultSet.getString(1) %>><%= resultSet.getString(2) + ", " + resultSet.getString(3) %></option>
						<%} %>	
						</select>
			<label>Employee 5: </label><select id="emp5" name="employee">
							<option selected disabled>Select</option>
						<%	resultSet.beforeFirst(); 
						while(resultSet.next()){ %>
							<option value=<%= resultSet.getString(1) %>><%= resultSet.getString(2) + ", " + resultSet.getString(3) %></option>
						<%} %>	
						</select>
			<label>Employee 6: </label><select id="emp6" name="employee">
							<option selected disabled>Select</option>
						<%	resultSet.beforeFirst(); 
						while(resultSet.next()){ %>
							<option value=<%= resultSet.getString(1) %>><%= resultSet.getString(2) + ", " + resultSet.getString(3) %></option>
						<%} //End of else%>	
						</select><br>
			<br><div class="errorMsg">${employeeError}</div>
			<br><div class="errorMsg">${dubError}</div><br>
			
			<% } %>
			<input type="submit" name="depSelection" value="Proceed">
			<input type="submit" name="Reset" value="Cancel">	
		</form>
	</div>
</body>
</html>
<% } else { 
	   response.sendRedirect(request.getContextPath() + "/login");
   }
%>