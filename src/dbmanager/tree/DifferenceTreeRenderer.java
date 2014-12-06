/**
 * DifferenceTreeRenderer.java
 *
 * Creato il 30/ago/06 10:19:33
 */
package dbmanager.tree;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author Matteo Piccinini
 */
public class DifferenceTreeRenderer extends DefaultTreeCellRenderer {

    private static final long serialVersionUID = -2923449797473556742L;

    public Component getTreeCellRendererComponent(JTree tree, Object value,
               boolean selected, boolean expanded,
               boolean leaf, int row, boolean hasFocus){
//        System.out.println(value + " " + value.getClass().getName());
        
        if (value instanceof DifferenceTreeNode)
            return getTreeCellRendererComponent(tree, (DifferenceTreeNode)value, selected, expanded, leaf, row, hasFocus);
        else
            return super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
    }
    
    public Component getTreeCellRendererComponent(JTree tree, DifferenceTreeNode value,
            boolean selected, boolean expanded,
            boolean leaf, int row, boolean hasFocus){
        JLabel label = (JLabel)super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
        String txt = label.getText();
        if (txt == null)
            return label;
        
        int diffType = value.getDiffType();
        switch (diffType) {
            case DifferenceTreeNode.ADD:
                label.setIcon(getOutIcon());
                break;

            case DifferenceTreeNode.DEL:
                label.setIcon(getInIcon());
                break;

            //case DifferenceTreeNode.NONE:            
            default:
                break;
        }
        return label;    
    }
    
    private Icon getOutIcon(){
        if (out == null)out = new ImageIcon("img/outgo_synch.gif");
        return out;
    }
    
    private Icon getInIcon(){
        if (in == null)in = new ImageIcon("img/incom_synch.gif");
        return in;
    }
    
    private Icon in, out;
}
