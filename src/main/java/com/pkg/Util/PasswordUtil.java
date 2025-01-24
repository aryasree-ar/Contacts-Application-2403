package com.pkg.Util;

import java.security.SecureRandom;


import org.bouncycastle.crypto.generators.SCrypt;

public class PasswordUtil {
	private static final int saltLen = 16;
	private static final int N = 16384;
	private static final int R = 8;
	private static final int P = 1;
	private static final int keyLen = 32;
	
	public static String hashPassword(String password) {
		byte[] salt = new byte[saltLen];
		new SecureRandom().nextBytes(salt);
		byte[] hashedPw = SCrypt.generate(password.getBytes(), salt, N, R, P, keyLen);
		return bytesToHex(hashedPw)+":"+bytesToHex(salt);
	}
	
	public static boolean checkPassword(String password , String storedPw) {
		String[] parts = storedPw.split(":");
		byte[] salt = hexToBytes(parts[1]);
		byte[] hash = hexToBytes(parts[0]);
		
		byte[] verifyHash = SCrypt.generate(password.getBytes(), salt, N, R, P, keyLen);
		return java.util.Arrays.equals(hash, verifyHash);
	}
	
	private static String bytesToHex(byte[] bytes) {
		StringBuilder hexString = new StringBuilder();
		for(byte b:bytes) {
			String hex = Integer.toHexString(0xff & b);//signed to unsigned (mapping all possible byte values 0 - 255)
			if(hex.length() == 1)hexString.append(0);
			hexString.append(hex);
		}
		return hexString.toString();
	}
	
	private static byte[] hexToBytes(String hex) {
		int len = hex.length();
		byte[] data = new byte[len/2];
		for(int i = 0 ; i < len;i+=2) {
			data[i/2] = (byte)((Character.digit(hex.charAt(i), 16) << 4)+Character.digit(hex.charAt(i + 1), 16));
		}
		return data;
	}
}
