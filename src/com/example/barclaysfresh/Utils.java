package com.example.barclaysfresh;




import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;


public class Utils {

	private static SharedPreferences userDtoSP;

	public static UserDTO getUserFromSharedPreferences(Context ctx) {
		SharedPreferences pref = getUserDtoSP(ctx);
		UserDTO dto = new UserDTO();
		dto.setUsername(pref.getString(KeyConstants.PREF_USERNAME, null));
		dto.setPassword(pref.getString(KeyConstants.PREF_USERPASSWORD, null));
		dto.setEmail(pref.getString(KeyConstants.PREF_USEREMAIL, null));
		dto.setContact(pref.getString(KeyConstants.PREF_USERCELLPHONENO, null));
		return dto;
	}


	public static void setUserDtoSP(Context ctx, UserDTO dto) {
		SharedPreferences pref = getUserDtoSP(ctx);
		SharedPreferences.Editor editor = pref.edit();
		editor.putString(KeyConstants.PREF_USERNAME, dto.getUsername());
		editor.putString(KeyConstants.PREF_USEREMAIL, dto.getEmail());
		editor.putString(KeyConstants.PREF_USERPASSWORD, dto.getPassword());
		editor.putString(KeyConstants.PREF_USERCELLPHONENO, dto.getContact());
		editor.commit();
	}
	
	public static SharedPreferences getUserDtoSP(Context context) {
		if (userDtoSP == null) {
			userDtoSP = context.getSharedPreferences(
					KeyConstants.USER_DATA_PREF, Context.MODE_PRIVATE);
		}
		return userDtoSP;
	}
	
	public static void clearUserFromSharedPreferences(Context ctx) {
		SharedPreferences pref = getUserDtoSP(ctx);
		pref.edit().clear().commit();
	}

	private static final String messageDigest(final String text, String hashMode) {
		try {
			// Create Hash
			MessageDigest digest = MessageDigest.getInstance(hashMode);
			digest.update(text.getBytes());
			byte messageDigest[] = digest.digest();

			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			int messageDigestLenght = messageDigest.length;
			for (int i = 0; i < messageDigestLenght; i++) {
				String hashedData = Integer
						.toHexString(0xFF & messageDigest[i]);
				while (hashedData.length() < 2)
					hashedData = "0" + hashedData;
				hexString.append(hashedData);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return ""; // if text is null then return nothing
	}

	public static String generateHash(Map<String, Object> params) {
		List<String> keys = new ArrayList<String>(params.keySet());
		StringBuilder strb = new StringBuilder(1000);
		for (String sk : keys) {
			if (!Arrays.asList("key", "txnid", "amount", "productinfo",
					"firstname", "email", "udf1", "udf2", "udf3", "udf4",
					"udf5").contains(sk)) {
				continue;
			}
			String value = String.valueOf(params.get(sk));
			strb.append(value + "|");
		}
		strb.append("|||||" + KeyConstants.PAYMENT_SALT); // Adding Salt

		return messageDigest(strb.toString(), "SHA-512");
	}
	
	
}
