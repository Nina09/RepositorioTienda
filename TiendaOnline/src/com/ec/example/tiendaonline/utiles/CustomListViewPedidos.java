package com.ec.example.tiendaonline.utiles;

import java.util.ArrayList;

import com.ec.example.tiendaonline.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomListViewPedidos extends BaseAdapter{

	protected Activity activity;
    protected ArrayList<ItemPedidos> items;

    public CustomListViewPedidos(Activity activity, ArrayList<ItemPedidos> items) {
	  this.activity = activity;
	  this.items = items;
    }
	
	@Override
	public int getCount() {
	return items.size();
	}
	
	@Override
	public Object getItem(int arg0) {
	return items.get(arg0);
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
	        View v = convertView;
	        
	        if(convertView == null){
	           LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	           v = inf.inflate(R.layout.activity_item_pedidos, null);
	        }
	
	        ItemPedidos dir = items.get(position);
	        TextView fecha = (TextView) v.findViewById(R.id.textViewFecha);
	        fecha.setText("Fecha: " + dir.getFecha());
	        TextView total = (TextView) v.findViewById(R.id.textViewTotal);
	        total.setText("Total: $ " + dir.getTotal());
	        return v;
      }
	
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}
