package be.vds.documentmover.ui;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class ActionPane extends GridPane {
	private static final Logger LOG = LoggerFactory.getLogger(ActionPane.class);
	private SelectionListener souceSelectionListener;
	private SelectionListener destSelectionListener;
	private TextField destFolderTf;
	private TextField fileNameTf;

	public ActionPane() {
		buildUI();
		createListeners();
	}

	private void createListeners() {
		souceSelectionListener = createSourceSelectionListener();
		destSelectionListener = createDestSelectionListener();
	}

	private SelectionListener createDestSelectionListener() {
		destSelectionListener = new SelectionListener() {

			@Override
			public void changed(ObservableValue<? extends TreeItem<DocMoverFile>> observable,
					TreeItem<DocMoverFile> oldValue, TreeItem<DocMoverFile> newValue) {
				if (null != newValue) {
					LOG.debug("Destination changed to " + newValue.getValue().getFile());
					handleDestinationChange(newValue.getValue().getFile());
				}
			}

		};
		return destSelectionListener;
	}

	private SelectionListener createSourceSelectionListener() {
		souceSelectionListener = new SelectionListener() {

			@Override
			public void changed(ObservableValue<? extends TreeItem<DocMoverFile>> observable,
					TreeItem<DocMoverFile> oldValue, TreeItem<DocMoverFile> newValue) {
				if (null != newValue) {
					LOG.debug("Source changed to " + newValue.getValue().getFile());
				}
			}
		};
		return souceSelectionListener;
	}

	private void buildUI() {
		Label destLabel = new Label("Dest Folder");
		Label filenameLabel = new Label("File name");
		destFolderTf = new TextField();
		fileNameTf = new TextField();
		Button savePatternBtn = new Button("Save Pattern");
		Button srcNameBtn = new Button("Source Name");
		Button moveBtn = new Button("MOVE");

		GridPane.setConstraints(destLabel, 0, 0, 1, 1, HPos.RIGHT, VPos.BASELINE, Priority.NEVER, Priority.NEVER,
				Insets.EMPTY);
		GridPane.setConstraints(destFolderTf, 1, 0, 3, 1, HPos.LEFT, VPos.BASELINE, Priority.ALWAYS, Priority.NEVER,
				Insets.EMPTY);

		GridPane.setConstraints(filenameLabel, 0, 1, 1, 1, HPos.RIGHT, VPos.BASELINE, Priority.NEVER, Priority.NEVER,
				Insets.EMPTY);
		GridPane.setConstraints(fileNameTf, 1, 1, 1, 1, HPos.LEFT, VPos.BASELINE, Priority.ALWAYS, Priority.NEVER,
				Insets.EMPTY);

		GridPane.setConstraints(savePatternBtn, 2, 1, 1, 1, HPos.LEFT, VPos.BASELINE, Priority.NEVER, Priority.NEVER,
				Insets.EMPTY);
		GridPane.setConstraints(srcNameBtn, 3, 1, 1, 1, HPos.LEFT, VPos.BASELINE, Priority.NEVER, Priority.NEVER,
				Insets.EMPTY);
		GridPane.setConstraints(moveBtn, 1, 2, 4, 1, HPos.CENTER, VPos.BASELINE, Priority.NEVER, Priority.NEVER,
				Insets.EMPTY);

		this.getChildren().add(destLabel);
		this.getChildren().add(destFolderTf);
		this.getChildren().add(filenameLabel);
		this.getChildren().add(fileNameTf);
		this.getChildren().add(savePatternBtn);
		this.getChildren().add(srcNameBtn);
		this.getChildren().add(moveBtn);
	}

	public void registerAsSourceSelectionListener(SelectionObservable observable) {
		observable.addSelectionListener(souceSelectionListener);
	}

	public void registerAsDestSelectionListener(SelectionObservable observable) {
		observable.addSelectionListener(destSelectionListener);
	}

	private void handleDestinationChange(File file) {
		String parentFolder = null;
		String fileName = null;
		if (file.isDirectory()) {
			parentFolder = file.getAbsolutePath();
		} else {
			parentFolder = file.getParentFile().getAbsolutePath();
			fileName = file.getName();
		}

		destFolderTf.setText(parentFolder);
		fileNameTf.setText(fileName);

	}

}
