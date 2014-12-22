package dbmanager.plugins;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;

import dbmanager.report.FreemarkerReport;

public class MySQLResources implements Plugin {

	private JButton btn;
	private Connection conn;

	public Component getToolbarComponent() {
		btn = new JButton("Check");
		btn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				showReport();				
			}
		});
		btn.setVisible(false);
		return btn;
	}

	public void setCurrentConnection(Connection conn) {
		//Non sono stato aggiunto alla toolbar
		if(btn == null)return;
		
		this.conn = conn;		
		if(conn == null){
			btn.setVisible(false);
			return;
		}

		try{
			String product = conn.getMetaData().getDatabaseProductName();
			boolean isMysql = product != null && product.equalsIgnoreCase("mysql");
			btn.setVisible(isMysql);
		}catch(Exception ex){
			btn.setVisible(false);
		}
	}

	private void showReport(){
		try{
			Map<String,Object>data = new HashMap<String,Object>();

			DatabaseMetaData dbmeta =  conn.getMetaData();
			data.put("major_version", dbmeta.getDatabaseMajorVersion() + "." + dbmeta.getDatabaseMinorVersion());

			String pVersion = dbmeta.getDatabaseProductVersion();
			Matcher matcher = Pattern.compile("(\\d+?\\.){2}(\\d+)[^0-9]*").matcher(pVersion);
			boolean found = matcher.find();
			String patchLevel = matcher.group(2);
			int databasePatchLevel = Integer.parseInt(patchLevel);
			String mysql_version_num = String.format("%02d%02d%02d", dbmeta.getDatabaseMajorVersion(), dbmeta.getDatabaseMinorVersion(), databasePatchLevel); 
			data.put("mysql_version_num", Long.parseLong(mysql_version_num));

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SHOW VARIABLES");
			fillMap(data, rs);
			rs.close();

			rs = stmt.executeQuery("SHOW GLOBAL STATUS");
			fillMap(data, rs);
			rs.close();
			stmt.close();
			process(data);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private void fillMap(Map<String, Object>data, ResultSet rs) throws SQLException{
		while(rs.next()){
			String key = rs.getString(1);
			Object value = rs.getObject(2);
			data.put(key, value);
		}
//		rs.close();
	}

	private void process(Map<String, Object> data) {
		// minimum supported version 5.5
		String version = (String)data.get("version");
		String[]versionTokens = version.split("[\\.-]");
		int major = Integer.parseInt(versionTokens[0]);
		int minor = Integer.parseInt(versionTokens[1]);
		boolean EOL = false;
		if(major < 5 || (major == 5 && minor < 5))EOL = true;
	
		FreemarkerReport fmr  = new FreemarkerReport();
		fmr.showReport(data, "mysql-resources.ftl");
		
	}

}
