package dbmanager.report;

import javax.swing.JFrame;
import javax.swing.table.TableModel;

import org.jfree.report.JFreeReport;
import org.jfree.report.modules.gui.base.PreviewFrame;
import org.jfree.report.modules.parser.base.ReportGenerator;
import org.jfree.xml.ParseException;

/**
 * @author Matteo Piccinini
 */
public class Report{


    /**
     * @return Returns the dati.
     */
    protected TableModel getDati() {
        return dati;
    }

    /**
     * @param dati
     *            The dati to set.
     */
    public void setDati(TableModel dati) {
        this.dati = dati;
    }

    /**
     * @return Returns the xmlFile.
     */
    protected String getXmlFile() {
        return xmlFile;
    }

    /**
     * @param xmlFile
     *            The xmlFile to set.
     */
    public void setXmlFile(String xmlFile) {
        this.xmlFile = xmlFile;
    }

    public JFrame getReport() throws ParseException {
        try {
            ReportGenerator generator = ReportGenerator.getInstance();
            JFreeReport report = generator.parseReport(getXmlFile());
            report.setData(getDati());
            PreviewFrame preview = new PreviewFrame(report);
            return preview;
        } catch (Exception e) {
            throw new ParseException("Errore durante il parsing del report", e);
        }
    }

    private String xmlFile;

    private TableModel dati;

}