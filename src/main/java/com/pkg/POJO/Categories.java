package com.pkg.POJO;

import java.util.Map;

@Table("categories")
public class Categories extends DbPojo {
	
	
	@Key
	@Column("user_id")
	private int userId;
	@Column("category_name")
	private String categoryName;
	@Key
	@Column("category_id")
	private int categoryId;
	@Column("created_at")
	private Long createdAt;
	@Column("modified_at")
	private Long modifiedAt;

	public Categories() {
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
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
		if (categoryName != null) {
			columnClassMap.put("category_name", categoryName);
		}
		if (categoryId != 0) {
			columnClassMap.put("category_id", categoryId);
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
