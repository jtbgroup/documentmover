package be.vds.documentmover.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import be.vds.documentmover.utils.ConfigurationHelper;
import be.vds.documentmover.utils.PreferencesHelper;
import be.vds.documentmover.vm.PreferencesViewModel;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PreferencesController implements Initializable{

	@FXML
	public TextField srcFolderTf;
	
	@FXML
	public TextField destFolderTf;

	@FXML
	public ListView<String> senderList;
	
	private PreferencesViewModel preferences;

	private Stage stage;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
			preferences = PreferencesViewModel.getInstance();
			destFolderTf.textProperty().bindBidirectional(preferences.detinationFolderProperty());
			srcFolderTf.textProperty().bindBidirectional(preferences.sourceFolderProperty());
			
			senderList.setItems(preferences.itemsProperty());
	}
	
	
	@FXML
	public void onSavePreferences(){
		try {
			PreferencesHelper.savePreferences(preferences);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void onCancel(){
		stage.close();
	}


	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
}
