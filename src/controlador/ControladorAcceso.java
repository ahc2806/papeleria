package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import controlador.ControladorVentanas;
import modelo.DAOUsuarios;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

public class ControladorAcceso  implements Initializable{
	@FXML TextField txtUsuario;
	@FXML PasswordField pfContrasena;
	@FXML Button btnIniciarSesion, btnRegistrar, btnOlvCon;
	@FXML GridPane tabla;
	@FXML CheckBox ckbVer;

	private DAOUsuarios usuario;
	private ControladorVentanas cv;
	private int intentos= 0;
	int a=5;
	private static final String estiloBoton = "-fx-background-color:-fx-shadow-highlight-color, -fx-outer-border, "
																														+ "-fx-body-color, -fx-inner-border;"; 
	private static final String letraNegra= "-fx-text-fill: -fx-text-base-color;";
	private static final String estiloNormal ="-fx-background-color:black;";
	private static final String letraNormal= "-fx-text-fill: WHITE;";
	

	public ControladorAcceso(){
		usuario=new DAOUsuarios();
		cv=ControladorVentanas.getInstancia();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		txtUsuario.textProperty().addListener((observable, oldValue, newValue)->{
			//Se asigna al valor anterior
			if(!newValue.matches("[a-zA-ZñÑ0-9]{0,20}") || newValue.length()>20){
				((StringProperty) observable).setValue(oldValue);
			}else{
				//Se asigna el nuevo valor, porque si coincide con la expresión
				((StringProperty)observable).setValue(newValue);
			}
		});

		pfContrasena.textProperty().addListener((observable, oldValue, newValue)->{
			//Se asigna al valor anterior
			if(!newValue.matches("[a-zA-ZñÑ0-9]{0,20}") || newValue.length()>20){
				((StringProperty) observable).setValue(oldValue);
			}else{
				//Se asigna el nuevo valor, porque si coincide con la expresión
				((StringProperty)observable).setValue(newValue);
			}
		});
		
		txtUsuario.setOnKeyPressed(new EventHandler<KeyEvent>() 
	    { 
	     @Override 
	     public void handle(KeyEvent ke) { 
	      if (ke.getCode().equals(KeyCode.ENTER)){ 
	    	  IniciarSesion();  
	      } 
	     } 
	    }); 
		
		pfContrasena.setOnKeyPressed(new EventHandler<KeyEvent>() 
	    { 
	     @Override 
	     public void handle(KeyEvent ke) { 
	      if (ke.getCode().equals(KeyCode.ENTER)){ 
	    	  IniciarSesion();
	      } 
	     } 
	    }); 

		btnIniciarSesion.setOnKeyPressed(new EventHandler<KeyEvent>() 
	    { 
	     @Override 
	     public void handle(KeyEvent ke) { 
	      if (ke.getCode().equals(KeyCode.ENTER)){ 
	    	  IniciarSesion();
	      } 
	     } 
	    }); 

		ckbVer.setOnKeyPressed(new EventHandler<KeyEvent>() 
	    { 
	     @Override 
	     public void handle(KeyEvent ke) { 
	      if (ke.getCode().equals(KeyCode.ENTER)){ 
	    	  ckbVer.setSelected(true);
	      } 
	     } 
	    }); 
		
		 btnIniciarSesion.setOnMouseEntered(e -> btnIniciarSesion.setStyle(estiloBoton)); 
		 btnIniciarSesion.setOnMouseEntered(e -> btnIniciarSesion.setStyle(letraNegra)); 
		 btnIniciarSesion.setOnMouseExited(e -> btnIniciarSesion.setStyle(letraNormal)); 
		 btnIniciarSesion.setOnMouseExited(e -> btnIniciarSesion.setStyle(estiloNormal)); 
		 
		 //Ver contraseña
		 final TextField textField = new TextField();
		 tabla.add(textField, 0,1);
		 textField.setPromptText("CONTRASEÑA");
		 textField.setManaged(false);
		 textField.setVisible(false);
		 textField.managedProperty().bind(ckbVer.selectedProperty());
		 textField.visibleProperty().bind(ckbVer.selectedProperty());
		 pfContrasena.managedProperty().bind(ckbVer.selectedProperty().not());
		 pfContrasena.visibleProperty().bind(ckbVer.selectedProperty().not());
		 textField.textProperty().bindBidirectional(pfContrasena.textProperty());
	}

	@FXML public void clickIniciarSesion(){
		IniciarSesion();
	}
	
	public void IniciarSesion(){
		try{
			if(txtUsuario.getText().trim().isEmpty() || pfContrasena.getText().trim().isEmpty()){
				ControladorNotificaciones.warnningAlert("ADVERTENCIA", null, "EXISTEN CAMPOS VACÍOS");
			}
			else{
				usuario.setUsuario_usuario(txtUsuario.getText());
				usuario.setContrasena_usuario(pfContrasena.getText());
				DAOUsuarios temp= usuario.validarDatos();
				if(temp!= null){//Valida si el usuario está registrado
					//Se cierra la ventana de inicio de sesión
					cv.cerrarAcceso();
					cv.asignarMenu("../vista/inicio.fxml", "INICIO", temp);
					cv.pantallaCompleta1();
				}
				else{//Si el usuario no está registrado
					intentos+=1;
					if(intentos ==1){
				        ControladorNotificaciones.warnningAlert("ADVERTENCIA", null, "USUARIO Y/O CONTRASEÑA INCORRETO(S)");
					}
					else if ( intentos == 2 ) {
						ControladorNotificaciones.warnningAlert("ADVERTENCIA", null, "USUARIO Y/O CONTRASEÑA INCORRETO(S)");
					}
					else if ( intentos == 3 ) {
						ControladorNotificaciones.errorAlert("ERROR", "ERROR - VALIDAR USUARIO", "HAS INTRODUCIDO UN USUARIO INCORRECTO 3 VECES.\n"
																										+ "EL PROGRAMA SE CERRARÁ AUTOMÁTICAMENTE.");
						intentos = 0;
				        cv.cerrarAcceso();
					}
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@FXML public void clickRegistrar() {
		cv.cerrarAcceso();
		cv.asignarModal("../vista/registro.fxml", "REGISTRO -NUEVO USUARIO-");
		cv.noMaximizar();
	}
	
	@FXML public void clickOlvidasteContrasena() {
		ControladorNotificaciones.infoAlert("INFORMACIÓN", "POR EL MOMENTO NO ESTÁ DISPONIBLE, DISCULPE LAS MOLESTIAS.",
																				"CONTACTE AL -ADMINISTRADOR- PARA PODER REESTABLECER SU CONTRASEÑA.\n¡GRACIAS!");
	}
}
