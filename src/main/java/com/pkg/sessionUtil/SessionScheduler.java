package com.pkg.sessionUtil;

import java.time.Instant;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.pkg.Dao.SessionDao;

public class SessionScheduler {
	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	public static void startScheduler() {
		Runnable sessionManager = new Runnable() {
			
			@Override
			public void run() {
				Set<String> sessionIds = UserSessionCache.getAllSessionIds();
                
				if(sessionIds != null) {
					//System.out.println("current sessions : "+sessionIds);
					SessionDao.updateLastAccessTime(sessionIds);
					UserSessionCache.clearAllSessions();
				}
                
				// Get the current time (in epoch)
                long currentTime = Instant.now().toEpochMilli();
                // Call the method to delete expired sessions (both in cache and DB)
                SessionDao.deleteExpiredSessions(currentTime);
                
				
			}
		};
		scheduler.scheduleAtFixedRate(sessionManager, 0, 5, TimeUnit.MINUTES);
	}
	
	public static void stopScheduler() {
		scheduler.shutdown();
	}
}
