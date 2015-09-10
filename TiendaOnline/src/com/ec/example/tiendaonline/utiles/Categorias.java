package com.ec.example.tiendaonline.utiles;

public class Categorias {
	
	int idCategoria;
	String nombre;
	String descripcion;
	
	public Categorias(int idCategoria, String nombre, String descripcion) {
		super();
		this.idCategoria = idCategoria;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}
	
	public Categorias() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
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
	
	

}
