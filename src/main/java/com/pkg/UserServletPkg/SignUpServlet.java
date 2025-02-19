package com.pkg.UserServletPkg;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.pkg.Dao.SessionDao;
import com.pkg.Dao.UserDao;
import com.pkg.Exceptions.InvalidInputException;
import com.pkg.POJO.User;
import com.pkg.POJO.UserEmails;
import com.pkg.POJO.UserSessions;
import com.pkg.Util.PasswordUtil;
import com.pkg.sessionUtil.SessionIdGenerator;
import com.pkg.sessionUtil.UserSessionCache;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		User user = new User();
		try {
			user.setUserName(request.getParameter("sign-up-name"));
			user.setUserPassword(PasswordUtil.hashPassword(request.getParameter("sign-up-password")));
			user.setUserFirstName(request.getParameter("sign-up-frist-name"));
			user.setUserLastName(request.getParameter("sign-up-last-name"));
			user.setUserPhone(request.getParameter("sign-up-phone-number"));
			user.setUserDOB(request.getParameter("sign-up-dob"));
			user.setUserLocation(request.getParameter("sign-up-location"));

			List<UserEmails> userEmails = new ArrayList<>();
			String primaryEmail = request.getParameter("sign-up-email");
			userEmails.add(new UserEmails(primaryEmail, 1));
			user.setUserMails(userEmails);

			if (UserDao.checkDuplicateMail(primaryEmail)) {
				response.sendRedirect(
						"signUp.jsp?message=An account with this email already exists. Please use a different email.");
				return;
			}
			int result = UserDao.insertUserDetails(user, 1);

			if (result > 0) {
				String sessionId = SessionIdGenerator.getSessionId();
				UserSessions userSession = new UserSessions(sessionId, result, Instant.now().toEpochMilli(),
						Instant.now().toEpochMilli());
				// adding the session to the cache and db
				UserSessionCache.addSessionToCache(userSession);
				SessionDao.createSession(userSession);
				// setting in cookie
				Cookie sessionCookie = new Cookie("SESSION_ID", sessionId);
				sessionCookie.setHttpOnly(true);
				response.addCookie(sessionCookie);
				response.sendRedirect("dashboard.jsp?message=Successfully signed up");

			} else {
				response.sendRedirect("signUp.jsp?message=Signup failed, please try again");
			}

		} catch (InvalidInputException e) {
			response.sendRedirect("signUp.jsp?message=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
		} catch (NamingException | SQLException | NoSuchFieldException | SecurityException | IllegalArgumentException
				| IllegalAccessException e) {
			response.sendRedirect("signUp.jsp?message=An internal error occurred, please try again later.");
		} catch (Exception e) {
			response.sendRedirect("signUp.jsp?message=" + e.getMessage());
		}

	}

}
