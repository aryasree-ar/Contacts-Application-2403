package com.pkg.POJO;

import java.util.HashMap;
import java.util.Map;

public class ContactDetails extends DbPojo {
	
	private int userID;
	private int contactID;
	private String nickName;
	private String contactDob;
	private String gender;
	private String place;
	private String contactEmail;
	private String contactPhone;
	
	public ContactDetails() {
		
	}
	
	public ContactDetails(int userID, int contactID, String nickName, String contactDob, String gender, String place, String contactEmail, String contactPhone) {
		
		this.userID = userID;
		this.contactID = contactID;
		this.nickName = nickName;
		this.contactDob = contactDob;
		this.gender = gender;
		this.place = place;
		this.contactEmail = contactEmail;
		this.contactPhone = contactPhone;
	}
	
	public int getUserID() {
		return userID;
	}
	public void setUserId(int userId) {
		this.userID = userId;
	}
	public int getContactId() {
		return contactID;
	}
	public void setContactId(int contactId) {
		this.contactID = contactId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getContactDob() {
		return contactDob;
	}
	public void setContactDob(String contactDob) {
		this.contactDob = contactDob;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	@Override
	public Map<String, Object> getColValMap(){
		if(userID != 0) {
		columnClassMap.put("userID", userID);
		}
		if(contactID != 0) {
		columnClassMap.put("contactID", contactID);
		}
		if(nickName != null) {
		columnClassMap.put("nickName", nickName);
		}
		if(contactDob != null) {
		columnClassMap.put("contactDob", contactDob);
		}
		if(gender != null) {
		columnClassMap.put("gender", gender);
		}
		if(place != null) {
		columnClassMap.put("place", place);
		}
		if(contactEmail != null) {
		columnClassMap.put("contactEmail", contactEmail);
		}
		if(contactPhone != null) {
		columnClassMap.put("contactPhone", contactPhone);
		}
		
		return columnClassMap;
	}
	public Map<String, Object> getPrimaryKey(){
    	Map<String, Object> primaryKey = new HashMap<>();
    	if(contactID != 0) {
    		primaryKey.put("contactID", contactID);
    	}
    	return primaryKey;
    }
}
