package PresentationLayer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class ClientGUI {
    public JFrame clientFrame;
    public JTable products;
    public JPanel clientPanel;
    public GridBagConstraints constraints;
    public JTextField keyword;
    public JTextField ratingMin, priceMin, caloriesMin, proteinMin, fatMin, sodiumMin;
    public JTextField ratingMax, priceMax, caloriesMax, proteinMax, fatMax, sodiumMax;
    public JButton search, order, refresh, logout;

    private Color darkBlue = new Color(45, 49, 66);
    private Color lightOrange = new Color(239, 131, 84);
    private Color white = new Color(255, 255, 255);
    private Color lightGray = new Color(191, 192, 192);
    private Color blueGray = new Color(79, 93, 117);

    String[] columns = {"Title", "Rating", "Calories", "Protein", "Fat", "Sodium", "Price"};

    public ClientGUI() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension clientDimension = new Dimension(3 * screenSize.width/4, 3 * screenSize.height/4);

        clientFrame = new JFrame("Client Dashboard");
        clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        clientFrame.setSize(clientDimension);
        clientFrame.setLocationRelativeTo(null);
        clientFrame.setResizable(true);
        clientFrame.setLayout(new BoxLayout(clientFrame.getContentPane(), BoxLayout.Y_AXIS));

        clientPanel = new JPanel();
        clientPanel.setSize(clientDimension);
        clientPanel.setBackground(blueGray);
        clientPanel.setLayout(new GridBagLayout());

        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(0, 0, 15, 15);

        constraints.gridheight = 1;
        constraints.gridx = constraints.gridy = 0;

        constraints.gridwidth = 3;
        search = new JButton("Search a product:");
        search.setFont(new Font("Courier New", Font.PLAIN, 15));
        search.setForeground(darkBlue);
        search.setBackground(lightOrange);
        search.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        clientPanel.add(search, constraints);

        constraints.gridwidth = 1;
        constraints.gridy++;
        JLabel keywordLabel = new JLabel("Keyword:");
        keywordLabel.setFont(new Font("Courier New", Font.PLAIN, 20));
        keywordLabel.setForeground(lightOrange);
        clientPanel.add(keywordLabel, constraints);

        constraints.gridwidth = 2;
        constraints.gridx++;
        keyword = new JTextField();
        createTextField(keyword);

        constraints.gridwidth = 1;
        constraints.gridy++;
        JLabel minimum = new JLabel("Minimum");
        minimum.setFont(new Font("Courier New", Font.PLAIN, 20));
        minimum.setForeground(lightOrange);
        clientPanel.add(minimum, constraints);

        constraints.gridx++;
        JLabel maximum = new JLabel("Maximum");
        maximum.setFont(new Font("Courier New", Font.PLAIN, 20));
        maximum.setForeground(lightOrange);
        clientPanel.add(maximum, constraints);

        constraints.gridx = 0;
        constraints.gridy++;
        JLabel rating = new JLabel("Rating");
        rating.setFont(new Font("Courier New", Font.PLAIN, 20));
        rating.setForeground(lightOrange);
        clientPanel.add(rating, constraints);

        constraints.gridx++;
        ratingMin = new JTextField();
        createTextField(ratingMin);

        constraints.gridx++;
        ratingMax = new JTextField();
        createTextField(ratingMax);

        constraints.gridx = 0;
        constraints.gridy++;
        JLabel price = new JLabel("Price");
        price.setFont(new Font("Courier New", Font.PLAIN, 20));
        price.setForeground(lightOrange);
        clientPanel.add(price, constraints);

        constraints.gridx++;
        priceMin = new JTextField();
        createTextField(priceMin);

        constraints.gridx++;
        priceMax = new JTextField();
        createTextField(priceMax);

        constraints.gridx = 0;
        constraints.gridy++;
        JLabel calories = new JLabel("Calories");
        calories.setFont(new Font("Courier New", Font.PLAIN, 20));
        calories.setForeground(lightOrange);
        clientPanel.add(calories, constraints);

        constraints.gridx++;
        caloriesMin = new JTextField();
        createTextField(caloriesMin);

        constraints.gridx++;
        caloriesMax = new JTextField();
        createTextField(caloriesMax);

        constraints.gridx = 0;
        constraints.gridy++;
        JLabel fat = new JLabel("Fat");
        fat.setFont(new Font("Courier New", Font.PLAIN, 20));
        fat.setForeground(lightOrange);
        clientPanel.add(fat, constraints);

        constraints.gridx++;
        fatMin = new JTextField();
        createTextField(fatMin);

        constraints.gridx++;
        fatMax = new JTextField();
        createTextField(fatMax);

        constraints.gridx = 0;
        constraints.gridy++;
        JLabel protein = new JLabel("Protein");
        protein.setFont(new Font("Courier New", Font.PLAIN, 20));
        protein.setForeground(lightOrange);
        clientPanel.add(protein, constraints);

        constraints.gridx++;
        proteinMin = new JTextField();
        createTextField(proteinMin);

        constraints.gridx++;
        proteinMax = new JTextField();
        createTextField(proteinMax);

        constraints.gridx = 0;
        constraints.gridy++;
        JLabel sodium = new JLabel("Sodium");
        sodium.setFont(new Font("Courier New", Font.PLAIN, 20));
        sodium.setForeground(lightOrange);
        clientPanel.add(sodium, constraints);

        constraints.gridx++;
        sodiumMin = new JTextField();
        createTextField(sodiumMin);

        constraints.gridx++;
        sodiumMax = new JTextField();
        createTextField(sodiumMax);

        constraints.gridx = 0;
        constraints.gridy++;
        constraints.gridwidth = 3;
        refresh = new JButton("Refresh");
        refresh.setFont(new Font("Courier New", Font.PLAIN, 15));
        refresh.setForeground(darkBlue);
        refresh.setBackground(lightOrange);
        refresh.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        clientPanel.add(refresh, constraints);

        constraints.gridy++;
        logout = new JButton("Log Out");
        logout.setFont(new Font("Courier New", Font.PLAIN, 15));
        logout.setForeground(darkBlue);
        logout.setBackground(lightOrange);
        logout.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        clientPanel.add(logout, constraints);

        constraints.gridwidth = 1;
        constraints.gridx = 4;
        constraints.gridy = 0;
        order = new JButton("Order Now");
        order.setFont(new Font("Courier New", Font.PLAIN, 15));
        order.setForeground(darkBlue);
        order.setBackground(lightOrange);
        order.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        clientPanel.add(order, constraints);

        constraints.gridy++;
        constraints.gridheight = 10;
        String[][] data = new String[0][];
        products = new JTable();
        products.setModel(new DefaultTableModel(data, columns));
        products.setRowSelectionAllowed(true);
        products.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        products.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        products.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(products, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(clientDimension.width / 3 + 45, clientDimension.height * 3/4));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setBackground(blueGray);
        scrollPane.setVisible(true);
        scrollPane.getViewport().setView(products);
        clientPanel.add(scrollPane, constraints);

        clientFrame.add(clientPanel);
        clientFrame.getContentPane();
        clientFrame.setVisible(true);
    }

    private void createTextField(JTextField textField) {
        textField.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 5));
        textField.setBackground(lightGray);
        textField.setForeground(darkBlue);
        textField.setFont(new Font("Courier New", Font.PLAIN, 16));
        textField.setEditable(true);
        clientPanel.add(textField, constraints);
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
}
