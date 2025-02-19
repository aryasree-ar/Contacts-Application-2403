package com.pkg.UserServletPkg;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pkg.Dao.UserDao;
import com.pkg.Exceptions.DBException;
import com.pkg.Exceptions.InvalidInputException;
import com.pkg.POJO.User;
import com.pkg.POJO.UserEmails;
import com.pkg.POJO.UserSessions;
import com.pkg.sessionUtil.UserSessionCache;

import com.pkg.Filters.AuthFilter;

@WebServlet("/AddEmail")
public class AddEmailServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sessionId = AuthFilter.SESSION_ID.get();
		UserSessions userSession = UserSessionCache.getSessionFromCache(sessionId);
		int userId = userSession.getUserId();

		String email = request.getParameter("addemail");
		try {
			if (!UserDao.checkDuplicateMail(email)) {
				if (UserDao.addUserEmail(userId, email)) {
					User user = UserDao.getUserById(userId);
					if (user != null) {
						UserEmails newMail = new UserEmails(email, 0);
						user.getUserMails().add(newMail);
					}
					response.sendRedirect("profile.jsp");
				} else {
					response.sendRedirect("profile.jsp?message=Oops! That did not work. Try again.");
				}
			} else {
				response.sendRedirect("profile.jsp?message=Email already existis. Please use another email");
			}
		} catch (InvalidInputException e) {
			response.sendRedirect("profile.jsp?message=" + e.getMessage());
		} catch (  SQLException | NamingException | IllegalAccessException
				| IllegalArgumentException | SecurityException | NoSuchFieldException e) {
			response.sendRedirect("profile.jsp?message=An internal error occured. Please try again.");
		} catch (DBException e) {
			response.sendRedirect("profile.jsp?message=" + e.getMessage());
		}
	}

}
