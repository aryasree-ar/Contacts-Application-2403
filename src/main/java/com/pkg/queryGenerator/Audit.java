package com.pkg.queryGenerator;

import java.lang.reflect.Field;

import java.time.Instant;
import com.google.gson.JsonObject;
import com.pkg.POJO.AuditLog;
import com.pkg.POJO.DbPojo;
import com.pkg.Exceptions.DBException;
import com.pkg.Exceptions.InvalidInputException;
import com.pkg.Filters.AuthFilter;

public class Audit {
	private void setMeta(AuditLog auditPojo) {
		if (AuthFilter.SESSION_ID.get() != null) {
			auditPojo.setModifiedBy("user :" + AuthFilter.USER_ID.get());
			auditPojo.setSessionId(AuthFilter.SESSION_ID.get());
			auditPojo.setUri(AuthFilter.URI.get());
		} else {
			auditPojo.setModifiedBy(Thread.currentThread().getName());
		}
	}

	public void insertEventAudit(DbPojo pojo) throws NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException, InvalidInputException, DBException {
		AuditLog auditPojo = new AuditLog();
		setMeta(auditPojo);
		auditPojo.setNewValue(getJsonObjectAsString(pojo));
		auditPojo.setOperation("INSERT");
		auditPojo.setTableName(pojo.getClass().getSimpleName());
		auditPojo.setTimestamp(Instant.now().toEpochMilli());
		QueryExecutor queryExecutor = new QueryExecutor();
		queryExecutor.executeInsert(auditPojo, false);
	}

	public void updateEventAudit(DbPojo newPojo, DbPojo oldPojo) throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException, InvalidInputException, DBException {
		AuditLog auditPojo = new AuditLog();
		setMeta(auditPojo);
		auditPojo.setNewValue(getJsonObjectAsString(newPojo));
		auditPojo.setOperation("UPDATE");
		auditPojo.setTableName(newPojo.getClass().getSimpleName());
		auditPojo.setOldValue(getJsonObjectAsString(oldPojo));
		auditPojo.setTimestamp(Instant.now().toEpochMilli());

		QueryExecutor queryExecutor = new QueryExecutor();
		queryExecutor.executeInsert(auditPojo, false);
	}

	public void deleteEventAudit(DbPojo pojo) throws NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException, InvalidInputException, DBException {
		AuditLog auditPojo = new AuditLog();
		setMeta(auditPojo);
		auditPojo.setOperation("DELETE");
		auditPojo.setTableName(pojo.getClass().getSimpleName());
		auditPojo.setTimestamp(Instant.now().toEpochMilli());
		if (pojo != null) {
			auditPojo.setOldValue(getJsonObjectAsString(pojo));
		}
		QueryExecutor queryExecutor = new QueryExecutor();
		queryExecutor.executeInsert(auditPojo, false);

	}

	private String getJsonObjectAsString(DbPojo pojo) {
		JsonObject jsonObject = new JsonObject();
		Field[] fields = pojo.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (!field.getName().equals("columnClassMap")) {
				field.setAccessible(true);
				try {
					Object value = field.get(pojo);
					if (value != null) {
						if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
							jsonObject.addProperty(field.getName(), (Integer) value);
						} else if (field.getType().equals(Long.class) || field.getType().equals(long.class)) {
							jsonObject.addProperty(field.getName(), (Long) value);
						} else if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
							jsonObject.addProperty(field.getName(), (Boolean) value);
						} else {
							jsonObject.addProperty(field.getName(), value.toString());
						}
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return jsonObject.toString();
	}

}
