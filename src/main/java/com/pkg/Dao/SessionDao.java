package com.pkg.Dao;

import java.util.List;
import java.util.Set;

import com.pkg.Exceptions.DBException;
import com.pkg.Exceptions.InvalidInputException;
import com.pkg.POJO.UserSessions;
import com.pkg.queryGenerator.QueryExecutor;
import com.pkg.sessionUtil.UserSessionCache;

public class SessionDao {

	public static boolean createSession(UserSessions userSession) throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException, InvalidInputException, Exception {
		if (userSession == null) {
			throw new InvalidInputException("Invalid session detail.Please try again.");
		}
		QueryExecutor queryExecutorObject = new QueryExecutor();
		if (queryExecutorObject.executeInsert(userSession, false) <= 0) {
			throw new Exception("Oops! That did not work.Please try again.");
		}
		return true;
	}

	public static boolean updateLastAccessTime(Set<String> sessionIds) throws DBException, Exception {
		if(sessionIds == null) {
			throw new InvalidInputException("Invalid session detail.Please try again.");
		}
		int result = 0;
		for (String sessionId : sessionIds) {
			UserSessions userSession = UserSessionCache.getSessionFromCache(sessionId);
			QueryExecutor queryExecutorObject = new QueryExecutor();
			if(queryExecutorObject.executeUpdate(userSession) <= 0) {
				throw new Exception("Oops! That did not work.Please try again.");
			}
		}
		return result > 0;
	}

	public static UserSessions getSessionById(String sessionId) throws InvalidInputException, DBException {
		UserSessions userSession = UserSessionCache.getSessionFromCache(sessionId);
		if (userSession != null) {
			return userSession;
		} else {
			userSession = new UserSessions();
			userSession.setSessionId(sessionId);
			QueryExecutor queryExecutorObject = new QueryExecutor();
			List<UserSessions> userSessions = queryExecutorObject.executeSelect(userSession).stream()
					.filter(UserSessions.class::isInstance).map(UserSessions.class::cast).toList();
			if (userSessions != null && userSessions.size() > 0) {
				return userSessions.get(0);
			} else {
				return null;
			}
		}
	}

	public static void deleteSessionById(String sessionId)
			throws IllegalArgumentException, IllegalAccessException, InvalidInputException, DBException {
		UserSessionCache.removeSessionFromCache(sessionId);
		UserSessions userSessionObj = new UserSessions();
		if (sessionId != null) {
			userSessionObj.setSessionId(sessionId);
			QueryExecutor queryExecutorObject = new QueryExecutor();
			queryExecutorObject.executeDelete(userSessionObj);
		} else {
			throw new NullPointerException();
		}
	}

	public static void deleteExpiredSessions(long currentTime) throws InvalidInputException, DBException {
		for (String sessionId : UserSessionCache.getAllSessionIds()) {
			UserSessions userSession = UserSessionCache.getSessionFromCache(sessionId);
			if (userSession != null && (currentTime - userSession.getLastAccessTime() > 30 * 60 * 1000)) {
				UserSessionCache.removeSessionFromCache(sessionId);
			}
		}

		QueryExecutor queryExecutorObject = new QueryExecutor();
		UserSessions userSessionObject = new UserSessions();
		List<UserSessions> userSessions = queryExecutorObject.executeSelect(userSessionObject).stream()
				.filter(UserSessions.class::isInstance).map(UserSessions.class::cast).toList();
		for (UserSessions userSession : userSessions) {
			if (userSession.getLastAccessTime() < currentTime - (30 * 60 * 1000)) {
				queryExecutorObject.executeDelete(userSession);
			}
		}
	}

}
