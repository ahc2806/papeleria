package controlador;

import controlador.ControladorVentanas;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	private ControladorVentanas ventanas;

	@Override
	public void start(Stage primaryStage) throws Exception {
		ventanas = ControladorVentanas.getInstancia();
		ventanas.setPrimaryStage(primaryStage);
		ventanas.asignarModal("../vista/login.fxml", "INICIAR SESI�N");
		ventanas.noMaximizar();
	}

	public static void main(String[] args) {
		launch(args);
	}
}