package dbmanager.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Collection;
import java.util.StringTokenizer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import dbmanager.autocompletion.SqlCompletionModel;
import dbmanager.autocompletion.TextPaneAutoCompletion;
import dbmanager.core.DatabaseProperties;
import dbmanager.core.Table;
import dbmanager.core.Type;
import dbmanager.tree.AdvancedTree;
import dbmanager.tree.DBTreeCellRenderer;
import dbmanager.tree.DBTreeModel;

public class ConnectionPane extends JPanel {

	private static final long serialVersionUID = 3691940753596588015L;
	public ConnectionPane(){
		setLayout(new BorderLayout());
		JSplitPane split = new JSplitPane();
		
		model = new SqlCompletionModel();

		sql = new TextPaneAutoCompletion(model);
		sql.setFont(Font.getFont(Font.MONOSPACED));
		sql.setText("");

		JScrollPane scroll = new JScrollPane(sql,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		NumeroRighe numeroRighe = new NumeroRighe(sql);
		scroll.setRowHeaderView(numeroRighe);

		split.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

		bottomPane = new BottomPane();

		split.add(scroll, javax.swing.JSplitPane.TOP);
		split.add(bottomPane, javax.swing.JSplitPane.BOTTOM);

		dbTree = new AdvancedTree();
		dbTree.setVisible(false);
		dbTree.setCellRenderer(new DBTreeCellRenderer());
		dbTree.setRootVisible(false);		
		add(split, BorderLayout.CENTER);
		add(new JScrollPane(dbTree), BorderLayout.WEST);
	}

	public void setResult(Component comp) {
		if (comp instanceof JTable) {
//			bottomPane.setText(null);
			bottomPane.showRecords((JTable) comp);
		} else {
//			bottomPane.showMessage(message);
//			bottomPane.setRecords(null);
		}
	}
	
	public JTable getLastResult(){
		JTable dati = bottomPane.getRecords();
		return dati;
	}
	
	
	public String getQuery() {
		String selection = sql.getSelectedText();
		return selection != null ? selection : sql.getText();
	}

	public void setQuery(String query) {
		sql.setText(query);
	}

	public void setDatabaseProperties(DatabaseProperties dbProps) {
		DBTreeModel root = new DBTreeModel(dbProps);
		dbTree.setModel(new DefaultTreeModel(root));
		dbTree.setVisible(true);
	}

	public void setTabelle(Collection<Table> tabelle) {
		model.setTabelle(tabelle);

//		sql = new TextPaneAutoCompletion(model);
//		jScrollPane1.getViewport().setView(sql);
		//TODO testare questa fix
		sql.setModel(model);
	}
	
	public void setKeywords(String keys, String nFunction, String sFunction, Collection<Type> types) {
		// System.out.println("Parole Chiave: " + keys
		// +"\nFunzioni Numeriche: "+nFunction+"\nFunzioni Stringa "+sFunction);

		SQLStyledDocument ssd = new SQLStyledDocument();
		StringTokenizer st = new StringTokenizer(keys, ",");
		String token;
		while (st.hasMoreTokens()) {
			token = st.nextToken();
			ssd.addKeyword(token);
			model.addKeyword(token);
		}

		st = new StringTokenizer(nFunction, ",");
		while (st.hasMoreTokens()) {
			token = st.nextToken();
			ssd.addNumericFunction(token);
			model.addNumericFunction(token);
		}

		st = new StringTokenizer(sFunction, ",");
		while (st.hasMoreTokens()) {
			token = st.nextToken();
			ssd.addStringFunction(token);
			model.addStringFunction(token);
		}

		for (Type type : types) {
			ssd.addDataType(type.getName());
		}

		sql.setStyledDocument(ssd);
	}

	public void appendMessage(String message) {
		bottomPane.showMessage(message);
	}
	
	private TextPaneAutoCompletion sql;
	private SqlCompletionModel model;
	private BottomPane bottomPane;
	private AdvancedTree dbTree;


}
