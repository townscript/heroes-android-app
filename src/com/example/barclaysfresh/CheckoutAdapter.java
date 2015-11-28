package com.example.barclaysfresh;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CheckoutAdapter extends ArrayAdapter<VegetableDTO>{
	
	private int textViewResourceId;
	
	public interface ItemCountCallback{
		void onPlus(int amount);
		void onMinus(int amount);
	}
	
	private ItemCountCallback callback;
	
	public CheckoutAdapter(Context context, int textViewResourceId,
			List<VegetableDTO> objects,ItemCountCallback callback) {
		super(context, textViewResourceId, objects);
		this.textViewResourceId = textViewResourceId;
		this.callback = callback;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(textViewResourceId, null);
        }
		
		TextView vegetableName = (TextView) convertView.findViewById(R.id.veggieName);
		vegetableName.setText(getItem(position).getName() + " (Quantity in kgs.)");
		
		final TextView quantity = (TextView) convertView.findViewById(R.id.countTV);
		quantity.setText(""+getItem(position).getQuantitySelected());
		
		ImageView plus = (ImageView) convertView.findViewById(R.id.plusIV);
		plus.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getItem(position).setQuantitySelected(getItem(position).getQuantitySelected()+1);
				quantity.setText(""+getItem(position).getQuantitySelected());
				
				callback.onPlus(getItem(position).getPriceKg());
			}
		});
		
		ImageView minus = (ImageView) convertView.findViewById(R.id.minusIV);
		minus.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(getItem(position).getQuantitySelected() == 0){
					return;
				}
				getItem(position).setQuantitySelected(getItem(position).getQuantitySelected()-1);
				quantity.setText(""+getItem(position).getQuantitySelected());
				callback.onMinus(getItem(position).getPriceKg());
			}
		});
		
	
		
		return convertView;
	}

}

