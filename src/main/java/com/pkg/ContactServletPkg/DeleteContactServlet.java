package com.pkg.ContactServletPkg;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pkg.Dao.ContactsDao;
import com.pkg.Exceptions.InvalidInputException;

@WebServlet("/DeleteContact")
public class DeleteContactServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int contactId = Integer.parseInt(request.getParameter("contactId"));
		try {
			ContactsDao.deleteContact(contactId);
			response.sendRedirect("myContacts.jsp");
		} catch (InvalidInputException e) {
			response.sendRedirect("myContacts.jsp?message="+e.getMessage());
		}catch (IllegalArgumentException | IllegalAccessException | IOException e) {
			response.sendRedirect("myContacts.jsp?message=An internal error occured. Please try again.");
		} catch (Exception e) {
			response.sendRedirect("myContacts.jsp?message=Something went wrong.");
		} 
	}

}
