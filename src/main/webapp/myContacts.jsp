<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page
	import="com.pkg.Dao.ContactsDao, java.sql.ResultSet,com.pkg.POJO.ContactDetails,com.pkg.POJO.DbPojo"%>
<%@page import="com.pkg.Util.DBConnection"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.Connection,java.util.List"%>
<%@page import="javax.naming.NamingException"%>
<%@page import="com.pkg.Filters.AuthFilter"%>
<%
Integer userId = AuthFilter.USER_ID.get();
List<ContactDetails> contactList = ContactsDao.getContacts(userId);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Contacts</title>
<script type="text/javascript">
    	window.onload= function(){
    		 const params = new URLSearchParams(window.location.search);
             if (params.has("message")) {
                 alert(params.get("message")); 
             }
    	};
    </script>
<style>
body, .noContacts {
	font-family: Arial, sans-serif;
	background: linear-gradient(120deg, #c471f5, #fcfcfc);
	margin: 20px;
	height: 100vh;
}

table {
	width: 80%;
	margin: 0 auto;
	border-collapse: collapse;
	background-color: #fff;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

th, td {
	padding: 12px;
	text-align: left;
	border-bottom: 1px solid #ddd;
}

th {
	background-color: #f8f8f8;
	color: #333;
}

tr:nth-child(even) {
	background-color: #f9f9f9;
}

tr:hover {
	background-color: #f1f1f1;
}

h1 {
	text-align: center;
	color: white;
	margin-bottom: 20px;
}

.dashboard-btn {
	display: block;
	margin: 20px 300px;
	width: 200px;
	text-align: center;
	color: #fff;
	background-color: #700ea8;
	padding: 10px;
	text-decoration: none;
	border-radius: 4px;
	margin-left: 42%;
}

.dashboard-btn:hover {
	background-color: #420864;
}

.action-btn {
	background-color: #700ea8;
	color: white;
	border: none;
	padding: 8px 16px;
	text-decoration: none;
	border-radius: 4px;
	cursor: pointer;
}

.action-btn:hover {
	background-color: #420864;
}

.delete-btn {
	background-color: #420864;
}

.delete-btn:hover {
	background-color: #black;
}

.modal {
	display: none;
	position: fixed;
	z-index: 1;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	background-color: rgba(0, 0, 0, 0.5);
}

.modal-content {
	background-color: #fff;
	margin: 15% auto;
	padding: 20px;
	border: 1px solid #888;
	width: 40%;
	border-radius: 8px;
}

.close {
	color: #aaa;
	float: right;
	font-size: 28px;
	font-weight: bold;
}

.close:hover, .close:focus {
	color: black;
	text-decoration: none;
	cursor: pointer;
}
</style>
</head>
<body>
	<h1>My Contacts</h1>
	<%
	if (contactList != null && !contactList.isEmpty()) {
	%>
	<table>
		<thead>
			<tr>
				<th>Nick Name</th>
				<th>Date of Birth</th>
				<th>Gender</th>
				<th>Place</th>
				<th>Email</th>
				<th>Phone Number</th>
				<th>Actions</th>
			</tr>
		</thead>
		<tbody>
			<%
			for (ContactDetails contact : contactList) {
			%>
			<tr>
				<td><%=contact.getNickName()%></td>
				<td><%=contact.getContactDob()%></td>
				<td><%=contact.getGender()%></td>
				<td><%=contact.getPlace()%></td>
				<td><%=contact.getContactEmail()%></td>
				<td><%=contact.getContactPhone()%></td>
				<td>
					<button type="button" class="action-btn"
						onclick="openModal('<%=contact.getUserId()%>','<%=contact.getContactId()%>', '<%=contact.getNickName()%>', 
                        '<%=contact.getContactDob()%>', '<%=contact.getGender()%>', 
                        '<%=contact.getPlace()%>', '<%=contact.getContactEmail()%>', 
                        '<%=contact.getContactPhone()%>')">
						Edit</button>
					<form action="DeleteContact" method="post"
						style="display: inline;">
						<input type="hidden" name="contactId"
							value="<%=contact.getContactId()%>">
						<button type="submit" class="action-btn delete-btn">Delete</button>
					</form>
				</td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>
	<%
	} else {
	%>
	<h2 style="background: linear-gradient(120deg, #c471f5, #fcfcfc);">No
		contacts added</h2>
	<%
	}
	%>
	<a href="dashboard.jsp" class="dashboard-btn">Go To Home</a>

	<div id="updateModal" class="modal">
		<div class="modal-content">
			<span class="close">&times;</span>
			<h2>Edit Contact</h2>
			<form action="UpdateContact" method="post">
				<input type="hidden" name="userId" id="modalUserId"> <input
					type="hidden" name="contactId" id="modalContactId"> <label
					for="nickName">Nick Name:</label> <input type="text"
					name="nickName" id="modalNickName" required> <br> <br>

				<label for="contactDob">Date of Birth:</label> <input type="date"
					name="contactDob" id="modalContactDob"> <br> <br>

				<label for="gender">Gender:</label> <select name="gender"
					id="modalGender">
					<option value="Male">Male</option>
					<option value="Female">Female</option>
					<option value="Others">Others</option>
				</select> <br> <br> <label for="place">Place:</label> <input
					type="text" name="place" id="modalPlace"> <br> <br>

				<label for="email">Email:</label> <input type="email" name="email"
					id="modalEmail" required> <br> <br> <label
					for="phone">Phone Number:</label> <input type="text" name="phone"
					id="modalPhone"> <br> <br>

				<button type="submit" class="action-btn">Save Changes</button>
			</form>
		</div>
	</div>

	<script>
		var modal = document.getElementById("updateModal");
		var closeBtn = document.getElementsByClassName("close")[0];

		function openModal(userId, contactId, nickName, dob, gender, place,
				email, phone) {
			modal.style.display = "block";

			// Populate the modal 
			document.getElementById("modalUserId").value = userId;
			document.getElementById("modalContactId").value = contactId;
			document.getElementById("modalNickName").value = nickName;
			document.getElementById("modalContactDob").value = dob;
			document.getElementById("modalGender").value = gender;
			document.getElementById("modalPlace").value = place;
			document.getElementById("modalEmail").value = email;
			document.getElementById("modalPhone").value = phone;
		}

		// Function to close the modal
		closeBtn.onclick = function() {
			modal.style.display = "none";
		}
	</script>
</body>
</html>

