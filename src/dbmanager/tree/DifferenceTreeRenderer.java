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

import dbmanager.gui.OverlayIcon;

/**
 *
 * @author Matteo Piccinini
 */
public class DifferenceTreeRenderer extends DefaultTreeCellRenderer {

    private static final long serialVersionUID = -2923449797473556742L;
    
    
    public DifferenceTreeRenderer(){
    	loadIcons();
    }

    private void loadIcons() {
    	tableIcon = new OverlayIcon(new ImageIcon("img/table.png"));
    	columnIcon = new OverlayIcon(new ImageIcon("img/column.png"));
        out = new ImageIcon("img/overlay_16_outgo.png");
    	in = new ImageIcon("img/overlay_16_incom.png");
	}

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
    	String name = value.getNodeName();
    	String key = value.getDifferenceKey();
    	String txt = key != null ? key + " = " + name : name;
        JLabel label = (JLabel)super.getTreeCellRendererComponent(tree, txt, selected, expanded, leaf, row, hasFocus);
//        String txt = label.getText();
//        if (txt == null)
//            return label;

        OverlayIcon ico = null;
        int level = value.getLevel();
        switch (level) {
		case 0:
			break;

		case 1:
			ico = tableIcon;
			label.setIcon(ico);
			break;

		case 2:
			ico = columnIcon;
			label.setIcon(ico);
			break;

		default:
			break;
		}

        if(ico != null)ico.clearOverlays();

        int diffType = value.getDiffType();
        switch (diffType) {
            case DifferenceTreeNode.ADD:
            	if(ico != null)
            		ico.addOverlay(out);
            	else
            		label.setIcon(out);
                break;

            case DifferenceTreeNode.DEL:
            	if(ico != null)
            		ico.addOverlay(in);
            	else
            		label.setIcon(in);
                break;

            //case DifferenceTreeNode.NONE:            
            default:
                break;
        }
        return label;    
    }
    

    
    private OverlayIcon tableIcon, columnIcon, fieldIcon;
    private Icon in, out;
}

