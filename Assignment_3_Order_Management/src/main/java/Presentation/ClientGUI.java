package Presentation;

import Model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;

/**
 * The view of the clients management part
 */
public class ClientGUI {
    public JButton insertButton, deleteButton, updateButton, showButton, backButton;
    public JFrame frame;
    public JPanel clientPanel, panel;
    public Dimension startDimension;
    private GridBagConstraints constraints, localConstraints;

    public JButton insertSubmitButton, deleteConfirmButton, deleteSearchButton, updateSearchButton, updateConfirmButton;
    public JTextField firstNameField, lastNameField, idField, phoneField, mailField;
    public JLabel firstNameLabel, lastNameLabel, idLabel, phoneLabel, mailLabel;
    public JScrollPane scrollPane;

    /**
     * It "inherits" the frame from the menu
     * @param frame the JFrame used in the menu
     */
    public ClientGUI(JFrame frame) {
        this.frame = frame;
    }

    /**
     * The first method called. It creates the client management menu
     */
    public void start() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        startDimension = new Dimension(screenSize.width/4 + 1, screenSize.height/2);
        frame.setSize(startDimension);
        
        clientPanel = new JPanel();
        clientPanel.setSize(startDimension);
        clientPanel.setLayout(new GridBagLayout());
        clientPanel.setBackground(new Color(89, 100, 117));
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridheight = constraints.gridwidth = 1;
        constraints.insets = new Insets(15, 0, 15, 30);
        constraints.gridx = 0;
        constraints.gridy = -1;

        insertButton = new JButton("Insert a new Client");
        createButton(insertButton);

        deleteButton = new JButton("Delete a Client");
        createButton(deleteButton);

        updateButton = new JButton("Update a Client");
        createButton(updateButton);

        showButton = new JButton("Show all Clients");
        createButton(showButton);

        backButton = new JButton("Back");
        createButton(backButton);

        insertSubmitButton = new JButton("Submit");
        deleteConfirmButton = new JButton("Confirm");
        deleteSearchButton = new JButton("Search");
        updateSearchButton = new JButton("Search");
        updateConfirmButton = new JButton("Confirm");
        panel = new JPanel();

