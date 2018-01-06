<%@ page import="helpers.ValidationHelper"%>
<%@ page import="utilities.DatabaseAccess"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import ="java.sql.*" %>
<%@ page import ="javax.sql.*" %>
<%@ page import ="java.util.Map" %>
<%@ page import ="java.util.LinkedHashMap" %>

<% if (ValidationHelper.isNotNull(session.getAttribute("loggedIn"))
	   && ((Boolean)session.getAttribute("loggedIn")) == true) { %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%! String title = "Attendance Entry"; %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/modal.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= title %></title>
</head>
<body>
	<%
		Map<String, String> departmentList = new LinkedHashMap<String, String>();
	
		Integer tempID = 0;
	
		if(request.getAttribute("departmentList") != null){
			departmentList = (Map<String, String>)request.getAttribute("departmentList");
		}
		if(request.getAttribute("tempID") != null){
			tempID = (Integer)request.getAttribute("tempID");
		}
	
	
	%>
	<jsp:include page="/WEB-INF/jsp/header.jsp" />
	<h1 class="text-align-center">Attendance Entry</h1>
	<form method="POST" action="attendance_entry">
	<input type="text" name="formselect" value="selectDepartment">
		Department: <select id="selectDept" name="selectDepartment">
						<option selected disabled hidden value=<%=tempID %>>Select Department</option>
							<%for(Map.Entry<String,String> entry : departmentList.entrySet()){	%>
						<option value="<%= entry.getKey() %>">
							<%= entry.getValue() %>
						</option>
							<% } %>	
					</select>
		<script>
		$(document).ready(function(){
			var id = $('#selectDept option:selected').val();
			console.log(id);
			$("#selectDept > option").each(function() {
			    if($(this).val() == id){
			    	$('#selectDept:selected').removeAttr("selected");
			    	$(this).attr("selected","selected");
			    }
			});
		});
		</script>	
		<input type="submit" value="Submit">
	</form>
</body>
</html>
<% } else { 
	   response.sendRedirect(request.getContextPath() + "/login");
   }
%>