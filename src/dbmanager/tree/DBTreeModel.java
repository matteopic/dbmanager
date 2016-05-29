package dbmanager.tree;

import javax.swing.tree.DefaultMutableTreeNode;

import dbmanager.core.Column;
import dbmanager.core.DatabaseProperties;
import dbmanager.core.Index;
import dbmanager.core.Catalog;
import dbmanager.core.Table;

public class DBTreeModel extends DefaultMutableTreeNode {

	private static final long serialVersionUID = -770781665405839746L;

	public DBTreeModel(DatabaseProperties props) {
		super(props);
		for (Catalog schema : props.getCatalogs()) {
			add(new DBTreeModel(schema));
		}
	}

	private DBTreeModel(Catalog schema) {
		super(schema);

		// DefaultMutableTreeNode mtnTables = new
		// DefaultMutableTreeNode("Tables");
		// add(mtnTables);
		//
		// DefaultMutableTreeNode mtnIndexes = new
		// DefaultMutableTreeNode("Indexes");
		// add(mtnIndexes);

		for (Table table : schema.getTables()) {
			add(new DBTreeModel(table));
		}
		//
		// for(Table table : schema.get){
		// mtnTables.add(new DBTreeModel(table));
		// }
	}

	private DBTreeModel(Table table) {
		super(table);

		DefaultMutableTreeNode mtnColumns = new DefaultMutableTreeNode("Columns");
		add(mtnColumns);

		Column[] columns = table.getColumns();
		for (int i = 0; i < columns.length; i++) {
			mtnColumns.add(new DBTreeModel(columns[i]));
		}

		DefaultMutableTreeNode mtnIndexes = new DefaultMutableTreeNode("Indexes");
		add(mtnIndexes);

		for (Index index : table.getIndexes()) {
			mtnIndexes.add(new DBTreeModel(index));
		}

	}

	private DBTreeModel(Column column) {
		super(column);
	}

	private DBTreeModel(Index index) {
		super(index);
	}
}
