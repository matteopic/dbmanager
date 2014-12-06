/**
 * AdvancedTree.java
 *
 * Creato il 11/set/06 12:49:37
 */
package dbmanager.tree;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author Matteo Piccinini
 */
public class AdvancedTree extends JTree {

    private static final long serialVersionUID = -1058794406239176167L;

    public AdvancedTree() {
        super();
    }

    /**
     * @param arg0
     */
    public AdvancedTree(Hashtable arg0) {
        super(arg0);
    }

    /**
     * @param value
     */
    public AdvancedTree(Object[] value) {
        super(value);
    }

    /**
     * @param newModel
     */
    public AdvancedTree(TreeModel newModel) {
        super(newModel);
    }

    /**
     * @param root
     * @param asksAllowsChildren
     */
    public AdvancedTree(TreeNode root, boolean asksAllowsChildren) {
        super(root, asksAllowsChildren);
    }

    /**
     * @param root
     */
    public AdvancedTree(TreeNode root) {
        super(root);
    }

    /**
     * @param arg0
     */
    public AdvancedTree(Vector arg0) {
        super(arg0);
    }
    

    public void expandAll() {
        TreeNode root = (TreeNode)getModel().getRoot();
        // Traverse tree from root
        expandAll(new TreePath(root), true);
    }

    public void collapseAll() {
        TreeNode root = (TreeNode)getModel().getRoot();
        // Traverse tree from root
        expandAll(new TreePath(root), false);
    }

    private void expandAll(TreePath parent, boolean expand) {
        // Traverse children
        TreeNode node = (TreeNode)parent.getLastPathComponent();
        if (node.getChildCount() >= 0) {
            for (Enumeration e=node.children(); e.hasMoreElements(); ) {
                TreeNode n = (TreeNode)e.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                expandAll(path, expand);
            }
        }
    
        // Expansion or collapse must be done bottom-up
        if (expand) {
            expandPath(parent);
        } else {
            collapsePath(parent);
        }
    }

}
