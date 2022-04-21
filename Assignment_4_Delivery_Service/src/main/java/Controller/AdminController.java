package Controller;

import BusinessLayer.BaseProduct;
import BusinessLayer.DeliveryService;
import BusinessLayer.MenuItem;
import DataLayer.Serializator;
import PresentationLayer.AdminGUI;
import Validators.AdminValidator;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class AdminController {
    AdminGUI adminGUI = new AdminGUI();
    DeliveryService service;
    List<MenuItem> products;
    int index;

    /**
     * Create a new controller for an admin
     * @param service the delivery service with the delivery information
     */
    public AdminController(DeliveryService service) {
        this.service = service;
    }

    /**
     * Add the action listeners for the buttons in the login window
     */
    public void addActionListeners() {
        adminGUI.load.addActionListener(e -> {
            service.loadCvsFile();
            adminGUI.updateTable(convertTableData());
        });
        adminGUI.newBaseProduct.addActionListener(e -> {
            adminGUI.createNewBaseProduct();
        });
        adminGUI.confirmCreate.addActionListener(e -> {
            String title = adminGUI.title.getText();
            String rating = adminGUI.rating.getText();
            String calories = adminGUI.calories.getText();
            String protein = adminGUI.protein.getText();
            String fat = adminGUI.fat.getText();
            String sodium = adminGUI.sodium.getText();
            String price = adminGUI.price.getText();

            try {
                AdminValidator.validateNewProduct(title, rating, calories, protein, fat, sodium, price);

                service.addMenuItem(new BaseProduct(title, rating, calories, protein, fat, sodium, price));

                Serializator serializator = new Serializator();
                serializator.serialize(service);

                adminGUI.updateTable(convertTableData());
                JOptionPane.showMessageDialog(null, "The product was created.");
                adminGUI.createFrame.dispose();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });
        adminGUI.newCompositeProduct.addActionListener(e -> {
            products = new ArrayList<>();
            int[] selectedRows = adminGUI.products.getSelectedRows();
            for (int row: selectedRows) {
                MenuItem menuItem = service.getMenuItems().stream()
                        .filter(item -> item.getTitle().equals(adminGUI.products.getValueAt(row, 0)))
                        .findFirst()
                        .orElse(null);
                products.add(menuItem);
            }
            adminGUI.nameCompositeProduct();
        });
        adminGUI.confirmMenu.addActionListener(e -> {
            try {
                service.createCompositeProduct(adminGUI.title.getText(), products);

                Serializator serializator = new Serializator();
                serializator.serialize(service);

                adminGUI.updateTable(convertTableData());
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            } finally {
                adminGUI.menuFrame.dispose();
            }
        });
        adminGUI.modifyProduct.addActionListener(e -> {
            int[] selectedRows = adminGUI.products.getSelectedRows();
            if (selectedRows.length == 1) {
                MenuItem menuItem = service.getMenuItems().stream()
                        .filter(item -> item.getTitle().equals(adminGUI.products.getValueAt(selectedRows[0], 0)))
                        .findFirst()
                        .orElse(null);
                index = service.getMenuItems().indexOf(menuItem);
                if (menuItem instanceof BaseProduct)
                    adminGUI.modifyNewBaseProduct((BaseProduct) menuItem);
                else JOptionPane.showMessageDialog(null, "Please choose a base product", "Warning", JOptionPane.WARNING_MESSAGE);
            } else JOptionPane.showMessageDialog(null, "Please select just one row", "Warning", JOptionPane.WARNING_MESSAGE);

        });
        adminGUI.confirmModify.addActionListener(e -> {
            String title = adminGUI.title.getText();
            String rating = adminGUI.rating.getText();
            String calories = adminGUI.calories.getText();
            String protein = adminGUI.protein.getText();
            String fat = adminGUI.fat.getText();
            String sodium = adminGUI.sodium.getText();
            String price = adminGUI.price.getText();

            try {
                AdminValidator.validateNewProduct(title, rating, calories, protein, fat, sodium, price);

                service.modifyProduct(index, title, rating, calories, protein, fat, sodium, price);

                Serializator serializator = new Serializator();
                serializator.serialize(service);

                adminGUI.updateTable(convertTableData());
                JOptionPane.showMessageDialog(null, "The product was modified.");
                adminGUI.createFrame.dispose();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });
        adminGUI.deleteProduct.addActionListener(e -> {
            int[] selectedRows = adminGUI.products.getSelectedRows();
            for (int row: selectedRows) {
                MenuItem menuItem = service.getMenuItems().stream()
                        .filter(item -> item.getTitle().equals(adminGUI.products.getValueAt(row, 0)))
                        .findFirst()
                        .orElse(null);
                service.getMenuItems().remove(menuItem);
            }
            adminGUI.updateTable(convertTableData());

            Serializator serializator = new Serializator();
            serializator.serialize(service);
        });
        adminGUI.generateReports.addActionListener(e -> {
            adminGUI.createReports();
        });
        adminGUI.logOut.addActionListener(e -> {
            adminGUI.adminFrame.dispose();
        });
        adminGUI.timeReport.addActionListener(e -> {
            try {
                AdminValidator.validateTimeReport(adminGUI.start.getText(), adminGUI.end.getText());

                int start = Integer.parseInt(adminGUI.start.getText());
                int end = Integer.parseInt(adminGUI.end.getText());
                service.createHourlyReport(start, end);
                JOptionPane.showMessageDialog(null, "The report was successfully generated.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });
        adminGUI.frequencyReport.addActionListener(e -> {
            try {
                AdminValidator.validateFrequencyReport(adminGUI.freq.getText());

                int frequency = Integer.parseInt(adminGUI.freq.getText());
                service.createFrequencyReport(frequency);
                JOptionPane.showMessageDialog(null, "The report was successfully generated.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });
        adminGUI.clientsReport.addActionListener(e -> {
            try {
                AdminValidator.validateClientsReport(adminGUI.ordersNum.getText(), adminGUI.total.getText());

                int frequrncy = Integer.parseInt(adminGUI.ordersNum.getText());
                double value = Double.parseDouble(adminGUI.total.getText());

                service.createClientReport(frequrncy, value);
                JOptionPane.showMessageDialog(null, "The report was successfully generated.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });
        adminGUI.dateReport.addActionListener(e -> {
            try {
                AdminValidator.validateDateReport(adminGUI.date.getText());
                String[] str = adminGUI.date.getText().split("\\.");

                int day = Integer.parseInt(str[0]);
                int month = Integer.parseInt(str[1]);
                int year = Integer.parseInt(str[2]);

                service.createDateReport(day, month, year);
                JOptionPane.showMessageDialog(null, "The report was successfully generated.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /**
     * Convert the menu items list to a printable format for the JTable
     * 
     * @return the new format
     */
    public String[][] convertTableData() {
        List<MenuItem> rawData = service.getMenuItems();
        String[][] data = new String[rawData.size()][];
        for (int i = 0; i < rawData.size(); i++) {
            data[i] = new String[7];
            data[i][0] = rawData.get(i).getTitle();
            data[i][1] = String.valueOf(rawData.get(i).computeRating());
            data[i][2] = String.valueOf(rawData.get(i).computeCalories());
            data[i][3] = String.valueOf(rawData.get(i).computeProtein());
            data[i][4] = String.valueOf(rawData.get(i).computeFat());
            data[i][5] = String.valueOf(rawData.get(i).computeSodium());
            data[i][6] = String.valueOf(rawData.get(i).computePrice());
        }
        return data;
    }
}
