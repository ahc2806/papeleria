package controlador;

import java.net.URL;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.sun.prism.impl.Disposer.Record;
import controlador.ControladorVentanas;
import modelo.DAOUsuarios;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

@SuppressWarnings("restriction")
public class ControladorUsuarios implements Initializable{

	@FXML TableView<DAOUsuarios> tvUsuarios;
	@FXML Button btnNuevo, btnGuardar, btnEditar, btnCancelar, btnRegresar, btnEliminar;
	@FXML TextField txtUsuario,txtNombre, txtApeP, txtApeM,txtTelefono, txtCorreo, txtBuscador ;
	@FXML PasswordField pfContrasena;
	@FXML CheckBox ckbVer, ckbInactivos;
	@FXML ComboBox<String> cbTipo, cbSexo;
	@FXML DatePicker dpFechaNac;
	@FXML GridPane tabla;
	@SuppressWarnings("rawtypes")
	@FXML TableColumn columnaRestaurar = new TableColumn<>();

	private ObservableList<DAOUsuarios> lista;
	private ObservableList<String>obTipos, obSexo;
	private DAOUsuarios usuario;
	private FilteredList<DAOUsuarios> listaBusqueda;
	private String email;
	String emailPattern = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@" +
  	      "[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
	Pattern pattern = Pattern.compile(emailPattern);
	private static final String estiloBoton = "-fx-background-color:-fx-shadow-highlight-color, -fx-outer-border, "
			+ "-fx-body-color, -fx-inner-border;"; 
	private static final String letraNegra= "-fx-text-fill: -fx-text-base-color;";
	private static final String estiloNormal ="-fx-background-color:transparent;";
	private static final String letraNormal= "-fx-text-fill: WHITE;";

	public ControladorUsuarios() {
		usuario =new DAOUsuarios();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//Formato de calendario
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

		txtBuscador.textProperty().addListener((observable, oldValue, newValue)->{
			//Se asigna al valor anterior
			if(!newValue.matches("[a-zA-ZñÑ0-9]{0,20}") || newValue.length()>20){
				((StringProperty) observable).setValue(oldValue);
			}else{
				//Se asigna el nuevo valor, porque si coincide con la expresión
				((StringProperty)observable).setValue(newValue);
			}
		});

		//Llenar lista de tipos de empleados
		obTipos = FXCollections.observableArrayList();
		obTipos.add("Administrador");
		obTipos.add("Trabajador");
		obTipos.add("Invitado");
		cbTipo.setItems(obTipos);
		//Llenar lista de sexos
		obSexo = FXCollections.observableArrayList();
		obSexo.add("Hombre");
		obSexo.add("Mujer");
		cbSexo.setItems(obSexo);

		//Código para ver la contraseña
		 final TextField textField = new TextField();
		    tabla.add(textField, 1,1);
		    textField.setManaged(false);
		    textField.setVisible(false);
		    textField.setPromptText("CONTRASEÑA");
		    textField.managedProperty().bind(ckbVer.selectedProperty());
		    textField.visibleProperty().bind(ckbVer.selectedProperty());
		    pfContrasena.managedProperty().bind(ckbVer.selectedProperty().not());
		    pfContrasena.visibleProperty().bind(ckbVer.selectedProperty().not());
		    textField.textProperty().bindBidirectional(pfContrasena.textProperty());
		    
		    //Estilos para los botones
		     btnNuevo.setOnMouseEntered(e -> btnNuevo.setStyle(estiloBoton)); 
			 btnNuevo.setOnMouseEntered(e -> btnNuevo.setStyle(letraNegra)); 
			 btnNuevo.setOnMouseExited(e -> btnNuevo.setStyle(letraNormal)); 
			 btnNuevo.setOnMouseExited(e -> btnNuevo.setStyle(estiloNormal));
			 
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
			 
			 btnEliminar.setOnMouseEntered(e -> btnEliminar.setStyle(estiloBoton)); 
			 btnEliminar.setOnMouseEntered(e -> btnEliminar.setStyle(letraNegra)); 
			 btnEliminar.setOnMouseExited(e -> btnEliminar.setStyle(letraNormal)); 
			 btnEliminar.setOnMouseExited(e -> btnEliminar.setStyle(estiloNormal));
			 
			 btnRegresar.setOnMouseEntered(e -> btnRegresar.setStyle(estiloBoton)); 
			 btnRegresar.setOnMouseEntered(e -> btnRegresar.setStyle(letraNegra)); 
			 btnRegresar.setOnMouseExited(e -> btnRegresar.setStyle(letraNormal)); 
			 btnRegresar.setOnMouseExited(e -> btnRegresar.setStyle(estiloNormal));

		   actualizarTabla();
			bloquear();
	}

