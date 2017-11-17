package be.vds.documentmover.ui;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.xml.internal.txw2.Document;

import be.vds.documentmover.controllers.MoverAppController;
import be.vds.documentmover.utils.FileUtils;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class FileTreeView extends TreeView<DocMoverFile> {
	private static final Logger LOG = LoggerFactory.getLogger(MoverAppController.class);
	public static Image computerImage = new Image("/images/computer.png");
	private FileTreeItem rootNode;

	public FileTreeView() {
		super();
		reloadRootNode();
		registerRefreshEvent();

	}
	
	public void reloadRootNode(){
		String hostName = "computer";
		try {
			hostName = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException x) {
			LOG.error(x.getMessage());
		}
		rootNode = new FileTreeItem(new DocMoverFile(hostName));
		Iterable<Path> rootDirectories = FileSystems.getDefault().getRootDirectories();
		for (Path name : rootDirectories) {
			FileTreeItem treeNode = new FileTreeItem(new DocMoverFile(name.toFile()));
			rootNode.getChildren().add(treeNode);
		}
		this.setRoot(rootNode);
	}

	private void registerRefreshEvent() {
		final EventHandler<KeyEvent> refreshKeyEventHandler = new EventHandler<KeyEvent>() {
			public void handle(final KeyEvent keyEvent) {
				TreeItem<DocMoverFile> selectedItem = getSelectionModel().getSelectedItem();
				File selectedFile = selectedItem.getValue().getFile();
				if (keyEvent.getCode() == KeyCode.F5 && selectedFile.isDirectory()) {
					refreshItem(selectedItem);
				}

				if (keyEvent.getCode() == KeyCode.DELETE && selectedFile.isFile()) {
					deleteFile(selectedItem);
				}
			}
		};
		this.addEventHandler(KeyEvent.KEY_PRESSED, refreshKeyEventHandler);
	}

	private void refreshItem(TreeItem<DocMoverFile> selectedItem) {
		FileTreeItem item = (FileTreeItem) selectedItem;
		item.refresh();
	}

	public void refreshSelectedNode() {
		TreeItem<DocMoverFile> itemToRefresh = this.getSelectionModel().getSelectedItem();
		if (null != itemToRefresh && itemToRefresh.isLeaf()) {
			itemToRefresh = itemToRefresh.getParent();
		}

		((FileTreeItem) itemToRefresh).refresh();
	}

	private void deleteFile(TreeItem<DocMoverFile> selectedItem) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText(null);
		alert.setContentText("You are about to delete file \r\n " + selectedItem.getValue().getFile());

		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			TreeItem<DocMoverFile> parent = selectedItem.getParent();
			FileUtils.deleteFile(selectedItem.getValue().getFile());
			FileTreeItem item = (FileTreeItem) parent;
			item.refresh();
			selectFile(parent.getValue().getFile());
		}
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
		if (null != node) {
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
		}
		return null;
	}
}
