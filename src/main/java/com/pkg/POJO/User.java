package com.pkg.POJO;

import java.util.ArrayList;
import java.util.List;

import com.pkg.Exceptions.InvalidInputException;
import com.pkg.Util.InputValidator;

public class User {
	
	private int userId;
	private String userName;
	private String userPassword;
	private String userFirstName;
	private String userLastName;
	private String userPhone;
	private String userDOB;
	private String userLocation;
	private Long createdAt;
	private Long modifiedAt;
	private List<UserEmails> userEmails = new ArrayList<>();
	public UserEmails getPrimaryMail() {
		for(UserEmails userEmail: userEmails) {
			if(userEmail.getIsPrimary() == 1) {
				return userEmail;
			}
		}
		return null;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) throws InvalidInputException {
		if (userId < 0) {
			throw new InvalidInputException("User ID cannot be negative.");
		}
		this.userId = userId;
	}
	
	public List<UserEmails> getUserMails() {
		return userEmails;
	}
	public void setUserMails(List<UserEmails> userEmails) {
		this.userEmails = userEmails;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) throws InvalidInputException {
		InputValidator.validateSafeString(userName,"user_name");
		this.userName = userName;
	}
	
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	public String getUserFirstName() {
		return userFirstName;
	}
	public void setUserFirstName(String userFirstName) throws InvalidInputException {
		InputValidator.validateSafeString(userFirstName, "first_name");
		this.userFirstName = userFirstName;
	}
	
	public String getUserLastName() {
		return userLastName;
	}
	public void setUserLastName(String userLastName) throws InvalidInputException {
		InputValidator.validateSafeString(userLastName,"last_name");
		this.userLastName = userLastName;
	}
	
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) throws InvalidInputException {
		InputValidator.validatePhoneNumber(userPhone);
		this.userPhone = userPhone;
	}
	
	public String getUserDOB() {
		return userDOB;
	}
	public void setUserDOB(String userDOB) throws InvalidInputException {
		InputValidator.validateDate(userDOB);
		this.userDOB = userDOB;
	}
	
	public String getUserLocation() {
		return userLocation;
	}
	public void setUserLocation(String userLocation) throws InvalidInputException {
		InputValidator.validateSafeString(userLocation, "location");
		this.userLocation = userLocation;
	}
	
	public long getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}

	public long getModifiedAt() {
		return modifiedAt;
	}
	public void setModifiedAt(long modifiedAt) {
		this.modifiedAt = modifiedAt;
	}
	
	
	
}
