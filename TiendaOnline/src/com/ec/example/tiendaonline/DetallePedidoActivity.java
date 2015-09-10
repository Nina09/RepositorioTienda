package com.ec.example.tiendaonline;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.ec.example.tiendaonline.internet.AccesoInternet;
import com.ec.example.tiendaonline.utiles.CustomListViewProductosDetallePedido;
import com.ec.example.tiendaonline.utiles.ItemProductosDetallePedido;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DetallePedidoActivity extends Activity {
	final String ip = "192.192.192.3";
	
	TextView textViewFechaDetallePedido,textViewTotalDetallePedido,textViewTituloPedido,textViewFecha,
	         NombreDetallePedido,CedulaDetallePedido,NumPedidoDetallePedido,IVADetallePedido,
	         textViewNombreDetallePedido,TextViewCedulaDetallePedido,textViewNumDetallePedido,textViewIVADetallePedido,
	         textViewNombreProductoDetalle,textViewCantidadProducto,textViewPrecioProducto,textViewSubtotal,
	         textViewSubtotalPedid,textViewTotalDetalle,textViewSubtotalDetallePedido;
	
	ListView listViewListasDetallePedido;
	//
	
	Activity a;
	Context context;
	static ArrayList<ItemProductosDetallePedido> lista;
	
	int idPedido=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle_pedido);
		///text.
		Inicializar();
		
		lista = new ArrayList<ItemProductosDetallePedido>();
        a=this;
        context=getApplicationContext();
		
		
		Intent intent = this.getIntent();
		idPedido= Integer.parseInt(intent.getStringExtra("id_pedido"));
		
		AccesoInternet acci = new AccesoInternet();
	    String internet = acci.checkConnectivity(this);
	    	
	    if(internet.equals("1")){	
		   CabeceraPedido();
		   DetallePedido();
	   }else
		{
			Toast.makeText(getApplicationContext(),"Verifique su conexión a internet", Toast.LENGTH_SHORT).show();
		}

	}
	public void Inicializar(){
		textViewFechaDetallePedido=(TextView) findViewById(R.id.textViewFechaDetallePedido);
		textViewTotalDetallePedido=(TextView) findViewById(R.id.textViewTotalCarrito);
		textViewTituloPedido=(TextView) findViewById(R.id.textViewTituloPedido);
		textViewFecha=(TextView) findViewById(R.id.textViewFecha);
		NombreDetallePedido=(TextView) findViewById(R.id.NombreDetallePedido);
		CedulaDetallePedido=(TextView) findViewById(R.id.CedulaDetallePedido);
		NumPedidoDetallePedido=(TextView) findViewById(R.id.NumPedidoDetallePedido);
		
		textViewNombreDetallePedido=(TextView) findViewById(R.id.textViewNombreDetallePedido);
		TextViewCedulaDetallePedido=(TextView) findViewById(R.id.TextViewCedulaDetallePedido);
		textViewNumDetallePedido=(TextView) findViewById(R.id.textViewNumDetallePedido);
		textViewIVADetallePedido=(TextView) findViewById(R.id.textViewIVADetallePedido);
		
		textViewNombreProductoDetalle=(TextView) findViewById(R.id.textViewNombreProductoDetalle);
		textViewCantidadProducto=(TextView) findViewById(R.id.textViewCantidadProducto);
		textViewPrecioProducto=(TextView) findViewById(R.id.textViewPrecioProducto);
		textViewSubtotal=(TextView) findViewById(R.id.textViewSubtotal);
		textViewSubtotalPedid=(TextView) findViewById(R.id.textViewSubtotalPedid);
		textViewSubtotalDetallePedido=(TextView) findViewById(R.id.textViewSubtotalDetallePedido);
		textViewTotalDetalle=(TextView) findViewById(R.id.textViewTotalDetalle);
		
		listViewListasDetallePedido=(ListView) findViewById(R.id.listViewListasDetallePedido);
		
	}
	
    public void CabeceraPedido(){
    	int numeroFactura=0;
        String fecha="";
        double subtotal=0;
        double total=0;	
        String nombres="";
        String apellidos="";
        String cedula="";
        double iva=0.00;
    	
    	
    	final String NAMESPACE = "http://servicio.upse.com";
    	final String URL ="http://"+ip+":8080/ServicioWeb/services/ServicioWeb?wsdl";
    	final String METHOD_NAME = "consultaPedidoxId";
    	final String SOAP_ACTION = "http://"+ip+":8080/ServicioWeb/services/ServicioWeb/consultaPedidoxId";
    	
    	 SoapSerializationEnvelope envelope=null;
		
		 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		 
		 request.addProperty("request1", idPedido);
		 
		  envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		  envelope.dotNet = true; 
		  envelope.setOutputSoapObject(request);
		  
		  HttpTransportSE transporte = new HttpTransportSE(URL);
		  
		  try {
				
				transporte.call(SOAP_ACTION, envelope);
				SoapObject result = (SoapObject) envelope.bodyIn;
				
				  if (result != null) {
				  
			     String strJSON = result.getProperty(0).toString();
			     JSONArray cab;
			
			     if (strJSON != null){
	            	
	            try {
						 cab = new JSONArray(strJSON);
					
						  for (int i = 0; i < cab.length(); i++) {	      
						    	
							    nombres = cab.getJSONObject(i).getJSONObject("usuario").getJSONObject("persona").getString("nombres");
								apellidos = cab.getJSONObject(i).getJSONObject("usuario").getJSONObject("persona").getString("apellidos");
								cedula = cab.getJSONObject(i).getJSONObject("usuario").getJSONObject("persona").getString("cedula");
								fecha = cab.getJSONObject(i).getString("fecha");
								numeroFactura = cab.getJSONObject(i).getInt("idPedidos");
								subtotal =  cab.getJSONObject(i).getDouble("subtotal");
								total =  cab.getJSONObject(i).getDouble("total");
								iva =  cab.getJSONObject(i).getDouble("total_iva");	
		                  }
						  
						  textViewFechaDetallePedido.setText(fecha);
				          textViewNombreDetallePedido.setText(nombres + "" + apellidos);
				  		  TextViewCedulaDetallePedido.setText(cedula);
				  		  textViewNumDetallePedido.setText(""+numeroFactura);
				  		  textViewIVADetallePedido.setText(""+iva);
					  	  textViewSubtotalDetallePedido.setText(""+subtotal);
					  	  textViewTotalDetalle.setText(""+total);
						
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
	
	public void DetallePedido(){
		final String NAMESPACE = "http://servicio.upse.com";
    	final String URL ="http://"+ip+":8080/ServicioWeb/services/ServicioWeb?wsdl";
    	final String METHOD_NAME = "consultaPedidoxDetalleLista";
    	final String SOAP_ACTION = "http://"+ip+":8080/ServicioWeb/services/ServicioWeb/consultaPedidoxDetalleLista";
    	
    	 SoapSerializationEnvelope envelope=null;
		
		 SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		 
		 request.addProperty("request1", idPedido);
		 
		  envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		  envelope.dotNet = true; 
		  envelope.setOutputSoapObject(request);
		  
		  HttpTransportSE transporte = new HttpTransportSE(URL);
		  
		  try {
			  String descripcion = "";
              int cantidad = 0;
              double precio = 0.00;
              double subtotal=0.00;
              
				transporte.call(SOAP_ACTION, envelope);
				SoapObject result = (SoapObject) envelope.bodyIn;
				
				  if (result != null) {
					
				  String strJSON = result.getProperty(0).toString();
				  JSONArray det = null;
				 
				 if (strJSON != null){
	            	try {
						
						det =  new JSONArray(strJSON);
		                
		                  for (int i = 0; i < det.length(); i++) {
		                     
		                	  descripcion = det.getJSONObject(i).getJSONObject("productos").getString("nombre_producto"); 
		                      cantidad = det.getJSONObject(i).getInt("cantidad");
		                      precio = det.getJSONObject(i).getJSONObject("productos").getDouble("precio");
		                      subtotal = det.getJSONObject(i).getDouble("subtotal");  
		                      
		                      ItemProductosDetallePedido item=new ItemProductosDetallePedido();
		                      item.setDescripcion(descripcion);
		                      item.setCantidad(cantidad);
		                      item.setPrecio(precio);
		                      item.setSubtotal(subtotal);
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
		  
		  CustomListViewProductosDetallePedido adaptador = new CustomListViewProductosDetallePedido(a,lista);
          listViewListasDetallePedido.setAdapter(adaptador); 
          listViewListasDetallePedido.refreshDrawableState();

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detalle_pedido, menu);
		return true;
	}	
}
