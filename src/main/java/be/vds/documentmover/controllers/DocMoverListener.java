package be.vds.documentmover.controllers;

import java.io.File;

public interface DocMoverListener {

	public void docFileMoved(File src, File dest);
}
