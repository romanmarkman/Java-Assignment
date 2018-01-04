<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="utilities.DatabaseAccess"%>
<%@ page import ="java.sql.*" %>
<%@ page import ="javax.sql.*" %>
<%@ page import ="helpers.ValidationHelper" %>
<%@ page import ="java.util.ArrayList" %>
<%@ page import ="objects.Employee" %>

<% if (ValidationHelper.isNotNull(session.getAttribute("loggedIn"))
	   && ((Boolean)session.getAttribute("loggedIn")) == true) { %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%! String title = "Employee Listing"; %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= title %></title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/employee-page.css">
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/header.jsp" />
	
	<%
		DatabaseAccess db = new DatabaseAccess();
		java.sql.Connection connect = DatabaseAccess.connectDataBase();
		Statement statement = connect.createStatement();
		ResultSet resultSet = statement.executeQuery("Select name from department");
	%>
	
	<div id="employee-load-div" class='centered'>
	<h1>Employee Listing</h1>
	<form action = "employee_view" method = "POST" name = "employeeView" id = "eView" class = "container">
		<select name="department">
			<option selected disabled hidden>Department</option>
				<% while(resultSet.next()){ %>
					<option><%= resultSet.getString(1) %></option>
				<%} %>	
		</select>
		<br><div class="errorMsg">${errorMessage8}</div>
		<br>
		
		
	<table>
		  <tr>
		    <th>Employee #</th>
		    <th>Firstname</th>
		    <th>Lastname</th>
		    <th>Hire Date</th>
		    <th>Email</th>
		    <th>Job Position</th>
		  </tr>
		
	<%
		ArrayList<Employee> eList = (ArrayList<Employee>)request.getAttribute("employeeL");
		if(request.getAttribute("employeeL")!=null){
			for (Employee e : eList){
				%>
				<tr>
					<td><%=e.getEmployeeNum() %></td>
					<td><%=e.getfName() %></td>
					<td><%=e.getlName() %></td>
					<td><%=e.getHireDate() %></td>
					<td><%=e.getEmail() %></td>
					<td><%=e.getJobPos() %></td>
					
				</tr>
				<%
			}
			
		};
	%>
		
	</table>	
		  
		  
		
		<input type="submit" value="Submit">
		<input type="reset"  value="Cancel">
	</form>
	</div>
</body>
</html>
<% } else { 
	   response.sendRedirect(request.getContextPath() + "/login");
   }
%>