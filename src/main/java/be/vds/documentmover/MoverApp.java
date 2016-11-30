package be.vds.documentmover;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.vds.documentmover.ui.MainPane;
import be.vds.documentmover.ui.PreferenceDialog;
import be.vds.documentmover.utils.ConfigurationHelper;
import be.vds.documentmover.utils.ResourceManager;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MoverApp extends Application {

	private static final Logger LOG = LoggerFactory.getLogger(MoverApp.class);

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	private MainPane mainPane;

	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Hello World!");

		final URL url = ResourceManager.getInstance().getResourceAsURL("fxml/MoverAppView.fxml");
		final FXMLLoader fxmlLoader = new FXMLLoader(url);
		final Region root = (Region) fxmlLoader.load();

		Scene scene = new Scene(root, 800, 500);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

//	private Node createMenu() {
//		MenuBar menuBar = new MenuBar();
//		// --- Menu File
//		MenuItem preferencesMI = new MenuItem("Preferences");
//		preferencesMI.setOnAction(new EventHandler<ActionEvent>() {
//			public void handle(ActionEvent t) {
//				PreferenceDialog prefDialog = new PreferenceDialog();
//				prefDialog.show();
//			}
//		});
//
//		Menu menuFile = new Menu("File");
//		menuFile.getItems().addAll(preferencesMI);
//		menuBar.getMenus().addAll(menuFile);
//		return menuBar;
//	}

}
