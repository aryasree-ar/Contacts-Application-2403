package com.pkg.Dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Set;

import org.bouncycastle.pqc.legacy.crypto.qtesla.QTESLAKeyGenerationParameters;

import com.pkg.POJO.UserSessions;
import com.pkg.Util.DBConnection;
import com.pkg.queryGenerator.QueryGenerator;
import com.pkg.sessionUtil.UserSessionCache;

public class SessionDao {
	
	//for session creation 
	public static boolean createSession(UserSessions userSession) {

		boolean result = false;
		try {
			QueryGenerator queryGenerator = new QueryGenerator();
			result = queryGenerator.insert(userSession) > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	//update the last accessed time
	public static boolean updateLastAccessTime(Set<String> sessionIds) {
		int result = 0;
		for(String sessionId: sessionIds) {
			UserSessions userSession = UserSessionCache.getSessionFromCache(sessionId);
			UserSessions us = new UserSessions();
			us.setSessionID(sessionId);
			QueryGenerator qg = new QueryGenerator();
			result = qg.update(userSession,us);
			
		}

		return result > 0 ;
	}
	
	//returning sessions
	public static UserSessions getSessionById(String sessionId) {
		
		//fetching session from cache
		UserSessions userSession = UserSessionCache.getSessionFromCache(sessionId);
		if(userSession != null) {
			return userSession;
		}
		
		//fetching from db since session not found in cache
		String query = "select * from user_sessions where sessionID = ?";
		try(Connection c = DBConnection.connect();
				PreparedStatement p = c.prepareStatement(query)){
			p.setString(1, sessionId);
			ResultSet rs = p.executeQuery();
			if(rs.next()) {
				userSession = new UserSessions(sessionId, 
						rs.getInt("userID"), 
						rs.getLong("session_creation_time"), 
						rs.getLong("last_access_time"));
				
				UserSessionCache.addSessionToCache(userSession);
				return userSession;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void deleteSessionById(String sessionId) throws IllegalArgumentException, IllegalAccessException {
		//removing session from cache
		UserSessionCache.removeSessionFromCache(sessionId);
		
		UserSessions userSessionObj = new UserSessions();
		userSessionObj.setSessionID(sessionId);
		if(userSessionObj != null) {
			QueryGenerator queryGenerator = new QueryGenerator();
			queryGenerator.delete(userSessionObj);
		}
		
		
		
	}
	
	public static int deleteExpiredSessions(long currentTime) {
		//check in the cache
		int result = -1;
		for(String sessionId:UserSessionCache.getAllSessionIds()) {
			UserSessions userSession = UserSessionCache.getSessionFromCache(sessionId);
			if(userSession != null && (currentTime - userSession.getLast_access_time() > 30 * 60 * 1000)){
				UserSessionCache.removeSessionFromCache(sessionId);
				result = 1;
			}
		}
		
		//check in the DB
		String query = "select * from user_sessions where last_access_time < ?";
		try(Connection c = DBConnection.connect();
				PreparedStatement p = c.prepareStatement(query)){
			p.setLong(1, currentTime - (30 * 60 * 1000));
			ResultSet rs = p.executeQuery();
			while(rs.next()) {
				result = 2;
				String sessionId = rs.getString("sessionID");
				UserSessions userSessionObj = new UserSessions();
				userSessionObj.setSessionID(sessionId);
				if(userSessionObj != null) {
					QueryGenerator queryGenerator = new QueryGenerator();
					queryGenerator.delete(userSessionObj);
					result = 3;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
}
