<%@ page import="helpers.ValidationHelper"%>
<%@ page import="utilities.DatabaseAccess"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import ="java.sql.*" %>
<%@ page import ="javax.sql.*" %>
<%@ page import ="java.util.Map" %>
<%@ page import ="java.util.LinkedHashMap" %>
<%@ page import ="java.util.ArrayList" %>
<%@ page import ="objects.Employee" %>
<%@ page import="helpers.ReportHelper" %>

<% if (ValidationHelper.isNotNull(session.getAttribute("loggedIn"))
	   && ((Boolean)session.getAttribute("loggedIn")) == true) { %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%! String title = "Attendance Entry"; %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/modal.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/employee-page.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= title %></title>
</head>
<body>
	<%
		Integer depID = 0;
		String date = ReportHelper.getCurrentDate();
		Map<String, String> departments = new LinkedHashMap<>();
		if(request.getAttribute("departments") != null){
			departments = (Map<String, String>)request.getAttribute("departments");
		}
		if(request.getAttribute("depID") != null){
			depID = (Integer)request.getAttribute("depID");
		}
	%>
<jsp:include page="/WEB-INF/jsp/header.jsp" />
	<div class="container">
	<h1 class="text-align-center">Attendance Entry</h1>
		<form action="attendance_entry" method="POST" name="departmentS" id="dSelect">
		<input type="hidden" name="formselect" value="selectDepartment">
		<select name="department">
			<option selected disabled hidden>Select Department</option>
				<%for(Map.Entry<String,String> entry : departments.entrySet()){%>
			<option value="<%=entry.getKey()%>"><%=entry.getValue() %></option>
			<%} %>
		</select>
		<br><div class="errorMsg">${errorMessage}</div><br>
		<input type="submit" value="Search">
		
		<hr>
		</form> 
			
		<form action="attendance_entry" method="POST" name="attendance">
		<input type="hidden" name="formselect" value="enterAttendance">
		<input type="hidden" name="departmentID" value="<%=depID%>">
		<span>Date:</span><input type="date" name="reportDate" placeholder="<%= date %>"><br>
		<br><div class="errorMsg">${invalidDate}</div><br>
		<div class="overflow">
			<table>
			<tr>
			    <th>Lastname</th>
			    <th>Firstname</th>
			    <th>Employee #</th>
			    <th>Present</th>
			 </tr>
			 
			 	<%
					ArrayList<Employee> eList = (ArrayList<Employee>)request.getAttribute("employeeL");
					if(request.getAttribute("employeeL")!=null){
						for (Employee e : eList){
							%>
							<tr>
								<td><%=e.getlName() %></td>
								<td><%=e.getfName() %></td>
								<td><%=e.getEmployeeNum() %></td>
								<td><input type="checkbox" name="present" value="<%=e.geteId()%>"></td>
							</tr>
							<%
						}
						
					};
				%>
			</table>
			<br><div class="errorMsg">${noBueno}</div><br>
			<br><br><input type="submit" value="Submit">
		</div>
		</form>
						<%
					String message = (String)request.getAttribute("confirmMessage"); 
					if(message != null){
				%>
					<!-- Modal from https://www.w3schools.com/howto/howto_css_modals.asp -->
						<div id="dialogBox" class ="dialog">
							<div class="dialogContent centered">
								<div class="dialogHeader">
									<span class="close">&times;</span>
									<h3>Attendance Submitted</h3>
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
		
</div>
</body>
</html>
<% } else { 
	   response.sendRedirect(request.getContextPath() + "/login");
   }
%>