package com.ec.example.tiendaonline;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class RegistrarClienteActivity extends Activity {
	MenuPrincipalActivity mp;
	String ip = mp.IP.toString();
	private final String NAMESPACE = "http://servicio.upse.com";
	private final String URL="http://"+ip+":8080/ServicioWebPrueba/services/ServicioWeb?wsdl";
	private final String METHOD_NAME = "registrousuario";
	private final String SOAP_ACTION = "http://"+ip+":8080/ServicioWebPrueba/services/ServicioWeb/registrousuario";

	private SoapObject request=null;
	private SoapSerializationEnvelope envelope=null;
	
    TextView textViewApellido,textViewNombre,textViewCedula,textViewEmail,
              textViewDireccion,textViewTelefono,textViewAlias,textViewPassword;
    EditText editTextNombre,editTextApellidos,editTextCedula,editTextEmail,
             editTextDireccion,editTextTelefono,editTextAlias,editTextContrasena;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registrar_cliente);
		
		Inicializar();
      
	}
	
	public void Inicializar(){
		
		textViewApellido = (TextView) findViewById(R.id.textViewApellido);
		textViewNombre = (TextView) findViewById(R.id.textViewNombre);
		textViewCedula = (TextView) findViewById(R.id.textViewCedula);
		textViewEmail = (TextView) findViewById(R.id.textViewEmail);
		textViewDireccion = (TextView) findViewById(R.id.textViewDireccion);
		textViewTelefono = (TextView) findViewById(R.id.textViewTelefono);
		textViewAlias = (TextView) findViewById(R.id.textViewAlias);
		textViewPassword = (TextView) findViewById(R.id.textViewPassword);
		
		editTextNombre = (EditText) findViewById(R.id.editTextNombre);
		editTextApellidos = (EditText) findViewById(R.id.editTextApellidos);
		editTextCedula = (EditText) findViewById(R.id.editTextCedula);
		editTextEmail = (EditText) findViewById(R.id.editTextEmail);
		editTextDireccion = (EditText) findViewById(R.id.editTextDireccion);
		editTextTelefono = (EditText) findViewById(R.id.editTextTelefono);
		editTextAlias = (EditText) findViewById(R.id.editTextAlias);
		editTextContrasena = (EditText) findViewById(R.id.editTextContrasena);
	}

	public void onAceptar(View v){
		
		String nombres = editTextNombre.getText().toString();
		String apellidos = editTextApellidos.getText().toString();
		String cedula = editTextCedula.getText().toString();
		String email = editTextEmail.getText().toString();
		String direccion = editTextDireccion.getText().toString();
		String telefono = editTextTelefono.getText().toString();
		String alias = editTextAlias.getText().toString();
		String dpassword = editTextContrasena.getText().toString();
				
		request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("request1",nombres);
		request.addProperty("request2",apellidos);
		request.addProperty("request3",cedula);
		request.addProperty("request4",email);
		request.addProperty("request5",direccion);
		request.addProperty("request6",telefono);
		request.addProperty("request7",2);
		request.addProperty("request8",alias);
		request.addProperty("request9",dpassword);
		
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	    envelope.dotNet = true;
	    envelope.setOutputSoapObject(request);
	    
	    HttpTransportSE transporte = new HttpTransportSE(URL);
		
		try {
			
	    	transporte.call(SOAP_ACTION, envelope);
	    	SoapObject result = (SoapObject) envelope.bodyIn;
	    		
	    	
	    	if(isEmpty()){
				Toast.makeText(this,"Falta(n)de ingresar algun(os) Campo(s)!!", Toast.LENGTH_LONG).show();
	    	
	    	}else if(result != null){
				
	  			//Toast.makeText(getApplicationContext(), result.getProperty(0).toString(), Toast.LENGTH_SHORT).show();
	  			if( result.getProperty(0).toString().equals("1")){
	  				Toast.makeText(getApplicationContext(), "Agregado Exitoso", Toast.LENGTH_SHORT).show();	  				
	  			}
	  			if( result.getProperty(0).toString().equals("2")){
	  				Toast.makeText(getApplicationContext(), "Usuario ya registrado", Toast.LENGTH_SHORT).show();	  				
	  			}
	  			if( result.getProperty(0).toString().equals("0")){
	  				Toast.makeText(getApplicationContext(), "No se pudo ingresar", Toast.LENGTH_SHORT).show();	  				
	  			}
	  			}else{
	  			Toast.makeText(getApplicationContext(), "No Response!", Toast.LENGTH_SHORT).show();
	  		}
	  		
	  		
	  		}catch (Exception e) {
	  			e.printStackTrace();
	  			Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
	  	  		
	  		}
		
		Limpiar();
    	}
		
	

public void Limpiar(){
	
	editTextNombre.setText("");
	editTextApellidos.setText("");
	editTextCedula.setText("");
	editTextEmail.setText("");
	editTextDireccion.setText("");
	editTextTelefono.setText("");
	editTextAlias.setText("");
	editTextContrasena.setText("");
}
    
    public boolean isEmpty(){
		if(editTextNombre.getText().toString().equals("")|| editTextApellidos.getText().toString().equals("") || editTextCedula.getText().toString().equals("")|| editTextEmail.getText().toString().equals("") || editTextDireccion.getText().toString().equals("") || editTextTelefono.getText().toString().equals("") || editTextAlias .getText().toString().equals("") || editTextContrasena .getText().toString().equals("") ){
			return true;
		}
		else{
			return false;
		}
	}
	
    public void aceptar() {
    	this.finish();
		Intent intent= new Intent(this,LoginActivity.class);
		startActivity(intent);
    }

	public void onCancelar(View v){
		Intent intent= new Intent(this,LoginActivity.class);
		startActivity(intent);
        this.finish();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registrar_cliente, menu);
		return true;
	}

}









