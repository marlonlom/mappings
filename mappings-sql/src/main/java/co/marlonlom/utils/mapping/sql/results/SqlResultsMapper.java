package co.marlonlom.utils.mapping.sql.results;

import co.marlonlom.utils.mapping.sql.annotation.SqlColumn;
import co.marlonlom.utils.mapping.sql.annotation.SqlJoin;

import java.beans.Transient;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.security.Timestamp;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Mapping utility for converting result sets into class references (single
 * object, list of objects)
 *
 * @param <T> a class reference, which may have {@linkplain co.marlonlom.utils.mapping.sql.annotation.SqlTable},
 *            {@linkplain co.marlonlom.utils.mapping.sql.annotation.SqlColumn} and/or {@linkplain co.marlonlom.utils.mapping.sql.annotation.SqlJoin} annotation
 * @author marlonlom
 */
@SuppressWarnings("all")
public final class SqlResultsMapper<T> {

    /**
     * Constant for <b>serialVersionUID</b>, for objects that implements
     * {@link Serializable} interface
     */
    private static final String SERIAL_VERSION_UID = "serialVersionUID";
    /**
     * Logger
     */
    private static final Logger LOGGER = Logger.getLogger(SqlResultsMapper.class.getSimpleName());

    /**
     * Auto-generated constructor stub
     */
    private SqlResultsMapper() {
        super();
    }

    /**
     * Returns singleton instance of the class
     *
     * @return singleton instance
     */
    public static <T> SqlResultsMapper<T> create() {
        return new SqlResultsMapper<T>();
    }

    /**
     * Perform mapping of resultset fields to the output object reference using
     * annotations
     *
     * @param rs     sql resultset for map
     * @param obj    class type
     * @param fields class reference declared fields
     * @throws IllegalAccessException   for errors related to field and annotation access
     * @throws SQLException             for errors related to information retrieval
     * @throws InstantiationException
     * @throws IllegalArgumentException
     */
    private void populateFields(ResultSet rs, T obj, Field[] fields)
            throws IllegalAccessException, SQLException, IllegalArgumentException, InstantiationException {
        String fieldName = null;

        for (Field _field : fields) {
            if (SERIAL_VERSION_UID.equals(_field.getName())) {
                continue;
            }

            _field.setAccessible(true);

            fieldName = _field.getName();

            Class<?> fieldType = _field.getType();

            Annotation[] annotations = _field.getDeclaredAnnotations();

            if (annotations != null && annotations.length > 0) {
                Class<? extends Annotation> annotationType = annotations[0].annotationType();
                if (annotationType == SqlColumn.class) {
                    SqlColumn colAnn = (SqlColumn) annotations[0];
                    fieldName = colAnn.name();
                    if (colAnn.type() != Object.class) {
                        fieldType = colAnn.type();
                    }

                    processFieldAttributes(rs, obj, fieldName, _field, fieldType);

                } else if (annotationType == SqlJoin.class) {
                    SqlJoin colAnn = (SqlJoin) annotations[0];
                    Class from = colAnn.from();
                    Object innerObj = prepareInnerObject(from, rs);
                    _field.set(obj, innerObj);

                } else if (annotationType == Transient.class)
                    continue;
            }

        }
    }

    /**
     * In case of searching for joined column attributes, it searchs for the
     * fields in the original sql result sets, and prepares inner object
     *
     * @param from inner class reference
     * @param rs   sql resultset
     * @return prepared inner object as joined table information
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws SQLException
     * @throws InstantiationException
     */
    private Object prepareInnerObject(Class<?> from, ResultSet rs)
            throws IllegalArgumentException, IllegalAccessException, SQLException, InstantiationException {
        Field[] innerFields = from.getDeclaredFields();
        Object obj = from.newInstance();
        for (int i = 0; i < innerFields.length; i++) {
            Field _innerField = innerFields[i];
            if (SERIAL_VERSION_UID.equals(_innerField.getName())) {
                continue;
            }
            _innerField.setAccessible(true);
            Class<?> _innerFieldType = _innerField.getType();
            Annotation[] _annotations = _innerField.getDeclaredAnnotations();
            if (_annotations != null && _annotations.length > 0) {
                Class<? extends Annotation> annotationType = _annotations[0].annotationType();
                if (annotationType == SqlColumn.class) {
                    SqlColumn colAnn = (SqlColumn) _annotations[0];
                    String fieldName = colAnn.name();

                    if (colAnn.type() != Object.class) {
                        _innerFieldType = colAnn.type();
                    }

                    if (this.existsInResult(rs, fieldName)) {

                        if (_innerFieldType == Long.class) {
                            _innerField.set(obj, rs.getLong(fieldName));
                        }

                        if (_innerFieldType == String.class) {
                            _innerField.set(obj, rs.getString(fieldName));
                        }

                        if (_innerFieldType == Integer.class) {
                            _innerField.set(obj, rs.getInt(fieldName));
                        }

                        if (_innerFieldType == Double.class) {
                            _innerField.set(obj, rs.getDouble(fieldName));
                        }

                        if (_innerFieldType == Date.class) {
                            java.sql.Date sqlDate = rs.getDate(fieldName);
                            _innerField.set(obj, new Date(sqlDate.getTime()));
                        }

                        if (_innerFieldType == BigDecimal.class) {
                            _innerField.set(obj, rs.getBigDecimal(fieldName));
                        }

                        if (_innerFieldType == Boolean.class) {
                            _innerField.set(obj, rs.getBoolean(fieldName));
                        }

                        if (_innerFieldType == Float.class) {
                            _innerField.set(obj, rs.getFloat(fieldName));
                        }

                        if (_innerFieldType == Short.class) {
                            _innerField.set(obj, rs.getShort(fieldName));
                        }

                        if (_innerFieldType == Timestamp.class) {
                            _innerField.set(obj, rs.getTimestamp(fieldName));
                        }
                    }

                } else if (annotationType == Transient.class)
                    continue;
            }
        }
        return obj;
    }

