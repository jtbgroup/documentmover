package be.vds.documentmover.controllers;

import java.io.File;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.vds.documentmover.vm.DocMoverViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class DocMoveView {
	private static final Logger LOG = LoggerFactory.getLogger(DocMoveView.class);
	@FXML
	private TextField destinationFolderTF;
	@FXML
	private TextField dtgTF;
	@FXML
	private TextField senderTF;
	@FXML
	private TextField nameTF;
	@FXML
	private TextField extensionTF;
	@FXML
	private Button moveBtn;
	private DocMoverViewModel docMoverViewModel;
	private File sourceFile;

	@FXML
	void initialize() {
		// Create the ViewModel - In production this should be done by
		// dependency injection
		docMoverViewModel = new DocMoverViewModel();
		// Connect the ViewModel
		destinationFolderTF.textProperty().bindBidirectional(docMoverViewModel.destinationFolderProperty());
		dtgTF.textProperty().bindBidirectional(docMoverViewModel.dtgProperty());
		senderTF.textProperty().bindBidirectional(docMoverViewModel.senderProperty());
		nameTF.textProperty().bindBidirectional(docMoverViewModel.nameProperty());
		extensionTF.textProperty().bindBidirectional(docMoverViewModel.extensionProperty());
		moveBtn.disableProperty().bind(docMoverViewModel.isLoginPossibleProperty().not());
	}

	@FXML
	public void onSourceButtonPressed() {
		docMoverViewModel.loadFile(sourceFile);
	}
	
	@FXML
	public void onMoveButtonPressed() {
		File destFile = docMoverViewModel.getDestFile();

		if (destFile.exists()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Warning");
			alert.setHeaderText(null);
			alert.setContentText("This File already exists. Not possible to replace an existing file.");

			alert.showAndWait();
			return;
		}

		LOG.info("Moving file " + sourceFile + "\r\n   to " + destFile);

		StringBuilder sb = new StringBuilder();
		sb.append("Moving file ");
		sb.append("\r\n  " + sourceFile.getAbsolutePath());
		sb.append("\r\n    to " + destFile.getAbsolutePath());
		sb.append("\r\nPlease Confirm");

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText(null);
		alert.setContentText(sb.toString());

		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
//			try {

//				FileUtils.moveFile(sourceFile, destFile);
				Alert a = new Alert(AlertType.INFORMATION);
				alert.setTitle("Info");
				alert.setHeaderText(null);
				alert.setContentText("File has been moved");
//			} catch (IOException e1) {
//				e1.printStackTrace();
//				Alert a = new Alert(AlertType.ERROR);
//				alert.setTitle("Error");
//				alert.setHeaderText(null);
//				alert.setContentText(e1.getMessage());
//			}
		}

	}

	public void registerSrcFile(File sourceFile) {
		this.sourceFile = sourceFile;
		if (!docMoverViewModel.isFileDetailLoaded()) {
			docMoverViewModel.loadFile(sourceFile);
		}
	}

	public void registerDestFile(File destFile) {
		docMoverViewModel.loadFile(destFile);
	}

}
