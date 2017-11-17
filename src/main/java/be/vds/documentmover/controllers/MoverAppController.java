package be.vds.documentmover.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.vds.documentmover.MoverApp;
import be.vds.documentmover.ui.DocMoverFile;
import be.vds.documentmover.ui.FileTreeView;
import be.vds.documentmover.ui.PDFViewer;
import be.vds.documentmover.utils.ConfigurationHelper;
import be.vds.documentmover.utils.FileUtils;
import be.vds.documentmover.utils.ResourceManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MoverAppController implements Initializable {
	private static final Logger LOG = LoggerFactory.getLogger(MoverAppController.class);
	@FXML
	private FileTreeView srcTreeView;
	@FXML
	private FileTreeView destTreeView;
	@FXML
	private TabPane tabPane;
	@FXML
	private ActionPaneController actionPaneController;

	private PDFViewer pdfViewer;
	private Stage stage;
	private Tab pdfTab;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		registerListeners();
		loadInitialValues();
	}

	private void registerListeners() {
		srcTreeView.addChangeListener(new ChangeListener<TreeItem<DocMoverFile>>() {
			@Override
			public void changed(ObservableValue<? extends TreeItem<DocMoverFile>> observable,
					TreeItem<DocMoverFile> oldValue, TreeItem<DocMoverFile> newValue) {
				if (newValue != null) {
					File newFile = newValue.getValue().getFile();
					if (newFile != null && !newFile.isDirectory() && FileUtils.getExtension(newFile).equals("pdf")) {
						openPDFFile(newFile);
						actionPaneController.registerSrcFile(newFile);
					}
				}
			}
		});

		destTreeView.addChangeListener(new ChangeListener<TreeItem<DocMoverFile>>() {

			@Override
			public void changed(ObservableValue<? extends TreeItem<DocMoverFile>> observable,
					TreeItem<DocMoverFile> oldValue, TreeItem<DocMoverFile> newValue) {
				if (newValue != null) {
					File newFile = newValue.getValue().getFile();
					if (newValue != null) {
						actionPaneController.registerDestFile(newFile);
					}
				}
			}
		});

		actionPaneController.addDocMoverListener(new DocMoverListener() {
			@Override
			public void docFileMoved(File src, File dest) {
				destTreeView.refreshSelectedNode();
				srcTreeView.refreshSelectedNode();
			}
		});
	}

	private void loadInitialValues() {
		try {
			ConfigurationHelper.getInstance().loadConfig();

			loadDestInitialValue();

			String src = ConfigurationHelper.getInstance().getSourceFolder();
			if (null != src) {
				srcTreeView.selectFile(new File(src));
			}

		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
	}

	private void loadDestInitialValue() {
		String dest = ConfigurationHelper.getInstance().getDestinationFolder();
		if (null != dest) {
			destTreeView.selectFile(new File(dest));
		}
	}

	private void openPDFFile(File file) {

		if (pdfViewer == null) {
			pdfViewer = new PDFViewer();
			pdfTab = new Tab();
			pdfTab.setContent(pdfViewer);
			tabPane.getTabs().add(pdfTab);
			tabPane.getSelectionModel().select(tabPane.getTabs().size() - 1);
		}

		pdfTab.setText(file.getName());
		pdfViewer.loadFile(file);
	}

	@FXML
	public void showPreferencesDialog() {
		try {
			final URL url = ResourceManager.getInstance().getResourceAsURL("fxml/PreferencesDlg.fxml");
			FXMLLoader loader = new FXMLLoader(url);
			Region page = loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Person");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(stage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			dialogStage.setHeight(500);
			dialogStage.setWidth(500);

			PreferencesController controller = loader.getController();
			controller.setStage(dialogStage);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@FXML
	public void onReloadDestPressed() {
		destTreeView.reloadRootNode();
		loadDestInitialValue();
	}
}
