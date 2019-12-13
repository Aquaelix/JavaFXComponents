package dad.javafx.mvc.components;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TestApp extends Application {
	
	public static void main(String[] args) {
		launch(args);
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		ListSelector<String> listSelector = new ListSelector<String>();
		
		listSelector.setLeftTitle("Jugadores");
		listSelector.setRightTitle("Participantes");
		
		listSelector.getLeftItems().addAll("Perico","Palotes","Menganita","Fulanito");

//		listSelector.getRightItems().addAll("Perico","Palotes","Menganita","Fulanito");

		BorderPane root = new BorderPane();
		root.setCenter(listSelector);
		
		Scene scene = new Scene(root, 320, 200);
		
		primaryStage.setTitle("Custom componentes test app");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
}
