package controlador;

import java.net.URL;
import java.util.ResourceBundle;
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
import modelo.DAOInventario;

@SuppressWarnings("restriction")
public class ControladorInventario  implements Initializable{

	@FXML TableView<DAOInventario> tvInventario;
	@FXML Button btnNuevo, btnGuardar, btnEditar, btnCancelar, btnRegresar, btnEliminar;
	@FXML TextField txtNombre, txtModelo, txtCodigo,txtPrecio, txtColor, txtBuscador, txtTamano, txtMedidas, txtCantidad,
											txtEspecificaciones, txtMarca, txtCapacidad, txtNum_de_pag, txtGenero;
	@FXML CheckBox ckbInactivos;
	@FXML ComboBox<String> cbTipo, cbSeccion;
	@SuppressWarnings("rawtypes")
	@FXML TableColumn tcSeccion, tcTipo, tcCapacidad, tcNum_de_pag, tcGenero;
	@SuppressWarnings("rawtypes")
	@FXML TableColumn columnaRestaurar = new TableColumn<>();
	
	private DAOInventario inventario;
	private ObservableList<DAOInventario> lista;
	private FilteredList<DAOInventario> listaBusqueda;
	private ObservableList<String> obSecciones, obTipos;
	private float precio;
	private static final String letraNegra= "-fx-text-fill: -fx-text-base-color;";
	private static final String estiloNormal ="-fx-background-color:transparent;";
	private static final String letraNormal= "-fx-text-fill: WHITE;";
	private static final String estiloBoton = "-fx-background-color:-fx-shadow-highlight-color, -fx-outer-border, "
			+ "-fx-body-color, -fx-inner-border;"; 
	
