package dbmanager.core;

import java.util.Collection;
import java.util.HashMap;

public class Catalog {

	private String name;
	private HashMap<String, Table> tables;

	public Catalog(String name) {
		this.name = name;
		tables = new HashMap<String, Table>();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Collection<Table> getTables() {
		return tables.values();
	}

	public Table getTable(String name) {
		return tables.get(name);
	}

	public void addTable(Table table) {
		tables.put(table.getName(), table);
	}
}
