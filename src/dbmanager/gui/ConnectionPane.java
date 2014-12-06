package dbmanager.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.StringTokenizer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

import dbmanager.autocompletion.SqlCompletionModel;
import dbmanager.autocompletion.TextPaneAutoCompletion;
import dbmanager.core.Table;

public class ConnectionPane extends JPanel {

	public ConnectionPane(){
		setLayout(new BorderLayout());
		JSplitPane split = new JSplitPane();

		sql = new TextPaneAutoCompletion(null);
		sql.setText("");

		JScrollPane scroll = new JScrollPane(sql,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		NumeroRighe numeroRighe = new NumeroRighe(sql);
		scroll.setRowHeaderView(numeroRighe);

		split.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
		split.add(scroll, javax.swing.JSplitPane.TOP);

		bottomPane = new BottomPane();
		split.add(bottomPane, javax.swing.JSplitPane.BOTTOM);

		add(split, BorderLayout.CENTER);
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
		return sql.getText();
	}

	public void setQuery(String query) {
		sql.setText(query);
	}
	
	public void setTabelle(Table[] tabelle) {
		model = new SqlCompletionModel();
		model.setTabelle(tabelle);

//		sql = new TextPaneAutoCompletion(model);
//		jScrollPane1.getViewport().setView(sql);
		//TODO testare questa fix
		sql.setModel(model);
	}
	
	public void setKeywords(String keys, String nFunction, String sFunction,
			dbmanager.core.Type[] types) {
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

		for (int i = 0; i < types.length; i++) {
			ssd.addDataType(types[i].getName());
		}

		sql.setStyledDocument(ssd);
	}

	public void appendMessage(String message) {
		bottomPane.showMessage(message);
	}
	
	private TextPaneAutoCompletion sql;
	private SqlCompletionModel model;
	private BottomPane bottomPane;

}