	public void actualizarTabla(){
		tvUsuarios.getItems().clear();
		lista=usuario.consultar("select * from usuarios where estado_usuario = true");
		tvUsuarios.setItems(lista);
		tvUsuarios.refresh();
		listaBusqueda = new FilteredList<DAOUsuarios>(lista);
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
		ckbVer.setDisable(true);
		cbSexo.setDisable(true);
		cbTipo.setDisable(true);
		btnGuardar.setDisable(true);
		btnEliminar.setDisable(true);
		btnEditar.setDisable(true);
		btnCancelar.setDisable(true);
		btnNuevo.setDisable(false);
		txtBuscador.setDisable(false);
		tvUsuarios.setDisable(false);
		ckbInactivos.setDisable(false);
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
		cbSexo.setDisable(false);
		cbTipo.setDisable(false);
	}

	public void limpiar(){
		txtUsuario.clear();
		txtNombre.clear();
		txtApeP.clear();
		txtApeM.clear();
		txtCorreo.clear();
		txtTelefono.clear();
		pfContrasena.clear();
		cbTipo.getSelectionModel().select(-1);
		cbSexo.getSelectionModel().select(-1);
		ckbVer.setSelected(false);
		dpFechaNac.setValue(null);
		tvUsuarios.setDisable(false);
		txtBuscador.setDisable(false);
		txtBuscador.clear();
		ckbInactivos.setDisable(false);
	}

	@FXML public void clickTabla(){
		if(tvUsuarios.getSelectionModel().getSelectedItem()!= null){
			activar();
			usuario = tvUsuarios.getSelectionModel().getSelectedItem();
			txtUsuario.setText(usuario.getUsuario_usuario());
			txtNombre.setText(usuario.getNombre_usuario());
			txtApeP.setText(usuario.getApeP_usuario());
			txtApeM.setText(usuario.getApeM_usuario());
			txtCorreo.setText(usuario.getCorreo_usuario());
			txtTelefono.setText(usuario.getTelefono_usuario());
			pfContrasena.setText(usuario.getContrasena_usuario());
			cbTipo.getSelectionModel().select(usuario.getTipo_usuario());
			cbSexo.getSelectionModel().select(usuario.getSexo_usuario());
			dpFechaNac.setValue(usuario.getFechaNac_usuario().toLocalDate());

			btnEditar.setDisable(false);
			btnEliminar.setDisable(false);
			btnCancelar.setDisable(false);
			btnNuevo.setDisable(true);
			ckbInactivos.setDisable(true);
			txtBuscador.clear();
			txtBuscador.setDisable(true);
		}
	}

	@FXML public void clickNuevo(){
		limpiar();
		inactivosFalse();
		activar();
		btnGuardar.setDisable(false);
		btnCancelar.setDisable(false);
		btnNuevo.setDisable(true);
		txtBuscador.setDisable(true);
		tvUsuarios.setDisable(true);
		ckbInactivos.setSelected(false);
		ckbInactivos.setDisable(true);

		cbSexo.getSelectionModel().selectFirst();
		cbTipo.getSelectionModel().selectLast();
	}

