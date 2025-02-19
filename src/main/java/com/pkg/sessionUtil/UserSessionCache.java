package com.pkg.sessionUtil;

import java.util.HashMap;

import java.util.Map;
import java.util.Set;

import com.pkg.POJO.UserSessions;

public class UserSessionCache {
	private static Map<String, UserSessions> userSessionCache = new HashMap<>();
	
	//adding session
	public static void addSessionToCache(UserSessions userSession) {
		userSessionCache.put(userSession.getSessionId(), userSession);
	}
	
	//retrieving 
	public static UserSessions getSessionFromCache(String sessionId) {
		return userSessionCache.get(sessionId);
	}
	
	//removing
	public static void removeSessionFromCache(String sessionId) {
		userSessionCache.remove(sessionId);
	}
	
    //to get all session IDs
    public static Set<String> getAllSessionIds(){
    	return userSessionCache.keySet();
    }
    //clearing cache
    public static void clearAllSessions() {
        userSessionCache.clear();
    }
}
