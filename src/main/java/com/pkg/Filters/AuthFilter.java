package com.pkg.Filters;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pkg.Dao.SessionDao;
import com.pkg.Exceptions.DBException;
import com.pkg.Exceptions.InvalidInputException;
import com.pkg.POJO.UserSessions;
import com.pkg.sessionUtil.SessionCookieUtil;
import com.pkg.sessionUtil.UserSessionCache;

@WebFilter(urlPatterns = { "/*" })
public class AuthFilter implements Filter {

	public static final ThreadLocal<Integer> USER_ID = new ThreadLocal<Integer>();
	public static final ThreadLocal<String> SESSION_ID = new ThreadLocal<String>();
	public static final ThreadLocal<String> URI = new ThreadLocal<String>();
	private static final Set<String> entryPoints = new HashSet<>();
	
	private boolean logInStatus(String sessionId) {
		return sessionId != null && !sessionId.isEmpty();
	}
	private boolean pageStatus(String requestURI) {
		return entryPoints.contains(requestURI);
	}
	@Override
	public void init(FilterConfig fConfig) {
		entryPoints.add("/ContactsApp/index.jsp");
		entryPoints.add("/ContactsApp/signUp.jsp");
		entryPoints.add("/ContactsApp/LoginServlet");
		entryPoints.add("/ContactsApp/SignUpServlet");
		entryPoints.add("/ContactsApp/notify");
		entryPoints.add("/ContactsApp/");
	}
	
	private void invalidateCookie(HttpServletResponse httpResponse) throws IOException {
		Cookie sessionCookie = new Cookie("SESSION_ID", null);
		sessionCookie.setMaxAge(0);
		httpResponse.addCookie(sessionCookie);
		httpResponse.sendRedirect("index.jsp");
	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String sessionId = SessionCookieUtil.getSessionIdFromCookie(httpRequest);
		String requestURI = httpRequest.getRequestURI();
		boolean isLoggedIn = logInStatus(sessionId);
		boolean isPublicPage = pageStatus(requestURI);
		
		if(!isLoggedIn && "ContactsApp/notify".equals(requestURI)) {
			chain.doFilter(httpRequest, httpResponse);
			return;
		}
		
		if (!isLoggedIn && !isPublicPage) {
			httpResponse.sendRedirect("index.jsp");
			return;
		}
		if (isLoggedIn) {
			UserSessions userSession = UserSessionCache.getSessionFromCache(sessionId);
			long currentTime = System.currentTimeMillis();
			if (userSession == null) {
				try {
					userSession = SessionDao.getSessionById(sessionId);
				} catch (InvalidInputException e) {
					e.printStackTrace();
				} catch (DBException e) {
					e.printStackTrace();
				}
				if (userSession == null) {
					invalidateCookie(httpResponse);
					return;
				}
			}
			if (userSession != null && (currentTime - userSession.getLastAccessTime() > 30 * 60 * 1000)) {
				UserSessionCache.removeSessionFromCache(sessionId);
				try {
					SessionDao.deleteSessionById(sessionId);
				} catch (IllegalArgumentException | IllegalAccessException | InvalidInputException e) {
					e.printStackTrace();
				} catch (DBException e) {
					e.printStackTrace();
				}
				invalidateCookie(httpResponse);
				return;
			}
			if (userSession != null) {
				int userId = userSession.getUserId();
				USER_ID.set(userId);
				SESSION_ID.set(sessionId);
				URI.set(requestURI);
				userSession.setLastAccessTime(currentTime);
				UserSessionCache.addSessionToCache(userSession);
			}
		}
		if (isLoggedIn && isPublicPage) {
			httpResponse.sendRedirect("dashboard.jsp");
			return;
		}
		chain.doFilter(request, response);
		USER_ID.remove();
		SESSION_ID.remove();
		URI.remove();
	}
}
