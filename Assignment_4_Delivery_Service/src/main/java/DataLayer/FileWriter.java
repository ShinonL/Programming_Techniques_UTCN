package DataLayer;

import BusinessLayer.MenuItem;
import BusinessLayer.Order;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class FileWriter {
    /**
     * Generate the message about the order to be given to the employee observers
     * @param items the ordered items
     * @param order the order to which they belong
     * @return the message
     */
    public static String generateObserverMessage(List<MenuItem> items, Order order) {
        String message = "<html>New Order<br>Order ID: " + order.getOrderId() + "<br>Date: " + order.getDate().toString() +
                "<br>Client: " + order.getClientId() + "<br>Total: ";
        message += items.stream()
                .mapToDouble(item -> item.computePrice())
                .sum();

        message += "<br>List of products ordered:<br>";
        for (MenuItem item: items)
            message += item.getTitle() + "<br>";
        message += "</html>";

        return message;
    }

    /**
     * Generate the bill for a newly created order
     * @param items items which belong tot the order
     * @param order the order details
     */
    public static void generateBill(List<MenuItem> items, Order order) {
        File bill = new File("src/main/resources/Order Bills/Order_" + order.getOrderId() + ".txt");

        try {
            bill.createNewFile();

            java.io.FileWriter writer = new java.io.FileWriter(bill.getPath(), true);
            writer.write("--------------------- ORDER NUMBER " + order.getOrderId() + " ---------------------\n");
            writer.write("Date: " + order.getDate().toString() + "\n");
            writer.write("Client: " + order.getClientId() + "\n");

            double total = items.stream()
                    .mapToDouble(item -> item.computePrice())
                    .sum();
            writer.write("Total: " + total + "\n\n");
            writer.write("--------------------- ORDERED PRODUCTS ---------------------\n");

            for (MenuItem item: items)
                writer.write("Product Name: " + item.getTitle() + "\n");

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a .txt file with the report
     * @param orders the list of orders in the report
     * @param startHour the starting hour for the report survey
     * @param endHour the ending hour for the report survey
     * @param hourlyReportCount the number of reports generated
     */
    public static void generateHourlyReport(List<Order> orders, int startHour, int endHour, int hourlyReportCount) {
        File report = new File("src/main/resources/Hourly Report/Report_" + hourlyReportCount + ".txt");

        try {
            report.createNewFile();

            java.io.FileWriter writer = new java.io.FileWriter(report.getPath(), true);
            writer.write("START HOUR: " + startHour + "\n");
            writer.write("END HOUR: " + endHour + "\n\n");
            writer.write("ORDERS between these hours:\n");

            for (Order order: orders) {
                writer.write("OrderId: " + order.getOrderId() + "\n");
                writer.write("Date: " + order.getDate().toString() + "\n");
                writer.write("ClientId: " + order.getClientId() + "\n");
                writer.write("---------------------------------------------------------\n");
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a .txt file with the report
     * @param products the list of products in the report
     * @param frequency minimum number of apparitions of a product
     * @param frequencyCount the number of reports generated
     */
    public static void generateFrequency(List<MenuItem> products, int frequency, int frequencyCount) {
        File report = new File("src/main/resources/Frequency Report/Report_" + frequencyCount + ".txt");

        try {
            report.createNewFile();

            java.io.FileWriter writer = new java.io.FileWriter(report.getPath(), true);
            writer.write("List of Products ordered more then " + frequency + " times\n\n");
            writer.write("---------------------------------------------------------\n");

            products.forEach(product -> System.out.println(product.getTitle()));

            products.forEach(item -> {
                        try {
                            writer.write(item.getTitle() + "\n");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a .txt file with the report
     * @param clients the list of clients in the report
     * @param orderFrequency minimum number of orders of a client
     * @param minimumValue minimum value spent on each order
     * @param clientCount the number of reports generated
     */
    public static void generateClient(List<String> clients, int orderFrequency, double minimumValue, int clientCount) {
        File report = new File("src/main/resources/Client Report/Report_" + clientCount + ".txt");

        try {
            report.createNewFile();

            java.io.FileWriter writer = new java.io.FileWriter(report.getPath(), true);
            writer.write("Clients that have made more than " + orderFrequency + " ");
            writer.write("and that have spent more than " + minimumValue + " on each order\n\n");

            clients.forEach(client -> {
                try {
                    writer.write("ClientId: " + client + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a .txt file with the report
     * @param products the list of products in the report
     * @param day the day for the report
     * @param month the month for the report
     * @param year the year for the report
     * @param dateCount the number of reports generated
     */
    public static void generateDate(Map<String, Integer> products, int day, int month, int year, int dateCount) {
        File report = new File("src/main/resources/Date Report/Report_" + dateCount + ".txt");

        try {
            report.createNewFile();

            java.io.FileWriter writer = new java.io.FileWriter(report.getPath(), true);
            writer.write("Products ordered on " + day + "." + month + "." + year + "\n");

            products.entrySet().stream()
                    .forEach(entry -> {
                        try {
                            writer.write("Product: " + entry.getKey() + "\n");
                            writer.write("Number of occurences: " + entry.getValue() + "\n");
                            writer.write("---------------------------------------------------------\n");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
