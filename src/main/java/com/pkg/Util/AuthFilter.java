package com.pkg.Util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pkg.Dao.SessionDao;
import com.pkg.POJO.UserSessions;
import com.pkg.sessionUtil.GetSessionIdFromCookie;
import com.pkg.sessionUtil.UserSessionCache;

@WebFilter(urlPatterns = { "/*" })
public class AuthFilter implements Filter {

	public static final ThreadLocal<Integer> user_id = new ThreadLocal<Integer>();

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String sessionId = GetSessionIdFromCookie.getSessionIdFromCookie(httpRequest);
		String requestURI = httpRequest.getRequestURI();

		boolean isLoggedIn = (sessionId != null && !sessionId.isEmpty());
		boolean isPublicPage = requestURI.endsWith("/index.jsp") || requestURI.endsWith("/")
				|| requestURI.endsWith("signUp.jsp") || requestURI.endsWith("/LoginServlet")
				|| requestURI.endsWith("/SignUpServlet");

		// not logged in and accessing non - public pages
		if (!isLoggedIn && !isPublicPage) {
			httpResponse.sendRedirect("index.jsp");
			return;
		}

		if (isLoggedIn) {
			UserSessions userSession = UserSessionCache.getSessionFromCache(sessionId);

			long currentTime = System.currentTimeMillis();

			// add the session to the cache if not present
			if (userSession == null) {// for cache
				userSession = SessionDao.getSessionById(sessionId);

				// else remove from cookie(session invalidated)

				if (userSession == null) {// for db
					Cookie sessionCookie = new Cookie("SESSION_ID", null);
					sessionCookie.setMaxAge(0);
					httpResponse.addCookie(sessionCookie);
					httpResponse.sendRedirect("index.jsp");
					return;
				}
				// add to cache

			}

			// check the session expiry
			if (userSession != null && (currentTime - userSession.getLast_access_time() > 30 * 60 * 1000)) {
				// remove the expired sessions
				UserSessionCache.removeSessionFromCache(sessionId);
				try {
					SessionDao.deleteSessionById(sessionId);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// invalidate session cookie
				Cookie cookie = new Cookie("SESSION_ID", null);
				cookie.setMaxAge(0);
				cookie.setPath("/");
				httpResponse.addCookie(cookie);

				httpResponse.sendRedirect("index.jsp");
				return;

			}

			if (userSession != null) {
				// update the last access time for valid session and set UID in the thread local
				int userId = userSession.getUserID();
				user_id.set(userId);
				userSession.setLast_access_time(currentTime);
				UserSessionCache.addSessionToCache(userSession);

			}
		}

		// User is logged in and is trying to access a public page
		if (isLoggedIn  && isPublicPage) {
			httpResponse.sendRedirect("dashboard.jsp");
			return;
		}

		// Allow the request to continue if no redirect conditions were met
		chain.doFilter(request, response);
		// chain.doFilter(request, response);
		user_id.remove();
	}
}
