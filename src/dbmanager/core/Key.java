package dbmanager.core;

public class Key {

	private short keySeq;
	private String pkName;
	private Column column;
	
	public short getKeySeq() {
		return keySeq;
	}
	public void setKeySeq(short keySeq) {
		this.keySeq = keySeq;
	}
	public String getPkName() {
		return pkName;
	}
	public void setPkName(String pkName) {
		this.pkName = pkName;
	}

	public void setColumn(Column column) {
		this.column = column;
	}
	public Column getColumn() {
		return column;
	}
	
	
}
