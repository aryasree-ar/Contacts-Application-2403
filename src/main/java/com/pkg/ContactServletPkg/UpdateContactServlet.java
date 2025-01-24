package com.pkg.ContactServletPkg;

import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pkg.Dao.ContactsDao;
import com.pkg.POJO.ContactDetails;


@WebServlet("/UpdateContactServlet")
public class UpdateContactServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
       
        ContactDetails contact = new ContactDetails();
        contact.setContactId(Integer.parseInt(request.getParameter("contactId")));
        contact.setNickName(request.getParameter("nickName"));
        contact.setContactDob(request.getParameter("contactDob"));
        contact.setGender(request.getParameter("gender"));
        contact.setPlace(request.getParameter("place"));
        contact.setContactEmail(request.getParameter("email"));
        contact.setContactPhone(request.getParameter("phone"));
        
		PrintWriter out = response.getWriter();

        if(ContactsDao.updateContact(contact)) {
        	out.println("<script type='text/javascript'>");
        	out.println("alert('Changes saved successfully !');");
        	out.println("window.location.href = 'MyContacts.jsp';"); 
        	out.println("</script>");
        }
        else {
        	out.println("<script type='text/javascript'>");
        	out.println("alert('Oops ! That did'nt work...');");
        	out.println("window.location.href = 'MyContacts.jsp';"); 
        	out.println("</script>");
        }
	}

}
