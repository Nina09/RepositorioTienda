package com.ec.example.tiendaonline.utiles;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import com.ec.example.tiendaonline.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomGridViewProductos extends BaseAdapter{
	
	protected Activity activity;
    protected ArrayList<Productos> items;

    public CustomGridViewProductos(Activity activity, ArrayList<Productos> items) {
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
	           v = inf.inflate(R.layout.activity_item_produ, null);
	        }
	
	        Productos dir = items.get(position);
	        
	    
	        
	        ImageView img = (ImageView) v.findViewById(R.id.imageViewLogoItemProducto);
	        URL imageUrl = null;
	    	 HttpURLConnection conn = null;
	    	 
	    	 try {
	        imageUrl = new URL("http://" + dir.getURLImagen());
	    	 conn = (HttpURLConnection) imageUrl.openConnection();
	    	 conn.connect();
	    	  
	    	 BitmapFactory.Options options = new BitmapFactory.Options();
	    	 options.inSampleSize = 2; // el factor de escala a minimizar la imagen, siempre es potencia de 2
	    	 
	    	 Bitmap imagen = BitmapFactory.decodeStream(conn.getInputStream(), new Rect(0, 0, 0, 0), options);
	    	 img.setImageBitmap(imagen);
	    	 
	    	 } catch (IOException e) {
	    	 
	    	 e.printStackTrace();
	    	 
	    	 }
	        
	       
	       TextView nombre = (TextView) v.findViewById(R.id.textViewNombreProducto);
	       nombre.setText(dir.getNombre());
	        TextView precio = (TextView) v.findViewById(R.id.textViewPrecio);
	        String prec = Double.toString(dir.getPrecio());
	        precio.setText(prec);
	      
	       return v;
      }
	
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
