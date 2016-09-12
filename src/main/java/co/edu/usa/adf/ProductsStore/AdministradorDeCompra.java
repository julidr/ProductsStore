package co.edu.usa.adf.ProductsStore;

import java.util.ArrayList;
import java.util.Date;

import co.edu.usa.adf.OverClass.Controlador;

public class AdministradorDeCompra {
	
	private Controlador ctrl= new Controlador();
	
	public Compra realizarCompra(Producto producto, int cantidadComprada, Date fecha, float totalDeLaCompra){
		Compra comprita= new Compra();
		comprita.setFecha(fecha);
		comprita.setProducto(producto);
		comprita.setCantidadComprada(cantidadComprada);
		comprita.setTotalDeLaCompra(totalDeLaCompra);
		return comprita;
	}
	
	public ArrayList<Object> convertirListaDeComprasAObjetos(ArrayList<Compra> listaDeCompras){
		ArrayList<Object> listaDeObjetos= new ArrayList<>();
		for(int i=0; i<listaDeCompras.size(); i++){
			listaDeObjetos.add(listaDeCompras.get(i));
		}
		return listaDeObjetos;
	}
	
	public void imprimirCompras(ArrayList<Compra> listaDeCompras){
		ctrl.construirClase("co.edu.usa.adf.ProductsStore.Compra", convertirListaDeComprasAObjetos(listaDeCompras));
		ctrl.setFormatoDeLaFecha("yyyy/mm/dd hh:mm:ss");
		ctrl.imprimir("C://Users//Usuario//workspace//ProductsStore//listaCompras.txt");
	}

}
