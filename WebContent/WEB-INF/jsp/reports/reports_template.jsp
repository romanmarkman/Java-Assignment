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
<script type="text/javascript" src="${pageContext.request.contextPath}/jQuery/jquery-3.2.0.js"></script>
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
					Report Template Name<input type="text" name="templateName" required>
					Date<input type="text" name="templateDate" value="<%= date %>" disabled><br>
					<div class="errorMsg">${errorMessageTemplateName }</div>
					Department<select name="selectDepartment">
								<option selected disabled hidden>Select Department</option>
							  	<% while(departmentList.next()) { %>
								<option value="<%= departmentList.getInt(1) %>">
									<%= departmentList.getString(2) %>
								</option>
								<% } %>	
						      </select>
						      <div class="errMsg">${errorMessageDepartment }</div>
				</div>
				<hr>
				<span>2. Section I:   </span><input type="text" name="sectionOne" required>
				<div id="sectionOneList" class="criteriaListDiv">
					<button type="button" id="addCriteria1" >Add Criteria</button>
					<button type="button" id="removeCriteria1">Undo</button><br><br>
					<div id="sectionOneChildren">
						<div>
							Criteria 1: <input type="text" name="section_1_criteria_name">
							Maximum: <select name="section_1_criteria_value">
									   	<option value=1>1</option>
									   	<option value=2>2</option>
									   	<option value=3>3</option>
									   	<option value=4>4</option>
									   	<option value=5 selected>5</option>
								   </select>
					   </div>
				   </div>
				</div>
				<script>
				
					
					$(document).ready(function(){
						var limit1 = 3;
						var number1 = 2;
			            $('button#addCriteria1').click(function(){
			                var criteria1 ='<div>'+
							    '<br>Criteria '+number1+': <input type="text" name="section_1_criteria_name">' +
							    ' Maximum: <select name="section_1_criteria_value">' +
							    '<option value=1>1</option>' +
							    '<option value=2>2</option>' +
							    '<option value=3>3</option>' +
							    '<option value=4>4</option>' +
							    '<option value=5 selected>5</option>' +
							    '</select></div>';
							    if (limit1 >= 0) {
							    	$('#sectionOneChildren').append(criteria1);
								    limit1--;
								    number1++;
							  	}
			            });
			            $('button#removeCriteria1').click(function () {
			                var $length = $('#sectionOneChildren > div').length;		                
			                if($length > 1) {
			                    $('#sectionOneChildren > div').last().remove();
			                    limit1++;
			                    number1--;
			                }
			            });
					});				
					
				</script>
				<hr>
				<span>3. Section II:   </span><input type="text" name="sectionTwo" required>
				<div id="sectionTwoList" class="criteriaListDiv">
					<button type="button" id="addCriteria2">Add Criteria</button>
					<button type="button" id="removeCriteria2">Undo</button><br><br>
						<div id="sectionTwoChildren">
							<div>
								Criteria 1: <input type="text" name="section_2_criteria_name">
								Maximum: <select name="section_2_criteria_value">
										   	<option value=1>1</option>
										   	<option value=2>2</option>
										   	<option value=3>3</option>
										   	<option value=4>4</option>
										   	<option value=5 selected>5</option>
									   </select>
						   </div>
					   </div>
				</div>
				<script>
				$(document).ready(function(){
					var limit2 = 1;
					var number2 = 2;
		            $('button#addCriteria2').click(function(){
		                var criteria2 ='<div>'+
						    '<br>Criteria '+number2+': <input type="text" name="section_2_criteria_name">' +
						    ' Maximum: <select name="section_2_criteria_value">' +
						    '<option value=1>1</option>' +
						    '<option value=2>2</option>' +
						    '<option value=3>3</option>' +
						    '<option value=4>4</option>' +
						    '<option value=5 selected>5</option>' +
						    '</select></div>';
						    if (limit2 >= 0) {
						    	$('#sectionTwoChildren').append(criteria2);
							    limit2--;
							    number2++;
							  }
		            });
		            $('button#removeCriteria2').click(function () {
		                var $length = $('#sectionTwoChildren > div').length;		                
		                if($length > 1) {
		                    $('#sectionTwoChildren > div').last().remove();
		                    limit2++;
		                    number2--;
		                }
		            });
				});
					
				</script>
				<hr>
				<span>4. Section III: </span><input type="text" name="sectionThree" required>
				<div id="sectionThreeList"  class="criteriaListDiv">
					<button type="button" id="addCriteria3">Add Criteria</button>
					<button type="button" id="removeCriteria3">Undo</button><br><br>
					<div id="sectionThreeChildren">
						<div >
							Criteria 1: <input type="text" name="section_3_criteria_name">
							Maximum: <select name="section_3_criteria_value">
									   	<option value=1>1</option>
									   	<option value=2>2</option>
									   	<option value=3>3</option>
									   	<option value=4>4</option>
									   	<option value=5 selected>5</option>
								   </select>
					   </div>
				   </div>
				</div>
				<script>
				$(document).ready(function(){
					var limit3 = 1;
					var number3 = 2;					
		            $('button#addCriteria3').click(function(){
		                var criteria3 ='<div>'+
						    'Criteria '+number3+': <input type="text" name="section_3_criteria_name">' +
						    ' Maximum: <select name="section_3_criteria_value">' +
						    '<option value=1>1</option>' +
						    '<option value=2>2</option>' +
						    '<option value=3>3</option>' +
						    '<option value=4>4</option>' +
						    '<option value=5 selected>5</option>' +
						    '</select></div>';
						    if (limit3 >= 0) {
						    	$('#sectionThreeChildren').append(criteria3);
							    limit3--;
							    number3++;								   
							  }						    
		            });
		            $('button#removeCriteria3').click(function () {
		                var $length = $('#sectionThreeChildren > div').length;		                
		                if($length > 1) {
		                    $('#sectionThreeChildren > div').last().remove();
		                    limit3++;
		                    number3--;
		                }
		            });
		            
				});
					
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