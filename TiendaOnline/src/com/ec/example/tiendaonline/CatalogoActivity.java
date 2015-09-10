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
import com.ec.example.tiendaonline.utiles.Categorias;
import com.ec.example.tiendaonline.utiles.CustomListViewCategorias;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("NewApi") public class CatalogoActivity extends Activity {
	final String ip = "192.192.192.3";
	
	final String NAMESPACE = "http://servicio.upse.com";
	final String URL ="http://"+ip+":8080/ServicioWeb/services/ServicioWeb?wsdl";
	final String METHOD_NAME = "consultaProductoCategoria";
	final String SOAP_ACTION = "http://"+ip+":8080/ServicioWeb/services/ServicioWeb/consultaProductoCategoria";
  
	private SoapSerializationEnvelope envelope=null;
	
	Activity a;
	Context context;
	static ArrayList<Categorias> lista;
	JSONArray cat;
	
	ListView listViewCatalogo;
	TextView textViewCatalogo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_catalogo);
		
		listViewCatalogo = (ListView) findViewById(R.id.listViewCatalogo);
		textViewCatalogo = (TextView) findViewById(R.id.textViewCatalogo);	
	    
		lista = new ArrayList<Categorias>();
        a=this;
        context=getApplicationContext();
        
        AccesoInternet acci = new AccesoInternet();
    	String internet = acci.checkConnectivity(this);
    	
   	if(internet.equals("1")){
    		 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
    			
   		  envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
   		  envelope.dotNet = true; 
   		  envelope.setOutputSoapObject(request);
   		  
   		  HttpTransportSE transporte = new HttpTransportSE(URL);
   		  
   				try {
   					transporte.call(SOAP_ACTION, envelope);
   					SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
   					
   					  if (resultsRequestSOAP != null) {
   					  String strJSON = "{\"categorias\":" + resultsRequestSOAP.getProperty(0).toString() + "}";
   					  
   					  if (strJSON != null){
   						
   		            	JSONObject jsonObj;
   						try {
   							
   							jsonObj = new JSONObject(strJSON);
   							cat = jsonObj.getJSONArray("categorias");

   			                  for (int i = 0; i < cat.length(); i++) {
   			                      JSONObject c = cat.getJSONObject(i);
   			                      int id = c.getInt("idCategoria");
   			                      String nombre = c.getString("nombre_categoria");
   			                      
   			                    	 Categorias categoria=new Categorias();
      			                      categoria.setIdCategoria(id);
      			                      categoria.setNombre(nombre);
      			                      lista.add(categoria);   
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
   					  
   		    Adapter adaptador = new CustomListViewCategorias(a,lista);
   		    listViewCatalogo.setAdapter((ListAdapter) adaptador);
            listViewCatalogo.setOnItemClickListener(new ItemClickListener());
            listViewCatalogo.refreshDrawableState();

		}
		else
		{
			Toast.makeText(getApplicationContext(),"Verifique su conexión a internet", Toast.LENGTH_SHORT).show();
		}
 }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.catalogo, menu);
		return true;
	}
	
	class ItemClickListener implements OnItemClickListener{
 		@Override
 		public void onItemClick(AdapterView<?> parent, View item, int position, long id) {
 			 //TODO Auto-generated method stub
 			Categorias itemseleccionado = (Categorias)  listViewCatalogo.getItemAtPosition(position);
 			Intent intent = null;
 			intent = new Intent(item.getContext(), ProductosActivity.class);
 			intent.putExtra("id_categoria",""+ itemseleccionado.getIdCategoria());
 			intent.putExtra("opcionproducto","0");
 			intent.putExtra("opcion", "1");
			startActivity(intent);
 		}
 		
     }
   }
