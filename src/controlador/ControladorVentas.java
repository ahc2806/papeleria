 package controlador;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.StageStyle;
import modelo.DAOInventario;
import modelo.DAOUsuarios;
import modelo.DAOVentas;

public class ControladorVentas implements Initializable{
	
	@FXML TableView<DAOVentas> tvVentas;
	@FXML Button btnAgregar, btnCancelar, btnRegresar, btnEliminar, btnBuscar, btnMas, btnMenos, btnCobrar, btnAceptar, btnCancelar2;
	@FXML TextField txtCodigo,txtCantidad, txtTotal, txtPago, txtTotal2, txtCambio;;
	@FXML TableColumn<DAOVentas, String> tcCantidad, tcImporte, tcDescripcion, tcTotal;
	
	private DAOInventario producto;
	private DAOVentas venta;
	private DAOUsuarios usuario;
	private ObservableList <DAOVentas> lista;
	private ControladorVentanas instancia;
	private static final String estiloBoton = "-fx-background-color:-fx-shadow-highlight-color, -fx-outer-border, "
			+ "-fx-body-color, -fx-inner-border;"; 
	private static final String letraNegra= "-fx-text-fill: -fx-text-base-color;";
	private static final String estiloNormal ="-fx-background-color:transparent;";
	private static final String letraNormal= "-fx-text-fill: WHITE;";
	private int masmenos = 0;
	private Date date = new Date();
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public ControladorVentas() {
		producto = new DAOInventario();
		instancia=ControladorVentanas.getInstancia();
		venta = new DAOVentas(null, null, null, null);
		usuario = new DAOUsuarios();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		usuario = (DAOUsuarios)instancia.getPrimaryStage().getUserData();
		txtCantidad.setText("1");
		txtTotal.setEditable(false);
		lista = FXCollections.observableArrayList();
		//tvVentas.setPlaceholder(new Label("El TableView no contiene datos."));       //Modifica el texto de la tabla cuando no tiene datos
		//tvVentas.setTableMenuButtonVisible(true);     //Crea boton que permite Visualizar o no las columnas
		
		//Restringir caracteres
		txtCodigo.textProperty().addListener((observable, oldValue, newValue)->{
			//Se asigna al valor anterior
			if(!newValue.matches("[a-zA-Z0-9  -]{0,15}") || newValue.length()>15){
				((StringProperty) observable).setValue(oldValue);
			}else{
				//Se asigna el nuevo valor, porque si coincide con la expresión
				((StringProperty)observable).setValue(newValue);
			}
		});
		
		txtCantidad.textProperty().addListener((observable, oldValue, newValue)->{
			//Se asigna al valor anterior
			if(!newValue.matches("[0-9]{0,4}") || newValue.length()>4){
				((StringProperty) observable).setValue(oldValue);
			}else{
				//Se asigna el nuevo valor, porque si coincide con la expresión
				((StringProperty)observable).setValue(newValue);
			}
		});
		
		//Funcionalidad con teclas
		btnMas.setOnKeyReleased(new EventHandler<KeyEvent>() 
	    { 
	     @Override 
	     public void handle(KeyEvent ke) { 
	      if (ke.getCode().equals(KeyCode.UP)){ 
	    	 btnEliminar.setDisable(true);
	  		masmenos = Integer.parseInt(txtCantidad.getText()) + 1;
	  		txtCantidad.setText(Integer.toString(masmenos));
	  		masmenos = 0;
	      } 
	     } 
	    }); 
		
		//Estilos para los botones
	     btnAgregar.setOnMouseEntered(e -> btnAgregar.setStyle(estiloBoton)); 
		 btnAgregar.setOnMouseEntered(e -> btnAgregar.setStyle(letraNegra)); 
		 btnAgregar.setOnMouseExited(e -> btnAgregar.setStyle(letraNormal)); 
		 btnAgregar.setOnMouseExited(e -> btnAgregar.setStyle(estiloNormal));
		 
		 btnCancelar.setOnMouseEntered(e -> btnCancelar.setStyle(estiloBoton)); 
		 btnCancelar.setOnMouseEntered(e -> btnCancelar.setStyle(letraNegra)); 
		 btnCancelar.setOnMouseExited(e -> btnCancelar.setStyle(letraNormal)); 
		 btnCancelar.setOnMouseExited(e -> btnCancelar.setStyle(estiloNormal));
		 
		 btnEliminar.setOnMouseEntered(e -> btnEliminar.setStyle(estiloBoton)); 
		 btnEliminar.setOnMouseEntered(e -> btnEliminar.setStyle(letraNegra)); 
		 btnEliminar.setOnMouseExited(e -> btnEliminar.setStyle(letraNormal)); 
		 btnEliminar.setOnMouseExited(e -> btnEliminar.setStyle(estiloNormal));
		 
		 btnRegresar.setOnMouseEntered(e -> btnRegresar.setStyle(estiloBoton)); 
		 btnRegresar.setOnMouseEntered(e -> btnRegresar.setStyle(letraNegra)); 
		 btnRegresar.setOnMouseExited(e -> btnRegresar.setStyle(letraNormal)); 
		 btnRegresar.setOnMouseExited(e -> btnRegresar.setStyle(estiloNormal)); 
		 
		 //Inicializar las columnas 
		 tcDescripcion.setCellValueFactory(new PropertyValueFactory<DAOVentas, String> ("descripcion"));
		 tcCantidad.setCellValueFactory(new PropertyValueFactory<DAOVentas, String> ("cantidad"));
		 tcImporte.setCellValueFactory(new PropertyValueFactory<DAOVentas, String> ("importe"));
		 tcTotal.setCellValueFactory(new PropertyValueFactory<DAOVentas, String> ("subtotal"));
		 
		 btnEliminar.setDisable(true);
	}
	
