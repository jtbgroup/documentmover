package be.vds.documentmover.ui;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FileTreeView extends TreeView<DocMoverFile>  {
	public static Image computerImage = new Image("/images/computer.png");
	private FileTreeItem rootNode;

	public FileTreeView() {
		super();
		String hostName = "computer";
		try {
			hostName = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException x) {
		}
		rootNode = new FileTreeItem(new DocMoverFile(hostName));
		Iterable<Path> rootDirectories = FileSystems.getDefault().getRootDirectories();
		for (Path name : rootDirectories) {
			FileTreeItem treeNode = new FileTreeItem(new DocMoverFile(name.toFile()));
			rootNode.getChildren().add(treeNode);
		}
		this.setRoot(rootNode);
	}

	public void addChangeListener(ChangeListener<TreeItem<DocMoverFile>> changeListener) {
		this.getSelectionModel().selectedItemProperty().addListener(changeListener);
	}
	
	public void selectFile(File file) {
		List<File> files = new ArrayList<File>();
		File iter = file;
		files.add(iter);
		while (iter.getParentFile() != null) {
			iter = iter.getParentFile();
			files.add(iter);
		}

		TreeItem<DocMoverFile> parentItem = rootNode;
		for (int i = files.size() - 1; i > -1; i--) {
			parentItem = openNode(parentItem, files.get(i));
		}
	}

	private TreeItem<DocMoverFile> openNode(TreeItem<DocMoverFile> node, File file) {
		ObservableList<TreeItem<DocMoverFile>> list = node.getChildren();
		for (TreeItem<DocMoverFile> treeItem : list) {
			if (treeItem.getValue().getFile().equals(file)) {
				this.getSelectionModel().select(treeItem);
				treeItem.setExpanded(true);
				int idx = this.getSelectionModel().getSelectedIndex();
				this.scrollTo(idx);
				return treeItem;
			}
		}
		return null;
	}
}
