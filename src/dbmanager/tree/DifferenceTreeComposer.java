/**
 * DifferenceTreeComposer.java
 *
 * Creato il 13-mar-2006 17.54.54
 */
package dbmanager.tree;

import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import dbmanager.tools.DifferenceResult;

/**
 *
 * @author Matteo Piccinini
 */
public class DifferenceTreeComposer {

    public DifferenceTreeComposer(){
        root = new DefaultMutableTreeNode("Database");
    }
    
    public void setDifferences(DifferenceResult result){
        setDifferences(root, result);
    }
    
    public void setDifferences(DefaultMutableTreeNode whereAppend, DifferenceResult result){
        Enumeration enums = result.elements();
        while (enums.hasMoreElements()){
            String tableName = (String)enums.nextElement();
            int diffType = result.getDiffType(tableName);
            DifferenceTreeNode node = generateNode(diffType, tableName);
            whereAppend.add(node);
            
            DifferenceResult sub = result.getSubDifferences(tableName); 
            if(sub != null)
                setDifferences(node, sub);
        }
    }

    public TreeModel getModel(){
        return new DefaultTreeModel(root);
    }

    private DifferenceTreeNode generateNode(int diffType, String text){
        DifferenceTreeNode node = null;
        switch (diffType) {
            case -1:
                node = new DifferenceTreeNode(DifferenceTreeNode.DEL, text);
                break;
            case 0:
                node = new DifferenceTreeNode(DifferenceTreeNode.NONE, text);
                break;
            case 1:
                node = new DifferenceTreeNode(DifferenceTreeNode.ADD, text);
                break;
        }
        return node;
    }
    
    private DefaultMutableTreeNode root;
}
