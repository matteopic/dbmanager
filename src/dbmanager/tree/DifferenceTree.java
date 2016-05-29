/**
 * DifferenceTree.java
 *
 * Creato il 05/set/06 15:00:53
 */
package dbmanager.tree;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import dbmanager.core.Catalog;
import dbmanager.tools.CatalogDifferences;
import dbmanager.tools.DifferenceResult.Subject;

/**
 *
 * @author Matteo Piccinini
 */
public class DifferenceTree extends JFrame {

	private static final long serialVersionUID = -4088830710260799907L;

	public DifferenceTree(final Catalog c1, final Catalog c2) {
		super("Databases Differences");
		final JLabel label = new JLabel("Processing...");
		getContentPane().add(label);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initGUI(c1, c2);
				getContentPane().remove(label);
			}
		});
	}

	private void initGUI( Catalog c1,  Catalog c2) {
		// pack();
		// setLocationRelativeTo(null);
		buttonExpand = new JButton("Espandi");
		buttonCollapse = new JButton("Comprimi");
		JToolBar toolbar = new JToolBar();
		toolbar.add(buttonExpand);
		toolbar.add(buttonCollapse);

		sqlEditor = new JTextArea();

		try {
			CatalogDifferences td = new CatalogDifferences(c1, c2);
			DifferenceTreeComposer dtc = new DifferenceTreeComposer();
			dtc.setDifferences(td);

			tree = new AdvancedTree(dtc.getModel());
			tree.setCellRenderer(new DifferenceTreeRenderer());
			tree.expandAll();
			tree.addTreeSelectionListener(new TreeSelectionListener() {

				public void valueChanged(TreeSelectionEvent e) {
					Object selected = e.getPath().getLastPathComponent();
					if(selected instanceof DifferenceTreeNode == false)return;

					DifferenceTreeNode dtn = (DifferenceTreeNode)selected;
					if(dtn.getDiffType() == DifferenceTreeNode.NONE)return;
					generateSQL(dtn);
					System.out.println(dtn);
				}

			});

			buttonExpand.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					tree.expandAll();
				}
			});

			buttonCollapse.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					tree.collapseAll();
				}
			});
			
			getContentPane().setLayout(new BorderLayout());
			getContentPane().add(toolbar, BorderLayout.NORTH);
			getContentPane().add(new JScrollPane(tree), BorderLayout.WEST);
			getContentPane().add(new JScrollPane(sqlEditor), BorderLayout.CENTER);
			pack();
			setLocationRelativeTo(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void generateSQL(DifferenceTreeNode dtn) {
		Subject subject = dtn.getSubject();
		DifferenceTreeNode parent = (DifferenceTreeNode)dtn.getParent();
		switch (subject) {
		
		case DataLength:
			if(parent.getDiffType() == DifferenceTreeNode.ADD)
				generateSQL(parent);
			break;

		case DataType:
			if(parent.getDiffType() == DifferenceTreeNode.ADD)
				generateSQL(parent);
			break;

		default:
			break;
		}
		
	}

//	public static void main(String[] args) {
//		try {
//			String url1 = "jdbc:mysql://localhost/test1";
//			String driver1 = "com.mysql.jdbc.Driver";
//			Class.forName(driver1);
//			Connection conn1 = DriverManager.getConnection(url1, "root", "root");
//
//			String url2 = "jdbc:mysql://localhost/test2";
//			String driver2 = "com.mysql.jdbc.Driver";
//			Class.forName(driver2);
//			Connection conn2 = DriverManager.getConnection(url2, "root", "root");
//
//			DatabaseProperties prop1 = new DatabaseProperties(conn1);
//			DatabaseProperties prop2 = new DatabaseProperties(conn2);
//			conn1.close();
//			conn2.close();
//
//			DifferenceTree dt = new DifferenceTree(prop1, prop2);
//			dt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//			dt.setVisible(true);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	private JButton buttonExpand, buttonCollapse;
	private AdvancedTree tree;
	private JTextArea sqlEditor;
}
