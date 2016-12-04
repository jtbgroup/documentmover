package be.vds.documentmover.ui;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import be.vds.documentmover.utils.ConfigurationHelper;
import be.vds.documentmover.utils.ResourceManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class PreferenceDialog extends Dialog {

	private TextField destFolderTf;
	private TextField srcFolderTf;

	public PreferenceDialog() {
		
		final URL url = ResourceManager.getInstance().getResourceAsURL("fxml/PreferencesDlg.fxml");
		final FXMLLoader fxmlLoader = new FXMLLoader(url);
		Region root;
		try {
			root = (Region) fxmlLoader.load();
//			Scene scene = new Scene(root, 1500, 1000);
			this.getDialogPane().setContent(root);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
//		buildUI();
//		loadConfig();
	}

	private void loadConfig() {
		String dest = ConfigurationHelper.getInstance().getDestinationFolder();
		if (null != dest) {
			destFolderTf.setText(dest);
		}

		String src = ConfigurationHelper.getInstance().getSourceFolder();
		if (null != src) {
			srcFolderTf.setText(src);
		}
	}

	private void buildUI() {
		GridPane grid = new GridPane();
		grid.setHgap(5);
		grid.setVgap(5);

		Label destLabel = new Label("Dest Folder");
		Label filenameLabel = new Label("Src folder");
		destFolderTf = new TextField();
		srcFolderTf = new TextField();

		GridPane.setConstraints(destLabel, 0, 0, 1, 1, HPos.RIGHT, VPos.BASELINE, Priority.NEVER, Priority.NEVER,
				Insets.EMPTY);
		GridPane.setConstraints(destFolderTf, 1, 0, 1, 1, HPos.LEFT, VPos.BASELINE, Priority.ALWAYS, Priority.NEVER,
				Insets.EMPTY);

		GridPane.setConstraints(filenameLabel, 0, 1, 1, 1, HPos.RIGHT, VPos.BASELINE, Priority.NEVER, Priority.NEVER,
				Insets.EMPTY);
		GridPane.setConstraints(srcFolderTf, 1, 1, 1, 1, HPos.LEFT, VPos.BASELINE, Priority.ALWAYS, Priority.NEVER,
				Insets.EMPTY);

		grid.getChildren().add(destLabel);
		grid.getChildren().add(destFolderTf);
		grid.getChildren().add(filenameLabel);
		grid.getChildren().add(srcFolderTf);

		this.getDialogPane().setContent(grid);
		// this.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
		// boolean saveBtn =
		// this.getDialogPane().getButtonTypes().add(ButtonType.YES);

		this.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
		this.getDialogPane().getButtonTypes().add(ButtonType.APPLY);
		Button saveButton = (Button) this.getDialogPane().lookupButton(ButtonType.APPLY);
		saveButton.setText("Save");
		saveButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				savePreferences();
			}

		});

	}

	private void savePreferences() {
		try {
			ConfigurationHelper.getInstance().setDestinationFolder(destFolderTf.getText());
			ConfigurationHelper.getInstance().setSourceFolder(srcFolderTf.getText());

			ConfigurationHelper.getInstance().saveConfig();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
