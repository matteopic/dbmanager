/**
 * XslExporter.java
 *
 * Creato il 6-feb-2006 12.19.41
 */
package dbmanager.tools;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Matteo Piccinini
 */
public class XslExporter {

    /**
     * 
     */
    public XslExporter() {
        // String url = "jdbc:odbc:Driver={Microsoft Access Driver
        // (*.mdb)};Dbq=d:\\database.mdb;Uid=admin;Pwd=";
        // String driver = "sun.jdbc.odbc.JdbcOdbcDriver";

        // String url ="jdbc:mysql://localhost/jaco";
        // String driver = "com.mysql.jdbc.Driver";
        // String username = "admin";
        // String password = "admin";

        String url = "jdbc:postgresql://192.168.0.12/quartz";
        String driver = "org.postgresql.Driver";
        String username = "admin";
        String password = "admin";

        // String url
        // ="jdbc:hsqldb:d:/tomcat/5.0.27/webapps/agility/WEB-INF/dati/agility";
        // String driver = "org.hsqldb.jdbcDriver";

        // String driver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
        // String url =
        // "jdbc:microsoft:sqlserver://192.168.0.6;DataBaseName=soste";

        // String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
        // String url = "jdbc:odbc:TEST";
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, username,
                    password);
            DatabaseMetaData dbMeta = conn.getMetaData();

            Map mappa = new HashMap();
            
            ResultSet rs = dbMeta.getTypeInfo();
            while (rs.next()){
                String typeName = rs.getString("TYPE_NAME");
                Integer typeId = new Integer(rs.getInt("DATA_TYPE"));
                List list = (List)mappa.get(typeId);
                if (list == null){
                    list = new ArrayList();
                    mappa.put(typeId, list);
                }
                
                list.add(typeName);
            }
            Object[] keys = mappa.keySet().toArray();
            Arrays.sort(keys);
            for(int i = 0; i < keys.length; i++){
                Integer typeId = (Integer)keys[i];
                List values = (List)mappa.get(typeId);
                for (int v = 0; v < values.size(); v++){
                       String typeName = (String)values.get(v); 
                       StringBuffer sb = new StringBuffer();
                       if (v > 0)sb.append("           <!--");
                       else sb.append("           <");
                       sb.append("xsl:when test=\"text()=\'").append(typeId).append("\'\">\n"); 
                       sb.append("               <xsl:text>").append(typeName).append("</xsl:text>\n"); 
                       sb.append("           </xsl:when");
                       if (v > 0)sb.append("-->");
                       else sb.append(">");
                       System.out.println(sb);
                       System.out.println();                
                
                }
            }
                
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        new XslExporter();
    }

}
