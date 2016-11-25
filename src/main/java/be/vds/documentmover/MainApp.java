package be.vds.documentmover;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.vds.documentmover.ui.MainPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
	
public class MainApp extends Application {

    private static final Logger log = LoggerFactory.getLogger(MainApp.class);

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
    	 primaryStage.setTitle("Hello World!");
        
	         primaryStage.setScene(new Scene(new MainPane(), 300, 250));
         primaryStage.show();
    }
}
