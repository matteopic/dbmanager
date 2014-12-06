/* Sviluppato da Matteo Piccinini */

package dbmanager;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.table.TableModel;

import dbmanager.core.DatabaseProperties;
import dbmanager.exporter.DbStruct;
import dbmanager.exporter.XmlExporter;
import dbmanager.gui.DBMetaTableModel;
import dbmanager.gui.ResultSetTableModel;
import dbmanager.report.DatabasePropertiesTableModel;
import dbmanager.report.Report;
import dbmanager.table.TabellaResultSet;

public class ControllerMaster {
    public ControllerMaster(Master master) {
        this.master = master;
        cm = new ConnectionManager();
		master.setConnesso(false);
    }

    public void connetti() {
        try {
            cm.setDriver(master.getDriver());
            cm.setUrl(master.getUrl());
            cm.setUsername(master.getUsername());
            cm.setPassword(master.getPassword());
            conn = cm.openConnection();
            if (conn != null && !conn.isClosed()){
            	DatabaseMetaData dbMeta = conn.getMetaData();

				prop = new DatabaseProperties(conn);
				master.setTabelle(prop.getTables());

				master.setKeywords(
					dbMeta.getSQLKeywords(),
					dbMeta.getNumericFunctions(),
					dbMeta.getStringFunctions(),
					prop.getSupportedTypes()
				);
				master.setQuery("SELECT * FROM ");
				master.setConnesso(true);
			}
            else
                master.setConnesso(false);
        } catch (Exception e) {
			e.printStackTrace();
            master.appendMessage(e.getMessage() + '\n');
        }
    }



    public void esegui() {
        try {
			if(stmt != null){
				stmt.close();
				stmt = null;
			}

            String sql = master.getQuery();
            boolean readOnly = true;
			try{
				stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				readOnly = false;
			}catch(Exception e){
				stmt = conn.createStatement();
			}

            boolean isResultSet = stmt.execute(sql);
            ResultSet rs = null;
            int upd = 0;
//            Component component = null;
            if (isResultSet) {
                rs = stmt.getResultSet();
                TabellaResultSet tabella = new TabellaResultSet(rs);
                master.setResult(tabella);
                if (readOnly){
					stmt.close();
					stmt = null;
				}
            }
            else {
                upd = stmt.getUpdateCount();
                master.appendMessage(upd + " record affetti dalla query");
                stmt.close();
                stmt = null;
            }
        } catch (SQLException e) {
            master.appendMessage(e.getMessage()+'\n');
            e.printStackTrace();
        } catch (Exception e) {
            master.appendMessage(e.getMessage()+'\n');
            e.printStackTrace();
        }
    }



    public void disconnetti() {
        cm.closeConnection(conn);
		master.setConnesso(false);
    }

    public void esportaXml(TableModel dati, java.io.File file){
		try {
			if (dati == null)return;
			XmlExporter exporter = new XmlExporter();
			exporter.esporta(dati, file.getCanonicalPath());
        } catch (Throwable e) {
            master.appendMessage(e.getMessage()+'\n');
            e.printStackTrace();
        }
	}

    public void esportaStruttura(File xsl, File dest){
		try {
			if (prop == null)return;
			DbStruct exporter = new DbStruct();
			exporter.esporta(prop, xsl, dest.getCanonicalPath());
        } catch (Throwable e) {
            master.appendMessage(e.getMessage()+'\n');
            e.printStackTrace();
        }
	}

	public JFrame getReportStruttura(){
		if (prop == null)return null;
		try{
			Report report = new Report();
			report.setXmlFile("report/report.xml");
			TableModel dati = new DatabasePropertiesTableModel(prop);
			report.setDati(dati);
			return report.getReport();
        } catch (Throwable e) {
            master.appendMessage(e.getMessage()+'\n');
            e.printStackTrace();
            return null;
        }

	}
	
	public ResultSetTableModel getColumnInfo(){
		try{
			DatabaseMetaData dbMeta = conn.getMetaData();
			ResultSet rs = dbMeta.getColumns(null, null, "%", "%");
			ResultSetTableModel model = new ResultSetTableModel(rs);
			rs.close();
			return model;
        } catch (Throwable e) {
            master.appendMessage(e.getMessage()+'\n');
            e.printStackTrace();
            return null;
        }
	}
	
	public ResultSetTableModel getTableInfo(){
		try{
			DatabaseMetaData dbMeta = conn.getMetaData();
			ResultSet rs = dbMeta.getTables(null, null, null, new String[] { "TABLE" });
			ResultSetTableModel model = new ResultSetTableModel(rs);
			rs.close();
			return model;
        } catch (Throwable e) {
            master.appendMessage(e.getMessage()+'\n');
            e.printStackTrace();
            return null;
        }
	}
	
	public ResultSetTableModel getTypeInfo(){
		try{
			DatabaseMetaData dbMeta = conn.getMetaData();
			ResultSet rs = dbMeta.getTypeInfo();
			ResultSetTableModel model = new ResultSetTableModel(rs);
			rs.close();
			return model;
        } catch (Throwable e) {
            master.appendMessage(e.getMessage()+'\n');
            e.printStackTrace();
            return null;
        }
	}
	
	public ResultSetTableModel getAttributesInfo(){
		try{
			DatabaseMetaData dbMeta = conn.getMetaData();
			ResultSet rs = dbMeta.getAttributes(null, null, "%", "%");
			ResultSetTableModel model = new ResultSetTableModel(rs);
			rs.close();
			return model;
        } catch (Throwable e) {
            master.appendMessage(e.getMessage()+'\n');
            e.printStackTrace();
            return null;
        }
	}
	
	public TableModel getPrimaryKeys(){
		try{
			DatabaseMetaData dbMeta = conn.getMetaData();
			ResultSet rs = dbMeta.getPrimaryKeys(null, null, "%");
			TableModel model = new ResultSetTableModel(rs);
			rs.close();
			return model;
        } catch (Throwable e) {
            master.appendMessage(e.getMessage()+'\n');
            e.printStackTrace();
            return null;
        }
	}
	
	public TableModel getGeneralInfo(){
		try{
			DatabaseMetaData dbMeta = conn.getMetaData();
			return new DBMetaTableModel(dbMeta);
        } catch (Throwable e) {
            master.appendMessage(e.getMessage()+'\n');
            e.printStackTrace();
            return null;
        }
	}



    private Master master;
    private ConnectionManager cm;
    private Connection conn;
    private Statement stmt;
    private DatabaseProperties prop;
}
