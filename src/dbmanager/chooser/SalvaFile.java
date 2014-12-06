package dbmanager.chooser;
import java.awt.Component;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class SalvaFile{


	public SalvaFile(Component parent, String defaultExtension){
		this.parent = parent;
		this.defaultExtension = defaultExtension;

	}

	private boolean confermaSovrascittura(File fileToSave)throws IOException{
		int confirm = JOptionPane.showConfirmDialog(
				parent,
				fileToSave.getCanonicalPath() + " esiste già.\nSostituirlo ?",
				"Salva con nome",
				JOptionPane.YES_NO_CANCEL_OPTION);

		return confirm == JOptionPane.YES_OPTION;
	}

	private void scegliFile(){
		file = null;
		try{
			JFileChooser chooser = new JFileChooser();
			int returnVal = chooser.showSaveDialog(parent);
//			System.out.println(returnVal);
			if(returnVal != JFileChooser.APPROVE_OPTION)return;

			File fileToSave = chooser.getSelectedFile();
			String filename = fileToSave.getName();

			if (filename.indexOf(".") == -1){
				filename += "." + defaultExtension;
				File dir = fileToSave.getParentFile();
				fileToSave = new File(dir, filename);
			}

			if (!fileToSave.exists()){ file = fileToSave; }
			else if (fileToSave.exists() && confermaSovrascittura(fileToSave)){ file = fileToSave; }
			else file = null;
		}catch(Exception e){
			e.printStackTrace();
			file = null;
		}
	}

	public File getFile(){
		scegliFile();
		return file;
	}

private File file;
private Component parent;
private String defaultExtension;
}