package dbmanager.gui;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JViewport;


public class BottomPane extends JPanel{
	/**
     * 
     */
    private static final long serialVersionUID = -1242730088375492363L;
    public BottomPane(){
		status = new Status();
		scroller = new JScrollPane();
		textarea = new JTextArea();
		textarea.setFont(Font.getFont(Font.MONOSPACED));
		setLayout(new GridBagLayout());

		GridBagConstraints constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 0;
		constr.weightx = 1;
		constr.weighty = 1;
		constr.fill = GridBagConstraints.BOTH;
		add(scroller, constr);

		constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 1;
		constr.fill = GridBagConstraints.HORIZONTAL;
		add(status, constr);

	}

	public void showRecords(JTable dati){
		this.dati = dati;
		if (dati != null){
			status.setNumeroRecord(dati.getRowCount());
			JViewport vw =  scroller.getViewport();
			vw.setView(dati);
		}else{
			status.setNumeroRecord(0);
		}


	}

	public void showMessage(String message) {
		dati = null;
		JViewport vw =  scroller.getViewport();
		vw.setView(textarea);
		textarea.append(message);
	}
	
//	public void setText(JTextArea textarea){
//		JViewport vw =  scroller.getViewport();
//		if (textarea != null)vw.setView(textarea);
//	}

	public JTable getRecords(){ return dati; }


	private Status status;
	private JTable dati;
	private JTextArea textarea;
	private JScrollPane scroller;


}