package be.vds.documentmover.ui;

import java.io.File;

import org.jpedal.examples.viewer.Commands;
import org.jpedal.examples.viewer.OpenViewerFX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.vds.documentmover.utils.FileUtils;
import be.vds.documentmover.utils.ResourceManager;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainPane extends StackPane {
	private static final Logger LOG = LoggerFactory.getLogger(MainPane.class);
	private ActionPane actionPane;
	private FileTreeView ftvDest;
	private FileTreeView ftvSource;
	private TabPane tabPane;

	public MainPane() {
		buildUI();
	}

	private void buildUI() {
		SplitPane splitpane = createSplitPane();
		Region actionsRegion = createActionsPane();

		VBox mainBox = new VBox();
		mainBox.getChildren().add(splitpane);
		mainBox.getChildren().add(actionsRegion);

		this.getChildren().add(mainBox);

		registerListeners();
	}

	private void registerListeners() {
		actionPane.registerAsSourceSelectionListener(ftvSource);
		actionPane.registerAsDestSelectionListener(ftvDest);
		ftvSource.addSelectionListener(new SelectionListener() {

			@Override
			public void changed(ObservableValue<? extends TreeItem<DocMoverFile>> observable,
					TreeItem<DocMoverFile> oldValue, TreeItem<DocMoverFile> newValue) {
				if (newValue != null && !newValue.getValue().getFile().isDirectory()
						&& FileUtils.getExtension(newValue.getValue().getFile()).equals("pdf")) {
					LOG.debug(newValue.getValue().toString());
					openNewTab(newValue.getValue().getFile());
				}
			}
		});
	}

	private Region createActionsPane() {
		actionPane = new ActionPane();
		return actionPane;
	}

	private SplitPane createSplitPane() {
		final SplitPane splitPane = new SplitPane();
		splitPane.setId("hiddenSplitter");
		Region leftPane = createLeftStackPane();
		leftPane.getStyleClass().add("rounded");

		Region region2 = createViewer();
		region2.getStyleClass().add("rounded");

		splitPane.getItems().addAll(leftPane, region2);
		splitPane.setDividerPositions(0.33);
		splitPane.getStylesheets().add(ResourceManager.getInstance().getCssString("HiddenSplitPane.css"));

		return splitPane;
	}

	private Region createViewer() {
		Tab tab = new Tab("Welcome");
		tab.setContent(new Label("Welcome in the Document Mover application"));

		tabPane = new TabPane();
		tabPane.getTabs().add(tab);
		tabPane.setTabClosingPolicy(TabClosingPolicy.ALL_TABS);

		return tabPane;
	}

	private Region createLeftStackPane() {
		// SOURCE
		StackPane sourcePane = new StackPane();
		Label sourceLabel = new Label("Source");
		ftvSource = new FileTreeView("/");
		VBox box = new VBox(5, sourceLabel, ftvSource);
		sourcePane.getChildren().add(box);
		sourcePane.getStyleClass().add("rounded");

		// DEST
		StackPane destPane = new StackPane();
		Label destLabel = new Label("Destination");
		ftvDest = new FileTreeView("/");
		VBox box2 = new VBox(5, destLabel, ftvDest);
		destPane.getChildren().add(box2);
		destPane.getStyleClass().add("rounded");

		SplitPane split = new SplitPane();
		split.getStylesheets().add("/styles/HiddenSplitPane.css");
		split.setOrientation(Orientation.VERTICAL);
		split.setDividerPositions(0.5);
		split.getItems().addAll(sourcePane, destPane);

		return split;
	}

	private void openNewTab(File file) {

		StackPane pdfPane = new StackPane();
		// OpenViewerFX pdfViewer = new OpenViewerFX(pdfPane, null);
		// pdfViewer.openDefaultFile(file.getAbsolutePath());

		OpenViewerFX viewer = new OpenViewerFX(pdfPane, null);
		viewer.getSwingGUI().enableDownloadBar(false, false);
		viewer.setupViewer();

		Object[] input = { file.getAbsolutePath() };// Or you could try creating
													// a new file object with
													// same path? Who knows,
													// worth a try?
		viewer.executeCommand(Commands.OPENFILE, input);

		Tab tab = new Tab(file.getName());
		tab.setContent(pdfPane);

		tabPane.getTabs().add(tab);
		tabPane.getSelectionModel().select(tabPane.getTabs().size() - 1);
	}

	public void selectSourceFile(File file) {
		ftvSource.selectFile(file);
	}

	public void selectDestFile(File file) {
		ftvDest.selectFile(file);
	}

}
