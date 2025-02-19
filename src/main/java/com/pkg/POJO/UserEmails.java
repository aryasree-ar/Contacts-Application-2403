package com.pkg.POJO;


import java.util.Map;

import com.pkg.Exceptions.InvalidInputException;
import com.pkg.Util.InputValidator;
@Table("user_emails")
public class UserEmails extends DbPojo{
	@Key
	@Column("user_id")
	private int userId ;
	@Key
	@Column("email")
	private String email;
	@Column("is_primary")
	private Integer isPrimary;
	@Column("created_at")
	private Long createdAt;
	@Column("modified_at")
	private Long modifiedAt;
	
	public UserEmails() {
		
	}
	
	public UserEmails(String email,Integer isPrimary) throws InvalidInputException {
		InputValidator.validateEmail(email);
		this.email = email;
		if(isPrimary != 0 && isPrimary != 1) {
			throw new InvalidInputException("Invalid input");
		}
		this.isPrimary = isPrimary;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) throws InvalidInputException {
		InputValidator.validateEmail(email);
		this.email = email;
	}
	public Integer getIsPrimary() {
		return isPrimary;
	}
	public void setIsPrimary(Integer isPrimary) throws InvalidInputException {
		if(isPrimary != 0 && isPrimary != 1) {
			throw new InvalidInputException("Invalid input");
		}
		this.isPrimary = isPrimary;
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
	public Map<String, Object> getColValMap(){
		if(email != null) {
		columnClassMap.put("email", email);
		}
		if(isPrimary != null) {
		columnClassMap.put("is_primary", isPrimary);
		}
		if(userId != 0) {
		columnClassMap.put("user_id",userId);
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
