package dbmanager.core;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;

public class DatabaseProperties {

	public DatabaseProperties(Connection conn) throws SQLException {
		DatabaseMetaData dbMeta = conn.getMetaData();

		initCatalogs(dbMeta);
		initTypes(dbMeta);
		initTables(dbMeta);
		initColumns(dbMeta);
		for(Catalog catalog : catalogs.values()){
			for(Table table : catalog.getTables()){
				initKeys(dbMeta, catalog, table);				
				initIndexes(dbMeta, catalog, table);
			}
		}
	}

	private void initCatalogs(DatabaseMetaData dbMeta) throws SQLException {
		ResultSet rs = dbMeta.getCatalogs();
		catalogs = new HashMap<String, Catalog>();
		while (rs.next()) {
			String name = rs.getString("TABLE_CAT");
			catalogs.put(name, new Catalog(name));
		}
		rs.close();
	}

	public void initTypes(DatabaseMetaData dbMeta) throws SQLException {
		ResultSet rs = dbMeta.getTypeInfo();
		types = new HashMap<String, Type>();
		while (rs.next()) {
			String typeName = rs.getString("TYPE_NAME");
			Number javaType = (Number) rs.getObject("DATA_TYPE");

			Type type = new Type();
			type.setName(typeName);
			type.setJavaType(javaType.intValue());
			String lowerName = typeName.toLowerCase();
			types.put(lowerName, type);
		}
		rs.close();

		// ResultSetTableModel model = new ResultSetTableModel(rs);
		// int rows = model.getRowCount();
		// int colonnaNomeTipo = model.findColumn("TYPE_NAME");
		// int colonnaJavaType = model.findColumn("DATA_TYPE");
		//
		// types = new HashMap<String, Type>();
		// for (int i = 0; i < rows; i++) {
		// Type type = new Type();
		// String typeName = (String) model.getValueAt(i, colonnaNomeTipo);
		// type.setName(typeName);
		//
		// Number javaType = (Number) model.getValueAt(i, colonnaJavaType);
		// type.setJavaType(javaType.intValue());
		//
		// // tipo.setPrefix((String)model.getValueAt(i, "LITERAL_PREFIX"));
		// // tipo.setSuffix((String)model.getValueAt(i, "LITERAL_SUFFIX"));
		// // if ((String)model.getValueAt(i, "CREATE_PARAMS") !=
		// // null)tipo.setParams(true);
		// // else tipo.setParams(false);
		// // tipi.add(tipo);
		//
		// }
	}

	private void initKeys(DatabaseMetaData dbMeta, Catalog catalog, Table table) throws SQLException {
		// JDBC says a table name is required
		ResultSet rs = dbMeta.getPrimaryKeys(catalog.getName(), null, table.getName());

		while (rs.next()) {
			String tabCat = rs.getString("TABLE_CAT");
			String tabSchem = rs.getString("TABLE_SCHEM");
			String tableName = rs.getString("TABLE_NAME");
			String columnName = rs.getString("COLUMN_NAME");
			short keySeq = rs.getShort("KEY_SEQ");
			String pkName = rs.getString("PK_NAME");


			Column column = table.getColumn(columnName);
			column.setPrimaryKey(true);

			Key key = new Key();
			key.setColumn(column);
			key.setKeySeq(keySeq);
			key.setPkName(pkName);

			table.addKey(key);
		}
		rs.close();

		// ResultSetTableModel model = new ResultSetTableModel(rs);
		// model.setIgnoreCase(true);
		// rs.close();
		// int colonnaColumnName = model.findColumn("COLUMN_NAME");
		// int colonnaKeySeq = model.findColumn("KEY_SEQ");
		//
		// Hashtable keys = new Hashtable();
		// int rows = model.getRowCount();
		// for (int i = 0; i < rows; i++) {
		// keys.put(model.getValueAt(i, colonnaColumnName), model.getValueAt(i,
		// colonnaKeySeq));
		// }
		// return keys;
	}

	private void initIndexes(DatabaseMetaData dbMeta, Catalog catalog,Table table) throws SQLException {
		ResultSet rs = dbMeta.getIndexInfo(catalog.getName(), null, table.getName(), false, false);
		while (rs.next()) {
			String tabCat = rs.getString("TABLE_CAT");
			String tabSchem = rs.getString("TABLE_SCHEM");
			String tableName = rs.getString("TABLE_NAME");
			String columnName = rs.getString("COLUMN_NAME");
			String indexName = rs.getString("INDEX_NAME");

//			Catalog catalog = catalogs.get(tabCat);
//			Table table = catalog.getTable(tableName);
			Column column = table.getColumn(columnName);

			Index index = new Index(indexName);
			index.setColumn(column);

			table.addIndex(index);
		}
		rs.close();

		// Connection conn = dbMeta.getConnection();
		// String catalog = conn.getCatalog();
		// String schema = conn.

		// ResultSetTableModel model = new ResultSetTableModel(rs);
		// model.setIgnoreCase(true);
		// rs.close();
		// int colonnaColumnName = model.findColumn("COLUMN_NAME");
		// int colonnaIndexName = model.findColumn("INDEX_NAME");
		//
		// Hashtable indexes = new Hashtable();
		// int rows = model.getRowCount();
		// for (int i = 0; i < rows; i++) {
		// indexes.put(model.getValueAt(i, colonnaColumnName),
		// model.getValueAt(i, colonnaIndexName));
		// }
		// return indexes;
	}
	
