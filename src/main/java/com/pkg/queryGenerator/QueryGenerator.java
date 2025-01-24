package com.pkg.queryGenerator;


import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pkg.POJO.DbPojo;
import com.pkg.POJO.UserEmails;

public class QueryGenerator {
	public boolean isString(Object object) {
		if(object.getClass() == String.class) {
			return true;
		}
		return false;
	}
	 
	//DELETE
	public int delete(DbPojo obj) throws IllegalArgumentException, IllegalAccessException {
		int result = -1;
		StringBuilder query = new StringBuilder("DELETE FROM ");
		// get all the fields
		query.append(obj.getClass().getSimpleName());
		
		query.append(" WHERE ");
		
		Map<String, Object> columnValueMap = obj.getColValMap();

		StringJoiner colAndValJoiner = new StringJoiner(" AND ");
		
		for (Map.Entry<String, Object> entry : columnValueMap.entrySet()) {
			colAndValJoiner.add(entry.getKey() + " = "+(isString(entry.getValue())? "'"+entry.getValue()+"'":entry.getValue()));
		}
		
		query.append(colAndValJoiner.toString());
		result = QueryExecutor.queryExecutor(query.toString(), false);
		
		if(result > 0) {
			JsonObject oldValues = new Gson().toJsonTree(obj).getAsJsonObject();
			
		}
		
		return result;
	}

	//INSERT
	public int insert(DbPojo obj) throws IllegalArgumentException, IllegalAccessException {
		int result = -1;
		StringBuilder query = new StringBuilder("INSERT INTO ");

		query.append(obj.getClass().getSimpleName());
		// adding the columns to the query

		Map<String, Object> columnValueMap = obj.getColValMap();

		StringJoiner columnJoiner = new StringJoiner(",","(", ")");
		StringJoiner valueJoiner = new StringJoiner(",","(",")");

		for (Map.Entry<String, Object> entry : columnValueMap.entrySet()) {
			columnJoiner.add(entry.getKey());
			valueJoiner.add( isString(entry.getValue())?"'"+entry.getValue()+"'":entry.getValue().toString());
		}
	
		query.append(columnJoiner.toString());
		query.append(" VALUES ");
		query.append(valueJoiner.toString());

		// query execution
		result = QueryExecutor.queryExecutor(query.toString(),true);

		return result;
	}
	
	//UPDATE
	public int update(DbPojo obj,DbPojo conditionObj) {
		StringBuilder query = new StringBuilder("UPDATE ");
		query.append(obj.getClass().getSimpleName()+" SET ");
		
		//System.out.println(obj.getClass().getSimpleName());//
		
		StringJoiner commaHandler = new StringJoiner(" , ");
		Map<String, Object> colValMap = obj.getColValMap();
		
		for(Map.Entry<String, Object> entry:colValMap.entrySet()) {
			commaHandler.add(entry.getKey()+" = "+(isString(entry.getValue())?"'"+entry.getValue()+"'":entry.getValue()));
			System.out.println(entry.getKey()+":"+entry.getValue());
		}
		
		query.append(commaHandler+" WHERE ");
		
		Map<String, Object> conditionsMap = conditionObj.getColValMap();
		
		commaHandler = new StringJoiner(" AND ");
		for(Map.Entry<String, Object> entry: conditionsMap.entrySet()) {
			commaHandler.add(entry.getKey()+" = "+(isString(entry.getValue())?"'"+entry.getValue()+"'":entry.getValue()));
		}
		query.append(commaHandler);
		//query.append(commaHandler+" WHERE "+primaryKey.getKey()+" = "+(isString(primaryKey.getValue())?"'"+primaryKey.getValue()+"'":primaryKey.getValue()));
		int result = QueryExecutor.queryExecutor(query.toString(), false);
		System.out.println(query);
		
		return result;
	}

	
	public List<DbPojo> select(DbPojo object){
		StringBuilder query = new StringBuilder("SELECT * FROM ");
		query.append(object.getClass().getSimpleName()+" WHERE ");
		StringJoiner colAndValJoiner = new StringJoiner(" AND ");
		
		Map<String,Object> colValMap = object.getColValMap();
		for(Map.Entry<String, Object> entry : colValMap.entrySet()) {
			colAndValJoiner.add(entry.getKey() + " = "+(isString(entry.getValue())? "'"+entry.getValue()+"'":entry.getValue()));
		}
		query.append(colAndValJoiner.toString());
		List<DbPojo> resultset = QueryExecutor.queryExecutor(query.toString(), (Class<? extends DbPojo>)object.getClass());
		
		System.out.println(query);
		return resultset;
	}

//	public static void main(String[] args) {
//		QueryGenerator qg = new QueryGenerator();
//		userEmails mail = new  userEmails();
//		mail.setUserId(16);
//	
//		List<DbPojo> mails = qg.select(mail);
//		System.out.println(mails);
//	}
}
