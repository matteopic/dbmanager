package dbmanager.core;
public class Type{
    
    public Type(){
        this.name = "";
    }

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setPrefix(String prefix){
		this.prefix = prefix;
	}

	public String getPrefix(){
		return prefix;
	}

	public void setSuffix(String suffix){
		this.suffix = suffix;
	}

	public String getSuffix(){
		return suffix;
	}

	public void setParams(boolean haveParams){
		params = haveParams;
	}

	public boolean haveParams(){
		return params;
	}

	public String toString(){
		return name;
	}

	public void setJavaType(int javaType){
		this.javaType = javaType;
	}

	public int getJavaType(){
		return javaType;
	}
    
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof Type))return false;
        return ((Type)obj).getName().equals(getName());
    }

private String prefix, suffix, name;
private boolean params;
private int javaType;
}