	public ControladorInventario() {
		inventario = new DAOInventario();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources ) {
		
		//Llenar lista de Secciones...
		obSecciones = FXCollections.observableArrayList();
		obSecciones.add("ELECTRÓNICA");
		obSecciones.add("ESCOLARES, ARTE Y DISEÑO");
		obSecciones.add("DULCES");
		obSecciones.add("LIBROS");
		obSecciones.add("MERCERÍA");
		obSecciones.add("PAPELERÍA");
		obSecciones.add("OTRO");
		cbSeccion.setItems(obSecciones);
		 
		//restricciones para escribir
		txtNombre.textProperty().addListener((observable, oldValue, newValue)->{
			//Se asigna al valor anterior
			if(!newValue.matches("[a-zA-ZñÑáéíóúÁÉÍÓÚ0-9/ ]{0,50}") || newValue.length()>50){
				((StringProperty) observable).setValue(oldValue);
			}else{
				//Se asigna el nuevo valor, porque si coincide con la expresión
				((StringProperty)observable).setValue(newValue);
			}
		});
		 txtModelo.textProperty().addListener((observable, oldValue, newValue)->{
				//Se asigna al valor anterior
				if(!newValue.matches("[a-zA-ZñÑáéíóúÁÉÍÓÚ0-9 .-]{0,25}") || newValue.length()>25){
					((StringProperty) observable).setValue(oldValue);
				}else{
					//Se asigna el nuevo valor, porque si coincide con la expresión
					((StringProperty)observable).setValue(newValue);
				}
		});
		txtCodigo.textProperty().addListener((observable, oldValue, newValue)->{
				//Se asigna al valor anterior
				if(!newValue.matches("[a-zA-ZñÑáéíóúÁÉÍÓÚ0-9 .-]{0,25}") || newValue.length()>25){
					((StringProperty) observable).setValue(oldValue);
				}else{
					//Se asigna el nuevo valor, porque si coincide con la expresión
					((StringProperty)observable).setValue(newValue);
				}
		});
		txtPrecio.textProperty().addListener((observable, oldValue, newValue)->{
			//Se asigna al valor anterior
			if(!newValue.matches("[0-9.]{0,10}") || newValue.length()>10){
				((StringProperty) observable).setValue(oldValue);
			}else{
				//Se asigna el nuevo valor, porque si coincide con la expresión
				((StringProperty)observable).setValue(newValue);
			}
		});
	 	txtColor.textProperty().addListener((observable, oldValue, newValue)->{
			//Se asigna al valor anterior
			if(!newValue.matches("[a-zA-ZñÑáéíóúÁÉÍÓÚ0-9 .-]{0,25}") || newValue.length()>25){
				((StringProperty) observable).setValue(oldValue);
			}else{
				//Se asigna el nuevo valor, porque si coincide con la expresión
				((StringProperty)observable).setValue(newValue);
			}
		});
	 	txtTamano.textProperty().addListener((observable, oldValue, newValue)->{
			//Se asigna al valor anterior
			if(!newValue.matches("[a-zA-ZñÑáéíóúÁÉÍÓÚ0-9 .-]{0,25}") || newValue.length()>25){
				((StringProperty) observable).setValue(oldValue);
			}else{
				//Se asigna el nuevo valor, porque si coincide con la expresión
				((StringProperty)observable).setValue(newValue);
			}
		});
	 	txtMedidas.textProperty().addListener((observable, oldValue, newValue)->{
			//Se asigna al valor anterior
			if(!newValue.matches("[a-zA-ZñÑáéíóúÁÉÍÓÚ0-9 .-]{0,25}") || newValue.length()>25){
				((StringProperty) observable).setValue(oldValue);
			}else{
				//Se asigna el nuevo valor, porque si coincide con la expresión
				((StringProperty)observable).setValue(newValue);
			}
		});
	 	txtCantidad.textProperty().addListener((observable, oldValue, newValue)->{
			//Se asigna al valor anterior
			if(!newValue.matches("[0-9]{0,5}") || newValue.length()>5){
				((StringProperty) observable).setValue(oldValue);
			}else{
				//Se asigna el nuevo valor, porque si coincide con la expresión
				((StringProperty)observable).setValue(newValue);
			}
		});
	 	txtEspecificaciones.textProperty().addListener((observable, oldValue, newValue)->{
			//Se asigna al valor anterior
			if(!newValue.matches("[a-zA-ZñÑáéíóúÁÉÍÓÚ0-9 .-]{0,150}") || newValue.length()>150){
				((StringProperty) observable).setValue(oldValue);
			}else{
				//Se asigna el nuevo valor, porque si coincide con la expresión
				((StringProperty)observable).setValue(newValue);
			}
	 	});
	 	txtMarca.textProperty().addListener((observable, oldValue, newValue)->{
			//Se asigna al valor anterior
			if(!newValue.matches("[a-zA-ZñÑáéíóúÁÉÍÓÚ0-9 .-]{0,20}") || newValue.length()>20){
				((StringProperty) observable).setValue(oldValue);
			}else{
				//Se asigna el nuevo valor, porque si coincide con la expresión
				((StringProperty)observable).setValue(newValue);
			}
		});
	 	txtCapacidad.textProperty().addListener((observable, oldValue, newValue)->{
			//Se asigna al valor anterior
			if(!newValue.matches("[a-zA-ZñÑáéíóúÁÉÍÓÚ0-9 .-]{0,20}") || newValue.length()>20){
				((StringProperty) observable).setValue(oldValue);
			}else{
				//Se asigna el nuevo valor, porque si coincide con la expresión
				((StringProperty)observable).setValue(newValue);
			}
		});
	 	txtNum_de_pag.textProperty().addListener((observable, oldValue, newValue)->{
			//Se asigna al valor anterior
			if(!newValue.matches("[a-zA-ZáéíóúÁÉÍÓÚ0-9 .-]{0,10}") || newValue.length()>10){
				((StringProperty) observable).setValue(oldValue);
			}else{
				//Se asigna el nuevo valor, porque si coincide con la expresión
				((StringProperty)observable).setValue(newValue);
			}
		});
	 	txtGenero.textProperty().addListener((observable, oldValue, newValue)->{
			//Se asigna al valor anterior
			if(!newValue.matches("[a-zA-ZñÑáéíóúÁÉÍÓÚ .-]{0,25}") || newValue.length()>25){
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
		 
		 tcSeccion.setVisible(true);
		 tcTipo.setVisible(true);
		 tcCapacidad.setVisible(true);
		 tcNum_de_pag.setVisible(true);
		 tcGenero.setVisible(true); 
		actualizarTabla();
		bloquear();
	}

	public void actualizarTabla(){
		tvInventario.getItems().clear();
		lista=inventario.consultar("select * from inventario where estado_inventario = true");
		tvInventario.setItems(lista);
		tvInventario.refresh();
		listaBusqueda = new FilteredList<DAOInventario>(lista);
	}
	
	public void bloquear(){
		txtNombre.setDisable(true);
		txtModelo.setDisable(true);
		txtCodigo.setDisable(true);
		txtPrecio.setDisable(true);
		txtColor.setDisable(true);
		txtTamano.setDisable(true);
		txtMedidas.setDisable(true);
		txtCantidad.setDisable(true);
		txtEspecificaciones.setDisable(true);
		txtMarca.setDisable(true);
		txtCapacidad.setDisable(true);
		txtNum_de_pag.setDisable(true);
		txtGenero.setDisable(true);
		cbSeccion.setDisable(true);
		cbTipo.setDisable(true);
		btnGuardar.setDisable(true);
		btnEliminar.setDisable(true);
		btnEditar.setDisable(true);
		btnCancelar.setDisable(true);
		btnNuevo.setDisable(false);
		txtBuscador.setDisable(false);
		tvInventario.setDisable(false);
		ckbInactivos.setDisable(false);
	}
	
	public void inactivosFalse() {
		if(columnaRestaurar.isVisible() == true){
			tvInventario.getColumns().remove(columnaRestaurar);
			tvInventario.getItems().clear();
			lista = inventario.consultar("select * from inventario where estado_inventario = true");
			tvInventario.setItems(lista);
			tvInventario.refresh();
		}
		else {
			tvInventario.getItems().clear();
			lista = inventario.consultar("select * from inventario where estado_inventario = true");
			tvInventario.setItems(lista);
			tvInventario.refresh();
		}
	}
	
	public void activar(){
		txtNombre.setDisable(false);
		txtModelo.setDisable(false);
		txtCodigo.setDisable(false);
		txtPrecio.setDisable(false);
		txtColor.setDisable(false);
		txtTamano.setDisable(false);
		txtMedidas.setDisable(false);
		txtCantidad.setDisable(false);
		txtEspecificaciones.setDisable(false);
		txtMarca.setDisable(false);
		txtCapacidad.setDisable(false);
		txtNum_de_pag.setDisable(false);
		txtGenero.setDisable(false);
		cbTipo.setDisable(false);
	}

	public void limpiar() {
		txtNombre.clear();
		txtModelo.clear();
		txtCodigo.clear();
		txtPrecio.clear();
		txtColor.clear();
		txtTamano.clear();
		txtMedidas.clear();
		txtCantidad.clear();
		txtEspecificaciones.clear();
		txtMarca.clear();
		txtCapacidad.clear();
		txtNum_de_pag.clear();
		txtGenero.clear();
		cbTipo.getSelectionModel().select(-1);
		cbSeccion.getSelectionModel().select(-1);
		txtBuscador.setDisable(false);
		txtBuscador.clear();
		ckbInactivos.setDisable(false);
	}
	
	@FXML public void clickTabla(){
		if(tvInventario.getSelectionModel().getSelectedItem()!= null){
			activar();
			inventario = tvInventario.getSelectionModel().getSelectedItem();
			txtNombre.setText(inventario.getNombre_inventario());
			txtModelo.setText(inventario.getModelo_inventario());
			txtCodigo.setText(inventario.getCodigo_inventario());
			txtPrecio.setText(inventario.getPrecio_inventario());
			txtColor.setText(inventario.getColor_inventario());
			txtTamano.setText(inventario.getTamano_inventario());
			txtMedidas.setText(inventario.getMedidas_inventario());
			txtCantidad.setText(Integer.toString(inventario.getCantidad_inventario()));
			txtEspecificaciones.setText(inventario.getEspecificaciones_inventario());
			txtMarca.setText(inventario.getMarca_inventario());
			txtCapacidad.setText(inventario.getCapacidad_inventario());
			txtNum_de_pag.setText(inventario.getNum_de_pag_inventario());
			txtGenero.setText(inventario.getGenero_inventario());
			cbTipo.getSelectionModel().select(inventario.getTipo_inventario());
			cbSeccion.getSelectionModel().select(inventario.getSeccion_inventario());

			btnEditar.setDisable(false);
			btnEliminar.setDisable(false);
			btnCancelar.setDisable(false);
			btnNuevo.setDisable(true);
			ckbInactivos.setDisable(true);
			txtBuscador.setDisable(true);
		}
	}
	
	@FXML public void clickSeccion() {
		cbTipo.setDisable(false);
		cbTipo.getItems().clear();
		//Recuperar la sección seleccionada
		if(cbSeccion.getSelectionModel().getSelectedItem() == "PAPELERÍA"){
			obTipos = FXCollections.observableArrayList();
			obTipos.add("CARTULINAS");
			obTipos.add("HOJAS BLANCAS");
			obTipos.add("HOJAS DE COLOR");
			obTipos.add("HOJAS OPALINA");
			obTipos.add("LÁMINAS");
			obTipos.add("MAPAS");
			obTipos.add("PAPEL AMERICA");
			obTipos.add("PAPEL BOND");
			obTipos.add("PAPEL CARBÓN");
			obTipos.add("PAPEL CASCARON");
			obTipos.add("PAPEL CHINA");
			obTipos.add("PAPEL CONTAC");
			obTipos.add("PAPEL CORRUGADO");
			obTipos.add("PAPEL CRAFF");
			obTipos.add("PAPEL CREPÉ");
			obTipos.add("PAPEL LUSTRE");
			obTipos.add("PAPEL METALIZADO");
			obTipos.add("OTRO");
			cbTipo.setItems(obTipos);
		}
		else if(cbSeccion.getSelectionModel().getSelectedItem() =="DULCES") {
			obTipos = FXCollections.observableArrayList();
			obTipos.add("CHICLES");
			obTipos.add("CHOCOLATES");
			obTipos.add("JUGOS");
			obTipos.add("PALETAS");
			obTipos.add("REFRESCOS");
			obTipos.add("OTRO");
			cbTipo.setItems(obTipos); 
		}
		else if(cbSeccion.getSelectionModel().getSelectedItem() =="ELECTRÓNICA") {
			obTipos = FXCollections.observableArrayList();
			obTipos.add("CELULARES");
			obTipos.add("COMPUTADORAS");
			obTipos.add("MEMORIAS");
			obTipos.add("OTRO");
			cbTipo.setItems(obTipos); 
		}
		else if(cbSeccion.getSelectionModel().getSelectedItem() =="ESCOLARES, ARTE Y DISEÑO") {
			obTipos = FXCollections.observableArrayList();
			obTipos.add("ARTE Y DISEÑO");
			obTipos.add("CAJAS");
			obTipos.add("CALCULADORAS");
			obTipos.add("CINTAS ADHESIVAS");
			obTipos.add("DIBUJO");
			obTipos.add("ESCRITURA");
			obTipos.add("ETIQUETAS");
			obTipos.add("FOLDERS Y SOBRES");
			obTipos.add("GRAPAS Y ACCESORIOS");
			obTipos.add("HERRAMIENTAS ESCOLARES");
			obTipos.add("LAPICERAS");
			obTipos.add("LETREROS Y LETRAS");
			obTipos.add("LIBRETAS, CUADERNOS Y BLOCKS");
			obTipos.add("MOCHILAS");
			obTipos.add("PEGAMENTOS");
			obTipos.add("PLANIFICACIÓN");
			obTipos.add("REGLAS Y ESCUADRAS");
			obTipos.add("SILICÓN Y ACCESORIOS");
			obTipos.add("OTRO");
			cbTipo.setItems(obTipos); 
		}
		else if(cbSeccion.getSelectionModel().getSelectedItem() =="LIBROS") {
			obTipos = FXCollections.observableArrayList();
			obTipos.add("CUENTOS");
			obTipos.add("DICCIONARIOS");
			obTipos.add("ENCICLOPEDIAS");
			obTipos.add("NOVELAS");
			obTipos.add("OTRO");
			cbTipo.setItems(obTipos); 
		}
		else if(cbSeccion.getSelectionModel().getSelectedItem() =="MERCERÍA") {
			obTipos = FXCollections.observableArrayList();
			obTipos.add("ANILLOS");
			obTipos.add("BOTONES");
			obTipos.add("COLLARES");
			obTipos.add("DIADEMAS");
			obTipos.add("HILOS");
			obTipos.add("PULSERAS");
			obTipos.add("ROPA");
			obTipos.add("OTRO");
			cbTipo.setItems(obTipos); 
		}
		else if(cbSeccion.getSelectionModel().getSelectedItem() =="OTRO") {
			obTipos = FXCollections.observableArrayList();
			obTipos.add("GENERAL");
			cbTipo.setItems(obTipos); 
		}
	}
	
	@FXML public void clickTipos() {
		if(cbSeccion.getSelectionModel().getSelectedItem() == "PAPELERÍA"){
			txtNombre.setDisable(false);
			txtModelo.setDisable(false);
			txtCodigo.setDisable(false);
			txtPrecio.setDisable(false);
			txtColor.setDisable(false);
			txtTamano.setDisable(false);
			txtMedidas.setDisable(false);
			txtCantidad.setDisable(false);
			txtEspecificaciones.setDisable(false);
			txtMarca.setDisable(false);
		}
		else if(cbSeccion.getSelectionModel().getSelectedItem() == "ELECTRÓNICA") {
			txtNombre.setDisable(false);
			txtModelo.setDisable(false);
			txtCodigo.setDisable(false);
			txtPrecio.setDisable(false);
			txtColor.setDisable(false);
			txtMedidas.setDisable(false);
			txtCantidad.setDisable(false);
			txtEspecificaciones.setDisable(false);
			txtMarca.setDisable(false);
			txtCapacidad.setDisable(false);
		}
		else if(cbSeccion.getSelectionModel().getSelectedItem() == "ESCOLARES, ARTE Y DISEÑO") {
			txtNombre.setDisable(false);
			txtModelo.setDisable(false);
			txtCodigo.setDisable(false);
			txtPrecio.setDisable(false);
			txtColor.setDisable(false);
			txtTamano.setDisable(false);
			txtMedidas.setDisable(false);
			txtCantidad.setDisable(false);
			txtEspecificaciones.setDisable(false);
			txtMarca.setDisable(false);
		}
		else if(cbSeccion.getSelectionModel().getSelectedItem() == "DULCES") {
			txtNombre.setDisable(false);
			txtCodigo.setDisable(false);
			txtPrecio.setDisable(false);
			txtTamano.setDisable(false);
			txtCantidad.setDisable(false);
			txtEspecificaciones.setDisable(false);
			txtMarca.setDisable(false);
		}
		else if(cbSeccion.getSelectionModel().getSelectedItem() == "LIBROS") {
			txtNombre.setDisable(false);
			txtCodigo.setDisable(false);
			txtPrecio.setDisable(false);
			txtCantidad.setDisable(false);
			txtEspecificaciones.setDisable(false);
			txtNum_de_pag.setDisable(false);
			txtGenero.setDisable(false);
		}
		else if(cbSeccion.getSelectionModel().getSelectedItem() == "MERCERÍA") {
			txtNombre.setDisable(false);
			txtModelo.setDisable(false);
			txtCodigo.setDisable(false);
			txtPrecio.setDisable(false);
			txtColor.setDisable(false);
			txtTamano.setDisable(false);
			txtMedidas.setDisable(false);
			txtCantidad.setDisable(false);
			txtEspecificaciones.setDisable(false);
			txtMarca.setDisable(false);
			txtCapacidad.setDisable(false);
		}
		else if(cbSeccion.getSelectionModel().getSelectedItem() == "OTRO") {
			activar();
		}
		cbSeccion.setDisable(true);
	}
	
	@FXML public void clickNuevo(){
		limpiar();
		inactivosFalse();
		btnGuardar.setDisable(false);
		btnCancelar.setDisable(false);
		btnNuevo.setDisable(true);
		txtBuscador.setDisable(true);
		tvInventario.setDisable(true);
		ckbInactivos.setSelected(false);
		ckbInactivos.setDisable(true);
		cbSeccion.setDisable(false);
	}
	
	@FXML public void clickEditar(){
		try{
			if(txtNombre.getText().trim().isEmpty() || txtCodigo.getText().trim().isEmpty() || txtPrecio.getText().trim().isEmpty() ||txtCantidad.getText().trim().isEmpty()){
				ControladorNotificaciones.warnningAlert("ADVERTENCIA", null, "ALGUNOS CAMPOS NECESARIOS ESTÁN VACÍOS.");
			}
			else{
				inventario.setSeccion_inventario(cbSeccion.getSelectionModel().getSelectedItem());
				inventario.setTipo_inventario(cbTipo.getSelectionModel().getSelectedItem());
				inventario.setNombre_inventario(txtNombre.getText().toUpperCase());
				if(txtModelo.getText() == null) {
					inventario.setModelo_inventario(null);
				}else {
					inventario.setModelo_inventario(txtModelo.getText().toUpperCase());	
				}
				inventario.setCodigo_inventario(txtCodigo.getText().toUpperCase());
				precio = Float.parseFloat(txtPrecio.getText());
				inventario.setPrecio_inventario(Float.toString(precio));
				precio = 0;
				if(txtColor.getText() == null) {
					inventario.setColor_inventario(null);
				}else {
					inventario.setColor_inventario(txtColor.getText().toUpperCase());
				}
				if(txtTamano.getText() == null) {
					inventario.setTamano_inventario(null);
				}else {
					inventario.setTamano_inventario(txtTamano.getText().toUpperCase());	
				}
				if(txtMedidas.getText() == null) {
					inventario.setMedidas_inventario(null);
				}else {
					inventario.setMedidas_inventario(txtMedidas.getText().toUpperCase());
				}
				inventario.setCantidad_inventario(Integer.parseInt(txtCantidad.getText()));
				if(txtEspecificaciones.getText() == null) {
					inventario.setEspecificaciones_inventario(null);
				}else {
					inventario.setEspecificaciones_inventario(txtEspecificaciones.getText().toUpperCase());
				}
				if(txtMarca.getText() == null) {
					inventario.setMarca_inventario(null);
				}else {
					inventario.setMarca_inventario(txtMarca.getText().toUpperCase());
				}
				if(txtCapacidad.getText() == null) {
					inventario.setCapacidad_inventario(null);
				}else {
					inventario.setCapacidad_inventario(txtCapacidad.getText().toUpperCase());
				}
				if(txtNum_de_pag.getText() == null) {
					inventario.setNum_de_pag_inventario(null);
				}else {
					inventario.setNum_de_pag_inventario(txtNum_de_pag.getText().toUpperCase());
				}
				if(txtGenero.getText() == null) {
					inventario.setGenero_inventario(null);
				}else {
					inventario.setGenero_inventario(txtGenero.getText().toUpperCase());
				}
				
				if(inventario.editar()){
					ControladorNotificaciones.infoAlert("INFORMACIÓN", null, "PRODUCTO ACTUALIZADO CORRECTAMENTE.");
					tvInventario.setDisable(false);
					ckbInactivos.setDisable(false);
					actualizarTabla();
					limpiar();
					bloquear();
				}
				else{
					ControladorNotificaciones.errorAlert("ERROR","PRODUCTO NO ACTUALIZADO.", "HA OCURRIDO UN ERROR. INTENTE NUEVAMENTE.");
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
			if(txtNombre.getText().trim().isEmpty() || txtCodigo.getText().trim().isEmpty() || txtPrecio.getText().trim().isEmpty() ||txtCantidad.getText().trim().isEmpty()){
				ControladorNotificaciones.warnningAlert("ADVERTENCIA", null, "ALGUNOS CAMPOS NECESARIOS ESTÁN VACÍOS.");
			}
			else{
				inventario.setSeccion_inventario(cbSeccion.getSelectionModel().getSelectedItem());
				inventario.setTipo_inventario(cbTipo.getSelectionModel().getSelectedItem());
				inventario.setNombre_inventario(txtNombre.getText().toUpperCase());
				if(txtModelo.getText() == null) {
					inventario.setModelo_inventario(null);
				}else {
					inventario.setModelo_inventario(txtModelo.getText().toUpperCase());	
				}
				inventario.setCodigo_inventario(txtCodigo.getText().toUpperCase());
				precio = Float.parseFloat(txtPrecio.getText());
				inventario.setPrecio_inventario(Float.toString(precio));
				precio = 0;
				if(txtColor.getText() == null) {
					inventario.setColor_inventario(null);
				}else {
					inventario.setColor_inventario(txtColor.getText().toUpperCase());
				}
				if(txtTamano.getText() == null) {
					inventario.setTamano_inventario(null);
				}else {
					inventario.setTamano_inventario(txtTamano.getText().toUpperCase());	
				}
				if(txtMedidas.getText() == null) {
					inventario.setMedidas_inventario(null);
				}else {
					inventario.setMedidas_inventario(txtMedidas.getText().toUpperCase());
				}
				inventario.setCantidad_inventario(Integer.parseInt(txtCantidad.getText()));
				if(txtEspecificaciones.getText() == null) {
					inventario.setEspecificaciones_inventario(null);
				}else {
					inventario.setEspecificaciones_inventario(txtEspecificaciones.getText().toUpperCase());
				}
				if(txtMarca.getText() == null) {
					inventario.setMarca_inventario(null);
				}else {
					inventario.setMarca_inventario(txtMarca.getText().toUpperCase());
				}
				if(txtCapacidad.getText() == null) {
					inventario.setCapacidad_inventario(null);
				}else {
					inventario.setCapacidad_inventario(txtCapacidad.getText().toUpperCase());
				}
				if(txtNum_de_pag.getText() == null) {
					inventario.setNum_de_pag_inventario(null);
				}else {
					inventario.setNum_de_pag_inventario(txtNum_de_pag.getText().toUpperCase());
				}
				if(txtGenero.getText() == null) {
					inventario.setGenero_inventario(null);
				}else {
					inventario.setGenero_inventario(txtGenero.getText().toUpperCase());
				}
				
				if(inventario.agregar()){
					ControladorNotificaciones.infoAlert("INFORMACIÓN",null, "PRODUCTO AGREGADO CORRECTAMENTE.");
					tvInventario.setDisable(false);
					ckbInactivos.setDisable(false);
					actualizarTabla();
					limpiar();
					bloquear();
				}
				else{
					ControladorNotificaciones.errorAlert("ERROR","PRODUCTO NO AGREGADO.", "HA OCURRIDO UN ERROR. INTENTE NUEVAMENTE.");
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@FXML public void clickEliminar(){
		if(inventario.getId_inventario() > 0){
			inventario.eliminar();
			actualizarTabla();
		}
		else{
			ControladorNotificaciones.warnningAlert("ADVERTENCIA", null, "SELECCIONE UN PRODUCTO DE LA TABLA.");
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
			tvInventario.getItems().clear();
			if(ckbInactivos.isSelected()){
				//Si está seleccionado se muestran los inactivos
				lista = inventario.consultar("select * from inventario where estado_inventario = false");
				listaBusqueda =  new FilteredList<DAOInventario>(lista);

				//Agregar una columna al tableView para restaurar el dato inactivo
				tvInventario.getColumns().add(0, columnaRestaurar);
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
			tvInventario.setItems(lista); //Asignar la lista actualizada a la tabla
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
					inventario = (DAOInventario) BotonActivar.this.getTableView().getItems().get(BotonActivar.this.getIndex());
					if(inventario.reactivar()){
						lista = inventario.consultar("select * from inventario where estado_inventario = false");
						tvInventario.setItems(lista);
						tvInventario.refresh();
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
		ckbInactivos.setDisable(true);
		if(txtBuscador.getText().trim().isEmpty()){
			tvInventario.refresh();
			tvInventario.setItems(lista);
			btnNuevo.setDisable(false);
			ckbInactivos.setDisable(false);
		}
		else{
			try{
				bloquear();
				btnNuevo.setDisable(true);
				ckbInactivos.setDisable(true);
				listaBusqueda.setPredicate(DAOInventario->{
					if(DAOInventario.getSeccion_inventario().toLowerCase().contains(txtBuscador.getText().toLowerCase()) ||
						DAOInventario.getNombre_inventario().toLowerCase().contains(txtBuscador.getText().toLowerCase()) ||
						DAOInventario.getTipo_inventario().toLowerCase().contains(txtBuscador.getText().toLowerCase()) ||
						DAOInventario.getModelo_inventario().toLowerCase().contains(txtBuscador.getText().toLowerCase()) ||
						DAOInventario.getCodigo_inventario().toLowerCase().contains(txtBuscador.getText().toLowerCase()) ||
						DAOInventario.getMarca_inventario().toLowerCase().contains(txtBuscador.getText().toLowerCase()) ||
						DAOInventario.getEspecificaciones_inventario().toLowerCase().contains(txtBuscador.getText().toLowerCase()) ){
					return true;
				}
					else{
						return false;
					}
				});
				tvInventario.refresh();
				tvInventario.setItems(listaBusqueda);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
