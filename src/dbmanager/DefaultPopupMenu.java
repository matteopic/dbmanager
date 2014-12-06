package dbmanager;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.text.JTextComponent;

public class DefaultPopupMenu extends JPopupMenu{

    private static final long serialVersionUID = -6181971988572076525L;
    public DefaultPopupMenu(JTextComponent invoker){
		setInvoker(invoker);
		this.invoker = invoker;
		initMenu();
		initListener();
	}

	private void initMenu(){
		taglia = new JMenuItem("Taglia");
		copia = new JMenuItem("Copia");
		incolla = new JMenuItem("Incolla");
		elimina = new JMenuItem("Elimina");
		seleziona = new JMenuItem("Seleziona Tutto");

		add(taglia);
		add(copia);
		add(incolla);
		add(elimina);
		addSeparator();
		add(seleziona);
	}

	private void initListener(){
		taglia.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
					taglia();
				}
			}
		);

		copia.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
					copia();
				}
			}
		);

		incolla.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
					incolla();
				}
			}
		);

		elimina.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
					elimina();
				}
			}
		);

		seleziona.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
					seleziona();
				}
			}
		);

		invoker.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent evt){
				if (evt.getButton() == MouseEvent.BUTTON3){
					mostra(evt.getX(), evt.getY());
				}
			}
		}
		);


	}
	public void mostra(int x, int y){
		show(invoker,x,y);
	}

	private void taglia(){
		invoker.cut();
	}

	private void copia(){
		invoker.copy();
	}

	private void incolla(){
		invoker.paste();
	}

	private void elimina(){
		invoker.replaceSelection(null);
	}

	private void seleziona(){ invoker.selectAll(); }

	public void setVisible(boolean visible){
		boolean textSelected = (invoker.getSelectedText() != null);
		if (visible){
			taglia.setEnabled(textSelected);
			copia.setEnabled(textSelected);
			incolla.setEnabled(textSelected);
			elimina.setEnabled(textSelected);

			incolla.setEnabled(getClipContent() != null);
		}
		super.setVisible(visible);
	}

	private String getClipContent(){
		try{
			Clipboard appunti = Toolkit.getDefaultToolkit().getSystemClipboard();
			Transferable contents = appunti.getContents(null);
			if (contents == null)return null;


			Object obj = contents.getTransferData(DataFlavor.stringFlavor);
			if (obj == null)return null;

			if (obj instanceof String)return (String)obj;
			else return null;
		}catch(Exception e){
			return null;
		}
	}

private JMenuItem taglia, copia, incolla, elimina, seleziona;
private JTextComponent invoker;
}