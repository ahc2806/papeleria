package controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ResourceBundle;
import com.sun.prism.impl.Disposer.Record;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import modelo.DAOJasperSoft;
import modelo.DAOReportes;

@SuppressWarnings("restriction")
public class ControladorReportes implements Initializable{

	@FXML TableView<DAOReportes> tvReportes;
	@FXML Button btnCancelar, btnRegresar, btnEliminar, btnAceptar, btnReporte;
	@FXML TextField txtTotal, txtBuscador;
	@FXML CheckBox ckbInactivos;
	@FXML DatePicker dpDesde, dpHasta;
	@SuppressWarnings("rawtypes")
	@FXML TableColumn columnaRestaurar = new TableColumn<>();
	@SuppressWarnings("rawtypes")
	@FXML TableColumn  tcTotal;
	
	private ObservableList<DAOReportes> lista;
	private DAOReportes reportes;
	private DAOJasperSoft jasper;
	private FilteredList<DAOReportes> listaBusqueda;
	private static final String estiloBoton = "-fx-background-color:-fx-shadow-highlight-color, -fx-outer-border, "
			+ "-fx-body-color, -fx-inner-border;"; 
	private static final String letraNegra= "-fx-text-fill: -fx-text-base-color;";
	private static final String estiloNormal ="-fx-background-color:transparent;";
	private static final String letraNormal= "-fx-text-fill: WHITE;";
	private Double total = (double) 0;
	
	public ControladorReportes() {
		reportes = new DAOReportes();
		jasper = new DAOJasperSoft();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
		//Formato de los Calendarios
		dpHasta.setDayCellFactory(dayCellFactory);
		dpDesde.setDayCellFactory(dayCellFactory);
		
		//Estilos de botones
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
		 
		 actualizarTabla();
		 btnReporte.setDisable(true);
		 btnEliminar.setDisable(true);
		 columnaRestaurar.setMinWidth(40);
		 columnaRestaurar.setMaxWidth(40);
		 txtTotal.setEditable(false);
	}
	
	public void actualizarTabla(){
		tvReportes.getItems().clear();
		lista=reportes.consultar("select * from ventas where estado_venta = true");
		tvReportes.setItems(lista);
		tvReportes.refresh();
		listaBusqueda = new FilteredList<DAOReportes>(lista);
		
		for(int i=0; tcTotal.getCellData(i) != null; i++) {
			total += Double.parseDouble((String) tcTotal.getCellData(i));
		}
		txtTotal.setText(Double.toString(total));
		total = (double) 0;
	}
	
	public void limpiar(){
		dpDesde.setValue(null);
		dpHasta.setValue(null);
		tvReportes.setDisable(false);
		txtBuscador.setDisable(false);
		txtBuscador.clear();
		ckbInactivos.setDisable(false);
	}
	
	@FXML public void clickTabla() {
		if(tvReportes.getSelectionModel().getSelectedItem()!= null){
			btnEliminar.setDisable(false);
			reportes = tvReportes.getSelectionModel().getSelectedItem();
			
			txtBuscador.clear();
			txtBuscador.setDisable(true);
			ckbInactivos.setDisable(true);
			dpHasta.setDisable(true);
			dpDesde.setDisable(true);
			btnAceptar.setDisable(true);
		}
	}
	
	@FXML public void clickEliminar() {
		if(reportes.getId_venta() > 0){
			reportes.eliminar();
			actualizarTabla();
		}
		else{
			ControladorNotificaciones.warnningAlert("ADVERTENCIA",null, "SELECCIONE UN REGISTRO DE LA TABLA.");
		}
		limpiar();
		btnEliminar.setDisable(true);
		ckbInactivos.setDisable(false);
		txtBuscador.setDisable(false);
		dpHasta.setDisable(false);
		dpDesde.setDisable(false);
		btnAceptar.setDisable(false);
	}
	
	@FXML public void clickCancelar() {
		actualizarTabla();
		limpiar();
		inactivosFalse();
		btnEliminar.setDisable(true);
		txtBuscador.setDisable(false);
		ckbInactivos.setDisable(false);
		ckbInactivos.setSelected(false);
		dpHasta.setDisable(false);
		dpDesde.setDisable(false);
		btnAceptar.setDisable(false);
		btnReporte.setDisable(true);
	}
	
