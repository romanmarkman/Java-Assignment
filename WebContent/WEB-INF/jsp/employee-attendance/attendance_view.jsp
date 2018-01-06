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
<%! String title = "Attendance Entry"; %>
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
	
	<form action = "attendance_view" method = "POST" name = "attendanceView" id = "eView" >
	<h1>Attendance Listing</h1>
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
	<div class="overflow-full">	
	<table>
		  <tr>
		    <th>Employee Lastname</th>
		    <th>Employee Firstname</th>
		    <th>Employee #</th>
		    <%
				ArrayList<String> dates = new ArrayList<String>();
				if(request.getAttribute("attenDates")!=null){
					dates = (ArrayList<String>)request.getAttribute("attenDates");
					for (String date : dates){
						%>
				   	<th><%=date %></th>
				   	<%
					}	
				};
			%>
		  </tr>
		
	<%
		ArrayList<ArrayList<String>> attenChecks = new ArrayList<ArrayList<String>>();
		if(request.getAttribute("attenCheck")!=null){
			attenChecks = (ArrayList<ArrayList<String>>)request.getAttribute("attenCheck");
		}
		ArrayList<Employee> eList = (ArrayList<Employee>)request.getAttribute("employeeL");
		if(request.getAttribute("employeeL")!=null){
			Integer counter = 0;
			for (Employee e : eList){
				ArrayList<String> check = attenChecks.get(counter);
				%>
				<tr>
					<td><%=e.getlName() %></td>
					<td><%=e.getfName() %></td>
					<td><%=e.getEmployeeNum() %></td>
					<% for(String c : check){%>
					<td><input type="checkbox" disabled <%if (c.equals("1")){ %>checked<%} %>></td>
					<% } counter++;%>
					
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