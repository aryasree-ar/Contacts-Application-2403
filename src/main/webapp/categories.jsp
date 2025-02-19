<%@page import="com.pkg.POJO.ContactDetails"%>
<%@page import="com.pkg.Dao.ContactsDao"%>
<%@page
	import="java.sql.ResultSet,com.pkg.POJO.Categories,com.pkg.POJO.CategoryMap"%>
<%@page import="com.pkg.Util.DBConnection,java.util.List"%>
<%@ page import="java.sql.Connection"%>
<%@page import="com.pkg.Dao.CategoryDao"%>
<%@page import="com.pkg.Filters.AuthFilter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Categories</title>
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
	font-family: Arial, sans-serif;
	margin: 0;
	padding: 20px;
	background: linear-gradient(120deg, #c471f5, #fcfcfc);
	height: 100vh;
}

h2 {
	color: white;
	font-weight: bold;
}

h4 {
	color: #333;
}

.category-container {
	background: #ffffff;
	padding: 15px;
	border: 1px solid #ddd;
	margin-bottom: 15px;
	border-radius: 5px;
}

.category-name {
	font-weight: bold;
	font-size: 18px;
	margin: 0;
	display: inline-block;
	margin-right: 10px;
}

.category-actions {
	display: inline-block;
}

.category-actions form {
	display: inline;
	margin-right: 5px;
}

.category-actions input[type="submit"] {
	background-color: #700ea8;
	color: white;
	border: none;
	padding: 5px 10px;
	cursor: pointer;
	border-radius: 3px;
}

.category-actions input[type="submit"]:hover {
	background-color: #420864;
}

.delete-button {
	background-color: #420864;
}

.delete-button:hover {
	background-color: #c0392b;
}

.form-section {
	background: #ffffff;
	padding: 15px;
	border: 1px solid #ddd;
	margin-top: 20px;
	border-radius: 5px;
}

.form-section label {
	display: block;
	margin: 10px 0 5px;
}

.form-section input[type="text"] {
	width: 100%;
	padding: 8px;
	margin: 5px 0 15px;
	border: 1px solid #ddd;
	border-radius: 3px;
}

.form-section input[type="submit"] {
	background-color: #700ea8;
	color: white;
	border: none;
	padding: 10px 20px;
	cursor: pointer;
	border-radius: 3px;
}

.form-section input[type="submit"]:hover {
	background-color: #420864;
}

.contact-checkbox {
	margin: 5px 0;
}

a {
	display: inline-block;
	margin-top: 15px;
	text-decoration: none;
	color: white;
	background-color: #420864;
	padding: 12px;
	border-radius: 3px;
}

a:hover {
	text-decoration: underline;
}
</style>

</head>
<body>

	<h2>Your Categories</h2>
	<%
	Integer userId = AuthFilter.USER_ID.get();
	List<Categories> categories = CategoryDao.getCategories(userId);
	for (Categories category : categories) {
	%>
	<div class="category-container">
		<span class="category-name"><%=category.getCategoryName()%></span>
		<div class="category-actions">
			<form action="categoryUpdate.jsp" method="post">
				<input type="hidden" name="categoryId"
					value="<%=category.getCategoryId()%>"> <input type="hidden"
					name="categoryName" value="<%=category.getCategoryName()%>">
				<input type="submit" value="View">
			</form>
			<form action="DeleteCategory" method="post">
				<input type="hidden" name="categoryId"
					value="<%=category.getCategoryId()%>"> <input type="submit"
					value="Delete" class="delete-button"
					onclick="return confirm('Are you sure you want to delete this category?');">
			</form>
		</div>
	</div>
	<%
}
%>
	<h2>Add Category:</h2>
	<div class="form-section">
		<form action="AddCategory" method="post">

			<label style="font-weight: bold;">Category Name:</label> <input
				type="text" name="categoryName" placeholder="Enter category name">

			<h4>Add Members:</h4>
			<%
			List<ContactDetails> contacts = ContactsDao.getContacts(userId);
			for (ContactDetails contact : contacts) {
			%>
			<div class="contact-checkbox">
				<input type="checkbox" name="selectedContacts"
					value="<%=contact.getContactId()%>">
				<%=contact.getNickName()%><br>
			</div>
			<%
			}
			%>
			<input type="submit" value="Add Members">
		</form>
	</div>

	<a href="dashboard.jsp">Go To Home</a>

</body>
</html>
