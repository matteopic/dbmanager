package dbmanager.gui;
import javax.swing.JButton;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class ButtonColumnTableModel implements TableModel{

	public ButtonColumnTableModel(TableModel model){
		setModel(model);
	}

	public void addTableModelListener(TableModelListener l){
		model.addTableModelListener(l);
	}

	public Class getColumnClass(int columnIndex){
		if (columnIndex == 0)return JButton.class;
		return model.getColumnClass(columnIndex - 1);
	}

	public int getColumnCount(){
		return model.getColumnCount() + 1;
	}

	public String getColumnName(int columnIndex){
		if (columnIndex == 0)return "";
		return model.getColumnName(columnIndex - 1);
	}

	public int getRowCount(){
		return model.getRowCount();
	}

	public Object getValueAt(int rowIndex, int columnIndex){
		if (columnIndex == 0)return null;
		return model.getValueAt(rowIndex, columnIndex - 1);

	}

 	public boolean isCellEditable(int rowIndex, int columnIndex){
		if(columnIndex == 0)return false;
		return model.isCellEditable(rowIndex, columnIndex);
	}

	public void	removeTableModelListener(TableModelListener l){
		model.removeTableModelListener(l);
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex){
		if (columnIndex == 0)return;
		model.setValueAt(aValue, rowIndex, columnIndex - 1);
	}

	public TableModel getModel(){ return model; }

	public void setModel(TableModel model){ this.model = model; }


private TableModel model;
}