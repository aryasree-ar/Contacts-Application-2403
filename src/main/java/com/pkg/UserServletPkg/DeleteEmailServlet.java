package com.pkg.UserServletPkg;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.pkg.Dao.UserDao;
import com.pkg.Exceptions.DBException;
import com.pkg.Exceptions.InvalidInputException;
import com.pkg.POJO.User;
import com.pkg.POJO.UserSessions;
import com.pkg.sessionUtil.UserSessionCache;
import com.pkg.Filters.AuthFilter;

@WebServlet("/DeleteEmail")
public class DeleteEmailServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String sessionId = AuthFilter.SESSION_ID.get();
		UserSessions userSession = UserSessionCache.getSessionFromCache(sessionId);
		int userId = userSession.getUserId();
		String email = request.getParameter("email");
		try {
			if (UserDao.deleteUserEmail(userId, email)) {
				User user = UserDao.getUserById(userId);
				if (user != null) {
					user.getUserMails().removeIf(e -> e.getEmail().equals(email));
				}
				response.sendRedirect("profile.jsp");
			} else {
				response.sendRedirect("profile.jsp?message=Oops! That did not work. Try again.");
			}
		} catch (InvalidInputException e) {
			response.sendRedirect("profile.jsp?message=" + e.getMessage());
		} catch (IllegalArgumentException | IOException e) {
			response.sendRedirect("profile.jsp?message=An internal error occured. Please try again.");
		} catch (DBException e) {
			response.sendRedirect("profile.jsp?message=" + e.getMessage());
		}
	}

}
