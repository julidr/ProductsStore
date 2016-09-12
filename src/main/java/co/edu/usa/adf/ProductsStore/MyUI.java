package co.edu.usa.adf.ProductsStore;

import java.awt.image.ImageFilter;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Container.Filterable;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Item;
import com.vaadin.data.util.filter.Like;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.event.Transferable;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.Table.TableDragMode;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Tree.TreeDragMode;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("mytheme")
@StyleSheet({ "http://fonts.googleapis.com/css?family=Sigmar+One",
		"http://fonts.googleapis.com/css?family=Bree+Serif" })
public class MyUI extends UI {

	ControladorDeTrashStore ctrlUI = new ControladorDeTrashStore();
	public Table tablaDeProductos = new Table();
	final VerticalLayout layoutOne = new VerticalLayout();
	final FormLayout form=new FormLayout();
	final TextField filtrador= new TextField();
	ArrayList<Integer> valoresSeleccionados= new ArrayList<>();
	ArrayList<Item>items=new ArrayList<>();
	Window subwindow=new Window("Factura de compra");
	Window nuevoProduct=new Window("Crear un nuevo producto");
	boolean escuchar=true;
	String textoDeFiltro;
	int cont=0;
	
	@Override
	protected void init(VaadinRequest vaadinRequest) {

		// Aspectos Basicos de Carga
		ctrlUI.cargarProductos();
		ctrlUI.convertirListaProductos();
		tablaDeProductos.addStyleName("tabla");

		Label titulo = new Label("Bienvenido a la TrashStore");
		titulo.addStyleName("titulo");
		filtrador.setInputPrompt("Buscar Producto");
		Button botonAgregarProducto = new Button("Agregar Producto Nuevo!");
		botonAgregarProducto.addClickListener(e -> {
			agregarProducto();
		});
		Button botonImprimirCompras = new Button("Imprimir Compras");
		botonImprimirCompras.addClickListener(e -> {
			System.out.println("Imprimiendo");
			imprimirCompras();
		});
		layoutOne.addComponents(titulo, filtrador,botonAgregarProducto,botonImprimirCompras);
		cargarTablaDeProductos();
		tablaDeProductos.sort(new Object[]{"Nombre"},new boolean[]{true});
		//label y crear 2 botones

		Button botonAceptar = new Button("Aceptar");
		layoutOne.addComponents(tablaDeProductos,botonAceptar);
		filtrarPorNombre();
		
		botonAceptar.addClickListener(e -> {
			filtrador.clear();
			recibirSeleccion(tablaDeProductos.getValue());
		});
		
		layoutOne.setComponentAlignment(tablaDeProductos, Alignment.MIDDLE_CENTER);
		layoutOne.setMargin(true);
		layoutOne.setSpacing(true);

		layoutOne.addStyleName("layoutOne");

		setContent(layoutOne);
	}
	
	public void cargarTablaDeProductos(){
		
		tablaDeProductos.addContainerProperty("Imagen", Layout.class, null);
		tablaDeProductos.addContainerProperty("Nombre", String.class, null);
		tablaDeProductos.setColumnWidth("Nombre", 300);
		tablaDeProductos.addContainerProperty("Precio", Float.class,null);
		tablaDeProductos.setColumnAlignment("Precio", Align.CENTER);
		tablaDeProductos.setColumnWidth("Precio", 200);
		tablaDeProductos.addContainerProperty("Cantidad Disponible", Integer.class, null);
		tablaDeProductos.setColumnAlignment("Cantidad Disponible", Align.CENTER);
		tablaDeProductos.addContainerProperty("Cantidad a comprar", TextField.class, null);
		for (Producto product : ctrlUI.getListaDeProductos()) {
			Resource imagen= new ThemeResource(product.getImagen());
			Image img= new Image(null, imagen);
			img.setWidth("100px");
			img.setHeight("100px");
			HorizontalLayout li= new HorizontalLayout();
			li.addComponent(img);
			tablaDeProductos.addItem(new Object[] { li, product.getNombre(), product.getPrecio(),
					product.getCantidadDisponible(), new TextField() }, cont);
			items.add(tablaDeProductos.getItem(cont));
			cont++;
		}
		tablaDeProductos.setSelectable(true);
		tablaDeProductos.setImmediate(true);
		tablaDeProductos.setMultiSelect(true);
		if(tablaDeProductos.size()>=5){
			tablaDeProductos.setPageLength(3);
		}
		else{
			tablaDeProductos.setPageLength(0);
		}
	}
	
