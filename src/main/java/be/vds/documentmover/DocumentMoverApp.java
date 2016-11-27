package be.vds.documentmover;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.vds.documentmover.ui.MainPane;
import be.vds.documentmover.utils.ConfigurationHelper;
import javafx.application.Application;
import javafx.scene.Scene;
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
		primaryStage.setScene(new Scene(mainPane, 800, 700));
		loadConfiguration();
		primaryStage.show();
	}

	private void loadConfiguration() {
		try {
			ConfigurationHelper.getInstance().loadConfig();

			mainPane.selectDestFile(new File(ConfigurationHelper.getInstance()
						.getDestinationFolder()));
			mainPane.selectSourceFile(new File(ConfigurationHelper.getInstance()
						.getSourceFolder()));

		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
	}
}
