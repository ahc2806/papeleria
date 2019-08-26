package controlador;

import modelo.DAOUsuarios;
import controlador.ControladorVentanas;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControladorVentanas {
	private static ControladorVentanas instancia;
	private Stage primaryStage, escenario2, escenario3;
	private Scene escena, escena2;
	private AnchorPane contenedorMenu, subcontenedor, subcontenedor2;

	 //Patrón Singleton: Permite asegurar que solo exista un único objeto de una clase}
	  public static ControladorVentanas getInstancia(){
	    if(instancia==null)
	      instancia=new ControladorVentanas();
	    return instancia;
	  }

	public Stage getPrimaryStage() {
		 return primaryStage;
		  }
		  //Asignar el escenario principal
	public void setPrimaryStage(Stage primaryStage) {
		 this.primaryStage = primaryStage;
		  }

	//Asignar menúprincipal
	 public void asignarMenu(String ruta, String titulo, DAOUsuarios usuario){
		 	try{
		 		primaryStage.setUserData(usuario);
				FXMLLoader parent =new FXMLLoader(getClass().getResource(ruta));
				contenedorMenu=(AnchorPane) parent.load();
				escena =new Scene(contenedorMenu);
				//escena.getStylesheets().add("../controlador/application.css");
				primaryStage.setScene(escena);
				primaryStage.getIcons().add(new Image("file:src/vista/img/icono.jpg"));
				primaryStage.setTitle(titulo);
				primaryStage.setResizable(true);
				
				primaryStage.show();
			}
		 	catch(Exception e){
				e.printStackTrace();
			}
		}

	 public void cerrarMenu(){
		 primaryStage.close();
	 }

	 public void asignarModal(String ruta, String titulo){
		 try{
			 FXMLLoader vista=new FXMLLoader(getClass().getResource(ruta));
			 subcontenedor=(AnchorPane)vista.load();
			 escena = new Scene(subcontenedor);
			 escenario2=new Stage();
			 escenario2.setScene(escena);
			 escenario2.setTitle(titulo);
			 escenario2.getIcons().add(new Image("file:src/vista/img/icono.jpg"));
			 escenario2.centerOnScreen();
			 escenario2.initModality(Modality.WINDOW_MODAL);
			 escenario2.initOwner(primaryStage);
			 escenario2.show();
		 }
		 catch(Exception e){
			 e.printStackTrace();
		}
	 }

	 public void asignarModal2(String ruta, String titulo){
		 try{
			 FXMLLoader vista=new FXMLLoader(getClass().getResource(ruta));
			 subcontenedor2=(AnchorPane)vista.load();
			 escena2 = new Scene(subcontenedor2);
			 escenario3=new Stage();
			 escenario3.setScene(escena2);
			 escenario3.setTitle(titulo);
			 escenario3.getIcons().add(new Image("file:src/vista/img/icono.jpg"));
			 escenario3.centerOnScreen();
			 escenario3.initModality(Modality.WINDOW_MODAL);
			 escenario3.initOwner(escenario2);
			 escenario3.show();
		 }
		 catch(Exception e){
			 e.printStackTrace();
		}
	 }
	 
	 public void cerrarAcceso(){
		 escenario2.close();
	 }
	 
	 public void cerrarAcceso2() {
		 escenario3.close();
	 }
	
	public void pantallaCompleta1(){
		primaryStage.setMaximized(true);
	}
	
	public void pantallaCompleta2(){
		escenario2.setMaximized(true);
	}
	
	public void pantallaCompleta3() {
		 escenario3.setMaximized(true);
	 }
	 
	public void noMaximizar() {
		escenario2.setResizable(false);
	}
	
	public void noMaximizar2() {
		escenario3.setResizable(false);
	}
	
	public void pantallaMinimizada(){
		primaryStage.setMaximized(false);
	}
}
