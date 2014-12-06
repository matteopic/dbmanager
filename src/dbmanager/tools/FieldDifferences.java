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

    public FieldDifferences(Column c1, Column c2){
//        String typeName1 = c1.getType().getName() + ;
//        String typeName2 = c2.getType().getName();

//        if (typeName2.equals(typeName1))
//            addEquality(typeName1);
//        
        if (c1 == null && c2 != null)
            leftNotHave(c2.toString());
        else if (c1 != null && c2 == null)
            rightNotHave(c1.toString());
        else if(c1 != null && c2 != null){
            String txt1 = c1.toString();
            String txt2 = c2.toString();

            if (txt1.equals(txt2))
                addEquality(txt1);
            else{
                rightNotHave(txt1);
                leftNotHave(txt2);
            }            
        }
//        if (c1 != null) txt1 = c1.getType().getName() + " (" + c1.getLength() + ")" + c1.getDefaultValue() + " " + c1.isPrimaryKey();
//        if (c2 != null) txt2 = c2.getType().getName() + " (" + c2.getLength() + ")" + c2.getDefaultValue() + " " + c2.isPrimaryKey();
        

//
//        processDiffs(columnNames1, columnNames2);
    }

}
