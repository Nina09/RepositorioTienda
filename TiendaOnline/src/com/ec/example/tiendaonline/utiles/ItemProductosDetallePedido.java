package com.ec.example.tiendaonline.utiles;

public class ItemProductosDetallePedido {
	
	String descripcion;
	int cantidad;
	double precio;
	double subtotal;
	public ItemProductosDetallePedido(String descripcion, int cantidad,
			double precio, double subtotal) {
		super();
		this.descripcion = descripcion;
		this.cantidad = cantidad;
		this.precio = precio;
		this.subtotal = subtotal;
	}
	public ItemProductosDetallePedido() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	
	

}
