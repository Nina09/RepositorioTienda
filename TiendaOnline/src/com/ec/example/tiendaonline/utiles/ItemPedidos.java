package com.ec.example.tiendaonline.utiles;

public class ItemPedidos {
    int idPedido;
	String fecha;
	double total;
	public ItemPedidos(int idPedido, String fecha, double total) {
		super();
		this.idPedido = idPedido;
		this.fecha = fecha;
		this.total = total;
	}
	public ItemPedidos() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getIdPedido() {
		return idPedido;
	}
	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
	
	
}
