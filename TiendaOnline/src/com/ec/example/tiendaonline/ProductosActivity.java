package com.ec.example.tiendaonline;


import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.ec.example.tiendaonline.internet.AccesoInternet;
import com.ec.example.tiendaonline.utiles.CustomGridViewProductos;
import com.ec.example.tiendaonline.utiles.Productos;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ProductosActivity extends Activity {
	final String ip = "192.192.192.3";
	
	Activity a;
	Context context;
	static ArrayList<Productos> lista;
	JSONArray prod;
	
	Intent intentopcion;
	
	ListView listViewListaProductos;
	TextView textViewProductosProductos;
	EditText editTextBuscarProducto;
	
	int idCat;
    String opcion,opcionproducto;
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_productos);
		
		listViewListaProductos = (ListView) findViewById(R.id.listViewListaProductos);
		textViewProductosProductos = (TextView) findViewById(R.id.textViewProductosProductos);
		editTextBuscarProducto = (EditText) findViewById(R.id.editTextBuscarProducto);
		
		lista = new ArrayList<Productos>();
        a=this;
        context=getApplicationContext();
		
        intentopcion = this.getIntent();
		opcion = intentopcion.getStringExtra("opcion");
		opcionproducto = intentopcion.getStringExtra("opcionproducto");
		ListarProductos();
	}

    public void onBuscar(View v){
    	 ListarProductos();
    }
    
    public void ListarProductos(){
  	lista = new ArrayList<Productos>();
    	
    	AccesoInternet acci = new AccesoInternet();
    	String internet = acci.checkConnectivity(this);
    	
    	if(internet.equals("1")){
    		if (opcion.equals("1")){
        		idCat = Integer.parseInt(intentopcion.getStringExtra("id_categoria"));
        		
    			String NAMESPACE = "http://servicio.upse.com";
    			String URL ="http://"+ip+":8080/ServicioWeb/services/ServicioWeb?wsdl";
    			String METHOD_NAME = "consultaProductoxCategoria";
    			String SOAP_ACTION = "http://"+ip+":8080/ServicioWeb/services/ServicioWeb/consultaProductoxCategoria";
    		  
    			 SoapSerializationEnvelope envelope=null;
    			 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
    			 
    			 request.addProperty("request1", idCat);
    			 request.addProperty("request2", editTextBuscarProducto.getText().toString());
    			 
    			  envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
    			  envelope.dotNet = true; 
    			  envelope.setOutputSoapObject(request);
    			  
    			  HttpTransportSE transporte = new HttpTransportSE(URL);
    			  
    					try {
    						
    						transporte.call(SOAP_ACTION, envelope);
    						SoapObject result = (SoapObject) envelope.bodyIn;
    						
    						  if (result != null) {
    						 String strJSON = "{\"productos\":" + result.getProperty(0).toString() + "}";
    						 if (strJSON != null){
    							 System.out.println(strJSON);
    							 JSONObject jsonObj;
    			            	
    			            	try {
    								
    								jsonObj = new JSONObject(strJSON);
    								
    								prod = jsonObj.getJSONArray("productos");
    								int id;
    			  	                  for (int i = 0; i < prod.length(); i++) {        
    			  	                	  JSONObject c = prod.getJSONObject(i);
    			  	                	  
    			  	                      id = c.getInt("idProductos");
    			  	                      String nombreprod = c.getString("nombre_producto");
    			  	                      double precio = c.getDouble("precio");
    			  	                      
    			  	                      String NAMESPACEImagen = "http://servicio.upse.com";
    			  	      			      String URLImagen ="http://"+ip+":8080/ServicioWeb/services/ServicioWeb?wsdl";
    			  	      			      String METHOD_NAMEImagen = "consultarImagenServidor";
    			  	      			      String SOAP_ACTIONImagen = "http://"+ip+":8080/ServicioWeb/services/ServicioWeb/consultarImagenServidor";
    			  	      		  
    			  	                     SoapSerializationEnvelope envelopeimagen=null;
    	    			    			 SoapObject requestimagen = new SoapObject(NAMESPACEImagen, METHOD_NAMEImagen);
    	    			    			
    	    			    			 requestimagen.addProperty("request1", id);
    	    			    			 
    	    			    			 envelopeimagen = new SoapSerializationEnvelope(SoapEnvelope.VER11);
    	    			    			 envelopeimagen.dotNet = true; 
    	    			    			 envelopeimagen.setOutputSoapObject(requestimagen);
    	    			    			  
    	    			    			  HttpTransportSE transporteimagen = new HttpTransportSE(URLImagen);
    	    			    			 
    	    			    			  transporteimagen.call(SOAP_ACTIONImagen, envelopeimagen);
    	    	    					  SoapObject resultimagen = (SoapObject) envelopeimagen.bodyIn;
    	    	    					  String strJSONImagen = resultimagen.getProperty(0).toString();
    	    	    					  
    			  	                      Productos productos=new Productos();
    				  	                  productos.setIdProducto(id);
    				  	                  productos.setNombre(nombreprod);
    				  	                  productos.setPrecio(precio);
    				  	                  productos.setURLImagen(strJSONImagen);
    				  	                  
    				  	                  listViewListaProductos.refreshDrawableState();
    				  	                  lista.add(productos);
    				  	                  
    			  	                      }
    							} catch (JSONException e) {
    								// TODO Auto-generated catch block
    								e.printStackTrace();
    							} 
    						 }
    					   }
    							
    					}catch (Exception e) {
    			  			e.printStackTrace();
    			  			Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();	
    			  		}
    		}
    	  
    		else
    		{
    			if (opcion.equals("2")){
    				
    				String NAMESPACE = "http://servicio.upse.com";
    				String URL ="http://"+ip+":8080/ServicioWeb/services/ServicioWeb?wsdl";
    				String METHOD_NAME = "consultaProducto";
    				String SOAP_ACTION = "http://"+ip+":8080/ServicioWeb/services/ServicioWeb/consultaProducto";
    			  
    				SoapSerializationEnvelope envelope=null;
    				
    				 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
    				 
    				 request.addProperty("request1", editTextBuscarProducto.getText().toString());
    				 
    				  envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
    				  envelope.dotNet = true; 
    				  envelope.setOutputSoapObject(request);
    				  
    				  HttpTransportSE transporte = new HttpTransportSE(URL);
    				  
    						try {
    							
    							transporte.call(SOAP_ACTION, envelope);
    							SoapObject result = (SoapObject) envelope.bodyIn;
    							 
    							  
    								  if (result != null) {
    			    						 String strJSON = "{\"productos\":" + result.getProperty(0).toString() + "}";
    			    						 if (strJSON != null){
    			    							 
    			    							 JSONObject jsonObj;
    			    			            	
    			    			                  try {
    			    								
    			    						            jsonObj = new JSONObject(strJSON);
    			    								
    			    							        prod = jsonObj.getJSONArray("productos");
    			    							        int id;
    			    			  	                      for (int i = 0; i < prod.length(); i++) {        
    			    			  	                	  JSONObject c = prod.getJSONObject(i);
    			    			  	                	  
    			    			  	                      id = c.getInt("idProductos");
    			    			  	                      String nombreprod = c.getString("nombre_producto");
    			    			  	                      double precio = c.getDouble("precio");
    			    			  	                      
    				  	                     
    			    			  	                    String NAMESPACEImagen = "http://servicio.upse.com";
    			    			  	      			      String URLImagen ="http://"+ip+":8080/ServicioWeb/services/ServicioWeb?wsdl";
    			    			  	      			      String METHOD_NAMEImagen = "consultarImagenServidor";
    			    			  	      			      String SOAP_ACTIONImagen = "http://"+ip+":8080/ServicioWeb/services/ServicioWeb/consultarImagenServidor";
    			    			  	      		  
    			    			  	                     SoapSerializationEnvelope envelopeimagen=null;
    			    	    			    			 SoapObject requestimagen = new SoapObject(NAMESPACEImagen, METHOD_NAMEImagen);
    			    	    			    			
    			    	    			    			 requestimagen.addProperty("request1", id);
    			    	    			    			 
    			    	    			    			 envelopeimagen = new SoapSerializationEnvelope(SoapEnvelope.VER11);
    			    	    			    			 envelopeimagen.dotNet = true; 
    			    	    			    			 envelopeimagen.setOutputSoapObject(requestimagen);
    			    	    			    			  
    			    	    			    			  HttpTransportSE transporteimagen = new HttpTransportSE(URLImagen);
    			    	    			    			 
    			    	    			    			  transporteimagen.call(SOAP_ACTIONImagen, envelopeimagen);
    			    	    	    					  SoapObject resultimagen = (SoapObject) envelopeimagen.bodyIn;
    			    	    	    					  
    			    	    	    					  String strJSONImagen = resultimagen.getProperty(0).toString();
    			    	    	    					         
    			    			  	                      Productos productos=new Productos();
    			    				  	                  productos.setIdProducto(id);
    			    				  	                  productos.setNombre(nombreprod);
    			    				  	                  productos.setPrecio(precio);
    			    				  	                  productos.setURLImagen(strJSONImagen);
    			    				  	                  
    			    				  	               
    			    				  	                  listViewListaProductos.refreshDrawableState();
    			    				  	                  lista.add(productos);
    			    				  	                  
    			    			  	                      }
    			    			           
    			    							} catch (JSONException e) {
    			    								// TODO Auto-generated catch block
    			    								e.printStackTrace();
    			    							} 
    			    			              }
    								      }
    						
    			    					}catch (Exception e) {
    			    			  			e.printStackTrace();
    			    			  			Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();	
    			    			  		}
    			}
    		}
        	Adapter adaptador = new CustomGridViewProductos(a,lista);
    		listViewListaProductos.setAdapter((ListAdapter) adaptador);
    	    listViewListaProductos.setOnItemClickListener(new ItemClickListener());
    	    listViewListaProductos.refreshDrawableState();
		}
		else
		{
			Toast.makeText(getApplicationContext(),"Verifique su conexión a internet", Toast.LENGTH_SHORT).show();
		}
    }
    class ItemClickListener implements OnItemClickListener{
	 		@Override
	 		public void onItemClick(AdapterView<?> parent, View item, int position, long id) {
	 			 //TODO Auto-generated method stub
	 			Productos itemseleccionado = (Productos)  listViewListaProductos.getItemAtPosition(position);
	 			Intent intent = null;
	 			intent = new Intent(item.getContext(), DetalleProductoActivity.class);
	 			intent.putExtra("id_producto",""+ itemseleccionado.getIdProducto());
				intent.putExtra("opcionproducto", opcionproducto);
	 			startActivity(intent);
	 		}
	     } 
	  	
	  	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.productos, menu);
		return true;
	  }

}
