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
<%! String title = "Report View"; %>
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
		ArrayList<String> sections    = new ArrayList<>();
		ArrayList<String> critValues  = new ArrayList<>();
		ArrayList<String> reportDetails  = new ArrayList<>();
		
		Map<String,String> templateList = new LinkedHashMap<String,String>();
		Map<String,String> reports      = new LinkedHashMap<String,String>();
		Map<String,String> section1crit = new LinkedHashMap<String,String>();
		Map<String,String> section2crit = new LinkedHashMap<String,String>();
		Map<String,String> section3crit = new LinkedHashMap<String,String>();
		
		Integer tempID = 0;
		Integer reportID = 0;
	
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
			if(request.getAttribute("reports") != null){
				reports = (Map<String,String>)request.getAttribute("reports");
			}
			if(request.getAttribute("critValues") != null){
				critValues = (ArrayList<String>)request.getAttribute("critValues");
			}
			if(request.getAttribute("reportDetails") != null){
				reportDetails = (ArrayList<String>)request.getAttribute("reportDetails");
			}
			
			if(request.getAttribute("tempID") != null){
				tempID = (Integer)request.getAttribute("tempID");
			}
			
		}	
	%>
	<jsp:include page="/WEB-INF/jsp/header.jsp" />
	<div class="container">
	<div class="container">
		<h1 class="text-align-center">VIEW REPORT</h1>
		<span>Filters</span>
		<form method="post" action="report_view" name="selectTemplate">
			<div class="details-div">
			<input type="hidden" name="formselect" value="selectTemplate">
						Report Template: <select id="selectTemp" onchange="this.form.submit()" name="selectTemplate">
											<option selected disabled hidden value=${tempID }>Select Template</option>
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
		<%Integer count = 0; %>
		<form method="post" action="report_view" name="enterReportForm" >	
		<input type="hidden" name="formselect" value="selectReport">	
		<input type="hidden" name="templateIDselect" value=${tempID }>	
				<div class="details-div">								
					<span>Department</span><select name="selectDepartment">											
												<%for(String name : departments){ %>					  	
												<option value="<%=name%>"><%=name%></option>
												<%} %>							
									      	</select>
					<span>Report Title</span><select name="reports">
										<%for(Map.Entry<String,String> entry : reports.entrySet()){	%>
										<option selected disabled hidden>Select Report</option>
										
										<option value="<%=entry.getKey() %>"><%=entry.getValue() %></option>
										<%} %>
									</select>
					
				</div>		
			<input type="submit" value="View">
		</form>			      					      
		<hr>
		<%if(request.getAttribute("critValues") != null){ %>
		<form method="post" action="report_view" name="updateForm">
		<input type="hidden" name="templateIDselect" value=${tempID }>
		<input type="hidden" name="formselect" value="updateForm">
		<input type="hidden" name="reportID" value=${reportID }>
		<span>1. Details</span>
		<table class="center-div">
			<tr>
				<td >Report</td>
				<td class="td-data"><%=reportDetails.get(0) %></td>
			</tr>
			<tr>
				<td>Report Title</td>
				<td class="td-data"><%=reportDetails.get(1) %></td>
			</tr>
			<tr>
				<td>Date Created</td>
				<td class="td-data"><%=reportDetails.get(2) %></td>
			</tr>
			<tr>
				<td>Department</td>
				<td class="td-data"><%=departments.get(0) %></td>
			</tr>
		</table>
		<hr>
		<span>2. Section I:   </span><input type="text" name="sectionOne" value="${section1 }" disabled>
				<div id="sectionOneList" >					
					<div id="sectionOneChildren" class="criteriaListDiv ">
					<%for(Map.Entry<String,String> entry : section1crit.entrySet()){	%>
						<div class="criteria-container">												
							<input type="text" name="section_1_criteria_name" value="<%=entry.getKey() %>" disabled>
							<input type="hidden" name="section_1_criteria_id" value="<%=entry.getValue() %>">
					 Evaluation: <select name="section_1_criteria_value" disabled>
									   	<%for(Integer i = 1; i < 6; i++){ %>
									   	<option value="<%=i %>"<%if(i == Integer.parseInt(critValues.get(count))){ %>selected<%} %>><%=i %></option>
									   	<%} count++;%>
								   </select>
					   </div>
					   <%} %>
					   
				   </div>
				   <div class="inline-block">
				   <textarea rows="8" cols="50" class="inline-block" name="section_1_comment"  disabled>${comment1 }</textarea>
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
			 Evaluation:	<select name="section_2_criteria_value" disabled>
							   	<%for(Integer j = 1; j < 6; j++){ %>
									   	<option value="<%=j %>"<%if(j == Integer.parseInt(critValues.get(count))){ %>selected<%} %>><%=j %></option>
							   	<%}count++; %>
						   </select>
					   </div>
					    <%} %>
				   </div>
				   <div class="inline-block">
				   <textarea rows="8" cols="50" class="inline-block" name="section_2_comment" disabled>${comment2 }</textarea>
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
				Evaluation: <select name="section_3_criteria_value" disabled>
							   	<%for(Integer k = 1; k < 6; k++){ %>
									   	<option value="<%=k %>"<%if(k == Integer.parseInt(critValues.get(count))){ %>selected<%} %>><%=k %></option>
							   	<%}count++; %>
						   </select>
					   </div>
					   <%} %>
				   </div>
				   <div class="inline-block">
				   <textarea rows="8" cols="50" class="inline-block" name="section_3_comment" disabled>${comment3 }</textarea>
				   </div>
				</div>	
		<hr>
		<div class="grade-container">
			<span>Total: </span><span id="grade-counter" class="grade-counter"></span><span id="grade-total"></span>
		</div>
		<script>
			$(document).ready(function(){
				var section1 = 0;
				var section2 = 0;
				var section3 = 0;
				var total = 0;
				$('select[name=section_1_criteria_value]').each(function(){
					total+=5;
					section1 += parseInt($(this).val());
				});
				$('select[name=section_2_criteria_value]').each(function(){
					total+=5;
					section2 += parseInt($(this).val());
				});
				$('select[name=section_3_criteria_value]').each(function(){
					total+=5;
					section3 += parseInt($(this).val());
				});
				$('select[name=section_1_criteria_value]').change(function(){
					section1 = 0;
					$('select[name=section_1_criteria_value]').each(function(){
						section1 += parseInt($(this).val());
					});
					$('#grade-counter').text(section1 + section2 + section3 );
				});
				$('select[name=section_2_criteria_value]').change(function(){
					section2 = 0;
					$('select[name=section_2_criteria_value]').each(function(){
						section2 += parseInt($(this).val());
					});
					$('#grade-counter').text(section1 + section2 + section3 );
				});
				$('select[name=section_3_criteria_value]').change(function(){
					section3 = 0;
					$('select[name=section_3_criteria_value]').each(function(){
						section3 += parseInt($(this).val());
					});
					$('#grade-counter').text(section1 + section2 + section3 );
				});
				$('#grade-counter').text(section1 + section2 + section3 );
				$('#grade-total').text(" / " + total);
			});
		
		</script>
		<div class="center-div">
			<input id="editButton" type="submit" value="Edit">
		</div>		
		</form>
		<script>
			$(document).ready(function(){
				var editable = false;
				$('#editButton').click(function(e){
					if(editable == false){
						e.preventDefault();
						$('#editButton').val("Update");
						$('textarea').each(function(){
							$(this).removeAttr('disabled',false);
						});
						$('form[name=updateForm] select').each(function(){
							$(this).removeAttr('disabled',false);
						});
						editable = true;
						
					} else {
						
					}
				});
			});
		</script>
		<% String message = (String)request.getAttribute("confirmMessage"); 
			   if (message != null) { %>
			<!-- Modal from https://www.w3schools.com/howto/howto_css_modals.asp -->
			<div id="dialogBox" class ="dialog">
				<div class="dialogContent centered">
					<div class="dialogHeader">
						<span class="close">&times;</span>
						<h3>Report Updated</h3>
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
	</div>
	</div>
	<%} %>				
</body>
</html>
<% } else { 
	   response.sendRedirect(request.getContextPath() + "/login");
   }
%>