        frame.add(clientPanel);
        frame.getContentPane();
        frame.setVisible(true);
    }

    /**
     * Creates the main menu's buttons
     * @param button The button to be inserted
     */
    private void createButton(JButton button) {
        constraints.gridy++;
        button.setFont(new Font("Courier New", Font.PLAIN, 20));
        button.setForeground(new Color(31, 34, 50));
        button.setBackground(new Color(253, 232, 233));
        button.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        clientPanel.add(button, constraints);
    }

    /**
     * Creates the buttons for the search menus
     * @param button Button pressed to confirm the search
     */
    private void createSearch(JButton button) {
        createPanel();
        localConstraints.gridwidth = 1;

        localConstraints.gridx = localConstraints.gridy = 0;
        idLabel = new JLabel("Search by ID: ");
        idLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
        idLabel.setForeground(new Color(253, 232, 233));
        panel.add(idLabel, localConstraints);

        localConstraints.gridy++;
        idField = new JTextField();
        idField.setFont(new Font("Courier New", Font.PLAIN, 18));
        idField.setForeground(new Color(31, 34, 50));
        idField.setBackground(new Color(227, 186, 198));
        panel.add(idField, localConstraints);

        localConstraints.gridwidth = 2;
        localConstraints.gridx = 0;
        localConstraints.gridy++;
        button.setFont(new Font("Courier New", Font.PLAIN, 20));
        button.setForeground(new Color(31, 34, 50));
        button.setBackground(new Color(253, 232, 233));
        button.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.add(button, localConstraints);

        clientPanel.add(panel, constraints);
        clientPanel.revalidate();
        clientPanel.repaint();

        frame.setSize(new Dimension(startDimension.width * 5/2 + 10, startDimension.height));
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Generalizes the data insertion used in inserting and updating
     * @param button The specific button that confirms the operation
     * @param client The client to be inserted / updated
     */
    private void createDataInsert(JButton button, Client client) {
        String info;

        localConstraints.gridwidth = 1;
        localConstraints.gridx = 0;
        localConstraints.gridy = 1;
        firstNameLabel = new JLabel("First name: ");
        firstNameLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
        firstNameLabel.setForeground(new Color(253, 232, 233));
        panel.add(firstNameLabel, localConstraints);

        localConstraints.gridx = 1;
        if (client != null)
            info = client.getFirstName();
        else info = "";
        firstNameField = new JTextField(info);
        firstNameField.setFont(new Font("Courier New", Font.PLAIN, 18));
        firstNameField.setForeground(new Color(31, 34, 50));
        firstNameField.setBackground(new Color(227, 186, 198));
        panel.add(firstNameField, localConstraints);

        localConstraints.gridx = 0;
        localConstraints.gridy++;
        lastNameLabel = new JLabel("Last name: ");
        lastNameLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
        lastNameLabel.setForeground(new Color(253, 232, 233));
        panel.add(lastNameLabel, localConstraints);

        localConstraints.gridx = 1;
        if (client != null)
            info = client.getLastName();
        else info = "";
        lastNameField = new JTextField(info);
        lastNameField.setFont(new Font("Courier New", Font.PLAIN, 18));
        lastNameField.setForeground(new Color(31, 34, 50));
        lastNameField.setBackground(new Color(227, 186, 198));
        panel.add(lastNameField, localConstraints);

        localConstraints.gridx = 0;
        localConstraints.gridy++;
        mailLabel = new JLabel("E-Mail : ");
        mailLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
        mailLabel.setForeground(new Color(253, 232, 233));
        panel.add(mailLabel, localConstraints);

        localConstraints.gridx = 1;
        if (client != null)
            info = client.getEmail();
        else info = "";
        mailField = new JTextField(info);
        mailField.setFont(new Font("Courier New", Font.PLAIN, 18));
        mailField.setForeground(new Color(31, 34, 50));
        mailField.setBackground(new Color(227, 186, 198));
        panel.add(mailField, localConstraints);

        localConstraints.gridx = 0;
        localConstraints.gridy++;
        phoneLabel = new JLabel("Phone Number: ");
        phoneLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
        phoneLabel.setForeground(new Color(253, 232, 233));
        panel.add(phoneLabel, localConstraints);

        localConstraints.gridx = 1;
        if (client != null)
            info = client.getPhone();
        else info = "";
        phoneField = new JTextField(info);
        phoneField.setFont(new Font("Courier New", Font.PLAIN, 18));
        phoneField.setForeground(new Color(31, 34, 50));
        phoneField.setBackground(new Color(227, 186, 198));
        panel.add(phoneField, localConstraints);

        localConstraints.gridwidth = 2;
        localConstraints.gridx = 0;
        localConstraints.gridy++;
        button.setFont(new Font("Courier New", Font.PLAIN, 20));
        button.setForeground(new Color(31, 34, 50));
        button.setBackground(new Color(253, 232, 233));
        button.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.add(button, localConstraints);

        clientPanel.add(panel, constraints);
        clientPanel.revalidate();
        clientPanel.repaint();

        frame.setSize(new Dimension(startDimension.width * 5/2 + 10, startDimension.height));
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Creates the operating panel
     * Here the operations upon the product may be completed
     */
    private void createPanel() {
        if (panel != null) {
            panel.removeAll();
            frame.remove(panel);
        }
        if (scrollPane != null) {
            scrollPane.removeAll();
            frame.remove(scrollPane);
            scrollPane.setVisible(false);
        }

        constraints.gridheight = 5;
        constraints.gridx = 1;
        constraints.gridy = 0;

        panel.setPreferredSize(startDimension);
        panel.setBackground(new Color(89, 100, 117));
        panel.setLayout(new GridBagLayout());
        panel.setVisible(true);

        localConstraints = new GridBagConstraints();
        localConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        localConstraints.fill = GridBagConstraints.HORIZONTAL;
        localConstraints.gridheight = 1;
        localConstraints.insets = new Insets(10, 0, 10, 0);
        localConstraints.gridx = localConstraints.gridy = 0;
    }

    /**
     * Sets up the operations panel for insertion
     */
    public void insertClient() {
        clientPanel.setPreferredSize(new Dimension(startDimension.width * 2, 3 * startDimension.height/4));

        createPanel();

        localConstraints.gridwidth = 2;
        idLabel = new JLabel("Fill in all the required data");
        idLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
        idLabel.setForeground(new Color(253, 232, 233));
        panel.add(idLabel, localConstraints);

        createDataInsert(insertSubmitButton, null);
    }

    /**
     * Sets up the operations panel for searching a product to delete
     */
    public void deleteSearch() {
        createSearch(deleteSearchButton);
    }

    /**
     * Updates the deleteSearch panel with the found information about the client and a confirmation button
     * @param client A reference to the found client
     */
    public void deleteClient(Client client) {
        createPanel();
        localConstraints.gridwidth = 2;
        idLabel = new JLabel("      Data about the client      ");
        idLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
        idLabel.setForeground(new Color(253, 232, 233));
        panel.add(idLabel, localConstraints);
        createDataInsert(deleteConfirmButton, client);
    }

    /**
     * Used to make the table cells fit the inside text
     * @param table the table to be fitted
     * @return the final total width of the table
     *
     * Source: https://stackoverflow.com/questions/17627431/auto-resizing-the-jtable-column-widths
     */
    public int resizeColumnWidth(JTable table) {
        int tableWidth = 0;
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 25;
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width +1 , width);
            }
            if(width > 400)
                width = 400;
            columnModel.getColumn(column).setPreferredWidth(width + 30);
            tableWidth += width + 30;

            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            table.getColumnModel().getColumn(column).setCellRenderer(centerRenderer);
        }
        return tableWidth;
    }

    /**
     * Displays in the operation panel a table with all the clients
     * @param data A matrix of data. A row corresponds to a single client and the columns correspond to the known
     *             client details
     */
    public void showAll(String[][] data) {
        if (panel != null) {
            panel.removeAll();
            frame.remove(panel);
            panel.setVisible(false);
        }
        if (scrollPane != null) {
            scrollPane.removeAll();
            frame.remove(scrollPane);
        }

        String[] columns = {"ID", "First Name", "Last Name", "E-Mail", "Phone Number"};
        JTable table = new JTable(data, columns);
        table.setFillsViewportHeight(true);
        table.setFont(new Font("Courier New", Font.PLAIN, 20));
        table.setForeground(new Color(31, 34, 50));
        table.setBackground(new Color(253, 232, 233));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        Dimension dim = new Dimension(20,1);
        table.setIntercellSpacing(new Dimension(dim));
        table.setRowHeight(table.getRowHeight() + 15);
        int tableWidth = resizeColumnWidth(table);

        scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(tableWidth, 3 * startDimension.height/4));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVisible(true);
        scrollPane.getViewport().setView(table);

        constraints.gridheight = 5;
        constraints.gridwidth = 1;
        constraints.gridx = 1;
        constraints.gridy = 0;

        clientPanel.add(scrollPane, constraints);
        clientPanel.revalidate();
        clientPanel.repaint();

        frame.setSize(new Dimension(startDimension.width * 5/2 + 10, startDimension.height));
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Sets up the operations panel for searching a product to update
     */
    public void updateSearch() {
        createSearch(updateSearchButton);
    }

    /**
     * Updates the updateSearch panel with the information about the product and the update confirmation button
     * @param client The product to be updated
     */
    public void update(Client client) {
        createPanel();
        localConstraints.gridwidth = 2;
        idLabel = new JLabel("      Fill in all the new data      ");
        idLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
        idLabel.setForeground(new Color(253, 232, 233));
        panel.add(idLabel, localConstraints);

        createDataInsert(updateConfirmButton, client);
    }
}
