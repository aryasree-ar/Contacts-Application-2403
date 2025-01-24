package com.pkg.UserServletPkg;

import java.io.IOException;


import java.io.PrintWriter;
import java.time.Instant;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pkg.Dao.SessionDao;
import com.pkg.Dao.UserDao;
import com.pkg.POJO.User;
import com.pkg.POJO.UserSessions;
import com.pkg.Util.*;
import com.pkg.sessionUtil.SessionIdGenerator;
//import com.pkg.Util.ObjectHash;
import com.pkg.sessionUtil.UserSessionCache;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	
    
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userMail = request.getParameter("login-email");
		String password = request.getParameter("login-password");
		
		PrintWriter out = response.getWriter();
		int result = UserDao.loginUser(userMail, password);
		out.println("<html><body>");
		if(result > 0) {
			String sessionId = SessionIdGenerator.getSessionId();
			UserSessions session = new UserSessions(sessionId, result, Instant.now().toEpochMilli(), Instant.now().toEpochMilli());
			
			//adding session to cache and DB
			UserSessionCache.addSessionToCache(session);
			SessionDao.createSession(session);
			//setting sessionId in the cookie
			Cookie sessionCookie = new Cookie("SESSION_ID",sessionId);
			sessionCookie.setHttpOnly(true);
			response.addCookie(sessionCookie);
			
			response.sendRedirect("dashboard.jsp");
		}
		else if(result == -1) {
			out.print("<p>pw wrong</p>");
			out.println("<script type='text/javascript'>");
			out.println("alert('Oops! Wrong Password');");
			out.println("window.location.href = 'index.jsp';");
			out.println("</script>");
		}
		else if(result == -2) {
			out.print("<p>!email</p>");
			out.println("<script type='text/javascript'>");
        	out.println("alert('Oops ! No such User Email exists');");
        	out.println("window.location.href = 'index.jsp';"); 
        	out.println("</script>");
		}else {
			out.print("<p>something went wrong</p>");
		}
		out.println("<a href='index.jsp'>Logout</a></body></html>");

		
	}

}
