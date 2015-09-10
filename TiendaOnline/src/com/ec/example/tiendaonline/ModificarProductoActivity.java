package com.ec.example.tiendaonline;

import com.ec.example.baseTemporal.SQLControlador;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ModificarProductoActivity extends Activity {

	TextView textViewNombreProductoEditar,textViewPrecioProductoEditar,
	         textViewDescripcionProductoEditar,textViewCantidadProductoEditar,
	         textViewSubtotalProductoEditar;
	EditText editTextNuevaCantidadProdCarroCompras;
	
	int idProducto=0;
	String nombreproducto="";
	String descripcion="";
	double precio=0.00;
	int cantidad=0;
	double subtotal=0.00;
	String nombres="";
	String apellidos="";
	String cedula="";
	int idUsuario=0;
	int cantidadactual=0;
	double subtotalactual=0.00;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_prod_carrito);
	
		Intent intent = this.getIntent();
		idProducto= Integer.parseInt(intent.getStringExtra("idProducto"));
		nombreproducto= intent.getStringExtra("nombre");
	    descripcion= intent.getStringExtra("descripcion");
	    precio= Double.parseDouble(intent.getStringExtra("precio"));
	    cantidadactual= Integer.parseInt(intent.getStringExtra("cantidad"));
	    subtotalactual= Double.parseDouble(intent.getStringExtra("subtotal"));
	    
	    idUsuario= Integer.parseInt(intent.getStringExtra("idusuario"));
	    nombres= intent.getStringExtra("nombres");
		apellidos= intent.getStringExtra("apellidos");
		cedula= intent.getStringExtra("cedula");
		
		textViewNombreProductoEditar = (TextView) findViewById(R.id.textViewNombreProductoEditar);
		textViewCantidadProductoEditar = (TextView) findViewById(R.id.textViewCantidadProductoEditar);
		textViewDescripcionProductoEditar = (TextView) findViewById(R.id.textViewDescripcionProductoEditar);
		textViewPrecioProductoEditar = (TextView) findViewById(R.id.textViewPrecioProductoEditar);
		textViewSubtotalProductoEditar = (TextView) findViewById(R.id.textViewSubtotalProductoEditar);
		editTextNuevaCantidadProdCarroCompras = (EditText) findViewById(R.id.editTextNuevaCantidadProdCarroCompras);
		
		textViewNombreProductoEditar.setText(nombreproducto);
		textViewDescripcionProductoEditar.setText(descripcion);
		textViewPrecioProductoEditar.setText(""+precio);
		textViewCantidadProductoEditar.setText(""+cantidadactual);
		textViewSubtotalProductoEditar.setText(""+subtotalactual);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.modificar_producto, menu);
		return true;
	}
	
	
	public void onGuardar(View v){
		
		cantidad = Integer.parseInt(editTextNuevaCantidadProdCarroCompras.getText().toString());
		subtotal = precio * cantidad;
		
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(ModificarProductoActivity.this);
      	alertDialog.setMessage("¿Modificar producto?");
      	alertDialog.setTitle("Aceptar Cambios");
      	alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
      	alertDialog.setCancelable(false);
      	alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() 
        {
      		
      		public void onClick(DialogInterface dialog, int which) 
            {
      			 Toast.makeText(getApplicationContext(), 
                   		"Pedido Cancelado", Toast.LENGTH_LONG).show();
            }
           
         });
    	alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() 
        {
      		public void onClick(DialogInterface dialog, int which) 
            {
               modificar();
               Toast.makeText(getApplicationContext(), 
                 		"Modificacion realizada", Toast.LENGTH_LONG).show();
               carrito();
               
            }
          }); 
              
          alertDialog.show(); 
		
	}
	
	public void onCancelar(View v){
		this.finish();
		Intent intent= new Intent(this,CarritoActivity.class);
		intent.putExtra("idusuario",""+idUsuario);
		intent.putExtra("nombre",nombres);
		intent.putExtra("apellidos",apellidos);
		intent.putExtra("cedula",cedula);
		 startActivity(intent);
	}

	public void modificar(){
		editTextNuevaCantidadProdCarroCompras.setText("");
		SQLControlador carritoDB = new SQLControlador();
		carritoDB.ModificarProducto(this, idProducto,cantidad,subtotal);
	}
	
	public void carrito(){
		this.finish();
		Intent intent= new Intent(this,CarritoActivity.class);
		intent.putExtra("idusuario",""+idUsuario);
		intent.putExtra("nombre",nombres);
		intent.putExtra("apellidos",apellidos);
		intent.putExtra("cedula",cedula);
		 startActivity(intent);
	}
	}
