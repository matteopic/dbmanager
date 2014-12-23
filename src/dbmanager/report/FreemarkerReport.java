package dbmanager.report;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class FreemarkerReport {

	private Configuration cfg;
	private File templatesDirectory;

	public FreemarkerReport() {
		cfg = new Configuration();
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		try {
			templatesDirectory = new File("ftl");
			cfg.setDirectoryForTemplateLoading(templatesDirectory);
		} catch (IOException e) {
			templatesDirectory = null;
			e.printStackTrace();
		}
	}

	public void showReport(Map<?, ?> dataModel, String template, String windowTitle) {
		try {
			Template temp = cfg.getTemplate(template);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			Writer out = new OutputStreamWriter(baos);
			temp.process(dataModel, out);
			out.flush();
			out.close();

			URL baseUrl = null;
			if(templatesDirectory != null)baseUrl = templatesDirectory.toURI().toURL();

			byte[] bytes = baos.toByteArray();
			String html = new String(bytes);

			JEditorPane jep = new JEditorPane("text/html", html);
			jep.setEditable(false);
//
//			
//			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
//			DocumentSource docSource = new StreamDocumentSource(bais, null, "text/html");
//			DOMSource parser = new DefaultDOMSource(docSource);
//			Document doc = parser.parse(); // doc represents the obtained DOM
//			DOMAnalyzer da = new DOMAnalyzer(doc, baseUrl);
//
//			BrowserCanvas browser = new BrowserCanvas(da.getRoot(), da, baseUrl);
//			browser.createLayout(new java.awt.Dimension(1000, 600));
			// Create frame with a specific size.
			JFrame frame = new JFrame(windowTitle);
			frame.setIconImage(new ImageIcon("img/kexi.png").getImage());
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setSize(600, 400);
//			frame.setContentPane(browser);
			frame.getContentPane().add(new JScrollPane(jep));
			frame.setVisible(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
