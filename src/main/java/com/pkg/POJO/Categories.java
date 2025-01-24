package com.pkg.POJO;


import java.util.HashMap;
import java.util.Map;

public class Categories extends DbPojo {
    private int userID ;
    private String categoryName;
    private int categoryID;
    
    public Categories(int userID, String categoryName, int categoryID) {
        this.userID = userID;
        this.categoryName = categoryName;
        this.categoryID = categoryID;
    }
    public Categories() {
    }

    

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
    	
        this.userID = userID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
    
    @Override 
    public Map<String, Object> getColValMap()
    {
    	if(userID != 0) {
    	 columnClassMap.put("userID", userID);
    	}
    	if(categoryName != null) {
         columnClassMap.put("categoryName", categoryName);
    	}
    	if(categoryID != 0) {
         columnClassMap.put("categoryID", categoryID);
    	}
         return columnClassMap;
    }
    
    public Map<String, Object> getPrimaryKey(){
    	Map<String, Object> primaryKey = new HashMap<>();
    	if(categoryID != 0) {
    		primaryKey.put("categoryID", categoryID);
    	}
    	return primaryKey;
    }
    
   
}
