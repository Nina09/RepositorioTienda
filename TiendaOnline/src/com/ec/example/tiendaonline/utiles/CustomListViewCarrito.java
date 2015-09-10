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

public class CustomListViewCarrito extends BaseAdapter{
	
	protected Activity activity;
    protected ArrayList<ItemCarrito> items;

    public CustomListViewCarrito(Activity activity, ArrayList<ItemCarrito> items) {
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
		           v = inf.inflate(R.layout.activity_item_carrito, null);
		        }
		
		ItemCarrito dir = items.get(position);
		
		TextView descripcion = (TextView) v.findViewById(R.id.textViewDescripcionItemCarrito);
		descripcion.setText(dir.getDescripcion());
		
		TextView nombre = (TextView) v.findViewById(R.id.textViewNombre);
		nombre.setText(dir.getNombre());
		
		
		TextView cantidad = (TextView) v.findViewById(R.id.textViewCantidadItemCarrito);
		cantidad.setText(""+dir.getCantidadd());
		
		TextView precio = (TextView) v.findViewById(R.id.textViewPrecioItemCarrito);
		precio.setText(""+dir.getPrecio());
		
		TextView total = (TextView) v.findViewById(R.id.textViewTotalItemCarrito);
		total.setText(""+dir.getTotal());	
		
		return v;
	      }
		
		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}
}

		
