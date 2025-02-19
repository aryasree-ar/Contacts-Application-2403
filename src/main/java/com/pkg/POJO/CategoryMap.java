package com.pkg.POJO;

import java.util.Map;
@Table("category_map")
public class CategoryMap extends DbPojo {
	@Key
	@Column("category_id")
	private int categoryId ;
	@Key
	@Column("contact_id")
	private int contactId;
	@Column("created_at")
	private Long createdAt;
	@Column("modified_at")
	private Long modifiedAt;

	public CategoryMap() {
		
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getContactId() {
		return contactId;
	}

	public void setContactId(int contactId) {
		this.contactId = contactId;
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
		if(categoryId != 0) {
		columnClassMap.put("category_id", categoryId);
		}
		if(contactId != 0) {
		columnClassMap.put("contact_id", contactId);
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
