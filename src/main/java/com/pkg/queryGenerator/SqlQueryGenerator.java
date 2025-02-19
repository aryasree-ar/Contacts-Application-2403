package com.pkg.queryGenerator;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.StringJoiner;

import com.pkg.Exceptions.InvalidInputException;
import com.pkg.POJO.Column;
import com.pkg.POJO.DbPojo;
import com.pkg.POJO.Key;
import com.pkg.POJO.Table;

public class SqlQueryGenerator {

	private boolean isString(Object object) {
		return object != null && object.getClass() == String.class;
	}

	private String getTableName(Class<? extends DbPojo> clazz) {
		if (clazz.isAnnotationPresent(Table.class)) {
			Table tableName = clazz.getAnnotation(Table.class);
			return tableName.value();
		}
		return null;
	}

	public String generateDeleteQuery(DbPojo object) throws InvalidInputException {
		if (object == null) {
			throw new InvalidInputException("The DbPojo object cannot be null.");
		}
		StringBuilder query = new StringBuilder("DELETE FROM ");
		query.append(getTableName(object.getClass()));
		query.append(" WHERE ");
		Map<String, Object> fieldValues = object.getColValMap();
		StringJoiner conditionClauseJoiner = new StringJoiner(" AND ");
		for (Map.Entry<String, Object> fieldEntry : fieldValues.entrySet()) {
			conditionClauseJoiner.add(fieldEntry.getKey() + " = "
					+ (isString(fieldEntry.getValue()) ? "'" + fieldEntry.getValue() + "'" : fieldEntry.getValue()));
		}
		query.append(conditionClauseJoiner.toString());
		return query.toString();
	}

	public String generateInsertQuery(DbPojo object) throws InvalidInputException {
		if (object == null) {
			throw new InvalidInputException("The DbPojo object cannot be null.");
		}
		StringBuilder query = new StringBuilder("INSERT INTO ");
		query.append(getTableName(object.getClass()));
		Map<String, Object> fieldValues = object.getColValMap();
		StringJoiner columnNamesJoiner = new StringJoiner(",", "(", ")");
		StringJoiner valuesJoiner = new StringJoiner(",", "(", ")");
		for (Map.Entry<String, Object> fieldEntry : fieldValues.entrySet()) {
			columnNamesJoiner.add(fieldEntry.getKey());
			valuesJoiner.add(isString(fieldEntry.getValue()) ? "'" + fieldEntry.getValue() + "'"
					: fieldEntry.getValue().toString());
		}
		query.append(columnNamesJoiner.toString());
		query.append(" VALUES ");
		query.append(valuesJoiner.toString());
		return query.toString();
	}

	public String generateUpdateQuery(DbPojo object) throws IllegalArgumentException, IllegalAccessException, InvalidInputException {
		if (object == null) {
			throw new InvalidInputException("updatedObj cannot be null.");
		}
		StringBuilder query = new StringBuilder("UPDATE ");
		query.append(getTableName(object.getClass()));
		query.append(" SET ");
		Map<String, Object> updatedFields = object.getColValMap();
		StringJoiner setClauseJoiner = new StringJoiner(", ");
		for (Map.Entry<String, Object> fieldEntry : updatedFields.entrySet()) {
			setClauseJoiner.add(fieldEntry.getKey() + " = "
					+ (isString(fieldEntry.getValue()) ? "'" + fieldEntry.getValue() + "'" : fieldEntry.getValue()));
		}
		query.append(setClauseJoiner.toString());
		query.append(" WHERE ");

		StringJoiner conditionClauseJoiner = new StringJoiner(" AND ");
		Field[] fields = object.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(Key.class) && field.get(object) != null) {
				if (field.isAnnotationPresent(Column.class)) {
					Column columnAnnotation = field.getAnnotation(Column.class);
					String columnName = columnAnnotation.value();
					conditionClauseJoiner.add(columnName + " = "
							+ (isString(field.get(object)) ? "'" + field.get(object) + "'" : field.get(object)));
				}
			}
		}
		query.append(conditionClauseJoiner.toString());
		return query.toString();
	}

	public String generateSelectQuery(DbPojo object) throws InvalidInputException {
		if (object == null) {
			throw new InvalidInputException("The DbPojo object cannot be null.");
		}
		StringBuilder query = new StringBuilder("SELECT * FROM ");
		query.append(getTableName(object.getClass()));
		Map<String, Object> fieldValues = object.getColValMap();
		if(fieldValues.size() > 0) {
			query.append(" WHERE ");
		}
		StringJoiner conditionClauseJoiner = new StringJoiner(" AND ");
		for (Map.Entry<String, Object> fieldEntry : fieldValues.entrySet()) {
			conditionClauseJoiner.add(fieldEntry.getKey() + " = "
					+ (isString(fieldEntry.getValue()) ? "'" + fieldEntry.getValue() + "'" : fieldEntry.getValue()));
		}
		query.append(conditionClauseJoiner.toString());
		return query.toString();
	}

}
