package com.pkg.POJO;

import java.util.HashMap;
import java.util.Map;

public class DbPojo {
	protected Map<String, Object> columnClassMap;
	public DbPojo() {
		columnClassMap = new HashMap<String, Object>();
	}
	
	public Map<String, Object> getColValMap(){
		return columnClassMap;
	}
	
}
