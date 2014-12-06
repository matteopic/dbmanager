package dbmanager.report;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import dbmanager.core.Column;
import dbmanager.core.DatabaseProperties;
import dbmanager.core.Table;

public class DatabasePropertiesTableModel extends AbstractTableModel{


    private static final long serialVersionUID = -3181933750876761439L;

    public DatabasePropertiesTableModel(DatabaseProperties prop){
		dati = new ArrayList();

		Table[]tabelle = prop.getTables();
		Column[]column = null;
		Object[] row = null;
		Table tabella = null;
		Column colonna = null;
		for(int i = 0; i < tabelle.length; i++){
			tabella = tabelle[i];
			column = tabella.getColumns();
			for(int c = 0; c < column.length; c++){
				colonna = column[c];

				row = new Object[]{
					tabella.getName(),
					colonna.getName(),
					(colonna.getType() != null ? colonna.getType().getName() : null),
					new Integer(colonna.getLength()),
					(colonna.allowNull() ? "SI" : "NO"),
					(colonna.isPrimaryKey() ? "PRIMARY KEY" : null),
					colonna.getDefaultValue()
				};

				dati.add(row);
			}
		}
	}

	public Object getValueAt(int row, int col){ return ((Object[])dati.get(row))[col]; }

	public String getColumnName(int index){
		return nomeColonna[index];
	}

	public int getRowCount(){ return dati.size(); }

	public int getColumnCount(){ return nomeColonna.length; }

	private static String[]nomeColonna = new String[]{
		"NomeTabella", "NomeColonna", "TipoColonna", "DimensioneColonna", "AllowNull", "PrimaryKey",
		"DefaultValue"
	};

	private List dati;
}