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
import com.ec.example.tiendaonline.utiles.CustomListViewPedidos;
import com.ec.example.tiendaonline.utiles.ItemPedidos;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;


@SuppressLint("NewApi") public class ListarPedidosActivity extends Activity {
	final String ip = "192.192.192.3";
	
	String NAMESPACE = "http://servicio.upse.com";
	String URL ="http://"+ip+":8080/ServicioWeb/services/ServicioWeb?wsdl";
	String METHOD_NAME = "consultaPedidoxUsuario";
	String SOAP_ACTION = "http://"+ip+":8080/ServicioWeb/services/ServicioWeb/consultaPedidoxUsuario";
  
	Spinner spinnerMenuPedidos;
	String estadopedido;
	
	//
	
	Activity a;
	Context context;
	static ArrayList<ItemPedidos> lista;
	JSONArray ped;
	
	TextView textViewPedidos;
	ListView listViewListaPedidos;
	
	int idUsuario;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pedidos);
      
		listViewListaPedidos = (ListView) findViewById(R.id.listViewListaPedidos);
		textViewPedidos = (TextView) findViewById(R.id.textViewPedidos);
		
		lista = new ArrayList<ItemPedidos>();
        a=this;
        context=getApplicationContext();
        
        Intent intent = this.getIntent();
        idUsuario= Integer.parseInt(intent.getStringExtra("idusuario"));
        
        
        spinnerMenuPedidos = (Spinner) findViewById(R.id.spinnerMenuPedidos);
		
		spinnerMenuPedidos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				estadopedido=spinnerMenuPedidos.getSelectedItem().toString();
				SpinnerMenu();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});

	}  
	
   public void SpinnerMenu(){
	   
	AccesoInternet acci = new AccesoInternet();
   	String internet = acci.checkConnectivity(this);
   	
   	if(internet.equals("1")){
   		lista = new ArrayList<ItemPedidos>();
	    
	     SoapSerializationEnvelope envelope=null;
		
		 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		 request.addProperty("request1", estadopedido);
		 request.addProperty("request2", idUsuario);
	
		  envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		  envelope.dotNet = true; 
		  envelope.setOutputSoapObject(request);
		  	  
		  HttpTransportSE transporte = new HttpTransportSE(URL);
		  
				try {
					
					transporte.call(SOAP_ACTION, envelope);
					SoapObject result = (SoapObject) envelope.bodyIn;
					 if (result != null) {
						
					 String strJSON = "{\"pedidos\":" + result.getProperty(0).toString() + "}";
					 if (strJSON != null){
		            	JSONObject jsonObj;	
		            try {
							
							jsonObj = new JSONObject(strJSON);
							
							ped = jsonObj.getJSONArray("pedidos");

							 
			                  for (int i = 0; i < ped.length(); i++) {
			                      JSONObject c = ped.getJSONObject(i);
			   
			                      int id = c.getInt("idConsulta");
			                      String fecha = c.getString("fecha");
			                      double total = c.getDouble("total");
			                     
			                      ItemPedidos item=new ItemPedidos();
			                      item.setIdPedido(id);
			                      item.setFecha(fecha);
			                      item.setTotal(total);
			                      lista.add(item);
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
				
				  CustomListViewPedidos adaptador = new CustomListViewPedidos(a,lista);
		          listViewListaPedidos.setAdapter(adaptador); 
		          listViewListaPedidos.setOnItemClickListener(new ItemClickListener());
		          listViewListaPedidos.refreshDrawableState();
	
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
  	 			ItemPedidos itemseleccionado = (ItemPedidos)  listViewListaPedidos.getItemAtPosition(position);
  	 			Intent intent = null;
  	 			intent = new Intent(item.getContext(), DetallePedidoActivity.class);
  	 			intent.putExtra("id_pedido",""+ itemseleccionado.getIdPedido());
  				startActivity(intent);
  	 		}
  	     }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pedidos, menu);
		return true;
	}
}
