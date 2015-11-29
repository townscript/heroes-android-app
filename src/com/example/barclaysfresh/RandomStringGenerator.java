package com.example.barclaysfresh;

import java.util.Random;

/**
 * 
 * @author Townscript
 *
 */

public class RandomStringGenerator {

	 private static final String charset = "!@#$%^&*()" +
		        "0123456789" +
		        "abcdefghijklmnopqrstuvwxyz" +
		        "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	 
	 private static final String numset ="0123456789";
		 
		    public static String generatePassword(int length) {
		       
		        StringBuffer sb = new StringBuffer();
		        for (int i = 0; i <= length; i++ ) {
		            int pos = new Random().nextInt(charset.length());
		            sb.append(charset.charAt(pos));
		        }
		        return sb.toString();
		    }
		    public static String generateUniqueId(int length) {
			       
		        StringBuffer sb = new StringBuffer();
		        for (int i = 0; i <= length; i++ ) {
		            int pos = new Random().nextInt(numset.length());
		            sb.append(numset.charAt(pos));
		        }
		        return sb.toString();
		    }
		 
		    
}
