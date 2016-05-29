package dbmanager.tools;

import java.util.ArrayList;
import java.util.List;

import dbmanager.core.Catalog;
import dbmanager.core.Table;

/**
 *
 * @author Matteo Piccinini
 */
public class CatalogDifferences extends AbstractDifferences {

	public CatalogDifferences(Catalog c1, Catalog c2) {
		super(Subject.Table);
		this.c1 = c1;
		this.c2 = c2;
		List<String> tableNames1 = tableNamesList(c1);
		List<String> tableNames2 = tableNamesList(c2);
		processDiffs(tableNames1, tableNames2);
	}

	private List<String> tableNamesList(Catalog cat) {
		ArrayList<String> list = new ArrayList<String>(cat.getTables().size());
		for (Table tab : cat.getTables())
			list.add(tab.getName());
		return list;
	}

	public DifferenceResult getSubDifferences(String element) {
		Table t1 = c1.getTable(element);
		Table t2 = c2.getTable(element);
		return new ColumnsDifferences(t1, t2);
	}

	private Catalog c1, c2;
}
