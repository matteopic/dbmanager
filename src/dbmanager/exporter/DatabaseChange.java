package dbmanager.exporter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import dbmanager.core.Column;
import dbmanager.core.Table;

public class DatabaseChange {

	private Document doc;
	private Element databaseChangeLog;

	public DatabaseChange() {
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.newDocument();
			databaseChangeLog = doc.createElement("databaseChangeLog");
			doc.appendChild(databaseChangeLog);
		} catch (ParserConfigurationException e) {
			throw new IllegalStateException(e);
		}
	}

	public void createTable(Table table) {
		Element createTable = doc.createElement("createTable");
		createTable.setAttribute("tableName", table.getName());

		Element changeset = doc.createElement("changeSet");
		changeset.appendChild(createTable);

		for(Column col : table.getColumns()){
			Element column = doc.createElement("column");
			column.setAttribute("name", col.getName());
			column.setAttribute("type", col.getType().getName());
			createTable.appendChild(column);
		}

		databaseChangeLog.appendChild(changeset);
	}
}
