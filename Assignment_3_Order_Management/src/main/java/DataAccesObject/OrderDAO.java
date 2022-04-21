package DataAccesObject;

import DatabaseConnection.ConnectionFactory;
import Model.Client;
import Model.Order;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * The order data access object are defined here
 *
 * This class extends the genericDAO class. All the methods are done by calling the super class methods with the order
 * specific type of items
 */
public class OrderDAO extends GenericDAO<Order> {
    public OrderDAO() {
        super(Order.class);
    }

    public void insert(Order order) {
        super.insert(order);
    }
    public List<Order> findAll() {
        return super.findAll();
    }
    public Order findByID(int ID) {
        return super.findByID(ID, "orderID");
    }
    public void delete(int orderId) {
        super.delete(orderId, "orderID");
    }

    /**
     * Find the latest order added tot he database
     * @return The order found by the query
     */
    public Order findLatestOrder() {
        String query = "SELECT * FROM schooldb.order WHERE OrderID = (SELECT max(OrderId) FROM schooldb.order)";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return super.createObjects(resultSet).get(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Finds all the orders of a specific client
     * @param ID The id of the client
     * @return A list of orders associated to the client
     */
    public List<Order> findByClientID(int ID) {
        return super.findByInt(ID, "ClientID");
    }
}
