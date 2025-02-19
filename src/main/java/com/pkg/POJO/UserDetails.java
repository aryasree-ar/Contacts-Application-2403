package com.pkg.POJO;

import java.util.Map;

import com.pkg.Exceptions.InvalidInputException;
import com.pkg.Util.InputValidator;

@Table("user_details")
public class UserDetails extends DbPojo {
	
	@Key
	@Column("user_id")
	private int userId;
	@Column("user_name")
	private String userName;
	@Column("password")
	private String password;
	@Column("first_name")
	private String firstName;
	@Column("last_name")
	private String lastName;
	@Column("phone_number")
	private String phoneNumber;
	@Column("dob")
	private String dob;
	@Column("location")
	private String location;
	@Column("password_version")
	private Integer passwordVersion;
	@Column("created_at")
	private Long createdAt;
	@Column("modified_at")
	private Long modifiedAt;

	public UserDetails() {

	}

	public UserDetails(int userId, String userName, String password, String firstName, String lastName,
			String phoneNumber, String dob, String location, int passwordVersion) {

		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.dob = dob;
		this.location = location;
		this.passwordVersion = passwordVersion;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) throws InvalidInputException {
		InputValidator.validateSafeString(userName,"user_name");
		this.userName = userName.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) throws InvalidInputException {
		InputValidator.validateSafeString(firstName, "first_name");
		this.firstName = firstName.trim();
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) throws InvalidInputException{
		InputValidator.validateSafeString(lastName,"last_name");
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) throws InvalidInputException{
		InputValidator.validatePhoneNumber(phoneNumber);
		this.phoneNumber = phoneNumber;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) throws InvalidInputException{
		InputValidator.validateDate(dob);
		this.dob = dob;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) throws InvalidInputException{
		InputValidator.validateSafeString(location, "location");
		this.location = location;
	}

	public int getPasswordVersion() {
		return passwordVersion;
	}

	public void setPasswordVersion(int passwordVersion) {
		this.passwordVersion = passwordVersion;
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

	@Override
	public Map<String, Object> getColValMap() {
		if (userId != 0) {
			columnClassMap.put("user_id", userId);
		}
		if (userName != null) {
			columnClassMap.put("user_name", userName);
		}
		if (password != null) {
			columnClassMap.put("password", password);
		}
		if (firstName != null) {
			columnClassMap.put("first_name", firstName);
		}
		if (lastName != null) {
			columnClassMap.put("last_name", lastName);
		}
		if (phoneNumber != null) {
			columnClassMap.put("phone_number", phoneNumber);
		}
		if (dob != null) {
			columnClassMap.put("dob", dob);
		}
		if (location != null) {
			columnClassMap.put("location", location);
		}
		if (passwordVersion != null) {
			columnClassMap.put("password_version", passwordVersion);
		}
		if (createdAt != null) {
			columnClassMap.put("created_at", createdAt);
		}
		if (modifiedAt != null) {
			columnClassMap.put("modified_at", modifiedAt);
		}
		return columnClassMap;
	}

}
