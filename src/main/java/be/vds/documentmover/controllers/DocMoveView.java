package be.vds.documentmover.controllers;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.org.apache.bcel.internal.classfile.SourceFile;

import be.vds.documentmover.utils.FileUtils;
import be.vds.documentmover.vm.DocMoverViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
	
	public void onMoveButtonPressed(){
		File destFile = docMoverViewModel.getDestFile();
		LOG.info("Moving file " + sourceFile+"\r\n   to "+ destFile);
//		try {
//			FileUtils.moveFile(sourceFile, destFile);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	public void registerSrcFile(File sourceFile) {
			this.sourceFile = sourceFile;
			if(!docMoverViewModel.isFileDetailLoaded()){
				docMoverViewModel.loadFile(sourceFile);
			}
	}

	public void registerDestFile(File destFile) {
		docMoverViewModel.loadFile(destFile);
	}

}
