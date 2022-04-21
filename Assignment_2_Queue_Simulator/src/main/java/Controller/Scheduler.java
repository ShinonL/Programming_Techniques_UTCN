package Controller;

import Model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class dispatches tha tasks to servers based on a given strategy
 */
public class Scheduler {
    private List<Server> servers;
    private Strategy strategy;

    /**
     * Constructor
     * Start a list of server Threads
     * @param numberOfServers the number of servers to be started
     */
    public Scheduler(int numberOfServers) {
        servers = new ArrayList<>();
        for (int i = 0; i < numberOfServers; i++) {
            Server server = new Server();
            servers.add(server);
            Thread serverThread = new Thread(server);
            serverThread.start();
        }
    }

    /**
     * Change the strategy of adding a task
     * @param policy one of the 2 possible selection strategies
     */
    public void changeStrategy(SelectionPolicy policy) {
        if (policy == SelectionPolicy.SHORTEST_QUEUE) {
            strategy = new StrategyQueue();
        } else strategy = new StrategyTime();
    }

    /**
     * Call the selected strategy to add a task
     * @param task The task to be added
     * @return the ID of the server where the task wa added
     */
    public int dispatchTask(Task task) {
        return strategy.addTask(servers, task);
    }

    public List<Server> getServers() {
        return servers;
    }
}
