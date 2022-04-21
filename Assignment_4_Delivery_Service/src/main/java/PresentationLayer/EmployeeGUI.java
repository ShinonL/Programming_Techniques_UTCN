package PresentationLayer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class EmployeeGUI {
    private JTable orders;
    private Color blueGray = new Color(79, 93, 117);
    private Color darkBlue = new Color(45, 49, 66);
    private Color lightOrange = new Color(239, 131, 84);

    public EmployeeGUI() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dimension = new Dimension(screenSize.width/2, screenSize.height/2);

        JFrame frame = new JFrame("Employee");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(dimension);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        JPanel panel = new  JPanel();
        panel.setSize(dimension);
        panel.setBackground(blueGray);

        String[][] data = new String[0][];
        orders = new JTable();
        orders.setModel(new DefaultTableModel(data, new String[]{"Orders"}));
        orders.setRowSelectionAllowed(true);
        orders.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        orders.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        orders.setFillsViewportHeight(true);
        TableColumnModel columnModel = orders.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(dimension.width - 100);
        orders.setRowHeight(dimension.height - 50);

        JScrollPane scrollPane = new JScrollPane(orders, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(dimension.width - 100, dimension.height - 50));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setBackground(blueGray);
        scrollPane.setVisible(true);
        scrollPane.getViewport().setView(orders);
        panel.add(scrollPane);

        frame.add(panel);
        frame.getContentPane();
        frame.setVisible(true);
    }

    public void addOrder(String message) {
        DefaultTableModel model = (DefaultTableModel) orders.getModel();
        model.addRow(new Object[]{message});

        orders.revalidate();
        orders.repaint();
    }
}
