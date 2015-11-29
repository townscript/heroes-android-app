package com.example.barclaysfresh;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.citrus.sdk.Callback;
import com.citrus.sdk.CitrusClient;
import com.citrus.sdk.Environment;
import com.citrus.sdk.TransactionResponse;
import com.citrus.sdk.classes.Amount;
import com.citrus.sdk.classes.CitrusException;
import com.citrus.sdk.payment.PaymentType;
import com.citrus.sdk.response.CitrusError;
import com.citrus.sdk.response.CitrusResponse;

public class CitrusPayCheckoutActivity extends Activity {
	
	Context context;
	private CitrusClient citrusClient;
	private String userName;
	private String userPhoneNumber;
	private String userEmail;
	private String userPassword;
	
	TextView helloMember;
	TextView amountToPay;
	TextView citrusMoneyAmount;
	
	Button loadMoneyButton;
	Button doPaymentButton;
	
	String amountToPayValue;
	Integer amountToPayValueInteger;
	Integer amountInCitrus = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_citrus_pay_checkout);
		
		helloMember = (TextView)findViewById(R.id.hellomember);
		amountToPay = (TextView)findViewById(R.id.amounttopaycaption);
		citrusMoneyAmount = (TextView)findViewById(R.id.amountincitruscaption);
		
		amountToPayValue = getIntent().getStringExtra(KeyConstants.TRANSACTION_VALUE);
		amountToPayValueInteger = Integer.valueOf(amountToPayValue);
		
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
		
		
		
		citrusClient.isCitrusMember(userEmail, userPhoneNumber, new com.citrus.sdk.Callback<Boolean>() 
				 {
				   @Override
				   public void success(Boolean citrusMember) {
					   
					   if(citrusMember) {
					   Toast.makeText(CitrusPayCheckoutActivity.this, "Succes - user is Citrus User", Toast.LENGTH_SHORT).show();
					   signinCitrusUser();
					   
					   } else {
						   Toast.makeText(CitrusPayCheckoutActivity.this, "Succes - user NOT A Citrus User", Toast.LENGTH_SHORT).show();
						   signupCitrusUser();
					   }
					   
				   }
				 
				   @Override
				   public void error(CitrusError error) {
				   
					   Toast.makeText(CitrusPayCheckoutActivity.this, "Error - In Citrus User Checking" + error.getMessage(), Toast.LENGTH_LONG).show();
				 }});
				     
		
		loadMoneyButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i = new Intent(CitrusPayCheckoutActivity.this, CreditDebitCardCheckoutActivity.class);
				CitrusPayCheckoutActivity.this.startActivity(i);
				
			}
		});
		
		doPaymentButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(amountInCitrus < amountToPayValueInteger) {
					Toast.makeText(CitrusPayCheckoutActivity.this, "You have to Load money as "
							+ "Amount to pay is less than Amount in wallet", Toast.LENGTH_LONG).show();
					return;
				}
				
				Amount amount = new Amount(amountToPayValue);
				
				try {
					citrusClient.payUsingCitrusCash(new PaymentType.CitrusCash(amount, "http://app/callback"), new Callback<TransactionResponse>() 
							 {
							   @Override
							   public void success(TransactionResponse transactionResponse) { }
							   
							   @Override
							   public void error(CitrusError error) { }
							 });
				} catch (CitrusException e) {
					e.printStackTrace();
					Toast.makeText(CitrusPayCheckoutActivity.this, "Error - while paying with Citrus cash " + e.getMessage(), 
							Toast.LENGTH_LONG).show();
				}
				
			}
		});
	}
	
	
	private void signupCitrusUser() {
		
		citrusClient.signUp(userEmail, userPhoneNumber, userPassword, new com.citrus.sdk.Callback<CitrusResponse>(){

			@Override
			public void error(CitrusError error) {
				
				Toast.makeText(CitrusPayCheckoutActivity.this, "Error - In Citrus User Creation" + error.getMessage(), Toast.LENGTH_LONG).show();
				
			}

			@Override
			public void success(CitrusResponse response) {
				
				Toast.makeText(CitrusPayCheckoutActivity.this, "Success - Citrus User Created", Toast.LENGTH_SHORT).show();
				signinCitrusUser();
				
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
				Toast.makeText(CitrusPayCheckoutActivity.this, "Success - Citrus User Signed In", Toast.LENGTH_SHORT).show();;
				accessCitrusCashInWallet();
			}
			
		});
		
	}
	
	
	private void accessCitrusCashInWallet() {
		
		citrusClient.getBalance(new com.citrus.sdk.Callback<Amount>() 
				 {
				   @Override
				   public void success(Amount amount) {
					   
					   Toast.makeText(CitrusPayCheckoutActivity.this, "Success - In Citrus User get balance", Toast.LENGTH_SHORT).show();;
					   citrusMoneyAmount.setText("Citrus Money - " + amount.getValue());
					   amountInCitrus = Integer.valueOf(amount.getValue());
					   
				   }
				 
				   @Override
				   public void error(CitrusError error) {
					   
					   Toast.makeText(CitrusPayCheckoutActivity.this, "Error - In Citrus User get balance" + error.getMessage(), Toast.LENGTH_LONG).show();;
				   }
				 });
				     
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.citrus_pay_checkout, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		/*int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}*/
		return super.onOptionsItemSelected(item);
	}
}
