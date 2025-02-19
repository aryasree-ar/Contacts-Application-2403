package com.pkg.ContactServletPkg;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pkg.Dao.ContactsDao;
import com.pkg.POJO.ContactDetails;
import com.pkg.POJO.UserSessions;
import com.pkg.sessionUtil.UserSessionCache;

import com.pkg.Filters.AuthFilter;


@WebServlet("/AddContact")
public class AddContactServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ContactDetails contact = new ContactDetails();
        contact.setNickName(request.getParameter("contact-name"));
        contact.setContactDob(request.getParameter("contact-dob"));
        contact.setGender(request.getParameter("contact-gender"));
        contact.setPlace(request.getParameter("contact-place"));
        contact.setContactPhone(request.getParameter("contact-phone-number"));
        contact.setContactEmail(request.getParameter("contact-email"));
		String sessionId = AuthFilter.SESSION_ID.get(); 
		UserSessions userSession = UserSessionCache.getSessionFromCache(sessionId);
		int userId = userSession.getUserId();
		try {
			if(ContactsDao.insertContactDetails(userId,contact)) {
				response.sendRedirect("dashboard.jsp");
			}
			else {
				response.sendRedirect("addContacts.jsp");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
