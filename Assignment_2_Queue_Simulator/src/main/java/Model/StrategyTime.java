package Model;

import java.util.List;

public class StrategyTime implements Strategy {
    /**
     * Add a task to the server with the smallest waiting time
     * @param servers The list of available server
     * @param task The task to be added
     * @return the ID of the selected server
     */
    @Override
    public int addTask(List<Server> servers, Task task) {
        int minimumWaitingTime = Integer.MAX_VALUE;
        Server bestServer = null;
        for (Server server: servers) {
            /*if (server.getRemovedTask() == null) {
                bestServer = server;
                break;
            }*/
            if (server.getWaitingTime().intValue() < minimumWaitingTime) {
                minimumWaitingTime = server.getWaitingTime().intValue();
                bestServer = server;
            }
        }

        if (bestServer != null)
            bestServer.addTask(task);

        return bestServer.getID();
    }
}
