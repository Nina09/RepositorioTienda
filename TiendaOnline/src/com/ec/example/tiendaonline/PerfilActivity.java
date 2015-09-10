package com.ec.example.tiendaonline;


//import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.ec.example.tiendaonline.internet.AccesoInternet;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class PerfilActivity extends Activity {
final String ip = "172.18.12.147";
	
	final String NAMESPACE = "http://servicio.upse.com";
	final String URL ="http://"+ip+":8080/ServicioWebPrueba/services/ServicioWeb?wsdl";
	final String METHOD_NAME = "iniciosesion";
	final String SOAP_ACTION = "http://"+ip+":8080/ServicioWebPrueba/services/ServicioWeb/iniciosesion";
 
	private SoapSerializationEnvelope envelope=null;

 
	String user,contrasena;
	TextView textViewUsuarioPerfil,textViewPerfil,textViewNombrePerfil,textViewApellidosPerfil,
	         textViewCedulaPerfil,textViewEmailPerfil,textViewDireccionPerfil,textviewTelefonoPerfilUsuario,
	         textViewNombrePerfilUsuario,textViewApellidosPerfilUsuario,textViewCedulaPerfilUsuario,
	         textViewEmailPerfilCliente,textViewDireccionPerfilCliente,textViewTelefonoPerfilCliente;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_perfil);
		
		Intent intent = this.getIntent();
		user= intent.getStringExtra("alias");
		contrasena= intent.getStringExtra("pass");
		
		inicializar();
		Perfilusuario();
	}
	
	public void inicializar(){
		textViewUsuarioPerfil = (TextView) findViewById(R.id.textViewUsuarioPerfil);
		textViewPerfil = (TextView) findViewById(R.id.textViewPerfil);
		textViewNombrePerfil = (TextView) findViewById(R.id.textViewNombrePerfil);
		textViewApellidosPerfil = (TextView) findViewById(R.id.textViewApellidosPerfil);
		textViewCedulaPerfil = (TextView) findViewById(R.id.textViewCedulaPerfil);
		textViewEmailPerfil = (TextView) findViewById(R.id.textViewEmailPerfil);
		textViewDireccionPerfil = (TextView) findViewById(R.id.textViewDireccionPerfil);
		textviewTelefonoPerfilUsuario = (TextView) findViewById(R.id.textviewTelefonoPerfilUsuario);
		
		textViewNombrePerfilUsuario = (TextView) findViewById(R.id.textViewNombrePerfilUsuario);
		textViewApellidosPerfilUsuario = (TextView) findViewById(R.id.textViewApellidosPerfilUsuario);
		textViewCedulaPerfilUsuario = (TextView) findViewById(R.id.textViewCedulaPerfilUsuario);
		textViewEmailPerfilCliente = (TextView) findViewById(R.id.textViewEmailPerfilCliente);
		textViewDireccionPerfilCliente = (TextView) findViewById(R.id.textViewDireccionPerfilCliente);
		textViewTelefonoPerfilCliente = (TextView) findViewById(R.id.textViewTelefonoPerfilCliente);
	}
	
	public void Perfilusuario(){

    	String nombCli="";
    	String apellCli="";
    	String cedCli="";
    	String telefCli="";
    	String aliasCli="";
    	String direc="";
    	String email="";
    	
    	AccesoInternet acci = new AccesoInternet();
    	String internet = acci.checkConnectivity(this);
    	
    	if(internet.equals("1")){
    		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
 		   request.addProperty("request1", user);
 	       request.addProperty("request2", contrasena);
 	    	
 	    	envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
 		    envelope.dotNet = true;
 		    envelope.setOutputSoapObject(request);
 		    
 		    HttpTransportSE transporte = new HttpTransportSE(URL);
 		    try {
 		    
 	    	transporte.call(SOAP_ACTION, envelope);
 	    	SoapObject result = (SoapObject) envelope.bodyIn;
 	    	
 	    	if(result != null){
 	  			String UsuarioJSon = result.getProperty(0).toString();
 	  		  if (UsuarioJSon != null) {
 	              try {
 	                  JSONObject jsonObj = new JSONObject(UsuarioJSon);
 	                  
 	                     
 	                      nombCli=jsonObj.getString("nombres");
 	                      apellCli=jsonObj.getString("apellidos");
 	                      cedCli=jsonObj.getString("cedula");
 	                      telefCli=jsonObj.getString("telefono");
 	                      aliasCli=jsonObj.getString("alias");
 	                      direc=jsonObj.getString("direccion");
 	                      email=jsonObj.getString("email");
 	                       
 	                  }
 	               catch (JSONException e) {
 	                  e.printStackTrace();
 	                  Log.e("ServiceHandler", "Esta habiendo problemas para cargar el JSON");
 	              }
 	  		  }
 	    	  }
 		    }catch (Exception e) {
 	  			e.printStackTrace();
 	  			Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();	
 	  		}
     	textViewUsuarioPerfil.setText(aliasCli);
     	textViewNombrePerfilUsuario.setText(nombCli);
 		textViewApellidosPerfilUsuario.setText(apellCli);
 		textViewCedulaPerfilUsuario.setText(cedCli);
 		textViewTelefonoPerfilCliente.setText(telefCli);
 		textViewEmailPerfilCliente.setText(email);
 		textViewDireccionPerfilCliente.setText(direc);
    	}
		else
		{
			textViewUsuarioPerfil.setText("");
	     	textViewNombrePerfilUsuario.setText("");
	 		textViewApellidosPerfilUsuario.setText("");
	 		textViewCedulaPerfilUsuario.setText("");
	 		textViewTelefonoPerfilCliente.setText("");
	 		textViewEmailPerfilCliente.setText("");
	 		textViewDireccionPerfilCliente.setText("");
			Toast.makeText(getApplicationContext(),"Verifique su conexión a internet", Toast.LENGTH_SHORT).show();
		}
    	
    	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.perfil, menu);
		return true;
	}
}
