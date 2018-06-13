package view;
	
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {		
		URL fxml = getClass().getResource("/fxml/telaInicial.fxml");
		Parent parent = FXMLLoader.load(fxml);
		stage.setTitle("CaronAI");
		stage.setScene(new Scene(parent));
		stage.show();
	}

}