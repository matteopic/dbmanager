package dbmanager.gui;

import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import dbmanager.ControllerMaster;
import dbmanager.Master;
import dbmanager.table.TabellaResultSet;
import dbmanager.table.TableCellRenderer;

public class InfoDb extends JDialog {

	private ControllerMaster controller;
	
	public InfoDb(ControllerMaster controller, Master master){
		super(master, "InfoDb", false);
		this.controller = controller;
		initGUI();
	}

	private void initGUI(){
		JTabbedPane tabbed = new JTabbedPane();
		setContentPane(tabbed);

		ResultSetTableModel model;
		JTable table;
		try {
			table = new JTable(controller.getGeneralInfo());
			table.setDefaultRenderer(Object.class, new TableCellRenderer());
			tabbed.add("General", new JScrollPane(table));
			
			model = controller.getTableInfo();
			table = new TabellaResultSet(model);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			tabbed.add("Tables", new JScrollPane(table));

			model = controller.getColumnInfo();
			table = new TabellaResultSet(model);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			tabbed.add("Columns", new JScrollPane(table));
			
			model = controller.getTypeInfo();
			table = new TabellaResultSet(model);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			tabbed.add("Types", new JScrollPane(table));
			
			model = controller.getAttributesInfo();
			table = new TabellaResultSet(model);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			tabbed.add("Attributes", new JScrollPane(table));
	
			/*model = controller.getPrimaryKeys();
			table = new JTable(model);
			tabbed.add("Primary Keys", new JScrollPane(table));*/
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
