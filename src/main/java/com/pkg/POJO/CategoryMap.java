package com.pkg.POJO;

import java.util.Map;

public class CategoryMap extends DbPojo {
	private int categoryID ;
	private int contactID;

	public CategoryMap() {
		
	}

	public CategoryMap(int categoryID, int contactID) {
		this.categoryID = categoryID;
		this.contactID = contactID;
	}

	public Integer getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public int getContactID() {
		return contactID;
	}

	public void setContactID(int contactID) {
		this.contactID = contactID;
	}
	@Override
	public Map<String, Object> getColValMap(){
		if(categoryID != 0) {
		columnClassMap.put("categoryID", categoryID);
		}
		if(contactID != 0) {
		columnClassMap.put("contactID", contactID);
		}
		return columnClassMap;
	}
	
}
