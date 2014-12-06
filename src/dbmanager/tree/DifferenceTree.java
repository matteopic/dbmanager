/**
 * DifferenceTree.java
 *
 * Creato il 05/set/06 15:00:53
 */
package dbmanager.tree;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import dbmanager.core.DatabaseProperties;
import dbmanager.tools.TablesDifferences;

/**
 *
 * @author Matteo Piccinini
 */
public class DifferenceTree extends JFrame {

	private static final long serialVersionUID = -4088830710260799907L;

	public DifferenceTree(final DatabaseProperties prop1,
			final DatabaseProperties prop2) {
		super("Databases Differences");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel label = new JLabel("Processing...");
		getContentPane().add(label);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initGUI(prop1, prop2);
			}
		});
	}

	private void initGUI(DatabaseProperties prop1, DatabaseProperties prop2) {
		// pack();
		// setLocationRelativeTo(null);

		buttonExpand = new JButton("Espandi");
		buttonCollapse = new JButton("Comprimi");
		JToolBar toolbar = new JToolBar();
		toolbar.add(buttonExpand);
		toolbar.add(buttonCollapse);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(toolbar, BorderLayout.NORTH);

		try {
			// DatabaseProperties prop1 = new DatabaseProperties(conn1);
			// DatabaseProperties prop2 = new DatabaseProperties(conn2);
			// conn1.close();
			// conn2.close();

			TablesDifferences td = new TablesDifferences(prop1, prop2);
			DifferenceTreeComposer dtc = new DifferenceTreeComposer();
			dtc.setDifferences(td);

			tree = new AdvancedTree(dtc.getModel());
			tree.setCellRenderer(new DifferenceTreeRenderer());
			tree.expandAll();

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

			getContentPane().add(new JScrollPane(tree), BorderLayout.CENTER);
			pack();
			setLocationRelativeTo(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			String url1 = "jdbc:mysql://localhost/jaco";
			String driver1 = "com.mysql.jdbc.Driver";
			Class.forName(driver1);
			Connection conn1 = DriverManager.getConnection(url1, "root", "");

			String url2 = "jdbc:mysql://192.168.0.44/jaco";
			String driver2 = "com.mysql.jdbc.Driver";
			Class.forName(driver2);
			Connection conn2 = DriverManager.getConnection(url2, "admin",
					"5ez10ne");

			DatabaseProperties prop1 = new DatabaseProperties(conn1);
			DatabaseProperties prop2 = new DatabaseProperties(conn2);
			conn1.close();
			conn2.close();

			DifferenceTree dt = new DifferenceTree(prop1, prop2);
			dt.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private JButton buttonExpand, buttonCollapse;
	private AdvancedTree tree;
}
