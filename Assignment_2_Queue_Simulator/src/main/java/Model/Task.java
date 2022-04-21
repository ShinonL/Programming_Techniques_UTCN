package Model;

import java.util.concurrent.atomic.AtomicInteger;

public class Task {
    private final int ID;
    private final int arrivalTime;
    private int serviceTime;
    private int waitTime;
    private static AtomicInteger counter = new AtomicInteger(0);

    public Task(int beginningTime, int serviceTime) {
        this.ID = counter.incrementAndGet();
        this.arrivalTime = beginningTime;
        this.serviceTime = serviceTime;
    }

    public int getID() {
        return ID;
    }
    public int getArrivalTime() {
        return arrivalTime;
    }
    public int getServiceTime() {
        return serviceTime;
    }
    public int getWaitTime() {
        return waitTime;
    }

    /**
     * The time a task has to wait before it can leave a queue is the time it has to wait until it reaches the front
     * of the queue plus the time it takes to process the task
     * @param queueWaitingTime
     */
    public void setWaitTime(int queueWaitingTime) {
        this.waitTime = queueWaitingTime + serviceTime;
    }

    /**
     * Reset the counter
     */
    public static void reset(){
        counter.set(0);
    }
    public void decreaseServiceTime() {
        serviceTime--;
    }
}
