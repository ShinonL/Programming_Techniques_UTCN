package Controller;

import Model.SelectionPolicy;
import Model.Server;
import Model.SimulationManager;
import Model.Task;
import View.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class connects the user interface with the back-end models
 */
public class GUIController {
    private static GUI gui = new GUI();
    private SimulationManager simulationManager;
    public static SelectionPolicy policy;

    /**
     * when starting the application
     * if start is pressed, check the input. If valid, then set the initial parameters and start a SimulationManagerThread
     * Update the GUI to see the queues evolution
     *
     * if "Shortest Queue" is pressed, change the server selection policy to the shortest queue one
     */
    public void startGUI() {
        gui.startButton.addActionListener(e -> {
            boolean ok = true;
            try {
                Validator.validateInput(gui.simulationTimeField.getText(), gui.maxArrivalTimeField.getText(), gui.minArrivalTimeField.getText(),
                        gui.maxServiceTimeField.getText(), gui.minServiceTimeField.getText(), gui.numOfClientsField.getText(), gui.numOfQueuesField.getText());
            } catch (IllegalArgumentException er) {
                JOptionPane.showMessageDialog(null, er.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ok = false;
            }
            if (ok) {
                int timeLimit = Integer.parseInt(gui.simulationTimeField.getText());
                int maxArrivalTime = Integer.parseInt(gui.maxArrivalTimeField.getText());
                int minArrivalTime = Integer.parseInt(gui.minArrivalTimeField.getText());
                int maxServiceTime = Integer.parseInt(gui.maxServiceTimeField.getText());
                int minServiceTime = Integer.parseInt(gui.minServiceTimeField.getText());
                int numberOfServers = Integer.parseInt(gui.numOfQueuesField.getText());
                int numberOfClients = Integer.parseInt(gui.numOfClientsField.getText());

                if (gui.queueScrollPanel != null) {
                    gui.frame.remove(gui.queueScrollPanel);
                    gui.frame.remove(gui.taskScrollPanel);
                }
                Task.reset();
                Server.reset();

                simulationManager = new SimulationManager(timeLimit, maxArrivalTime,
                        minArrivalTime, maxServiceTime, minServiceTime, numberOfServers, numberOfClients, policy);
                Thread simulationThread = new Thread(simulationManager);
                simulationThread.start();

                GUIController.updateGUI(numberOfClients, numberOfServers);
            }
        });

        gui.policyButton.addActionListener(e -> {
           policy = SelectionPolicy.SHORTEST_QUEUE;
        });
    }

    /**
     * Update the GUI to display the panels for simulation
     * @param numberOfTasks Used to create the fields for each task
     * @param numberOfServers Used to create the panels for each server
     */
    public static void updateGUI(int numberOfTasks, int  numberOfServers) {
        gui.taskBoxes = new ArrayList<>(numberOfTasks);

        for (int i = 1; i <= numberOfTasks; i++) {
            JTextField waitingTask = new JTextField(" Client " + i + " ");
            waitingTask.setFont(new Font("Courier New", Font.PLAIN, 16));
            waitingTask.setBackground(new Color(248, 236, 222));
            waitingTask.setForeground(new Color(73, 69, 70));
            waitingTask.setEditable(false);
            waitingTask.setBorder(BorderFactory.createEmptyBorder());
            gui.taskBoxes.add(waitingTask);
            gui.statusPanel.add(waitingTask);
        }

        gui.taskScrollPanel = new JScrollPane(gui.statusPanel);
        gui.taskScrollPanel.setPreferredSize(new Dimension(gui.preferredDimension.width, gui.preferredDimension.height/2));
        gui.taskScrollPanel.setBorder(BorderFactory.createEmptyBorder());
        gui.taskScrollPanel.setVisible(true);
        gui.taskScrollPanel.getViewport().setView(gui.statusPanel);

        JPanel scrollContainer = new JPanel();
        scrollContainer.setLayout(new GridLayout(1, numberOfServers));
        gui.queuesPanels = new ArrayList<>(numberOfServers);

        for (int i = 1; i <= numberOfServers; i++) {
            JPanel serverPanel = new JPanel();
            serverPanel.setLayout(new GridLayout(numberOfTasks+1, 1));
            serverPanel.setBackground(new Color(73, 69, 70));
            JTextField title = new JTextField("Queue " + i);
            title.setFont(new Font("Courier New", Font.PLAIN, 16));
            title.setForeground(new Color(73, 69, 70));
            title.setBackground(new Color(232, 134, 108));
            title.setHorizontalAlignment(JTextField.CENTER);
            title.setBorder(BorderFactory.createEmptyBorder());
            title.setEditable(false);
            serverPanel.add(title);
            scrollContainer.add(serverPanel);

            gui.queuesPanels.add(serverPanel);
        }

        gui.queueScrollPanel = new JScrollPane(scrollContainer);
        gui.queueScrollPanel.setPreferredSize(new Dimension(3*gui.preferredDimension.width, 3*gui.preferredDimension.height/4));
        gui.queueScrollPanel.setBorder(BorderFactory.createEmptyBorder());
        gui.queueScrollPanel.setVisible(true);
        gui.queueScrollPanel.getViewport().setView(scrollContainer);

        gui.statusPanel.revalidate();
        gui.statusPanel.repaint();

        gui.outputPanel.setLayout(new GridLayout(5, 1));
        gui.outputPanel.setVisible(true);

        gui.frame.revalidate();
        gui.frame.repaint();

        gui.frame.add(gui.taskScrollPanel);
        gui.frame.add(gui.queueScrollPanel);
        gui.frame.add(gui.outputPanel);
    }

    /**
     * Add a task to a Queue panel
     * @param server The ID of the server where to add the task
     * @param task The ID of the task to be added
     */
    public static void addToGUIQueue(int server, int task) {
        gui.queuesPanels.get(server - 1).getComponent(0).setBackground(new Color(197, 208, 193));

        gui.taskBoxes.get(task - 1).setBackground(new Color(130, 142, 140));
        gui.queuesPanels.get(server - 1).add(gui.taskBoxes.get(task - 1));
        gui.statusPanel.remove(gui.taskBoxes.get(task - 1));

        gui.frame.revalidate();
        gui.frame.repaint();
    }

    /**
     * Remove a task from a specific server
     * @param server The ID of the server from which to remove the task
     * @param task The ID of the task to be removed
     */
    public static void removeFromGUIQueue(int server, int task) {
        if(gui.queuesPanels.get(server - 1).getComponentCount() == 2)
            gui.queuesPanels.get(server - 1).getComponent(0).setBackground(new Color(232, 134, 108));

        gui.queuesPanels.get(server - 1).remove(gui.taskBoxes.get(task - 1));

        gui.frame.revalidate();
        gui.frame.repaint();
    }

    /**
     * Update the output panel with the most recent information about the simulation
     * @param simulationManager Has all the data about the simulation
     */
    public static void updateOutput(SimulationManager simulationManager) {
        if(gui.currentSimulatiuonTime != null) {
            gui.outputPanel.removeAll();
        }

        gui.currentSimulatiuonTime = new JLabel("Current Simulation Time: " + SimulationManager.currentTime, SwingConstants.CENTER);
        gui.currentSimulatiuonTime.setFont(new Font("Courier New", Font.PLAIN, 16));
        gui.currentSimulatiuonTime.setForeground(new Color(248, 236, 222));
        gui.outputPanel.add(gui.currentSimulatiuonTime);

        gui.avgWaitingTime = new JLabel("Average Waiting Time: " + simulationManager.getAverageWaitingTime(), SwingConstants.CENTER);
        gui.avgWaitingTime.setFont(new Font("Courier New", Font.PLAIN, 16));
        gui.avgWaitingTime.setForeground(new Color(248, 236, 222));
        gui.outputPanel.add(gui.avgWaitingTime);

        gui.avgServiceTime = new JLabel("Average Service Time: " + simulationManager.getAverageServiceTime(), SwingConstants.CENTER);
        gui.avgServiceTime.setFont(new Font("Courier New", Font.PLAIN, 16));
        gui.avgServiceTime.setForeground(new Color(248, 236, 222));
        gui.outputPanel.add(gui.avgServiceTime);

        gui.peakHour = new JLabel("Peak Hour: " + simulationManager.getPeakHour(), SwingConstants.CENTER);
        gui.peakHour.setFont(new Font("Courier New", Font.PLAIN, 16));
        gui.peakHour.setForeground(new Color(248, 236, 222));
        gui.outputPanel.add(gui.peakHour);

        JButton logButton = new JButton("Open Log File");
        logButton.setBorder(BorderFactory.createEmptyBorder());
        logButton.setFont(new Font("Courier New", Font.PLAIN, 16));
        logButton.setForeground(new Color(248, 236, 222));
        logButton.setBackground(new Color(130, 142, 140));
        logButton.addActionListener(e -> {
            try {
                if(!SimulationManager.running)
                    Desktop.getDesktop().open(new File("Log.txt"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        gui.outputPanel.add(logButton);

        gui.outputPanel.revalidate();
        gui.outputPanel.repaint();

        gui.frame.revalidate();
        gui.frame.repaint();
    }
}
