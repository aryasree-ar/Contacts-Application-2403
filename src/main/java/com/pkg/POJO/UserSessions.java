package com.pkg.POJO;

import java.util.HashMap;
import java.util.Map;

public class UserSessions extends DbPojo {
	
	private String sessionID;
	private int userID;
	private long session_creation_time;
	private long last_access_time;
	public UserSessions() {
		
	}
	public UserSessions(String sessionID, int userID, long session_creation_time, long last_access_time) {
		
		this.sessionID = sessionID;
		this.userID = userID;
		this.session_creation_time = session_creation_time;
		this.last_access_time = last_access_time;
	}
	
	public String getSessionID() {
		return sessionID;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public long getSession_creation_time() {
		return session_creation_time;
	}
	public void setSession_creation_time(long session_creation_time) {
		this.session_creation_time = session_creation_time;
	}
	public long getLast_access_time() {
		return last_access_time;
	}
	public void setLast_access_time(long last_access_time) {
		this.last_access_time = last_access_time;
	}
	@Override
	public Map<String, Object> getColValMap(){
		if(sessionID != null) {
		columnClassMap.put("sessionID", sessionID);
		}
		if(userID != 0) {
		columnClassMap.put("userID", userID);
		}
		if(session_creation_time != 0L) {
		columnClassMap.put("session_creation_time", session_creation_time);
		}
		if(last_access_time != 0L) {
		columnClassMap.put("last_access_time", last_access_time);
		}
		return columnClassMap;
	}
	
	public Map<String, Object> getPrimaryKey(){
    	Map<String, Object> primaryKey = new HashMap<>();
    	if(sessionID != null || !(sessionID.isEmpty())) {
    		primaryKey.put("sessionID", sessionID);
    	}
    	return primaryKey;
    }
}
