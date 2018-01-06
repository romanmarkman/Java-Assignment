<%@ page import="helpers.ValidationHelper"%>
<%@ page import="utilities.DatabaseAccess"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import ="java.sql.*" %>
<%@ page import ="javax.sql.*" %>
<%@ page import ="java.util.Map" %>
<%@ page import ="java.util.ArrayList" %>
<%@ page import ="java.util.LinkedHashMap" %>
<% if (ValidationHelper.isNotNull(session.getAttribute("loggedIn"))
	   && ((Boolean)session.getAttribute("loggedIn")) == true) { %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%! String title = "Group Entry"; %>
<html>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/jQuery/jquery-3.2.0.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/group-page.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/modal.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= title %></title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/header.jsp" />
	<%  //If null in session or the page was loaded for the first time
				
		//Conn to DB
		
		Integer depID = 0;
		Map<String,String> departments = new LinkedHashMap<>();
		Map<String,String> employees = new LinkedHashMap<>();
		
		if(request.getAttribute("departments") != null){
			departments = (Map<String,String>)request.getAttribute("departments");
		}
		if(request.getAttribute("depID") != null){
			depID = (Integer)request.getAttribute("depID");
		}
		if(request.getAttribute("employees") != null){
			employees = (Map<String,String>)request.getAttribute("employees");
		}
		
	%>
	<div id="group-div" class="centered container">
	<h1>Group Entry</h1>
		<form id="departmentForm" action="group_entry" method="POST" name="departmentEntry" >
			<input type="hidden" name="formselect" value="departmentSelect" >
			<label for="department">Department: </label><select id="departmentS" onchange="this.form.submit()" name="department">
						<option selected disabled hidden value="<%=depID%>">Select</option>
						<%for(Map.Entry<String,String> entry : departments.entrySet()){	%>
						<option value="<%= entry.getKey() %>">
							<%= entry.getValue() %>
						</option>
						<%} %>					
						</select>
						<br><div class="errorMsg">${departmentError}</div><br>
		</form>	
		<script>
		$(document).ready(function(){
			var id = $('#departmentS option:selected').val();
			console.log(id);
			$("#departmentS > option").each(function() {
			    if($(this).val() == id){
			    	$('#departmentS:selected').removeAttr("selected");
			    	$(this).attr("selected","selected");
			    }
			});
		});
		</script>	 
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
			<%if(request.getAttribute("employees") != null){ %>
		<form id="groupForm" action="group_entry" method="post" name="groupEntry">
			<input type="hidden" name="formselect" value="groupSelect" >
			<input type="hidden" name="departmentID" value="<%=depID %>" >
			
			Group Name: <input type="text" name="groupName" value="${validGN }"/><br>
			<div class="errorMsg">${groupError}</div><br>
			
			<label>Employee 1: </label><select id="emp1" name="employee">
							<option selected disabled hidden value="0">Select</option>
							<%for(Map.Entry<String,String> entry : employees.entrySet()){	%>
							<option value="<%= entry.getKey() %>">
								<%= entry.getValue() %>
							</option>
							<%} %>
						</select>
			<label>Employee 2: </label><select id="emp2" name="employee">
							<option selected disabled hidden value="0">Select</option>
							<%for(Map.Entry<String,String> entry : employees.entrySet()){	%>
							<option value="<%= entry.getKey() %>">
								<%= entry.getValue() %>
							</option>
							<%} %>
						</select>
			<label>Employee 3: </label><select id="emp3" name="employee">
							<option selected disabled hidden value="0">Select</option>
							<%for(Map.Entry<String,String> entry : employees.entrySet()){	%>
							<option value="<%= entry.getKey() %>">
								<%= entry.getValue() %>
							</option>
							<%} %>
						</select><br><br>
			<label>Employee 4: </label><select id="emp4" name="employee">
							<option selected disabled hidden value="0">Select</option>
							<%for(Map.Entry<String,String> entry : employees.entrySet()){	%>
							<option value="<%= entry.getKey() %>">
								<%= entry.getValue() %>
							</option>
							<%} %>
						</select>
			<label>Employee 5: </label><select id="emp5" name="employee">
							<option selected disabled hidden value="0">Select</option>
							<%for(Map.Entry<String,String> entry : employees.entrySet()){	%>
							<option value="<%= entry.getKey() %>">
								<%= entry.getValue() %>
							</option>
						    <%} %>
						</select>
			<label>Employee 6: </label><select id="emp6" name="employee">
							<option selected disabled hidden value="0">Select</option>
							<%for(Map.Entry<String,String> entry : employees.entrySet()){	%>
							<option value="<%= entry.getKey() %>">
								<%= entry.getValue() %>
							</option>
							<%} %>
						</select><br>
						
			<br><div class="errorMsg">${employeeError}</div>
			<br><div class="errorMsg">${dubError}</div><br>
			
			
			<input type="submit"  value="Proceed">
			<input type="reset"  value="Cancel">	
		</form>
		<%} %>
	</div>
</body>
</html>
<% } else { 
	   response.sendRedirect(request.getContextPath() + "/login");
   }
%>