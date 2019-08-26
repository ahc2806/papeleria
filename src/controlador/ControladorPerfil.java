package controlador;

import java.net.URL;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import modelo.DAOUsuarios;

public class ControladorPerfil implements Initializable{

	@FXML Button btnGuardar, btnEditar, btnCancelar, btnRegresar, btnCambiar;
	@FXML TextField txtUsuario,txtNombre, txtApeP, txtApeM,txtTelefono, txtCorreo;
	@FXML PasswordField pfContrasena;
	@FXML CheckBox ckbVer;
	@FXML DatePicker dpFechaNac;
	@FXML GridPane tabla;
	@FXML Label tipo;
	@FXML ImageView perfil;

	private DAOUsuarios usuario;
	private ControladorVentanas instancia;
	private String email;
	String emailPattern = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@" +
  	      "[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
	Pattern pattern = Pattern.compile(emailPattern);
	private static final String estiloBoton = "-fx-background-color:-fx-shadow-highlight-color, -fx-outer-border, "
			+ "-fx-body-color, -fx-inner-border;"; 
	private static final String letraNegra= "-fx-text-fill: -fx-text-base-color;";
	private static final String estiloNormal ="-fx-background-color:transparent;";
	private static final String letraNormal= "-fx-text-fill: WHITE;";
	private int id_usuario;

