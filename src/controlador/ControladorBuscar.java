package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import modelo.DAOInventario;

public class ControladorBuscar implements Initializable{
	
	@FXML Button btnAceptar, btnCerrar;
	@FXML TableView <DAOInventario> tvInventario;
	@FXML TextField txtBuscador;
	@FXML TableColumn <DAOInventario, String> tcCodigo;
	
	private DAOInventario inventario;
	private ObservableList<DAOInventario> lista;
	private FilteredList<DAOInventario> listaBusqueda;
	public static String codigo;
	
	public ControladorBuscar() {
		inventario = new DAOInventario();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnAceptar.setDisable(true);
		actualizarTabla();
	}
	
	@FXML public void clickTabla() {
		if(tvInventario.getSelectionModel().getSelectedItem() != null) {
			btnAceptar.setDisable(false);
		}
	}
	
	@FXML public void clickAceptar() {
		if(tvInventario.getSelectionModel().getSelectedItem() != null) {
			codigo = tcCodigo.getCellData(tvInventario.getSelectionModel().getSelectedItem());
			ControladorVentanas cv = ControladorVentanas.getInstancia();
			cv.cerrarAcceso2();
		}
		else {
			ControladorNotificaciones.warnningAlert("ADVERTENCIA", null, "NO HA SELECCIONADO NINGÚN PRODUCTO.");
		}
	}
	
	public void actualizarTabla(){
		tvInventario.getItems().clear();
		lista=inventario.consultar("select * from inventario where estado_inventario = true");
		tvInventario.setItems(lista);
		tvInventario.refresh();
		listaBusqueda = new FilteredList<DAOInventario>(lista);
	}

	@FXML public void textChange_busqueda(){
		if(txtBuscador.getText().trim().isEmpty()){
			tvInventario.refresh();
			tvInventario.setItems(lista);
		}
		else{
			try{
				listaBusqueda.setPredicate(DAOInventario->{
					if(DAOInventario.getNombre_inventario().toLowerCase().contains(txtBuscador.getText().toLowerCase()) ||
						DAOInventario.getCodigo_inventario().toLowerCase().contains(txtBuscador.getText().toLowerCase()) ||
						DAOInventario.getSeccion_inventario().toLowerCase().contains(txtBuscador.getText().toLowerCase()) ||
						DAOInventario.getTipo_inventario().toLowerCase().contains(txtBuscador.getText().toLowerCase()) ||
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
	
	@FXML public void clickCerrar(){
		ControladorVentanas cv = ControladorVentanas.getInstancia();
		cv.cerrarAcceso2();
	}
}
