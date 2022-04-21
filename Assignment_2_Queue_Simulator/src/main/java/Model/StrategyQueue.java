package Model;

import java.util.List;


public class StrategyQueue implements Strategy {
    /**
     * Add a task to the server with fewer tasks
     * @param servers The list of available server
     * @param task The task to be added
     * @return the ID of the selected server
     */
    @Override
    public int addTask(List<Server> servers, Task task) {
        int minimumQueueSize = Integer.MAX_VALUE;
        Server bestServer = null;
        for (Server server: servers) {
            /*if (server.getRemovedTask() == null) {
                bestServer = server;
                break;
            }*/
            if (server.getTasks().size() < minimumQueueSize) {
                minimumQueueSize = server.getTasks().size();
                bestServer = server;
            }
        }

        if (bestServer != null)
            bestServer.addTask(task);

        return bestServer.getID();
    }
}
