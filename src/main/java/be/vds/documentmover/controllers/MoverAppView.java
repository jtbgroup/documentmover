package be.vds.documentmover.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.vds.documentmover.ui.DocMoverFile;
import be.vds.documentmover.ui.FileTreeView;
import be.vds.documentmover.ui.PDFViewer;
import be.vds.documentmover.ui.PreferenceDialog;
import be.vds.documentmover.utils.ConfigurationHelper;
import be.vds.documentmover.utils.FileUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;

public class MoverAppView implements Initializable {
	private static final Logger LOG = LoggerFactory.getLogger(MoverAppView.class);
	@FXML
	private FileTreeView srcTreeView;
	@FXML
	private FileTreeView destTreeView;
	@FXML
	private TabPane tabPane;
	@FXML
	private DocMoveView actionPaneController;

	private PDFViewer pdfViewer;

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
					if (!newFile.isDirectory() && FileUtils.getExtension(newFile).equals("pdf")) {
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
				File newFile = newValue.getValue().getFile();
				if (newValue != null) {
						actionPaneController.registerDestFile(newFile);
				}
			}

		});
	}

	private void loadInitialValues() {
		try {
			ConfigurationHelper.getInstance().loadConfig();

			String dest = ConfigurationHelper.getInstance().getDestinationFolder();
			if (null != dest) {
				destTreeView.selectFile(new File(dest));
			}

			String src = ConfigurationHelper.getInstance().getSourceFolder();
			if (null != src) {
				srcTreeView.selectFile(new File(src));
			}

		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
	}

	private void openPDFFile(File file) {

		if (pdfViewer == null) {
			pdfViewer = new PDFViewer();
			Tab pdfTab = new Tab(file.getName());
			pdfTab.setContent(pdfViewer);
			tabPane.getTabs().add(pdfTab);
			tabPane.getSelectionModel().select(tabPane.getTabs().size() - 1);
		}

		pdfViewer.loadFile(file);
	}
	
	@FXML
	public void showPreferencesDialog(){
		PreferenceDialog prefDialog = new PreferenceDialog();
		prefDialog.show();
	}
}
