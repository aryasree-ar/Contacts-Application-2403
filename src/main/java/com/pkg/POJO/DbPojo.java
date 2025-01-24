package com.pkg.POJO;

import java.util.HashMap;
import java.util.Map;

public class DbPojo {
	protected Map<String, Object> columnClassMap;
	public DbPojo() {
		columnClassMap = new HashMap<String, Object>();
	}
//	
//	public DbPojo(String columnName, Object value) {
//		columnClassMap.put(columnName, value);
//	}
	
	public Map<String, Object> getColValMap(){
		return columnClassMap;
	}
	public Map<String, Object> getPrimaryKey(){
    	Map<String, Object> primaryKey = new HashMap<>();
    	return primaryKey;
    }
	
	
	
}
