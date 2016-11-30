package be.vds.documentmover.ui;

import java.io.File;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ActionManagerViewModel {

	private final StringProperty dtg = new SimpleStringProperty();

	public void registerSourceFile(File sourceFile) {
		// this.sourceFile = sourceFile;
		// extension = FileUtils.getExtension(sourceFile);
	}

	public ActionManagerViewModel() {
	}

	public StringProperty dtgProperty() {
		return dtg;
	}


}
