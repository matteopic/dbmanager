package dbmanager.gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;

import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

public class NumeroRighe extends JComponent {

    private static final long serialVersionUID = 1323961031433479593L;

    public NumeroRighe(JEditorPane editor){
		this.editor = editor;
		initDocumentListener();
	}

	private void initDocumentListener(){
		DocumentPropertyListener dpl = new DocumentPropertyListener();
		Document doc = editor.getDocument();
		doc.addDocumentListener(dpl);
	}


	public void fireTextChanged(){
		String text = editor.getText();
		StringReader sr = new StringReader(text);
		LineNumberReader lnr = new LineNumberReader(sr);
//		String line;
		try{
//			while((line = lnr.readLine())!= null);
            while(lnr.readLine() != null);//Scorro tutto il testo
		}catch(IOException e){}

		rows = lnr.getLineNumber();
		if (text.endsWith("\n") || rows == 0)rows++;

		int width = getGraphics().getFontMetrics().stringWidth( String.valueOf(rows) );
		int height = getGraphics().getFontMetrics().getHeight() * rows;// stringWidth( String.valueOf(rows) );
		setPreferredSize(new Dimension(width + 18, height) );
		repaint();
	}

    public void paintComponent(Graphics g) {
        Rectangle drawHere = g.getClipBounds();

        String text = null;
       	FontMetrics metrics = g.getFontMetrics();
       	int height = metrics.getHeight();
       	int ascent = metrics.getAscent();



		g.setColor(editor.getBackground());
        g.fillRect(drawHere.x, drawHere.y, drawHere.width, drawHere.height);

        g.setFont(editor.getFont());
        g.setColor(grigio);
		int value = 0;
        for (int i = 0; i < rows; i ++) {
			value = i + 1;
			text = Integer.toString(value);
			g.drawString(text, 9, (i * height) + ascent);
        }
    }


	private class DocumentPropertyListener implements DocumentListener{
		public void	changedUpdate(DocumentEvent e){
			//fireTextChanged();
		}

		public void	insertUpdate(DocumentEvent e){
			fireTextChanged();
		}

		public void removeUpdate(DocumentEvent e){
			fireTextChanged();
		}
	}





private int rows;
private JEditorPane editor;
private Color grigio = new Color(128,128,128);
//private DocumentPropertyListener dpl;
}