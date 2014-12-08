/* Sviluppato da Matteo Piccinini */

package dbmanager;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.table.TableModel;

import dbmanager.core.DatabaseProperties;
import dbmanager.exporter.DbStruct;
import dbmanager.exporter.XmlExporter;
import dbmanager.gui.ChangeConnectionPaneListener;
import dbmanager.gui.DBMetaTableModel;
import dbmanager.gui.ResultSetTableModel;
import dbmanager.report.DatabasePropertiesTableModel;
import dbmanager.report.Report;
import dbmanager.table.TabellaResultSet;

public class ControllerMaster {

	public ControllerMaster(Master master) {
		this.master = master;
		master.addChangeConnectionPaneListener(new ChangeConnectionPaneListener() {
			public void paneChanging(int index) {
				switchToConnection(index);
			}
		});

		connections = new ArrayList<ConnectionManager>();
		connections.add( new ConnectionManager() );
		switchToConnection(0);
	}

	protected void switchToConnection(int index) {
		cm = connections.get(index);		
		master.setConnesso(cm.isConnected());
		master.setDriver(cm.getDriver());
		master.setUrl(cm.getUrl());
		master.setUsername(cm.getUsername());
		master.setPassword(cm.getPassword());
	}

	public void addNewConnection() {
		connections.add(new ConnectionManager());
		master.addNewConnectionTab();

		switchToConnection( connections.size() - 1 );
	}

	public void connetti() {
		try {
			cm.setDriver(master.getDriver());
			cm.setUrl(master.getUrl());
			cm.setUsername(master.getUsername());
			cm.setPassword(master.getPassword());
			Connection conn = cm.openConnection();
			if (conn != null && !conn.isClosed()) {
				DatabaseMetaData dbMeta = conn.getMetaData();

				prop = new DatabaseProperties(conn);
				master.setTabelle(prop.getTables());

				master.setKeywords(dbMeta.getSQLKeywords(),
						dbMeta.getNumericFunctions(),
						dbMeta.getStringFunctions(), prop.getSupportedTypes());
				master.setQuery("SELECT * FROM ");
				master.setConnesso(true);
			} else
				master.setConnesso(false);
		} catch (Exception e) {
			e.printStackTrace();
			master.appendMessage(e.getMessage() + '\n');
		}
	}

	public DatabaseProperties getDatabaseProperties() {
		return prop;
	}

	public void esegui() {
		closeUpdatabales();

		Statement stmt = null;
		ResultSet rs = null;

		try {
			String sql = master.getQuery();
			Connection conn = cm.getConnection();
			boolean updatable = false;
			try {
				stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				updatable = true;
			} catch (SQLException e) {
				stmt = conn.createStatement();
			}

			boolean isResultSet = stmt.execute(sql);
			if (isResultSet) {
				rs = stmt.getResultSet();
				if(updatable)updatableResultSet = rs;
				TabellaResultSet tabella = new TabellaResultSet(rs);
				master.setResult(tabella);
			} else {
				int updateCount = stmt.getUpdateCount();
				master.appendMessage(updateCount + " record affetti dalla query");
			}
		} catch (SQLException e) {
			master.appendMessage(e.getMessage() + '\n');
			e.printStackTrace();
		} catch (Exception e) {
			master.appendMessage(e.getMessage() + '\n');
			e.printStackTrace();
		} finally {
			if(updatableResultSet == null){
				closeQuitely(rs);
				closeQuitely(stmt);
			}
		}
	}

	private void closeUpdatabales() {
		if(updatableResultSet != null){
			try {
				Statement stmt = updatableResultSet.getStatement();
				closeQuitely(updatableResultSet);
				closeQuitely(stmt);
			} catch (SQLException e) {
				//e.printStackTrace();
			}finally{
				updatableResultSet = null;
			}
		}
	}

	private void closeQuitely(Statement stmt) {
		if(stmt == null)return;
		try {
			stmt.close();
		} catch (SQLException e) {
//			e.printStackTrace();
		}
	}

	private void closeQuitely(ResultSet rs) {
		if(rs == null)return;
		try {
			rs.close();
		} catch (SQLException e) {
//			e.printStackTrace();
		}
	}

	public void disconnetti() {
		cm.closeCurrentConnection();
		master.setConnesso(false);
	}

