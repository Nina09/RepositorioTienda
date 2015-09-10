package com.ec.example.tiendaonline.utiles;

public class Pedidos {

	int idPedidos;
	String fecha;
	double subtotal;
	double total;
	
	public Pedidos(int idPedidos, String fecha, double subtotal, double total) {
		super();
		this.idPedidos = idPedidos;
		this.fecha = fecha;
		this.subtotal = subtotal;
		this.total = total;
	}
	
	public Pedidos() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getIdPedidos() {
		return idPedidos;
	}
	public void setIdPedidos(int idPedidos) {
		this.idPedidos = idPedidos;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
	
}
