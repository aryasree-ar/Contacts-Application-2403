package com.pkg.Util;

import java.net.InetAddress;

import java.net.UnknownHostException;
import javax.servlet.ServletContextEvent;

import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.pkg.sessionUtil.SessionScheduler;
import com.pkg.Dao.ServerDao;
import com.pkg.Exceptions.DBException;
import com.pkg.Exceptions.InvalidInputException;
import com.pkg.POJO.Servers;

@WebListener
public class AppListner implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("Startingscheduler");
		String portNumber = sce.getServletContext().getInitParameter("serverPort");
		String ipAddress = null;
		try {
			ipAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		Servers server = new Servers(ipAddress, Integer.parseInt(portNumber));
		try {
			if(ServerDao.addServer(server)) {
				System.out.println("server inserted"); 
			}
			else {
				System.out.println("servre insertion failed");
			}
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | InvalidInputException e) {
			e.printStackTrace();
		} catch (DBException e) {
			e.printStackTrace();
		}
		SessionScheduler.startSessionManagerScheduler();
	}

	public void contextDestroyed(ServletContextEvent sce) { 
		System.out.println("Stoppingscheduler");
		String ipAddress = null;
		try {
			ipAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		Servers server = new Servers();
		server.setServerIp(ipAddress);
		server.setPortNumber(Integer.parseInt(sce.getServletContext().getInitParameter("serverPort")));
		try {
			ServerDao.removeServer(server);
		} catch (InvalidInputException e) {
			e.printStackTrace();
		} catch (DBException e) {
			e.printStackTrace();
		}
		SessionScheduler.stopSessionManagerScheduler();

	}

}
