

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import co.edu.usa.adf.ProductsStore.LectorDeProductos;

public class LectorDeProductosTest {

	public LectorDeProductos ldp; 
	
	@Before
	public void setUp() throws Exception {
		ldp= new LectorDeProductos();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void debecargarLosProductos() {
		ldp.cargarProductos();
	}
	
	@Test
	public void debeMostrarLosProductos() {
		ldp.cargarProductos();
		ArrayList<Object> listaDeObjetos= ldp.getListaDeObjetos();
		System.out.println("Impresion de objetos");
		System.out.println(listaDeObjetos.size());
		for(int i=0; i<listaDeObjetos.size(); i++){
			System.out.println(listaDeObjetos.get(i).toString());
		}
	}

}
