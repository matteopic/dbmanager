package dbmanager.core;

public abstract class AbstractDatabaseManager implements DatabaseManager {

	
	public void alterColumn(String oldName, Column newColumn) {
		// TODO Auto-generated method stub

	}

	public void dropColumn(String columnName) {
		// TODO Auto-generated method stub

	}

	public void dropTable(String table) {
		// TODO Auto-generated method stub

	}

	public void renameTable(String oldName, String newName) {
		// TODO Auto-generated method stub

	}
	
	protected abstract String getAlterColumnQuery(String oldName, Column newColumn);
	protected abstract String getDropColumnQuery(String columnName);
	protected abstract String getDropTableQuery(String tableName);
	protected abstract String getRenameTableQuery(String oldName, String newName);

}
