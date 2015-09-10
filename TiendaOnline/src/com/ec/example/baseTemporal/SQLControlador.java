package com.ec.example.baseTemporal;
import java.util.ArrayList;

import com.ec.example.tiendaonline.utiles.Carrito;
import com.ec.example.tiendaonline.utiles.ItemCarrito;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SQLControlador extends Activity{

	public static final String DB_NAME = "DB_Carrito";
	public static final String TABLE_NAME = "Compras";
	
	public void nuevoProducto(Context contexto, int idproducto,String nombre, String descripcion, int cantidad,double precio, double subtotal){

		BaseTemporalCarritoCompras tarjetaDB= new BaseTemporalCarritoCompras(contexto,DB_NAME, null, 1);
		SQLiteDatabase db = tarjetaDB.getWritableDatabase();
		
		if(db!=null){
			db.execSQL("INSERT INTO Compras (idproducto, nombre, descripcion, cantidad, precio, subtotal) " + "VALUES (" + idproducto + ",'" + nombre + "','" + descripcion + "'," + cantidad + "," + precio + "," + subtotal + ")");	
		}
	
		db.close();		
	}
	
	public void EliminarProducto(Context contexto, int id){

		BaseTemporalCarritoCompras tarjetaDB= new BaseTemporalCarritoCompras(contexto,DB_NAME, null, 1);
		SQLiteDatabase db = tarjetaDB.getWritableDatabase();
		if(db!=null){
			db.execSQL("DELETE From Compras WHERE idproducto = " + id);	
		}
	
		db.close();		
	}
	
	public void ModificarProducto(Context contexto, int idproducto, int cantidad,double subtotal){

		BaseTemporalCarritoCompras tarjetaDB= new BaseTemporalCarritoCompras(contexto,DB_NAME, null, 1);
		SQLiteDatabase db = tarjetaDB.getWritableDatabase();
		
		if(db!=null){
			db.execSQL("UPDATE Compras SET cantidad = " + cantidad + ", subtotal = " + subtotal + " WHERE idproducto = " + idproducto);	
		}
	
		db.close();		
	}
	
	public ArrayList<ItemCarrito> listarProductosCarrito(Context contexto){
		ArrayList<ItemCarrito> listaProductosCarrito=null;
		BaseTemporalCarritoCompras basetemporal =new BaseTemporalCarritoCompras(contexto,DB_NAME,null,1);
		SQLiteDatabase  db = basetemporal.getReadableDatabase();
		listaProductosCarrito = new ArrayList<ItemCarrito>();
		
		String sql = "SELECT * From Compras";
		Cursor cursor = db.rawQuery(sql,null);
		
		if(cursor.moveToFirst()){
			do{
				ItemCarrito item=new ItemCarrito(cursor.getInt(0),
					cursor.getString(1),
					cursor.getString(2),
					cursor.getInt(3),
					cursor.getDouble(4),
					cursor.getDouble(5));
				listaProductosCarrito.add(item);
			}while(cursor.moveToNext());
			
		}
		
		cursor.close();
		db.close();
		return listaProductosCarrito;
	}
	
	public Carrito BuscarProducto(Context contexto,int idProducto) {
		Carrito item = null;
		BaseTemporalCarritoCompras carritoDB =new BaseTemporalCarritoCompras(contexto,DB_NAME,null,1);
		SQLiteDatabase  db = carritoDB.getReadableDatabase();
		
		String [] parametrosBusqueda= new String[]{""+idProducto+""};
		String sql="Select * From Compras WHERE idproducto = ? ";
		Cursor cursor = db.rawQuery(sql, parametrosBusqueda);
		if(cursor.moveToFirst()){	
		   do{
			   item = new Carrito();
			   item.setId(cursor.getInt(0));
			}while(cursor.moveToNext());
		}
		db.close();
		return item;
	}
	
	public void LimpiarCarrito(Context contexto){

		BaseTemporalCarritoCompras tarjetaDB= new BaseTemporalCarritoCompras(contexto,DB_NAME, null, 1);
		SQLiteDatabase db = tarjetaDB.getWritableDatabase();
		if(db!=null){
			db.execSQL("DELETE From Compras");	
		}
	
		db.close();		
	}
}
	
