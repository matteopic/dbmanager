package dbmanager.core;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import dbmanager.gui.ResultSetTableModel;

public class DatabaseProperties{

	public DatabaseProperties(Connection conn)throws SQLException{
		db = new Vector();	//Nomi Tabelle
		DatabaseMetaData dbMeta = conn.getMetaData();

		initTypes(dbMeta);
		initTables(dbMeta);
	}

	public void initTypes(DatabaseMetaData dbMeta)throws SQLException{
		ResultSet rs = dbMeta.getTypeInfo();

        ResultSetTableModel model = new ResultSetTableModel(rs);
		int rows = model.getRowCount();
		int colonnaNomeTipo = model.findColumn("TYPE_NAME");
		int colonnaJavaType = model.findColumn("DATA_TYPE");

        types = new Type[rows];
        Type tipo;
		for (int i = 0; i < rows; i++){
			tipo = new Type();
			tipo.setName((String)model.getValueAt(i, colonnaNomeTipo));

			Number javaType = (Number)model.getValueAt(i, colonnaJavaType);
			tipo.setJavaType(javaType.intValue());

//			tipo.setPrefix((String)model.getValueAt(i, "LITERAL_PREFIX"));
//			tipo.setSuffix((String)model.getValueAt(i, "LITERAL_SUFFIX"));
//			if ((String)model.getValueAt(i, "CREATE_PARAMS") != null)tipo.setParams(true);
//			else tipo.setParams(false);
//			tipi.add(tipo);
            types[i] = tipo;
		}
	}


	public Map getKeys(DatabaseMetaData dbMeta, String tabella)throws SQLException{
		ResultSet rs =  dbMeta.getPrimaryKeys(null, null, tabella);

		ResultSetTableModel model = new ResultSetTableModel(rs);
        model.setIgnoreCase(true);
		rs.close();
		int colonnaColumnName = model.findColumn("COLUMN_NAME");
		int colonnaKeySeq = model.findColumn("KEY_SEQ");

		Hashtable keys = new Hashtable();
		int rows = model.getRowCount();
		for(int i = 0; i < rows; i++){
			keys.put( model.getValueAt(i, colonnaColumnName),   model.getValueAt(i, colonnaKeySeq));
		}
		return keys;
	}
    
    public Map getIndexes(DatabaseMetaData dbMeta, String tabella)throws SQLException{
//        Connection conn = dbMeta.getConnection();
//        String catalog = conn.getCatalog();
//        String schema = conn.
        ResultSet rs =  dbMeta.getIndexInfo(null, null, tabella, false, false);

        ResultSetTableModel model = new ResultSetTableModel(rs);
        model.setIgnoreCase(true);
        rs.close();
        int colonnaColumnName = model.findColumn("COLUMN_NAME");
        int colonnaIndexName = model.findColumn("INDEX_NAME");

        Hashtable indexes = new Hashtable();
        int rows = model.getRowCount();
        for(int i = 0; i < rows; i++){
            indexes.put( model.getValueAt(i, colonnaColumnName),   model.getValueAt(i, colonnaIndexName));
        }
        return indexes;
    }


	public Table[] getTables(){
		return tabelle;
	}
    
    public Table findTable(String name) {
        Table[] tab = getTables();
        for (int i = 0; i < tab.length; i++)
            if (tab[i].getName().equals(name))
                return tab[i];
        return null;
    }


	public Type[] getSupportedTypes(){
		return types;
	}



	public Type typeForName(String name){
		for (int i = 0; i < types.length; i++){
			if (types[i].getName().equalsIgnoreCase(name))return types[i];
		}
		return new Type();
	}

	private void initTables(DatabaseMetaData dbMeta)throws SQLException{
		ResultSet rs = dbMeta.getTables(null, null, null, new String[]{"TABLE"});
		ResultSetTableModel rstm = new ResultSetTableModel(rs);
		int rows = rstm.getRowCount();

		String nomeTabella = null;
		Table table = null;
		int columnNomeTabella = rstm.findColumn("TABLE_NAME");
        if(columnNomeTabella == -1)columnNomeTabella = rstm.findColumn("table_name");

			//finch� ci sono tabelle
		for (int i = 0; i < rows; i++){
			nomeTabella = (String)rstm.getValueAt(i, columnNomeTabella);
			//System.out.println(nomeTabella);
			table = new Table(nomeTabella);
			initColumns(table, dbMeta);
			db.add(table);
		}
		int size = db.size();
		tabelle = new Table[size];
		tabelle = (Table[])db.toArray(tabelle);

	}

	private void initColumns(Table table, DatabaseMetaData dbMeta)throws SQLException{
		String nomeTabella = table.getName();
		ResultSet columnsRs = dbMeta.getColumns(null, null, nomeTabella,null);

		ResultSetTableModel rstm = new ResultSetTableModel(columnsRs);

		String typeName = null;
		Column column = null;

		int columnNomeColonna = rstm.findColumn("COLUMN_NAME");
		int columnTipoColonna = rstm.findColumn("TYPE_NAME");
		int columnNullable = rstm.findColumn("IS_NULLABLE");
		int columnSize = rstm.findColumn("COLUMN_SIZE");
		int columnDefaultValue = rstm.findColumn("COLUMN_DEF");

		int rows = rstm.getRowCount();
		Map tableKeys = getKeys(dbMeta, nomeTabella);
        Map tableIndexes = getIndexes(dbMeta, nomeTabella);
		Number keySeq = null;
		String nomeColonna = null;

		for(int i = 0; i < rows; i++){
			column = new Column();
			nomeColonna = (String)rstm.getValueAt(i, columnNomeColonna);
			column.setName(nomeColonna);
			keySeq = (Number)tableKeys.get(nomeColonna);
			if(keySeq != null){
				column.setPrimaryKey(true);
				column.setKeySequence(keySeq.intValue());
			}else column.setPrimaryKey(false);

			typeName = (String)rstm.getValueAt(i, columnTipoColonna);
			column.setType(typeForName(typeName));
			
			Number length = (Number)rstm.getValueAt(i, columnSize);
			if(length != null){
				column.setLength(length.intValue());
			}

			String nullable = rstm.getValueAt(i, columnNullable).toString();
			if (nullable.equals("NO"))column.setAllowNull(false);
			else column.setAllowNull(true);

			column.setDefaultValue( (String)rstm.getValueAt(i, columnDefaultValue) );

			table.addColumn(column);
		}

	}



	public static void main(String[]args){
		try{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection c = DriverManager.getConnection("jdbc:odbc:TEST");
//			DatabaseProperties dp = 
                new DatabaseProperties(c);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

//private String[]nomiTabelle;
private static Type[]types;
private List db;
private Table[]tabelle;
}