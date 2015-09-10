package com.ec.example.tiendaonline;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import com.ec.example.baseTemporal.SQLControlador;
import com.ec.example.tiendaonline.internet.AccesoInternet;
import com.ec.example.tiendaonline.utiles.Carrito;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetalleProductoActivity extends Activity {
	final String ip = "192.192.192.3";
	final String NAMESPACE = "http://servicio.upse.com";
	final String URL ="http://"+ip+":8080/ServicioWeb/services/ServicioWeb?wsdl";
	final String METHOD_NAME = "consultaProductoDetalle";
	final String SOAP_ACTION = "http://"+ip+":8080/ServicioWeb/services/ServicioWeb/consultaProductoDetalle";
	
	private SoapSerializationEnvelope envelope=null;

	
	final String NAMESPACEValidar = "http://servicio.upse.com";
	final String URLValidar ="http://"+ip+":8080/ServicioWeb/services/ServicioWeb?wsdl";
	final String METHOD_NAMEValidar = "validar_stock";
	final String SOAP_ACTIONValidar = "http://"+ip+":8080/ServicioWeb/services/ServicioWeb/validar_stock";
	
	private SoapSerializationEnvelope envelopeValidar=null;

	
	
	
	JSONArray prod;
	
    ImageView imageViewLogoDetalle;
    TextView textViewNombreDetalleProducto,textViewDescripcion,textViewPrecio,textViewCantidad,textViewAgregarAlCarrito;
    EditText editTextCantidad;
    ImageButton imageButtonCarrito;
   
    int idProducto;
    String opcionproducto;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle_producto);
		
		Intent intent = this.getIntent();
		
		idProducto= Integer.parseInt(intent.getStringExtra("id_producto"));
		opcionproducto= intent.getStringExtra("opcionproducto");
		
		textViewNombreDetalleProducto=(TextView) findViewById(R.id.textViewNombreDetalleProducto);
		textViewDescripcion=(TextView) findViewById(R.id.textViewDescripcion);
		textViewPrecio=(TextView) findViewById(R.id.textViewPrecio);
		textViewCantidad=(TextView) findViewById(R.id.textViewCantidad);
		textViewAgregarAlCarrito=(TextView) findViewById(R.id.textViewAgregarAlCarrito);
		imageButtonCarrito=(ImageButton) findViewById(R.id.imageButtonCarrito);
		
		editTextCantidad=(EditText) findViewById(R.id.editTextCantidad);
		
		imageViewLogoDetalle=(ImageView) findViewById(R.id.imageViewLogoDetalle);
		
		if (opcionproducto.equals("1")){
			imageButtonCarrito.setVisibility(View.INVISIBLE);
			textViewCantidad.setVisibility(View.INVISIBLE);
			editTextCantidad.setVisibility(View.INVISIBLE);
			textViewAgregarAlCarrito.setVisibility(View.INVISIBLE);
		}
		if (opcionproducto.equals("0")){
			imageButtonCarrito.setVisibility(View.VISIBLE);
			textViewCantidad.setVisibility(View.VISIBLE);
			editTextCantidad.setVisibility(View.VISIBLE);
			textViewAgregarAlCarrito.setVisibility(View.VISIBLE);
		}
	
		String nombre="";
		String descProd="";
		double prec=0;
		String strJSONImagen="";
		
		AccesoInternet acci = new AccesoInternet();
    	String internet = acci.checkConnectivity(this);
    	
    	if(internet.equals("1")){
    		  SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
    		
    		  request.addProperty("request1", idProducto);
    		
    		  envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
    		  envelope.dotNet = true; 
    		  envelope.setOutputSoapObject(request);
    		  
    		  HttpTransportSE transporte = new HttpTransportSE(URL);
    		  
    				try {
    					transporte.call(SOAP_ACTION, envelope);
    					SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
    					
    					  if (resultsRequestSOAP != null) {
    						
    					String strJSON = "{\"detalle_producto\":" + resultsRequestSOAP.getProperty(0).toString() + "}";
    						  
    					  if (strJSON != null){
    						
    		            	JSONObject jsonObj = new JSONObject(strJSON);
    						try {
    							jsonObj = new JSONObject(strJSON);
    							prod = jsonObj.getJSONArray("detalle_producto");

    			                  for (int i = 0; i < prod.length(); i++) {
    			                       JSONObject c = prod.getJSONObject(i);
    			                        nombre = c.getString("nombre_producto");
    			        	    		descProd = c.getString("descripcion");
    			        	    		prec = c.getDouble("precio");
    			        	          
    			        	    	String NAMESPACEImagen = "http://servicio.upse.com";
   			  	      			    String URLImagen ="http://"+ip+":8080/ServicioWeb/services/ServicioWeb?wsdl";
   			  	      			    String METHOD_NAMEImagen = "consultarImagenServidor";
   			  	      			    String SOAP_ACTIONImagen = "http://"+ip+":8080/ServicioWeb/services/ServicioWeb/consultarImagenServidor";
   			  	      		  
   			  	                     SoapSerializationEnvelope envelopeimagen=null;
   	    			    			 SoapObject requestimagen = new SoapObject(NAMESPACEImagen, METHOD_NAMEImagen);
   	    			    			
   	    			    			 requestimagen.addProperty("request1", idProducto);
   	    			    			 
   	    			    			 envelopeimagen = new SoapSerializationEnvelope(SoapEnvelope.VER11);
   	    			    			 envelopeimagen.dotNet = true; 
   	    			    			 envelopeimagen.setOutputSoapObject(requestimagen);
   	    			    			  
   	    			    			  HttpTransportSE transporteimagen = new HttpTransportSE(URLImagen);
   	    			    			 
   	    			    			  transporteimagen.call(SOAP_ACTIONImagen, envelopeimagen);
   	    	    					   SoapObject resultimagen = (SoapObject) envelopeimagen.bodyIn;
   	    	    					  strJSONImagen = resultimagen.getProperty(0).toString();
   			  	                      
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
    							
    				 textViewNombreDetalleProducto.setText(nombre);
    				 textViewDescripcion.setText(descProd);
    		    	 String valor = Double.toString(prec);
    		    	 textViewPrecio.setText(valor);
    		    	 
    		    	 URL imageUrl = null;
    		    	 HttpURLConnection conn = null;
    		    	 
    		    	 try {
    		    	 
    		    	 imageUrl = new URL("http://" + strJSONImagen);
    		    	 conn = (HttpURLConnection) imageUrl.openConnection();
    		    	 conn.connect();
    		    	  
    		    	 BitmapFactory.Options options = new BitmapFactory.Options();
    		    	 options.inSampleSize = 2; // el factor de escala a minimizar la imagen, siempre es potencia de 2
    		    	 
    		    	 Bitmap imagen = BitmapFactory.decodeStream(conn.getInputStream(), new Rect(0, 0, 0, 0), options);
    		    	 imageViewLogoDetalle.setImageBitmap(imagen);
    		    	 
    		    	 } catch (IOException e) {
    		    	 
    		    	 e.printStackTrace();
    		    	 
    		    	 }

		}
		else
		{
			Toast.makeText(getApplicationContext(),"Verifique su conexión a internet", Toast.LENGTH_SHORT).show();
		}
	}

	
	
	public void onAgregar(View v){
		// TODO Auto-generated method stub
		          SQLControlador dbconeccion= new SQLControlador();
		    
				   String nombre = textViewNombreDetalleProducto.getText().toString();
				   String descrip = textViewDescripcion.getText().toString();
				   double precio = Double.parseDouble(textViewPrecio.getText().toString());
				   
				   
				     if (editTextCantidad.getText().toString().equals(""))
					   {
						   Toast.makeText(getApplicationContext(),"Ingrese Cantidad" , Toast.LENGTH_SHORT).show();
					   }
					   else
					   {
						   SQLControlador carritoDB = new SQLControlador(); 
						   Carrito carrito = carritoDB.BuscarProducto(this,idProducto);
						 
						   if (carrito == null){
							   
								  SoapObject request = new SoapObject(NAMESPACEValidar, METHOD_NAMEValidar);
					    			int canti = Integer.parseInt(editTextCantidad.getText().toString());
								  request.addProperty("request1", idProducto);
							       request.addProperty("request2", canti);
							    
						   		  envelopeValidar = new SoapSerializationEnvelope(SoapEnvelope.VER11);
						   		  envelopeValidar.dotNet = true; 
						   		  envelopeValidar.setOutputSoapObject(request);
						   		  
						   		  HttpTransportSE transporte = new HttpTransportSE(URLValidar);
						   		  
						   				try {
						   					transporte.call(SOAP_ACTIONValidar, envelopeValidar);
						   					SoapObject resultsRequestSOAP = (SoapObject) envelopeValidar.bodyIn;
						   					if(resultsRequestSOAP!=null)
						   					{
						   						String strJSON = resultsRequestSOAP.getProperty(0).toString();
						   						if(strJSON.equals("1"))
						   						{
						   							int cantidad = Integer.parseInt(editTextCantidad.getText().toString());
													   double tot = precio * cantidad; 
													   dbconeccion.nuevoProducto(this,idProducto,nombre,descrip,cantidad,precio,tot);
													   Toast.makeText(getApplicationContext(),"Producto agregado a carrito correctamente" , Toast.LENGTH_SHORT).show();
													   editTextCantidad.setText("");
						   						}
						   						else
						   						{
						   						 Toast.makeText(getApplicationContext(),"Producto no agregado.....Cantidad excedida al stock" , Toast.LENGTH_SHORT).show();
						   						}	
						   					   
						   					}
						   				}catch (Exception e) {
						   		  			e.printStackTrace();
						   		  			Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();	
						   		  		}	
							   							   
						   }
						   else
						   {
							   Toast.makeText(getApplicationContext(),"Este prodcuto ya esta agregado al carrito   Verifique!!!!!" , Toast.LENGTH_SHORT).show();
						   }	
						
					}		  
}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detalle_producto, menu);
		return true;
	}

}


