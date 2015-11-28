package com.example.barclaysfresh;

import java.util.Iterator;
import java.util.List;

import com.example.barclaysfresh.CheckoutAdapter.ItemCountCallback;
import com.example.barclaysfresh.HomePage;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class CheckOut extends Activity{
	
	private ListView vegetableslistView;
	private CheckoutAdapter dataAdapter;
	private List<VegetableDTO> vegetablesCart;
	private TextView displayTotalAmount;
	private int totalAmount = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkout);	
		vegetablesCart = HomePage.getAddedVegetables();
		vegetableslistView = (ListView) findViewById(R.id.checkoutitems);
		displayTotalAmount = (TextView) findViewById(R.id.totalAmount);
		dataAdapter = new CheckoutAdapter(this,
				R.layout.ticket_registration_list_details, vegetablesCart, new ItemCountCallback() {
					
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
	}

}
