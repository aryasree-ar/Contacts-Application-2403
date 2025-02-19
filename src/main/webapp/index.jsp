<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Log In</title>
<script type="text/javascript">
window.onload = function(){
	const param = new URLSearchParams(window.location.search);
	if(param.has("message")){
		alert(param.get("message"));
	}
};
</script>
<style>
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

body {
	background: linear-gradient(120deg, #c471f5, #fcfcfc);
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
	padding: 20px;
}

/* Container Styling */
.login-container {
	background: #fff;
	padding: 30px 40px;
	border-radius: 8px;
	box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
	width: 100%;
	max-width: 400px;
	text-align: center;
	border: 1px solid #ddd;
}

.login-container h2 {
	margin-bottom: 20px;
	color: #ac3eec;
	font-size: 24px;
	font-weight: 600;
}

.login-container label {
	display: block;
	text-align: left;
	margin: 10px 0 5px;
	color: #555;
	font-size: 14px;
}

.login-container input {
	width: 100%;
	padding: 10px;
	margin: 5px 0 15px;
	border: 1px solid #ccc;
	border-radius: 4px;
	transition: border-color 0.3s;
}

.login-container input:focus {
	border-color: #c471f5;
}

.login-container button {
	width: 100%;
	padding: 12px;
	background-color: #ac3eec;
	border: none;
	border-radius: 4px;
	color: #fff;
	font-size: 16px;
	cursor: pointer;
	transition: background-color 0.3s;
	margin-top: 10px;
}

.login-container button:hover {
	background-color: #420864;
}

.login-container p {
	margin-top: 15px;
	font-size: 14px;
	color: #666;
}

.login-container a {
	color: #c471f5;
	text-decoration: none;
	font-weight: 500;
}

.login-container a:hover {
	text-decoration: underline;
}
</style>
</head>
<body>
	<div class="login-container">
		<h2>ZOHO CONTACTS</h2>
		<form action="LoginServlet" method="post">
			<label for="login-email">Email:*</label> <input type="text"
				name="login-email" id="le" placeholder="Enter your email" required>

			<label for="login-password">Password:*</label> <input type="password"
				name="login-password" id="lpw" placeholder="Enter your password" required>

			<button type="submit">Login</button>
			<p>
				Don't have an account? <a href="signUp.jsp">Create a new account</a>
			</p>
		</form>
	</div>
</body>
</html>
