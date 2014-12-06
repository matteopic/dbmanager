/* Sviluppato da Matteo Piccinini */

package dbmanager.gui;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import dbmanager.SQLConverter;

public class ResultSetTableModel extends AbstractTableModel {

    private static final long serialVersionUID = -1471898938341474221L;
    public ResultSetTableModel(ResultSet result) throws SQLException {
		dformatter = new SimpleDateFormat("dd/MM/yyyy");
		tformatter = new SimpleDateFormat("HH:mm:ss");
		dtformatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        setResultSet(result);
    }

    /* (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#findColumn(java.lang.String)
     */
    public int findColumn(String columnName) {
        for(int i = 0; i < nomiColonne.length; i++){
            String name = nomiColonne[i];

            if(isIgnoreCase() && name.equalsIgnoreCase(columnName)) return i;
            else if(!isIgnoreCase() && name.equals(columnName))return i;
        }
        return -1;
    }
    
    public String getColumnName(int index) {
        return nomiColonne[index];
    }

    public int getColumnCount() {
        return numeroColonne;
    }

    public int getRowCount() {
        return numeroRighe;
    }

    public boolean isReadOnly(){ return readOnly; }

    public Object getValueAt(int row, int col) {
        Object obj = ((Object[]) rows.get(row)) [col];
        if (obj == null)return null;

        if (obj instanceof java.sql.Date)return dformatter.format((java.util.Date)obj);
        if (obj instanceof java.sql.Time)return tformatter.format((java.util.Date)obj);
        if (obj instanceof java.sql.Timestamp)return dtformatter.format((java.util.Date)obj);
        return obj;
    }


    public void setValueAt(Object obj, int row, int col) {
		try{
			if(!readOnly){
				if(rs.absolute(row + 1)){

					Object sqlObject = SQLConverter.convert((String)obj, tipiColonne[col]);
					rs.updateObject(col + 1, sqlObject);
					rs.updateRow();
					((Object[]) rows.get(row)) [col] = obj;
					fireTableCellUpdated(row, col);
				}
			}
		}catch(Exception e){
			System.out.println(e);
		}


    }


    public boolean isCellEditable(int rowIndex, int columnIndex){
		return !readOnly;
	}


    public ResultSet getResultSet() {
        return rs;
    }


    public void setResultSet(ResultSet rs) throws SQLException {
        this.rs = rs;
        ResultSetMetaData rsMeta = rs.getMetaData();
        numeroColonne = rsMeta.getColumnCount();

        nomiColonne = new String[numeroColonne];
        tipiColonne = new int[numeroColonne];

        for (int i = 0; i < numeroColonne; i++) {
            nomiColonne[i] = rsMeta.getColumnName(i + 1);
            tipiColonne[i] = rsMeta.getColumnType(i + 1);
        }

        rows = new ArrayList();
        Object[] record = null;
        while (rs.next()) {
            record = new Object[numeroColonne];
            for (int i = 0; i < numeroColonne; i++) {
                record[i] = rs.getObject(i + 1);
            }
            rows.add(record);
        }
        numeroRighe = rows.size();

        readOnly = !(rs.getConcurrency() == ResultSet.CONCUR_UPDATABLE && rs.getType() == ResultSet.TYPE_SCROLL_INSENSITIVE);
    }

    public Object getValueAt(int row, String column){
		return getValueAt(row, findColumn(column));
    }

    public Class getColumnClass(int index){
		int sqlType = tipiColonne[index];
		switch (sqlType){
			case Types.BOOLEAN:
				return Boolean.class;

			default:
				return String.class;
		}
	}
    
    public void setIgnoreCase(boolean ignore){
        this.ignoreCase = ignore;
    }
    
    /**
     * @return Returns the ignoreCase.
     */
    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    private boolean ignoreCase;
    private List rows;
    private ResultSet rs;
    private int numeroColonne, numeroRighe;
    private String[] nomiColonne;
    private int[] tipiColonne;
    private SimpleDateFormat dformatter, tformatter, dtformatter;
    private boolean readOnly = true;
}