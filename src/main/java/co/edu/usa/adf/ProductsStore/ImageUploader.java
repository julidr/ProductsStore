package co.edu.usa.adf.ProductsStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

public class ImageUploader implements Receiver, SucceededListener{
	public File file;
	private String path="";
	private String nombreImagen="";
	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		// TODO Auto-generated method stub
		FileOutputStream fos=null;
		try {
			System.out.println(filename);
			nombreImagen=filename;
			file=new File("C://Users//Usuario//workspace//ProductsStore//src//main//webapp//VAADIN//themes//mytheme//Imagenes//"+filename);
			System.out.println(file);
			//path=file.getCanonicalPath();
			System.out.println(fos);
			fos=new FileOutputStream(file);
		} catch (Exception e) {
			System.out.println("Error: "+e);
		}
		return fos;
	}
	@Override
	public void uploadSucceeded(SucceededEvent event) {
		// TODO Auto-generated method stub
		Notification.show("Carga exitosa!",Notification.TYPE_TRAY_NOTIFICATION);
		
	}
	
	public String getNombreImagen(){
		return nombreImagen;
	}
	public String getPath(){
		return path;
	}

}
