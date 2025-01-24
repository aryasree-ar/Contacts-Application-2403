package com.pkg.POJO;

import java.util.HashMap;
import java.util.Map;

public class UserDetails extends DbPojo {
	
	private int userID;
	private String user_name;
	private String password;
	private String first_name;
	private String last_name;
	private String phone_number;
	private String dob;
	private String location;
	private Integer hash_code;
	
	public UserDetails() {
		
	}
	
	public UserDetails(int userID, String user_name, String password, String first_name, String last_name,
			String phone_number, String dob, String location, int hash_code) {
		
		this.userID = userID;
		this.user_name = user_name;
		this.password = password;
		this.first_name = first_name;
		this.last_name = last_name;
		this.phone_number = phone_number;
		this.dob = dob;
		this.location = location;
		this.hash_code = hash_code;
	}
	
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getHash_code() {
		return hash_code;
	}
	public void setHash_code(int hash_code) {
		this.hash_code = hash_code;
	}
	@Override
	public Map<String, Object> getColValMap(){
		if(userID != 0) {
		columnClassMap.put("userID", userID);
		}
		if(user_name != null) {
		columnClassMap.put("user_name", user_name);
		}
		if(password != null) {
		columnClassMap.put("password", password);
		}
		if(first_name != null) {
		columnClassMap.put("first_name", first_name);
		}
		if(last_name != null) {
		columnClassMap.put("last_name", last_name);
		}
		if(phone_number != null) {
		columnClassMap.put("phone_number", phone_number);
		}
		if(dob != null) {
		columnClassMap.put("dob", dob);
		}
		if(location != null) {
		columnClassMap.put("location", location);
		}
		if(hash_code != null) {
		columnClassMap.put("hash_code", hash_code);
		}
		
		return columnClassMap;
	}
	
	
	public Map<String, Object> getPrimaryKey(){
    	Map<String, Object> primaryKey = new HashMap<>();
    	System.out.println("userID from getPrimaryKey:"+ userID);
    	if(userID != 0) {
    		primaryKey.put("userID", userID);
    	}
    	return primaryKey;
    }
	
}
