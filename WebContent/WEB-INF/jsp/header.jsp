<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<div id='header'>
	<nav id='header-nav'>
		<ul>
			<li><a href="${pageContext.request.contextPath}/home">Home</a></li>
			<li><a href="${pageContext.request.contextPath}/department">Department</a></li>
			<li><a href="${pageContext.request.contextPath}/employee">Employee</a></li>
			<li><a href="${pageContext.request.contextPath}/group">Group</a></li>
			<li><a href="${pageContext.request.contextPath}/reports">Reports</a></li>
		</ul>
	</nav>
	<div id="user-panel">
		<% 
			if (session.getAttribute("loggedIn") != null
				&& ((Boolean)session.getAttribute("loggedIn")) == false) { %>
				<a href='${pageContext.request.contextPath}/login'>Log In</a>
		<%  } else {  %>
			<div id="welcome-user">Welcome, <span id="user_name">${sessionScope.loggedInUser}</span>!</div>
			<form action='login' method='GET' name='logout-form' id='logout-form'>
				<input type='hidden' name='action' value='logout'/>
				<a href='#' onclick='document.getElementById("logout-form").submit();'>Log Out</a>
			</form>
		<%  }  %>
	</div>
</div>