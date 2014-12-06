package dbmanager.gui;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.text.JTextComponent;

public class CodeCompletation extends JPopupMenu{


	/**
     * 
     */
    private static final long serialVersionUID = -8180054673721486292L;
    public CodeCompletation(){
		lista = new JList();
		nomiTabelle = new Vector();
		setPreferredSize(new Dimension(100,150));
		insert(new JScrollPane(lista), 0);

		lista.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					try{
					selected = (String)lista.getSelectedValue();
					JTextComponent invoker = (JTextComponent)getInvoker();
					invoker.getDocument().insertString(invoker.getCaretPosition(), selected, null);
					}catch(Exception ex){}
					setVisible(false);

				}
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
					selected = null;
					setVisible(false);
				}
			}
		});
	}

	public void addTabella(String nome){
		nomiTabelle.add(nome);
		Object[]nomi = nomiTabelle.toArray();
		Arrays.sort(nomi);
		lista.setListData(nomi);
	}


	public void show(Component invoker, int x, int y){
		super.show(invoker, x, y) ;
		lista.requestFocus();
	}


	public String getSelectedTable(){
		return selected;
	}


	private JList lista;
	private Vector nomiTabelle;
	private String selected;
}