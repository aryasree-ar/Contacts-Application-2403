package com.pkg.ContactServletPkg;

import java.io.IOException;



import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pkg.Dao.ContactsDao;
import com.pkg.POJO.ContactDetails;
import com.pkg.POJO.UserSessions;
import com.pkg.sessionUtil.GetSessionIdFromCookie;
import com.pkg.sessionUtil.UserSessionCache;


@WebServlet("/AddContactServlet")
public class AddContactServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		ContactDetails contact = new ContactDetails();
        contact.setNickName(request.getParameter("contact-name"));
        contact.setContactDob(request.getParameter("contact-dob"));
        contact.setGender(request.getParameter("contact-gender"));
        contact.setPlace(request.getParameter("contact-place"));
        contact.setContactPhone(request.getParameter("contact-phone-number"));
        contact.setContactEmail(request.getParameter("contact-email"));
        
		PrintWriter out = response.getWriter();
		String sessionId = GetSessionIdFromCookie.getSessionIdFromCookie(request); 
		UserSessions userSession = UserSessionCache.getSessionFromCache(sessionId);
		int userId = userSession.getUserID();
		try {
			if(ContactsDao.insertContactDetails(userId,contact)) {
				out.println("<script type='text/javascript'>");
				out.println("alert('Contact added successfully');");
				out.println("window.location.href = 'dashboard.jsp';");
				out.println("</script>");
			}
			else {
				out.println("<script type='text/javascript'>");
				out.println("alert('Oops! That didnt work...');");
				out.println("window.location.href = 'AddContacts.jsp';");
				out.println("</script>");
				
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
