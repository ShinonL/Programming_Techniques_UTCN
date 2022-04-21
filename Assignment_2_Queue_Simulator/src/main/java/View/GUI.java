package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

/**
 * The User Interface is split into 4 panels:
 * 1. Input: Here the user can enter data and manipulate the simulation.
 * 2. Status: Here are the tasks that ar known to exist, but their arrival time was not reached yet.
 * 3. Scroll/Queue: Here are the servers (queues), with the active tasks associated to each server.
 * 4. Output: Here the minimum output required is displayed.
 */
public class GUI {
    public JFrame frame;

    public JPanel inputPanel, outputPanel, buttonPanel, statusPanel;
    public JScrollPane queueScrollPanel, taskScrollPanel;
    public JLabel numOfClientsLabel, numOfQueuesLabel, maxArrivalTimeLabel, minArrivalTimeLabel, maxServiceTimeLabel, minServiceTimeLabel;
    public JTextField numOfClientsField, numOfQueuesField, maxArrivalTimeField, minArrivalTimeField, maxServiceTimeField, minServiceTimeField;
    public JLabel simulationTimeLabel;
    public JTextField simulationTimeField;
    public RoundButton startButton, policyButton;
    public JLabel avgServiceTime, avgWaitingTime, currentSimulatiuonTime, peakHour;

    public List<JTextField> taskBoxes;
    public List<JPanel> queuesPanels;
    public Dimension preferredDimension;

    public GUI() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        preferredDimension = new Dimension((int)(2*screenSize.getWidth()/5), (int)(2*screenSize.getHeight()/9));

        ImageIcon icon = new ImageIcon("src/main/resources/human-queue.png");

        frame = new JFrame("Queue Simulator");
        frame.setSize((int)(2*screenSize.getWidth()/5), (int)(2*screenSize.getHeight()/3));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setIconImage(icon.getImage());

        inputPanel = new JPanel();
        inputPanel.setBackground(new Color(73, 69, 70));
        inputPanel.setPreferredSize(preferredDimension);
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.ipady = 5;

        constraints.gridx = constraints.gridy = 0;
        simulationTimeLabel = new JLabel("Simulation time (s): ");
        simulationTimeLabel.setFont(new Font("Courier New", Font.PLAIN, 16));
        simulationTimeLabel.setForeground(new Color(248, 236, 222));
        simulationTimeLabel.setVisible(true);
        inputPanel.add(simulationTimeLabel, constraints);

