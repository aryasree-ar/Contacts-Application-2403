package com.pkg.ContactServletPkg;

import java.io.IOException;

import java.util.List;

import com.pkg.Dao.ContactsDao;
import com.pkg.POJO.ContactDetails;
import com.pkg.POJO.UserSessions;
import com.pkg.sessionUtil.GetSessionIdFromCookie;
import com.pkg.sessionUtil.UserSessionCache;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/DisplayContactsServlet")
public class DisplayContactsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sessionId = GetSessionIdFromCookie.getSessionIdFromCookie(request);
		UserSessions userSession = UserSessionCache.getSessionFromCache(sessionId);
		List<ContactDetails> contacts = ContactsDao.getContacts(userSession.getUserID());
		request.setAttribute("contactsList", contacts);
		request.getRequestDispatcher("MyContacts.jsp").forward(request, response);
	}

}
