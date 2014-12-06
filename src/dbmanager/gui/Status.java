package dbmanager.gui;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JToolBar;

public class Status extends JToolBar{
	
    private static final long serialVersionUID = -3636293349954443668L;
    public Status(){
		initGUI();
	}

	public void setNumeroRecord(int numero){
		nrecord.setText("Numero Record " + numero);
	}

	public void setMessage(String txt){
		message.setText(txt);
	}

	public void initGUI(){
		setFloatable(false);
		nrecord = new JLabel();
		message = new JLabel();

		setLayout(new GridBagLayout());

		GridBagConstraints constr = new GridBagConstraints();
		constr.gridx=0;
		add(message, constr);

		constr = new GridBagConstraints();
		constr.gridx=1;
		add(nrecord, constr);
	}

	private JLabel nrecord;
	private JLabel message;
}