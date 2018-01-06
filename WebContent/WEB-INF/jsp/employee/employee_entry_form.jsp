<%@ page import="utilities.DatabaseAccess"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import ="java.sql.*" %>
<%@ page import ="javax.sql.*" %>
<%@ page import ="helpers.ValidationHelper" %>
<%@ page import ="java.util.LinkedHashMap" %>
<%@ page import ="java.util.Map" %>
<% if (ValidationHelper.isNotNull(session.getAttribute("loggedIn"))
	   && ((Boolean)session.getAttribute("loggedIn")) == true) { %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%! String title = "Employee Entry"; %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= title %></title>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/employee-page.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/modal.css">
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/header.jsp" />
	<!-- Database connection and running Query to populate the department need a way to have 1 class connect -->
	<!-- Need to fix "java.lang.IllegalStateException: Cannot forward after response has been committed" issues -->
	<%
		Map<String,String> departments = new LinkedHashMap<>();
		if(request.getAttribute("departments") != null){
			departments = (Map<String,String>)request.getAttribute("departments");
		}
		
	%>
	
	<div id="employee-entry-div" class='centered'>
		<form action="employee_entry" method="POST" name="employeeForm" id="ePForm" class="container">
			<h1>Employee Entry</h1>
			<label for="firstName">First Name: </label><input type="text" name="firstName" value="${validFN}"/>
			<br/><div class="errorMsg">${errorMessage}</div>
			<br/><label for="lastName">Last Name: </label><input type="text" name="lastName" value="${validLN}"/>
			<br/><div class="errorMsg">${errorMessage2}</div>
			<br/><label for="employeeNumber">Employee #: </label><input type="text" name="employeeNumber" value="${validEN}"/>
			<br/><div class="errorMsg">${errorMessage3}</div>
			<br/><label for="email">Email: </label><input type="text" name="email" value="${validEmail}"/>
			<br/><div class="errorMsg">${errorMessage4}</div>
			<br/>
			
			<% String message = (String)request.getAttribute("confirmMessage"); 
			   if (message != null) { %>
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
			</div><%
			} %>
		
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
			
			<select id="hireYear" name="hireYear">
				<option selected disabled hidden>Hire Year</option>
			</select>
			<br/><div class="errorMsg">${errorMessage5}</div>
			<br/>
			
			<script>
				var hireYearOptions = document.getElementById("hireYear");
				for(var i = 2005; i < 2018; i++){
					var yearOption = document.createElement("option");
					yearOption.innerHTML = i;
					yearOption.value = i;
					hireYearOptions.appendChild(yearOption);
				}
			</script>
			
			<select name="jobPosition">
				<option selected disabled hidden>Job Position</option>
				<option value="Junior Programmer">Junior Programmer</option>
				<option value="Senior Programmer">Senior Programmer</option>
				<option value="Accountant">Accountant</option>
				<option value="It Director">IT Director</option>
				<option value="Software Architect">Software Architect</option>
				<option value="Bookkeeper">Bookkeeper</option>
				<option value="Digital Marketer">Digital Marketer</option>
			</select>
			<br/><div class="errorMsg">${errorMessage6}</div>
			<br/>
			
			<select name="department">
				<option selected disabled hidden>Department</option>
				<%for(Map.Entry<String,String> entry : departments.entrySet()){	%>
				<option value="<%=entry.getKey()%>"><%=entry.getValue() %></option>
				<%} %>
				
			</select>
			<br><div class="errorMsg">${errorMessage7}</div>
			<br>
			
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