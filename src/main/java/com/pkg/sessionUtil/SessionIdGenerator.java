package com.pkg.sessionUtil;

import java.security.SecureRandom;
import java.util.Base64;
public class SessionIdGenerator {
	
	 private static final SecureRandom secureRandom = new SecureRandom(); 
	    public static String getSessionId() {
	        // Generate a random byte array (16 bytes = 128 bits)
	        byte[] randomBytes = new byte[16];
	        secureRandom.nextBytes(randomBytes);

	        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
	    }
}
