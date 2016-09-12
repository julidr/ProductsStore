package co.edu.usa.adf.ProductsStore;

import java.util.ArrayList;
import java.util.Date;

public class ControladorDeTrashStore {
	
	private LectorDeProductos ldp= new LectorDeProductos();
	private AdministradorDeCompra adc= new AdministradorDeCompra();
	private ArrayList<Producto> listaDeProductos= new ArrayList<>();
	private ArrayList<Compra> listaDeCompras= new ArrayList<>();
	
	public ControladorDeTrashStore(){
	}
	
	public void cargarProductos(){
		ldp.cargarProductos();
	}
	
	public ArrayList<Producto> getListaDeProductos(){
		return listaDeProductos;
	}
	
	public void convertirListaProductos(){
		for (int i=0; i<ldp.getListaDeObjetos().size(); i++){
			listaDeProductos.add((Producto) ldp.getListaDeObjetos().get(i));
		}
	}
	
	public void realizarCompra(Producto producto, int cantidadComprada, Date fecha, float totalDeLaCompra){
		listaDeCompras.add(adc.realizarCompra(producto, cantidadComprada, fecha, totalDeLaCompra));
	}
	
	public void imprimirCompras(){
		adc.imprimirCompras(listaDeCompras);
	}
	
	public Producto obtenerProducto(int indice){
		return listaDeProductos.get(indice);
	}
	
	public void imprimirListaDeCompras(){
		for(int i=0; i<listaDeCompras.size(); i++){
			System.out.println(listaDeCompras.get(i).toString());
		}
	}

}
