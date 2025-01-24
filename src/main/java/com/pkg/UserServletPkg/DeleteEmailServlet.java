package com.pkg.UserServletPkg;

import java.io.IOException;


import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pkg.Dao.UserDao;
import com.pkg.POJO.User;
//import com.pkg.Util.ObjectHash;
import com.pkg.POJO.UserSessions;
import com.pkg.sessionUtil.GetSessionIdFromCookie;
import com.pkg.sessionUtil.UserSessionCache;

@WebServlet("/DeleteEmailServlet")
public class DeleteEmailServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String sessionId = GetSessionIdFromCookie.getSessionIdFromCookie(request); 
		UserSessions userSession = UserSessionCache.getSessionFromCache(sessionId);
		int userId = userSession.getUserID();
		
		String email = request.getParameter("email");
		PrintWriter out = response.getWriter();
		
		try {
			if(UserDao.deleteUserEmail(userId, email)) {
				User user = UserDao.getUserById(userId);
				if(user != null) {
					user.getUserMails().removeIf(e -> e.getEmail().equals(email));
				}
				response.sendRedirect("Profile.jsp");
			}
			else {
				out.println("<script type='text/javascript'>");
				out.println("alert('Try Again !');");
				out.println("window.location.href = 'Profile.jsp';");
				out.println("</script>");
			}
		} catch (IllegalArgumentException | IllegalAccessException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
