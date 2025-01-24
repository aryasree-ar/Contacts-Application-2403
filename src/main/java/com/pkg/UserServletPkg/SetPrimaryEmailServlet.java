package com.pkg.UserServletPkg;

import java.io.IOException;



import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.cj.CharsetSettings;
import com.pkg.Dao.UserDao;
import com.pkg.POJO.User;
import com.pkg.POJO.UserEmails;
//import com.pkg.Util.ObjectHash;
import com.pkg.POJO.UserSessions;
import com.pkg.sessionUtil.GetSessionIdFromCookie;
import com.pkg.sessionUtil.UserSessionCache;


@WebServlet("/SetPrimaryEmailServlet")
public class SetPrimaryEmailServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String sessionId = GetSessionIdFromCookie.getSessionIdFromCookie(request); 
		UserSessions userSession = UserSessionCache.getSessionFromCache(sessionId);
		int userId = userSession.getUserID();
		
		String primaryMail = request.getParameter("email");
		PrintWriter out = response.getWriter();
        if(UserDao.setPrimaryEmail(userId, primaryMail)) {
        	User user = UserDao.getUserById(userId);
        	if(user != null) {
        		for(UserEmails email:user.getUserMails()) {
        			//setting non primary emails
        			if(email.getIsPrimary() > 0 && !(email.getEmail().equals(primaryMail))) {
        				email.setIsPrimary(0);	
        			}
        			else if(email.getEmail().equals(primaryMail)) {
        				email.setIsPrimary(1);
        			}
        			
        		}
        	}
        	response.sendRedirect("Profile.jsp");
        }
        else {
        	out.println("<script type='text/javascript'>");
			out.println("alert('Try Again !');");
			out.println("window.location.href = 'Profile.jsp';");
			out.println("</script>");
        }
	}

}
