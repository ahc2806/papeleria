package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import modelo.DAOUsuarios;

public class ControladorAvatares implements Initializable{
	
	@FXML Button btnCancelar, btnCerrar, btnFoto;
	@FXML ImageView perfil, a1, a2, a3, a4, a5;
	
	final private String foto_default = "file:src/vista/img/usuario2.png";
	private DAOUsuarios usuario;
	private ControladorVentanas instancia;
	private String foto_temp = "";
	
	public ControladorAvatares() {
		usuario =new DAOUsuarios();
		instancia=ControladorVentanas.getInstancia();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		usuario = (DAOUsuarios)instancia.getPrimaryStage().getUserData();
		Image imagen = new Image(usuario.getFoto_usuario());
		perfil.setImage(imagen);
	}
	
	@FXML public void clickElegir() {
		if(foto_temp != "") {
			Image imagen = new Image(foto_temp, 230, 230, true, true, true);
			perfil.setImage(imagen);
			usuario.setFoto_usuario(foto_temp);
			usuario.foto_de_perfil();
			ControladorNotificaciones.infoAlert("INFORMACIÓN", null, "CIERRE E INICIE SESIÓN NUEVAMENTE PARA QUE LOS CAMBIOS SURTAN EFECTO.");
			foto_temp = "";
		}
		else {
			ControladorNotificaciones.warnningAlert("ADVERTENCIA", null, "SELECCIONE UN AVATAR.");
		}
	}
	
	@FXML public void clickEliminar() {
		Image imagen = new Image(foto_default, 230, 230, true, true, true);
		perfil.setImage(imagen);
		usuario.setFoto_usuario(foto_default);
		usuario.foto_de_perfil();
		foto_temp = "";
	}
	
	@FXML public void clickCancelar() {
		foto_temp = "";
		Image imagen = new Image(usuario.getFoto_usuario(), 230, 230, true, true, true);
		perfil.setImage(imagen);
	}

	@FXML public void clickCerrar() {
		ControladorVentanas cv = ControladorVentanas.getInstancia();
		cv.cerrarAcceso2();
	}
	
	@FXML public void f1() {
		foto_temp = "file:src/vista/img/avatares/avatar%201.png";
		Image imagen = new Image(foto_temp, 230, 230, true, true, true);
		perfil.setImage(imagen);
	}
	
	@FXML public void f2() {
		foto_temp = "file:src/vista/img/avatares/avatar%202.png";
		Image imagen = new Image(foto_temp, 230, 230, true, true, true);
		perfil.setImage(imagen);
	}
	
	@FXML public void f3() {
		foto_temp = "file:src/vista/img/avatares/avatar%203.png";
		Image imagen = new Image(foto_temp, 230, 230, true, true, true);
		perfil.setImage(imagen);
	}
	
	@FXML public void f4() {
		foto_temp = "file:src/vista/img/avatares/avatar%204.png";
		Image imagen = new Image(foto_temp, 230, 230, true, true, true);
		perfil.setImage(imagen);
	}
	
	@FXML public void f5() {
		foto_temp = "file:src/vista/img/avatares/avatar%205.png";
		Image imagen = new Image(foto_temp, 230, 230, true, true, true);
		perfil.setImage(imagen);
	}
	
	@FXML public void f6() {
		foto_temp = "file:src/vista/img/avatares/avatar%206.png";
		Image imagen = new Image(foto_temp, 230, 230, true, true, true);
		perfil.setImage(imagen);
	}
	
	@FXML public void f7() {
		foto_temp = "file:src/vista/img/avatares/avatar%207.png";
		Image imagen = new Image(foto_temp, 230, 230, true, true, true);
		perfil.setImage(imagen);
	}
	
	@FXML public void f8() {
		foto_temp = "file:src/vista/img/avatares/avatar%208.png";
		Image imagen = new Image(foto_temp, 230, 230, true, true, true);
		perfil.setImage(imagen);
	}
	
	@FXML public void f9() {
		foto_temp = "file:src/vista/img/avatares/avatar%209.png";
		Image imagen = new Image(foto_temp, 230, 230, true, true, true);
		perfil.setImage(imagen);
	}
	
	@FXML public void f10() {
		foto_temp = "file:src/vista/img/avatares/avatar%2010.png";
		Image imagen = new Image(foto_temp, 230, 230, true, true, true);
		perfil.setImage(imagen);
	}
	
	@FXML public void f11() {
		foto_temp = "file:src/vista/img/avatares/avatar%2011.png";
		Image imagen = new Image(foto_temp, 230, 230, true, true, true);
		perfil.setImage(imagen);
	}
	
	@FXML public void f12() {
		foto_temp = "file:src/vista/img/avatares/avatar%2012.png";
		Image imagen = new Image(foto_temp, 230, 230, true, true, true);
		perfil.setImage(imagen);
	}
	
