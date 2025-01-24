<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign Up</title>
    <style>
        body, h2, p, label, input, button, form {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;

        }

        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(120deg, #c471f5, #fcfcfc);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            padding: 20px;
        }

        /* Form container */
        .signup-container {
            background-color: #fff;
            padding: 20px 30px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
        }

        /* Form title */
        h2 {
            text-align: center;
            margin-bottom: 20px;
            color: #ac3eec;
        }

        /* Form styling */
        form {
            display: flex;
            flex-direction: column;
        }

        label {
            margin-bottom: 5px;
            
            color: #555;
        }

        input {
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ddd;
            border-radius: 4px;
            transition: border-color 0.3s ease;
            width: calc(100% - 20px);
        }

        input:focus {
            border-color: #c471f5;
            outline: none;
        }

        button {
            background-color: #c471f5;
            color: #fff;
            padding: 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        button:hover {
            background-color: #c471f5;
        }

        /* Link styling */
        p {
            text-align: center;
            margin-top: 15px;
            font-size: 0.9em;
            color: #555;
        }

        a {
            color: #c471f5;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    
    <div class="signup-container">
        <h2>Sign Up</h2>
        <form action="SignUpServlet" method="post">
            <label for="SignUpEmail">Email:*</label>
            <input type="email" name="sign-up-email" id="se" required>

            <label for="SignUpName">Name:</label>
            <input type="text" name="sign-up-name" id="sn" >

            <label for="SignUpPassword">Password:*</label>
            <input type="password" name="sign-up-password" id="spw" required>

            <label for="SignUpFirstName">First Name:</label>
            <input type="text" name="sign-up-frist-name" id="sfn" >

            <label for="SignUpLastName">Last Name:</label>
            <input type="text" name="sign-up-last-name" id="sln">

            <label for="SignUpPhoneNumber">Phone Number:</label>
            <input type="text" name="sign-up-phone-number" id="spn">

            <label for="SignUpDob">Date of Birth:</label>
            <input type="date" name="sign-up-dob" id="sdob">

            <label for="SignUpLocation">Location:</label>
            <input type="text" name="sign-up-location" id="sl">

            <button type="submit">Sign up</button>
        </form>

        <p>Already have an account? <a href="index.jsp">Login here</a></p>
    </div>
</body>
</html>
    