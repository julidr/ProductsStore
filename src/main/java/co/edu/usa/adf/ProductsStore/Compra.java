package co.edu.usa.adf.ProductsStore;

import java.util.Date;

import co.edu.usa.adf.OverClass.Campo;
import co.edu.usa.adf.OverClass.Tomate;

@Tomate
public class Compra {
	
	@Campo(ancho=20) private Date fecha;
	@Campo(ancho=50) private Producto producto;
	@Campo(ancho=10) private int cantidadComprada;
	@Campo(ancho=15) private float totalDeLaCompra;
	
	public Compra(){
		
	}
	
	public Date getFecha() {
		return fecha;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public Producto getProducto() {
		return producto;
	}
	
	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public int getCantidadComprada() {
		return cantidadComprada;
	}

	public void setCantidadComprada(int cantidadComprada) {
		this.cantidadComprada = cantidadComprada;
	}

	public float getTotalDeLaCompra() {
		return totalDeLaCompra;
	}

	public void setTotalDeLaCompra(float totalDeLaCompra) {
		this.totalDeLaCompra = totalDeLaCompra;
	}

	@Override
	public String toString() {
		return "Compra [fecha=" + fecha + ", producto=" + producto + ", cantidadComprada=" + cantidadComprada
				+ ", totalDeLaCompra=" + totalDeLaCompra + "]";
	}
}
