<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="utilities.DatabaseAccess"%>
<%@ page import ="java.sql.*" %>
<%@ page import ="javax.sql.*" %>
<%@ page import ="helpers.ValidationHelper" %>
<%@ page import ="java.util.ArrayList" %>
<%@ page import ="java.util.LinkedHashMap" %>
<%@ page import ="java.util.Map" %>
<%@ page import ="objects.Employee" %>

<% if (ValidationHelper.isNotNull(session.getAttribute("loggedIn"))
	   && ((Boolean)session.getAttribute("loggedIn")) == true) { %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%! String title = "Employee Listing"; %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= title %></title>
<script type="text/javascript" src="${pageContext.request.contextPath}/jQuery/jquery-3.2.0.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/employee-page.css"/>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/header.jsp" />
	
	<%
		Map<String,String> departments = new LinkedHashMap<>();
		if(request.getAttribute("departments") != null){
			departments = (Map<String,String>)request.getAttribute("departments");
		}
	%>
	
	<div id="employee-load-div" class='centered container'>
	
	<form action = "employee_view" method = "POST" name = "employeeView" id = "eView" >
	<h1>Employee Listing</h1>
		<select name="department">
			<option selected disabled hidden>Department</option>
				<%for(Map.Entry<String,String> entry : departments.entrySet()){	%>
				<option value="<%=entry.getKey()%>"><%=entry.getValue() %></option>
				<%} %>
				
		</select>
		<br><div class="errorMsg">${errorMessage8}</div>
		<br>
	<input type="submit" value="Search">
	
	<hr>
	<div class="overflow">	
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
	</div>	
	</form>
	<form action="${pageContext.request.contextPath}/home">
    	<input type="submit" value="Home" />
	</form>
	</div>
</body>
</html>
<% } else { 
	   response.sendRedirect(request.getContextPath() + "/login");
   }
%>