package dbmanager.plugins;

import java.awt.Component;
import java.sql.Connection;

public interface Plugin {

	void setCurrentConnection(Connection conn);
	
	public Component getToolbarComponent();
}
