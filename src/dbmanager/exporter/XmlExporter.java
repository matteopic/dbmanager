package dbmanager.exporter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.table.TableModel;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class XmlExporter{
	public XmlExporter(){}

    public void esporta(TableModel dati, String fileToSave)throws Throwable{
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

			String root = "RECORDS";
			String elementName = "RECORD";

			Element records = doc.createElement(root);
			doc.appendChild(records);

			int rows = dati.getRowCount();
			int column = dati.getColumnCount();

			Element element, subElement;
			Text textElement;
			Object obj;
			for (int row = 0; row < rows; row++){
				element = doc.createElement(elementName);
				for (int col = 0; col < column; col++){
					subElement = doc.createElement( dati.getColumnName(col) );

					obj = dati.getValueAt(row, col);
					if (obj != null)textElement = doc.createTextNode( obj.toString() );
					else textElement = doc.createTextNode( null );

					subElement.appendChild(textElement);
					element.appendChild(subElement);
				}
				records.appendChild(element);
			}

			save(doc, new File(fileToSave));
	}

    private void save(Document doc, File writeTo) throws IOException, TransformerException{
		FileOutputStream out = new FileOutputStream(writeTo);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        DOMSource source = new DOMSource(doc);
        StreamResult result =  new StreamResult(out);
        transformer.transform(source, result);

		out.flush();
		out.close();    	
    }
}