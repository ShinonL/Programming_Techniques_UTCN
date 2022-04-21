package DataAccesObject;

import DatabaseConnection.ConnectionFactory;
import Model.Order;
import Model.OrderDetail;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 * The order details data access object are defined here
 *
 * This class extends the genericDAO class. All the methods are done by calling the super class methods with the order
 * details specific type of items
 */
public class OrderDetailDAO extends GenericDAO<OrderDetail> {
    public OrderDetailDAO() {
        super(OrderDetail.class);
    }

    public void insert(OrderDetail orderDetail) {
        super.insert(orderDetail);
    }
    public void delete(int orderId) {
        super.delete(orderId, "orderId");
    }

    /**
     * Get all the details of a specific order
     * @param orderID The id of the order to be detailed
     * @return The list of details about the order
     */
    public List<OrderDetail> getDetails(int orderID) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM schooldb.OrderDetail WHERE OrderID = " + orderID;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return super.createObjects(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * Find all details about the selling of a given product
     * @param ID the id of the product to be searched
     * @return a list of details about the product sales
     */
    public List<OrderDetail> findByProductID(int ID) {
        return super.findByInt(ID, "ProductID");
    }
}
