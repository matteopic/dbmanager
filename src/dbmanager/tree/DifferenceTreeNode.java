/**
 * DifferenceTreeNode.java
 *
 * Creato il 13-mar-2006 17.50.30
 */
package dbmanager.tree;

import javax.swing.tree.DefaultMutableTreeNode;

import dbmanager.tools.DifferenceResult.Subject;

/**
 *
 * @author Matteo Piccinini
 */
public class DifferenceTreeNode extends DefaultMutableTreeNode {

	/**
     * 
     */
	private static final long serialVersionUID = -7020451427071979161L;
	public static final int ADD = 1;
	public static final int DEL = 2;
	public static final int NONE = 0;

	public DifferenceTreeNode(int diffType, String nodeName, Subject subject) {
		setUserObject(nodeName);
		this.diffType = diffType;
		this.nodeName = nodeName;
		this.subject = subject;
	}

	/**
	 * @return Returns the diffType.
	 */
	public int getDiffType() {
		return diffType;
	}

	public String getNodeName() {
		return nodeName;
	}

	private String nodeName;
	private int diffType;
	private String differenceKey;

	public void setDifferenceKey(String differenceKey) {
		this.differenceKey = differenceKey;
	}
	
	public String getDifferenceKey() {
		return differenceKey;
	}
	
	public Subject getSubject() {
		return subject;
	}

	private Subject subject;
}
