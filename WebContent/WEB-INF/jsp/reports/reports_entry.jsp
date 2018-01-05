<%@ page import="helpers.ValidationHelper" %>
<%@ page import="helpers.ReportHelper" %>
<%@ page import="utilities.DatabaseAccess"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import ="java.sql.*" %>
<%@ page import ="javax.sql.*" %>
<%@ page import ="java.util.Map" %>
<%@ page import ="java.util.HashMap" %>
<%@ page import ="java.util.LinkedHashMap" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<% if (ValidationHelper.isNotNull(session.getAttribute("loggedIn"))
	   && ((Boolean)session.getAttribute("loggedIn")) == true) { %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%! String title = "Report Entry"; %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="${pageContext.request.contextPath}/jQuery/jquery-3.2.0.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reports.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/modal.css">
<title><%= title %></title>
</head>
<body>
	<%
		
		ArrayList<String> departments = new ArrayList<>();
		ArrayList<String> sections = new ArrayList<>();
		ArrayList<String> employees = new ArrayList<>();
		ArrayList<String> groups = new ArrayList<>();
		
		Map<String,String> templateList = new LinkedHashMap<String,String>();
		Map<String,String> section1crit = new HashMap<String,String>();
		Map<String,String> section2crit = new HashMap<String,String>();
		Map<String,String> section3crit = new HashMap<String,String>();
		Integer tempID = 0;
		
		if(request.getAttribute("templateList") != null){
			templateList = (Map<String,String>)request.getAttribute("templateList");
		}
		if(request.getAttribute("departmentList") != null){						
			departments = (ArrayList<String>)request.getAttribute("departmentList");
			
			if(request.getAttribute("sectionOneCrit") != null){
				section1crit = (Map<String,String>)request.getAttribute("sectionOneCrit");
			}
			if(request.getAttribute("sectionTwoCrit") != null){
				section2crit = (Map<String,String>)request.getAttribute("sectionTwoCrit");
			}
			if(request.getAttribute("sectionThreeCrit") != null){
				section3crit = (Map<String,String>)request.getAttribute("sectionThreeCrit");
			}
			if(request.getAttribute("employees") != null){
				employees = (ArrayList<String>)request.getAttribute("employees");
			}
			if(request.getAttribute("groups") != null){
				groups = (ArrayList<String>)request.getAttribute("groups");
			}
			if(request.getAttribute("tempID") != null){
				tempID = (Integer)request.getAttribute("tempID");
			}
		}	
	%>
	<jsp:include page="/WEB-INF/jsp/header.jsp" />
	<div class="container">
	<div class="container">
		<h1 class="text-align-center">ENTER REPORT</h1>
		<span>1. Details</span>
		<form method="post" action="report_entry" name="buildTemplateForm">
			<div class="details-div">
			<input type="hidden" name="formselect" value="selectTemplate">
						Report Template: <select id="selectTemp" onchange="this.form.submit()" name="selectTemplate">
										  	<option selected disabled hidden value=<%=tempID %>>Select Template</option>
										  	<%for(Map.Entry<String,String> entry : templateList.entrySet()){	%>
											<option value="<%= entry.getKey() %>">
												<%= entry.getValue() %>
											</option>
											<% } %>	
										 </select>
		<script>
		$(document).ready(function(){
			var id = $('#selectTemp option:selected').val();
			console.log(id);
			$("#selectTemp > option").each(function() {
			    if($(this).val() == id){
			    	$('#selectTemp:selected').removeAttr("selected");
			    	$(this).attr("selected","selected");
			    }
			});
		});
		</script>	 
		</div>
		</form>
		<form method="post" action="report_entry" name="enterReportForm" >	
		<input type="hidden" name="formselect" value="enterReport">	
		<input type="hidden" name="templateIDselect" value="<%=tempID%>">	
				<div class="details-div">								
					<span>Department</span><select name="selectDepartment">											
												<%for(String name : departments){ %>					  	
												<option value="<%=name%>"><%=name%></option>
												<%} %>							
									      	</select>
					<span>Report Title</span><input type="text" name="reportTitle">
					<span>Date</span><input type="date" name="reportDate" >
					<div>
						<span>Report Type</span>
						<input type="radio" id="groupSelect" name="reportType" value="group">Group<br>
						<input type="radio" id="employeeSelect" name="reportType" value="employee" checked>Employee
					</div>
					<select id="group" name="selectGroup" class="disabled" disabled>
						<option selected disabled hidden>Group</option>
						<%for(String name : groups){ %>
						<option value="<%=name%>"><%=name%></option>
						<%} %>	
					</select>
					<select id="employees" name="selectEmployee">
						<option selected disabled hidden>Employee</option>
						<%for(String name : employees){ %>
						<option value="<%=name%>"><%=name%></option>
						<%} %>	
					</select>
				</div>		
				<script>
				$(document).ready(function(){
					$('#groupSelect').click(function(){
						$('#group').removeAttr("disabled");
						$('#employees').attr("disabled",true);
						$('#employees').addClass("disabled");
						$('#group').removeClass("disabled");
					});
					
					$('#employeeSelect').click(function(){
						$('#employees').removeAttr("disabled");
						$('#group').attr("disabled",true);
						$('#group').addClass("disabled");
						$('#employees').removeClass("disabled");
					});
				});
				
				</script>	
		<%if(request.getAttribute("departmentList") != null){ %>				      					      
		<hr>
		<span>2. Section I:   </span><input type="text" name="sectionOne" value="${section1 }" disabled>
				<div id="sectionOneList" >					
					<div id="sectionOneChildren" class="criteriaListDiv ">
					<%for(Map.Entry<String,String> entry : section1crit.entrySet()){	%>
						<div class="criteria-container">												
							<input type="text" name="section_1_criteria_name" value="<%=entry.getKey() %>" disabled>
							<input type="hidden" name="section_1_criteria_id" value="<%=entry.getValue() %>">
					 Evaluation: <select name="section_1_criteria_value">
									   	<option value=1>1</option>
									   	<option value=2>2</option>
									   	<option value=3>3</option>
									   	<option value=4>4</option>
									   	<option value=5 selected>5</option>
								   </select>
					   </div>
					   <%} %>
					   
				   </div>
				   <div class="inline-block">
				   <textarea rows="8" cols="50" class="inline-block" name="section_1_comment"></textarea>
				   </div>
				</div>
		<hr>
		<span>3. Section II:   </span><input type="text" name="sectionTwo" value="${section2 }" disabled>
				<div id="sectionTwoList" >					
					<div id="sectionTwoChildren" class="criteriaListDiv ">
					<%for(Map.Entry<String,String> entry : section2crit.entrySet()){	%>
						<div class="criteria-container">
							<input type="text" name="section_2_criteria_name" value="<%=entry.getKey() %>" disabled>
							<input type="hidden" name="section_2_criteria_id" value="<%=entry.getValue() %>">
			 Evaluation:	<select name="section_2_criteria_value">
							   	<option value=1>1</option>
							   	<option value=2>2</option>
							   	<option value=3>3</option>
							   	<option value=4>4</option>
							   	<option value=5 selected>5</option>
						   </select>
					   </div>
					    <%} %>
				   </div>
				   <div class="inline-block">
				   <textarea rows="8" cols="50" class="inline-block" name="section_2_comment"></textarea>
				   </div>
				</div>
		<hr>
		<span>4. Section III:   </span><input type="text" name="sectionOne" value="${section3 }" disabled>
				<div id="sectionThreeList" >					
					<div id="sectionThreeChildren" class="criteriaListDiv ">
					<%for(Map.Entry<String,String> entry : section3crit.entrySet()){	%>
						<div class="criteria-container">
							<input type="text" name="section_3_criteria_name" value="<%=entry.getKey() %>" disabled>
							<input type="hidden" name="section_3_criteria_id" value="<%=entry.getValue() %>">
				Evaluation: <select name="section_3_criteria_value">
							   	<option value=1>1</option>
							   	<option value=2>2</option>
							   	<option value=3>3</option>
							   	<option value=4>4</option>
							   	<option value=5 selected>5</option>
						   </select>
					   </div>
					   <%} %>
				   </div>
				   <div class="inline-block">
				   <textarea rows="8" cols="50" class="inline-block" name="section_3_comment"></textarea>
				   </div>
				</div>	
		<hr>
			<div class="center-div">
					<input type="submit" value="Enter">
					<input type="reset" value="Cancel">
			</div>	
		</form>
		</div>
		</div>
	<%} %>			
		<% String message = (String)request.getAttribute("confirmMessage"); 
			   if (message != null) { %>
			<!-- Modal from https://www.w3schools.com/howto/howto_css_modals.asp -->
			<div id="dialogBox" class ="dialog">
				<div class="dialogContent centered">
					<div class="dialogHeader">
						<span class="close">&times;</span>
						<h3>New Report Added</h3>
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
	
	
</body>
</html>
<% } else { 
	   response.sendRedirect(request.getContextPath() + "/login");
   }
%>