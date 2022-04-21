package Model;

import Controller.GUIController;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The "Consumer" class
 * This is the queue class. I holds the tasks waiting to be processed by this specific server
 * Each time a task is processed, it is removed from the list
 */
public class Server implements Runnable {
    private int ID;
    private volatile BlockingQueue<Task> tasks;
    private AtomicInteger waitingTime;
    private boolean running = true;
    private static AtomicInteger counter = new AtomicInteger(0);

    /**
     * Constructor
     * Sets a unique ID to the server and creates an empty queue with waiting time equal to 0
     */
    public Server() {
        tasks = new LinkedBlockingQueue<>();
        ID = counter.incrementAndGet();
        waitingTime = new AtomicInteger(0);
    }

    /**
     * Method used by the scheduler to add a task
     * When a task is added, we must update the average waiting time of the simulation and the waiting time of the server
     * @param task The task to be added
     */
    public void addTask(Task task) {
        tasks.add(task);
        task.setWaitTime(waitingTime.intValue());
        SimulationManager.averageWaitingTime += task.getWaitTime();
        waitingTime.addAndGet(task.getServiceTime());
    }

    /**
     * The method called when a Server thread is started
     * Takes out the first element of the queue then waits for the task to be processed
     * After that, if the simulation is still running, repeat the procedure
     */
    @Override
    public void run() {
        while (running) {
            try {
                Task task = tasks.peek();
                if(task != null) {
                    SimulationManager.averageServiceTime += task.getServiceTime();
                    SimulationManager.clientCount++;
                    Thread.sleep(task.getServiceTime() * 1000);
                    if (SimulationManager.running) {
                        GUIController.removeFromGUIQueue(this.ID, task.getID());
                        tasks.take();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Decrease the waiting time of the server
     * Called by the Simulation Manager for each second that passes
     */
    public void decreaseWaitingTime() {
        Task task = tasks.peek();
        if(task != null) task.decreaseServiceTime();
        if (waitingTime.intValue() == 0)
            return;
        waitingTime.addAndGet(-1);
    }

    /**
     * Stop the thread
     */
    public void terminate() {
        running = false;
    }
    /**
     * Reset the counter
     */
    public static void reset() {
        counter.set(0);
    }
    public BlockingQueue<Task> getTasks() {
        return tasks;
    }
    public AtomicInteger getWaitingTime() {
        return waitingTime;
    }
    public int getID() {
        return ID;
    }
}
