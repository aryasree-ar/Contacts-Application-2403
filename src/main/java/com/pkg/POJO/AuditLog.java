package com.pkg.POJO;

import java.util.Map;
@Table("audit_log")
public class AuditLog extends DbPojo{
	@Column("audit_log")
	private Long auditId;
	@Column("table_name")
	private String tableName;
	@Column("old_value")
	private String oldValue;
	@Column("new_value")
	private String newValue;
	@Column("operation")
	private String operation;
	@Column("timestamp")
	private Long timestamp;
	@Column("modified_by")
	private String modifiedBy;
	@Column("session_id")
	private String sessionId;
	@Column("uri")
	private String uri;
	
	public long getAuditId() {
		return auditId;
	}
	public void setAuditId(long auditId) {
		this.auditId = auditId;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	@Override
	public Map<String,Object> getColValMap(){
		if(auditId != null) {
			columnClassMap.put("audit_id", auditId);
		}
		if(tableName != null) {
			columnClassMap.put("table_name", tableName);
		}
		if(oldValue != null) {
			columnClassMap.put("old_value", oldValue);
		}
		if(newValue != null) {
			columnClassMap.put("new_value", newValue);
		}
		if(operation != null) {
			columnClassMap.put("operation", operation);
		}
		if(timestamp != null) {
			columnClassMap.put("timestamp", timestamp);
		}
		if(modifiedBy != null) {
			columnClassMap.put("modified_by", modifiedBy);
		}
		if(sessionId != null) {
			columnClassMap.put("session_id", sessionId);
		}
		if(uri != null) {
			columnClassMap.put("uri", uri);
		}
		return columnClassMap;
	}
	
}
