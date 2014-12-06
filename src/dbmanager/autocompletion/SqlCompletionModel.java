package dbmanager.autocompletion;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.ListCellRenderer;

import dbmanager.core.Table;

public class SqlCompletionModel implements AutoCompletionModel {

	public SqlCompletionModel(){
		nfunction = new Vector();
		sfunction = new Vector();
		keys = new Vector();
		renderer = new SQLCellRenderer(this);
	}

    public List getWordList(String word) {
//		System.out.println("getWordList #" + word + "#");
		Vector lista = new Vector();
		word = word.trim();

        int size = sfunction.size();
        String function, key;
        for(int i = 0; i < size; i++){
			function = (String)sfunction.get(i);
			if (function.toLowerCase().startsWith(word))lista.add(function);
		}


		size = nfunction.size();
        for(int i = 0; i < size; i++){
			function = (String)nfunction.get(i);
			if (function.toLowerCase().startsWith(word))lista.add(function);
		}

		size = keys.size();
        for(int i = 0; i < size; i++){
			key = (String)keys.get(i);
			if (key.toLowerCase().startsWith(word))lista.add(key);
		}

        Table[] tabella = getTable(word);
        String nomeTabella;
		for(int i = 0; i < tabella.length; i++){
			nomeTabella = tabella[i].getName();
			String[]nomiColonne = tabella[i].getColumnNames();

			boolean showTableName = true;
			if (word.equals(nomeTabella))showTableName = false;
			if (showTableName)lista.add(nomeTabella);

			for(int c = 0; c < nomiColonne.length; c++){
				if(showTableName)lista.add(nomeTabella+"."+nomiColonne[c]);
				else lista.add(nomiColonne[c]);
			}
		}

		//size = lista.size();
		Object[]words = lista.toArray();
		Arrays.sort(words, new StringComparator());
		return Arrays.asList(words);
    }


	public Table[] getTable(String nome){
		nome = nome.toLowerCase();
		Vector tabs = new Vector();
		for(int i = 0; i < tabelle.length; i++){
			if (tabelle[i].getName().toLowerCase().startsWith(nome)){
				tabs.add(tabelle[i]);
			}
		}

		int size = tabs.size();
		Table[]t = new Table[size];
		tabs.toArray(t);
		return t;
	}

//	private List getCompleteList(){
//		Vector lista = new Vector();
//		Table tabella;
//		for(int i = 0; i < tabelle.length; i++){
//			tabella = tabelle[i];
//			lista.add(tabella.getName());
//			addColumns(lista, tabella);
//		}
//		return lista;
//	}

//	private void addColumns(Vector lista, Table tabella){
//		String[]colonne = tabella.getColumnNames();
//		String nomeTabella = tabella.getName();
//		for(int i = 0; i < colonne.length; i++){
//			lista.add(nomeTabella + "." +colonne[i]);
//		}
//	}

	public void setTabelle(Table[]tabelle){ this.tabelle = tabelle; }

	public void addStringFunction(String function){
		sfunction.add(function);
	}

	public void addNumericFunction(String function){
		nfunction.add(function);
	}

	public void addKeyword(String key){
		keys.add(key);
	}


	public boolean isStringFunction(String value){
		return sfunction.contains(value);
	}

	public boolean isNumericFunction(String value){
		return nfunction.contains(value);
	}

	public boolean isKeyword(String value){
		return keys.contains(value);
	}

	public boolean isTable(String value){
		for(int i = 0; i < tabelle.length; i++){
			if (tabelle[i].getName().equals(value))return true;
		}
		return false;
	}


	public boolean isColumn(String value){
		String nomeTabella;
		for(int i = 0; i < tabelle.length; i++){
			nomeTabella = tabelle[i].getName();

			String[]colonne = tabelle[i].getColumnNames();
			for(int c = 0; c < colonne.length; c++){
				if((nomeTabella + "." +colonne[c]).equals(value))return true;
			}
		}
		return false;
	}


	public ListCellRenderer getListCellRenderer(){
		return renderer;
	}

	private Table[]tabelle;
	private Vector nfunction, sfunction, keys;
	private SQLCellRenderer renderer;
}