package be.vds.documentmover.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.vds.documentmover.ui.DocMoverFile;
import be.vds.documentmover.utils.FileUtils;
import be.vds.documentmover.utils.PreferencesHelper;
import be.vds.documentmover.vm.DocMoverViewModel;
import be.vds.documentmover.vm.PreferencesViewModel;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import np.com.ngopal.control.AutoFillTextBox;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;

public class ActionPaneController {
	private static final Logger LOG = LoggerFactory.getLogger(ActionPaneController.class);
	@FXML
	private TextField destinationFolderTF;
	@FXML
	private TextField dtgTF;
	@FXML
	private AutoFillTextBox<String> senderAfTf;
	@FXML
	private TextField nameTF;
	@FXML
	private TextField extensionTF;
	@FXML
	private Button moveBtn;
	@FXML
	private Button addSenderBtn;
	private DocMoverViewModel docMoverViewModel;
	private File sourceFile;
	private PreferencesViewModel preferencesViewModel = PreferencesViewModel.getInstance();
	private List<DocMoverListener> listeners=new ArrayList<DocMoverListener>();

	@FXML
	void initialize() {
		// Create the ViewModel - In production this should be done by
		// dependency injection
		docMoverViewModel = new DocMoverViewModel();
		// Connect the ViewModel
		destinationFolderTF.textProperty().bindBidirectional(docMoverViewModel.destinationFolderProperty());
		dtgTF.textProperty().bindBidirectional(docMoverViewModel.dtgProperty());
		// senderCb.textProperty().bindBidirectional(docMoverViewModel.senderProperty());
		senderAfTf.getTextbox().textProperty().bindBidirectional(docMoverViewModel.senderProperty());
		nameTF.textProperty().bindBidirectional(docMoverViewModel.nameProperty());
		extensionTF.textProperty().bindBidirectional(docMoverViewModel.extensionProperty());
		moveBtn.disableProperty().bind(docMoverViewModel.isLoginPossibleProperty().not());

		fillSenderComboBox(preferencesViewModel.itemsProperty());
		
		registerListeners();
	}

	private void registerListeners() {
		final EventHandler<KeyEvent> moveKeyEventHandler = new EventHandler<KeyEvent>() {
			public void handle(final KeyEvent keyEvent) {
				if (keyEvent.getCode() == KeyCode.ENTER ) {
					onMoveButtonPressed();
				}
			}
		};
		moveBtn.addEventHandler(KeyEvent.KEY_PRESSED, moveKeyEventHandler);
	}

	private void fillSenderComboBox(ObservableList<String> senderList) {
		senderAfTf.setData(senderList);
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
			try {
				FileUtils.moveFile(sourceFile, destFile);
				
				notifyListeners(sourceFile, destFile);
				
				Alert a = new Alert(AlertType.INFORMATION);
				alert.setTitle("Info");
				alert.setHeaderText(null);
				alert.setContentText("File has been moved");
				alert.show();
			} catch (IOException e1) {
				e1.printStackTrace();
				Alert a = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText(e1.getMessage());
				alert.show();
			}
		}
	}

	private void notifyListeners(File src, File dest) {
		for (DocMoverListener listener : listeners) {
			listener.docFileMoved(src, dest);
		}
	}
	
	public void addDocMoverListener(DocMoverListener docMoverListener){
		listeners.add(docMoverListener);
	}

	@FXML
	public void onAddSenderPressed() {
		String sender = senderAfTf.getText();
		LOG.debug("You are about to store sender " + sender);
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText(null);
		alert.setContentText("You are about to store sender " + sender);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {

			try {
				preferencesViewModel.itemsProperty().add(sender);
				preferencesViewModel.savePreferences();
				fillSenderComboBox(preferencesViewModel.itemsProperty());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
