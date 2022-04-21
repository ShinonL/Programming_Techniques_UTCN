package Controller;

import BusinessLogic.OrderBLL;
import Presentation.OrderGUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controls all the buttons presented in the OrderGUI
 *
 * It calls upon an orderBLL to come and handle the requests made by the user
 */
public class OrderGUIController {
    private static OrderGUI orderGUI;
    private OrderBLL orderBLL;

    public OrderGUIController(JFrame frame) {
        orderBLL = new OrderBLL();

        orderGUI = new OrderGUI(frame);
        orderGUI.start();
    }
    public void addActionListeners() {
        orderGUI.insertButton.addActionListener(e -> {
            orderGUI.insertOrder(orderBLL.getClientsString());
        });
        orderGUI.addProductButton.addActionListener(e -> {
            orderGUI.addProduct(orderBLL.getProductsString());
        });
        orderGUI.insertSubmitButton.addActionListener(e -> {
            List<String> products = new ArrayList<>();
            for (JComboBox comboBox: orderGUI.productTypes) {
                products.add(comboBox.getSelectedItem().toString());
            }

            List<String> quantities = new ArrayList<>();
            for (JTextField textField: orderGUI.quantities) {
                quantities.add(textField.getText());
            }

            try {
                orderBLL.createOrder(orderGUI.clientBox.getSelectedItem().toString(), orderGUI.addressField.getText(), products, quantities);
                JOptionPane.showMessageDialog(null, "Order created!");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        orderGUI.showAllButton.addActionListener(e -> {
            orderGUI.showAll(orderBLL.getAllOrders());
        });

        orderGUI.showBillsButton.addActionListener(e -> {
            try {
                Desktop.getDesktop().open(new File("Order Bills"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        orderGUI.searchButton.addActionListener(e -> {
            try {
                orderGUI.createSearch();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });
        orderGUI.cancelOrder.addActionListener(e -> {
            try {
                orderBLL.cancelOrder(orderGUI.addressField.getText());
                JOptionPane.showMessageDialog(null, "Order was cancelled successfully.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        orderGUI.backButton.addActionListener(e -> {
            orderGUI.frame.remove(orderGUI.orderPanel);
            GUIController.gui.reset();
        });
    }
}
