package com.app.codingTogether.controller.password;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class PasswordEncoder {
	 public static String hashPassword(String password) {
	        try {
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            byte[] passwordBytes = password.getBytes();
	            byte[] hashedBytes = md.digest(passwordBytes);
	            StringBuilder sb = new StringBuilder();
	            for (byte b : hashedBytes) {
	                sb.append(String.format("%02x", b));
	            }
	            return sb.toString();
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
}
