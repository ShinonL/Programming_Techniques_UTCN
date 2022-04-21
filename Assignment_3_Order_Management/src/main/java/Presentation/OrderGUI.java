package Presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The view of the orders management part
 */
public class OrderGUI {
    public JButton insertButton, showBillsButton, backButton, showAllButton, cancelOrder;
    public JFrame frame;
    public JPanel orderPanel, panel;
    public Dimension startDimension;
    private GridBagConstraints constraints, localConstraints;

    public JButton insertSubmitButton, searchButton, addProductButton;
    public JTextField addressField;
    public List<JTextField> quantities;
    public JLabel clientIdLabel;
    public JComboBox clientBox;
    public List<JComboBox> productTypes;
    public JScrollPane scrollPane;

    /**
     * It "inherits" the frame from the menu
     * @param frame the JFrame used in the menu
     */
    public OrderGUI(JFrame frame) {
        this.frame = frame;
    }

    /**
     * The first method called. It creates the client management menu
     */
    public void start() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        startDimension = new Dimension(screenSize.width/4 + 1, screenSize.height/2);
        frame.setSize(startDimension);

        orderPanel = new JPanel();
        orderPanel.setSize(startDimension);
        orderPanel.setLayout(new GridBagLayout());
        orderPanel.setBackground(new Color(89, 100, 117));
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridheight = constraints.gridwidth = 1;
        constraints.insets = new Insets(15, 0, 15, 30);
        constraints.gridx = 0;
        constraints.gridy = -1;

        insertButton = new JButton("Create a new Order");
        createButton(insertButton);

        showAllButton = new JButton("Show all Orders");
        createButton(showAllButton);

        showBillsButton = new JButton("Open Bills Folder");
        createButton(showBillsButton);

        searchButton = new JButton("Cancel an Order");
        createButton(searchButton);

        backButton = new JButton("Back");
        createButton(backButton);

        insertSubmitButton = new JButton("Submit");
        addProductButton = new JButton("Add Product");
        cancelOrder = new JButton("Confirm Cancellation");
        panel = new JPanel();

