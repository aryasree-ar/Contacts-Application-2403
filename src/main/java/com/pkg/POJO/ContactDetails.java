package com.pkg.POJO;

import java.util.Map;

@Table("contact_details")
public class ContactDetails extends DbPojo {
	@Key
	@Column("user_id")
	private int userId;
	@Key
	@Column("contact_id")
	private int contactId;
	@Column("nick_name")
	private String nickName;
	@Column("contact_dob")
	private String contactDob;
	@Column("gender")
	private String gender;
	@Column("place")
	private String place;
	@Column("contact_email")
	private String contactEmail;
	@Column("contact_phone")
	private String contactPhone;
	@Column("created_at")
	private Long createdAt;
	@Column("modified_at")
	private Long modifiedAt;

	public ContactDetails() {

	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getContactId() {
		return contactId;
	}

	public void setContactId(int contactId) {
		this.contactId = contactId;
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
		if (contactId != 0) {
			columnClassMap.put("contact_id", contactId);
		}
		if (nickName != null) {
			columnClassMap.put("nick_name", nickName);
		}
		if (contactDob != null) {
			columnClassMap.put("contact_dob", contactDob);
		}
		if (gender != null) {
			columnClassMap.put("gender", gender);
		}
		if (place != null) {
			columnClassMap.put("place", place);
		}
		if (contactEmail != null) {
			columnClassMap.put("contact_email", contactEmail);
		}
		if (contactPhone != null) {
			columnClassMap.put("contact_phone", contactPhone);
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
