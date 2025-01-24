package com.pkg.CategoryServletPkg;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.pkg.sessionUtil.GetSessionIdFromCookie;
import com.pkg.sessionUtil.UserSessionCache;


@WebServlet("/CategoryUpdateServlet")
public class CategoryUpdateServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sessionId = GetSessionIdFromCookie.getSessionIdFromCookie(request);
		//user_sessions userSession = UserSessionCache.getSessionFromCache(sessionId);
		int userId = UserSessionCache.getUserIdFromSessionCache(sessionId);
		
		request.getRequestDispatcher("Categories.jsp").forward(request, response);
	}

}