	@FXML public void clickRegresar(){
		ControladorVentanas cv = ControladorVentanas.getInstancia();
		cv.cerrarAcceso();
	}
	
	@FXML public void clickAceptar() {
		if((dpHasta.getValue() != null) && (dpDesde.getValue() != null)) {
			tvReportes.getItems().clear();
			lista=reportes.consultar("select * from ventas "
					+ "where fecha_venta between '" + dpDesde.getValue()+ "'AND '"+dpHasta.getValue()+"' and estado_venta = true");
			tvReportes.setItems(lista);
			tvReportes.refresh();
			listaBusqueda = new FilteredList<DAOReportes>(lista);
			
			for(int i=0; tcTotal.getCellData(i) != null; i++) {
				total += Double.parseDouble((String) tcTotal.getCellData(i));
			}
			txtTotal.setText(Double.toString(total));
			total = (double) 0;
			btnReporte.setDisable(false);
			txtBuscador.setDisable(false);
		}
		else {
			ControladorNotificaciones.warnningAlert("INFORMACIÓN", "EL CAMPO -DESDE- Y -HASTA- NO DEBEN ESTAR VACÍOS.",
																								 "ESPECIFICAR CORRECTAMENTE LAS FECHAS PARA LA CONSULTA.");
		}
	}
	
	@FXML public void clickReporte() throws IOException {
		if((dpHasta.getValue() != null) && (dpDesde.getValue() != null)) {
			jasper.generarReporte(Date.valueOf(dpDesde.getValue()), Date.valueOf(dpHasta.getValue()), txtTotal.getText());
		}
	}
	
	@FXML public void clickFecha() {
		txtBuscador.setDisable(true);
		lista.clear();
		tvReportes.getItems().clear();
		tvReportes.refresh();
		txtTotal.clear();
		btnReporte.setDisable(true);
	}
	
	@SuppressWarnings("unchecked")
	@FXML public void clickInactivos(){
		txtBuscador.setDisable(false);
		tvReportes.setDisable(false);
		ckbInactivos.setDisable(false);
		dpHasta.setDisable(false);
		dpDesde.setDisable(false);
		btnAceptar.setDisable(false);
		actualizarTabla();
		limpiar();
		
		try{
			tvReportes.getItems().clear();
			if(ckbInactivos.isSelected()){
				dpHasta.setDisable(true);
				dpDesde.setDisable(true);
				btnAceptar.setDisable(true);
				txtTotal.setText("0.0");
				//Si está seleccionado se muestran los inactivos
				lista = reportes.consultar("select * from ventas where estado_venta = false");
				listaBusqueda =  new FilteredList<DAOReportes>(lista);

				//Agregar una columna al tableView para restaurar el dato inactivo
				tvReportes.getColumns().add(0, columnaRestaurar);
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
			tvReportes.setItems(lista); //Asignar la lista actualizada a la tabla
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
					reportes = (DAOReportes) BotonActivar.this.getTableView().getItems().get(BotonActivar.this.getIndex());
					if(reportes.reactivar()){
						lista = reportes.consultar("select * from ventas where estado_venta = false");
						tvReportes.setItems(lista);
						tvReportes.refresh();
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
			tvReportes.refresh();
			tvReportes.setItems(lista);
			ckbInactivos.setDisable(false);
			btnCancelar.setDisable(false);
		}
		else{
			try{
				btnCancelar.setDisable(true);
				ckbInactivos.setDisable(true);
				listaBusqueda.setPredicate(DAOVentas->{
					if(DAOVentas.getDescripcion_venta().toLowerCase().contains(txtBuscador.getText().toLowerCase()) ||
						DAOVentas.getVendedor_venta().toLowerCase().contains(txtBuscador.getText().toLowerCase()) ){
					return true;
				}
					else{
						return false;
					}
				});
				tvReportes.refresh();
				tvReportes.setItems(listaBusqueda);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void inactivosFalse() {
		if(columnaRestaurar.isVisible() == true){
			tvReportes.getColumns().remove(columnaRestaurar);
			tvReportes.getItems().clear();
			lista = reportes.consultar("select * from ventas where estado_venta = true");
			tvReportes.setItems(lista);
			tvReportes.refresh();
		}
		else {
			tvReportes.getItems().clear();
			lista = reportes.consultar("select * from ventas where estado_venta = true");
			tvReportes.setItems(lista);
			tvReportes.refresh();
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