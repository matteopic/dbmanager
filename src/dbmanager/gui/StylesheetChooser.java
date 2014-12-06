package dbmanager.gui;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class StylesheetChooser extends JDialog{

    private static final long serialVersionUID = -9184941868986464311L;
    public StylesheetChooser(Frame owner){
		super(owner, "Database di destinazione" ,true);
		initGUI();
	}

	public void initGUI(){
		File xslDir = new File("xsl");
		xsl = xslDir.listFiles( new XSLFileFilter() );

		final JList lista = new JList(xsl);
		lista.setCellRenderer(new DefaultListCellRenderer(){
			private static final long serialVersionUID = -1509422961952557817L;

			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				File f = (File)value;
				String nomeFile = f.getName();
				return super.getListCellRendererComponent(list, nomeFile, index, isSelected, cellHasFocus);
			}
		});
		JScrollPane scroll = new JScrollPane(lista);
		getContentPane().setLayout( new GridBagLayout() );

		final JButton ok = new JButton("Ok");
		final JButton annulla = new JButton("Annulla");
        final JButton xml = new JButton("Xml");

		ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				int index = lista.getSelectedIndex();
				if (index == -1)selected = null;
				else selected = xsl[index];
                pureXml = false;
				dispose();
				}
			}
		);
        
        xml.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                selected = null;
                pureXml = true;
                dispose();
                }
            }
        );


		annulla.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				selected = null;
                pureXml = false;
				dispose();
				}
			}
		);


		GridBagConstraints constr = new GridBagConstraints();
		constr.insets = new Insets(5, 5, 5, 5);
		constr.gridwidth = 3;
		constr.fill = GridBagConstraints.HORIZONTAL;
		getContentPane().add(scroll, constr);

		constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 1;
		constr.insets = new Insets(5, 5, 5, 5);
		getContentPane().add(ok, constr);

 		constr = new GridBagConstraints();
		constr.gridx = 1;
		constr.gridy = 1;
		constr.insets = new Insets(5, 5, 5, 5);
		getContentPane().add(annulla, constr);

        constr = new GridBagConstraints();
        constr.gridx = 1;
        constr.gridy = 2;
        constr.insets = new Insets(5, 5, 5, 5);
        getContentPane().add(xml, constr);

		setResizable(false);
		pack();
		setLocationRelativeTo(null);
	}


	public File getSelectedTemplate(){
		return selected;
	}
    
    public boolean isPureXml(){
        return pureXml;
    }



	private class XSLFileFilter implements FileFilter{

		public boolean accept(File file){
			return (file.isFile() && file.getName().toLowerCase().endsWith(".xsl"));
		}
	}




private File[]xsl;
private File selected;
private boolean pureXml;
}