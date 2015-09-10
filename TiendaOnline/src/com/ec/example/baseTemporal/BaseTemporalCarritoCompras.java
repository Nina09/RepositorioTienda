package com.ec.example.baseTemporal;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
public class BaseTemporalCarritoCompras extends SQLiteOpenHelper{

	 String sqlCreateCarrito = "CREATE TABLE Compras (idproducto INTEGER, nombre TEXT, descripcion TEXT, cantidad INTEGER, precio Double, subtotal Double)";
		  
	

  public BaseTemporalCarritoCompras(Context context, String name, CursorFactory factory,int version) {
			super(context, name, factory, version);
	}


@Override
public void onCreate(SQLiteDatabase db) {
	 db.execSQL(sqlCreateCarrito);
}

@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// TODO Auto-generated method stub
db.execSQL("DROP TABLE IF EXISTS Compras");
db.execSQL(sqlCreateCarrito);
}
}
