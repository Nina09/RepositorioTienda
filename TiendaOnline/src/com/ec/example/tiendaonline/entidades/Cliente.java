package com.ec.example.tiendaonline.entidades;

public class Cliente {

  int id;
  String nombre;
  String apellido;
  String cedula;
  String telefono;
  String alias;
  
 public Cliente(int id, String nombre, String apellido, String cedula,
		String telefono, String alias) {
	super();
	this.id = id;
	this.nombre = nombre;
	this.apellido = apellido;
	this.cedula = cedula;
	this.telefono = telefono;
	this.alias = alias;
}

public Cliente() {
	super();
	// TODO Auto-generated constructor stub
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getNombre() {
	return nombre;
}

public void setNombre(String nombre) {
	this.nombre = nombre;
}

public String getApellido() {
	return apellido;
}

public void setApellido(String apellido) {
	this.apellido = apellido;
}

public String getCedula() {
	return cedula;
}

public void setCedula(String cedula) {
	this.cedula = cedula;
}

public String getTelefono() {
	return telefono;
}

public void setTelefono(String telefono) {
	this.telefono = telefono;
}

public String getAlias() {
	return alias;
}

public void setAlias(String alias) {
	this.alias = alias;
}
  
 
  
}
