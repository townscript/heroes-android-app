package com.example.barclaysfresh;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class HomePage extends Activity {

	private List<VegetableDTO> vegetables;
	private ListView vegetableslistView;
	private VegetableAdapter dataAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);	
		vegetableslistView = (ListView) findViewById(R.id.vegetables);
		
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
		/*if (id == R.id.action_settings) {
			return true;
		}*/
		return super.onOptionsItemSelected(item);
	}
}
