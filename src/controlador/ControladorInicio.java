package controlador;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import controlador.ControladorVentanas;
import modelo.DAOUsuarios;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.StageStyle;

public class ControladorInicio implements Initializable{

	private ControladorVentanas instancia;
	@FXML TextField txfHora, txfUsuario, txfTipo;
	@FXML Button btnUsuarios, btnPerfil, btnVentas, btnProveedores, btnInventario, btnReportes, btnAgregarComp,
									btnConfiguracion, btnCerrarSesion;
	@FXML ImageView perfil;
	private static final String estiloBoton = "-fx-background-color:-fx-shadow-highlight-color, -fx-outer-border, "
			+ "-fx-body-color, -fx-inner-border;";
	private static final String letraNegra= "-fx-text-fill: -fx-text-base-color;";
	private static final String estiloNormal ="-fx-background-color:transparent;";
	private static final String letraNormal= "-fx-text-fill: WHITE;";
	private Date date = new Date();
	private DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
	private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	public ControladorInicio(){
		instancia=ControladorVentanas.getInstancia();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//Recuperar información del usuario logueado
		DAOUsuarios usuario=(DAOUsuarios)instancia.getPrimaryStage().getUserData();
		txfHora.setText(dateFormat.format(date) + "  - " + hourFormat.format(date));
		txfUsuario.setText(usuario.getNombre_usuario().toUpperCase() + " " + usuario.getApeP_usuario().toUpperCase());
		txfTipo.setText(usuario.getTipo_usuario().toUpperCase());
		Image imagen = new Image(usuario.getFoto_usuario(), 50, 50, true, true, true);
		perfil.setImage(imagen);

		switch(usuario.getTipo_usuario()){

			case "Administrador":
						btnPerfil.setDisable(false);
						btnUsuarios.setDisable(false);
						btnInventario.setDisable(false);
						btnVentas.setDisable(false);
						btnProveedores.setDisable(false);
						btnReportes.setDisable(false);
						btnConfiguracion.setDisable(false);
						btnAgregarComp.setDisable(false);
						break;

			case "Trabajador":
						btnPerfil.setDisable(false);
						btnUsuarios.setDisable(true);
						btnInventario.setDisable(false);
						btnVentas.setDisable(false);
						btnProveedores.setDisable(false);
						btnReportes.setDisable(false);
						btnAgregarComp.setDisable(false);
						btnConfiguracion.setDisable(true);
						break;

			case "Invitado":
						btnPerfil.setDisable(false);
						btnUsuarios.setDisable(true);
						btnVentas.setDisable(false);
						btnProveedores.setDisable(true);
						btnInventario.setDisable(true);
						btnReportes.setDisable(true);
						btnAgregarComp.setDisable(true);
						btnConfiguracion.setDisable(true);
						break;
		}

		//Estilos para los botones
		btnPerfil.setOnMouseEntered(e -> btnPerfil.setStyle(estiloBoton));
		 btnPerfil.setOnMouseEntered(e -> btnPerfil.setStyle(letraNegra));
		 btnPerfil.setOnMouseExited(e -> btnPerfil.setStyle(letraNormal));
		 btnPerfil.setOnMouseExited(e -> btnPerfil.setStyle(estiloNormal));

	    btnUsuarios.setOnMouseEntered(e -> btnUsuarios.setStyle(estiloBoton));
		 btnUsuarios.setOnMouseEntered(e -> btnUsuarios.setStyle(letraNegra));
		 btnUsuarios.setOnMouseExited(e -> btnUsuarios.setStyle(letraNormal));
		 btnUsuarios.setOnMouseExited(e -> btnUsuarios.setStyle(estiloNormal));

		 btnInventario.setOnMouseEntered(e -> btnInventario.setStyle(estiloBoton));
		 btnInventario.setOnMouseEntered(e -> btnInventario.setStyle(letraNegra));
		 btnInventario.setOnMouseExited(e -> btnInventario.setStyle(letraNormal));
		 btnInventario.setOnMouseExited(e -> btnInventario.setStyle(estiloNormal));

		 btnVentas.setOnMouseEntered(e -> btnVentas.setStyle(estiloBoton));
		 btnVentas.setOnMouseEntered(e -> btnVentas.setStyle(letraNegra));
		 btnVentas.setOnMouseExited(e -> btnVentas.setStyle(letraNormal));
		 btnVentas.setOnMouseExited(e -> btnVentas.setStyle(estiloNormal));

		 btnProveedores.setOnMouseEntered(e -> btnProveedores.setStyle(estiloBoton));
		 btnProveedores.setOnMouseEntered(e -> btnProveedores.setStyle(letraNegra));
		 btnProveedores.setOnMouseExited(e -> btnProveedores.setStyle(letraNormal));
		 btnProveedores.setOnMouseExited(e -> btnProveedores.setStyle(estiloNormal));

		 btnReportes.setOnMouseEntered(e -> btnReportes.setStyle(estiloBoton));
		 btnReportes.setOnMouseEntered(e -> btnReportes.setStyle(letraNegra));
		 btnReportes.setOnMouseExited(e -> btnReportes.setStyle(letraNormal));
		 btnReportes.setOnMouseExited(e -> btnReportes.setStyle(estiloNormal));
		 
		 btnAgregarComp.setOnMouseEntered(e -> btnAgregarComp.setStyle(estiloBoton));
		 btnAgregarComp.setOnMouseEntered(e -> btnAgregarComp.setStyle(letraNegra));
		 btnAgregarComp.setOnMouseExited(e -> btnAgregarComp.setStyle(letraNormal));
		 btnAgregarComp.setOnMouseExited(e -> btnAgregarComp.setStyle(estiloNormal));

		 btnConfiguracion.setOnMouseEntered(e -> btnConfiguracion.setStyle(estiloBoton));
		 btnConfiguracion.setOnMouseEntered(e -> btnConfiguracion.setStyle(letraNegra));
		 btnConfiguracion.setOnMouseExited(e -> btnConfiguracion.setStyle(letraNormal));
		 btnConfiguracion.setOnMouseExited(e -> btnConfiguracion.setStyle(estiloNormal));

		 btnConfiguracion.setOnMouseEntered(e -> btnConfiguracion.setStyle(estiloBoton));
		 btnConfiguracion.setOnMouseEntered(e -> btnConfiguracion.setStyle(letraNegra));
		 btnConfiguracion.setOnMouseExited(e -> btnConfiguracion.setStyle(letraNormal));
		 btnConfiguracion.setOnMouseExited(e -> btnConfiguracion.setStyle(estiloNormal));

		 btnCerrarSesion.setOnMouseEntered(e -> btnCerrarSesion.setStyle(estiloBoton));
		 btnCerrarSesion.setOnMouseEntered(e -> btnCerrarSesion.setStyle(letraNegra));
		 btnCerrarSesion.setOnMouseExited(e -> btnCerrarSesion.setStyle(letraNormal));
		 btnCerrarSesion.setOnMouseExited(e -> btnCerrarSesion.setStyle(estiloNormal));

		 //Activar tecla Enter en los botones
		 btnPerfil.setOnKeyPressed(new EventHandler<KeyEvent>()
		    {
		     @Override
		     public void handle(KeyEvent ke) {
		      if (ke.getCode().equals(KeyCode.ENTER)){
		    	  instancia.asignarModal("../vista/perfil.fxml", "MODIFICAR PERFIL");
		    	  instancia.noMaximizar();
		      }
		     }
		    });
		 
		 btnUsuarios.setOnKeyPressed(new EventHandler<KeyEvent>()
		    {
		     @Override
		     public void handle(KeyEvent ke) {
		      if (ke.getCode().equals(KeyCode.ENTER)){
		    	  instancia.asignarModal("../vista/usuarios.fxml", "REGISTRO DE USUARIOS");
		    	  instancia.pantallaCompleta2();
		      }
		     }
		    });
		 
		 btnInventario.setOnKeyPressed(new EventHandler<KeyEvent>()
		    {
		     @Override
		     public void handle(KeyEvent ke) {
		      if (ke.getCode().equals(KeyCode.ENTER)){
		    	  instancia.asignarModal("../vista/inventario.fxml", "REGISTRO DE PRODUCTOS");
		  		  instancia.pantallaCompleta2();
		      }
		     }
		    });
		 
		 btnVentas.setOnKeyPressed(new EventHandler<KeyEvent>()
		    {
		     @Override
		     public void handle(KeyEvent ke) {
		      if (ke.getCode().equals(KeyCode.ENTER)){
		  		instancia.asignarModal("../vista/ventas.fxml", "REGISTRO DE VENTAS");
				instancia.pantallaCompleta2();
		      }
		     }
		    });
		 
		 btnProveedores.setOnKeyPressed(new EventHandler<KeyEvent>()
		    {
		     @Override
		     public void handle(KeyEvent ke) {
		      if (ke.getCode().equals(KeyCode.ENTER)){
					instancia.asignarModal("../vista/proveedores.fxml", "REGISTRO DE PROVEEDORES");
					instancia.pantallaCompleta2();
		      }
		     }
		    });
		 
		 btnReportes.setOnKeyPressed(new EventHandler<KeyEvent>()
		    {
		     @Override
		     public void handle(KeyEvent ke) {
		      if (ke.getCode().equals(KeyCode.ENTER)){
		  		instancia.asignarModal("../vista/reportes.fxml", "REPORTES DE VENTAS / SALIDAS");
				instancia.pantallaCompleta2();
		      }
		     }
		    });
		 
		 btnAgregarComp.setOnKeyPressed(new EventHandler<KeyEvent>()
		    {
		     @Override
		     public void handle(KeyEvent ke) {
		      if (ke.getCode().equals(KeyCode.ENTER)){
		  		instancia.asignarModal2("../vista/companias.fxml", "REGISTRO DE COMPAÑÍAS");
				instancia.pantallaCompleta3();
		      }
		     }
		    });

		 btnCerrarSesion.setOnKeyPressed(new EventHandler<KeyEvent>()
		    {
		     @Override
		     public void handle(KeyEvent ke) {
		      if (ke.getCode().equals(KeyCode.ENTER)){
		  		Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("CONFIRMACIÓN");
				alert.setHeaderText(null);
				alert.setContentText("¿ESTÁ SEGURO QUE DESEA CERRAR SESIÓN?");
				alert.initStyle(StageStyle.UTILITY);
				java.awt.Toolkit.getDefaultToolkit().beep();

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					instancia.cerrarMenu();
					instancia.cerrarAcceso();
					ControladorNotificaciones.infoAlert("INFORMACIÓN",null,"¡SESIÓN CERRADA!");
					instancia.asignarModal("../vista/login.fxml", "LOGIN");
					instancia.pantallaMinimizada();
					instancia.noMaximizar();
				}
				else {
					alert.close();
				}
		      }
		    }
		 });
	}

	@FXML public void clickPerfil(){
		instancia.asignarModal("../vista/perfil.fxml", "MODIFICAR PERFIL");
		instancia.noMaximizar();
	}
	
	@FXML public void clickUsuarios(){
		instancia.asignarModal("../vista/usuarios.fxml", "REGISTRO DE USUARIOS");
		instancia.pantallaCompleta2();
	}
	
	@FXML public void clickInventario(){
		instancia.asignarModal("../vista/inventario.fxml", "REGISTRO DE PRODUCTOS");
		instancia.pantallaCompleta2();
	}
	
	@FXML public void clickVentas(){
		instancia.asignarModal("../vista/ventas.fxml", "REGISTRO DE VENTAS");
		instancia.pantallaCompleta2();
	}
	
	@FXML public void clickProveedores(){
		instancia.asignarModal("../vista/proveedores.fxml", "REGISTRO DE PROVEEDORES");
		instancia.pantallaCompleta2();
	}
	
	@FXML public void clickCompanias(){
		instancia.asignarModal2("../vista/companias.fxml", "REGISTRO DE COMPAÑÍAS");
		instancia.pantallaCompleta3();
	}
	
	@FXML public void clickReportes(){
		instancia.asignarModal("../vista/reportes.fxml", "REPORTES DE VENTAS / SALIDAS");
		instancia.pantallaCompleta2();
	}
	
	@FXML public void clickCerrarSesion(){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("CONFIRMACIÓN");
		alert.setHeaderText(null);
		alert.setContentText("¿ESTÁ SEGURO QUE DESEA CERRAR SESIÓN?");
		alert.initStyle(StageStyle.UTILITY);
		java.awt.Toolkit.getDefaultToolkit().beep();

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			instancia.cerrarMenu();
			instancia.cerrarAcceso();
			ControladorNotificaciones.infoAlert("INFORMACIÓN",null,"¡SESIÓN CERRADA!");
			instancia.asignarModal("../vista/login.fxml", "LOGIN");
			instancia.pantallaMinimizada();
			instancia.noMaximizar();
		}
		else {
			alert.close();
		}
	}
}
