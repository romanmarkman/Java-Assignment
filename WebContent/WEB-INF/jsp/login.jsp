<%@ page import ="helpers.ValidationHelper" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%! String title = "Login"; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= title %></title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css"/>
</head>
<body>
	<div id="login-div" class="centered">
		<% if (ValidationHelper.isNotNull(request.getAttribute("errorMessage"))) { %>
			<span class='error-message'>${errorMessage}</span>
		<% } %>
		<form action="login" method="POST" name="login-form" id="login-form" class="container">
			<h1>Login</h1>
			<div id="labels">
				<label for="username">Username:</label>
				<br/><label for="password">Password:</label>
			</div>
			<div id="input-boxes">
				<input id="username" name="username" type="text" placeholder="Enter username" value="${username}"/>
				<br/><input id="password" name="password" type="password" placeholder="Enter password" value="${password}"/>
			</div>
			<br class="clearfix"/>
			<div id="login">
				<button type="submit">Login</button>
			</div>
		</form>
	</div>
</body>
</html>