// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   Prova.java

package dbmanager;

//import dbmanager.mapping.ResultSetMapper;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import dbmanager.gui.ResultSetTableModel;

public class Prova extends JFrame
{

    private static final long serialVersionUID = -3938501926056943065L;

    public Prova()
    {
        //PropertyConfigurator.configure("log4j.properties");
        try
        {



			//String url = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};Dbq=d:\\database.mdb;Uid=admin;Pwd=";
			//String driver = "sun.jdbc.odbc.JdbcOdbcDriver";

//          String url ="jdbc:derby:jaco;create=true";
//          String driver = "org.apache.derby.jdbc.EmbeddedDriver";
  //        String username = "admin";
    //      String password = "";
        	
           String url ="jdbc:mysql://192.168.0.128:3307/jaco_produzione";
           String driver = "com.mysql.jdbc.Driver";
           String username = "admin";
           String password = "admin";

//            String url ="jdbc:postgresql://192.168.0.12/quartz";
//            String driver = "org.postgresql.Driver";
//            String username = "admin";
//            String password = "admin";
            
            //String url ="jdbc:hsqldb:d:/tomcat/5.0.27/webapps/agility/WEB-INF/dati/agility";
            //String driver = "org.hsqldb.jdbcDriver";

//            String driver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
//            String url = "jdbc:microsoft:sqlserver://192.168.0.6;DataBaseName=soste";

            //String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
            //String url = "jdbc:odbc:TEST";
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, username, password);
            DatabaseMetaData dbMeta = conn.getMetaData();
            setTitle(conn.getCatalog());	

            ResultSet rs = dbMeta.getTypeInfo();
//            ResultSet rs = dbMeta.getTables(null, null, null, new String[] { "TABLE" });
            //ResultSet rs = dbMeta.getColumns(null, null, "%", "%");
//            ResultSet rs = dbMeta.getPrimaryKeys(null, null, "people");
//            ResultSet rs = dbMeta.getExportedKeys(null, null, "people");
//            ResultSet rs = dbMeta.getImportedKeys(null, null, "people");
            //ResultSet rs = dbMeta.getPrimaryKeys(null, null, "gara");



            ResultSetTableModel rsm = new ResultSetTableModel(rs);
            JTable table = new JTable(rsm);
            table.setAutoResizeMode(0);
            setContentPane(new JScrollPane(table));
            setDefaultCloseOperation(3);
            pack();
            setVisible(true);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String args[])
    {
        new Prova();
    }

//    private Logger logger = Logger.getLogger(Prova.class);
}
