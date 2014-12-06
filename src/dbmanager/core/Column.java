package dbmanager.core;
public class Column{
    
	public Column(){
	    type = new Type();
    }

	/**Imposta il nome della colonna*/
	public void setName(String name){
		this.name = name;
	}

	/**Restituisce il nome della colonna*/
	public String getName(){
		return name;
	}

	public void setType(Type type){
		this.type = type;
	}

	public Type getType(){
		return type;
	}

	public void setAllowNull(boolean allow){
		nullable = allow;
	}

	public boolean allowNull(){
		return nullable;
	}

	public void setLength(int length){
		this.length = length;
	}

	public int getLength(){
		return length;
	}

	public String toString(){
        String ret = getName() + " " + getType().getName() +"("+getLength()+")" + 
        (allowNull() ? " NULL" : " NOT NULL");
        if (getDefaultValue() != null)ret += " DEFAULT " + getDefaultValue();
        return ret;
	}

	public void setPrimaryKey(boolean primary){ this.primary = primary; }

	public boolean isPrimaryKey(){ return primary; }

	public void setKeySequence(int increment){ this.increment = increment; }

	public int getKeySequence(){ return increment; }

	public String getDefaultValue(){ return defaultValue; }

	public void setDefaultValue(String defaultValue){ this.defaultValue = defaultValue; }

	private String name, defaultValue;
	private Type type;
	private int length, increment;
	private boolean nullable, primary;

}