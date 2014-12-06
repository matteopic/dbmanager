package dbmanager.gui;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
public class JButtonColumn extends TableColumn{

    private static final long serialVersionUID = -1253831700455631347L;




    public JButtonColumn(){
		setModelIndex(0);
		setResizable(false);
		JButtonRenderer render = new JButtonRenderer();
		setCellRenderer(render);
		setHeaderValue("");
		setCellEditor(null);
	}

	public int getWidth(){
		return 30;
	}
/*
	public void insertIn(JTable table){
		table.addColumn(this);
		table.moveColumn(table.getColumnCount() - 1, 0);
	}*/




	class JButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer{

        private static final long serialVersionUID = -8164920157671797580L;
        private JTable table;

		public JButtonRenderer(){
			setFont( getFont().deriveFont(Font.PLAIN) );
		}

		public void fireActionPerformed(ActionEvent event){
			super.fireActionPerformed(event);

			setText("*");
			repaint();
			System.out.println(table);
			if (table == null)return;

			int row = table.getEditingRow();
			System.out.println(row+"");
			table.setRowSelectionInterval(row, row);
			table.setColumnSelectionInterval(0, table.getColumnCount()-1);
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
			if (row == table.getSelectedRow())setText(">");
			else setText("");
			return this;
		}

	}
}