	public Collection<Catalog> getCatalogs() {
		return catalogs.values();
	}
	
	public Catalog getCatalog(String name){
		return catalogs.get(name);
	}

	@Deprecated
	public Table[] getTables() {
		return tabelle;
	}

	@Deprecated
	public Table findTable(String name) {
		Table[] tab = getTables();
		for (int i = 0; i < tab.length; i++)
			if (tab[i].getName().equals(name))
				return tab[i];
		return null;
	}

	@Deprecated
	public Type[] getSupportedTypes() {
		return types.values().toArray(new Type[types.size()]);
	}
	
	public Collection<Type> getTypes(){
		return types.values();
	}
	
	private Type typeForName(String name) {
		String lowerName = name.toLowerCase();
		Type type = types.get(lowerName);
		return type != null ? type : new Type();
	}

	private void initTables(DatabaseMetaData dbMeta) throws SQLException {
		ResultSet rs = dbMeta.getTables(null, null, null, new String[] { "TABLE" });
		while (rs.next()) {
			String tabCat = rs.getString("TABLE_CAT");
			String tabSchem = rs.getString("TABLE_SCHEM");
			String tableName = rs.getString("TABLE_NAME");
			Table table = new Table(tableName);

			catalogs.get(tabCat).addTable( table );
		}
		rs.close();
	}

	private void initColumns(DatabaseMetaData dbMeta) throws SQLException {
		ResultSet columnsRs = dbMeta.getColumns(null, null, null, null);
		while (columnsRs.next()) {
			String tabCat = columnsRs.getString("TABLE_CAT");
			String tabSchem = columnsRs.getString("TABLE_SCHEM");
			String tableName = columnsRs.getString("TABLE_NAME");
			String columnName = columnsRs.getString("COLUMN_NAME");
			String typeName = columnsRs.getString("TYPE_NAME");
			int isNullable = columnsRs.getInt("NULLABLE");
			Number columnSize = (Number) columnsRs.getObject("COLUMN_SIZE");
			String columnDef = columnsRs.getString("COLUMN_DEF");

			Column column = new Column();
			column.setName(columnName);
			column.setType(typeForName(typeName));
			if (columnSize != null) {
				column.setLength(columnSize.intValue());
			}

			switch (isNullable) {
			case DatabaseMetaData.columnNoNulls:
				column.setAllowNull(false);
				break;
			case DatabaseMetaData.columnNullable:
				column.setAllowNull(true);
				break;
			case DatabaseMetaData.columnNullableUnknown:
				// TODO Implement
				break;
			}

			column.setDefaultValue(columnDef);

			Catalog catalog = catalogs.get(tabCat);
			Table table = catalog.getTable(tableName);
			table.addColumn(column);
		}

		// ResultSetTableModel rstm = new ResultSetTableModel(columnsRs);
		//
		// String typeName = null;
		// Column column = null;
		//
		// String nomeTabella = table.getName();
		// int columnNomeColonna = rstm.findColumn("COLUMN_NAME");
		// int columnTipoColonna = rstm.findColumn("TYPE_NAME");
		// int columnNullable = rstm.findColumn("IS_NULLABLE");
		// int columnSize = rstm.findColumn("COLUMN_SIZE");
		// int columnDefaultValue = rstm.findColumn("COLUMN_DEF");
		//
		// int rows = rstm.getRowCount();
		// Map tableKeys = getKeys(dbMeta, nomeTabella);
		// Map tableIndexes = getIndexes(dbMeta, nomeTabella);
		// Number keySeq = null;
		// String nomeColonna = null;
		//
		// for (int i = 0; i < rows; i++) {
		// column = new Column();
		// nomeColonna = (String) rstm.getValueAt(i, columnNomeColonna);
		// column.setName(nomeColonna);
		// keySeq = (Number) tableKeys.get(nomeColonna);
		// if (keySeq != null) {
		// column.setPrimaryKey(true);
		// column.setKeySequence(keySeq.intValue());
		// } else
		// column.setPrimaryKey(false);
		//
		// typeName = (String) rstm.getValueAt(i, columnTipoColonna);
		// column.setType(typeForName(typeName));
		//
		// Number length = (Number) rstm.getValueAt(i, columnSize);
		// if (length != null) {
		// column.setLength(length.intValue());
		// }
		//
		// String nullable = rstm.getValueAt(i, columnNullable).toString();
		// if (nullable.equals("NO"))
		// column.setAllowNull(false);
		// else
		// column.setAllowNull(true);
		//
		// column.setDefaultValue((String) rstm.getValueAt(i,
		// columnDefaultValue));
		//
		// table.addColumn(column);
		// }

	}

	// private String[]nomiTabelle;

	private Table[] tabelle;
	private HashMap<String, Catalog> catalogs;
	private HashMap<String, Type> types;
}