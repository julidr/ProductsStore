package co.edu.usa.adf.ProductsStore;

import co.edu.usa.adf.OverClass.Campo;
import co.edu.usa.adf.OverClass.Tomate;

@Tomate
public class Producto {
	
	@Campo(ancho=40) private String nombre;
	@Campo(ancho=20) private float precio;
	@Campo(ancho=20) private int cantidadDisponible;
	@Campo(ancho=60) private String imagen;
	
	public Producto(){
		
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public int getCantidadDisponible() {
		return cantidadDisponible;
	}

	public void setCantidadDisponible(int cantidadDisponible) {
		this.cantidadDisponible = cantidadDisponible;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	@Override
	public String toString() {
		return "Producto [nombre=" + nombre + ", precio=" + precio + ", cantidadDisponible=" + cantidadDisponible
				+ ", imagen=" + imagen + "]";
	}
}
