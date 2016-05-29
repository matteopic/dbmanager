package dbmanager.tree;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import dbmanager.core.Catalog;
import dbmanager.core.Index;

public class DBTreeCellRenderer extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = 2756664005458023608L;

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,
			int row, boolean hasFocus) {

		if(value instanceof DefaultMutableTreeNode){
			value = ((DefaultMutableTreeNode)value).getUserObject();
		}
			
		if(value instanceof Catalog)
			value = ((Catalog)value).getName();
		else if(value instanceof Index)
			value = ((Index)value).getName();
//		else if(value instanceof Ta)
//			value = ((Catalog)value).getName();		
		
		return super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
	}
}
