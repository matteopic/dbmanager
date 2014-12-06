/**
 * ColumnsDifferences.java
 *
 * Creato il 04/set/06 18:34:40
 */
package dbmanager.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dbmanager.core.Column;
import dbmanager.core.Table;

/**
 *
 * @author Matteo Piccinini
 */
public class ColumnsDifferences extends AbstractDifferences{

    public ColumnsDifferences(Table table1, Table table2){
        this.table1 = table1;
        this.table2 = table2;
        List columnNames1 = columnNamesList(table1);
        List columnNames2 = columnNamesList(table2);
        processDiffs(columnNames1, columnNames2);
    }
    
    private List columnNamesList(Table table) {
        if (table == null)return Collections.EMPTY_LIST;
        Column[] tab = table.getColumns();
        ArrayList list = new ArrayList();
        for (int i = 0; i < tab.length; i++)
            list.add(tab[i].getName());
        return list;
    }
    
    public DifferenceResult getSubDifferences(String element) {
        Column c1 = table1 != null ? table1.getColumn(element) : null;
        Column c2 = table2 != null ? table2.getColumn(element) : null;

        return new FieldDifferences(c1, c2);
    }

    private Table table1, table2;
}
