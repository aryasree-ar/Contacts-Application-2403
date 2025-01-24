package com.pkg.POJO;

import java.util.ArrayList;
import java.util.List;

public class User {
	private int userId;
	private String userName;
	private String userPassword;
	private String userFirstName;
	private String userLastName;
	private String userPhone;
	private String userDOB;
	private String userLocation;
	private List<UserEmails> userEmails = new ArrayList<>();
	//private List<contacts> userContacts = 
	//userID
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	//userEmail
	public List<UserEmails> getUserMails() {
		return userEmails;
	}
	public void setUserMails(List<UserEmails> userEmails) {
		this.userEmails = userEmails;
	}
	
	//userName
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	//password
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	//userFirstName
	public String getUserFirstName() {
		return userFirstName;
	}
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}
	
	//userLastName
	public String getUserLastName() {
		return userLastName;
	}
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}
	
	//userPhone
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	
	//userDOB
	public String getUserDOB() {
		return userDOB;
	}
	public void setUserDOB(String userDOB) {
		this.userDOB = userDOB;
	}
	
	//userLocation
	public String getUserLocation() {
		return userLocation;
	}
	public void setUserLocation(String userLocation) {
		this.userLocation = userLocation;
	}
	
	
	
}
