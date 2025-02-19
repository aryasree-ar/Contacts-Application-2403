package com.pkg.sessionUtil;

import java.time.Instant;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.pkg.Dao.SessionDao;
import com.pkg.Exceptions.DBException;
import com.pkg.Exceptions.InvalidInputException;

public class SessionScheduler {
	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	public static void startSessionManagerScheduler() {
		Runnable sessionManager = new Runnable() {
			@Override
			public void run() {
				Thread.currentThread().setName("SessionManagerThread");
				Set<String> sessionIds = UserSessionCache.getAllSessionIds();
				if (sessionIds != null) {
					try {
						SessionDao.updateLastAccessTime(sessionIds);
					} catch (InvalidInputException e) {
						e.printStackTrace();
					}catch (NoSuchFieldException | SecurityException | IllegalArgumentException
							| IllegalAccessException |DBException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					} 
					UserSessionCache.clearAllSessions();
				}
				try {
					long currentTime = Instant.now().toEpochMilli();

						SessionDao.deleteExpiredSessions(currentTime);
				} catch (InvalidInputException | DBException e) {
					e.printStackTrace();
				}
				
			}
		};
		scheduler.scheduleAtFixedRate(sessionManager, 0, 5, TimeUnit.MINUTES);
	}

	public static void stopSessionManagerScheduler() {
		scheduler.shutdown();
	}
}
