package com.pkg.sessionUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class GetSessionIdFromCookie {
	public static String getSessionIdFromCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("SESSION_ID".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
