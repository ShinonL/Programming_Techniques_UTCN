package DataAccesObject;

import DatabaseConnection.ConnectionFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is a generic Database Access Class.
 * Because the queries are almost all the same, this class implements the common queries.
 *
 * @param <T> This is a generic parameter that takes the place of a specific class Model so that, the methods
 *           implemented by this class may be used by any other subclass or by any instantiation of type T
 */
public class GenericDAO<T> {
    private static final Logger LOGGER = Logger.getLogger(GenericDAO.class.getName());
    private final Class<T> type;

    /**
     * Set the class type
     * @param entityClass The class type to which the methods will be applied
     */
    public GenericDAO(Class<T> entityClass) {
        type = entityClass;
    }

    /**
     * Create a SELECT (FIND) query with an optional condition
     * @param field In case a condition for the query exists, the field will contain the name of the field to which the
     *              condition is applied. If there is no condition, the field will be null. This is applied in case the
     *              query is supposed to select all elements from a table
     * @return A string with the final form of the query based on the field
     */
    private String createSelectQuery(String field) {
        String query = "SELECT * FROM schooldb." + type.getSimpleName();
        if (field != null)
            return query + " WHERE " + field + " =?";
        return query;
    }

    /**
     * Create a DELETE query
     * @param field The column based on which the items to be deleted are selected
     * @return A string with the final form of the query
     */
    private String createDeleteQuery(String field) {
        return "DELETE FROM schooldb." + type.getSimpleName() + " WHERE " + field + " =?";
    }

    /**
     * Create an INSERT (CREATE) query
     * @param fields The field parameters to be set for insertion
     * @return A string containing the fields to be set
     */
    private StringBuilder createInsertQuery(Field[] fields) {
        StringBuilder query = new StringBuilder("INSERT INTO schooldb." + type.getSimpleName() + "(");
        try {
            int i = 1;
            if (type.getSimpleName().equals("OrderDetail"))
                i = 0;
            while (i < fields.length - 1) {
                fields[i].setAccessible(true);
                query.append(fields[i].getName()).append(", ");
                i++;
            }
            fields[fields.length - 1].setAccessible(true);
            query.append(fields[fields.length - 1].getName());
            query.append(") VALUES (");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return query;
    }

    /**
     * Create the update query
     * @param values The list of new values of the fields
     * @param idField The id of the object to be updated
     * @return The final update query
     */
    private String createUpdateQuery(List<Object> values, String idField) {
        Field[] fields = type.getDeclaredFields();

        String query = "UPDATE schooldb." + type.getSimpleName() + " SET ";
        try {
            for (int i = 1; i < fields.length - 1; i++) {
                fields[i].setAccessible(true);
                query = query + fields[i].getName() + " = ";
                if (values.get(i - 1).getClass().getSimpleName().equals("String"))
                    query = query + "\"" + values.get(i - 1) + "\", ";
                else query = query + values.get(i - 1) + ", ";
            }
            fields[fields.length - 1].setAccessible(true);
            query = query + fields[fields.length - 1].getName() + " = ";
            if (values.get(values.size() - 1).getClass().getSimpleName().equals("String"))
                query = query + "\"" + values.get(values.size() - 1) + "\"";
            else query = query + values.get(values.size() - 1);
            query = query + " WHERE " + idField + " =?";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return query;
    }

    /**
     * Convert the result set given by the execution of the query to a list of items of the given type
     * @param resultSet The result set obtained
     * @return The list of items in the result set
     */
    protected List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        try {
            while (resultSet.next()) {
                T instance = type.getDeclaredConstructor().newInstance();
                for (Field field: type.getDeclaredFields()) {
                    Object value = resultSet.getObject(field.getName());
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Return all items in a table
     * @return A list of the items of type T
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery(null);

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Find the item with the specified ID
     * @param ID The ID of the item
     * @param field The name of the id field
     * @return The item that is associated to the given ID
     */
    public T findByID(int ID, String field) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery(field);

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, ID);
            resultSet = statement.executeQuery();

            List<T> result = createObjects(resultSet);
            if (result.size() == 0)
                return null;
            else return result.get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findByID " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Returns a list of items which have the value in the specified field equal with the given value
     * @param value The value to search for
     * @param field The field in which to look for the value
     * @return A list of items for which the condition is true
     */
    public List<T> findByInt(int value, String field) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery(field);

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, value);
            resultSet = statement.executeQuery();

            List<T> result = createObjects(resultSet);
            if (result.size() == 0)
                return null;
            else return result;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findByInt " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Returns a list of items which have the value in the specified field equal with the given value
     * @param value The value to search for
     * @param field The field in which to look for the value
     * @return A list of items for which the condition is true
     */
    public List<T> findByString(String value, String field) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery(field);

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, value);
            resultSet = statement.executeQuery();

            List<T> result = createObjects(resultSet);
            if (result.size() == 0)
                return null;
            else return result;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findByString " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Completes the insert query with the given values and connects to the database to execute the query
     * @param object The object to be inserted
     */
    public void insert(Object object) {
        // build the insert query
        Field[] fields = object.getClass().getDeclaredFields();
        StringBuilder query = createInsertQuery(fields);
        try {
            int i = 1;
            if (type.getSimpleName().equals("OrderDetail"))
                i = 0;
            while (i < fields.length - 1) {
                Object value = fields[i].get(object);
                if (fields[i].getType().getSimpleName().equals("String"))
                    query.append("\"" + value + "\"");
                else query.append(value);
                query.append(", ");
                i++;
            }
            Object value = fields[fields.length - 1].get(object);
            if (fields[fields.length - 1].getType().getSimpleName().equals("String"))
                query.append("\"" + value + "\"");
            else query.append(value);
            query.append(")");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // create the connection and execute the query
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query.toString());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Detelete the objects which have the field value equal with the given integer value
     * @param value The value to search for
     * @param field The field to look into
     */
    public void delete(int value, String field) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createDeleteQuery(field);

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, value);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Executes an update query
     * @param ID The id of the item to be updated
     * @param idField the name of the id field of the item
     * @param values the new values to be inserted
     */
    public void update(int ID, String idField, List<Object> values) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createUpdateQuery(values, idField);

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, ID);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }
}
