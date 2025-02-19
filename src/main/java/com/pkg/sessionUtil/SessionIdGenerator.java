package com.pkg.sessionUtil;

import java.security.SecureRandom;
import java.util.Base64;
public class SessionIdGenerator {
	
	 private static final SecureRandom secureRandom = new SecureRandom(); 
	    public static String getSessionId() {
	        byte[] randomBytes = new byte[16];
	        secureRandom.nextBytes(randomBytes);
	        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
	    }
}
