package com.pkg.UserServletPkg;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.pkg.Dao.UserDao;
import com.pkg.Exceptions.InvalidInputException;
import com.pkg.POJO.User;
import com.pkg.POJO.UserEmails;
import com.pkg.POJO.UserSessions;
import com.pkg.sessionUtil.UserSessionCache;
import com.pkg.Filters.AuthFilter;

@WebServlet("/SetPrimaryEmail")
public class SetPrimaryEmailServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String sessionId = AuthFilter.SESSION_ID.get();
		UserSessions userSession = UserSessionCache.getSessionFromCache(sessionId);
		int userId = userSession.getUserId();

		String primaryMail = request.getParameter("email");
		try {
			if (UserDao.setPrimaryEmail(userId, primaryMail)) {
				User user = UserDao.getUserById(userId);
				if (user != null) {
					for (UserEmails email : user.getUserMails()) {
						if (email.getIsPrimary() > 0 && !(email.getEmail().equals(primaryMail))) {
							email.setIsPrimary(0);
						} else if (email.getEmail().equals(primaryMail)) {
							email.setIsPrimary(1);
						}

					}
				}
				response.sendRedirect("profile.jsp");
			} else {
				response.sendRedirect("profile.jsp?message=Oops! That did not work. Try again.");
			}
		} catch (InvalidInputException e) {
			response.sendRedirect("profile.jsp?message=" + e.getMessage());
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException
				| IOException e) {
			response.sendRedirect("profile.jsp?message=An internal error occured. Please try again.");
		} catch (Exception e) {
			response.sendRedirect("profile.jsp?message=An unexpected error occured. Please try again.");
		}
	}

}
