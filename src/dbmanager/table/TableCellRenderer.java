package dbmanager.table;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author Matteo Piccinini
 */
public class TableCellRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = -5933413552717944359L;

    public TableCellRenderer(){
        grigio = new Color(238, 238, 238);
        bianco = Color.WHITE;
        grigioBlu = new Color(200,200,255);
        defaultColor = getForeground();
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
		if (value == null){
			value="(Null)";
			setForeground(grigioBlu);
		}else setForeground(defaultColor);

        if (row % 2 == 0)setBackground(grigio);
        else setBackground(bianco);
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }

    private Color bianco, grigio, grigioBlu, defaultColor;
}
