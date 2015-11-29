package com.example.barclaysfresh;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class HomePage extends Activity {

	private List<VegetableDTO> vegetables;
	private ListView vegetableslistView;
	private VegetableAdapter dataAdapter;
	private Button gotocart;
	Context context;

	static List<VegetableDTO> vegetablesCart = new ArrayList<VegetableDTO>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.activity_home_page);	
		vegetableslistView = (ListView) findViewById(R.id.vegetables);
		gotocart = (Button) findViewById(R.id.gotocart);
		
		vegetables = new ArrayList<VegetableDTO>();
		vegetables.add(new VegetableDTO("Potato",20));
		vegetables.add(new VegetableDTO("Tomato",40));
		vegetables.add(new VegetableDTO("Onion",28));
		vegetables.add(new VegetableDTO("Cauliflower",60));
		vegetables.add(new VegetableDTO("Cabbage",40));
		vegetables.add(new VegetableDTO("Carrot",40));
		vegetables.add(new VegetableDTO("Green Chilli",80));
		vegetables.add(new VegetableDTO("Lady Finger",48));
		dataAdapter = new VegetableAdapter(this,
				R.layout.listitem_eventlistitem, vegetables);
		
		vegetableslistView.setAdapter(dataAdapter);
		
		gotocart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(context,CheckOut.class);
				Iterator<VegetableDTO> iterator = vegetables.iterator();
				while(iterator.hasNext()){
					VegetableDTO veggie = iterator.next();
					if(veggie.isAddedToCart()){
						vegetablesCart.add(veggie);
					}
				}
				startActivity(i);
			}
		});
		
		
	}
	
	public static List<VegetableDTO> getAddedVegetables(){
		return vegetablesCart;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_page, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.logout) {

			Utils.clearUserFromSharedPreferences(context);
			Toast.makeText(context, "Successfully logged out.",
					Toast.LENGTH_SHORT).show();
			return true;
			
		}
		return super.onOptionsItemSelected(item);
	}
}
