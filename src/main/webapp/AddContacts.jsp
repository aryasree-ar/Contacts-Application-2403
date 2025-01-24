<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Contacts</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background: linear-gradient(120deg, #c471f5, #fcfcfc);
        margin: 0;
        padding: 0;
        height:100vh;
    }
    .container {
        max-width: 600px;
        margin: 50px auto;
        padding: 20px;
        background-color: #fff;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        border-radius: 8px;
    }
    h2, h3 {
        color: #700ea8;
        text-align: center;
    }
    form {
        display: flex;
        flex-direction: column;
        gap: 15px;
    }
    label {
        font-weight: bold;
        margin-bottom: 5px;
        color: #555;
    }
    input[type="text"], input[type="date"] {
        padding: 10px;
        width: 95%;
        border: 1px solid #ddd;
        border-radius: 4px;
    }
    button, .dashboard-btn {
        padding: 10px 15px;
        border: none;
        background-color: #700ea8;
        color: white;
        border-radius: 4px;
        cursor: pointer;
        width: 100px;
        align-self: center;
    }
    .dashboard-btn {
        display: inline-block; /* Change to block to enable centering */
        text-align: center;
         /* Center text inside the button */
        padding: 10px 15px;
        border: none;
        background-color: #700ea8;
        color: white;
        border-radius: 4px;
        cursor: pointer;
        width: 100px;
        margin: 20px auto; /* Center the button */
        text-decoration: none; /* Remove underline */
        align-self: center;
        margin-left: 39%;
    }
    button:hover , .dashboard-btn:hover {
        background-color: #420864;
    }
    .dashboard-btn {
        text-decoration: none;
        display: inline-block;
    }
</style>
</head>
<body>
<div class="container">
	<h2>Create a New Contact</h2>
	<h3>Add Details</h3>
	<form action="AddContactServlet" method="post">
		<label>Nick Name:*</label>
		<input type="text" name="contact-name" required>
		
		<label>Date of birth:</label>
		<input type="date" name="contact-dob">
		
		<label>Gender:</label>
		<input type="text" name="contact-gender">
		
		<label>Place:</label>
		<input type="text" name="contact-place">
		
		<label>Add Phone Number:*</label>
		<input type="text" name="contact-phone-number" required>
		
		<label>Add Email:*</label>
		<input type="text" name="contact-email" required>
		
		<button type="submit">Done</button>
	</form>
	<a href="dashboard.jsp" class="dashboard-btn">Go To Home</a>
</div>
</body>
</html>
