package com.example.barclaysfresh;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.barclaysfresh.CheckoutAdapter.ItemCountCallback;

public class CheckOut extends Activity{
	
	private ListView vegetableslistView;
	private CheckoutAdapter dataAdapter;
	private List<VegetableDTO> vegetablesCart;
	private TextView displayTotalAmount;
	private Button proceedToPayment;
	private int totalAmount = 0;
	Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkout);	
		context = this;
		vegetablesCart = HomePage.getAddedVegetables();
		vegetableslistView = (ListView) findViewById(R.id.checkoutitems);
		displayTotalAmount = (TextView) findViewById(R.id.totalAmount);
		proceedToPayment = (Button) findViewById(R.id.confirmcheckout);
		dataAdapter = new CheckoutAdapter(this,
				R.layout.listitem_checkout, vegetablesCart, new ItemCountCallback() {
					
					@Override
					public void onPlus(int amount) {
						// TODO Auto-generated method stub
						totalAmount += amount;
						displayTotalAmount.setText("Grand Amount : " + totalAmount);
					}
					
					@Override
					public void onMinus(int amount) {
						// TODO Auto-generated method stub
						totalAmount -= amount;
						displayTotalAmount.setText("Grand Amount : " + totalAmount);
					}
				});
		
		vegetableslistView.setAdapter(dataAdapter);
		
		displayTotalAmount.setText("Grand Amount : " + totalAmount);
		
		proceedToPayment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(context,PaymentOptionSelectorActivity.class);
				i.putExtra(KeyConstants.TRANSACTION_VALUE, String.valueOf(totalAmount));
				startActivity(i);
			}
		});
	}

}
