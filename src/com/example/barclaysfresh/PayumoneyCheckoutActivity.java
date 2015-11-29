package com.example.barclaysfresh;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Set;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class PayumoneyCheckoutActivity extends Activity {

	WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payumoney_checkout);
		webView = (WebView) findViewById(R.id.payU);
		
		final String transactionValue = getIntent().getStringExtra(KeyConstants.TRANSACTION_VALUE);
		
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setDefaultTextEncodingName("UTF-8");
		webView.getSettings().setPluginState(WebSettings.PluginState.ON);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setAllowFileAccess(true);
		webView.getSettings().setBuiltInZoomControls(false);
		webView.getSettings().setAppCacheMaxSize(1024*1024*8);
		webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
		
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("key", KeyConstants.PAYMENT_KEY);
		params.put("txnid", RandomStringGenerator.generateUniqueId(5)+ new Date().getTime());
		params.put("amount", new Float(transactionValue));
		params.put("productinfo", "BarclaysFresh");
		params.put("firstname",Utils.getUserFromSharedPreferences(this).getUsername());
		params.put("email", Utils.getUserFromSharedPreferences(this).getEmail());		
		params.put("surl", KeyConstants.PAYMENT_CALLBACK_URL);
		params.put("phone", Utils.getUserFromSharedPreferences(this).getContact());
		params.put("curl", KeyConstants.PAYMENT_CALLBACK_URL);
		params.put("furl", KeyConstants.PAYMENT_CALLBACK_URL);

		params.put("udf1", "Initiated From Android App"); 
		params.put("udf2", "");
		params.put("udf3", "");
		params.put("udf4", "");
		params.put("udf5", "");
		params.put("service_provider", "");
		
		

		params.put("hash", Utils.generateHash(params));
		
		String URL;


		URL = "javascript:" + 
				"var to = '"+KeyConstants.PAYMENT_URL+"';" +
				"var p = {";

		Set<String> keys = params.keySet();
		for (String key : keys) {
			URL += key + ":'" + params.get(key)+"',";
		}
		URL = URL.substring(0, URL.length()-1);

		URL	+=	"};"+
				"var myForm = document.createElement('form');" +
				"myForm.method='post' ;" +
				"myForm.action = to;" +
				"for (var k in p) {" +
				"if(p.hasOwnProperty(k)) {"+
				"var myInput = document.createElement('input') ;" +
				"myInput.setAttribute('type', 'text');" +
				"myInput.setAttribute('name', k) ;" +
				"myInput.setAttribute('value', p[k]);" +
				"myForm.appendChild(myInput) ;" +
				"}" +
				"}" +
				"document.body.appendChild(myForm) ;" +
				"myForm.submit() ;" +
				"document.body.removeChild(myForm) ;";
		
		webView.clearView();
		webView.loadUrl(URL);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.payumoney_checkout, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
	/*	int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}*/
		return super.onOptionsItemSelected(item);
	}
}
