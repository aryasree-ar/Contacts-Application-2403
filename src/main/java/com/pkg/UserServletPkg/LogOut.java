package com.pkg.UserServletPkg;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.pkg.Dao.SessionDao;
import com.pkg.Exceptions.DBException;
import com.pkg.Exceptions.InvalidInputException;
import com.pkg.sessionUtil.UserSessionCache;
import com.pkg.Filters.AuthFilter;

@WebServlet("/LogOut")
public class LogOut extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String sessionId = AuthFilter.SESSION_ID.get();
		if (sessionId != null) {
			// remove session from db and cache
			UserSessionCache.removeSessionFromCache(sessionId);
			try {
				SessionDao.deleteSessionById(sessionId);
			} catch (IllegalArgumentException | IllegalAccessException | NullPointerException | InvalidInputException e) {
				response.sendRedirect("dashboard.jsp?message=An internal error occured.");
			} catch (DBException e) {
				response.sendRedirect("dashboard.jsp?message="+e.getMessage());
			}
			// remove session cookie
			Cookie sessionCookie = new Cookie("SESSION_ID", "");
			sessionCookie.setMaxAge(0);
			response.addCookie(sessionCookie);

		} else {
			response.sendRedirect("dashboard.jsp?message=Oops! That did not work. Please try again.");
		}
		response.sendRedirect("index.jsp");
	}

}
