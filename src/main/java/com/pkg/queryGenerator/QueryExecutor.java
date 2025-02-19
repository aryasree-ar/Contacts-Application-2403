package com.pkg.queryGenerator;

import java.lang.reflect.Field;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.pkg.Exceptions.DBException;
import com.pkg.Exceptions.InvalidInputException;
import com.pkg.POJO.*;
import com.pkg.POJO.AuditLog;
import com.pkg.POJO.DbPojo;
import com.pkg.POJO.UserSessions;
import com.pkg.Util.DBConnection;
import com.pkg.Util.NotifyServers;

public class QueryExecutor {

	private Audit auditObject = new Audit();

	public List<DbPojo> executeSelect(DbPojo pojo) throws InvalidInputException, DBException {
		List<DbPojo> resultSet = new ArrayList<DbPojo>();
		try (Connection connectionObject = DBConnection.connect();
				Statement statementObject = connectionObject.createStatement()) {
			ResultSet resultSetObject = statementObject.executeQuery(new SqlQueryGenerator().generateSelectQuery(pojo));
			while (resultSetObject.next()) {
				DbPojo row = pojo.getClass().getDeclaredConstructor().newInstance();
				Field[] fields = row.getClass().getDeclaredFields();

				for (Field field : fields) {
					field.setAccessible(true);
					if (field.isAnnotationPresent(Column.class)) {
						Column columnAnnotation = field.getAnnotation(Column.class);
						String columnName = columnAnnotation.value();
						Object value = resultSetObject.getObject(columnName);
						field.set(row, value);
					} else {
						Object value = resultSetObject.getObject(field.getName());
						field.set(row, value);
					}
				}
				resultSet.add(row);
			}
			return resultSet;
		} catch (Exception e) {
			throw new DBException("Unable to retrieve data. Please try again .");
		}

	}

	public int executeInsert(DbPojo pojo, boolean returnGeneratedKey) throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException, InvalidInputException, DBException {
		if (pojo.getClass() != UserSessions.class && pojo.getClass() != AuditLog.class) {
			Field feild = pojo.getClass().getDeclaredField("createdAt");
			feild.setAccessible(true);
			feild.set(pojo, Instant.now().toEpochMilli());
		}
		int rowsAffected = -1;
		int generatedId = -1;
		try (Connection connectionObject = DBConnection.connect();
				Statement statementObject = connectionObject.createStatement()) {
			String query = new SqlQueryGenerator().generateInsertQuery(pojo);
			rowsAffected = statementObject.executeUpdate(query,
					returnGeneratedKey ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
			if (returnGeneratedKey && rowsAffected > 0) {
				try (ResultSet resultSetObject = statementObject.getGeneratedKeys()) {
					if (resultSetObject.next()) {
						generatedId = resultSetObject.getInt(1);
					}
				}
			}
			if (pojo.getClass() != AuditLog.class && rowsAffected > 0 && pojo.getClass() != UserSessions.class) {
				auditObject.insertEventAudit(pojo);
			}
			return returnGeneratedKey == true ? generatedId : rowsAffected;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DBException("Unable to save the data. Please try again.");
		}

	}

	public int executeUpdate(DbPojo pojo) throws NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException, InvalidInputException, DBException {
		if (pojo.getClass() != AuditLog.class && pojo.getClass() != UserSessions.class
				&& pojo.getClass() != Servers.class) {
			Field feild = pojo.getClass().getDeclaredField("modifiedAt");
			feild.setAccessible(true);
			feild.set(pojo, Instant.now().toEpochMilli());
		}
		try (Connection connectionObject = DBConnection.connect();
				Statement statementObject = connectionObject.createStatement()) {

			DbPojo oldObject = pojo.getClass().getDeclaredConstructor().newInstance();
			Field[] fields = oldObject.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				if (field.isAnnotationPresent(Key.class) && field.get(pojo) != null) {
					field.set(oldObject, field.get(pojo));
				}
			}
			List<DbPojo> resultSetPojo = new QueryExecutor().executeSelect(oldObject);
			int rowsAffected = statementObject.executeUpdate(new SqlQueryGenerator().generateUpdateQuery(pojo));
			if (pojo.getClass() != AuditLog.class && rowsAffected > 0 && pojo.getClass() != UserSessions.class) {
				auditObject.updateEventAudit(pojo, resultSetPojo.get(0));
			}
			return rowsAffected;
		} catch (Exception e) {
			throw new DBException("Unable to update the data. Please try again.");
		}
	}

	public int executeDelete(DbPojo pojo) throws InvalidInputException, DBException {
		try (Connection connectionObject = DBConnection.connect();
				Statement statementObject = connectionObject.createStatement()) {
			List<DbPojo> resultSet = executeSelect(pojo);
			int rowsAffected = statementObject.executeUpdate(new SqlQueryGenerator().generateDeleteQuery(pojo));
			if (pojo.getClass() != AuditLog.class && rowsAffected > 0 && pojo.getClass() != UserSessions.class
					&& resultSet.size() > 0) {
				auditObject.deleteEventAudit(resultSet.get(0));
			}
			if (pojo.getClass() == UserSessions.class) {
				NotifyServers notificationObject = new NotifyServers();
				String sessionId = ((UserSessions) pojo).getSessionId();
				notificationObject.notifyAllServers(sessionId);
			}
			return rowsAffected;
		} catch (Exception e) {
			throw new DBException("Unable to delete the data. Please try again.");
		}
	}

}
