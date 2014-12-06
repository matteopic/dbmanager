package dbmanager.exporter;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import dbmanager.core.Column;
import dbmanager.core.DatabaseProperties;
import dbmanager.core.Table;
import dbmanager.core.Type;


public class DbStruct{

    public Document esporta(DatabaseProperties db, File xsl, String fileToSave)throws Throwable{
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Table[]tabelle = db.getTables();
		creaTabelle(doc, tabelle);
		toSql(doc, xsl, fileToSave);

/*		FileOutputStream out = new FileOutputStream(fileToSave);

		OutputFormat outputFormat = new OutputFormat(doc);
		outputFormat.setEncoding("UTF-8");
		outputFormat.setIndent(4);
		XMLSerializer xmlser = new XMLSerializer(out, outputFormat);
		xmlser.serialize(doc);

		out.flush();
		out.close();*/
		return doc;
	}

	public void creaTabelle(Document doc, Table[]tabelle){
		Element elementTabelle = doc.createElement("tabelle");

		for(int i = 0; i < tabelle.length; i++){
			creaTabella(elementTabelle, tabelle[i]);
		}

		doc.appendChild(elementTabelle);
	}

	public void creaTabella(Element element, Table tabella){
		Document doc = element.getOwnerDocument();

//		for(int i = 0; i < tabelle.length; i++){
			Element elementTabella = doc.createElement("tabella");
			elementTabella.setAttribute("nome", tabella.getName());

			Column[]colonne = tabella.getColumns();
			creaColonne(elementTabella, colonne);
			element.appendChild(elementTabella);
//		}

	}

	public void creaColonne(Element elementTabella, Column[]colonne){
		Document doc = elementTabella.getOwnerDocument();
		Element elementColonne = doc.createElement("colonne");

		for(int i = 0; i < colonne.length; i++){
			creaColonna(elementColonne, colonne[i]);
		}

		elementTabella.appendChild(elementColonne);
	}


	public void creaColonna(Element elementColonne, Column colonna){
		Document doc = elementColonne.getOwnerDocument();
		Element elementColonna = doc.createElement("colonna");
		elementColonna.setAttribute("nome", colonna.getName());
		Type tipo = colonna.getType();
		insert(elementColonna, "tipo", String.valueOf(tipo.getJavaType()));
		insert(elementColonna, "dimensione", String.valueOf(colonna.getLength()));
		insert(elementColonna, "ammettiNull", String.valueOf(colonna.allowNull()));
		insert(elementColonna, "primaryKey", String.valueOf(colonna.isPrimaryKey()));
		if (colonna.getDefaultValue() != null)insert(elementColonna, "defaultValue", String.valueOf(colonna.getDefaultValue()));
		if(colonna.isPrimaryKey()) insert(elementColonna, "keySequence", String.valueOf(colonna.getKeySequence()));
		elementColonne.appendChild(elementColonna);
	}


	public void insert(Element element, String nome, String valore){
		Document doc = element.getOwnerDocument();
		Element el = doc.createElement(nome);
		Text txt = doc.createTextNode(valore) ;
		el.appendChild(txt);
		element.appendChild(el);
	}

	public void toSql(Document doc, File xsl, String fileToSave){

//	  throws TransformerException, TransformerConfigurationException

//		String media= null , title = null, charset = null;
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();

      		DOMSource dbStruct = new DOMSource(doc);
      		Source stylesheet = null;
            if (xsl != null)stylesheet = new StreamSource(xsl.getCanonicalFile());

			FileOutputStream fos = new FileOutputStream(fileToSave);

			Transformer transformer;
			if (stylesheet == null){
                try{ tFactory.setAttribute("indent-number", new Integer(4)); }catch(Exception e){}
				transformer = tFactory.newTransformer();
                //W3C raccomandation parameters
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            }
			else
				transformer = tFactory.newTransformer(stylesheet);

		   	transformer.transform(dbStruct, new StreamResult(fos));
		   	fos.flush();
		   	fos.close();
	  	}catch (Exception e){
	    	e.printStackTrace();
	  	}
	}
}