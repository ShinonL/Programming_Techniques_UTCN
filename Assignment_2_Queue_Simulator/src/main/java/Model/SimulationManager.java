package Model;

import Controller.GUIController;
import Controller.Scheduler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The "Producer" class
 * This class manages the simulation by:
 * - creating random Tasks
 * - calling the Scheduler to dispatch the task
 * - keeping track of time
 * - adding data to the log file
 * - calls the GUI Controller to update the information given for display
 *
 */
public class SimulationManager implements Runnable {
    private int timeLimit;
    private int maxArrivalTime, minArrivalTime;
    private int maxServiceTime, minServiceTime;
    private int numberOfServers, numberOfTasks;

    public static int currentTime = 0, clientCount = 0;
    public static double averageWaitingTime = 0, averageServiceTime = 0;
    public static boolean running;

    private List<Task> generatedTasks;
    private Scheduler scheduler;
    private int peakHour, maxNumberOfActiveTasks = -1;

    /**
     * Constructor
     * Basic information from the user is initialized.
     * Scheduler is created using the data entered.
     * The Log file is created if it doesn't exist, or emptied if it does.
     * All parameters are given by the user
     * @param timeLimit         Simulation must not exceed this time
     * @param maxArrivalTime    A task may not be entered after this time limit
     * @param minArrivalTime    A task may not enter a server before this time limit
     * @param maxServiceTime    Processing a task may not exceed this time limit
     * @param minServiceTime    Processing a task may not take less than this time limit
     * @param numberOfServers   The number of available servers (queues)
     * @param numberOfTasks     The number of tasks to be generated
     * @param policy            The strategy used by the Scheduler to decide where to place the next task
     */
    public SimulationManager(int timeLimit, int maxArrivalTime, int minArrivalTime, int maxServiceTime, int minServiceTime,
                             int numberOfServers, int numberOfTasks, SelectionPolicy policy) {
        scheduler = new Scheduler(numberOfServers);
        scheduler.changeStrategy(policy);
        this.timeLimit = timeLimit;
        this.maxArrivalTime = maxArrivalTime;
        this.minArrivalTime = minArrivalTime;
        this.maxServiceTime = maxServiceTime;
        this.minServiceTime = minServiceTime;
        this.numberOfTasks = numberOfTasks;
        this.numberOfServers = numberOfServers;
        averageWaitingTime = averageServiceTime = 0;
        currentTime = clientCount = 0;
        running = true;
        generateTasks();

        File logFile = new File("Log.txt");
        try {
            if (!logFile.createNewFile()) {
                try {
                    FileWriter myWriter = new FileWriter("Log.txt");
                    myWriter.write("");
                    myWriter.close();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Random Task Generator
     * Based on the minimum and maximum times allowed, generate a task
     * Add this task to a list of generated tasks because the dispatching process hasn't started yet
     * Sort the list by the increasing value of their arrival time
     */
    private void generateTasks() {
        generatedTasks = new ArrayList<>();
        int arrivalTime, serviceTime;
        for (int i = 0; i < numberOfTasks; i++) {
            do {
                Random random = new Random();
                arrivalTime = random.nextInt(maxArrivalTime);
            } while(arrivalTime < minArrivalTime);
            do {
                Random random = new Random();
                serviceTime = random.nextInt(maxServiceTime);
            } while(serviceTime < minServiceTime);
            Task task = new Task(arrivalTime, serviceTime);

            generatedTasks.add(task);
        }
        generatedTasks.sort((t1, t2) -> t1.getArrivalTime() - t2.getArrivalTime());
    }

    /**
     * The method called when the Thread is started
     */
    @Override
    public void run() {
        while (running) {
            // as long as there are tasks left for distributing and their arrival time is NOW, then dispatch them
            while (!generatedTasks.isEmpty() && currentTime == generatedTasks.get(0).getArrivalTime()) {
                Task task = generatedTasks.remove(0);
                int serverID = scheduler.dispatchTask(task);

                // update the live GUI
                GUIController.addToGUIQueue(serverID, task.getID());
            }

            printTimeLogs(currentTime);
            // update the minimum required output
            GUIController.updateOutput(this);

            // decrease the waiting time of the servers
            int numberOfActiveTasks = 0;
            for (Server server: scheduler.getServers()) {
                numberOfActiveTasks += server.getTasks().size();
                server.decreaseWaitingTime();
            }

            //update the peak hour
            if (numberOfActiveTasks > maxNumberOfActiveTasks) {
                maxNumberOfActiveTasks = numberOfActiveTasks;
                peakHour = currentTime;
            }
            
            // let a second pass
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // terminate the run if there are no more tasks to be added and the queues are empty ore if the time limit is reached
            if ((numberOfActiveTasks == 0 && generatedTasks.isEmpty()) || currentTime == timeLimit)
                terminate();
            currentTime++;
        }
    }

    public void printTimeLogs(int time) {
        try {
            FileWriter wr = new FileWriter("Log.txt",true);
            wr.write("Time " + time + "\n");
            wr.write("Waiting clients: ");
            for(Task task : generatedTasks) {
                wr.write("(" + task.getID() + ", " + task.getArrivalTime() + ", " + task.getServiceTime() + "); ");
            }
            wr.write("\n");

            for(Server server : scheduler.getServers()) {
                wr.write("Queue " + server.getID() + ": ");
                if(!server.getTasks().isEmpty()) {
                    for(Task task : server.getTasks()) {
                        if(task.getServiceTime() != 0)
                            wr.write("(" + task.getID() + ", " + task.getArrivalTime() + ", " + task.getServiceTime() + "); ");
                    }
                } else {
                    wr.write("closed\n");
                    continue;
                }
                wr.write("\n");
            }
            wr.write("\n");
            wr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * When the simulation is over, stop the servers from taking out the remaining tasks and add the minimum required 
     * data to the log
     */
    public void terminate() {
        for (Server server: scheduler.getServers()) {
            server.terminate();
        }
        running = false;
        try {
            FileWriter wr = new FileWriter("Log.txt",true);
            wr.write("Average Waiting Time: " + getAverageWaitingTime() + "\n");
            wr.write("Average Service Time: " + getAverageServiceTime() + "\n");
            wr.write("Peak Hour: " + peakHour);
            wr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        GUIController.policy = SelectionPolicy.SHORTEST_TIME;
    }

    /**
     * Compute the average waiting time based on the number of tasks that have entered a server
     * @return The average waiting time of a task from its arrival and until the moment it leaves
     */
    public double getAverageWaitingTime() {
        if (numberOfTasks - generatedTasks.size() == 0)
            return 0;
        return averageWaitingTime / (numberOfTasks - generatedTasks.size());
    }

    /**
     * Compute the average service time based on the number of tasks that have entered a server
     * @return The average service time of a task
     */
    public double getAverageServiceTime() {
        if (clientCount == 0)
            return 0;
        return averageServiceTime / clientCount;
    }

    public int getPeakHour() {
        return peakHour;
    }
}
