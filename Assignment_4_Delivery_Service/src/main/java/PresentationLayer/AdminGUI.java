package PresentationLayer;

import BusinessLayer.BaseProduct;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class AdminGUI {
    public JFrame adminFrame;
    public Dimension adminDimension;
    public JTable products;
    public JScrollPane scrollPane;
    public JPanel adminPanel;
    public GridBagConstraints constraints;
    public JButton load, generateReports, newBaseProduct, newCompositeProduct, modifyProduct, deleteProduct, logOut;
    public JButton timeReport, frequencyReport, clientsReport, dateReport;
    public JTextField start, end, freq, ordersNum, total, date;

    public JFrame createFrame, menuFrame;
    public JButton confirmCreate, confirmModify, confirmMenu;
    public JTextField title, rating, calories, protein, fat, sodium, price;

    private Color darkBlue = new Color(45, 49, 66);
    private Color lightOrange = new Color(239, 131, 84);
    private Color lightGray = new Color(191, 192, 192);
    private Color blueGray = new Color(79, 93, 117);

    String[] columns = {"Title", "Rating", "Calories", "Protein", "Fat", "Sodium", "Price"};

    public AdminGUI() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        adminDimension = new Dimension(3 * screenSize.width/4, 3 * screenSize.height/4);

        adminFrame = new JFrame("Admin Dashboard");
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminFrame.setSize(adminDimension);
        adminFrame.setLocationRelativeTo(null);
        adminFrame.setResizable(true);
        adminFrame.setLayout(new BoxLayout(adminFrame.getContentPane(), BoxLayout.Y_AXIS));

        adminPanel = new JPanel();
        adminPanel.setSize(adminDimension);
        adminPanel.setBackground(blueGray);
        adminPanel.setLayout(new GridBagLayout());

        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(15, 0, 15, 30);

        constraints.gridheight = constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = -1;

        constraints.gridy++;
        load = new JButton("Load .cvs File");
        createButton(load);
        adminPanel.add(load, constraints);

        constraints.gridy++;
        generateReports = new JButton("Generate Reports");
        createButton(generateReports);
        adminPanel.add(generateReports, constraints);

        constraints.gridy++;
        newBaseProduct = new JButton("New Base Product");
        createButton(newBaseProduct);
        adminPanel.add(newBaseProduct, constraints);

        constraints.gridy++;
        newCompositeProduct = new JButton("New Composite Product");
        createButton(newCompositeProduct);
        adminPanel.add(newCompositeProduct, constraints);

        constraints.gridy++;
        modifyProduct = new JButton("Modify Product");
        createButton(modifyProduct);
        adminPanel.add(modifyProduct, constraints);

        constraints.gridy++;
        deleteProduct = new JButton("Delete Products");
        createButton(deleteProduct);
        adminPanel.add(deleteProduct, constraints);

        constraints.gridy++;
        logOut = new JButton("Log Out");
        createButton(logOut);
        adminPanel.add(logOut, constraints);

        String[][] data = new String[0][];
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 7;

        products = new JTable();
        products.setModel(new DefaultTableModel(data, columns));
        products.setRowSelectionAllowed(true);
        products.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        products.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        products.setFillsViewportHeight(true);

        scrollPane = new JScrollPane(products, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(adminDimension.width / 3 + 45, adminDimension.height * 3/4));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setBackground(blueGray);
        scrollPane.setVisible(true);
        scrollPane.getViewport().setView(products);
        adminPanel.add(scrollPane, constraints);

        title = new JTextField();
        rating = new JTextField("0.0");
        calories = new JTextField();
        protein = new JTextField();
        fat = new JTextField();
        sodium = new JTextField();
        price = new JTextField();

        confirmCreate = new JButton("Confirm");
        confirmModify = new JButton("Confirm");
        confirmMenu = new JButton("Confirm");

        start = new JTextField();
        end = new JTextField();
        freq = new JTextField();
        ordersNum = new JTextField();
        total = new JTextField();
        date = new JTextField();

        timeReport = new JButton("Generate");
        frequencyReport = new JButton("Generate");
        clientsReport = new JButton("Generate");
        dateReport = new JButton("Generate");

        adminFrame.add(adminPanel);
        adminFrame.getContentPane();
        adminFrame.setVisible(true);
    }

    /**
     * Creates the main menu's buttons
     * @param button The button to be inserted
     */
    private void createButton(JButton button) {
        button.setFont(new Font("Courier New", Font.PLAIN, 15));
        button.setForeground(darkBlue);
        button.setBackground(lightOrange);
        button.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    }
    private void createTextField(JTextField textField) {
        textField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        textField.setBackground(lightGray);
        textField.setForeground(darkBlue);
        textField.setFont(new Font("Courier New", Font.PLAIN, 20));
        textField.setEditable(true);
    }

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
            columnModel.getColumn(column).setPreferredWidth(width + 10);
            tableWidth += width + 10;

            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            table.getColumnModel().getColumn(column).setCellRenderer(centerRenderer);
        }
        return 2 * tableWidth;
    }

    public void updateTable(String[][] data) {
        products.setModel(new DefaultTableModel(data, columns));
        products.revalidate();
        products.repaint();
        int width = resizeColumnWidth(products);
    }

    public void createNewBaseProduct() {
        createFrame = new JFrame("New Menu Item");
        createFrame.setSize(adminDimension);
        createFrame.setLocationRelativeTo(null);
        createFrame.setResizable(true);
        createFrame.setLayout(new BoxLayout(createFrame.getContentPane(), BoxLayout.Y_AXIS));

        JPanel newPanel = new JPanel();
        newPanel.setSize(adminDimension);
        newPanel.setBackground(blueGray);
        newPanel.setLayout(new GridBagLayout());

        GridBagConstraints local = new GridBagConstraints();
        local.anchor = GridBagConstraints.FIRST_LINE_START;
        local.fill = GridBagConstraints.HORIZONTAL;
        local.insets = new Insets(15, 0, 15, 30);
        local.gridheight = 1;
        local.gridwidth = 2;
        local.gridy = local.gridx = 0;
        JLabel label = new JLabel("Please insert data about the new menu item:");
        label.setFont(new Font("Courier New", Font.PLAIN, 20));
        label.setForeground(lightOrange);
        newPanel.add(label, local);

        local.gridy++;
        local.gridwidth = local.gridheight = 1;
        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
        titleLabel.setForeground(lightOrange);
        newPanel.add(titleLabel, local);

        local.gridx++;
        createTextField(title);
        title.setText("");
        newPanel.add(title, local);

        local.gridx = 0;
        local.gridy++;
        JLabel ratingLabel = new JLabel("Rating:");
        ratingLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
        ratingLabel.setForeground(lightOrange);
        newPanel.add(ratingLabel, local);

        local.gridx++;
        createTextField(rating);
        newPanel.add(rating, local);

        local.gridx = 0;
        local.gridy++;
        JLabel caloriesLabel = new JLabel("Calories:");
        caloriesLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
        caloriesLabel.setForeground(lightOrange);
        newPanel.add(caloriesLabel, local);

        local.gridx++;
        createTextField(calories);
        newPanel.add(calories, local);

        local.gridx = 0;
        local.gridy++;
        JLabel proteinLabel = new JLabel("Protein:");
        proteinLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
        proteinLabel.setForeground(lightOrange);
        newPanel.add(proteinLabel, local);

        local.gridx++;
        createTextField(protein);
        newPanel.add(protein, local);

        local.gridx = 0;
        local.gridy++;
        JLabel fatLabel = new JLabel("Fat:");
        fatLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
        fatLabel.setForeground(lightOrange);
        newPanel.add(fatLabel, local);

        local.gridx++;
        createTextField(fat);
        newPanel.add(fat, local);

        local.gridx = 0;
        local.gridy++;
        JLabel sodiumLabel = new JLabel("Sodium:");
        sodiumLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
        sodiumLabel.setForeground(lightOrange);
        newPanel.add(sodiumLabel, local);

        local.gridx++;
        createTextField(sodium);
        newPanel.add(sodium, local);

        local.gridx = 0;
        local.gridy++;
        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
        priceLabel.setForeground(lightOrange);
        newPanel.add(priceLabel, local);

        local.gridx++;
        createTextField(price);
        newPanel.add(price, local);

        local.gridx = 0;
        local.gridy++;
        local.gridwidth = 2;
        createButton(confirmCreate);
        newPanel.add(confirmCreate, local);

        createFrame.add(newPanel);
        createFrame.getContentPane();
        createFrame.setVisible(true);
    }

    public void modifyNewBaseProduct(BaseProduct item) {
        createFrame = new JFrame("Modify Menu Item");
        createFrame.setSize(adminDimension);
        createFrame.setLocationRelativeTo(null);
        createFrame.setResizable(true);
        createFrame.setLayout(new BoxLayout(createFrame.getContentPane(), BoxLayout.Y_AXIS));

        JPanel newPanel = new JPanel();
        newPanel.setSize(adminDimension);
        newPanel.setBackground(blueGray);
        newPanel.setLayout(new GridBagLayout());

        GridBagConstraints local = new GridBagConstraints();
        local.anchor = GridBagConstraints.FIRST_LINE_START;
        local.fill = GridBagConstraints.HORIZONTAL;
        local.insets = new Insets(15, 0, 15, 30);
        local.gridheight = 1;
        local.gridwidth = 2;
        local.gridy = local.gridx = 0;
        JLabel label = new JLabel("Please insert new data about the new menu item:");
        label.setFont(new Font("Courier New", Font.PLAIN, 20));
        label.setForeground(lightOrange);
        newPanel.add(label, local);

        local.gridy++;
        local.gridwidth = local.gridheight = 1;
        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
        titleLabel.setForeground(lightOrange);
        newPanel.add(titleLabel, local);

        local.gridx++;
        createTextField(title);
        title.setText(item.getTitle());
        title.setEditable(false);
        newPanel.add(title, local);

        local.gridx = 0;
        local.gridy++;
        JLabel ratingLabel = new JLabel("Rating:");
        ratingLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
        ratingLabel.setForeground(lightOrange);
        newPanel.add(ratingLabel, local);

        local.gridx++;
        createTextField(rating);
        rating.setText(String.valueOf(item.computeRating()));
        newPanel.add(rating, local);

        local.gridx = 0;
        local.gridy++;
        JLabel caloriesLabel = new JLabel("Calories:");
        caloriesLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
        caloriesLabel.setForeground(lightOrange);
        newPanel.add(caloriesLabel, local);

        local.gridx++;
        createTextField(calories);
        calories.setText(String.valueOf(item.computeCalories()));
        newPanel.add(calories, local);

        local.gridx = 0;
        local.gridy++;
        JLabel proteinLabel = new JLabel("Protein:");
        proteinLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
        proteinLabel.setForeground(lightOrange);
        newPanel.add(proteinLabel, local);

        local.gridx++;
        createTextField(protein);
        protein.setText(String.valueOf(item.computeProtein()));
        newPanel.add(protein, local);

        local.gridx = 0;
        local.gridy++;
        JLabel fatLabel = new JLabel("Fat:");
        fatLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
        fatLabel.setForeground(lightOrange);
        newPanel.add(fatLabel, local);

        local.gridx++;
        createTextField(fat);
        fat.setText(String.valueOf(item.computeFat()));
        newPanel.add(fat, local);

        local.gridx = 0;
        local.gridy++;
        JLabel sodiumLabel = new JLabel("Sodium:");
        sodiumLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
        sodiumLabel.setForeground(lightOrange);
        newPanel.add(sodiumLabel, local);

        local.gridx++;
        createTextField(sodium);
        sodium.setText(String.valueOf(item.computeSodium()));
        newPanel.add(sodium, local);

        local.gridx = 0;
        local.gridy++;
        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
        priceLabel.setForeground(lightOrange);
        newPanel.add(priceLabel, local);

        local.gridx++;
        createTextField(price);
        price.setText(String.valueOf(item.computePrice()));
        newPanel.add(price, local);

        local.gridx = 0;
        local.gridy++;
        local.gridwidth = 2;
        createButton(confirmModify);
        newPanel.add(confirmModify, local);

        createFrame.add(newPanel);
        createFrame.getContentPane();
        createFrame.setVisible(true);
    }

    public void nameCompositeProduct() {
        menuFrame = new JFrame("Name the new Menu");
        menuFrame.setSize(450, 300);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setResizable(true);
        menuFrame.setLayout(new BoxLayout(menuFrame.getContentPane(), BoxLayout.Y_AXIS));

        JPanel panel = new JPanel();
        panel.setSize(450, 300);
        panel.setBackground(blueGray);
        panel.setLayout(new GridBagLayout());

        GridBagConstraints local = new GridBagConstraints();
        local.anchor = GridBagConstraints.FIRST_LINE_START;
        local.fill = GridBagConstraints.HORIZONTAL;
        local.insets = new Insets(15, 0, 15, 0);
        local.gridwidth = local.gridheight = 1;
        local.gridy = local.gridx = 0;

        JLabel label = new JLabel("Name of the menu:");
        label.setFont(new Font("Courier New", Font.PLAIN, 20));
        label.setForeground(lightOrange);
        panel.add(label, local);

        local.gridy++;
        createTextField(title);
        panel.add(title, local);

        local.gridy++;
        confirmMenu.setFont(new Font("Courier New", Font.PLAIN, 15));
        confirmMenu.setForeground(darkBlue);
        confirmMenu.setBackground(lightOrange);
        confirmMenu.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.add(confirmMenu, local);

        menuFrame.setSize(panel.getSize());
        menuFrame.add(panel);
        menuFrame.getContentPane();
        menuFrame.setVisible(true);
    }

    public void createReports() {
        JFrame reportFrame = new JFrame("Administrator Reports");
        reportFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        reportFrame.setSize(adminDimension);
        reportFrame.setLocationRelativeTo(null);
        reportFrame.setResizable(true);
        reportFrame.setLayout(new BoxLayout(reportFrame.getContentPane(), BoxLayout.Y_AXIS));

        JPanel reportPanel = new JPanel();
        reportPanel.setSize(adminDimension);
        reportPanel.setBackground(blueGray);
        reportPanel.setLayout(new GridBagLayout());

        GridBagConstraints localConstraints = new GridBagConstraints();
        localConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        localConstraints.fill = GridBagConstraints.HORIZONTAL;
        localConstraints.insets = new Insets(15, 5, 15, 5);

        localConstraints.gridx = localConstraints.gridy = 0;
        localConstraints.gridwidth = 2;
        localConstraints.gridheight = 1;
        JLabel timeLabel = new JLabel("Time Interval Report");
        timeLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
        timeLabel.setForeground(lightOrange);
        reportPanel.add(timeLabel, localConstraints);

        localConstraints.gridwidth = 1;
        localConstraints.gridy++;
        JLabel startHour = new JLabel("Start Hour:");
        startHour.setFont(new Font("Courier New", Font.PLAIN, 20));
        startHour.setForeground(lightOrange);
        reportPanel.add(startHour, localConstraints);

        localConstraints.gridx++;
        createTextField(start);
        reportPanel.add(start, localConstraints);

        localConstraints.gridx = 0;
        localConstraints.gridy++;
        JLabel endHour = new JLabel("End Hour:");
        endHour.setFont(new Font("Courier New", Font.PLAIN, 20));
        endHour.setForeground(lightOrange);
        reportPanel.add(endHour, localConstraints);

        localConstraints.gridx++;
        createTextField(end);
        reportPanel.add(end, localConstraints);

        localConstraints.gridx++;
        createButton(timeReport);
        reportPanel.add(timeReport, localConstraints);

        localConstraints.gridwidth = 2;
        localConstraints.gridy++;
        localConstraints.gridx = 0;
        JLabel prodFreq = new JLabel("Product Frequency Reports");
        prodFreq.setFont(new Font("Courier New", Font.PLAIN, 20));
        prodFreq.setForeground(lightOrange);
        reportPanel.add(prodFreq, localConstraints);

        localConstraints.gridwidth = 1;
        localConstraints.gridy++;
        JLabel minFreq = new JLabel("Frequency");
        minFreq.setFont(new Font("Courier New", Font.PLAIN, 20));
        minFreq.setForeground(lightOrange);
        reportPanel.add(minFreq, localConstraints);

        localConstraints.gridx++;
        createTextField(freq);
        reportPanel.add(freq, localConstraints);

        localConstraints.gridx++;
        createButton(frequencyReport);
        reportPanel.add(frequencyReport, localConstraints);

        localConstraints.gridy++;
        localConstraints.gridx = 0;
        JLabel clientsLabel = new JLabel("Clients Reports");
        clientsLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
        clientsLabel.setForeground(lightOrange);
        reportPanel.add(clientsLabel, localConstraints);

        localConstraints.gridy++;
        localConstraints.gridx = 0;
        JLabel order = new JLabel("No. of orders");
        order.setFont(new Font("Courier New", Font.PLAIN, 20));
        order.setForeground(lightOrange);
        reportPanel.add(order, localConstraints);

        localConstraints.gridx++;
        createTextField(ordersNum);
        reportPanel.add(ordersNum, localConstraints);

        localConstraints.gridx = 0;
        localConstraints.gridy++;
        JLabel minTotal = new JLabel("Total");
        minTotal.setFont(new Font("Courier New", Font.PLAIN, 20));
        minTotal.setForeground(lightOrange);
        reportPanel.add(minTotal, localConstraints);

        localConstraints.gridx++;
        createTextField(total);
        reportPanel.add(total, localConstraints);

        localConstraints.gridx++;
        createButton(clientsReport);
        reportPanel.add(clientsReport, localConstraints);

        localConstraints.gridy++;
        localConstraints.gridx = 0;
        localConstraints.gridwidth = 2;
        JLabel dates = new JLabel("Reports by date");
        dates.setFont(new Font("Courier New", Font.PLAIN, 20));
        dates.setForeground(lightOrange);
        reportPanel.add(dates, localConstraints);

        localConstraints.gridy++;
        localConstraints.gridwidth = 1;
        JLabel dateLabel = new JLabel("dd.MM.YYYY");
        dateLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
        dateLabel.setForeground(lightOrange);
        reportPanel.add(dateLabel, localConstraints);

        localConstraints.gridx++;
        createTextField(date);
        reportPanel.add(date, localConstraints);

        localConstraints.gridx++;
        createButton(dateReport);
        reportPanel.add(dateReport, localConstraints);

        localConstraints.gridwidth = 3;
        localConstraints.gridx = 0;
        localConstraints.gridy++;
        JButton close = new JButton("Cancel");
        close.setFont(new Font("Courier New", Font.PLAIN, 15));
        close.setForeground(darkBlue);
        close.setBackground(lightOrange);
        close.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        close.addActionListener(e -> reportFrame.dispose());
        reportPanel.add(close, localConstraints);

        reportFrame.add(reportPanel);
        reportFrame.getContentPane();
        reportFrame.setVisible(true);
    }
}
