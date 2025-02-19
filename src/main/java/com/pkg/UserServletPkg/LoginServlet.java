package com.pkg.UserServletPkg;

import java.io.IOException;
import java.time.Instant;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.pkg.Dao.SessionDao;
import com.pkg.Dao.UserDao;
import com.pkg.Exceptions.InvalidInputException;
import com.pkg.POJO.UserSessions;
import com.pkg.sessionUtil.SessionIdGenerator;
import com.pkg.sessionUtil.UserSessionCache;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userMail = request.getParameter("login-email");
		String password = request.getParameter("login-password");
		System.out.println(userMail + password);
		try {
			int userId = UserDao.loginUser(userMail, password);
			System.out.println(userId);
			String sessionId = SessionIdGenerator.getSessionId();
			UserSessions session = new UserSessions(sessionId, userId, Instant.now().toEpochMilli(),
					Instant.now().toEpochMilli());

			// adding session to cache and DB
			
			UserSessionCache.addSessionToCache(session);
			SessionDao.createSession(session);
			// setting sessionId in the cookie
			Cookie sessionCookie = new Cookie("SESSION_ID", sessionId);
			sessionCookie.setHttpOnly(true);
			response.addCookie(sessionCookie);

			response.sendRedirect("dashboard.jsp");
		} catch (InvalidInputException e) {
			e.printStackTrace();
			response.sendRedirect("index.jsp?message=" + e.getMessage());
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			response.sendRedirect("index.jsp?message=An internal error occured. Please try again.");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("index.jsp?message=" + e.getMessage());
		}
	}

}
