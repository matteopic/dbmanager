package dbmanager.event;
import java.sql.Connection;
public class ConnectionEvent{

	public ConnectionEvent(Connection conn){
		this.conn = conn;
	}

	public Connection getConnection(){
		return conn;
	}

	private Connection conn;
}