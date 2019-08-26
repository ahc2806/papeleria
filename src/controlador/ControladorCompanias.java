package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.sun.prism.impl.Disposer.Record;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import modelo.DAOCompanias;
import modelo.DAOSepomex;

@SuppressWarnings("restriction")
public class ControladorCompanias implements Initializable{

	@FXML TableView<DAOCompanias> tvCompanias;
	@FXML Button btnNuevo, btnGuardar, btnEditar, btnCancelar, btnRegresar, btnEliminar, btnValidarCP;
	@FXML TextField txtDireccion, txtNombre, txtTelefono, txtCorreo, txtBuscador, txtCp;
	@FXML CheckBox ckbInactivos;
	@FXML ComboBox<String> cbMunicipios, cbEstados, cbColonias;
	@SuppressWarnings("rawtypes")
	@FXML TableColumn columnaRestaurar = new TableColumn<>();
	
	private ObservableList<DAOCompanias> lista;
	private DAOCompanias compania;
	private DAOSepomex sepomex;
	private FilteredList<DAOCompanias> listaBusqueda;
	private String email;
	String emailPattern = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@" +
  	      "[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
	Pattern pattern = Pattern.compile(emailPattern);
	private static final String estiloBoton = "-fx-background-color:-fx-shadow-highlight-color, -fx-outer-border, "
			+ "-fx-body-color, -fx-inner-border;"; 
	private static final String letraNegra= "-fx-text-fill: -fx-text-base-color;";
	private static final String estiloNormal ="-fx-background-color:transparent;";
	private static final String letraNormal= "-fx-text-fill: WHITE;";
	private boolean validar = false;
	
