package Grupo4.Lab2.Config.Converters;

import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKBReader;
import org.locationtech.jts.io.WKBWriter;
import org.postgresql.util.PGobject;
import org.sql2o.converters.Converter;
import org.sql2o.converters.ConverterException;

import java.sql.SQLException;

public class LineStringConverter implements Converter<LineString> {

    @Override
    public LineString convert(Object val) throws ConverterException {
        if (val == null) {
            return null;
        }
        try {
            if (val instanceof PGobject) { // PostGIS devuelve PGobject
                PGobject pgObject = (PGobject) val;
                if ("geometry".equals(pgObject.getType())) {
                    // El valor es usualmente hexadecimal WKB
                    // pgObject.getValue() no declara SQLException
                    byte[] bytes = WKBReader.hexToBytes(pgObject.getValue());
                    return (LineString) new WKBReader().read(bytes); // read() puede lanzar ParseException
                }
            } else if (val instanceof String) { // Podría ser WKB hexadecimal como String
                 byte[] bytes = WKBReader.hexToBytes((String) val);
                 return (LineString) new WKBReader().read(bytes); // read() puede lanzar ParseException
            } else if (val instanceof byte[]) { // Podría ser WKB binario
                return (LineString) new WKBReader().read((byte[]) val); // read() puede lanzar ParseException
            }
            // Si ninguna conversión fue posible
            throw new ConverterException("Cannot convert object of type " + val.getClass().getName() + " to LineString");
        } catch (ParseException e) { // Solo capturamos ParseException aquí
            throw new ConverterException("Error converting PostGIS geometry to LineString", e);
        }
    }

    @Override
    public Object toDatabaseParam(LineString val) { // No puede declarar 'throws ConverterException' o 'throws SQLException'
        if (val == null) {
            return null;
        }
        // WKBWriter no debería lanzar excepciones chequeadas en estas operaciones comunes
        WKBWriter writer = new WKBWriter(2, true); // 2D, include SRID (true)
        byte[] wkb = writer.write(val);
        
        PGobject pgObject = new PGobject();
        pgObject.setType("geometry"); // El tipo para PostGIS
        try {
            // Convertir WKB a formato hexadecimal que PostGIS espera
            pgObject.setValue(WKBWriter.toHex(wkb)); // setValue() puede lanzar SQLException
        } catch (SQLException e) {
            // Envolvemos la SQLException (chequeada) en una RuntimeException
            // ya que este método no puede declarar SQLException en su firma.
            throw new RuntimeException("Error converting LineString to database parameter for PGobject", e);
        }
        return pgObject;
    }
}