	@FXML public void clickEditar(){
		try{
			if(txtUsuario.getText().trim().isEmpty() || pfContrasena.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty() ||
					txtApeP.getText().trim().isEmpty()){
				ControladorNotificaciones.warnningAlert("ADVERTENCIA", null, "ALGUNOS CAMPOS NECESARIOS ESTÁN VACÍOS.");
			}
			else{
				int lengthUsuario = txtUsuario.getText().length();
				if(lengthUsuario < 6){
					ControladorNotificaciones.warnningAlert("ADVERTENCIA", null, "EL USUARIO DEBE TENER AL MENOS 6 CARACTERES.");
				}
				else{
					int lengthContra = pfContrasena.getText().length();
					if(lengthContra < 8){
						ControladorNotificaciones.warnningAlert("ADVERTENCIA", null,"LA CONTRASEÑA DEBE TENER AL MENOS 8 CARACTERES.");
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
							usuario.setSexo_usuario(cbSexo.getSelectionModel().getSelectedItem());
							usuario.setFechaNac_usuario(Date.valueOf(dpFechaNac.getValue()));
							usuario.setTipo_usuario(cbTipo.getSelectionModel().getSelectedItem());
							if(txtTelefono.getText() == null){
								usuario.setTelefono_usuario(null);
							}else{
								usuario.setTelefono_usuario(txtTelefono.getText());
							}
							usuario.setCorreo_usuario(null);

							if(usuario.editar()){
								ControladorNotificaciones.infoAlert("INFORMACIÓN", null,  "USUARIO ACTUALIZADO CORRECTAMENTE.");
								tvUsuarios.setDisable(false);
								ckbInactivos.setDisable(false);
								actualizarTabla();
								limpiar();
								bloquear();
							}
							else{
								ControladorNotificaciones.errorAlert("ERROR", "USUARIO NO ACTUALIZADO.", "HA OCURRIDO UN ERROR. INTENTE NUEVAMENTE.");
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
								usuario.setSexo_usuario(cbSexo.getSelectionModel().getSelectedItem());
								usuario.setFechaNac_usuario(Date.valueOf(dpFechaNac.getValue()));
								usuario.setTipo_usuario(cbTipo.getSelectionModel().getSelectedItem());
								if(txtTelefono.getText() == null){
									usuario.setTelefono_usuario(null);
								}else{
									usuario.setTelefono_usuario(txtTelefono.getText());
								}
								usuario.setCorreo_usuario(txtCorreo.getText());

								if(usuario.editar()){
									ControladorNotificaciones.infoAlert("INFORMACIÓN", null, "USUARIO ACTUALIZADO CORRECTAMENTE.");
									tvUsuarios.setDisable(false);
									ckbInactivos.setDisable(false);
									actualizarTabla();
									limpiar();
									bloquear();
								}
								else{
									ControladorNotificaciones.errorAlert("ERROR", "USUARIO NO ACTUALIZADO.", "HA OCURRIDO UN ERROR. INTENTE NUEVAMENTE.");
								}
						    }
						    else{
						    	ControladorNotificaciones.warnningAlert("ADVERTENCIA",null, "INGRESA UN CORREO VÁLIDO.");
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

	@FXML public void clickCancelar(){
		limpiar();
		actualizarTabla();
		bloquear();
	}

	@FXML public void clickGuardar(){
		try{
			if(txtUsuario.getText().trim().isEmpty() || pfContrasena.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty() ||
					txtApeP.getText().trim().isEmpty()){
				ControladorNotificaciones.warnningAlert("ADVERTENCIA", null, "ALGUNOS CAMPOS NECESARIOS ESTÁN VACÍOS.");
			}
			else{
				int lengthUsuario = txtUsuario.getText().length();
				if( lengthUsuario < 6){
					ControladorNotificaciones.warnningAlert("ADVERTENCIA", null, "EL USUARIO DEBE TENER AL MENOS 6 CARACTERES.");
				}
				else{
					int lengthContra = pfContrasena.getText().length();
					if(lengthContra < 8){
						ControladorNotificaciones.warnningAlert("ADVERTENCIA", null, "LA CONTRASEÑA DEBE TENER AL MENOS 8 CARACTERES.");
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
							usuario.setSexo_usuario(cbSexo.getSelectionModel().getSelectedItem());
							usuario.setFechaNac_usuario(Date.valueOf(dpFechaNac.getValue()));
							usuario.setTipo_usuario(cbTipo.getSelectionModel().getSelectedItem());
							if(txtApeM.getText() == null){
								usuario.setTelefono_usuario(null);
							}else{
								usuario.setTelefono_usuario(txtTelefono.getText());
							}
							usuario.setCorreo_usuario(null);

							if(usuario.agregar()){
								ControladorNotificaciones.infoAlert("INFORMACIÓN", null, "USUARIO AGREGADO CORRECTAMENTE");
								tvUsuarios.setDisable(false);
								ckbInactivos.setDisable(false);
								actualizarTabla();
								limpiar();
								bloquear();
							}
							else{
								ControladorNotificaciones.errorAlert("ERROR", "USUARIO NO AGREGADO.", "HA OCURRIDO UN ERROR. INTENTE NUEVAMENTE.");
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
								usuario.setSexo_usuario(cbSexo.getSelectionModel().getSelectedItem());
								usuario.setFechaNac_usuario(Date.valueOf(dpFechaNac.getValue()));
								usuario.setTipo_usuario(cbTipo.getSelectionModel().getSelectedItem());
								if(txtApeM.getText() == null){
									usuario.setTelefono_usuario(null);
								}else{
									usuario.setTelefono_usuario(txtTelefono.getText());
								}
								usuario.setCorreo_usuario(txtCorreo.getText());

								if(usuario.agregar()){
									ControladorNotificaciones.infoAlert("INFORMACIÓN", null, "USUARIO AGREGADO CORRECTAMENTE.");
									tvUsuarios.setDisable(false);
									ckbInactivos.setDisable(false);
									actualizarTabla();
									limpiar();
									bloquear();
								}
								else{
									ControladorNotificaciones.errorAlert("ERROR", "USUARIO NO AGREGADO.", "HA OCURRIDO UN ERROR. INTENTE NUEVAMENTE.");
								}
						    }
						    else{
						    	ControladorNotificaciones.warnningAlert("ADVERTENCIA", null, "INGRESE UN CORREO VÁLIDO.");
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

	@FXML public void clickEliminar(){
		if(usuario.getId_usuario() > 0){
			usuario.eliminar();
			actualizarTabla();
		}
		else{
			ControladorNotificaciones.warnningAlert("ADVERTENCIA", null, "SELECCIONE UN USUARIO DE LA TABLA.");
		}
		limpiar();
		bloquear();
	}

	@FXML public void clickRegresar(){
		ControladorVentanas cv = ControladorVentanas.getInstancia();
		cv.cerrarAcceso();
	}

	@SuppressWarnings("unchecked")
	@FXML public void clickInactivos(){
		bloquear();
		limpiar();
		try{
			tvUsuarios.getItems().clear();
			if(ckbInactivos.isSelected()){
				//Si está seleccionado se muestran los inactivos
				lista = usuario.consultar("select * from usuarios where estado_usuario = false");
				listaBusqueda =  new FilteredList<DAOUsuarios>(lista);

				//Agregar una columna al tableView para restaurar el dato inactivo
				tvUsuarios.getColumns().add(0, columnaRestaurar);
				columnaRestaurar.setCellValueFactory(
						new Callback<TableColumn.CellDataFeatures<Record, Boolean>, ObservableValue<Boolean>>(){
							@Override
							public ObservableValue<Boolean> call(CellDataFeatures<Record, Boolean> param) {
								return new SimpleBooleanProperty(param.getValue() != null);
							}
						}
					);
				columnaRestaurar.setCellFactory(
						new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>(){
							@Override
							public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> param) {
								return new BotonActivar();
							}
						}
					);
			} //Cierra if
			else{
				//Si está desactivado se muestran los activos
				inactivosFalse();
			}
			tvUsuarios.setItems(lista); //Asignar la lista actualizada a la tabla
		}
		catch(Exception e){
			e.printStackTrace();
		}
	} //Cierra el método clickInactivos

	public class BotonActivar extends TableCell<Record, Boolean>{
		Button cellButton;
		Image imagen;
		ImageView contenedor;

		BotonActivar(){
			imagen = new Image("vista/img/restaurar.png");
			contenedor = new ImageView(imagen);
			contenedor.setFitWidth(15);
			contenedor.setFitHeight(15);
			cellButton = new Button("", contenedor);

			cellButton.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent t) {
					usuario = (DAOUsuarios) BotonActivar.this.getTableView().getItems().get(BotonActivar.this.getIndex());
					if(usuario.reactivar()){
						lista = usuario.consultar("select * from usuarios where estado_usuario = false");
						tvUsuarios.setItems(lista);
						tvUsuarios.refresh();
					}
				}
			});
		}
		@Override
		protected void updateItem(Boolean t, boolean empty){
			super.updateItem(t, empty);
			if(!empty){ //Si la fila no está vacía se muestra el botón
				setGraphic(cellButton);
			}
		}
	}

	@FXML public void textChange_busqueda(){
		if(txtBuscador.getText().trim().isEmpty()){
			tvUsuarios.refresh();
			tvUsuarios.setItems(lista);
			btnNuevo.setDisable(false);
			ckbInactivos.setDisable(false);
		}
		else{
			try{
				bloquear();
				btnNuevo.setDisable(true);
				ckbInactivos.setDisable(true);
				listaBusqueda.setPredicate(DAOUsuario->{
					if(DAOUsuario.getUsuario_usuario().toLowerCase().contains(txtBuscador.getText().toLowerCase()) ||
						DAOUsuario.getNombre_usuario().toLowerCase().contains(txtBuscador.getText().toLowerCase()) ||
						DAOUsuario.getApeP_usuario().toLowerCase().contains(txtBuscador.getText().toLowerCase()) ||
						DAOUsuario.getApeM_usuario().toLowerCase().contains(txtBuscador.getText().toLowerCase()) ||
						DAOUsuario.getTipo_usuario().toLowerCase().contains(txtBuscador.getText().toLowerCase()) ){
					return true;
				}
					else{
						return false;
					}
				});
				tvUsuarios.refresh();
				tvUsuarios.setItems(listaBusqueda);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void inactivosFalse() {
		if(columnaRestaurar.isVisible() == true){
			tvUsuarios.getColumns().remove(columnaRestaurar);
			tvUsuarios.getItems().clear();
			lista = usuario.consultar("select * from usuarios where estado_usuario = true");
			tvUsuarios.setItems(lista);
			tvUsuarios.refresh();
		}
		else {
			tvUsuarios.getItems().clear();
			lista = usuario.consultar("select * from usuarios where estado_usuario = true");
			tvUsuarios.setItems(lista);
			tvUsuarios.refresh();
		}
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
