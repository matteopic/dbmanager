/**
 * AbstrctDifferences.java
 *
 * Creato il 04/set/06 18:43:52
 */
package dbmanager.tools;

import java.util.List;

/**
 *
 * @author Matteo Piccinini
 */
public abstract class AbstractDifferences extends DifferenceResult{

    public AbstractDifferences(Subject subject) {
		super(subject);
	}

	protected void processDiffs(List<String> list1, List<String> list2) {
        String elementName;
        for (int i = 0; i < list1.size(); i++) {
            elementName = (String) list1.get(i);
            if (list2.contains(elementName)) {
                addEquality(elementName);
            } else {
                rightNotHave(elementName);
            }
        }

        for (int i = 0; i < list2.size(); i++) {
            elementName = (String) list2.get(i);
            if (!list1.contains(elementName)) {
                leftNotHave(elementName);
            }
        }
    }
    
}
