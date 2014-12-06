/**
 * DifferenceTreeNode.java
 *
 * Creato il 13-mar-2006 17.50.30
 */
package dbmanager.tree;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Matteo Piccinini
 */
public class DifferenceTreeNode extends DefaultMutableTreeNode {

    /**
     * 
     */
    private static final long serialVersionUID = -7020451427071979161L;
    public static final int ADD = 1;
    public static final int DEL = 2;
    public static final int NONE = 0;
    public DifferenceTreeNode(int diffType, String nodeName){
//        if(diffType == ADD)
//            setUserObject(">>> " + nodeName);
//        else if(diffType == DEL)
//            setUserObject("<<< " + nodeName);
//        
//        else if(diffType == NONE)
            setUserObject(nodeName);
        this.diffType = diffType;
        this.nodeName=nodeName;
    }
    
    /**
     * @return Returns the diffType.
     */
    public int getDiffType() {
        return diffType;
    }
    
    public String getNodeName() {
        return nodeName;
    }
    
    private String nodeName;
    private int diffType;
}
