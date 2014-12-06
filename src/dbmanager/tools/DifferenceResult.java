/**
 * DifferenceResult.java
 *
 * Creato il 20-gen-2006 12.28.44
 */
package dbmanager.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Matteo Piccinini
 */
public class DifferenceResult {

    public DifferenceResult(){
        equality = new ArrayList();
        leftNotHave = new ArrayList();
        rightNotHave = new ArrayList();
        subs = new HashMap();
    }
    
    public Enumeration elements(){
        int elementsSize = equality.size() + leftNotHave.size() + rightNotHave.size();
        List list = new ArrayList(elementsSize);
        list.addAll(equality);
        list.addAll(leftNotHave);
        list.addAll(rightNotHave);
        Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
        return Collections.enumeration(list);
    }
    
    /**
     * Restituisce -1 se non sta a sinistra, 0 se è comune, +1 se non è a destra
     * @param name
     * @return
     */
    public int getDiffType(String name){
        if(leftNotHave.contains(name))
            return -1;
        else if(equality.contains(name))
            return 0;
        else if(rightNotHave.contains(name))
            return 1;
        else
            throw new IllegalArgumentException(name + " non è presente");
    }
    
    public void addEquality(String name){
        equality.add(name);
    }
    
    public void leftNotHave(String name){
        leftNotHave.add(name);
    }
    
    public void rightNotHave(String name){
        rightNotHave.add(name);
    }
    
    /**
     * @return Returns the equality.
     */
    public List getEquality() {
        return equality;
    }
    
    /**
     * @return Returns the leftNotHave.
     */
    public List getLeftNotHave() {
        return leftNotHave;
    }
    
    /**
     * @return Returns the rightNotHave.
     */
    public List getRightNotHave() {
        return rightNotHave;
    }
    
    public String toString(){
        StringBuffer sb = new StringBuffer();
        Iterator iter = leftNotHave.iterator();
        while (iter.hasNext()){
            sb.append("<<< ").append(iter.next());
            if(iter.hasNext())sb.append("\n");
        }
        if (leftNotHave.size() > 0 && rightNotHave.size() > 0)
            sb.append("\n");
        
        iter = rightNotHave.iterator();
        while (iter.hasNext()){
            sb.append(">>> ").append(iter.next());
            if(iter.hasNext())sb.append("\n");
        }
        return sb.toString();
    }
    
    public boolean existsDifferences(){
        return (leftNotHave.size() > 0 || rightNotHave.size() > 0);
    }
    
    public DifferenceResult getSubDifferences(String element){
        return (DifferenceResult)subs.get(element);
    }
    
    public void setSubDifferences(String element, DifferenceResult sub){
        subs.put(element, sub);
    }
    
    private List equality, leftNotHave, rightNotHave;
    private Map subs;
}
