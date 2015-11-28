package com.example.barclaysfresh;


import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author Townscript
 *
 */

public class VegetableAdapter extends ArrayAdapter<VegetableDTO>{
	
	private int textViewResourceId;
	
	public VegetableAdapter(Context context, int textViewResourceId,
			List<VegetableDTO> objects) {
		super(context, textViewResourceId, objects);
		this.textViewResourceId = textViewResourceId;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(textViewResourceId, null);
        }
		
		TextView vegetableName = (TextView) convertView.findViewById(R.id.vegetablename);
		vegetableName.setText(getItem(position).getName());
		
		TextView price = (TextView) convertView.findViewById(R.id.price);
		price.setText("Rs."+getItem(position).getPriceKg()+"/KG");
		
		final Button addVeggie = (Button) convertView.findViewById(R.id.maincaption);
		
		if(getItem(position).isAddedToCart()){
			addVeggie.setVisibility(View.GONE);
		}else{
			addVeggie.setVisibility(View.VISIBLE);
		}
		 
		addVeggie.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getContext(), "Added "+getItem(position).getName()+" to cart.",
						Toast.LENGTH_SHORT).show();
				addVeggie.setVisibility(View.GONE);
				getItem(position).setAddedToCart(true);
			}
		});
	
		
		return convertView;
	}

}
