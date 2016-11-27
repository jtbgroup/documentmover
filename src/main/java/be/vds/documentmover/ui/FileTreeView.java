package be.vds.documentmover.ui;

import java.io.File;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class FileTreeView extends TreeView<DocMoverFile> implements SelectionObservable{

	public FileTreeView(String rootFile) {
		super();
		this.setRoot(new FileTreeItem(new DocMoverFile(new File(rootFile))));
	}

	public void addSelectionListener(SelectionListener selectionListener) {
		this.getSelectionModel().selectedItemProperty().addListener(selectionListener);
	}

	public void selectFile(File file) {
		String[] names = file.getAbsolutePath().split(File.separator);
		for (int i = 0; i < names.length; i++) {
			openNode( this.getRoot(), names, i);
		}
	}

	private void openNode(TreeItem<DocMoverFile> node, String[] names, int i) {
		ObservableList<TreeItem<DocMoverFile>> list = node.getChildren();
		for (TreeItem<DocMoverFile> treeItem : list) {
			if(treeItem.getValue().getFile().getName().equals(names[i])){
				this.getSelectionModel().select(treeItem);
				int idx = this.getSelectionModel().getSelectedIndex();
				this.scrollTo(idx);
				if(i+1 < names.length){
					openNode(treeItem, names, i+1);
				}else{
					treeItem.setExpanded(true);;
				}
			}
		}
		
	}

}
