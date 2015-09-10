package com.ec.example.tiendaonline;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import com.ec.example.tiendaonline.MenuPrincipalActivity;
import com.ec.example.baseTemporal.SQLControlador;
import com.ec.example.tiendaonline.utiles.CustomListViewCarrito;
import com.ec.example.tiendaonline.utiles.ItemCarrito;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CarritoActivity extends Activity {
	MenuPrincipalActivity mp;
	String ip  = mp.IP.toString();
	final String NAMESPACE = "http://servicio.upse.com";
	final String URL ="http://"+ ip +":8080/ServicioWebPrueba/services/ServicioWeb?wsdl";
	final String METHOD_NAME = "guardarpedido";
	final String SOAP_ACTION = "http://"+ip+":8080/ServicioWebPrueba/services/ServicioWeb/guardarpedido";
	final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	final LayoutInflater li = LayoutInflater.from(this);
	private SoapSerializationEnvelope envelope=null;
	
	ListView listViewCarritoProductos;
	TextView textViewCarrito,textViewNombreProductoDetalle,textViewCantidadProducto,textViewPrecioProducto,textViewTotalCarrito,
	         textViewTotalCarritoDetalle,textViewNombrePedidoCarrito,textViewCedulaPedidoCarrito,textViewFechaPedidoCarrito,
	         textViewSubtotalPedidoCarrito,textViewIvaPedidoCarrito;
	
	SQLControlador dbconeccion;
	
	Double subtot,subtotIva,tot;
	String fecha="";
	
	int idUsuario;
	String nombre,apellidos,cedula;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_carrito);
		
		 Intent intent = this.getIntent();
	     idUsuario= Integer.parseInt(intent.getStringExtra("idusuario"));
	     nombre= intent.getStringExtra("nombre");
		 apellidos= intent.getStringExtra("apellidos");
		 cedula= intent.getStringExtra("cedula");
		
		inicializar();
		Cargar();
	}
	
public void inicializar(){
		textViewCarrito=(TextView) findViewById(R.id.textViewCarrito);
		textViewNombreProductoDetalle=(TextView) findViewById(R.id.textViewNombreProductoDetalle);
		textViewCantidadProducto=(TextView) findViewById(R.id.textViewCantidadProducto);
		textViewPrecioProducto=(TextView) findViewById(R.id.textViewPrecioProducto);
		textViewTotalCarrito=(TextView) findViewById(R.id.textViewTotalCarrito);
		textViewTotalCarritoDetalle=(TextView) findViewById(R.id.textViewTotalCarritoDetalle);
		
		textViewNombrePedidoCarrito=(TextView) findViewById(R.id.textViewNombrePedidoCarrito);
		textViewCedulaPedidoCarrito=(TextView) findViewById(R.id.textViewCedulaPedidoCarrito);
		textViewFechaPedidoCarrito=(TextView) findViewById(R.id.textViewFechaPedidoCarrito);
		textViewSubtotalPedidoCarrito=(TextView) findViewById(R.id.textViewSubtotalPedidoCarrito);
		textViewIvaPedidoCarrito=(TextView) findViewById(R.id.textViewIvaPedidoCarrito);
		
		listViewCarritoProductos=(ListView) findViewById(R.id.listViewCarritoProductos);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		textViewFechaPedidoCarrito.setText(dateFormat.format(cal.getTime()));
		fecha=dateFormat.format(cal.getTime());
		
		textViewNombrePedidoCarrito.setText(nombre + " " + apellidos);
		textViewCedulaPedidoCarrito.setText(cedula);	 
	}