	public void actualizarTabla() {
		int cantidad = producto.consultarCantidad("select cantidad_inventario from inventario "
											+ "where codigo_inventario = '"+txtCodigo.getText()+"' and estado_inventario = true");
		if(Integer.parseInt(txtCantidad.getText()) > cantidad) {
			ControladorNotificaciones.warnningAlert("ADVERTENCIA",null, "LA CANTIDAD DE ARTÍCULOS INGRESADOS "
																										+ "SUPERA AL TOTAL DE ARTÍCULOS DISPONIBLES.");
		}
		else {
			int id = producto.consultarId("select id_inventario from inventario "
												+ "where codigo_inventario = '"+txtCodigo.getText()+"' and estado_inventario = true");
			String descrip = producto.consultarNombre("select nombre_inventario from inventario "
					+ "where codigo_inventario = '"+txtCodigo.getText()+"' and estado_inventario = true");
			String precio = producto.consultarPrecio("select precio_inventario from inventario "
					+ "where codigo_inventario = '"+txtCodigo.getText()+"' and estado_inventario = true");
			
			cantidad =  cantidad - Integer.parseInt(txtCantidad.getText());
			producto.setCantidad_inventario(cantidad);
			producto.setId_inventario(id);
			//producto.dismAlmac();
	
			String total = Double.toString((Double.parseDouble(precio))*(Integer.parseInt(txtCantidad.getText())));
			//Se llena la tabla.
			venta = new DAOVentas (descrip, txtCantidad.getText() , precio, total);
			
			
			//Avisos de Faltantes
			if(cantidad == 0) {
				ControladorNotificaciones.errorAlert("IMPORTANTE", descrip,"SE HA AGOTADO PARA FUTURAS VENTAS.");
				producto.setId_inventario(id);
				producto.eliminar();
			}
			else if(cantidad == 1) {
				ControladorNotificaciones.warnningAlert("ADVERTENCIA", descrip, "SÓLO QUEDA -1- ARTÍCULO DISPONIBLE.");
			}
			else if(cantidad == 2) {
				ControladorNotificaciones.warnningAlert("ADVERTENCIA", descrip,"SÓLO QUEDAN -2- ARTÍCULOS DISPONIBLES.");
			}
			else if(cantidad == 3){
				ControladorNotificaciones.warnningAlert("ADVERTENCIA", descrip, "SÓLO QUEDAN -3- ARTÍCULOS DISPONIBLES.");
			}
			
			//Llena el total.
			txtTotal.setText(Double.toString( (Double.parseDouble(txtTotal.getText() ) ) + (Double.parseDouble(total) ) ) );
			
			lista.add(venta);
			tvVentas.setItems (lista);
			tvVentas.refresh();
			txtCodigo.clear();
			txtCantidad.setText("1");
		}
	}
	
	public void limpiar() {
		lista.clear();
		tvVentas.getItems().clear();
		txtTotal.setText("0");
		txtCantidad.setText("1");
		txtCodigo.clear();
		masmenos = 0;
	}
	
