package Presentation;

import javax.swing.*;
import java.awt.*;

/**
 * The components of the main start page
 * It forms kind of a menu to choose to manage either client, product or order tables
 */
public class GUI {
    public JFrame frame;
    public JButton clientButton, orderButton, productButton;
    public JPanel startPanel;
    public Dimension startDimension;

    public void start() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        startDimension = new Dimension(screenSize.width/4, screenSize.height/2);

        ImageIcon icon = new ImageIcon("src/main/resources/warehouse-icon.jpg");

        frame = new JFrame("Order Management");
        frame.setSize(startDimension);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setIconImage(icon.getImage());

        startPanel = new JPanel();
        startPanel.setPreferredSize(startDimension);
        startPanel.setLayout(new GridBagLayout());
        startPanel.setBackground(new Color(31, 34, 50));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridheight = constraints.gridwidth = 1;
        constraints.insets = new Insets(15, 0, 15, 0);

        constraints.gridx = constraints.gridy = 0;
        clientButton = new JButton("Manage Clients");
        clientButton.setFont(new Font("Courier New", Font.PLAIN, 20));
        clientButton.setForeground(new Color(89, 100, 117));
        clientButton.setBackground(new Color(253, 232, 233));
        clientButton.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        startPanel.add(clientButton, constraints);

        constraints.gridy = 1;
        productButton = new JButton("Manage Products");
        productButton.setFont(new Font("Courier New", Font.PLAIN, 20));
        productButton.setForeground(new Color(89, 100, 117));
        productButton.setBackground(new Color(253, 232, 233));
        productButton.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        startPanel.add(productButton, constraints);

        constraints.gridy = 2;
        orderButton = new JButton("Manage Orders");
        orderButton.setFont(new Font("Courier New", Font.PLAIN, 20));
        orderButton.setForeground(new Color(89, 100, 117));
        orderButton.setBackground(new Color(253, 232, 233));
        orderButton.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        startPanel.add(orderButton, constraints);

        frame.add(startPanel);
        frame.getContentPane();
        frame.setVisible(true);
    }

    public void reset() {
        frame.setSize(startDimension);

        frame.add(startPanel);
        frame.getContentPane();
        frame.setVisible(true);
    }
}
