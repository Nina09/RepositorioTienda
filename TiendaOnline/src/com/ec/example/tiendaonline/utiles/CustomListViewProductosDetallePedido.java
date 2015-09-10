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

public class CustomListViewProductosDetallePedido extends BaseAdapter  {

	protected Activity activity;
    protected ArrayList<ItemProductosDetallePedido> items;

    public CustomListViewProductosDetallePedido(Activity activity, ArrayList<ItemProductosDetallePedido> items) {
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
	           v = inf.inflate(R.layout.activity_item_productos_detalle_pedido, null);
	        }
	
	        ItemProductosDetallePedido dir = items.get(position);
	        TextView descripcion = (TextView) v.findViewById(R.id.textViewDescripcionItem);
	        descripcion.setText(dir.getDescripcion());
	        TextView cantidad = (TextView) v.findViewById(R.id.textViewCantidadItem);
            String cant = Integer.toString(dir.getCantidad());
            cantidad.setText(cant);
            String valor = Double.toString(dir.getPrecio());
	        TextView precio = (TextView) v.findViewById(R.id.textViewPrecioItem);
	        precio.setText(valor);
	        
	        return v;
      }
	
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
