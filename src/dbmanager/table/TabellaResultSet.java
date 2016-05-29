/* Sviluppato da Matteo Piccinini */

package dbmanager.table;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EventObject;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
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
        
        JButtonRenderer jbr = new JButtonRenderer();
		col.setCellRenderer(jbr);
//		col.setCellEditor(jbr);
		col.setResizable(false);
		col.setPreferredWidth(30);

      int columnCount = getColumnCount();
      for(int i = 1; i < columnCount; i++){
          getColumn(getColumnName(i)).setPreferredWidth(120);
      }

      setCellSelectionEnabled(true);
      setRowSelectionAllowed(true);
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

	class JButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer, TableCellEditor{

        private static final long serialVersionUID = -5036058995946210780L;
            private JTable table;
			public JButtonRenderer(){
				table = TabellaResultSet.this;
				
                Font f = getFont();
				setFont( f.deriveFont(Font.PLAIN).deriveFont(f.getSize() - 2) );
				addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						setText("*");
						repaint();

						int row = table.getEditingRow();
						table.setRowSelectionInterval(row, row);
					}
				});
			}

			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
                setBorder(BorderFactory.createRaisedBevelBorder());
				if (row == table.getSelectedRow())setText("\u25b6");
				else setText("");
				return this;
			}

			public Object getCellEditorValue() {
				return null;
			}

			public boolean isCellEditable(EventObject anEvent) {
				return true;
			}

			public boolean shouldSelectCell(EventObject anEvent) {
				return false;
			}

			public boolean stopCellEditing() {
				return true;
			}

			public void cancelCellEditing() {				
			}

			public void addCellEditorListener(CellEditorListener l) {				
			}

			public void removeCellEditorListener(CellEditorListener l) {				
			}

			public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
					int column) {
				return getTableCellRendererComponent(table, value, isSelected, true, row, column);
			}

	}
}