        constraints.gridx = 1;
        simulationTimeField = new JTextField(5);
        simulationTimeField.setFont(new Font("Courier New", Font.PLAIN, 16));
        simulationTimeField.setForeground(new Color(248, 236, 222));
        simulationTimeField.setBackground(new Color(73, 69, 70));
        simulationTimeField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        simulationTimeField.setVisible(true);
        inputPanel.add(simulationTimeField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        numOfClientsLabel = new JLabel("Number Of Clients: ");
        numOfClientsLabel.setFont(new Font("Courier New", Font.PLAIN, 16));
        numOfClientsLabel.setForeground(new Color(248, 236, 222));
        numOfClientsLabel.setVisible(true);
        inputPanel.add(numOfClientsLabel, constraints);

        constraints.gridx = 1;
        numOfClientsField = new JTextField(5);
        numOfClientsField.setFont(new Font("Courier New", Font.PLAIN, 16));
        numOfClientsField.setForeground(new Color(248, 236, 222));
        numOfClientsField.setBackground(new Color(73, 69, 70));
        numOfClientsField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        numOfClientsField.setVisible(true);
        inputPanel.add(numOfClientsField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        numOfQueuesLabel = new JLabel("Number Of Queues: ");
        numOfQueuesLabel.setFont(new Font("Courier New", Font.PLAIN, 16));
        numOfQueuesLabel.setForeground(new Color(248, 236, 222));
        numOfQueuesLabel.setVisible(true);
        inputPanel.add(numOfQueuesLabel, constraints);

        constraints.gridx = 1;
        numOfQueuesField = new JTextField(5);
        numOfQueuesField.setFont(new Font("Courier New", Font.PLAIN, 16));
        numOfQueuesField.setForeground(new Color(248, 236, 222));
        numOfQueuesField.setBackground(new Color(73, 69, 70));
        numOfQueuesField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        numOfQueuesField.setVisible(true);
        inputPanel.add(numOfQueuesField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        minArrivalTimeLabel = new JLabel("Minimum Arrival Time: ");
        minArrivalTimeLabel.setFont(new Font("Courier New", Font.PLAIN, 16));
        minArrivalTimeLabel.setForeground(new Color(248, 236, 222));
        minArrivalTimeLabel.setVisible(true);
        inputPanel.add(minArrivalTimeLabel, constraints);

        constraints.gridx = 1;
        minArrivalTimeField = new JTextField(5);
        minArrivalTimeField.setFont(new Font("Courier New", Font.PLAIN, 16));
        minArrivalTimeField.setForeground(new Color(248, 236, 222));
        minArrivalTimeField.setBackground(new Color(73, 69, 70));
        minArrivalTimeField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        minArrivalTimeField.setVisible(true);
        inputPanel.add(minArrivalTimeField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        maxArrivalTimeLabel = new JLabel("Maximum Arrival Time: ");
        maxArrivalTimeLabel.setFont(new Font("Courier New", Font.PLAIN, 16));
        maxArrivalTimeLabel.setForeground(new Color(248, 236, 222));
        maxArrivalTimeLabel.setVisible(true);
        inputPanel.add(maxArrivalTimeLabel, constraints);

        constraints.gridx = 1;
        maxArrivalTimeField = new JTextField(5);
        maxArrivalTimeField.setFont(new Font("Courier New", Font.PLAIN, 16));
        maxArrivalTimeField.setForeground(new Color(248, 236, 222));
        maxArrivalTimeField.setBackground(new Color(73, 69, 70));
        maxArrivalTimeField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        maxArrivalTimeField.setVisible(true);
        inputPanel.add(maxArrivalTimeField, constraints);

        constraints.gridx = 2;
        constraints.gridy = 3;
        minServiceTimeLabel = new JLabel("  Minimum Service Time: ");
        minServiceTimeLabel.setFont(new Font("Courier New", Font.PLAIN, 16));
        minServiceTimeLabel.setForeground(new Color(248, 236, 222));
        minServiceTimeLabel.setVisible(true);
        inputPanel.add(minServiceTimeLabel, constraints);

        constraints.gridx = 3;
        minServiceTimeField = new JTextField(5);
        minServiceTimeField.setFont(new Font("Courier New", Font.PLAIN, 16));
        minServiceTimeField.setForeground(new Color(248, 236, 222));
        minServiceTimeField.setBackground(new Color(73, 69, 70));
        minServiceTimeField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        minServiceTimeField.setVisible(true);
        inputPanel.add(minServiceTimeField, constraints);

        constraints.gridx = 2;
        constraints.gridy = 4;
        maxServiceTimeLabel = new JLabel("  Maximum Service Time: ");
        maxServiceTimeLabel.setFont(new Font("Courier New", Font.PLAIN, 16));
        maxServiceTimeLabel.setForeground(new Color(248, 236, 222));
        maxServiceTimeLabel.setVisible(true);
        inputPanel.add(maxServiceTimeLabel, constraints);

        constraints.gridx = 3;
        maxServiceTimeField = new JTextField(5);
        maxServiceTimeField.setFont(new Font("Courier New", Font.PLAIN, 16));
        maxServiceTimeField.setForeground(new Color(248, 236, 222));
        maxServiceTimeField.setBackground(new Color(73, 69, 70));
        maxServiceTimeField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        maxServiceTimeField.setVisible(true);
        inputPanel.add(maxServiceTimeField, constraints);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBackground(new Color(73, 69, 70));

        startButton = new RoundButton("Start");
        startButton.setFont(new Font("Courier New", Font.PLAIN, 20));
        startButton.setForeground(new Color(248, 236, 222));
        startButton.setBackground(new Color(130, 142, 140));
        buttonPanel.add(startButton, constraints);

        buttonPanel.add(Box.createRigidArea(new Dimension(20, 0)));

        policyButton = new RoundButton("Shortest Queue");
        policyButton.setFont(new Font("Courier New", Font.PLAIN, 20));
        policyButton.setForeground(new Color(248, 236, 222));
        policyButton.setBackground(new Color(232, 134, 108));
        buttonPanel.add(policyButton, constraints);

        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridheight = 3;
        constraints.gridwidth = 2;
        inputPanel.add(buttonPanel, constraints);

        statusPanel = new JPanel();
        statusPanel.setBackground(new Color(73, 69, 70));
        statusPanel.setPreferredSize(new Dimension(19*preferredDimension.width/20, 30*preferredDimension.height));
        statusPanel.setLayout(new FlowLayout());

        outputPanel = new JPanel();
        outputPanel.setPreferredSize(new Dimension(preferredDimension.width, 3*preferredDimension.height/4));
        outputPanel.setBackground(new Color(73, 69, 70));
        outputPanel.setBorder(BorderFactory.createEmptyBorder());

        frame.add(inputPanel);
        frame.getContentPane();
        frame.setVisible(true);
    }
}
