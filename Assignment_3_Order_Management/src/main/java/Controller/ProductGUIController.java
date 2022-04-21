package Controller;

import BusinessLogic.ProductBLL;
import Presentation.ProductGUI;

import javax.swing.*;

/**
 * Controls all the buttons presented in the ProductGUI
 *
 * It calls upon an productBLL to come and handle the requests made by the user
 */
public class ProductGUIController {
    private static ProductGUI productGUI;
    private ProductBLL productBLL;

    public ProductGUIController(JFrame frame) {
        productBLL = new ProductBLL();

        productGUI = new ProductGUI(frame);
        productGUI.start();
    }
    public void addActionListeners() {
        productGUI.insertButton.addActionListener(e -> {
            productGUI.insert();
        });
        productGUI.insertSubmitButton.addActionListener(e -> {
            String name = productGUI.nameField.getText();
            String quantity = productGUI.quantityField.getText();
            String price = productGUI.priceField.getText();

            try {
                productBLL.createProduct(name, quantity, price);
                JOptionPane.showMessageDialog(null, "The product was created.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        productGUI.deleteButton.addActionListener(e -> {
            productGUI.deleteSearch();
        });
        productGUI.deleteSearchButton.addActionListener(e -> {
            try {
                productGUI.delete(productBLL.findProduct(productGUI.idField.getText()));
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });
        productGUI.deleteConfirmButton.addActionListener(e -> {
            try {
                productBLL.deleteProduct();
                JOptionPane.showMessageDialog(null, "The product was deleted.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        productGUI.updateButton.addActionListener(e -> {
            productGUI.updateSearch();
        });
        productGUI.updateSearchButton.addActionListener(e -> {
            try {
                productGUI.update(productBLL.findProduct(productGUI.idField.getText()));
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });
        productGUI.updateConfirmButton.addActionListener(e -> {
            String name = productGUI.nameField.getText();
            String quantity = productGUI.quantityField.getText();
            String price = productGUI.priceField.getText();

            try {
                productBLL.updateProduct(name, quantity, price);
                JOptionPane.showMessageDialog(null, "The product was updated.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });

        productGUI.showButton.addActionListener(e -> {
            productGUI.showAll(productBLL.getAllProducts());
        });

        productGUI.backButton.addActionListener(e -> {
            productGUI.frame.remove(productGUI.productPanel);
            GUIController.gui.reset();
        });
    }
}
