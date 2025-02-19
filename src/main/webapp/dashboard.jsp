<%@page import="com.pkg.Util.DBConnection"%>
<%@page import="com.pkg.Dao.UserDao"%>
<%@page import="com.pkg.POJO.User"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.naming.NamingException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.pkg.Filters.AuthFilter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
Integer userId = AuthFilter.USER_ID.get();
User user = UserDao.getUserById(userId);
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>
	<%
	if (user != null) {
		out.print(user.getUserName());
	}
	%>'s Contacts
</title>
<script type="text/javascript">
    	window.onload= function(){
    		 const params = new URLSearchParams(window.location.search);
             if (params.has("message")) {
                 alert(params.get("message")); 
             }
    	};
    </script>
<style>
body {
	margin: 0;
	font-family: Arial, sans-serif;
	background: linear-gradient(120deg, #c471f5, #fcfcfc);
}

.container {
	display: flex;
	height: 100vh;
}
/* Sidebar styling */
.sidebar {
	width: 300px;
	background-color: #fafafa;
	color: #ac3eec;
	padding: 15px;
	box-sizing: border-box;
	display: flex;
	flex-direction: column;
	gap: 10px;
	justify-content: space-between; /* Distribute space between items */
	height: 100vh;
}

.sidebar h3 {
	margin: 0;
	padding: 10px 0;
	text-align: center;
	border-bottom: 1px solid #ac3eec;
	font-size: 30px;
}

.sidebar a {
	display: block;
	color: #ecf0f1;
	padding: 15px 15px;
	text-decoration: none;
	background-color: #ac3eec;
	border-radius: 4px;
	transition: background-color 0.3s;
}

.logout {
	margin-top: auto;
}

.sidebar a:hover {
	background-color: #420864;
}

.banner-container {
	flex-grow: 1;
	display: flex;
	align-items: center;
	justify-content: center;
}

.banner {
	width: 30%;
	height: 20%;
	max-width: 800px;
	background-color: #f4f4f4;
	padding: 50px;
	box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
	text-align: center;
	border-radius: 8px;
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
}

.banner h1 {
	color: #5c1b82;
	font-size: 36px;
}

.banner p {
	color: #333;
	font-size: 18px;
}
</style>
</head>
<body>
	<div class="container">
		<div class="sidebar">
			<h3>Dashboard</h3>
			<a href="profile.jsp ">Profile</a> 
			<a href="myContacts.jsp ">My Contacts</a> 
			<a href="addContacts.jsp">Add Contact</a> 
			<a href="categories.jsp">Categories</a> 
			<a href="LogOut" class="logout">Logout</a>
		</div>



		<div class="banner-container">
			<div class="banner">
				<h1>
					Welcome,
					<%=user.getUserName()%>!
				</h1>
				<p>Manage your contacts easily with ZOHO Contacts!</p>
			</div>
		</div>
	</div>
</body>
</html>




