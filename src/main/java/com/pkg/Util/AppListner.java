package com.pkg.Util;

import javax.servlet.ServletContextEvent;

import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.pkg.sessionUtil.SessionScheduler;

@WebListener
public class AppListner implements ServletContextListener {
	
	public void contextInitialized(ServletContextEvent sce)  {
    	System.out.println("Startingscheduler");
    	SessionScheduler.startScheduler();
    }
	
    public void contextDestroyed(ServletContextEvent sce)  { 
         System.out.println("Stoppingscheduler");
         SessionScheduler.stopScheduler();
   
    }

}
