package com.pkg.Dao;

import java.util.List;

import com.pkg.Exceptions.DBException;
import com.pkg.Exceptions.InvalidInputException;
import com.pkg.POJO.Servers;
import com.pkg.queryGenerator.QueryExecutor;

public class ServerDao {
	public static List<Servers> getAvailableServers() throws InvalidInputException, DBException {
		QueryExecutor queryExecutorObject = new QueryExecutor();
		Servers server = new Servers();
		List<Servers> servers = queryExecutorObject.executeSelect(server).stream().filter(Servers.class::isInstance)
				.map(Servers.class::cast).toList();
		return servers;
	}

	public static boolean addServer(Servers object) throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException, InvalidInputException, DBException {
		QueryExecutor queryExecutorObject = new QueryExecutor();
		return queryExecutorObject.executeInsert(object, false) > 0;
	}

	public static boolean removeServer(Servers object) throws InvalidInputException, DBException {
		QueryExecutor queryExecutorObject = new QueryExecutor();
		return queryExecutorObject.executeDelete(object) > 0;
	}
}
