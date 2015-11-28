package com.example.barclaysfresh;

import com.citrus.sdk.CitrusClient;
import com.citrus.sdk.Environment;
import com.citrus.sdk.response.CitrusError;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class CitrusPayCheckoutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_citrus_pay_checkout);
		
		final TextView isSushantCitrusMember = (TextView)findViewById(R.id.sushantmemberquestion);
		TextView isSushantCitrusAmount = (TextView)findViewById(R.id.sushantcitrusamount);
		
		
		CitrusClient citrusClient = CitrusClient.getInstance(this);
		
		citrusClient.init("6WJKFBKTJYEP4LK62VEX", "7b82617cf4c6b1993b4f431ea87a5d24ebaced9f", 
				"6m1rmtqhg4-JS-signin", "b5339f7c55dda128335d7fb2a6407ada", 
				"6m1rmtqhg4", Environment.SANDBOX);
		
		 //First Parameter – SignUp Key
		 //Second Parameter – SignUp Secret
		 //Third Parameter – SignIn Key
		 //Fourth Parameter - SignIn Secret
		 //Fifth Parameter -   Vanity
		 //Sixth Parameter -   Environment
		
		String emailId = "pawarsushant007@gmail.com";
		String mobileNo = "9860553252";
		
		
		citrusClient.isCitrusMember(emailId, mobileNo, new com.citrus.sdk.Callback<Boolean>() 
				 {
				   @Override
				   public void success(Boolean citrusMember) {
					   
					   isSushantCitrusMember.setText(citrusMember.toString());
					   
				   }
				 
				   @Override
				   public void error(CitrusError error) {}

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
