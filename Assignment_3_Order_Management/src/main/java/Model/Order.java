package Model;

/**
 * A mirror of the "order" table from the "schooldb" database.
 * It is the internal representation of the table.
 *
 * The database design made associates to each order more products.
 */
public class Order {
    private int orderID, clientID;
    private String address;
    private double total;

    /**
     * The empty constructor is used by the SQL queries.
     * It creates an instance of a order without initializing its fields.
     */
    public Order() {}

    /**
     * A parameterized constructor is used to create an instance of an order with the following pre-set fields.
     * Besides these parameters, an order also has an unique identifying ID (the "orderID" field).
     * This field is automatically generated by the inset query into the database.
     *
     * The pre-set parameters are:
     * @param clientID The id of the client that placed the order. It is stored as an INTEGER.
     *                 A client may order something more than once so this field is not necessary to be unique for each
     *                 order
     * @param address This is the shipping address corresponding to these order. It is stores as a STRING value.
     * @param total The total amount spent on this order. Stored as a DOUBLE value.
     */
    public Order(int clientID, String address, double total) {
        this.clientID = clientID;
        this.address = address;
        this.total = total;
    }

    public int getOrderID() {
        return orderID;
    }
    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getClientID() {
        return clientID;
    }
    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }
}
