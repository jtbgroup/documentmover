package be.vds.documentmover;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.vds.documentmover.ui.MainPane;
import be.vds.documentmover.ui.PreferenceDialog;
import be.vds.documentmover.utils.ConfigurationHelper;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DocumentMoverApp extends Application {

	private static final Logger LOG = LoggerFactory.getLogger(DocumentMoverApp.class);

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	private MainPane mainPane;

	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Hello World!");
		mainPane = new MainPane();
		VBox vbox = new VBox();
		vbox.getChildren().add(createMenu());
		vbox.getChildren().add(mainPane);
		Scene scene = new Scene(vbox, 800, 700);
		primaryStage.setScene(scene);
		loadConfiguration();
		primaryStage.show();
	}

	private Node createMenu() {
		MenuBar menuBar = new MenuBar();
		// --- Menu File
		MenuItem preferencesMI = new MenuItem("Preferences");
		preferencesMI.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				PreferenceDialog prefDialog = new PreferenceDialog();
				prefDialog.show();
			}
		});

		Menu menuFile = new Menu("File");
		menuFile.getItems().addAll(preferencesMI);
		menuBar.getMenus().addAll(menuFile);
		return menuBar;
	}

	private void loadConfiguration() {
		try {
			ConfigurationHelper.getInstance().loadConfig();

			String dest = ConfigurationHelper.getInstance().getDestinationFolder();
			if (null != dest) {
				mainPane.selectDestFile(new File(dest));
			}

			String src = ConfigurationHelper.getInstance().getSourceFolder();
			if (null != src) {
				mainPane.selectSourceFile(new File(src));
			}

		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
	}
}
