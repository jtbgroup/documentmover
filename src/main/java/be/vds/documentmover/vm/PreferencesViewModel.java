package be.vds.documentmover.vm;

import java.io.IOException;

import be.vds.documentmover.utils.PreferencesHelper;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PreferencesViewModel {

	private StringProperty sourceFolder = new SimpleStringProperty();
	private StringProperty destinationFolder = new SimpleStringProperty();
	private ObservableList<String> items = FXCollections.observableArrayList();
	private ObjectProperty<String> selectedItem = new SimpleObjectProperty<String>();

	private static PreferencesViewModel instance;

	private PreferencesViewModel() {
	}

	public static PreferencesViewModel getInstance() {
		if (instance == null) {
			try {
				instance = new PreferencesViewModel();
				PreferencesHelper.loadPreferences(instance);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return instance;
	}

	public StringProperty sourceFolderProperty() {
		return sourceFolder;
	}

	public StringProperty detinationFolderProperty() {
		return destinationFolder;
	}

	public ObservableList<String> itemsProperty() {
		return items;
	}

	public ObjectProperty<String> selectedItemProperty() {
		return selectedItem;
	}

	public void savePreferences() throws IOException {
		PreferencesHelper.savePreferences(this);
	}
}
