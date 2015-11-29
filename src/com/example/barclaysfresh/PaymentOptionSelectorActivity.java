package com.example.barclaysfresh;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/*
 * This will be displaying the page where User will be making selections among various payment options
 */
public class PaymentOptionSelectorActivity extends Activity {

	
	private TextView transactionValueView;
	private Button paymentButton;
	private RadioGroup paymentOptions;
	private ProgressBar progressBar;
	
	private RadioButton radioButtonPayUMoney;
	private RadioButton radioButtonCitrusPay;
	private RadioButton radioButtonCCDebitCard;
	
	private String transactionValue;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment_option_selector);
		
		transactionValueView = (TextView) findViewById(R.id.transactionvalue);
		paymentButton = (Button) findViewById(R.id.proceedpayment);
		paymentOptions = (RadioGroup) findViewById(R.id.paymentoptions);
		progressBar = (ProgressBar) findViewById(R.id.progressbarpaymentoptions);
		
		radioButtonPayUMoney = (RadioButton)findViewById(R.id.payumoney);
		radioButtonCitrusPay = (RadioButton)findViewById(R.id.citruspay);
		radioButtonCCDebitCard = (RadioButton)findViewById(R.id.ccdb);
		

		transactionValue = getIntent().getStringExtra(KeyConstants.TRANSACTION_VALUE);
		transactionValueView.setText("Rs " + transactionValue);
		
		
		// We have to load the Payment Options defined by Merchant on the Server
		new AsyncTask<Void, String, List<String>>() {
			
			protected void preExecute() {
				super.onPreExecute();
				
				progressBar.setVisibility(View.VISIBLE);
				transactionValueView.setVisibility(View.GONE);
				paymentButton.setVisibility(View.GONE);
				paymentOptions.setVisibility(View.GONE);
			}

			@Override
			protected List<String> doInBackground(Void... params) {
				
				List<String> values = new ArrayList();
				
				String URL_param = "?appid=5";
				
				//Getting response from the server
				JSONObject jsonObject = (JSONObject) HttpUtility.getHttpGetResponse(KeyConstants.SERVER_BASE_URL,
						KeyConstants.WEB_PAGE_URL + URL_param);

				if (jsonObject.get("result").equals("Success")) {

					String data = (String) jsonObject.get("data");
					Type merchantObject = new TypeToken<List<String>>() {
					}.getType();

					values = new Gson().fromJson(data, merchantObject);
					
				} else {
					
					values.add("PAYUMONEY");
					values.add("CITRUSPAY");
					values.add("CCDBCARD");
				}
				
			
				// Test code
//				List<String> values1 = new ArrayList();
//				values.add("PAYUMONEY");
				
				return values;
			}

			protected void onPostExecute(List<String> result) {
				
				progressBar.setVisibility(View.GONE);
				
				transactionValueView.setVisibility(View.VISIBLE);
				paymentButton.setVisibility(View.VISIBLE);
				paymentOptions.setVisibility(View.VISIBLE);
				
				if(!result.isEmpty()) {
					renderPaymentOptions(result);
				}
				
			}
			
			protected void onProgressUpdate(String... values) {
				super.onProgressUpdate(values);
				
				Toast.makeText(PaymentOptionSelectorActivity.this, "Error - "
						+ "while trying to load the data from server ", 
						Toast.LENGTH_LONG).show();
				
				
			}
			
		}.execute();
		
		
		// According to the user selected state of each payment option, 
		// we will direct user to corresponding checkout activity page
		
		paymentButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(getSelectedPaymentOption().equals(PaymentOption.PAYU_MONEY)) {
					Intent i = new Intent(PaymentOptionSelectorActivity.this, PayumoneyCheckoutActivity.class);
					i.putExtra(KeyConstants.TRANSACTION_VALUE, transactionValue);
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
	
	private void renderPaymentOptions(List<String> values) {
		
		if(values.contains(PaymentOption.PAYU_MONEY.getStringValue().toUpperCase())) {
			radioButtonPayUMoney.setVisibility(View.VISIBLE);
		}
		
		if(values.contains(PaymentOption.CITRUS_PAY.getStringValue().toUpperCase())) {
			radioButtonCitrusPay.setVisibility(View.VISIBLE);
		}
		
		if(values.contains(PaymentOption.CREDIT_AND_DEBIT_CARD.getStringValue().toUpperCase())) {
			radioButtonCCDebitCard.setVisibility(View.VISIBLE);
		}
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
		
		if(radioButton.getId() == R.id.ccdb) {
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
