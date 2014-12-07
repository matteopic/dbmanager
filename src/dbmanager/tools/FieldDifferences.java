/**
 * FieldDifferences.java
 *
 * Creato il 04/set/06 18:34:58
 */
package dbmanager.tools;

import dbmanager.core.Column;

/**
 *
 * @author Matteo Piccinini
 */
public class FieldDifferences extends AbstractDifferences{

//	private Map<String, DifferenceResult> diffs;
	

    public FieldDifferences(Column c1, Column c2){
//    	setSubDifferences("name", new DifferenceResult());
    	setSubDifferences("type", new DifferenceResult());
    	setSubDifferences("length", new DifferenceResult());
    	setSubDifferences("nullable", new DifferenceResult());
    	setSubDifferences("defaultValue", new DifferenceResult());

//    	String name1 = c1.getName();
//    	String name2 = c2.getName();
//    	checkDifferences("name", name1, name2);
//
//    	String type1 = c1.getType().getName();
//    	String type2 = c2.getType().getName();
//    	checkDifferences("type", type1, type2);

        if (c1 == null && c2 != null){
//        	getSubDifferences("name").leftNotHave(c2.getName());
        	getSubDifferences("type").leftNotHave(c2.getType().getName());
        	getSubDifferences("length").leftNotHave(((Integer)c2.getLength()).toString());
        	getSubDifferences("nullable").leftNotHave(((Boolean)c2.allowNull()).toString());
        	getSubDifferences("defaultValue").leftNotHave(c2.getDefaultValue());
        }
        else if (c1 != null && c2 == null){
//        	getSubDifferences("name").rightNotHave(c1.getName());
        	getSubDifferences("type").rightNotHave(c1.getType().getName());
        	getSubDifferences("length").rightNotHave(((Integer)c1.getLength()).toString());
        	getSubDifferences("nullable").rightNotHave(((Boolean)c1.allowNull()).toString());
        	getSubDifferences("defaultValue").rightNotHave(c1.getDefaultValue());
        }
        else if(c1 != null && c2 != null){
        	String type1 = c1.getType().getName();
        	String type2 = c2.getType().getName();
        	checkDifferences("type", type1, type2);

        	Integer length1 = c1.getLength();
        	Integer length2 = c2.getLength();
        	checkDifferences("length", length1, length2);

        	Boolean nullable1 = c1.allowNull();
        	Boolean nullable2 = c2.allowNull();
        	checkDifferences("nullable", nullable1, nullable2);

        	Object defVal1 = c1.getDefaultValue();
        	Object defVal2 = c2.getDefaultValue();
        	checkDifferences("defaultValue", defVal1, defVal2);

            /*String txt1 = c1.toString();
            String txt2 = c2.toString();

            if (txt1.equals(txt2))
                addEquality(txt1);
            else{
                rightNotHave(txt1);
                leftNotHave(txt2);
            } */           
        }


//        if (c1 != null) txt1 = c1.getType().getName() + " (" + c1.getLength() + ")" + c1.getDefaultValue() + " " + c1.isPrimaryKey();
//        if (c2 != null) txt2 = c2.getType().getName() + " (" + c2.getLength() + ")" + c2.getDefaultValue() + " " + c2.isPrimaryKey();
        

//
//        processDiffs(columnNames1, columnNames2);
    }

//    public DifferenceResult getNameDifferences(){
//    	return getSubDifferences("name");
//    }
    
    public DifferenceResult getTypeDifferences(){
    	return getSubDifferences("type");
    }

    public DifferenceResult getLengthDifferences(){
    	return getSubDifferences("length");
    }
    
    public DifferenceResult getNullableDifferences(){
    	return getSubDifferences("nullable");
    }

    public DifferenceResult getDefaultValueDifferences(){
    	return getSubDifferences("defaultValue");
    }

    private void checkDifferences(String key, Object valLeft, Object valRight){
    	DifferenceResult result = getSubDifferences(key);
    	if(valLeft == null && valRight == null){
    		result.addEquality(null);
    	}
    	else if(valLeft == null && valRight != null)
    		result.leftNotHave(valRight.toString());
    	else if(valLeft != null && valRight == null)
    		result.rightNotHave(valLeft.toString());    	
    	else if(valLeft.equals(valRight))
    		result.addEquality(valLeft.toString());
    	else if(!valLeft.equals(valRight)){
    		result.leftNotHave(valRight.toString());
    		result.rightNotHave(valLeft.toString());
    	}
    }

}
