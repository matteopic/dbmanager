/* Sviluppato da Matteo Piccinini */

package dbmanager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    public String getUsername(){ return username; }

    public void setUsername(String username){ this.username = username; }

    public String getPassword(){ return password; }

    public void setPassword(String password){ this.password = password; }

    public String getUrl(){ return url; }

    public void setUrl(String url){ this.url = url; }

    public String getDriver(){ return driver; }

    public void setDriver(String driver){ this.driver = driver; }

    public Connection openConnection()throws SQLException{
		try{
			Class.forName(driver);
    	    conn = DriverManager.getConnection(url, username, password);
    	    return conn;
        }catch(ClassNotFoundException e){
        	SQLException ex = new SQLException("Driver not found: " + driver);
        	ex.initCause(e);
        	throw ex;
        }
        //return null;
    }

    public boolean isConnected(){
    	try {
			return conn != null && !conn.isClosed();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
    }

    public void closeConnection(Connection conn){
		if (conn == null)return;
		try{
            if (conn.isClosed())return;
            conn.commit();
            conn.close();
        }catch(Exception e){}
    }

    public Connection getConnection(){
    	return conn;
    }

	public void closeCurrentConnection() {
		closeConnection(conn);
	}

    private Connection conn;
    private String username;
    private String password;
    private String url;
    private String driver;

}
