/**
 * Index.java
 *
 * Creato il 05/set/07 10:33:20
 */
package dbmanager.core;

/**
 *
 * @author Matteo Piccinini
 */
public class Index {

	public Index(String name) {
		this.name = name;
	}

	private String name;
	private Column column;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Column getColumn() {
		return column;
	}

	public void setColumn(Column column) {
		this.column = column;
	}

}
