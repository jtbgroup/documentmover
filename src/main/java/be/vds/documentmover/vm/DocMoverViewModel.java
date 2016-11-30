package be.vds.documentmover.vm;

import java.io.File;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DocMoverViewModel {

	private final StringProperty destinationFolder = new SimpleStringProperty();
	private final StringProperty dtg = new SimpleStringProperty();
	private final StringProperty name = new SimpleStringProperty();
	private final StringProperty sender = new SimpleStringProperty();
	private final StringProperty extension = new SimpleStringProperty();
	private final ReadOnlyBooleanWrapper movePossible = new ReadOnlyBooleanWrapper();

	public DocMoverViewModel() {
		// Logic to check, whether the login is possible or not
		movePossible.bind(dtg.isNotEmpty().and(name.isNotEmpty()
				.and(extension.isNotEmpty().and(sender.isNotEmpty().and(destinationFolder.isNotEmpty())))));
	}

	public StringProperty destinationFolderProperty() {
		return destinationFolder;
	}

	public StringProperty dtgProperty() {
		return dtg;
	}

	public StringProperty senderProperty() {
		return sender;
	}

	public StringProperty nameProperty() {
		return name;
	}

	public StringProperty extensionProperty() {
		return extension;
	}

	public ReadOnlyBooleanProperty isLoginPossibleProperty() {
		return movePossible.getReadOnlyProperty();
	}

	public void loadFile(File file) {
		if(file.isDirectory()){
			destinationFolder.setValue(file.getAbsolutePath());
		}else{
			destinationFolder.setValue(file.getParent());
			String fileName = file.getName();
			name.setValue(fileName.substring(0,fileName.lastIndexOf(".")));
			extension.setValue(fileName.substring(fileName.lastIndexOf(".")+1));
		}
	}
}
