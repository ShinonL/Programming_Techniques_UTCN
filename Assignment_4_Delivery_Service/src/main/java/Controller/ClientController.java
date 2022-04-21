package Controller;

import BusinessLayer.DeliveryService;
import BusinessLayer.MenuItem;
import DataLayer.Serializator;
import PresentationLayer.ClientGUI;
import Validators.ClientValidator;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ClientController {
    ClientGUI clientGUI = new ClientGUI();
    DeliveryService service;
    String username;

    /**
     * Create a new controller for a client
     * @param service the delivery service with the delivery information
     * @param username the username of the client
     */
    public ClientController(DeliveryService service, String username) {
        this.service = service;
        this.username = username;
    }

    /**
     * Add the action listeners for the buttons in the login window
     */
    public void addActionListeners() {
        service.loadCvsFile();
        clientGUI.updateTable(convertTableData(service.getMenuItems()));
        clientGUI.search.addActionListener(e -> {
            List<MenuItem> products = service.getMenuItems();

            try {
                String str = clientGUI.keyword.getText();
                if (!str.equals(""))
                    products = service.findByKeyword(products, str);

                str = clientGUI.ratingMin.getText();
                if (!str.equals("")) {
                    ClientValidator.validateRating(str);
                    double value = Double.parseDouble(str);
                    products = service.findByMinRating(products, value);
                }
                str = clientGUI.ratingMax.getText();
                if (!str.equals("")) {
                    ClientValidator.validateRating(str);
                    double value = Double.parseDouble(str);
                    products = service.findByMaxRating(products, value);
                }

                str = clientGUI.priceMin.getText();
                if (!str.equals("")) {
                    ClientValidator.validatePrice(str);
                    double value = Double.parseDouble(str);
                    products = service.findByMinPrice(products, value);
                }
                str = clientGUI.priceMax.getText();
                if (!str.equals("")) {
                    ClientValidator.validatePrice(str);
                    double value = Double.parseDouble(str);
                    products = service.findByMaxPrice(products, value);
                }

                str = clientGUI.caloriesMin.getText();
                if (!str.equals("")) {
                    ClientValidator.validateInterger(str);
                    int value = Integer.parseInt(str);
                    products = service.findByMinCalories(products, value);
                }
                str = clientGUI.caloriesMax.getText();
                if (!str.equals("")) {
                    ClientValidator.validatePrice(str);
                    int value = Integer.parseInt(str);
                    products = service.findByMaxCalories(products, value);
                }

                str = clientGUI.proteinMin.getText();
                if (!str.equals("")) {
                    ClientValidator.validateInterger(str);
                    int value = Integer.parseInt(str);
                    products = service.findByMinProtein(products, value);
                }
                str = clientGUI.proteinMax.getText();
                if (!str.equals("")) {
                    ClientValidator.validatePrice(str);
                    int value = Integer.parseInt(str);
                    products = service.findByMaxProtein(products, value);
                }

                str = clientGUI.fatMin.getText();
                if (!str.equals("")) {
                    ClientValidator.validateInterger(str);
                    int value = Integer.parseInt(str);
                    products = service.findByMinFat(products, value);
                }
                str = clientGUI.fatMax.getText();
                if (!str.equals("")) {
                    ClientValidator.validatePrice(str);
                    int value = Integer.parseInt(str);
                    products = service.findByMaxFat(products, value);
                }

                str = clientGUI.sodiumMin.getText();
                if (!str.equals("")) {
                    ClientValidator.validateInterger(str);
                    int value = Integer.parseInt(str);
                    products = service.findByMinSodium(products, value);
                }
                str = clientGUI.sodiumMax.getText();
                if (!str.equals("")) {
                    ClientValidator.validatePrice(str);
                    int value = Integer.parseInt(str);
                    products = service.findByMaxSodium(products, value);
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }

            clientGUI.updateTable(convertTableData(products));
        });
        clientGUI.order.addActionListener(e -> {
            List<MenuItem> products = new ArrayList<>();
            int[] selectedRows = clientGUI.products.getSelectedRows();
            for (int row: selectedRows) {
                MenuItem menuItem = service.getMenuItems().stream()
                        .filter(item -> item.getTitle().equals(clientGUI.products.getValueAt(row, 0)))
                        .findFirst()
                        .orElse(null);
                products.add(menuItem);
            }

            service.createOrder(products, username);

            Serializator serializator = new Serializator();
            serializator.serialize(service);
        });
        clientGUI.refresh.addActionListener(e -> {
            clientGUI.updateTable(convertTableData(service.getMenuItems()));
        });
        clientGUI.logout.addActionListener(e -> {
            clientGUI.clientFrame.dispose();
        });
    }

    /**
     * Convert the menu items list to a printable format for the JTable
     *
     * @return the new format
     */
    public String[][] convertTableData(List<MenuItem> rawData) {
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
