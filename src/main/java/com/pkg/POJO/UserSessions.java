package com.pkg.POJO;


import java.util.Map;
@Table("user_sessions")
public class UserSessions extends DbPojo {
	
	@Key
	@Column("session_id")
	private String sessionId;
	@Key
	@Column("user_id")
	private int userId;
	@Column("session_creation_time")
	private long sessionCreationTime;
	@Column("last_access_time")
	private long lastAccessTime;
	public UserSessions() {
		
	}
	public UserSessions(String sessionId, int userId, long sessionCreationTime, long lastAccessTime) {
		this.sessionId = sessionId;
		this.userId = userId;
		this.sessionCreationTime = sessionCreationTime;
		this.lastAccessTime = lastAccessTime;
	}
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public long getSessionCreationTime() {
		return sessionCreationTime;
	}
	public void setSessionCreationTime(long sessionCreationTime) {
		this.sessionCreationTime = sessionCreationTime;
	}
	public long getLastAccessTime() {
		return lastAccessTime;
	}
	public void setLastAccessTime(long lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}
	@Override
	public Map<String, Object> getColValMap(){
		if(sessionId != null) {
		columnClassMap.put("session_id", sessionId);
		}
		if(userId != 0) {
		columnClassMap.put("user_id", userId);
		}
		if(sessionCreationTime != 0L) {
		columnClassMap.put("session_creation_time", sessionCreationTime);
		}
		if(lastAccessTime != 0L) {
		columnClassMap.put("last_access_time", lastAccessTime);
		}
		return columnClassMap;
	}
	

}
