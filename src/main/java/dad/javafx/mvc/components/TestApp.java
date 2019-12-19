package dad.javafx.mvc.components;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TestApp extends Application {
	
	private DateChooser date;
	
	private Button consulta, iniciar;
	
	public static void main(String[] args) {
		launch(args);
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
	
		date = new DateChooser();
/*		
		ListSelector<String> listSelector = new ListSelector<String>();
		
		listSelector.setLeftTitle("Jugadores");
		listSelector.setRightTitle("Participantes");
		
		listSelector.getLeftItems().addAll("Perico","Palotes","Menganita","Fulanito");

		listSelector.getRightItems().addAll("Perico","Palotes","Menganita","Fulanito");
*/
		
		iniciar = new Button("Inicializar");
		iniciar.setOnAction(e -> onIniciarAction(e));
		
		consulta = new Button("Consultar");
		consulta.setOnAction(e -> onConsultaAction(e));
		
		
		HBox hbox = new HBox(5.0, iniciar, consulta);
		hbox.setAlignment(Pos.CENTER);
		
		VBox vbox = new VBox(5, date, hbox);
		vbox.setAlignment(Pos.CENTER);
	

		BorderPane root = new BorderPane();
		root.setCenter(vbox);
		
		Scene scene = new Scene(root, 320, 200);
		
		primaryStage.setTitle("Custom componentes test app");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	private void onConsultaAction(ActionEvent e) {
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Fecha");
		alert.setContentText("La fecha seleccionada es: "+date.getDateFormated());
		alert.showAndWait();
	}

	private void onIniciarAction(ActionEvent e) {
		
		date.inicializar();
		
	}
}
