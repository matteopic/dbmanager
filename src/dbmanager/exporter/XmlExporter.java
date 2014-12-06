package dbmanager.exporter;

import java.io.FileOutputStream;

import javax.swing.table.TableModel;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

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

			FileOutputStream out = new FileOutputStream(fileToSave);

			OutputFormat outputFormat = new OutputFormat(doc);
			outputFormat.setEncoding("UTF-8");
			outputFormat.setIndent(4);
            outputFormat.setIndenting(true);

			XMLSerializer xmlser = new XMLSerializer(out, outputFormat);
			xmlser.serialize(doc);

			out.flush();
			out.close();
	}
}