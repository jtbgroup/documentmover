package be.vds.documentmover.ui;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class ActionPane extends GridPane {
	private static final Logger LOG = LoggerFactory.getLogger(ActionPane.class);
	private SelectionListener souceSelectionListener;
	private SelectionListener destSelectionListener;
	private TextField destFolderTf;
	private TextField fileNameTf;
	private TextField extensionTf;
	private TextField senderTf;
	private TextField dtgTf;
	private ActionManagerViewModel actionManager = new ActionManagerViewModel();

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
					actionManager.registerSourceFile(newValue.getValue().getFile());
				}
			}
		};
		return souceSelectionListener;
	}

	private void buildUI() {
		Label destLabel = new Label("Dest Folder");
		destFolderTf = new TextField();

		Label filenameLabel = new Label("File name");
		dtgTf = new TextField();
		dtgTf.setPrefColumnCount(8);
		dtgTf.setPrefWidth(60);
		Label label1 = new Label("-");
		senderTf = new TextField();
		Label label2 = new Label("-");
		fileNameTf = new TextField();
		Label label3 = new Label(".");
		extensionTf = new TextField();

		Button savePatternBtn = new Button("Save Pattern");
		Button srcNameBtn = new Button("Source Name");
		Button moveBtn = new Button("MOVE");

		GridPane.setConstraints(destLabel, 0, 0, 1, 1, HPos.RIGHT, VPos.BASELINE, Priority.NEVER, Priority.NEVER,
				Insets.EMPTY);
		GridPane.setConstraints(destFolderTf, 1, 0, 9, 1, HPos.LEFT, VPos.BASELINE, Priority.ALWAYS, Priority.NEVER,
				Insets.EMPTY);

		GridPane.setConstraints(filenameLabel, 0, 1, 1, 1, HPos.RIGHT, VPos.BASELINE, Priority.NEVER, Priority.NEVER,
				Insets.EMPTY);
		GridPane.setConstraints(dtgTf, 1, 1, 1, 1, HPos.LEFT, VPos.BASELINE, Priority.ALWAYS, Priority.NEVER,
				Insets.EMPTY);
		GridPane.setConstraints(label1, 2, 1, 1, 1, HPos.LEFT, VPos.BASELINE, Priority.NEVER, Priority.NEVER,
				Insets.EMPTY);
		GridPane.setConstraints(senderTf, 3, 1, 1, 1, HPos.LEFT, VPos.BASELINE, Priority.ALWAYS, Priority.NEVER,
				Insets.EMPTY);
		GridPane.setConstraints(label2, 4, 1, 1, 1, HPos.LEFT, VPos.BASELINE, Priority.NEVER, Priority.NEVER,
				Insets.EMPTY);
		GridPane.setConstraints(fileNameTf, 5, 1, 1, 1, HPos.LEFT, VPos.BASELINE, Priority.ALWAYS, Priority.NEVER,
				Insets.EMPTY);
		GridPane.setConstraints(label3, 6, 1, 1, 1, HPos.LEFT, VPos.BASELINE, Priority.NEVER, Priority.NEVER,
				Insets.EMPTY);
		GridPane.setConstraints(extensionTf, 7, 1, 1, 1, HPos.LEFT, VPos.BASELINE, Priority.ALWAYS, Priority.NEVER,
				Insets.EMPTY);

		GridPane.setConstraints(savePatternBtn, 8, 1, 1, 1, HPos.LEFT, VPos.BASELINE, Priority.NEVER, Priority.NEVER,
				Insets.EMPTY);
		GridPane.setConstraints(srcNameBtn, 9, 1, 1, 1, HPos.LEFT, VPos.BASELINE, Priority.NEVER, Priority.NEVER,
				Insets.EMPTY);
		GridPane.setConstraints(moveBtn, 1, 2, 10, 1, HPos.CENTER, VPos.BASELINE, Priority.NEVER, Priority.NEVER,
				Insets.EMPTY);

		this.getChildren().add(destLabel);
		this.getChildren().add(destFolderTf);
		this.getChildren().add(filenameLabel);
		this.getChildren().add(dtgTf);
		this.getChildren().add(label1);
		this.getChildren().add(senderTf);
		this.getChildren().add(label2);
		this.getChildren().add(fileNameTf);
		this.getChildren().add(label3);
		this.getChildren().add(extensionTf);
		this.getChildren().add(savePatternBtn);
		this.getChildren().add(srcNameBtn);
		this.getChildren().add(moveBtn);

		dtgTf.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<Event>(){
			@Override
			public void handle(Event event) {
				if (((KeyEvent)event).getCode().isLetterKey()) { /* Digits only */
					dtgTf.deletePreviousChar();
				}
				
				if(dtgTf.getText().length()>8){
					dtgTf.deletePreviousChar();
				}
			}
		});
		
		moveBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				moveFile();
			}
		});
	}

	public void registerAsSourceSelectionListener(SelectionObservable observable) {
		observable.addSelectionListener(souceSelectionListener);
	}

	public void registerAsDestSelectionListener(SelectionObservable observable) {
		observable.addSelectionListener(destSelectionListener);
	}

//	private void handleSourceChange(File file) {
//		sourceFile = file;
//		if (null != file && !file.isDirectory()
//				&& (fileNameTf.getText() == null || fileNameTf.getText().trim().length() == 0)) {
//			fileNameTf.setText(file.getName());
//		}
//	}

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

	private void moveFile() {
		System.out.println("move");
	}
}
