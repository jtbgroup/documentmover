package be.vds.documentmover;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.vds.documentmover.controllers.MoverAppController;
import be.vds.documentmover.utils.ResourceManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class MoverApp extends Application {

	private static final Logger LOG = LoggerFactory.getLogger(MoverApp.class);

	public static void main(String[] args) throws Exception {
		launch(args);
	}


	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Document mover");

		final URL url = ResourceManager.getInstance().getResourceAsURL("fxml/MoverApp.fxml");
		final FXMLLoader fxmlLoader = new FXMLLoader(url);
		final Region root = (Region) fxmlLoader.load();
		MoverAppController controller = fxmlLoader.getController();
		controller.setStage(primaryStage);

		Scene scene = new Scene(root, 1500, 1000);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	

}
