package com.example.barclaysfresh;




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
	
	
}
