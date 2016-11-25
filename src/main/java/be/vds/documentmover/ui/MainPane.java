package be.vds.documentmover.ui;

import be.vds.documentmover.utils.ResourceManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class MainPane extends StackPane {

	public MainPane() {
		buildUI();
	}

	private void buildUI() {
		SplitPane splitpane = createSplitPane();
		this.getChildren().add(splitpane);

	}

	private SplitPane createSplitPane() {
		        final SplitPane splitPane = new SplitPane();
		        splitPane.setId("hiddenSplitter");
		        StackPane leftPane = createLeftStackPane();
		        leftPane.getStyleClass().add("rounded");
		        Region region2 = new Region();
		        region2.getStyleClass().add("rounded");
		        Region region3 = new Region();
		        region3.getStyleClass().add("rounded");
		       
		        splitPane.getItems().addAll(leftPane, region2, region3);
		        splitPane.setDividerPositions(0.33, 0.66);
		        splitPane.getStylesheets().add("/styles/HiddenSplitPane.css");
		        splitPane.getStylesheets().add(ResourceManager.getInstance().getCssString("HiddenSplitPane.css"));
		        
		        return splitPane;
	}

	private StackPane createLeftStackPane() {
		Button btn = new Button();
		btn.setText("Say 'Hello World'");
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("Hello World!");
			}
		});

		StackPane sp = new StackPane();
		sp.getChildren().add(btn);
		return sp;
	}

}
