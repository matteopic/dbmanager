package dbmanager.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

public class Table {

	public Table() {
		this("");
	}

	public Table(String name) {
		this.name = name;
		colonne = new Vector();
		indici = new ArrayList<Index>();
		keys = new ArrayList<Key>();
	}

	public String getName() {
		return name;
	}

	public void addIndex(Index index) {
		indici.add(index);
	}

	public Collection<Index> getIndexes() {
		return indici;
	}

	public void addColumn(Column col) {
		colonne.add(col);
		int size = colonne.size();
		columns = new Column[size];
		for (int i = 0; i < size; i++) {
			columns[i] = (Column) colonne.elementAt(i);
		}

	}

	public void removeColumn(int columnIndex) {
		colonne.set(columnIndex, null);
		pack();
		int size = colonne.size();
		columns = new Column[size];
		for (int i = 0; i < size; i++) {
			columns[i] = (Column) colonne.elementAt(i);
		}

	}

	public Column getColumn(String columnName) {
		Column column;
		for (int i = 0; i < colonne.size(); i++) {
			column = (Column) colonne.elementAt(i);
			if (column.getName().equals(columnName))
				return column;

		}
		return null;
	}

	public void removeColumn(String columnName) {
		int i = 0;
		for (i = 0; i < colonne.size(); i++) {
			if (((Column) colonne.elementAt(i)).getName().equals(columnName)) {
				removeColumn(i);
				break;
			}
		}
	}

	public Column[] getColumns() {
		return (Column[]) colonne.toArray(new Column[colonne.size()]);
	}

	public String[] getColumnNames() {
		String[] ret = new String[colonne.size()];

		for (int i = 0; i < colonne.size(); i++) {
			ret[i] = ((Column) colonne.elementAt(i)).getName();
		}
		return ret;
	}

	public boolean existColumn(String colName) {
		boolean exist = false;
		int size = columns.length;

		for (int i = 0; i < size; i++) {
			if (columns[i].getName().equals(colName)) {
				exist = true;
				break;
			}
		}
		return exist;
	}

	private void pack() {
		Vector tmp = new Vector();
		Object obj = null;
		int size = colonne.size();
		for (int i = 0; i < size; i++) {
			obj = colonne.elementAt(i);
			if (obj != null)
				tmp.add(obj);
		}
		colonne = tmp;
	}

	public String toString() {
		return name;
	}

	public void addKey(Key key) {
		keys.add(key);
	}

	Vector colonne;
	String name;
	Column[] columns;
	private List<Index> indici;
	private List<Key> keys;

}