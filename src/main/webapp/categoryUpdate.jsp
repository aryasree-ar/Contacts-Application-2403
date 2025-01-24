<%@page import="com.pkg.POJO.ContactDetails"%>
<%@page import="java.lang.reflect.Member"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection,java.util.List,java.sql.ResultSet,com.pkg.Util.DBConnection,com.pkg.Dao.CategoryDao,com.pkg.POJO.Categories" %>
<%@page import="com.pkg.Util.AuthFilter"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Category Update</title>
<style>
    body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 20px;
        background: linear-gradient(120deg, #c471f5, #fcfcfc);
        height:100vh;
    }

    h2, h3 {
        color: white;
    }

    .category-container, .form-section {
        background: #ffffff;
        padding: 15px;
        border: 1px solid #ddd;
        margin-bottom: 15px;
        border-radius: 5px;
    }

    .member {
        margin: 5px 0;
        padding: 10px;
        background-color: #e9ecef;
        border: 1px solid #ddd;
        border-radius: 5px;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .member span {
        font-weight: bold;
    }

    .member form {
        display: inline;
    }

    .member input[type="submit"] {
    	background-color: #700ea8;
        color: white;
        border: none;
        padding: 5px 10px;
        cursor: pointer;
        border-radius: 3px;
    }

    .member input[type="submit"]:hover {
        background-color: #420864;
    }

    .form-section label {
        display: block;
        margin: 10px 0 5px;
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
        margin-left:47%;
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

<%
int categoryId = Integer.parseInt(request.getParameter("categoryId"));
    String categoryName = request.getParameter("categoryName");
    Integer userId = AuthFilter.user_id.get();   
    List<ContactDetails> categoryMembers= CategoryDao.getCategoryMembers(categoryId);
    List<ContactDetails> nonCategoryMembers = CategoryDao.getNonCategoryMembers(userId, categoryId);
%>

<h2>Category Name: <%=categoryName%></h2>

<%
if (!categoryMembers.isEmpty()) {
%>
    <h3>Category Members:</h3>
    <div class="category-container">
    <%
    for(ContactDetails categoryMember : categoryMembers){
    %>
        <div class="member">
            <span><%=categoryMember.getNickName()%></span>
            <form action="RemoveMemberServlet" method="post">
                <input type="hidden" name="categoryId" value="<%=categoryId%>">
                <input type="hidden" name="categoryName" value="<%=categoryName%>">
                <input type="hidden" name="contactId" value="<%=categoryMember.getContactId()%>">
                <input type="submit" value="Remove">
            </form>
        </div>
    <%
    }
    %>
    </div>
<%
}
%>


<%
if (!nonCategoryMembers.isEmpty()) {
%>
    <h3>Add Members:</h3>
    <div class="form-section">
        <form action="AddContactsToCategoryServlet" method="post">
            <input type="hidden" name="categoryId" value="<%=categoryId%>">
            <input type="hidden" name="categoryName" value="<%=categoryName%>">
            
            <%
                        for(ContactDetails contact: nonCategoryMembers){
                        %>
                <div class="contact-checkbox">
                    <input type="checkbox" name="selectedContacts" value="<%= contact.getContactId() %>">
                    <%= contact.getNickName() %>
                </div>
            <% } %>
            <input type="submit" value="Add Members">
        </form>
    </div>
<% } %>

<a href="Categories.jsp">Go Back</a> 



</body>
</html>
