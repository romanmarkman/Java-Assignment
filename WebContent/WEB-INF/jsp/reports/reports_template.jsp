<%@ page import="helpers.ValidationHelper" %>
<%@ page import="helpers.ReportHelper" %>
<%@ page import="utilities.DatabaseAccess"%>
<%@ page import ="java.sql.*" %>
<%@ page import ="javax.sql.*" %>
<%@ page import ="java.util.LinkedHashMap" %>
<%@ page import ="java.util.Map" %>
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
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/modal.css">
<title><%= title %></title>
</head>
<body>
	<%
		Map<String,String> departmentList = new LinkedHashMap<>();
		String date = ReportHelper.getCurrentDate();
		if(request.getAttribute("departmentList") != null){
			departmentList = (Map<String,String>)request.getAttribute("departmentList");
		}
		
		
	%>
	<jsp:include page="/WEB-INF/jsp/header.jsp" />
	<div class="container">
		<form method="post" action="report_template" name="buildTemplateForm" class="container">
			<h1 class="text-align-center">CREATE REPORT TEMPLATE</h1>
				<span>1. Details</span>
				<div class="details-div">
					Report Template Name<input type="text" name="templateName" required>
					Date<input type="text" name="templateDate" value="<%= date %>" disabled><br>
					<div class="errorMsg">${errTemplateName }</div>
					Department<select name="selectDepartment">
								<option selected disabled hidden>Select Department</option>
							  	<%for(Map.Entry<String,String> entry : departmentList.entrySet()){	%>
								<option value="<%= entry.getKey() %>">
									<%= entry.getValue() %>
								</option>
								<% } %>	
						      </select>
						      <div class="errorMsg">${errDepartSelect }</div>
				</div>
				<hr>
				
				<span>2. Section I:   </span><input type="text" name="sectionOne" required>
				<div class="errorMsg">${errSectionOne }</div>
				<div id="sectionOneList" class="buildListDiv">
					<button type="button" id="addCriteria1" >Add Criteria</button>
					<button type="button" id="removeCriteria1">Undo</button><br><br>
					<div class="errorMsg">${errCritOne}</div>
					<div id="critOneEmpty" class="errorMsg" style="display:none;">You have empty criteria</div>
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
				<div class="errorMsg">${errSectionTwo }</div>
				<div id="sectionTwoList" class="buildListDiv">
					<button type="button" id="addCriteria2">Add Criteria</button>
					<button type="button" id="removeCriteria2">Undo</button><br><br>
					<div class="errorMsg">${errCritTwo}</div>
					<div id="critTwoEmpty" class="errorMsg" style="display:none;">You have empty criteria</div>
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
				<div class="errorMsg">${errSectionThree }</div>
				<div id="sectionThreeList"  class="buildListDiv">
					<button type="button" id="addCriteria3">Add Criteria</button>
					<button type="button" id="removeCriteria3">Undo</button><br><br>
					<div class="errorMsg">${errCritThree}</div>
					<div id="critThreeEmpty" class="errorMsg" style="display:none;">You have empty criteria</div>
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
					<input id="templateSubmit" type="submit" value="Create">
					<input type="reset" value="Cancel">
				</div>
				
			
		</form>
		<script>
		$(document).ready(function(){
			$('#templateSubmit').click(function(e){
				var valid = true;
				$('input[name=section_1_criteria_name]').each(function(){
					if($(this).val().trim() == ""){
						$('#critOneEmpty').show()
						valid = false;
					}else{
						$('#critOneEmpty').hide();
					}
				});
				$('input[name=section_2_criteria_name]').each(function(){
					if($(this).val().trim() == ""){
						$('#critTwoEmpty').show()
						valid = false;
					}else{
						$('#critTwoEmpty').hide();
					}
				});
				$('input[name=section_3_criteria_name]').each(function(){
					if($(this).val().trim() == ""){
						$('#critThreeEmpty').show()
						valid = false;
					}else{
						$('#critThreeEmpty').hide();
					}
				});
				if(valid == false){
					e.preventDefault();
					
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
						<h3>New Template Added</h3>
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
</body>
</html>
<% } else { 
	   response.sendRedirect(request.getContextPath() + "/login");
   }
%>