package BusinessLayer;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Order implements Serializable {
    private int orderId;
    private String clientId;
    private LocalDateTime date;

    /**
     * Create a new order
     *
     * @param orderId a unique identification of the orders
     * @param clientId the username of a client
     */
    public Order(int orderId, String clientId) {
        this.orderId = orderId;
        this.clientId = clientId;
        date = LocalDateTime.now();
    }

    /**
     * @return the orderId
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * @return the username of the client who made the order
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * @return the hour when the order was created
     */
    public int getHour() {
        return date.getHour();
    }

    /**
     * @return the date when the order was created
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Method used to compare 2 objects of the Order class
     * @param o the object to be compared to
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId && clientId.equals(order.clientId) && date.equals(order.date);
    }

    /**
     * @return the hashcode resulted by hashing the object based on all of its fields
     */
    @Override
    public int hashCode() {
        return Objects.hash(orderId, clientId, date);
    }
}
