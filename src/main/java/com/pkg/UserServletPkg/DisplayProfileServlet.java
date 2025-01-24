package com.pkg.UserServletPkg;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pkg.Dao.UserDao;
import com.pkg.POJO.User;
import com.pkg.POJO.UserSessions;
import com.pkg.sessionUtil.GetSessionIdFromCookie;
import com.pkg.sessionUtil.UserSessionCache;

@WebServlet("/DisplayProfileServlet")
public class DisplayProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sessionId = GetSessionIdFromCookie.getSessionIdFromCookie(request);
		UserSessions userSession = UserSessionCache.getSessionFromCache(sessionId);
		User user = UserDao.getUserById(userSession.getUserID());
		request.setAttribute("user", user);
		request.getRequestDispatcher("Profile.jsp").forward(request, response);
	}

}