	public ControladorPerfil() {
		usuario =new DAOUsuarios();
		instancia=ControladorVentanas.getInstancia();
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

		txtNombre.textProperty().addListener((observable, oldValue, newValue)->{
			//Se asigna al valor anterior
			if(!newValue.matches("[a-zA-ZñÑ ]{0,20}") || newValue.length()>50){
				((StringProperty) observable).setValue(oldValue);
			}else{
				//Se asigna el nuevo valor, porque si coincide con la expresión
				((StringProperty)observable).setValue(newValue);
			}
		});

		txtApeP.textProperty().addListener((observable, oldValue, newValue)->{
			//Se asigna al valor anterior
			if(!newValue.matches("[a-zA-ZñÑ ]{0,20}") || newValue.length()>30){
				((StringProperty) observable).setValue(oldValue);
			}else{
				//Se asigna el nuevo valor, porque si coincide con la expresión
				((StringProperty)observable).setValue(newValue);
			}
		});

		txtApeM.textProperty().addListener((observable, oldValue, newValue)->{
			//Se asigna al valor anterior
			if(!newValue.matches("[a-zA-ZñÑ ]{0,20}") || newValue.length()>30){
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

		txtTelefono.textProperty().addListener((observable, oldValue, newValue)->{
			//Se asigna al valor anterior
			if(!newValue.matches("[0-9]{0,15}") || newValue.length()>15){
				((StringProperty) observable).setValue(oldValue);
			}else{
				//Se asigna el nuevo valor, porque si coincide con la expresión
				((StringProperty)observable).setValue(newValue);
			}
		});

		txtCorreo.textProperty().addListener((observable, oldValue, newValue)->{
			//Se asigna al valor anterior
			if(!newValue.matches("[a-zA-ZñÑ._@0-9-]{0,30}") || newValue.length()>50){
				((StringProperty) observable).setValue(oldValue);
			}else{
				//Se asigna el nuevo valor, porque si coincide con la expresión
				((StringProperty)observable).setValue(newValue);
			}
		});
		
		//Código para ver la contraseña
		 final TextField textField = new TextField();
		    tabla.add(textField, 1,1);
		    textField.setManaged(false);
		    textField.setVisible(false);
		    textField.setPromptText("CONTRASEÑA");
		    textField.setPrefHeight(40);
		    textField.setPrefWidth(306);
		    textField.managedProperty().bind(ckbVer.selectedProperty());
		    textField.visibleProperty().bind(ckbVer.selectedProperty());
		    pfContrasena.managedProperty().bind(ckbVer.selectedProperty().not());
		    pfContrasena.visibleProperty().bind(ckbVer.selectedProperty().not());
		    textField.textProperty().bindBidirectional(pfContrasena.textProperty());
		    
		  //Estilos para los botones
		     btnEditar.setOnMouseEntered(e -> btnEditar.setStyle(estiloBoton)); 
			 btnEditar.setOnMouseEntered(e -> btnEditar.setStyle(letraNegra)); 
			 btnEditar.setOnMouseExited(e -> btnEditar.setStyle(letraNormal)); 
			 btnEditar.setOnMouseExited(e -> btnEditar.setStyle(estiloNormal));
			 
			 btnCancelar.setOnMouseEntered(e -> btnCancelar.setStyle(estiloBoton)); 
			 btnCancelar.setOnMouseEntered(e -> btnCancelar.setStyle(letraNegra)); 
			 btnCancelar.setOnMouseExited(e -> btnCancelar.setStyle(letraNormal)); 
			 btnCancelar.setOnMouseExited(e -> btnCancelar.setStyle(estiloNormal));
			 
			 btnGuardar.setOnMouseEntered(e -> btnGuardar.setStyle(estiloBoton)); 
			 btnGuardar.setOnMouseEntered(e -> btnGuardar.setStyle(letraNegra)); 
			 btnGuardar.setOnMouseExited(e -> btnGuardar.setStyle(letraNormal)); 
			 btnGuardar.setOnMouseExited(e -> btnGuardar.setStyle(estiloNormal));
			 
			btnRegresar.setOnMouseEntered(e -> btnRegresar.setStyle(estiloBoton)); 
			btnRegresar.setOnMouseEntered(e -> btnRegresar.setStyle(letraNegra)); 
			btnRegresar.setOnMouseExited(e -> btnRegresar.setStyle(letraNormal)); 
			btnRegresar.setOnMouseExited(e -> btnRegresar.setStyle(estiloNormal));

			actualizarTabla();
			bloquear();
			dpFechaNac.setDayCellFactory(dayCellFactory);
	}
	
	public void actualizarTabla() {
		usuario = (DAOUsuarios)instancia.getPrimaryStage().getUserData();
		id_usuario = usuario.getId_usuario();
		txtUsuario.setText(usuario.getUsuario_usuario());
		pfContrasena.setText(usuario.getContrasena_usuario());
		txtNombre.setText(usuario.getNombre_usuario());
		txtApeP.setText(usuario.getApeP_usuario());
		if(usuario.getApeM_usuario() == null) {
			txtApeM.setText("");
		}else {
			txtApeM.setText(usuario.getApeM_usuario());
		}
		if(usuario.getTelefono_usuario() == null) {
			txtTelefono.setText("");
		}else {
			txtTelefono.setText(usuario.getTelefono_usuario());
		}
		if(usuario.getCorreo_usuario() == null) {
			txtCorreo.setText("");
		}else {
			txtCorreo.setText(usuario.getCorreo_usuario());
		}
		dpFechaNac.setValue(usuario.getFechaNac_usuario().toLocalDate());
		tipo.setText(usuario.getTipo_usuario());
		Image imagen = new Image(usuario.getFoto_usuario());
		perfil.setImage(imagen);
	}
	
	public void activar(){
		txtUsuario.setDisable(false);
		txtNombre.setDisable(false);
		txtApeP.setDisable(false);
		txtApeM.setDisable(false);
		txtTelefono.setDisable(false);
		txtCorreo.setDisable(false);
		dpFechaNac.setDisable(false);
		pfContrasena.setDisable(false);
		ckbVer.setDisable(false);
		btnCancelar.setDisable(false);
		btnGuardar.setDisable(false);
	}
	
	public void bloquear(){
		txtUsuario.setDisable(true);
		txtNombre.setDisable(true);
		txtApeP.setDisable(true);
		txtApeM.setDisable(true);
		txtTelefono.setDisable(true);
		txtCorreo.setDisable(true);
		dpFechaNac.setDisable(true);
		pfContrasena.setDisable(true);
		btnGuardar.setDisable(true);
		btnCancelar.setDisable(true);
		ckbVer.setDisable(true);
	}
	
	@FXML public void clickEditar() {
		activar();
		btnEditar.setDisable(true);
		btnCambiar.setDisable(true);
	}
	
	@FXML public void clickCancelar(){
		actualizarTabla();
		bloquear();
		btnEditar.setDisable(false);
		btnCambiar.setDisable(false);
		ckbVer.setSelected(false);
	}
	
	@FXML public void clickGuardar() {
		try{
			if(txtUsuario.getText().trim().isEmpty() || pfContrasena.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty() ||
					txtApeP.getText().trim().isEmpty()){
					ControladorNotificaciones.warnningAlert("ADVERTENCIA",null, "ALGUNOS CAMPOS NECESARIOS ESTÁN VACÍOS.");
			}
			else{
				int lengthUsuario = txtUsuario.getText().length();
				if(lengthUsuario < 6){
					ControladorNotificaciones.warnningAlert("ADVERTENCIA",null, "EL USUARIO DEBE TENER AL MENOS 6 CARACTERES.");
				}
				else{
					int lengthContra = pfContrasena.getText().length();
					if(lengthContra < 8){
						ControladorNotificaciones.warnningAlert("ADVERTENCIA",null, "LA CONTRASEÑA DEBE TENER AL MENOS 8 CARACTERES.");
					}
					else{
						if (txtCorreo.getText().isEmpty()) {
							usuario.setUsuario_usuario(txtUsuario.getText());
							usuario.setContrasena_usuario(pfContrasena.getText());
							usuario.setNombre_usuario(txtNombre.getText());
							usuario.setApeP_usuario(txtApeP.getText());
							if(txtApeM.getText() == null){
								usuario.setApeM_usuario(null);
							}else{
								usuario.setApeM_usuario(txtApeM.getText());
							}
							usuario.setFechaNac_usuario(Date.valueOf(dpFechaNac.getValue()));
							if(txtTelefono.getText() == null){
								usuario.setTelefono_usuario(null);
							}else{
								usuario.setTelefono_usuario(txtTelefono.getText());
							}
							usuario.setCorreo_usuario(null);
							usuario.setId_usuario(id_usuario);

							if(usuario.editar2()){
								ControladorNotificaciones.infoAlert("INFORMACIÓN",null, "PERFIL ACTUALIZADO CORRECTAMENTE.");
								btnEditar.setDisable(false);
								btnCambiar.setDisable(false);
								ckbVer.setSelected(false);
								actualizarTabla();
								bloquear();
							}
							else{
								ControladorNotificaciones.errorAlert("ERROR","PERFIL NO ACTUALIZADO.", "HA OCURRIDO UN ERROR. INTENTE NUEVAMENTE.");
							}
						}
						else{
							email = txtCorreo.getText();
							Matcher matcher = pattern.matcher(email);
						    if (matcher.matches()) {
						    	usuario.setUsuario_usuario(txtUsuario.getText());
								usuario.setContrasena_usuario(pfContrasena.getText());
								usuario.setNombre_usuario(txtNombre.getText());
								usuario.setApeP_usuario(txtApeP.getText());
								if(txtApeM.getText() == null){
									usuario.setApeM_usuario(null);
								}else{
									usuario.setApeM_usuario(txtApeM.getText());
								}
								usuario.setFechaNac_usuario(Date.valueOf(dpFechaNac.getValue()));
								if(txtTelefono.getText() == null){
									usuario.setTelefono_usuario(null);
								}else{
									usuario.setTelefono_usuario(txtTelefono.getText());
								}
								usuario.setCorreo_usuario(txtCorreo.getText());
								usuario.setId_usuario(id_usuario);

								if(usuario.editar2()){
									ControladorNotificaciones.infoAlert("INFORMACIÓN",null,"PERFIL ACTUALIZADO CORRECTAMENTE.");
									btnEditar.setDisable(false);
									btnCambiar.setDisable(false);
									ckbVer.setSelected(false);
									actualizarTabla();
									bloquear();
								}
								else{
									ControladorNotificaciones.errorAlert("ERROR","PERFIL NO ACTUALIZADO.", "HA OCURRIDO UN ERROR. INTENTE NUEVAMENTE.");
								}
						    }
						    else{
						    	ControladorNotificaciones.warnningAlert("ADVERTENCIA",null, "INGRESE UN CORREO VÁLIDO.");
						    }
						}
					}
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@FXML public void clickRegresar(){
		ControladorVentanas cv = ControladorVentanas.getInstancia();
		cv.cerrarAcceso();
	}
	
	@FXML public void clickCambiar() {
		instancia.asignarModal2("../vista/avatares.fxml", "FOTO DE PERFIL");
		instancia.noMaximizar2();
	}
	
	Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell() {
	    @Override
	    public void updateItem(LocalDate item, boolean empty) {

	        super.updateItem(item, empty);
	        
	        this.setDisable(false);
	        this.setBackground(null);
	        this.setTextFill(Color.BLACK);

	        // deshabilitar las fechas futuras
	        if (item.isAfter(LocalDate.now())) {
	            this.setDisable(true);
	        }
	        
	        // fines de semana en color verde
	        DayOfWeek dayweek = item.getDayOfWeek();
	        if (dayweek == DayOfWeek.SATURDAY || dayweek == DayOfWeek.SUNDAY) {
	            this.setTextFill(Color.RED);
	        }
	    }
	};
}