public void Cargar(){
		
		dbconeccion = new SQLControlador(this);
		dbconeccion.abrirBaseDeDatos();
        dbconeccion.listarCarrito();
			ArrayList<ItemCarrito> list_Productos = dbconeccion.listarCarrito();
			if (list_Productos != null){
				CustomListViewCarrito customListViewCarrito	= new CustomListViewCarrito(this,list_Productos); 
				listViewCarritoProductos.setAdapter(customListViewCarrito);
				listViewCarritoProductos.setOnItemClickListener(new ItemClickListener());	
				listViewCarritoProductos.refreshDrawableState();	
			}
        subtotal();
	}

 public void subtotal(){
	subtot = 0.00;
	dbconeccion.abrirBaseDeDatos();
	Cursor cursor = dbconeccion.leerDatos(); 
	if (cursor.getCount()>0)
	{
	do{
		 Double sub = Double.parseDouble(cursor.getString(5));
		 subtot = subtot + sub;
	    }while(cursor.moveToNext());
	    textViewSubtotalPedidoCarrito.setText(""+subtot);
     subIvaTotal();
	}
	else
	{
		subtot = 0.00;
		subtotIva=0.00;
		tot=0.00;
		textViewSubtotalPedidoCarrito.setText(""+subtot);
		textViewIvaPedidoCarrito.setText(""+subtotIva);
		textViewTotalCarritoDetalle.setText(""+tot);
	}
}