	public void esportaXml(TableModel dati, java.io.File file) {
		try {
			if (dati == null)
				return;
			XmlExporter exporter = new XmlExporter();
			exporter.esporta(dati, file.getCanonicalPath());
		} catch (Throwable e) {
			master.appendMessage(e.getMessage() + '\n');
			e.printStackTrace();
		}
	}

	public void esportaStruttura(File xsl, File dest) {
		try {
			if (prop == null)
				return;
			DbStruct exporter = new DbStruct();
			exporter.esporta(prop, xsl, dest.getCanonicalPath());
		} catch (Throwable e) {
			master.appendMessage(e.getMessage() + '\n');
			e.printStackTrace();
		}
	}

	public JFrame getReportStruttura() {
		if (prop == null)
			return null;
		try {
			Report report = new Report();
			report.setXmlFile("report/report.xml");
			TableModel dati = new DatabasePropertiesTableModel(prop);
			report.setDati(dati);
			return report.getReport();
		} catch (Throwable e) {
			master.appendMessage(e.getMessage() + '\n');
			e.printStackTrace();
			return null;
		}

	}

	public ResultSetTableModel getColumnInfo() {
		try {
			DatabaseMetaData dbMeta = cm.getConnection().getMetaData();
			ResultSet rs = dbMeta.getColumns(null, null, "%", "%");
			ResultSetTableModel model = new ResultSetTableModel(rs);
			rs.close();
			return model;
		} catch (Throwable e) {
			master.appendMessage(e.getMessage() + '\n');
			e.printStackTrace();
			return null;
		}
	}

	public ResultSetTableModel getTableInfo() {
		try {
			DatabaseMetaData dbMeta = cm.getConnection().getMetaData();
			ResultSet rs = dbMeta.getTables(null, null, null,
					new String[] { "TABLE" });
			ResultSetTableModel model = new ResultSetTableModel(rs);
			rs.close();
			return model;
		} catch (Throwable e) {
			master.appendMessage(e.getMessage() + '\n');
			e.printStackTrace();
			return null;
		}
	}

	public ResultSetTableModel getTypeInfo() {
		try {
			DatabaseMetaData dbMeta = cm.getConnection().getMetaData();
			ResultSet rs = dbMeta.getTypeInfo();
			ResultSetTableModel model = new ResultSetTableModel(rs);
			rs.close();
			return model;
		} catch (Throwable e) {
			master.appendMessage(e.getMessage() + '\n');
			e.printStackTrace();
			return null;
		}
	}

	public ResultSetTableModel getAttributesInfo() {
		try {
			DatabaseMetaData dbMeta = cm.getConnection().getMetaData();
			ResultSet rs = dbMeta.getAttributes(null, null, "%", "%");
			ResultSetTableModel model = new ResultSetTableModel(rs);
			rs.close();
			return model;
		} catch (Throwable e) {
			master.appendMessage(e.getMessage() + '\n');
			e.printStackTrace();
			return null;
		}
	}

	public TableModel getPrimaryKeys() {
		try {
			DatabaseMetaData dbMeta = cm.getConnection().getMetaData();
			ResultSet rs = dbMeta.getPrimaryKeys(null, null, "%");
			TableModel model = new ResultSetTableModel(rs);
			rs.close();
			return model;
		} catch (Throwable e) {
			master.appendMessage(e.getMessage() + '\n');
			e.printStackTrace();
			return null;
		}
	}

	public TableModel getGeneralInfo() {
		try {
			DatabaseMetaData dbMeta = cm.getConnection().getMetaData();
			return new DBMetaTableModel(dbMeta);
		} catch (Throwable e) {
			master.appendMessage(e.getMessage() + '\n');
			e.printStackTrace();
			return null;
		}
	}

	public void shutdown() {
		for (ConnectionManager cm : connections) {
			cm.closeCurrentConnection();
		}
		connections.clear();
	}

	private Master master;
	private ConnectionManager cm;
	private List<ConnectionManager> connections;
//	private Connection conn;
	private ResultSet updatableResultSet;
	private DatabaseProperties prop;

	public void startDbCompare() {
		try {
			if (connections.size() >= 2) {
				DatabaseProperties prop1 = new DatabaseProperties(connections.get(0).getConnection());
				DatabaseProperties prop2 = new DatabaseProperties(connections.get(1).getConnection());
				master.openCompareWindow(prop1, prop2);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
