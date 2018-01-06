<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="utilities.DatabaseAccess"%>
<%@ page import ="java.sql.*" %>
<%@ page import ="javax.sql.*" %>
<%@ page import ="helpers.ValidationHelper" %>
<%@ page import ="java.util.ArrayList" %>
<%@ page import ="objects.Department" %>

<% if (ValidationHelper.isNotNull(session.getAttribute("loggedIn"))
	   && ((Boolean)session.getAttribute("loggedIn")) == true) { %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%! String title = "Department Listing"; %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= title %></title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/employee-page.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css"/>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/header.jsp" />
	
	
	<div id="employee-load-div" class='centered'>
	
	<div class="container">
	<h1>Department Listing</h1>				
	<div class="overflow">	
	<table class="tableData">
		  <tr>
		    <th>Department Name</th>
		    <th>Department Location/Floor</th>
		  </tr>
		
	<%
	if(request.getAttribute("departments") != null){
		ArrayList<Department> dList = (ArrayList<Department>)request.getAttribute("departments");
			for (Department d : dList){
				%>
				<tr>
					<td><%=d.getName() %></td>
					<td><%=d.getLocation() %></td>								
				</tr>
				<%
			}		
		}
	%>
		
	</table>
	<form action="${pageContext.request.contextPath}/home">
    	<input type="submit" value="Home" />
	</form>
	</div>
	</div>	
		  	
	</div>
</body>
</html>
<% } else { 
	   response.sendRedirect(request.getContextPath() + "/login");
   }
%>