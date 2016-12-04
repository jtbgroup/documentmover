package be.vds.documentmover.ui;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FileTreeItem extends TreeItem<DocMoverFile> {

	public static Image folderCollapseImage = new Image("images/folder.png");
	public static Image folderExpandImage = new Image("images/folder-open.png");
	public static Image fileImage = new Image("/images/text-x-generic.png");
	public static Image computerImage = new Image("/images/computer.png");

	private boolean isLeaf;
	private boolean isFirstTimeChildren = true;
	private boolean isFirstTimeLeaf = true;

	public FileTreeItem(DocMoverFile f) {
		super(f);
		decorate(f);
	}

	private TreeItem<DocMoverFile> createNode(DocMoverFile f) {
		FileTreeItem tip = new FileTreeItem(f);
		return tip;
	}

	private void decorate(DocMoverFile f) {
		// test if this is a directory and set the icon
		if (f.getFile() == null) {
			this.setGraphic(new ImageView(computerImage));
		} else if (Files.isDirectory(f.getFile().toPath())) {
			this.setGraphic(new ImageView(folderCollapseImage));
		} else {
			this.setGraphic(new ImageView(fileImage));
			// if you want different icons for different file types this is
			// where you'd do it
		}

		// set the value
		setValue(f);
	}

	@Override
	public ObservableList<TreeItem<DocMoverFile>> getChildren() {
		if (isFirstTimeChildren) {
			isFirstTimeChildren = false;
			super.getChildren().setAll(buildChildren(this));
		}
		return super.getChildren();
	}

	@Override
	public boolean isLeaf() {
		if (isFirstTimeLeaf) {
			isFirstTimeLeaf = false;
			if(getValue().getFile() == null){
				isLeaf=false;
			}else{
				isLeaf = getValue().getFile().isFile();
			}
		}
		return isLeaf;
	}

	private ObservableList<TreeItem<DocMoverFile>> buildChildren(TreeItem<DocMoverFile> treeItem) {
		DocMoverFile f = treeItem.getValue();
		if (f == null || f.getFile() == null) {
			return FXCollections.emptyObservableList();
		}
		if (f.getFile().isFile()) {
			return FXCollections.emptyObservableList();
		}
		File[] files = f.getFile().listFiles();
		if (files != null) {
			ObservableList<TreeItem<DocMoverFile>> children = FXCollections.observableArrayList();

			Arrays.sort(files);

			for (File childFile : files) {
				children.add(createNode(new DocMoverFile(childFile)));
			}
			return children;
		}
		return FXCollections.emptyObservableList();
	}

	public void refresh() {
		getChildren().clear();
		isFirstTimeChildren = true;
		getChildren();
	}

}