	public ControladorCompanias() {
		compania = new DAOCompanias();
		sepomex = new DAOSepomex();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//Restricciones
			txtNombre.textProperty().addListener((observable, oldValue, newValue)->{
				//Se asigna al valor anterior
				if(!newValue.matches("[a-zA-ZñÑáÁéÉíÍóÓúÚ ]{0,30}") || newValue.length()>30){
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
				if(!newValue.matches("[a-zA-ZñÑ._@0-9-]{0,40}") || newValue.length()>40){
					((StringProperty) observable).setValue(oldValue);
				}else{
					//Se asigna el nuevo valor, porque si coincide con la expresión
					((StringProperty)observable).setValue(newValue);
				}
			});
				
			txtDireccion.textProperty().addListener((observable, oldValue, newValue)->{
				//Se asigna al valor anterior
				if(!newValue.matches("[a-zA-ZñÑáÁéÉíÍóÓúÚ 0-9.-]{0,50}") || newValue.length()>50){
					((StringProperty) observable).setValue(oldValue);
				}else{
					//Se asigna el nuevo valor, porque si coincide con la expresión
					((StringProperty)observable).setValue(newValue);
				}
			});
				
			txtCp.textProperty().addListener((observable, oldValue, newValue)->{
				//Se asigna al valor anterior
				if(!newValue.matches("[0-9]{0,5}") || newValue.length()>5){
					((StringProperty) observable).setValue(oldValue);
				}else{
					//Se asigna el nuevo valor, porque si coincide con la expresión
					((StringProperty)observable).setValue(newValue);
				}
			});
				
			txtBuscador.textProperty().addListener((observable, oldValue, newValue)->{
				//Se asigna al valor anterior
				if(!newValue.matches("[a-zA-ZñÑáÁéÉíÍóÓúÚ 0-9.-]{0,20}") || newValue.length()>20){
					((StringProperty) observable).setValue(oldValue);
				}else{
					//Se asigna el nuevo valor, porque si coincide con la expresión
					((StringProperty)observable).setValue(newValue);
				}
			});
				
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
		tvCompanias.getItems().clear();
		lista = compania.consultar("select * from companias where estado_compania = true");
		tvCompanias.setItems(lista);
		tvCompanias.refresh();
		listaBusqueda = new FilteredList<DAOCompanias>(lista);
	}
	
	public void activar(){
		txtDireccion.setDisable(false);
		txtNombre.setDisable(false);
		txtTelefono.setDisable(false);
		txtCorreo.setDisable(false);
		txtCp.setDisable(false);
		btnValidarCP.setDisable(false);
	}
	
	public void limpiar(){
		txtNombre.clear();
		txtTelefono.clear();
		txtCorreo.clear();
		txtDireccion.clear();
		txtCp.clear();
		cbEstados.getItems().clear();
		cbEstados.getSelectionModel().select(-1);
		cbMunicipios.getItems().clear();
		cbMunicipios.getSelectionModel().select(-1);
		cbColonias.getItems().clear();
		cbColonias.getSelectionModel().select(-1);
		tvCompanias.setDisable(false);
		txtBuscador.setDisable(false);
		txtBuscador.clear();
		ckbInactivos.setDisable(false);
		validar = false;
	}
	
	public void bloquear(){
		txtNombre.setDisable(true);
		txtTelefono.setDisable(true);
		txtCorreo.setDisable(true);
		txtDireccion.setDisable(true);
		txtCp.setDisable(true);
		btnValidarCP.setDisable(true);
		cbEstados.setDisable(true);
		cbMunicipios.setDisable(true);
		cbColonias.setDisable(true);
		btnGuardar.setDisable(true);
		btnEliminar.setDisable(true);
		btnEditar.setDisable(true);
		btnCancelar.setDisable(true);
		btnNuevo.setDisable(false);
		txtBuscador.setDisable(false);
		tvCompanias.setDisable(false);
		ckbInactivos.setDisable(false);
	}
	
	public void inactivosFalse() {
		if(columnaRestaurar.isVisible() == true){
			tvCompanias.getColumns().remove(columnaRestaurar);
			tvCompanias.getItems().clear();
			lista = compania.consultar("select * from companias where estado_compania = true");
			tvCompanias.setItems(lista);
			tvCompanias.refresh();
		}
		else {
			tvCompanias.getItems().clear();
			lista = compania.consultar("select * from companias where estado_compania = true");
			tvCompanias.setItems(lista);
			tvCompanias.refresh();
		}
	}
	
	@FXML public void clickValidarCP() {
		sepomex.setCodigo_postal(txtCp.getText());
		DAOSepomex temp = sepomex.validarCP();
		if(temp!=null) { //Valida si el CP existe
			cbEstados.setDisable(false);
			cbEstados.getItems().clear();
			cbEstados.setItems(sepomex.consultarEstado());
			cbEstados.getSelectionModel().select(0);
			cbEstados.setDisable(true);
			cbMunicipios.setDisable(false);
			cbMunicipios.getItems().clear();
			cbMunicipios.setItems(sepomex.consultarMunicipio());
			cbMunicipios.getSelectionModel().select(0);
			cbMunicipios.setDisable(true);
			cbColonias.setDisable(false);
			cbColonias.getItems().clear();
			cbColonias.setItems(sepomex.consultarColonias());
			cbColonias.getSelectionModel().select(0);
			validar = true;
		}
		else {
			ControladorNotificaciones.warnningAlert("ADVERTENCIA", null, "EL -CÓDIGO POSTAL- NO EXISTE. INTENTE NUEVAMENTE.");
			cbEstados.getItems().clear();
			cbEstados.getSelectionModel().select(-1);
			cbMunicipios.getItems().clear();
			cbMunicipios.getSelectionModel().select(-1);
			cbColonias.getItems().clear();
			cbColonias.getSelectionModel().select(-1);
			validar = false;
		}
	}
	
	@FXML public void clickTabla(){
		if(tvCompanias.getSelectionModel().getSelectedItem()!= null){
			activar();
			compania = tvCompanias.getSelectionModel().getSelectedItem();
			txtNombre.setText(compania.getNombre_compania());
			txtTelefono.setText(compania.getTelefono_compania());
			txtCorreo.setText(compania.getCorreo_compania());
			txtDireccion.setText(compania.getDireccion_compania());
			txtCp.setText(compania.getCp_compania());
			cbEstados.getSelectionModel().select(compania.getEdo_compania());
			cbMunicipios.getSelectionModel().select(compania.getMpio_compania());
			cbColonias.getSelectionModel().select(compania.getCol_compania());
			validar = true;

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
		tvCompanias.setDisable(true);
		ckbInactivos.setSelected(false);
		ckbInactivos.setDisable(true);
	}
	
	@FXML public void clickEditar(){
		try{
			if(txtNombre.getText().trim().isEmpty() || txtDireccion.getText().trim().isEmpty() ||txtCp.getText().trim().isEmpty()){
				ControladorNotificaciones.warnningAlert("ADVERTENCIA", null, "ALGUNOS CAMPOS NECESARIOS ESTÁN VACÍOS.");
			}
			else {
				if(validar) {
					if (txtCorreo.getText().isEmpty()) {
						compania.setNombre_compania(txtNombre.getText());
						if(txtTelefono.getText() == null){
							compania.setTelefono_compania(null);
						}else{
							compania.setTelefono_compania(txtTelefono.getText());
						}
						compania.setCorreo_compania(null);
						compania.setDireccion_compania(txtDireccion.getText());
						compania.setCp_compania(txtCp.getText());
						compania.setEdo_compania(cbEstados.getSelectionModel().getSelectedItem());
						compania.setMpio_compania(cbMunicipios.getSelectionModel().getSelectedItem());
						compania.setCol_compania(cbColonias.getSelectionModel().getSelectedItem());
						
						if(compania.editar()){
							ControladorNotificaciones.infoAlert("INFORMACIÓN", null, "COMPAÑÍA ACTUALIZADA CORRECTAMENTE.");
							tvCompanias.setDisable(false);
							ckbInactivos.setDisable(false);
							actualizarTabla();
							limpiar();
							bloquear();
						}
						else{
							ControladorNotificaciones.errorAlert("ERROR", "COMPAÑÍA NO ACTUALIZADA.", "HA OCURRIDO UN ERROR. INTENTE NUEVAMENTE.");
						}
					}
	
					else {
						email = txtCorreo.getText();
						Matcher matcher = pattern.matcher(email);
					    if (matcher.matches()) {
							compania.setNombre_compania(txtNombre.getText());
							if(txtTelefono.getText() == null){
								compania.setTelefono_compania(null);
							}else{
								compania.setTelefono_compania(txtTelefono.getText());
							}
							compania.setCorreo_compania(txtCorreo.getText());
							compania.setDireccion_compania(txtDireccion.getText());
							compania.setCp_compania(txtCp.getText());
							compania.setEdo_compania(cbEstados.getSelectionModel().getSelectedItem());
							compania.setMpio_compania(cbMunicipios.getSelectionModel().getSelectedItem());
							compania.setCol_compania(cbColonias.getSelectionModel().getSelectedItem());
							
							if(compania.editar()){
								ControladorNotificaciones.infoAlert("INFORMACIÓN", null, "COMPAÑÍA ACTUALIZADA CORRECTAMENTE.");
								tvCompanias.setDisable(false);
								ckbInactivos.setDisable(false);
								actualizarTabla();
								limpiar();
								bloquear();
							}
							else{
								ControladorNotificaciones.errorAlert("ERROR", "COMPAÑÍA NO ACTUALIZADA.",  "HA OCURRIDO UN ERROR. INTENTE NUEVAMENTE.");
							}
					    }
					    else{
					    	ControladorNotificaciones.warnningAlert("ADVERTENCIA", null, "INGRESE UN CORREO VÁLIDO.");
					    }
					}
				}
				else {
					ControladorNotificaciones.warnningAlert("ADVERTENCIA", null, "NO SE HA VALIDADO EL -CÓDIGO POSTAL-");
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@FXML public void clickGuardar(){
		try{
			if(txtNombre.getText().trim().isEmpty() || txtDireccion.getText().trim().isEmpty() ||txtCp.getText().trim().isEmpty()){
				ControladorNotificaciones.warnningAlert("ADVERTENCIA", null, "ALGUNOS CAMPOS NECESARIOS ESTÁN VACÍOS.");
			}
			else {
				if(validar) {
					if (txtCorreo.getText().isEmpty()) {
						compania.setNombre_compania(txtNombre.getText());
						if(txtTelefono.getText() == null){
							compania.setTelefono_compania(null);
						}else{
							compania.setTelefono_compania(txtTelefono.getText());
						}
						compania.setCorreo_compania(null);
						compania.setDireccion_compania(txtDireccion.getText());
						compania.setCp_compania(txtCp.getText());
						compania.setEdo_compania(cbEstados.getSelectionModel().getSelectedItem());
						compania.setMpio_compania(cbMunicipios.getSelectionModel().getSelectedItem());
						compania.setCol_compania(cbColonias.getSelectionModel().getSelectedItem());
						
						if(compania.agregar()){
							ControladorNotificaciones.infoAlert("INFORMACIÓN", null, "COMPAÑÍA AGREGADA CORRECTAMENTE.");
							tvCompanias.setDisable(false);
							ckbInactivos.setDisable(false);
							actualizarTabla();
							limpiar();
							bloquear();
						}
						else{
							ControladorNotificaciones.errorAlert("ERROR", "COMPAÑÍA NO AGREGADA.",  "HA OCURRIDO UN ERROR. INTENTE NUEVAMENTE.");
						}
					}
	
					else {
						email = txtCorreo.getText();
						Matcher matcher = pattern.matcher(email);
					    if (matcher.matches()) {
							compania.setNombre_compania(txtNombre.getText());
							if(txtTelefono.getText() == null){
								compania.setTelefono_compania(null);
							}else{
								compania.setTelefono_compania(txtTelefono.getText());
							}
							compania.setCorreo_compania(txtCorreo.getText());
							compania.setDireccion_compania(txtDireccion.getText());
							compania.setCp_compania(txtCp.getText());
							compania.setEdo_compania(cbEstados.getSelectionModel().getSelectedItem());
							compania.setMpio_compania(cbMunicipios.getSelectionModel().getSelectedItem());
							compania.setCol_compania(cbColonias.getSelectionModel().getSelectedItem());
							
							if(compania.agregar()){
								ControladorNotificaciones.infoAlert("INFORMACIÓN", null, "COMPAÑÍA AGREGADA CORRECTAMENTE.");
								tvCompanias.setDisable(false);
								ckbInactivos.setDisable(false);
								actualizarTabla();
								limpiar();
								bloquear();
							}
							else{
								ControladorNotificaciones.errorAlert("ERROR", "COMPAÑÍA NO AGREGADA.", "HA OCURRIDO UN ERROR. INTENTE NUEVAMENTE.");
							}
					    }
					    else{
					    	ControladorNotificaciones.warnningAlert("ADVERTENCIA", null, "INGRESE UN CORREO VÁLIDO.");
					    }
					}
				}
				else {
					ControladorNotificaciones.warnningAlert("ADVERTENCIA", null, "NO SE HA VALIDADO EL -CÓDIGO POSTAL-");
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@FXML public void clickCancelar(){
		actualizarTabla();
		limpiar();
		bloquear();
	}

	@FXML public void clickEliminar(){
		if(compania.getId_compania() > 0){
			compania.eliminar();
			actualizarTabla();
		}
		else{
			ControladorNotificaciones.warnningAlert("ADVERTENCIA", null, "SELECCIONE UNA COMPAÑÍA DE LA TABLA.");
		}
		limpiar();
		bloquear();
	}

	@FXML public void clickRegresar(){
		ControladorVentanas cv = ControladorVentanas.getInstancia();
		cv.cerrarAcceso2();
	}
	
	@SuppressWarnings("unchecked")
	@FXML public void clickInactivos(){
		bloquear();
		limpiar();
		try{
			tvCompanias.getItems().clear();
			if(ckbInactivos.isSelected()){
				//Si está seleccionado se muestran los inactivos
				lista = compania.consultar("select * from companias where estado_compania = false");
				listaBusqueda =  new FilteredList<DAOCompanias>(lista);

				//Agregar una columna al tableView para restaurar el dato inactivo
				tvCompanias.getColumns().add(0, columnaRestaurar);
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
			tvCompanias.setItems(lista); //Asignar la lista actualizada a la tabla
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
			imagen = new Image("Vista/img/restaurar.png");
			contenedor = new ImageView(imagen);
			contenedor.setFitWidth(15);
			contenedor.setFitHeight(15);
			cellButton = new Button("", contenedor);

			cellButton.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent t) {
					compania = (DAOCompanias) BotonActivar.this.getTableView().getItems().get(BotonActivar.this.getIndex());
					if(compania.reactivar()){
						lista = compania.consultar("select * from companias where estado_compania = false");
						tvCompanias.setItems(lista);
						tvCompanias.refresh();
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
			tvCompanias.refresh();
			tvCompanias.setItems(lista);
			btnNuevo.setDisable(false);
			ckbInactivos.setDisable(false);
		}
		else{
			try{
				bloquear();
				btnNuevo.setDisable(true);
				ckbInactivos.setDisable(true);
				listaBusqueda.setPredicate(DAOProveedores->{
					if(DAOProveedores.getNombre_compania().toLowerCase().contains(txtBuscador.getText().toLowerCase()) ||
						DAOProveedores.getTelefono_compania().toLowerCase().contains(txtBuscador.getText().toLowerCase()) ||
						DAOProveedores.getCol_compania().toLowerCase().contains(txtBuscador.getText().toLowerCase()) ||
						DAOProveedores.getMpio_compania().toLowerCase().contains(txtBuscador.getText().toLowerCase()) ||
						DAOProveedores.getEdo_compania().toLowerCase().contains(txtBuscador.getText().toLowerCase()) ){
					return true;
				}
					else{
						return false;
					}
				});
				tvCompanias.refresh();
				tvCompanias.setItems(listaBusqueda);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
