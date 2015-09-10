package com.ec.example.tiendaonline.utiles;

public class Carrito {

	 int id;
	    String nombre;
		String descripcion;
		int cantidadd;
		double precio;
		double total;
		
		public Carrito(int id, String nombre, String descripcion,
				int cantidadd, double precio, double total) {
			super();
			this.id = id;
			this.nombre = nombre;
			this.descripcion = descripcion;
			this.cantidadd = cantidadd;
			this.precio = precio;
			this.total = total;
		}
		public Carrito() {
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
		public String getDescripcion() {
			return descripcion;
		}
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
		public int getCantidadd() {
			return cantidadd;
		}
		public void setCantidadd(int cantidadd) {
			this.cantidadd = cantidadd;
		}
		public double getPrecio() {
			return precio;
		}
		public void setPrecio(double precio) {
			this.precio = precio;
		}
		public double getTotal() {
			return total;
		}
		public void setTotal(double total) {
			this.total = total;
		}
		
		
			
	}
