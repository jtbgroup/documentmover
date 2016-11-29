package be.vds.documentmover.ui;

import java.io.File;

public class DocMoverFile {

	private File file;
	private String name;
	private boolean isRootDrive;

	public DocMoverFile(File file){
		this.file = file;
		this.isRootDrive = file.toPath().getNameCount() == 0;
	}
	
	public DocMoverFile(String name){
		this.name = name;
	}
	
	public File getFile() {
		return file;
	}
	
	@Override
	public String toString() {
		if(null == file){
			return name;
		}
		
		if(isRootDrive){
			return file.getAbsolutePath();
		}
		return file.getName();
	}
}
