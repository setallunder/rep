package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import view.SnakesWindowController;

public class Main extends Application {
	private Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) {
		System.out.println("Begin\n");
		try {
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("Snake");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		windowInit();
	}
	
	public void windowInit(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../view/SnakesWindow.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			
	        primaryStage.setScene(new Scene(root, 600, 400));
	        primaryStage.show();
	        
	        SnakesWindowController controller = loader.getController();
	        controller.setMain(this);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
