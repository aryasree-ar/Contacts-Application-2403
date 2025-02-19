package com.pkg.ContactServletPkg;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.pkg.Dao.ContactsDao;
import com.pkg.Exceptions.DBException;
import com.pkg.Exceptions.InvalidInputException;
import com.pkg.POJO.ContactDetails;


@WebServlet("/UpdateContact")
public class UpdateContactServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
       
        ContactDetails contact = new ContactDetails();
        contact.setUserId(Integer.parseInt(request.getParameter("userId")));
        contact.setContactId(Integer.parseInt(request.getParameter("contactId")));
        contact.setNickName(request.getParameter("nickName"));
        contact.setContactDob(request.getParameter("contactDob"));
        contact.setGender(request.getParameter("gender"));
        contact.setPlace(request.getParameter("place"));
        contact.setContactEmail(request.getParameter("email"));
        contact.setContactPhone(request.getParameter("phone"));
        try {
        	ContactsDao.updateContact(contact);
        	response.sendRedirect("myContacts.jsp");
		} catch (InvalidInputException e) {
			response.sendRedirect("myContacts.jsp?message="+e.getMessage());
		}catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			response.sendRedirect("myContacts.jsp?message=An internal error occured.");
		} catch (DBException e) {
			response.sendRedirect("myContacts.jsp?message="+e.getMessage());
		} 
        
	}

}
