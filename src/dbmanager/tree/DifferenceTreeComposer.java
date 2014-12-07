/**
 * DifferenceTreeComposer.java
 *
 * Creato il 13-mar-2006 17.54.54
 */
package dbmanager.tree;

import java.util.Enumeration;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import dbmanager.tools.DifferenceResult;

/**
 *
 * @author Matteo Piccinini
 */
public class DifferenceTreeComposer{

    public DifferenceTreeComposer(){
        root = new DefaultMutableTreeNode("Database");
    }

    public void setDifferences(DifferenceResult result){
        setDifferences(root, null, result);
    }

    public void setDifferences(DefaultMutableTreeNode whereAppend, String key, DifferenceResult result){
        Enumeration<String> enums = result.elements();
        while (enums.hasMoreElements()){
            String differenceKey = (String)enums.nextElement();
            int diffType = result.getDiffType(differenceKey);
            DifferenceTreeNode node = generateNode(diffType, differenceKey);
            node.setDifferenceKey(key);
            whereAppend.add(node);

            DifferenceResult sub = result.getSubDifferences(differenceKey); 
            if(sub != null)
                setDifferences(node, null, sub);
        }
    	Set<String> keys = result.getSubKeys();
    	for(String subKey : keys){
    		DifferenceResult sub = result.getSubDifferences(subKey);
    		setDifferences(whereAppend, subKey, sub);
    	}
    }

//    private void traverse(DifferenceResult result){
//    	Set<String> keys = result.getSubKeys();
//    	for(String key : keys){
//    		DifferenceResult sub = result.getSubDifferences(key);
//    	}
//    		
//    }

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
