package com.ec.example.tiendaonline.utiles;

public class ItemCarritoaPedido {
	String descripcion;
	int cantidadd;
	double total;
	double precio;
	
	public ItemCarritoaPedido(String descripcion, int cantidadd,  double total) {
		super();
		this.descripcion = descripcion;
		this.cantidadd = cantidadd;
		this.total= total;
	}
	
	public ItemCarritoaPedido() {
		super();
		// TODO Auto-generated constructor stub
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
	public double getTotal(){
		return total;
	}
	public void setTotal(double total){
		this.total=total;
	}
}