        frame.add(orderPanel);
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
        orderPanel.add(button, constraints);
    }

    /**
     * Creates the operating panel
     * Here the operations upon the orders may be completed
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
     * Allows created an order only for the existing clients.
     * @param clients A list of existing clients
     */
    public void insertOrder(String[] clients) {
        orderPanel.setPreferredSize(new Dimension(startDimension.width * 2, 3 * startDimension.height/4));

        createPanel();

        localConstraints.gridwidth = 3;
        clientIdLabel = new JLabel("Select an existing client: ");
        clientIdLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
        clientIdLabel.setForeground(new Color(253, 232, 233));
        panel.add(clientIdLabel, localConstraints);

        localConstraints.gridy = 1;
        clientBox = new JComboBox<>(clients);
        clientBox.setFont(new Font("Courier New", Font.PLAIN, 20));
        clientBox.setForeground(new Color(31, 34, 50));
        clientBox.setBackground(new Color(253, 232, 233));
        panel.add(clientBox, localConstraints);

        localConstraints.gridy++;
        localConstraints.gridwidth = 1;
        localConstraints.gridx = 0;
        JLabel addressLabel = new JLabel("Address: ");
        addressLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
        addressLabel.setForeground(new Color(253, 232, 233));
        panel.add(addressLabel, localConstraints);

        localConstraints.gridx = 1;
        localConstraints.gridwidth = 2;
        addressField = new JTextField();
        addressField.setFont(new Font("Courier New", Font.PLAIN, 20));
        addressField.setForeground(new Color(31, 34, 50));
        addressField.setBackground(new Color(253, 232, 233));
        panel.add(addressField, localConstraints);

        localConstraints.gridx = 0;
        localConstraints.gridy++;
        localConstraints.gridwidth = 3;
        addProductButton.setFont(new Font("Courier New", Font.PLAIN, 15));
        addProductButton.setForeground(new Color(31, 34, 50));
        addProductButton.setBackground(new Color(253, 232, 233));
        addProductButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        panel.add(addProductButton, localConstraints);

        productTypes = new ArrayList<>();
        quantities = new ArrayList<>();
        localConstraints.gridheight = localConstraints.gridwidth = 1;

        orderPanel.add(panel, constraints);
        orderPanel.revalidate();
        orderPanel.repaint();

        frame.setSize(new Dimension(startDimension.width * 5/2 + 10, startDimension.height));
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Adds a product to the order.
     * Only existing products are allowed.
     * @param products A list of available products
     */
    public void addProduct(String[] products) {
        panel.remove(insertSubmitButton);

        JComboBox<String> productBox = new JComboBox<>(products);
        productBox.setFont(new Font("Courier New", Font.PLAIN, 20));
        productBox.setForeground(new Color(31, 34, 50));
        productBox.setBackground(new Color(253, 232, 233));

        productTypes.add(productBox);

        localConstraints.gridwidth = 1;
        localConstraints.gridx = 0;
        localConstraints.gridy++;
        panel.add(productTypes.get(productTypes.size() - 1), localConstraints);

        localConstraints.gridx++;
        JLabel quantityLabel = new JLabel(" Quantity ");
        quantityLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
        quantityLabel.setForeground(new Color(253, 232, 233));
        panel.add(quantityLabel, localConstraints);

        localConstraints.gridx++;
        JTextField qntyField = new JTextField();
        qntyField.setFont(new Font("Courier New", Font.PLAIN, 18));
        qntyField.setForeground(new Color(31, 34, 50));
        qntyField.setBackground(new Color(227, 186, 198));

        quantities.add(qntyField);
        panel.add(quantities.get(quantities.size() - 1), localConstraints);

        localConstraints.gridy++;
        localConstraints.gridx = 0;
        localConstraints.gridwidth = 3;
        insertSubmitButton.setFont(new Font("Courier New", Font.PLAIN, 15));
        insertSubmitButton.setForeground(new Color(31, 34, 50));
        insertSubmitButton.setBackground(new Color(253, 232, 233));
        insertSubmitButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        panel.add(insertSubmitButton, localConstraints);

        orderPanel.add(panel, constraints);
        orderPanel.revalidate();
        orderPanel.repaint();

        frame.setSize(new Dimension(startDimension.width * 5/2 + 10, startDimension.height));
        frame.revalidate();
        frame.repaint();
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
     * Displays in the operation panel a table with all the orders
     * @param data A matrix of data. A row corresponds to a single order and the columns correspond to the known
     *             order details
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

        String[] columns = {"ID", "Client Name", "Shipping Address", "Total"};
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

        orderPanel.add(scrollPane, constraints);
        orderPanel.revalidate();
        orderPanel.repaint();

        frame.setSize(new Dimension(startDimension.width * 5/2 + 10, startDimension.height));
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Creates the search panel for the cancel menu
     */
    public void createSearch() {
        createPanel();
        localConstraints.gridwidth = 1;

        localConstraints.gridx = localConstraints.gridy = 0;
        clientIdLabel = new JLabel("Search by ID: ");
        clientIdLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
        clientIdLabel.setForeground(new Color(253, 232, 233));
        panel.add(clientIdLabel, localConstraints);

        localConstraints.gridy++;
        addressField = new JTextField();
        addressField.setFont(new Font("Courier New", Font.PLAIN, 18));
        addressField.setForeground(new Color(31, 34, 50));
        addressField.setBackground(new Color(227, 186, 198));
        panel.add(addressField, localConstraints);

        localConstraints.gridwidth = 2;
        localConstraints.gridx = 0;
        localConstraints.gridy++;
        cancelOrder.setFont(new Font("Courier New", Font.PLAIN, 20));
        cancelOrder.setForeground(new Color(31, 34, 50));
        cancelOrder.setBackground(new Color(253, 232, 233));
        cancelOrder.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.add(cancelOrder, localConstraints);

        orderPanel.add(panel, constraints);
        orderPanel.revalidate();
        orderPanel.repaint();

        frame.setSize(new Dimension(startDimension.width * 5/2 + 10, startDimension.height));
        frame.revalidate();
        frame.repaint();
    }
}
