package com.ec.example.tiendaonline;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import com.ec.example.tiendaonline.entidades.Cliente;
import com.ec.example.tiendaonline.internet.AccesoInternet;
import com.ec.example.tiendaonline.utiles.Categorias;
import com.ec.example.tiendaonline.utiles.CustomListViewCategorias;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

@SuppressLint("NewApi") public class LoginActivity extends Activity {
	
	final String ip = "192.192.192.3";
	final String NAMESPACELOGIN = "http://servicio.upse.com";
	final String URLLOGIN ="http://"+ip+":8080/ServicioWeb/services/ServicioWeb?wsdl";
	final String METHOD_NAMELOGIN = "iniciosesion";
	final String SOAP_ACTIONLOGIN = "http://"+ip+":8080/ServicioWeb/services/ServicioWeb/iniciosesion";
	
	private SoapSerializationEnvelope envelopelogin=null;
	
	final String NAMESPACECATEGORIA = "http://servicio.upse.com";
	final String URLCATEGORIA ="http://"+ip+":8080/ServicioWeb/services/ServicioWeb?wsdl";
	final String METHOD_NAMECATEGORIA = "consultaProductoCategoria";
	final String SOAP_ACTIONCATEGORIA = "http://"+ip+":8080/ServicioWeb/services/ServicioWeb/consultaProductoCategoria";
  
	private SoapSerializationEnvelope envelopecategoria=null;

	Activity a;
	Context context;
	static ArrayList<Categorias> lista;
	JSONArray cat;
	
	ListView listViewListaProductosLogin;
	
	
	EditText editTextUsuarioLogin,editTextContrasenaLogin;
	TextView textViewRegistrarse;
	
	String alias,password;
	int idUsu;
	String nombre, apellidos,cedula;
	
   
   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Inicializar();
        
