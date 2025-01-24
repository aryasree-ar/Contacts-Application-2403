package com.pkg.POJO;

import java.util.HashMap;
import java.util.Map;

public class UserEmails extends DbPojo{
	@Override
	public String toString() {
		return "userEmails [userID=" + userID + ", email=" + email + ", isPrimary=" + isPrimary + "]";
	}


	private int userID ;
	private String email;
	private Integer isPrimary;
	
	public UserEmails() {
		
	}
	
	public UserEmails(String email,Integer isPrimary) {
		this.email = email;
		this.isPrimary = isPrimary;
	}
	
	public int getUserId() {
		return userID;
	}
	public void setUserId(int userID) {
		this.userID = userID;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getIsPrimary() {
		return isPrimary;
	}
	public void setIsPrimary(Integer isPrimary) {
		this.isPrimary = isPrimary;
	}
	

	@Override
	public Map<String, Object> getColValMap(){
		if(email != null) {
		columnClassMap.put("email", email);
		}
		if(isPrimary != null) {
		columnClassMap.put("isPrimary", isPrimary);
		}
		if(userID != 0) {
		columnClassMap.put("userID",userID);
		}
		return columnClassMap;
	}
	
	
	
	
}
