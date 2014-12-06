package dbmanager.core;

public interface DatabaseManager {
	
	public void renameTable(String oldName, String newName);
	public void alterColumn(String oldName, Column newColumn);
	public void dropTable(String table);
	public void dropColumn(String columnName);

}