public void subIvaTotal(){
	subtotIva = 0.00;
	subtotIva = subtot * 0.12;
	tot=subtot + subtotIva;
	textViewIvaPedidoCarrito.setText(""+subtotIva);
	textViewTotalCarritoDetalle.setText(""+tot);
}



	public void GuardarCarrito(View v){
		  
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(CarritoActivity.this);
	      	alertDialog.setMessage("¿Confirmar el pedido?");
	      	alertDialog.setTitle("Confirmación");
	      	alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
	      	alertDialog.setCancelable(false);
	      	alertDialog.setNegativeButton("Si", new DialogInterface.OnClickListener() 
	        {
	      		
	      		public void onClick(DialogInterface dialog, int which) 
	            {
	              try 
	              { 
	            	dbconeccion.abrirBaseDeDatos();
	          		Cursor cursor = dbconeccion.leerDatos(); 
	          		if (cursor.getCount()>0)
	          		{
	          		String cadena = CarritoProductos();
	 	          	
	          		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
	 	 		   
	 	 		    request.addProperty("request1", idUsuario);
	 	 		    request.addProperty("request2", fecha);
	 	 		    request.addProperty("request3", subtot);
	 	 		    request.addProperty("request4", subtotIva);
	 	 	        request.addProperty("request5", tot);
	 	 	        request.addProperty("request6", cadena);
	 	 	    	
	 	 	    	envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	 	 		    envelope.dotNet = true;
	 	 		    envelope.setOutputSoapObject(request);
	 	 		    
	 	 		    HttpTransportSE transporte = new HttpTransportSE(URL);
	 	 		    try {
	 	 		    
	 	 	    	transporte.call(SOAP_ACTION, envelope);
	 	 	    	SoapObject result = (SoapObject) envelope.bodyIn;
	 	 	    	
	 	 	    	if(result != null){
	 	 	    		String resG = result.getProperty(0).toString();
	 	 	    		if (resG.equals("1")){
	 	 	    			Toast.makeText(getApplicationContext(),"Pedido Guardado Correctamente", Toast.LENGTH_SHORT).show();
	 	 	    		}
	 	 	    		else{
	 	 	    			Toast.makeText(getApplicationContext(),"Pedido No Guardado", Toast.LENGTH_SHORT).show();
	 	 	    		 }
	 	 	    	   }
	 	 		    }catch (Exception e) {
	 	 	  			e.printStackTrace();
	 	 	  			Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
	 	 		    }
	 	 		
	          		}
	          		else
	          		{
	          			Toast.makeText(getApplicationContext(),"No hay productos en el carrito", Toast.LENGTH_SHORT).show();
	          		}	
	            	
	              } 
	              catch (Exception e) 
	              {
	                Toast.makeText(getApplicationContext(), 
	              		  "No se ha podido realizar el pedido", Toast.LENGTH_LONG).show();
	              }
	            }
	           
	        });
	      	
	      	alertDialog.setPositiveButton("No", new DialogInterface.OnClickListener() 
	          {
	      		public void onClick(DialogInterface dialog, int which) 
	            {
	              Toast.makeText(getApplicationContext(), 
	              		"Pedido Cancelado", Toast.LENGTH_LONG).show();
	            }
	          }); 
	           
	          alertDialog.show(); 
	}
	
	
	public String CarritoProductos(){
		String resultado = "[";
		Integer contador = 0;
		
		Cursor c = dbconeccion.leerDatos();
		
		if(c.moveToFirst()){
			do{
				String Json = "{'idProductos':" + c.getString(0) +",'cantidad':'" + c.getString(4) + ",'subtotal':'" + c.getString(5) + "'}";
				contador = contador + 1;
				if (contador== 1){
					resultado = resultado + Json;
				}
				if (contador != 1){
					resultado = resultado + "," +Json;
				}
			}while(c.moveToNext());
		}
		return resultado + "]";
	}
	
	
	String idP="";
	class ItemClickListener implements OnItemClickListener{
 		@Override
 		public void onItemClick(AdapterView<?> parent, View item, int position, long id) {
 			 //TODO Auto-generated method stub
 			ItemCarrito itemseleccionado = (ItemCarrito)  listViewCarritoProductos.getItemAtPosition(position);
 			idP = itemseleccionado.getId();
 			AlertDialog.Builder alertDialog = new AlertDialog.Builder(CarritoActivity.this);
 	      	alertDialog.setMessage("¿Desea editar o eliminar el producto?");
 	      	alertDialog.setTitle("Eliminando producto...");
 	      	alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
 	      	alertDialog.setCancelable(true);
 	      	alertDialog.setNegativeButton("Editar", new DialogInterface.OnClickListener() 
 	        {
 	      		
 	      		public void onClick(DialogInterface dialog, int which) 
 	            {
 	      			View prompt = li.inflate(R.layout.activity_porducto_seleccionado_modificar, null);
 	        		alertDialogBuilder.setView(prompt);
 	        		alertDialogBuilder.setCancelable(false).setPositiveButton("Aceptar cambios", new DialogInterface.OnClickListener() {
 	    						@Override
 	    						public void onClick(DialogInterface dialog, int id) {
 	    							// TODO Auto-generated method stub
 	    						
 	    						
 	    						
 	    						}	
 	    					}).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
 	    						@Override
 	    						public void onClick(DialogInterface dialog, int id) {
 	    							// TODO Auto-generated method stub
 	    						
 	    						
 	    						
 	    						}	
 	    					});
 	    			AlertDialog alertDialog = alertDialogBuilder.create();
 	    			alertDialog.show();
 	    			
 	            }
 	           
 	        });
 	      	
 	      	alertDialog.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() 
 	          {
 	      		public void onClick(DialogInterface dialog, int which) 
 	            {
 	      			try 
 	 	              { 
 	 	            	 eliminarProducto(idP);
 	 	     			Toast.makeText(getApplicationContext(),"Producto eliminado de la lista exitosamente", Toast.LENGTH_SHORT).show();
 	 	     			Cargar();
 	 	              } 
 	 	              catch (Exception e) 
 	 	              {
 	 	                Toast.makeText(getApplicationContext(), 
 	 	              		  "No se ha podido eliminar el producto seleccionado", Toast.LENGTH_LONG).show();
 	 	              }
 	            }
 	          }); 
 	           
 	          alertDialog.show(); 
 		}
  }	
	public void eliminarProducto(String id){
		//dbconeccion.deleteData(memberID);
		dbconeccion = new SQLControlador(this);
		dbconeccion.abrirBaseDeDatos();
	    dbconeccion.deleteData(id);
	} 

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.carrito, menu);
		return true;
	}

}
