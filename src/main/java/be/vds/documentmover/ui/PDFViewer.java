package be.vds.documentmover.ui;

import java.io.File;

import org.jpedal.examples.viewer.Commands;
import org.jpedal.examples.viewer.OpenViewerFX;

import javafx.scene.layout.StackPane;

public class PDFViewer extends StackPane {

	private OpenViewerFX viewer;

	public PDFViewer(File file) {
		buildUI();
		setFile(file);
	}

	private void setFile(File file) {
		Object[] input = { file.getAbsolutePath() };// Or you could try creating
		// a new file object with
		// same path? Who knows,
		// worth a try?
		viewer.executeCommand(Commands.OPENFILE, input);
	}

	private void buildUI() {
		viewer = new OpenViewerFX(this, null);
//		viewer.getSwingGUI().enableDownloadBar(false, false);
		viewer.setupViewer();
	}
}
