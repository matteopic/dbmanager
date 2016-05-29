package dbmanager.autocompletion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.ListCellRenderer;

import dbmanager.core.Table;

public class SqlCompletionModel implements AutoCompletionModel {

	public SqlCompletionModel() {
		nfunction = new Vector();
		sfunction = new Vector();
		keys = new Vector();
		renderer = new SQLCellRenderer(this);
	}

	public List<String> getWordList(String word) {
		// System.out.println("getWordList #" + word + "#");
		ArrayList<String> list = new ArrayList<String>();
		word = word.trim();

		int size = sfunction.size();
		String function, key;
		for (int i = 0; i < size; i++) {
			function = (String) sfunction.get(i);
			if (function.toLowerCase().startsWith(word))
				list.add(function);
		}

		size = nfunction.size();
		for (int i = 0; i < size; i++) {
			function = (String) nfunction.get(i);
			if (function.toLowerCase().startsWith(word))
				list.add(function);
		}

		size = keys.size();
		for (int i = 0; i < size; i++) {
			key = (String) keys.get(i);
			if (key.toLowerCase().startsWith(word))
				list.add(key);
		}

		Table[] tabella = getTable(word);
		String nomeTabella;
		for (int i = 0; i < tabella.length; i++) {
			nomeTabella = tabella[i].getName();
			String[] nomiColonne = tabella[i].getColumnNames();

			boolean showTableName = true;
			if (word.equals(nomeTabella))
				showTableName = false;
			if (showTableName)
				list.add(nomeTabella);

			for (int c = 0; c < nomiColonne.length; c++) {
				if (showTableName)
					list.add(nomeTabella + "." + nomiColonne[c]);
				else
					list.add(nomiColonne[c]);
			}
		}

		list.sort(String.CASE_INSENSITIVE_ORDER);
		return list;
	}

	public Table[] getTable(String nome) {
		nome = nome.toLowerCase();
		Collection<Table> tabs = tabelle.subMap( nome, nome + Character.MAX_VALUE ).values();

		int size = tabs.size();
		Table[] t = new Table[size];
		tabs.toArray(t);
		return t;
	}

	public void setTabelle(Collection<Table> tables) {
		this.tabelle = new TreeMap<String, Table>();
		for(Table table : tables){
			tabelle.put(table.getName().toLowerCase(), table);
		}
	}

	public void addStringFunction(String function) {
		sfunction.add(function);
	}

	public void addNumericFunction(String function) {
		nfunction.add(function);
	}

	public void addKeyword(String key) {
		keys.add(key);
	}

	public boolean isStringFunction(String value) {
		return sfunction.contains(value);
	}

	public boolean isNumericFunction(String value) {
		return nfunction.contains(value);
	}

	public boolean isKeyword(String value) {
		return keys.contains(value);
	}

	public boolean isTable(String value) {
		return tabelle.containsKey(value);
	}

	public boolean isColumn(String value) {
		for (Table table : tabelle.values()) {
			String nomeTabella = table.getName();

			String[] colonne = table.getColumnNames();
			for (int c = 0; c < colonne.length; c++) {
				if ((nomeTabella + "." + colonne[c]).equals(value))
					return true;
			}
		}
		return false;
	}

	public ListCellRenderer getListCellRenderer() {
		return renderer;
	}

	private TreeMap<String,Table> tabelle;
	private Vector nfunction, sfunction, keys;
	private SQLCellRenderer renderer;
}