        if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
        CargaCategorias();
      }
    
    
    public void Inicializar(){
    	 lista = new ArrayList<Categorias>();
         a=this;
         context=getApplicationContext();
        
    	 editTextUsuarioLogin = (EditText) findViewById(R.id.editTextUsuarioLogin);
         editTextContrasenaLogin = (EditText) findViewById(R.id.editTextContrasenaLogin);
         listViewListaProductosLogin = (ListView) findViewById(R.id.listViewListaProductosLogin);
    }


    public void CargaCategorias(){
    	lista = new ArrayList<Categorias>();
        a=this;
        context=getApplicationContext();
        
        AccesoInternet acci = new AccesoInternet();
    	String internet = acci.checkConnectivity(this);
    	
   	if(internet.equals("1")){
    		 SoapObject request = new SoapObject(NAMESPACECATEGORIA, METHOD_NAMECATEGORIA);
    			
   		  envelopecategoria = new SoapSerializationEnvelope(SoapEnvelope.VER11);
   		  envelopecategoria.dotNet = true; 
   		  envelopecategoria.setOutputSoapObject(request);
   		  
   		  HttpTransportSE transporte = new HttpTransportSE(URLCATEGORIA);
   		  
   				try {
   					transporte.call(SOAP_ACTIONCATEGORIA, envelopecategoria);
   					SoapObject resultsRequestSOAP = (SoapObject) envelopecategoria.bodyIn;
   					
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
   		   listViewListaProductosLogin.setAdapter((ListAdapter) adaptador);
   		   listViewListaProductosLogin.setOnItemClickListener(new ItemClickListener());
   		   listViewListaProductosLogin.refreshDrawableState();

		}
		else
		{
			Toast.makeText(getApplicationContext(),"Verifique su conexión a internet", Toast.LENGTH_SHORT).show();
		}

    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }
    
    
    public void onIniciarSesion(View v){
    	    	
    	AccesoInternet acci = new AccesoInternet();
    	String internet = acci.checkConnectivity(this);
    	
		if(internet.equals("1")){
			alias = editTextUsuarioLogin.getText().toString();
	    	password = editTextContrasenaLogin.getText().toString();
			
			SoapObject request = new SoapObject(NAMESPACELOGIN, METHOD_NAMELOGIN);
			   request.addProperty("request1", alias);
		       request.addProperty("request2", password);
		    	
		    	envelopelogin = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			    envelopelogin.dotNet = true;
			    envelopelogin.setOutputSoapObject(request);
			    
			    HttpTransportSE transporte = new HttpTransportSE(URLLOGIN);
			    try {
			    
		    	transporte.call(SOAP_ACTIONLOGIN, envelopelogin);
		    	SoapObject result = (SoapObject) envelopelogin.bodyIn;
		    	
		    	if(result != null){
		  			
		  			String UsuarioJSon = result.getProperty(0).toString();
		  			Cliente client = new Cliente();
		  			
		  		  if (UsuarioJSon != null) {
		              try {
		                  JSONObject jsonObj = new JSONObject(UsuarioJSon);
		                  
		                     
		                      client.setNombre(jsonObj.getString("nombres"));
		                      client.setApellido(jsonObj.getString("apellidos"));
		                      client.setCedula(jsonObj.getString("cedula"));
		                      client.setTelefono(jsonObj.getString("telefono"));
		                      client.setAlias(jsonObj.getString("alias"));
		                      client.setTelefono(jsonObj.getString("telefono"));
		                      client.setId(jsonObj.getInt("id_usuario"));
		                      nombre=jsonObj.getString("nombres");
		                      apellidos=jsonObj.getString("apellidos");
		                      cedula=jsonObj.getString("cedula");
		                      idUsu= jsonObj.getInt("id_usuario");
		                       
		                  }
		               catch (JSONException e) {
		                  e.printStackTrace();
		                  Log.e("ServiceHandler", "Esta habiendo problemas para cargar el JSON");
		              }
		          } 
		  			
		  			if(client.getId()>0){
		  				Toast.makeText(getApplicationContext(), "Bienvenidos ", Toast.LENGTH_SHORT).show();
		  				Intent intent= new Intent(this,MenuPrincipalActivity.class);
			  			intent.putExtra("alias",alias);
			  			intent.putExtra("pass",password);
			  			intent.putExtra("nombre",""+nombre);
			  			intent.putExtra("apellidos",""+apellidos);
			  			intent.putExtra("cedula",""+cedula);
			  			intent.putExtra("idusuario",""+idUsu);
			  			startActivity(intent);
			  			this.finish();
		  			}
		  			else{
		  				Toast.makeText(getApplicationContext(), "Datos Ingresados Incorrectos", Toast.LENGTH_SHORT).show();		
		  				}
		  		    }else{
		  			Toast.makeText(getApplicationContext(), "No Responde!", Toast.LENGTH_SHORT).show();
		  		    }
		  		
		  		
		  		}catch (Exception e) {
		  			e.printStackTrace();
		  			Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();	
		  		}

		}
		else
		{
			Toast.makeText(getApplicationContext(),"Verifique su conexión a internet", Toast.LENGTH_SHORT).show();
		}
    }
   
     public void onResgistrase(View v){	 
    	AccesoInternet acci = new AccesoInternet();
     	String internet = acci.checkConnectivity(this);
     	
		if(internet.equals("1")){
			Intent intent= new Intent(this,RegistrarClienteActivity.class);
	     	startActivity(intent);
	 		//this.finish();
		}
		else
		{
			Toast.makeText(getApplicationContext(),"Verifique su conexión a internet", Toast.LENGTH_SHORT).show();
		}
     }
     
 	
 	public void onProductos(View v){
 		Intent intent= new Intent(this,ProductosActivity.class);
 		intent.putExtra("opcion","2");
 		intent.putExtra("opcionproducto", "1");
 		startActivity(intent);
 	}
 	
 	class ItemClickListener implements OnItemClickListener{
 		@Override
 		public void onItemClick(AdapterView<?> parent, View item, int position, long id) {
 			 //TODO Auto-generated method stub
 			Categorias itemseleccionado = (Categorias)  listViewListaProductosLogin.getItemAtPosition(position);
 			Intent intent = null;
 			intent = new Intent(item.getContext(), ProductosActivity.class);
 			intent.putExtra("id_categoria",""+ itemseleccionado.getIdCategoria());
 			intent.putExtra("opcionproducto", "1");
 			intent.putExtra("opcion", "1");
			startActivity(intent);
 		}
 		
     }
   
}