	@FXML public void f13() {
		foto_temp = "file:src/vista/img/avatares/avatar%2013.png";
		Image imagen = new Image(foto_temp, 230, 230, true, true, true);
		perfil.setImage(imagen);
	}
	
	@FXML public void f14() {
		foto_temp = "file:src/vista/img/avatares/avatar%2014.png";
		Image imagen = new Image(foto_temp, 230, 230, true, true, true);
		perfil.setImage(imagen);
	}
	
	@FXML public void f15() {
		foto_temp = "file:src/vista/img/avatares/avatar%2015.png";
		Image imagen = new Image(foto_temp, 230, 230, true, true, true);
		perfil.setImage(imagen);
	}
	
	@FXML public void f16() {
		foto_temp = "file:src/vista/img/avatares/avatar%2016.png";
		Image imagen = new Image(foto_temp, 230, 230, true, true, true);
		perfil.setImage(imagen);
	}
	
	@FXML public void f17() {
		foto_temp = "file:src/vista/img/avatares/avatar%2017.png";
		Image imagen = new Image(foto_temp, 230, 230, true, true, true);
		perfil.setImage(imagen);
	}
	
	@FXML public void f18() {
		foto_temp = "file:src/vista/img/avatares/avatar%2018.png";
		Image imagen = new Image(foto_temp, 230, 230, true, true, true);
		perfil.setImage(imagen);
	}
	
	@FXML public void f19() {
		foto_temp = "file:src/vista/img/avatares/avatar%2019.png";
		Image imagen = new Image(foto_temp, 230, 230, true, true, true);
		perfil.setImage(imagen);
	}
	
	@FXML public void f20() {
		foto_temp = "file:src/vista/img/avatares/avatar%2020.png";
		Image imagen = new Image(foto_temp, 230, 230, true, true, true);
		perfil.setImage(imagen);
	}
	
	@FXML public void f21() {
		foto_temp = "file:src/vista/img/avatares/avatar%2021.png";
		Image imagen = new Image(foto_temp, 230, 230, true, true, true);
		perfil.setImage(imagen);
	}
	
	@FXML public void f22() {
		foto_temp = "file:src/vista/img/avatares/avatar%2022.png";
		Image imagen = new Image(foto_temp, 230, 230, true, true, true);
		perfil.setImage(imagen);
	}
	
	@FXML public void f23() {
		foto_temp = "file:src/vista/img/avatares/avatar%2023.png";
		Image imagen = new Image(foto_temp, 230, 230, true, true, true);
		perfil.setImage(imagen);
	}
	
	@FXML public void f24() {
		foto_temp = "file:src/vista/img/avatares/avatar%2024.png";
		Image imagen = new Image(foto_temp, 230, 230, true, true, true);
		perfil.setImage(imagen);
	}
	
	@FXML public void f25() {
		foto_temp = "file:src/vista/img/avatares/avatar%2025.png";
		Image imagen = new Image(foto_temp, 230, 230, true, true, true);
		perfil.setImage(imagen);
	}
	
	@FXML public void f26() {
		foto_temp = "file:src/vista/img/avatares/avatar%2026.jpg";
		Image imagen = new Image(foto_temp, 230, 230, true, true, true);
		perfil.setImage(imagen);
	}
	
	@FXML public void f27() {
		foto_temp = "file:src/vista/img/avatares/avatar%2027.jpg";
		Image imagen = new Image(foto_temp, 230, 230, true, true, true);
		perfil.setImage(imagen);
	}
	
	@FXML public void f28() {
		foto_temp = "file:src/vista/img/avatares/avatar%2028.png";
		Image imagen = new Image(foto_temp, 230, 230, true, true, true);
		perfil.setImage(imagen);
	}
	
	@FXML public void f29() {
		foto_temp = "file:src/vista/img/avatares/avatar%2029.png";
		Image imagen = new Image(foto_temp, 230, 230, true, true, true);
		perfil.setImage(imagen);
	}
	
	@FXML public void f30() {
		foto_temp = "file:src/vista/img/avatares/avatar%2030.png";
		Image imagen = new Image(foto_temp, 230, 230, true, true, true);
		perfil.setImage(imagen);
	}
	
	/*try{
			FileChooser fileChooser=new FileChooser();
			FileChooser.ExtensionFilter extension=new FileChooser.ExtensionFilter("Image Files (*.jpg, *.png", "*.png", "*.jpg");
			fileChooser.getExtensionFilters().add(extension);
			File file=fileChooser.showOpenDialog(new Stage());
			if(file != null) {
				if(file.isFile() && (file.getName().contains(".jpg") || file.getName().contains(".png"))) {
					String direccion = file.toURI().toURL().toString();
					System.out.print(direccion);
					Image imagen=new Image (direccion, 230, 230, true, true, true);
					perfil.setImage(imagen);
				}
			}
		}
		catch (MalformedURLException e){
			Logger.getLogger(ControladorPerfil.class.getName()).log(Level.SEVERE, null, e);
		}
	 */
}