	public void filtrarPorNombre(){
			filtrador.addTextChangeListener(new TextChangeListener() {

	            @Override
	            public void textChange(TextChangeEvent event) {
	                Filterable filtro= (Filterable) (tablaDeProductos.getContainerDataSource());
	                filtro.removeAllContainerFilters();
	                tablaDeProductos.sort(new Object[]{"Nombre"},new boolean[]{true});
	                textoDeFiltro = event.getText();
	                if (textoDeFiltro.length() > 0) {
	                	Like busqueda= new Like("Nombre", "%"+textoDeFiltro +"%");
	                	busqueda.setCaseSensitive(false);
	                    filtro.addContainerFilter(busqueda);
	                    if(filtro.size()>0 && filtro.size()<=4){
	                    	tablaDeProductos.setPageLength(0);
	                    }
	                    else{
	                    	tablaDeProductos.setPageLength(3);
	                    }
	                }
	                else{
	                	tablaDeProductos.setPageLength(3);
	                }
	            }
	        });
	}
	
	public void recibirSeleccion(Object seleccion) {
		ArrayList<Producto>temp=new ArrayList<>();
		System.out.println("Imprimiendo Seleccion");
		System.out.println(seleccion);
		String[] arregloSeleccion= adquirirArregloDeValores(seleccion);
		boolean compracomit=false;
		int cantidadComprada=0;
		float total=0;
		Producto producto= new Producto();
        Date fecha= new Date();
		for(int i=0; i<arregloSeleccion.length; i++){
			int indiceTabla=0;
			try {
				indiceTabla= Integer.parseInt(arregloSeleccion[i].trim());
			} catch (Exception e) {
				// TODO: handle exception
				Notification.show("Ningun producto se ha seleccionado!!",Notification.TYPE_ERROR_MESSAGE);compracomit=false;break;
			}
			
			System.out.println(indiceTabla);
			Item item=items.get(indiceTabla);
			int cantidadDisp=(Integer)item.getItemProperty("Cantidad Disponible").getValue();
			TextField tx=(TextField)item.getItemProperty("Cantidad a comprar").getValue();
			if(tx.isEmpty()==true){
				compracomit=false;
				Notification.show("Algunos productos seleccionados no tienen cantidad a comprar", Notification.TYPE_ERROR_MESSAGE);
				break;
			}else if(Integer.parseInt(tx.getValue())>cantidadDisp){
				compracomit=false;
				Notification.show("Algunos productos no tienen la cantidad Suficiente",Notification.TYPE_WARNING_MESSAGE);
				break;
			}else{
				compracomit=true;
				producto= ctrlUI.obtenerProducto(indiceTabla);
				cantidadComprada= Integer.parseInt(tx.getValue());
				item.getItemProperty("Cantidad Disponible").setValue(cantidadDisp-Integer.parseInt(tx.getValue()));
				System.out.println("Modified In table, new value");
				total=total+(Float)item.getItemProperty("Precio").getValue()*Float.parseFloat(tx.getValue());
		        ctrlUI.realizarCompra(producto, cantidadComprada, fecha, total);
		        temp.add(producto);
		        ctrlUI.imprimirListaDeCompras();
		        tx.clear();
			}
		}if(compracomit){
			subwindow.center();
			subwindow.setWidth("350");
			addWindow(subwindow);
			VerticalLayout subContent = new VerticalLayout();
	        subContent.setMargin(true);
	        Button a=new Button("Aceptar");
	        subwindow.setContent(subContent);
	        subContent.addComponent(new Label("Fecha de compra: "+new Date().toLocaleString()+"\n"));
	        subContent.addComponent(new Label("Resumen de productos\n"));
	        for (Producto produ : temp) {
				subContent.addComponent(new Label("    Nombre: "+produ.getNombre()));
			}
	        subContent.addComponent(new Label("\nTotal a Pagar: "+total));
	        subContent.addComponent(a);
	        a.addClickListener(e->{
	        	temp.clear();
	        	subwindow.close();
	        });	
		}
	}
	
