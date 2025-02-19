package com.pkg.POJO;

import java.util.Map;
@Table("servers")
public class Servers extends DbPojo{
	
	@Column("server_id")
	private int serverId;
	@Column("server_ip")
	private String serverIp;
	@Column("port_number")
	private int portNumber;
	@Column("created_at")
	private long createdAt;
	
	public Servers() {
		
	}
	
	public Servers(String serverIp, int portNumber) {
		this.serverIp = serverIp;
		this.portNumber = portNumber;
	}
	
	public int getServerId() {
		return serverId;
	}
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
	public String getServerIp() {
		return serverIp;
	}
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	public int getPortNumber() {
		return portNumber;
	}
	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}
	public long getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}
	
	@Override
	public Map<String, Object> getColValMap(){
		if(serverId != 0) {
			columnClassMap.put("server_id", serverId);
		}
		if(serverIp != null) {
			columnClassMap.put("server_ip", serverIp);
		}
		if(portNumber != 0) {
			columnClassMap.put("port_number", portNumber);
		}
		if(createdAt != 0) {
			columnClassMap.put("created_at", createdAt);
		}
		
		return columnClassMap;
	}
	
	@Override
	public String toString() {
		return "Servers [serverId=" + serverId + ", serverIp=" + serverIp + ", portNumber=" + portNumber
				+ ", createdAt=" + createdAt +  "]";
	}
	
}
