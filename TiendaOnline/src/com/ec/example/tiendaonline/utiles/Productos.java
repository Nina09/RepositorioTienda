package com.ec.example.tiendaonline.utiles;

import android.graphics.Bitmap;

public class Productos {
	
	int idProducto;
	String nombre;
	String descripcion;
	double precio;
	String URLImagen;
	public Bitmap image;
	
	
	public Productos(int idProducto, String nombre, String descripcion,
			double precio, String uRLImagen, Bitmap image) {
		super();
		this.idProducto = idProducto;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		URLImagen = uRLImagen;
		this.image = image;
	}
	

	public Productos() {
		super();
		// TODO Auto-generated constructor stub
	}


	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	
	public String getURLImagen() {
		return URLImagen;
	}


	public void setURLImagen(String uRLImagen) {
		URLImagen = uRLImagen;
	}


	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}
	
	

}
