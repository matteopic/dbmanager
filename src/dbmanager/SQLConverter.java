package dbmanager;
import java.sql.Types;
public class SQLConverter{



	public static Object convert(String convert, int sqlType)throws Exception{
		if (convert == null)return null;

		switch (sqlType){

			case Types.VARCHAR:
				return convert;
/*
			case Types.ARRAY:
			case Types.BIGINT:
			case Types.BINARY:
			case Types.BIT:
			case Types.BLOB:
			case Types.BOOLEAN:
			case Types.CHAR:
			case Types.CLOB:
			case Types.DATALINK:
			case Types.DATE:
			case Types.DECIMAL:
			case Types.DISTINCT:
*/
			case Types.DOUBLE:
				return new Double(convert);

			case Types.FLOAT:
				return new Float(convert);

			case Types.INTEGER:
				return new Integer(convert);

			case Types.JAVA_OBJECT:
				return convert;
/*
			case Types.LONGVARBINARY:
			case Types.LONGVARCHAR:
			case Types.NULL:
			case Types.NUMERIC:
			case Types.OTHER:
			case Types.REAL:
			case Types.REF:
			case Types.SMALLINT:
			case Types.STRUCT:
			case Types.TIME:
			case Types.TIMESTAMP:
			case Types.TINYINT:
			case Types.VARBINARY:
*/

			default:
				throw new Exception("Tipo " + sqlType + "non riconosciuto ");

		}

	}

}