package dbmanager.event;
public interface ConnectionListener{

	public void connectionEstabilished(ConnectionEvent evt);

	public void connectionClosed(ConnectionEvent evt);

}