	public String[] adquirirArregloDeValores(Object seleccion){
		String seleccionString= seleccion.toString().substring(1,seleccion.toString().length()-1);
		return seleccionString.split(",");
	}
	
	public void agregarProducto(){
		FormLayout form=new FormLayout();
		nuevoProduct.setHeight("390");
		nuevoProduct.setWidth("690");
		nuevoProduct.center();
		TextField tf1= new TextField("Nombre");
		tf1.setIcon(FontAwesome.AMAZON);
		tf1.addValidator(new NullValidator("Este campo es obligatorio", false));
		form.addComponent(tf1);
		TextField tf2=new TextField("Precio");
		tf2.setIcon(FontAwesome.BITCOIN);
		tf2.setValue("0");
		tf2.addValidator(new NullValidator("Este campo es obligatorio", false));
		form.addComponent(tf2);
		TextField tf3=new TextField("Cantidad Disponible");
		tf3.setValue("0");
		tf3.setIcon(FontAwesome.BOOKMARK);
		form.addComponent(tf3);
		//Botoooooon
		Button guardar=new Button("Agregar");
		//Carga de imagen
		ImageUploader reciever=new ImageUploader();
		Upload upload=new Upload("Selecciona la imagen: ",reciever);
		upload.setIcon(FontAwesome.IMAGE);
		upload.setWidth("468");
		upload.addSucceededListener(reciever);
		upload.setButtonCaption("Cargar");
		form.addComponent(upload);
		form.addComponent(guardar);
		guardar.addClickListener(e -> {
			System.out.println("Boton guardar");
			Producto ne=new Producto();
			try {
				tf1.getValue();
				Float.parseFloat(tf2.getValue());
				Integer.parseInt(tf3.getValue());
			} catch (Exception e2) {
				// TODO: handle exception
				Notification.show("Valor numerico incorrecto, o campos vacios!");
			}
			ne.setNombre(tf1.getValue());
			ne.setPrecio(Float.parseFloat(tf2.getValue()));
			ne.setCantidadDisponible(Integer.parseInt(tf3.getValue()));
			ne.setImagen("Imagenes//"+reciever.getNombreImagen());
			System.out.println(ne);
			ctrlUI.getListaDeProductos().add(ne);
			agregarColumnas(ne);
			System.out.println("Lista");
			for (Producto com : ctrlUI.getListaDeProductos()) {
				System.out.println(com.toString());
			}
		});
		addWindow(nuevoProduct);
		VerticalLayout vt=new VerticalLayout();
		vt.addComponent(form);
		vt.setComponentAlignment(form, Alignment.MIDDLE_CENTER);
		vt.setMargin(true);
		vt.setSpacing(true);
		nuevoProduct.setContent(vt);
		//Button acction
	}
	
	public void agregarColumnas(Producto product){
		Resource imagen= new ThemeResource(product.getImagen());
		Image img= new Image(null, imagen);
		img.setWidth("100px");
		img.setHeight("100px");
		HorizontalLayout li= new HorizontalLayout();
		li.addComponent(img);
		tablaDeProductos.addItem(new Object[] { li, product.getNombre(), product.getPrecio(),
				product.getCantidadDisponible(), new TextField() }, cont);
		items.add(tablaDeProductos.getItem(cont));
		cont++;
	}
	
	public void imprimirCompras(){
		ctrlUI.imprimirCompras();
	}
	
	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}
}