package com.example.barclaysfresh;

import com.citrus.sdk.CitrusClient;
import com.citrus.sdk.Environment;
import com.citrus.sdk.response.CitrusError;
import com.citrus.sdk.response.CitrusResponse;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class CitrusPayCheckoutActivity extends Activity {
	
	Context context;
	private CitrusClient citrusClient;
	private String userName;
	private String userPhoneNumber;
	private String userEmail;
	private String userPassword;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_citrus_pay_checkout);
		
		final TextView helloMember = (TextView)findViewById(R.id.hellomember);
		final TextView amountToPay = (TextView)findViewById(R.id.amounttopaycaption);
		final TextView citrusMoneyAmount = (TextView)findViewById(R.id.amountincitruscaption);
		
		// Get current user credentials
		UserDTO userDTO = Utils.getUserFromSharedPreferences(this);
		
		userName = userDTO.getUsername();
		userPhoneNumber = userDTO.getContact();
		userEmail = userDTO.getEmail();
		userPassword = userDTO.getPassword();
		
		// Set member greeting
		helloMember.setText("Hello " + userName + ",");
		
		citrusClient = CitrusClient.getInstance(this);
		
		citrusClient.init("6m1rmtqhg4-signup", "97e628e89f3ae9b6f22762f318318907", 
				"6m1rmtqhg4-signin", "dbd4283789743acb905598173fdf464f", 
				"6m1rmtqhg4", Environment.SANDBOX);
		
		 //First Parameter – SignUp Key 
		 //Second Parameter – SignUp Secret
		 //Third Parameter – SignIn Key
		 //Fourth Parameter - SignIn Secret
		 //Fifth Parameter -   Vanity
		 //Sixth Parameter -   Environment
		
		String emailId = "sachin.aero@gmail.com";
		String mobileNo = "9689557730"; 
		
		
		citrusClient.isCitrusMember(userEmail, userPhoneNumber, new com.citrus.sdk.Callback<Boolean>() 
				 {
				   @Override
				   public void success(Boolean citrusMember) {
					   
					   Toast.makeText(CitrusPayCheckoutActivity.this, "This user is Citrus User", Toast.LENGTH_LONG).show();;
					   
					   
				   }
				 
				   @Override
				   public void error(CitrusError error) {
				   
					   Toast.makeText(CitrusPayCheckoutActivity.this, "Error - In Citrus User Checking" + error.getMessage(), Toast.LENGTH_LONG).show();;
				 }});
				     
		
	}
	
	
	private void signupCitrusUser() {
		
		citrusClient.signUp(userEmail, userPhoneNumber, userPassword, new com.citrus.sdk.Callback<CitrusResponse>(){

			@Override
			public void error(CitrusError error) {
				
				Toast.makeText(CitrusPayCheckoutActivity.this, "Error - In Citrus User Creation" + error.getMessage(), Toast.LENGTH_LONG).show();;
				
			}

			@Override
			public void success(CitrusResponse response) {
				
				Toast.makeText(CitrusPayCheckoutActivity.this, "Success - Citrus User Created", Toast.LENGTH_LONG).show();;
				
			}
						
		});
	}
	
	
	private void signinCitrusUser() {
		
		citrusClient.signIn(userEmail, userPassword, new com.citrus.sdk.Callback<CitrusResponse>() {

			@Override
			public void error(CitrusError error) {
				Toast.makeText(CitrusPayCheckoutActivity.this, "Error - In Citrus User Signin" + error.getMessage(), Toast.LENGTH_LONG).show();;
				
			}

			@Override
			public void success(CitrusResponse response) {
				Toast.makeText(CitrusPayCheckoutActivity.this, "Success - Citrus User Signed In", Toast.LENGTH_LONG).show();;
				
			}
			
		});
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.citrus_pay_checkout, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
