/* Sviluppato da Matteo Piccinini */

package dbmanager.table;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import dbmanager.gui.ButtonColumnTableModel;
import dbmanager.gui.ResultSetTableModel;

public class TabellaResultSet extends JTable{

    private static final long serialVersionUID = 3786619763898189809L;

    public TabellaResultSet(ResultSet rs)throws SQLException{
        this(new ResultSetTableModel(rs));
    }
    
    public TabellaResultSet(ResultSetTableModel model)throws SQLException{
        super(new ButtonColumnTableModel(model));
        
        TableColumn col = getColumn(getColumnName(0));
        
//      javax.swing.table.TableCellRenderer xxx = getTableHeader().getDefaultRenderer();
      col.setCellRenderer(new JButtonRenderer());
//      col.setCellRenderer(xxx);
      col.setResizable(false);
      col.setPreferredWidth(30);

      int columnCount = getColumnCount();
      for(int i = 1; i < columnCount; i++){
          getColumn(getColumnName(i)).setPreferredWidth(120);
      }
      
      setCellSelectionEnabled(true);
      //setRowSelectionAllowed(false);
      //setColumnSelectionAllowed(true);

		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setDefaultRenderer(Object.class, new TableCellRenderer());
		//JButtonColumn jb = new JButtonColumn();
		//jb.insertIn(this);
    }



    public String getToolTipText(MouseEvent event){
		Point point = event.getPoint();
		int row = rowAtPoint(point);
		int col = columnAtPoint(point);

		if (row == -1 || col == -1)return null;
		Object obj = getValueAt(row, col);
		if (obj == null)return "<NULL>";
		else if (obj instanceof String && obj.equals(""))return "<EMPTY>";
		else return obj.getClass().getName();
	}

	class JButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer{

        private static final long serialVersionUID = -5036058995946210780L;
            private JTable table;
			public JButtonRenderer(){
                Font f = getFont();
				setFont( f.deriveFont(Font.PLAIN).deriveFont(f.getSize() - 2) );
			}

			public void fireActionPerformed(ActionEvent event){
				super.fireActionPerformed(event);

				setText("*");
				repaint();
				if (table == null)return;

				int row = table.getEditingRow();
				table.setRowSelectionInterval(row, row);
			}

			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
                setBorder(BorderFactory.createRaisedBevelBorder());
				if (row == table.getSelectedRow())setText(">");
				else setText("");
				return this;
			}

	}
}
