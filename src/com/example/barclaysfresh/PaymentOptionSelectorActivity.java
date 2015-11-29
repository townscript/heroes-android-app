package com.example.barclaysfresh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/*
 * This will be displaying the page where User will be making selections among various payment options
 */
public class PaymentOptionSelectorActivity extends Activity {

	
	private TextView transactionValueView;
	
	private Button paymentButton;
	
	private RadioGroup paymentOptions;
	
	private String transactionValue;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment_option_selector);
		
		transactionValueView = (TextView) findViewById(R.id.transactionvalue);
		paymentButton = (Button) findViewById(R.id.proceedpayment);
		paymentOptions = (RadioGroup) findViewById(R.id.paymentoptions);
		
		transactionValue = getIntent().getStringExtra(KeyConstants.TRANSACTION_VALUE);
		transactionValueView.setText("Rs " + transactionValue);
		
		
		// According to the user selected state of each payment option, 
		// we will direct user to corresponding checkout activity page
		
		paymentButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(getSelectedPaymentOption().equals(PaymentOption.PAYU_MONEY)) {
					Intent i = new Intent(PaymentOptionSelectorActivity.this, PayumoneyCheckoutActivity.class);
					PaymentOptionSelectorActivity.this.startActivity(i);
				}
				
				if(getSelectedPaymentOption().equals(PaymentOption.CITRUS_PAY)) {
					Intent i = new Intent(PaymentOptionSelectorActivity.this, CitrusPayCheckoutActivity.class);
					i.putExtra(KeyConstants.TRANSACTION_VALUE, transactionValue);
					PaymentOptionSelectorActivity.this.startActivity(i);
				}
				
				if(getSelectedPaymentOption().equals(PaymentOption.CREDIT_AND_DEBIT_CARD)) {
					Intent i = new Intent(PaymentOptionSelectorActivity.this, CreditDebitCardCheckoutActivity.class);
					PaymentOptionSelectorActivity.this.startActivity(i);
				}
				
			}
		});
		
	}

	
	PaymentOption getSelectedPaymentOption() {
		
		int radioButtonID = paymentOptions.getCheckedRadioButtonId();
		View radioButton = paymentOptions.findViewById(radioButtonID);
		
		if(radioButton.getId() == R.id.citruspay) {
			return PaymentOption.CITRUS_PAY;
		}
		
		if(radioButton.getId() == R.id.payumoney) {
			return PaymentOption.PAYU_MONEY;
		}
		
		if(radioButton.getId() == R.id.ccdc) {
			return PaymentOption.CREDIT_AND_DEBIT_CARD;
		}
		
		throw new IllegalStateException("Payment Option Radio button doesn't "
				+ "matches any option in the system");
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.payment_option_selector, menu);
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
