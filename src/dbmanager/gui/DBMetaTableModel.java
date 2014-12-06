package dbmanager.gui;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;


public class DBMetaTableModel extends AbstractTableModel {

	public DBMetaTableModel(DatabaseMetaData meta) throws SQLException{
		data = new Object[][]{
				{"Database Major Version", meta.getDatabaseMajorVersion() },
				{"Database Minor Version ", meta.getDatabaseMinorVersion() },
				{"Database Product Name", meta.getDatabaseProductName()},
				{"Database Product Version", meta.getDatabaseProductVersion()},
				{"Catalog Separator", meta.getCatalogSeparator()},
				{"Catalog Term", meta.getCatalogTerm()},
				{"Driver Name", meta.getDriverName()},
				{"Driver Major Version", meta.getDriverMajorVersion()},
				{"Driver Minor Version", meta.getDriverMinorVersion()},
				{"Driver Version", meta.getDriverVersion()},
				{"Extra Name Characters", meta.getExtraNameCharacters()},
				{"Identifier Quote String", meta.getIdentifierQuoteString()},
				{"JDBC Major Version",meta.getJDBCMajorVersion()},
				{"JDBC Minor Version",meta.getJDBCMinorVersion()},
				{"Max Binary Literal Length", meta.getMaxBinaryLiteralLength()},
				{"Max Catalog Name Length", meta.getMaxCatalogNameLength()},
				{"Max Char Literal Length", meta.getMaxCharLiteralLength()},
				{"Max Column Name Length", meta.getMaxColumnNameLength()},
				{"Max Columns In Group By", meta.getMaxColumnsInGroupBy()},
				{"Max Columns In Index", meta.getMaxColumnsInIndex()},
				{"Max Columns In Order By", meta.getMaxColumnsInOrderBy()},
				{"Max Columns In Select", meta.getMaxColumnsInSelect()},
				{"Max Columns In Table", meta.getMaxColumnsInTable()},
				{"Max Connections", meta.getMaxConnections()},
				{"Max Cursor Name Length", meta.getMaxCursorNameLength()},
				{"Max Index Length", meta.getMaxIndexLength()},
				{"Max Procedure Name Length", meta.getMaxProcedureNameLength()},
				{"Max Row Size", meta.getMaxRowSize()},
				{"Max Schema Name Length", meta.getMaxSchemaNameLength()},
				{"Max Statement Length", meta.getMaxStatementLength()},
				{"Max Statements", meta.getMaxStatements()},
				{"Max Table Name Length", meta.getMaxTableNameLength()},
				{"Max Tables In Select", meta.getMaxTablesInSelect()},
				{"Max User Name Length", meta.getMaxUserNameLength()},
				{"Numeric Functions", meta.getNumericFunctions()}
		};

		/*
 int 	getMaxTablesInSelect()
          Retrieves the maximum number of tables this database allows in a SELECT statement.
 int 	getMaxUserNameLength()
          Retrieves the maximum number of characters this database allows in a user name.
 String 	getNumericFunctions()
          Retrieves a comma-separated list of math functions available with this database.
		 */
	}

	public int getColumnCount() {
		return 2;
	}

	public int getRowCount() {
		return data.length;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}
	
	@Override
	public String getColumnName(int column) {
		return "";
	}
	
	private Object[][] data;

}
