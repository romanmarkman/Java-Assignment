<%@ page import="helpers.ValidationHelper" %>
<%@ page import="helpers.ReportHelper" %>
<%@ page import="utilities.DatabaseAccess"%>
<%@ page import ="java.sql.*" %>
<%@ page import ="javax.sql.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<% if (ValidationHelper.isNotNull(session.getAttribute("loggedIn"))
	   && ((Boolean)session.getAttribute("loggedIn")) == true) { %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%! String title = "Home"; %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reports.css"/>
<title><%= title %></title>
</head>
<body>
	<%
		DatabaseAccess db = new DatabaseAccess();
		java.sql.Connection connect = DatabaseAccess.connectDataBase();
		Statement statement = connect.createStatement();
		ResultSet departmentList = statement.executeQuery("SELECT department_id,name FROM department");
		String date = ReportHelper.getCurrentDate();
	%>
	<jsp:include page="/WEB-INF/jsp/header.jsp" />
	<div class="container">
		<form method="post" action="report_template" name="buildTemplateForm" class="container">
			<h1 class="text-align-center">CREATE REPORT TEMPLATE</h1>
				<span>1. Details</span>
				<div class="details-div">
					Report Template Name<input type="text" name="templateName">
					Date<input type="text" name="templateDate" value="<%= date %>" disabled><br>
					Department<select name="selectDepartment">
								<option selected disabled hidden>Select Department</option>
							  	<% while(departmentList.next()) { %>
								<option value="<%= departmentList.getInt(1) %>">
									<%= departmentList.getString(2) %>
								</option>
								<% } %>	
						      </select>
				</div>
				<hr>
				<span>2. Section I:   </span><input type="text" name="sectionOne">
				<div id="sectionOneList" class="criteriaListDiv">
					<button type="button" onclick="sectionOneAddCriteria()">Add Criteria</button><br><br>
					Criteria 1: <input type="text" name="section_1_criteria_1_name">
					Maximum: <select name="section_1_criteria_1_value">
							   	<option value=1>1</option>
							   	<option value=2>2</option>
							   	<option value=3>3</option>
							   	<option value=4>4</option>
							   	<option value=5>5</option>
						   </select>
				</div>
				<script>
				
					var limit1 = 3;
					var number1 = 2;
					function sectionOneAddCriteria() {
					  var criteria1 =
					    '<br>Criteria '+number1+': <input type="text" name="section_1_criteria_' +
					    number1 +
					    '_name">' +
					    ' Maximum: <select name="section_1_criteria_' +
					    number1 +
					    '_value">' +
					    '<option value=1>1</option>' +
					    '<option value=2>2</option>' +
					    '<option value=3>3</option>' +
					    '<option value=4>4</option>' +
					    '<option value=5>5</option>' +
					    '</select>';
					  if (limit1 >= 0) {
					    var element = document.getElementById("sectionOneList");
					    element.innerHTML += criteria1;
					    limit1--;
					    number1++;
					  }
					}
					
				</script>
				<hr>
				<span>3. Section II:   </span><input type="text" name="sectionTwo">
				<div id="sectionTwoList" class="criteriaListDiv">
					<button type="button" onclick="sectionTwoCriteria()">Add Criteria</button><br><br>
					Criteria 1: <input type="text" name="section_2_criteria_1_name">
					Maximum: <select name="section_2_criteria_1_value">
							   	<option value=1>1</option>
							   	<option value=2>2</option>
							   	<option value=3>3</option>
							   	<option value=4>4</option>
							   	<option value=5>5</option>
						   </select>
				</div>
				<script>
					var limit2 = 1;
					var number2 = 2;
					function sectionTwoCriteria() {
					  var criteria2 =
					    '<br>Criteria '+number2+': <input type="text" name="section_2_criteria_' +
					    number2 +
					    '_name">' +
					    ' Maximum: <select name="section_2_criteria_' +
					    number2 +
					    '_value">' +
					    '<option value=1>1</option>' +
					    '<option value=2>2</option>' +
					    '<option value=3>3</option>' +
					    '<option value=4>4</option>' +
					    '<option value=5>5</option>' +
					    '</select>';
					  if (limit2 >= 0) {
					    var element = document.getElementById("sectionTwoList");
					    element.innerHTML += criteria2;
					    limit2--;
					    number2++;
					  }
					}
					
				</script>
				<hr>
				<span>4. Section III: </span><input type="text" name="sectionThree">
				<div id="sectionThreeList" class="criteriaListDiv">
					<button type="button" onclick="sectionThreeCriteria()">Add Criteria</button><br><br>
					Criteria 1: <input type="text" name="section_3_criteria_1_name">
					Maximum: <select name="section_3_criteria_1_value">
							   	<option value=1>1</option>
							   	<option value=2>2</option>
							   	<option value=3>3</option>
							   	<option value=4>4</option>
							   	<option value=5>5</option>
						   </select>
				</div>
				<script>
					var limit3 = 1;
					var number3 = 2;
					function sectionThreeCriteria() {
					  var criteria3 =
					    '<br>Criteria '+number3+': <input type="text" name="section_3_criteria_' +
					    number3 +
					    '_name">' +
					    ' Maximum: <select name="section_3_criteria_' +
					    number3 +
					    '_value">' +
					    '<option value=1>1</option>' +
					    '<option value=2>2</option>' +
					    '<option value=3>3</option>' +
					    '<option value=4>4</option>' +
					    '<option value=5>5</option>' +
					    '</select>';
					  if (limit3 >= 0) {
					    var element = document.getElementById("sectionThreeList");
					    element.innerHTML += criteria3;
					    limit3--;
					    number3++;
					  }
					}
					
				</script>
				<hr>
				<div class="center-div">
					<input type="submit" value="Create">
					<input type="reset" value="Cancel">
				</div>
				
			
		</form>
	</div>
</body>
</html>
<% } else { 
	   response.sendRedirect(request.getContextPath() + "/login");
   }
%>