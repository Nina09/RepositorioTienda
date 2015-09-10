package com.ec.example.tiendaonline;

import com.ec.example.tiendaonline.internet.AccesoInternet;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MenuPrincipalActivity extends Activity { 
	
    String user,contrasena;
    String nombre,apellidos,cedula;
    int idUsu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_principal);
		
		Intent intent = this.getIntent();
		user= intent.getStringExtra("alias");
		contrasena= intent.getStringExtra("pass");
		
		nombre= intent.getStringExtra("nombre");
		apellidos= intent.getStringExtra("apellidos");
		cedula= intent.getStringExtra("cedula");
		
		idUsu= Integer.parseInt(intent.getStringExtra("idusuario"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_principal, menu);
		return true;
	}
	
	public void onCategoria(View v){
		Intent intent= new Intent(this,CatalogoActivity.class);
		startActivity(intent);
	}
	
	public void onProductos(View v){
		Intent intent= new Intent(this,ProductosActivity.class);
		intent.putExtra("opcion","2");
		intent.putExtra("opcionproducto","0");
		startActivity(intent);
	}
	public void onCarrito(View v){
		Intent intent= new Intent(this,CarritoActivity.class);
		intent.putExtra("nombre",nombre);
		intent.putExtra("apellidos",apellidos);
		intent.putExtra("cedula",cedula);
		intent.putExtra("idusuario",""+idUsu);
		startActivity(intent);
	}
	public void onPedidos(View v){
		Intent intent= new Intent(this,ListarPedidosActivity.class);
		intent.putExtra("idusuario",""+idUsu);
		startActivity(intent);
	}
	
	public void onPerfil(View v){
		Intent intent= new Intent(this,PerfilActivity.class);
		intent.putExtra("alias",user);
    	intent.putExtra("pass",contrasena);
		startActivity(intent);
	}
	
	public void CerrarSesion(View v){
		  
      	AlertDialog.Builder alertDialog = new AlertDialog.Builder(MenuPrincipalActivity.this);
      	alertDialog.setMessage("¿Desea cerrar la sesion?");
      	alertDialog.setTitle("Cerrar sesión...");
      	alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
      	alertDialog.setCancelable(false);
      	alertDialog.setPositiveButton("No", new DialogInterface.OnClickListener() 
          {
      		public void onClick(DialogInterface dialog, int which) 
            {
              Toast.makeText(getApplicationContext(), 
              		"Cierro de la Sesión Cancelada", Toast.LENGTH_LONG).show();
            }
          }); 
          alertDialog.setNegativeButton("Si", new DialogInterface.OnClickListener() 
          {
        	  public void onClick(DialogInterface dialog, int which) 
              {
                try 
                { 
              	  TareaWSCerrarSesion tarea = new TareaWSCerrarSesion();
  		             tarea.execute();
                } 
                catch (Exception e) 
                {
                  Toast.makeText(getApplicationContext(), 
                		  "No se ha podido cerrar la sesión", Toast.LENGTH_LONG).show();
                }
              }
             
          }); 
          alertDialog.show(); 
	}
	
	
	private class TareaWSCerrarSesion extends AsyncTask<Void,Integer,Boolean> {
		 
		private ProgressDialog pDialog;
      
	      protected void onPreExecute() {
	    	   super.onPreExecute();
	           pDialog = new ProgressDialog(MenuPrincipalActivity.this);
	           pDialog.setMessage("Cerrando Sesión ...");
	           pDialog.setIndeterminate(false);
	           pDialog.setCancelable(true);
	           pDialog.show();
	      } 

	      @Override
			protected Boolean doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
	    	  boolean result = false; 
	    	  String enab = internet();
	    	   if (enab.equals("1")){
	    		   result=true;
	    	   }
				return result;
		 }
	      
	      protected void onPostExecute(Boolean result) {
	    	  super.onPostExecute(result);
	        	// Dismiss the progress dialog
		          if (pDialog.isShowing()){
		              pDialog.dismiss();
		          }
		          Login();
	      }  
	  }
	public String internet(){
		    String enable = "2";
		    AccesoInternet acci = new AccesoInternet();
        	String internet = acci.checkConnectivity(this);
        	
        	if(internet.equals("1")){
        		return enable = "1";
        	}
        	return enable;
	}
	
	
	
	public void Login(){
		Intent intent= new Intent(this,LoginActivity.class);
		startActivity(intent);
		this.finish();
	}
}
