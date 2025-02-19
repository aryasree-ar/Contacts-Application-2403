<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page
	import="com.pkg.Dao.UserDao,com.pkg.POJO.User,com.pkg.POJO.UserEmails"%>
<%@page import="com.pkg.Util.DBConnection"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.naming.NamingException"%>
<%@page import="com.pkg.Filters.AuthFilter"%>
<%
try {
	Integer userId = AuthFilter.USER_ID.get();
	User user = UserDao.getUserById(userId);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Profile</title>
<script type="text/javascript">
window.onload = function(){
	const param = new URLSearchParams(window.location.search);
	if(param.has("message")){
		alert(param.get("message"));
	}
};
</script>
<style>
body {
	font-family: Arial, sans-serif;
	background: linear-gradient(120deg, #c471f5, #fcfcfc);
	margin: 0;
	padding: 0;
	height: 100vh;
}

.container {
	max-width: 800px;
	margin: 50px auto;
	padding: 20px;
	background-color: white;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	border-radius: 8px;
}

h1 {
	color: #ac3eec;
	text-align: center;
}

h2 {
	color: #9d63be;
	border-bottom: 2px solid #f0f0f0;
	padding-bottom: 5px;
}

p {
	color: #666;
	line-height: 1.5;
	margin: 10px 0;
}

.email-list {
	margin-top: 10px;
}

.email-item {
	padding: 5px;
	border-bottom: 1px solid #e0e0e0;
}

form {
	margin-top: 20px;
}

input[type="email"] {
	padding: 10px;
	width: 70%;
	border: 1px solid #ddd;
	border-radius: 4px;
}

input[type="submit"] {
	padding: 10px 20px;
	border: none;
	background-color: #ac3eec;
	color: white;
	border-radius: 4px;
	cursor: pointer;
}

input[type="submit"]:hover {
	background-color: #420864;
}

.logout, .dashboard-btn {
	display: block;
	margin: 20px 300px;
	width: 200px;
	text-align: center;
	color: #fff;
	background-color: #700ea8;
	padding: 10px;
	text-decoration: none;
	border-radius: 4px;
}

.logout:hover, dashboard-btn:hover {
	background-color: #420864;
}
</style>
</head>
<body>
	<div class="container">
		<h1>My Profile</h1>
		<h2>General Details</h2>
		<p>
			<strong>Hi <%=user.getUserName()%> !!!
			</strong>
		</p>
		<p>
			<strong>Name:</strong>
			<%=user.getUserFirstName()%>
			<%=user.getUserLastName()%></p>
		<p>
			<strong>Phone Number:</strong>
			<%=user.getUserPhone()%></p>
		<p>
			<strong>Date of Birth:</strong>
			<%=user.getUserDOB()%></p>
		<p>
			<strong>Location:</strong>
			<%=user.getUserLocation()%></p>

		<h2>Email Addresses</h2>
		<div class="email-list">
			<%
			if (user.getUserMails() != null) {
				System.out.println(user.getUserMails());
				for (UserEmails email : user.getUserMails()) {
					Integer isPrimary = email.getIsPrimary();
			%>
			<div class="email-item"
				style="display: flex; justify-content: space-between; align-items: center; padding: 5px; border-bottom: 1px solid #e0e0e0;">

				<p style="margin: 0;">
					<%=email.getEmail()%>
					<%
					if (isPrimary > 0) {
					%>
					<strong>⭐</strong>
					<%
					}
					%>
				</p>

				<form action="SetPrimaryEmail" method="post"
					style="margin: 0;">
					<input type="hidden" name="email" value="<%=email.getEmail()%>">
					<%
					if (isPrimary < 1) {
					%>
					<input type="submit" value="Set as Primary"
						style="padding: 5px 10px; background-color: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer;">
					<%
					}
					%>
				</form>
				<%
				if (isPrimary < 1) {
				%>
				<form action="DeleteEmail" method="post" style="margin: 0;">
					<input type="hidden" name="email" value="<%=email.getEmail()%>">
					<input type="submit" value="Delete"
						style="padding: 5px 10px; background-color: #420864; color: white; border: none; border-radius: 4px; cursor: pointer;">
				</form>
				<%
				}
				%>
			</div>
			<%
			}
			} else {
			System.out.println("null user");
			}
			%>

		</div>

		<form action="AddEmail" method="post">
			<input type="email" name="addemail" placeholder="Add a new email"
				required> <input type="submit" value="Add Email">
		</form>

		<a href="LogOut" class="logout">Logout</a> 
		<a href="dashboard.jsp" class="dashboard-btn">Go To Home</a>
	</div>
</body>
</html>
<%
} catch (Exception e) {
e.printStackTrace();
}
%>
