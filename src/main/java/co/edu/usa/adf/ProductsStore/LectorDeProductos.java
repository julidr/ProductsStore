package co.edu.usa.adf.ProductsStore;

import java.util.ArrayList;

import co.edu.usa.adf.OverClass.Controlador;

public class LectorDeProductos {
	
	Controlador ctrl= new Controlador();
	
	public void cargarProductos(){
		try {
			ctrl.cargarInformacionArchivos("C://Users//Usuario//workspace//ProductsStore//infoProductos.txt");
			ArrayList<String> infoArchivo= ctrl.getInformacionArchivos();
			ctrl.construirClase(infoArchivo.get(0), infoArchivo.get(1));
		} catch (Exception e) {
			System.out.println("Error de carga: " + e);
		}
	}
	
	public ArrayList<Object> getListaDeObjetos(){
		return ctrl.getListaDeObjetos();
	}

}
