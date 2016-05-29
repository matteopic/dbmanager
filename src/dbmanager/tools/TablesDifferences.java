/**
 * SchemaDifferences.java
 *
 * Creato il 04/set/06 18:34:01
 */
package dbmanager.tools;

import java.util.ArrayList;
import java.util.List;

import dbmanager.core.DatabaseProperties;
import dbmanager.core.Table;
import dbmanager.tools.DifferenceResult.Subject;

/**
 *
 * @author Matteo Piccinini
 */
public class TablesDifferences extends AbstractDifferences {

    public TablesDifferences(DatabaseProperties prop1, DatabaseProperties prop2){
    	super(Subject.Table);
        this.prop1 = prop1;
        this.prop2 = prop2;
        List<String> tableNames1 = tableNamesList(prop1);
        List<String> tableNames2 = tableNamesList(prop2);
        processDiffs(tableNames1, tableNames2);
    }


    private List<String> tableNamesList(DatabaseProperties prop) {
        Table[] tab = prop.getTables();
        ArrayList<String> list = new ArrayList<String>(tab.length);
        for (int i = 0; i < tab.length; i++)
            list.add(tab[i].getName());
        return list;
    }


    public DifferenceResult getSubDifferences(String element) {
        Table t1 = prop1.findTable(element);
        Table t2 = prop2.findTable(element);
//        if(t1 == null)t1 = new Table();
//        if(t2 == null)t2 = new Table();
        return new ColumnsDifferences(t1, t2);
    }

    private DatabaseProperties prop1, prop2;
}
