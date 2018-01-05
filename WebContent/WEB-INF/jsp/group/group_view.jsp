<%@ page import="helpers.ValidationHelper" %>
<%@ page import="helpers.ReportHelper" %>
<%@ page import="utilities.DatabaseAccess"%>
<%@ page import="objects.GroupMember"%>
<%@ page import ="java.sql.*" %>
<%@ page import ="javax.sql.*" %>
<%@ page import ="java.util.LinkedHashMap" %>
<%@ page import ="java.util.Map" %>
<%@ page import ="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<% if (ValidationHelper.isNotNull(session.getAttribute("loggedIn"))
	   && ((Boolean)session.getAttribute("loggedIn")) == true) { %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%! String title = "View Groups"; %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="${pageContext.request.contextPath}/jQuery/jquery-3.2.0.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/group-page.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/modal.css">
<title>Group View</title>
</head>
<body>
<%
	Integer depID = 0;
	Map<String,String> departments = new LinkedHashMap<String,String>();
	Map<String,String> groups = new LinkedHashMap<>();
	ArrayList<GroupMember> members = new ArrayList<>();
	if(request.getAttribute("departments") != null){
		departments = (Map<String,String>)request.getAttribute("departments");
		if(request.getAttribute("groups") != null){
			groups = (Map<String,String>)request.getAttribute("groups");
		}
		if(request.getAttribute("members") != null){
			members = (ArrayList<GroupMember>)request.getAttribute("members");
		}
		if(request.getAttribute("depID") != null){
			depID = (Integer)request.getAttribute("depID");
		}
	}

%>
	<jsp:include page="/WEB-INF/jsp/header.jsp" />
	<div class="centered container">
		<h1 class="text-align-center">VIEW GROUPS</h1>
		<span>Filters</span>
		<form method="post" action="group_view" name="selectDepartment">
			<div class="details-div">
			<input type="hidden" name="formselect" value="selectDepartment">
						Department: <select id="selectDep" onchange="this.form.submit()" name="selectDepartment">
											<option selected disabled hidden value=<%=depID%>>Select Department</option>
										  	<%for(Map.Entry<String,String> entry : departments.entrySet()){	%>
											<option value="<%= entry.getKey() %>">
												<%= entry.getValue() %>
											</option>
											<% } %>	
											
										 </select>
		<script>
		$(document).ready(function(){
			var id = $('#selectDep option:selected').val();
			console.log(id);
			$("#selectDep > option").each(function() {
			    if($(this).val() == id){
			    	$('#selectDep:selected').removeAttr("selected");
			    	$(this).attr("selected","selected");
			    }
			});
		});
		</script>	 
		</div>
		</form>
		
		<form method="post" action="group_view" name="selectGroupForm" >	
			<input type="hidden" name="formselect" value="selectGroup">	
			<input type="hidden" name="departmentIDselect" value=<%=depID%>>
				<div class="details-div">								
					<span>Group</span><select name="selectGroupForm">											
										<option selected disabled hidden value="">Select Group</option>							  	
												<%for(Map.Entry<String,String> entry : groups.entrySet()){	%>
											<option value="<%= entry.getKey() %>">
												<%= entry.getValue() %>
											</option>
											<% } %>	
									      	</select>
									
				</div>		
			<input type="submit" value="View">
			<hr>
			<table>
			<tr>
				<th>Department</th>
				<th>Group Name</th>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Employee #</th>
			</tr>
				<%
					if(request.getAttribute("members") != null){						
							for (GroupMember m : members){
								%>
								<tr>
									<td><%=m.getDepartmentName() %></td>
									<td><%=m.getGroupName() %></td>		
									<td><%=m.getFirstName() %></td>
									<td><%=m.getLastName() %></td>	
									<td><%=m.geteID() %></td>							
								</tr>
								<%
							}							
						}
				%>
			</table>
		</form>	
		</div>		      					      
</body>
</html>
<% } else { 
	   response.sendRedirect(request.getContextPath() + "/login");
   }
%>