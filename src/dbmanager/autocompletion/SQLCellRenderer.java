package dbmanager.autocompletion;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

public class SQLCellRenderer extends DefaultListCellRenderer{//JLabel implements ListCellRenderer {
     /**
     * 
     */
    private static final long serialVersionUID = -523762490307261956L;
    final static ImageIcon stringFunctionIcon = new ImageIcon("img/sf.gif");
     final static ImageIcon numericFunctionIcon = new ImageIcon("img/nf.gif");
     final static ImageIcon tableIcon = new ImageIcon("img/table.png");
     final static ImageIcon columnIcon = new ImageIcon("img/column.png");
     final static ImageIcon keywordIcon = new ImageIcon("img/keyword.gif");
     final static Color selected = new Color(79,137,255);

     // This is the only method defined by ListCellRenderer.
     // We just reconfigure the JLabel each time we're called.

	public SQLCellRenderer(SqlCompletionModel model){
		this.model = model;
	}

     public Component getListCellRendererComponent(
       JList list,
       Object value,            // value to display
       int index,               // cell index
       boolean isSelected,      // is the cell selected
       boolean cellHasFocus)    // the list and the cell have the focus
     {
		String s = value.toString();
		setText(s);
		setToolTipText(s);
		JLabel label =  (JLabel)super.getListCellRendererComponent(list, s, index, isSelected, cellHasFocus);
		label.setIcon(getIcon(s));
		label.setFont(list.getFont().deriveFont(Font.PLAIN));
		return label;
     }

	public Icon getIcon(String value){
		if (model.isStringFunction(value))return stringFunctionIcon;
		else if (model.isNumericFunction(value))return numericFunctionIcon;
		else if (model.isKeyword(value))return keywordIcon;
		else if (model.isTable(value))return tableIcon;
		else if (model.isColumn(value))return columnIcon;
		else return null;
	}

	private SqlCompletionModel model;
 }
