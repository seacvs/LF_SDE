package proyecto.LF_SDE;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Simon extends Application{
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = FXMLLoader.load(getClass().getResource("simonGF.fxml"));		
		Scene scene = new Scene(root);			
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Simon Dice y Evalua");
		primaryStage.show();		
	}
	
	
	

}
