package controlador;

import java.net.URL;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import modelo.DAOUsuarios;

public class ControladorRegistro implements Initializable{
	
	@FXML Button btnRegistrar, btnCancelar, btnRegresar;
	@FXML TextField txtUsuario,txtNombre, txtApeP, txtApeM,txtTelefono, txtCorreo;
	@FXML PasswordField pfContrasena;
	@FXML ComboBox<String> cbSexo;
	@FXML DatePicker dpFechaNac;

	private ControladorVentanas instancia;
	private DAOUsuarios usuario;
	private ObservableList<String> obSexo;
	private String email;
	String emailPattern = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@" +
  	      "[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
	Pattern pattern = Pattern.compile(emailPattern);
	private static final String estiloBoton = "-fx-background-color:-fx-shadow-highlight-color, -fx-outer-border, "
			+ "-fx-body-color, -fx-inner-border;"; 
	private static final String letraNegra= "-fx-text-fill: -fx-text-base-color;";
	private static final String estiloNormal ="-fx-background-color:transparent;";
	private static final String letraNormal= "-fx-text-fill: WHITE;";
	
	public ControladorRegistro() {
		usuario = new DAOUsuarios();
		instancia=ControladorVentanas.getInstancia();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		dpFechaNac.setDayCellFactory(dayCellFactory);
		
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
		
		//Llenar lista de sexos
				obSexo = FXCollections.observableArrayList();
				obSexo.add("Hombre");
				obSexo.add("Mujer");
				cbSexo.setItems(obSexo);
				
				 //Estilos para los botones
			     btnRegistrar.setOnMouseEntered(e -> btnRegistrar.setStyle(estiloBoton)); 
				 btnRegistrar.setOnMouseEntered(e -> btnRegistrar.setStyle(letraNegra)); 
				 btnRegistrar.setOnMouseExited(e -> btnRegistrar.setStyle(letraNormal)); 
				 btnRegistrar.setOnMouseExited(e -> btnRegistrar.setStyle(estiloNormal));
				 
				 btnCancelar.setOnMouseEntered(e -> btnCancelar.setStyle(estiloBoton)); 
				 btnCancelar.setOnMouseEntered(e -> btnCancelar.setStyle(letraNegra)); 
				 btnCancelar.setOnMouseExited(e -> btnCancelar.setStyle(letraNormal)); 
				 btnCancelar.setOnMouseExited(e -> btnCancelar.setStyle(estiloNormal));
				 
				 btnRegresar.setOnMouseEntered(e -> btnRegresar.setStyle(estiloBoton)); 
				 btnRegresar.setOnMouseEntered(e -> btnRegresar.setStyle(letraNegra)); 
				 btnRegresar.setOnMouseExited(e -> btnRegresar.setStyle(letraNormal)); 
				 btnRegresar.setOnMouseExited(e -> btnRegresar.setStyle(estiloNormal));

	}
	
	public void limpiar(){
		txtUsuario.clear();
		txtNombre.clear();
		txtApeP.clear();
		txtApeM.clear();
		txtCorreo.clear();
		txtTelefono.clear();
		pfContrasena.clear();
		cbSexo.getSelectionModel().select(-1);
		dpFechaNac.setValue(null);
	}
	
	@FXML public void clickRegistrar() {
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
							usuario.setSexo_usuario(cbSexo.getSelectionModel().getSelectedItem());
							usuario.setCorreo_usuario(null);

							if(usuario.agregar2()){
								ControladorNotificaciones.infoAlert("INFORMACIÓN",null, "REGISTRO EXITOSO.");
								limpiar();
								ControladorVentanas cv = ControladorVentanas.getInstancia();
								cv.cerrarMenu();
								cv.cerrarAcceso();
								ControladorNotificaciones.infoAlert("INFORMACIÓN",null, "INICIE SESIÓN.");
								instancia.asignarModal("../vista/login.fxml", "INICIAR SESIÓN");
								instancia.pantallaMinimizada();
								instancia.noMaximizar();
							}
							else{
								ControladorNotificaciones.errorAlert("INFORMACIÓN","USUARIO NO REGISTRADO.", "HA OCURRIDO UN ERROR. INTENTE NUEVAMENTE.");
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
								usuario.setSexo_usuario(cbSexo.getSelectionModel().getSelectedItem());
								usuario.setCorreo_usuario(txtCorreo.getText());

								if(usuario.agregar2()){
									ControladorNotificaciones.infoAlert("INFORMACIÓN",null, "REGISTRO EXITOSO.");
									limpiar();
									ControladorVentanas cv = ControladorVentanas.getInstancia();
									cv.cerrarMenu();
									cv.cerrarAcceso();
									ControladorNotificaciones.infoAlert("INFORMACIÓN",null, "INICIE SESIÓN.");
									instancia.asignarModal("../vista/login.fxml", "INCIAR SESIÓN.");
									instancia.pantallaMinimizada();
								}
								else{
									ControladorNotificaciones.errorAlert("INFORMACIÓN","USUARIO NO REGISTRADO.", "HA OCURRIDO UN ERROR. INTENTE NUEVAMENTE.");
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
	
	@FXML public void clickCancelar() {
		limpiar();
	}
	
	@FXML public void clickRegresar() {
		limpiar();
		ControladorVentanas cv = ControladorVentanas.getInstancia();
		cv.cerrarAcceso();
		instancia.asignarModal("../vista/login.fxml", "INICIAR SESIÓN");
		instancia.pantallaMinimizada();
		instancia.noMaximizar();
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
