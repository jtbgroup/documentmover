package be.vds.documentmover.controllers;

import java.io.File;

import be.vds.documentmover.vm.DocMoverViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class DocMoveView {

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
	
	public void onLoginButtonPressed(){
//		System.out.println(loginViewModel.passwordProperty());
	}

	public void registerSourceFile(File file) {
		docMoverViewModel.loadFile(file);
	}

}
