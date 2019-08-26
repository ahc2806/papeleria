package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.sun.prism.impl.Disposer.Record;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import modelo.DAOCompanias;
import modelo.DAOProveedores;
import modelo.DAOSepomex;

@SuppressWarnings("restriction")
public class ControladorProveedores implements Initializable{

	@FXML TableView<DAOProveedores> tvProveedores;
	@FXML Button btnNuevo, btnGuardar, btnEditar, btnCancelar, btnRegresar, btnEliminar, btnAgregarComp, btnValidarCP;
	@FXML TextField txtDireccion,txtNombre, txtApeP, txtApeM,txtTelefono, txtCorreo, txtBuscador, txtCp;
	@FXML CheckBox ckbInactivos;
	@FXML ComboBox<String> cbSexo, cbMunicipios, cbEstados, cbColonias, cbCompanias;
	@SuppressWarnings("rawtypes")
	@FXML TableColumn columnaRestaurar = new TableColumn<>();

	private ObservableList<DAOProveedores> lista;
	private ObservableList<String> obSexo;
	private DAOProveedores proveedor;
	private DAOSepomex sepomex;
	private DAOCompanias compania;
	private ControladorVentanas instancia;
	private FilteredList<DAOProveedores> listaBusqueda;
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
	
	public ControladorProveedores() {
		proveedor = new DAOProveedores();
		sepomex = new DAOSepomex();
		compania = new DAOCompanias();
		instancia=ControladorVentanas.getInstancia();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		obSexo = FXCollections.observableArrayList();
		obSexo.add("Hombre");
		obSexo.add("Mujer");
		cbSexo.setItems(obSexo);
		
		cbCompanias.setItems(compania.consultarCompanias());
		
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
		
		txtApeP.textProperty().addListener((observable, oldValue, newValue)->{
			//Se asigna al valor anterior
			if(!newValue.matches("[a-zA-ZñÑáÁéÉíÍóÓúÚ ]{0,30}") || newValue.length()>30){
				((StringProperty) observable).setValue(oldValue);
			}else{
				//Se asigna el nuevo valor, porque si coincide con la expresión
				((StringProperty)observable).setValue(newValue);
			}
		});
		
		txtApeM.textProperty().addListener((observable, oldValue, newValue)->{
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
		 
		 btnAgregarComp.setOnMouseEntered(e -> btnAgregarComp.setStyle(estiloBoton)); 
		 btnAgregarComp.setOnMouseEntered(e -> btnAgregarComp.setStyle(letraNegra)); 
		 btnAgregarComp.setOnMouseExited(e -> btnAgregarComp.setStyle(letraNormal)); 
		 btnAgregarComp.setOnMouseExited(e -> btnAgregarComp.setStyle(estiloNormal));
		 
		 btnRegresar.setOnMouseEntered(e -> btnRegresar.setStyle(estiloBoton)); 
		 btnRegresar.setOnMouseEntered(e -> btnRegresar.setStyle(letraNegra)); 
		 btnRegresar.setOnMouseExited(e -> btnRegresar.setStyle(letraNormal)); 
		 btnRegresar.setOnMouseExited(e -> btnRegresar.setStyle(estiloNormal));
		 
		 actualizarTabla();
		 bloquear();
	}
	
	public void actualizarTabla(){
		tvProveedores.getItems().clear();
		lista = proveedor.consultar("select * from proveedores where estado_proveedor = true");
		tvProveedores.setItems(lista);
		tvProveedores.refresh();
		listaBusqueda = new FilteredList<DAOProveedores>(lista);
	}
	
	public void activar(){
		txtDireccion.setDisable(false);
		txtNombre.setDisable(false);
		txtApeP.setDisable(false);
		txtApeM.setDisable(false);
		txtTelefono.setDisable(false);
		txtCorreo.setDisable(false);
		txtCp.setDisable(false);
		btnValidarCP.setDisable(false);
		cbSexo.setDisable(false);
		cbCompanias.setDisable(false);
	}
	
	public void limpiar(){
		txtNombre.clear();
		txtApeP.clear();
		txtApeM.clear();
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
		cbSexo.getSelectionModel().select(-1);
		cbCompanias.getSelectionModel().select(-1);
		tvProveedores.setDisable(false);
		txtBuscador.setDisable(false);
		txtBuscador.clear();
		ckbInactivos.setDisable(false);
		validar = false;
	}
	
	public void bloquear(){
		txtNombre.setDisable(true);
		txtApeP.setDisable(true);
		txtApeM.setDisable(true);
		txtTelefono.setDisable(true);
		txtCorreo.setDisable(true);
		txtDireccion.setDisable(true);
		txtCp.setDisable(true);
		btnValidarCP.setDisable(true);
		cbSexo.setDisable(true);
		cbEstados.setDisable(true);
		cbMunicipios.setDisable(true);
		cbColonias.setDisable(true);
		cbCompanias.setDisable(true);
		btnGuardar.setDisable(true);
		btnEliminar.setDisable(true);
		btnEditar.setDisable(true);
		btnCancelar.setDisable(true);
		btnNuevo.setDisable(false);
		txtBuscador.setDisable(false);
		tvProveedores.setDisable(false);
		ckbInactivos.setDisable(false);
	}
	
	public void inactivosFalse() {
		if(columnaRestaurar.isVisible() == true){
			tvProveedores.getColumns().remove(columnaRestaurar);
			tvProveedores.getItems().clear();
			lista = proveedor.consultar("select * from proveedores where estado_proveedor = true");
			tvProveedores.setItems(lista);
			tvProveedores.refresh();
		}
		else {
			tvProveedores.getItems().clear();
			lista = proveedor.consultar("select * from proveedores where estado_proveedor = true");
			tvProveedores.setItems(lista);
			tvProveedores.refresh();
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
			ControladorNotificaciones.warnningAlert("ADVERTENCIA",null,  "EL -CÓDIGO POSTAL- NO EXISTE. INTENTE NUEVAMENTE.");
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
		if(tvProveedores.getSelectionModel().getSelectedItem()!= null){
			activar();
			proveedor = tvProveedores.getSelectionModel().getSelectedItem();
			txtNombre.setText(proveedor.getNombre_proveedor());
			txtApeP.setText(proveedor.getApeP_proveedor());
			txtApeM.setText(proveedor.getApeM_proveedor());
			txtTelefono.setText(proveedor.getTelefono_proveedor());
			txtCorreo.setText(proveedor.getCorreo_proveedor());
			txtDireccion.setText(proveedor.getDireccion_proveedor());
			txtCp.setText(proveedor.getCp_proveedor());
			cbEstados.getSelectionModel().select(proveedor.getEdo_proveedor());
			cbMunicipios.getSelectionModel().select(proveedor.getMpio_proveedor());
			cbColonias.getSelectionModel().select(proveedor.getCol_proveedor());
			cbSexo.getSelectionModel().select(proveedor.getSexo_proveedor());
			cbCompanias.getSelectionModel().select(proveedor.getCompania_proveedor());
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
		tvProveedores.setDisable(true);
		ckbInactivos.setSelected(false);
		ckbInactivos.setDisable(true);
		cbSexo.getSelectionModel().selectFirst();
	}
	
	@FXML public void clickEditar(){
		try{
			if(txtNombre.getText().trim().isEmpty() ||txtApeP.getText().trim().isEmpty() ||txtDireccion.getText().trim().isEmpty() ||txtCp.getText().trim().isEmpty()){
				ControladorNotificaciones.warnningAlert("ADVERTENCIA",null,  "ALGUNOS CAMPOS NECESARIOS ESTÁN VACÍOS.");
			}
			else {
				if(validar) {
					if (txtCorreo.getText().isEmpty()) {
						proveedor.setNombre_proveedor(txtNombre.getText());
						proveedor.setApeP_proveedor(txtApeP.getText());
						if(txtApeM.getText() == null){
							proveedor.setApeM_proveedor(null);
						}else{
							proveedor.setApeM_proveedor(txtApeM.getText());
						}
						if(txtTelefono.getText() == null){
							proveedor.setTelefono_proveedor(null);
						}else{
							proveedor.setTelefono_proveedor(txtTelefono.getText());
						}
						proveedor.setCorreo_proveedor(null);
						proveedor.setDireccion_proveedor(txtDireccion.getText());
						proveedor.setCp_proveedor(txtCp.getText());
						proveedor.setEdo_proveedor(cbEstados.getSelectionModel().getSelectedItem());
						proveedor.setMpio_proveedor(cbMunicipios.getSelectionModel().getSelectedItem());
						proveedor.setCol_proveedor(cbColonias.getSelectionModel().getSelectedItem());
						proveedor.setSexo_proveedor(cbSexo.getSelectionModel().getSelectedItem());
						proveedor.setCompania_proveedor(cbCompanias.getSelectionModel().getSelectedItem());
						
						if(proveedor.editar()){
							ControladorNotificaciones.infoAlert("INFORMACIÓN",null,"PROVEEDOR ACTUALIZADO CORRECTAMENTE.");
							tvProveedores.setDisable(false);
							ckbInactivos.setDisable(false);
							actualizarTabla();
							limpiar();
							bloquear();
						}
						else{
							ControladorNotificaciones.errorAlert("ERROR","PROVEEDOR NO ACTUALIZADO.", "HA OCURRIDO UN ERROR. INTENTE NUEVAMENTE.");
						}
					}
	
					else {
						email = txtCorreo.getText();
						Matcher matcher = pattern.matcher(email);
					    if (matcher.matches()) {
					    	proveedor.setNombre_proveedor(txtNombre.getText());
							proveedor.setApeP_proveedor(txtApeP.getText());
							if(txtApeM.getText() == null){
								proveedor.setApeM_proveedor(null);
							}else{
								proveedor.setApeM_proveedor(txtApeM.getText());
							}
							if(txtTelefono.getText() == null){
								proveedor.setTelefono_proveedor(null);
							}else{
								proveedor.setTelefono_proveedor(txtTelefono.getText());
							}
							proveedor.setCorreo_proveedor(txtCorreo.getText());
							proveedor.setDireccion_proveedor(txtDireccion.getText());
							proveedor.setCp_proveedor(txtCp.getText());
							proveedor.setEdo_proveedor(cbEstados.getSelectionModel().getSelectedItem());
							proveedor.setMpio_proveedor(cbMunicipios.getSelectionModel().getSelectedItem());
							proveedor.setCol_proveedor(cbColonias.getSelectionModel().getSelectedItem());
							proveedor.setSexo_proveedor(cbSexo.getSelectionModel().getSelectedItem());
							proveedor.setCompania_proveedor(cbCompanias.getSelectionModel().getSelectedItem());
					    	
					    	if(proveedor.editar()){
					    		ControladorNotificaciones.infoAlert("INFORMACIÓN",null, "PROVEEDOR ACTUALIZADO CORRECTAMENTE.");
								tvProveedores.setDisable(false);
								ckbInactivos.setDisable(false);
								actualizarTabla();
								limpiar();
								bloquear();
							}
							else{
								ControladorNotificaciones.errorAlert("ERROR","PROVEEDOR NO ACTUALIZADO.","HA OCURRIDO UN ERROR. INTENTE NUEVAMENTE.");
							}
					    }
					    else{
					    	ControladorNotificaciones.warnningAlert("ADVERTENCIA",null, "INGRESE UN CORREO VÁLIDO.");
					    }
					}
				}
				else {
					ControladorNotificaciones.warnningAlert("ADVERTENCIA",null,   "NO SE HA VALIDADO EL -CÓDIGO POSTAL-");
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@FXML public void clickGuardar() {
		try{
			if(txtNombre.getText().trim().isEmpty() ||txtApeP.getText().trim().isEmpty() ||txtDireccion.getText().trim().isEmpty() ||txtCp.getText().trim().isEmpty()){
				ControladorNotificaciones.warnningAlert("ADVERTENCIA",null,   "ALGUNOS CAMPOS NECESARIOS ESTÁN VACÍOS.");
			}
			else {
				if(validar) {
					if (txtCorreo.getText().isEmpty()) {
						proveedor.setNombre_proveedor(txtNombre.getText());
						proveedor.setApeP_proveedor(txtApeP.getText());
						if(txtApeM.getText() == null){
							proveedor.setApeM_proveedor(null);
						}else{
							proveedor.setApeM_proveedor(txtApeM.getText());
						}
						if(txtTelefono.getText() == null){
							proveedor.setTelefono_proveedor(null);
						}else{
							proveedor.setTelefono_proveedor(txtTelefono.getText());
						}
						proveedor.setCorreo_proveedor(null);
						proveedor.setDireccion_proveedor(txtDireccion.getText());
						proveedor.setCp_proveedor(txtCp.getText());
						proveedor.setEdo_proveedor(cbEstados.getSelectionModel().getSelectedItem());
						proveedor.setMpio_proveedor(cbMunicipios.getSelectionModel().getSelectedItem());
						proveedor.setCol_proveedor(cbColonias.getSelectionModel().getSelectedItem());
						proveedor.setSexo_proveedor(cbSexo.getSelectionModel().getSelectedItem());
						proveedor.setCompania_proveedor(cbCompanias.getSelectionModel().getSelectedItem());
						
						if(proveedor.agregar()){
							ControladorNotificaciones.infoAlert("INFORMACIÓN",null,"PROVEEDOR AGREGADO CORRECTAMENTE.");
							tvProveedores.setDisable(false);
							ckbInactivos.setDisable(false);
							actualizarTabla();
							limpiar();
							bloquear();
						}
						else{
							ControladorNotificaciones.errorAlert("ERROR","PROVEEDOR NO AGREGADO.","HA OCURRIDO UN ERROR. INTENTE NUEVAMENTE.");
						}
					}
	
					else {
						email = txtCorreo.getText();
						Matcher matcher = pattern.matcher(email);
					    if (matcher.matches()) {
					    	proveedor.setNombre_proveedor(txtNombre.getText());
							proveedor.setApeP_proveedor(txtApeP.getText());
							if(txtApeM.getText() == null){
								proveedor.setApeM_proveedor(null);
							}else{
								proveedor.setApeM_proveedor(txtApeM.getText());
							}
							if(txtTelefono.getText() == null){
								proveedor.setTelefono_proveedor(null);
							}else{
								proveedor.setTelefono_proveedor(txtTelefono.getText());
							}
							proveedor.setCorreo_proveedor(txtCorreo.getText());
							proveedor.setDireccion_proveedor(txtDireccion.getText());
							proveedor.setCp_proveedor(txtCp.getText());
							proveedor.setEdo_proveedor(cbEstados.getSelectionModel().getSelectedItem());
							proveedor.setMpio_proveedor(cbMunicipios.getSelectionModel().getSelectedItem());
							proveedor.setCol_proveedor(cbColonias.getSelectionModel().getSelectedItem());
							proveedor.setSexo_proveedor(cbSexo.getSelectionModel().getSelectedItem());
							proveedor.setCompania_proveedor(cbCompanias.getSelectionModel().getSelectedItem());
					    	
					    	if(proveedor.agregar()){
					    		ControladorNotificaciones.infoAlert("INFORMACIÓN",null, "PROVEEDOR AGREGADO CORRECTAMENTE.");
								tvProveedores.setDisable(false);
								ckbInactivos.setDisable(false);
								actualizarTabla();
								limpiar();
								bloquear();
							}
							else{
								ControladorNotificaciones.errorAlert("ERROR","PROVEEDOR NO AGREGADO.", "HA OCURRIDO UN ERROR. INTENTE NUEVAMENTE.");
							}
					    }
					    else{
					    	ControladorNotificaciones.warnningAlert("ADVERTENCIA",null, "INGRESE UN CORREO VÁLIDO.");
					    }
					}
				}
				else {
					ControladorNotificaciones.warnningAlert("ADVERTENCIA",null, "NO SE HA VALIDADO EL -CÓDIGO POSTAL-");
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
		if(proveedor.getId_proveedor() > 0){
			proveedor.eliminar();
			actualizarTabla();
		}
		else{
			ControladorNotificaciones.warnningAlert("ADVERTENCIA",null, "SELECCIONE UN PROVEEDOR DE LA TABLA.");
		}
		limpiar();
		bloquear();
	}
	
	@FXML public void clickCompanias(){
		instancia.asignarModal2("../vista/companias.fxml", "REGISTRO DE COMPAÑÍAS");
		instancia.pantallaCompleta3();
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
			tvProveedores.getItems().clear();
			if(ckbInactivos.isSelected()){
				//Si está seleccionado se muestran los inactivos
				lista = proveedor.consultar("select * from proveedores where estado_proveedor = false");
				listaBusqueda =  new FilteredList<DAOProveedores>(lista);

				//Agregar una columna al tableView para restaurar el dato inactivo
				tvProveedores.getColumns().add(0, columnaRestaurar);
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
			tvProveedores.setItems(lista); //Asignar la lista actualizada a la tabla
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
					proveedor = (DAOProveedores) BotonActivar.this.getTableView().getItems().get(BotonActivar.this.getIndex());
					if(proveedor.reactivar()){
						lista = proveedor.consultar("select * from proveedores where estado_proveedor = false");
						tvProveedores.setItems(lista);
						tvProveedores.refresh();
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
			tvProveedores.refresh();
			tvProveedores.setItems(lista);
			btnNuevo.setDisable(false);
			ckbInactivos.setDisable(false);
		}
		else{
			try{
				bloquear();
				btnNuevo.setDisable(true);
				ckbInactivos.setDisable(true);
				listaBusqueda.setPredicate(DAOProveedores->{
					if(DAOProveedores.getNombre_proveedor().toLowerCase().contains(txtBuscador.getText().toLowerCase()) ||
						DAOProveedores.getApeP_proveedor().toLowerCase().contains(txtBuscador.getText().toLowerCase()) ||
						DAOProveedores.getApeM_proveedor().toLowerCase().contains(txtBuscador.getText().toLowerCase()) ||
						DAOProveedores.getTelefono_proveedor().toLowerCase().contains(txtBuscador.getText().toLowerCase()) ||
						DAOProveedores.getCol_proveedor().toLowerCase().contains(txtBuscador.getText().toLowerCase()) ||
						DAOProveedores.getMpio_proveedor().toLowerCase().contains(txtBuscador.getText().toLowerCase()) ||
						DAOProveedores.getEdo_proveedor().toLowerCase().contains(txtBuscador.getText().toLowerCase()) ){
					return true;
				}
					else{
						return false;
					}
				});
				tvProveedores.refresh();
				tvProveedores.setItems(listaBusqueda);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
