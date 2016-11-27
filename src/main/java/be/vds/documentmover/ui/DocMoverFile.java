package be.vds.documentmover.ui;

import java.io.File;

import javafx.scene.control.Label;

public class DocMoverFile {

	private File file;

	public DocMoverFile(File file){
		this.file = file;
	}
	
	public File getFile() {
		return file;
	}
	
	@Override
	public String toString() {
		return file.getName();
	}
}