	public static boolean isNumeric(String cadena) {
        boolean resultado;
        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }
        return resultado;
    }
	
	@FXML public void clickCobrar() {
		try {
			if(Double.parseDouble(txtTotal.getText()) !=  0) {
				
				Alert confirm = new Alert(AlertType.CONFIRMATION);
				confirm.setTitle("CONFIRMACIÓN");
				confirm.setHeaderText("ESQUEMA DE COBRO.");
				confirm.setContentText("SELECCIONE UNA OPCIÓN.");
				confirm.initStyle(StageStyle.UTILITY);
				
				ButtonType efectivo = new ButtonType("Efectivo");
				ButtonType credito = new ButtonType("Crédito");
				ButtonType descuento = new ButtonType("Descuento");
				ButtonType cancelar = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
				
				confirm.getButtonTypes().setAll(efectivo, credito, descuento, cancelar);
	
				Optional<ButtonType> result = confirm.showAndWait();
				if (result.get() == efectivo){
					
					TextInputDialog dialog = new TextInputDialog();
					dialog.setTitle("EFECTIVO");
					dialog.setHeaderText(null);
					dialog.setContentText("INGRESE EL EFECTIVO DEL CLIENTE.");
					dialog.initStyle(StageStyle.UTILITY);
	
					// Manera tipica de ibtener el valor responsivo del textfield
					Optional<String> resultado;
					Boolean cobro = false;
					do {
						resultado = dialog.showAndWait();
						if (resultado.isPresent()){
							if((resultado.get() != null) && (resultado.get().length() > 0)) {
								 if (isNumeric(resultado.get()) != true) {
							    	 do {
							    		 ControladorNotificaciones.errorAlert("ERROR", "CARACTERES NO VÁLIDOS.", "INGRESE UNA CANTIDAD NUMÉRICA VÁLIDA.");
							    		 resultado = dialog.showAndWait();
							    	 } while (isNumeric(resultado.get()) != true);
							     }
								 if(Double.parseDouble(resultado.get()) >= Double.parseDouble(txtTotal.getText())) {
									 Double dinero = Double.parseDouble(resultado.get());
									 Double cambio = dinero - Double.parseDouble(txtTotal.getText());
								 	 ControladorNotificaciones.infoAlert("VENTA","EFECTIVO: $ "+dinero+"\n"
																																	+ "TOTAL: $ "+txtTotal.getText()+"\n"
																																	+ "CAMBIO: $ "+cambio, null);
								 	 for(int i = 0; tcDescripcion.getCellData(i) != null; i++) {
										 venta.setDescripcion_venta(tcDescripcion.getCellData(i));
										 venta.setCantidad_venta(tcCantidad.getCellData(i));
										 venta.setImporte_venta(tcImporte.getCellData(i));
										 venta.setSubtotal_venta(tcTotal.getCellData(i));
										 venta.setFecha_venta(java.sql.Date.valueOf(dateFormat.format(date)));
										 venta.setVendedor_venta(usuario.getNombre_usuario().toUpperCase() + " " + usuario.getApeP_usuario().toUpperCase());
										 venta.agregar();
								 	 }
								 	 cobro = true;
								 	 limpiar();
									 btnEliminar.setDisable(true);
								 }
								 else {
									 ControladorNotificaciones.warnningAlert("ADVERTENCIA",null, "EL EFECTIVO RECIBIDO ES MENOR AL TOTAL A PAGAR.");
									 cobro = false;
								 }
							}
							else {
								ControladorNotificaciones.warnningAlert("ADVERTENCIA", null, "NO HA INTRODUCIDO NINGUNA CANTIDAD.");
								cobro = false;
							}
						}
						else {
							cobro = true;
						}
					} while (cobro != true);
				}
				
				else if (result.get() == credito) {
					ControladorNotificaciones.infoAlert("INFORMACIÓN", "POR EL MOMENTO NO ESTÁ DISPONIBLE.", 
																									"SEGUIMOS TRABAJANDO EN SU FUNCIONALIDAD, DISCULPE LAS MOLESTIAS.");
				}
				else if (result.get() == descuento) {
					ControladorNotificaciones.infoAlert("INFORMACIÓN", "POR EL MOMENTO NO ESTÁ DISPONIBLE.", 
																									"SEGUIMOS TRABAJANDO EN SU FUNCIONALIDAD, DISCULPE LAS MOLESTIAS.");
				}
			}
			else {
				ControladorNotificaciones.warnningAlert("ADVERTENCIA",null, "NO HA REGISTRADO NINGUNA VENTA.");
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@FXML public void clickTabla(){
		if(tvVentas.getSelectionModel().getSelectedItem()!= null){
			btnEliminar.setDisable(false);
		}
	}
	
	@FXML public void clickAgregar() {
		btnEliminar.setDisable(true);
		try {
			if(txtCodigo.getText().isEmpty() || txtCantidad.getText().isEmpty()) {
				ControladorNotificaciones.warnningAlert("ADVERTENCIA",null, "EXISTEN CAMPOS VACÍOS");
			}
			else {
				if(Integer.parseInt(txtCantidad.getText()) > 0) {
					producto.setCodigo_inventario(txtCodigo.getText());
					DAOInventario temp = producto.validarProducto();
					if(temp!=null) { //Valida si el producto está registrado o disponible
						actualizarTabla();
						masmenos = 0;
					}
					else { //Valida si el producto está disponible o registrado
						ControladorNotificaciones.errorAlert("ERROR",null, "PRODUCTO INEXISTENTE O AGOTADO.");
						txtCodigo.clear();
						txtCantidad.setText("1");
						masmenos = 0;
					}
				}
				else {
					ControladorNotificaciones.warnningAlert("ADVERTENCIA",null, "LA CANTIDAD DEBE SER -MAYOR- A -CERO-");
					masmenos = 0;
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@FXML public void clickCancelar() {
		btnEliminar.setDisable(true);
		if(tvVentas.getItems() != null) {
			for(int i= 0; tcDescripcion.getCellData(i) != null; i++) {
				int id  = producto.consultarId("select id_inventario from inventario "
																		+ "where nombre_inventario = '"+tcDescripcion.getCellData(i)+
																		"' and estado_inventario = true");
				int cantidad = Integer.parseInt(tcCantidad.getCellData(i)) +
																				producto.consultarCantidad("select cantidad_inventario from inventario "
																			+ "where id_inventario = '"+id+"' and estado_inventario = true");
				producto.setCantidad_inventario(cantidad);
				producto.setId_inventario(id);
				producto.dismAlmac();
			}
		}
		limpiar();
	}
	
	@FXML public void clickEliminar() {
		if(tvVentas.getSelectionModel().getSelectedItem() != null) {
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("CONFIRMACIÓN");
			alert.setHeaderText(null);
			alert.setContentText("¿ESTÁ SEGURO QUE DESEA ELIMINAR?");
			alert.initStyle(StageStyle.UTILITY);
			Optional<ButtonType> result = alert.showAndWait();
			
			if (result.get() == ButtonType.OK) {
				int id  = producto.consultarId("select id_inventario from inventario "
																			+ "where nombre_inventario = '"+tcDescripcion.getCellData(tvVentas.getSelectionModel().getSelectedItem())+
																			"' and estado_inventario = true");
				int cantidad = Integer.parseInt(tcCantidad.getCellData(tvVentas.getSelectionModel().getSelectedItem())) +
											producto.consultarCantidad("select cantidad_inventario from inventario "
											+ "where id_inventario = '"+id+"' and estado_inventario = true");
				Double total = Double.parseDouble(txtTotal.getText()) - Double.parseDouble(tcTotal.getCellData(tvVentas.getSelectionModel().getSelectedItem()));
				txtTotal.setText(Double.toString(total));
				producto.setCantidad_inventario(cantidad);
				producto.setId_inventario(id);
				producto.dismAlmac();
				tvVentas.getItems().remove(tvVentas.getSelectionModel().getSelectedItem());
				txtCantidad.setText("1");
				txtCodigo.clear();
			}
			btnEliminar.setDisable(true);
			masmenos = 0;
		}
		else {
			ControladorNotificaciones.warnningAlert("ADVERTENCIA",null, "SELECCIONA UN PRODUCTO DE LA TABLA");
		}
	}
	
	@FXML public void clickRegresar() {
		ControladorVentanas cv = ControladorVentanas.getInstancia();
		cv.cerrarAcceso();
	}
	
	@FXML public void clickMas() {
		btnEliminar.setDisable(true);
		masmenos = Integer.parseInt(txtCantidad.getText()) + 1;
		txtCantidad.setText(Integer.toString(masmenos));
		masmenos = 0;
	}
	
	@FXML public void clickMenos() {
		btnEliminar.setDisable(true);
		masmenos = Integer.parseInt(txtCantidad.getText()) - 1;
		txtCantidad.setText(Integer.toString(masmenos));
		masmenos = 0;
	}

	@FXML public void clickBuscar() {
		instancia.asignarModal2("../vista/buscar.fxml", "LISTA DE PRODUCTOS.");
		instancia.noMaximizar2();
	}
}