    /**
     * Prepares result object, searching for fields that matches column names
     * from original sql result set
     *
     * @param rs        sql result set
     * @param obj       object reference
     * @param fieldName current field name for validate
     * @param f         reference field
     * @param fieldType class type for selected field
     * @throws SQLException
     * @throws IllegalAccessException
     */
    private void processFieldAttributes(ResultSet rs, T obj, String fieldName, Field f, Class<?> fieldType)
            throws SQLException, IllegalAccessException {
        if (this.existsInResult(rs, fieldName)) {
            if (fieldType == Long.class) {
                f.set(obj, rs.getLong(fieldName));
            }

            if (fieldType == String.class) {
                f.set(obj, rs.getString(fieldName));
            }

            if (fieldType == Integer.class) {
                f.set(obj, rs.getInt(fieldName));
            }

            if (fieldType == Double.class) {
                f.set(obj, rs.getDouble(fieldName));
            }

            if (fieldType == Date.class) {
                java.sql.Date sqlDate = rs.getDate(fieldName);
                f.set(obj, new Date(sqlDate.getTime()));
            }

            if (fieldType == BigDecimal.class) {
                f.set(obj, rs.getBigDecimal(fieldName));
            }

            if (fieldType == Boolean.class) {
                f.set(obj, rs.getBoolean(fieldName));
            }

            if (fieldType == Float.class) {
                f.set(obj, rs.getFloat(fieldName));
            }

            if (fieldType == Short.class) {
                f.set(obj, rs.getShort(fieldName));
            }

            if (fieldType == Timestamp.class) {
                f.set(obj, rs.getTimestamp(fieldName));
            }
        }
    }

    /**
     * Checks if a field name exists as column name in resultset
     *
     * @param rs        sql resultset
     * @param fieldName
     * @return true/false if column name exists
     * @throws SQLException if an error happens
     */
    private boolean existsInResult(ResultSet rs, final String fieldName) throws SQLException {
        boolean b = false;
        ResultSetMetaData metaData = rs.getMetaData();
        final int count = metaData.getColumnCount();
        for (int i = 1; i <= count; i++) {
            String columnLabel = metaData.getColumnLabel(i);
            b = columnLabel.equalsIgnoreCase(fieldName);
            if (b) {
                break;
            }
        }
        return b;
    }

    /**
     * Performs mapping from resultset into a list of referenced instance
     * objects
     *
     * @param rs      resultset to map
     * @param toClazz output class instance reference
     * @return mapped list of object
     */
    public List<T> toList(ResultSet rs, Class<T> toClazz) {
        List<T> listObj = new LinkedList<T>();
        T obj = null;
        Field[] fields = toClazz.getDeclaredFields();
        if (fields == null || fields.length == 0)
            return listObj;
        try {
            while (rs.next()) {
                obj = toClazz.newInstance();
                populateFields(rs, obj, fields);
                listObj.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listObj;
    }

    /**
     * Performs mapping from resultset into a class instance object
     *
     * @param rs      resultset to map
     * @param toClazz output class instance reference
     * @return mapped object
     */
    public T toObject(ResultSet rs, Class<T> toClazz) {
        T obj = null;
        Field[] fields = toClazz.getDeclaredFields();
        try {
            if (rs.next() && fields != null && fields.length > 0) {
                obj = toClazz.newInstance();
                populateFields(rs, obj, fields);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;
    }

}