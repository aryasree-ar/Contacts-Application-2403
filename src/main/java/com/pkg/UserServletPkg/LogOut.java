package com.pkg.UserServletPkg;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pkg.Dao.SessionDao;
import com.pkg.sessionUtil.GetSessionIdFromCookie;
import com.pkg.sessionUtil.UserSessionCache;


@WebServlet("/LogOut")
public class LogOut extends HttpServlet {
	
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
		//Cookie[] cookies = req.getCookies();
		String sessionId = GetSessionIdFromCookie.getSessionIdFromCookie(req);
//		if(cookies != null) {
//			for(Cookie cookie : cookies) {
//				if("SESSION_ID".equals(cookie.getName())) {
//					sessionId = cookie.getName();
//					break;
//				}
//			}
//		}
		
		if(sessionId != null) {
			//remove session from db and cache
			UserSessionCache.removeSessionFromCache(sessionId);
			try {
				SessionDao.deleteSessionById(sessionId);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//remove session cookie
			Cookie sessionCookie = new Cookie("SESSION_ID", "");
            sessionCookie.setMaxAge(0);
            res.addCookie(sessionCookie);
		}
		res.sendRedirect("index.jsp");
	}

}
