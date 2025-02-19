package com.pkg.Util;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pkg.sessionUtil.UserSessionCache;

@WebServlet("/notify")
public class SessionCleanerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sessionId = request.getParameter("sessionId");
		if(sessionId == null) {
			response.sendError(404,"Invalid Request");
			return;
		}
		UserSessionCache.removeSessionFromCache(sessionId);
	}

	

}
