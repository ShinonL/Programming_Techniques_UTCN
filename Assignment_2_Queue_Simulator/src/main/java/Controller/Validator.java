package Controller;

public class Validator {
    /**
     * Check the data introduced by the user
     * @throws IllegalArgumentException Throw an exception if invalid data was found
     */
    public static void validateInput(String timeLimit, String maxArrivalTime, String minArrivalTime, String maxServiceTime,
                                     String minServiceTime, String numberOfServers, String numberOfClients) throws IllegalArgumentException {
        if(!timeLimit.matches("^\\d+$"))
            throw new IllegalArgumentException("Wrong input!");
        if(!maxArrivalTime.matches("^\\d+$"))
            throw new IllegalArgumentException("Wrong input!");
        if(!minArrivalTime.matches("^\\d+$"))
            throw new IllegalArgumentException("Wrong input!");
        if(!maxServiceTime.matches("^\\d+$"))
            throw new IllegalArgumentException("Wrong input!");
        if(!minServiceTime.matches("^\\d+$"))
            throw new IllegalArgumentException("Wrong input!");
        if(!numberOfServers.matches("^\\d+$"))
            throw new IllegalArgumentException("Wrong input!");
        if(!numberOfClients.matches("^\\d+$"))
            throw new IllegalArgumentException("Wrong input!");

        int max = Integer.parseInt(maxArrivalTime);
        int min = Integer.parseInt(minArrivalTime);
        if (min > max) throw new IllegalArgumentException("Minimum time cannot be bigger than maximum time.");

        max = Integer.parseInt(maxServiceTime);
        min = Integer.parseInt(minServiceTime);
        if (min > max) throw new IllegalArgumentException("Minimum time cannot be bigger than maximum time.");
    }
}
