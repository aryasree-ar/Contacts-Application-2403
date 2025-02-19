package com.pkg.queryGenerator;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import com.pkg.Dao.SessionDao;
import com.pkg.POJO.ContactDetails;
import com.pkg.POJO.DbPojo;
import com.pkg.POJO.Key;
import com.pkg.POJO.Table;
import com.pkg.POJO.UserEmails;
import com.pkg.POJO.UserSessions;
import com.pkg.Util.NotifyServers;

public class Test {
//	private String getTableName(Class<? extends DbPojo> clazz) {
//		if (clazz.isAnnotationPresent(Table.class)) {
//			Table tableName = clazz.getAnnotation(Table.class);
//			return tableName.value();
//		}
//		return null;
//	}
//	public String generateDeleteQuery(DbPojo object) {
//		if (object == null) {
//			throw new IllegalArgumentException("The DbPojo object cannot be null.");
//		}
//		StringBuilder query = new StringBuilder("DELETE FROM ");
//		query.append(getTableName(object.getClass()));
//		query.append(" WHERE ");
//		Map<String, Object> fieldValues = object.getColValMap();
//		StringJoiner conditionClauseJoiner = new StringJoiner(" AND ");
//		for (Map.Entry<String, Object> fieldEntry : fieldValues.entrySet()) {
//			conditionClauseJoiner.add(fieldEntry.getKey() + " = ? ");
//		}
//		query.append(conditionClauseJoiner.toString());
//		System.out.println(query);
//		return query.toString();
//	}
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, SecurityException, URISyntaxException, IOException, InterruptedException {
//		SqlQueryGenerator qg = new SqlQueryGenerator();
//		UserSessions us = new UserSessions();
//		System.out.println("from test");
//		us.setSessionId("21");
//		us.setUserId(1);
//		System.out.println(qg.generateInsertQuery(us));
//		Test t = new Test();
//		UserEmails mail = new UserEmails();
//		mail.setEmail("a@gmail.com");
//		mail.setIsPrimary(0);
//		mail.setUserId(16);
//		t.arya(mail);
//	}
//	
//	public DbPojo arya(DbPojo pojo) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
//		DbPojo oldObject = pojo.getClass().getDeclaredConstructor().newInstance();
//		Field[] fields = oldObject.getClass().getDeclaredFields();
//		for(Field field:fields) {
//			field.setAccessible(true);
//			if(field.isAnnotationPresent(Key.class) && field.get(pojo) != null) {
//				field.set(oldObject, field.get(pojo));
//			}
//		}
//		List<DbPojo> resultSetPojo = new QueryExecutor().executeSelect(oldObject);
//	
//		return resultSetPojo.get(0);
//		UserEmails obj = new UserEmails();
//		SqlQueryGenerator queryObj = new SqlQueryGenerator();
//		System.out.println(queryObj.generateSelectQuery(obj));
//		
	//	System.out.println(URLEncoder.encode("arya sree@"));
		
	}
}
