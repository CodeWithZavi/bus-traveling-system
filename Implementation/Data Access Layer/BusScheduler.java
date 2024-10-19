import java.util.ArrayList;

public class BusScheduler {
    private ArrayList<Bus> buses;

    // Constructor to initialize the bus list
    public BusScheduler(ArrayList<Bus> buses) {
        this.buses = buses;
    }

    // High Cohesion: Method to find the next available bus
    public Bus findNextAvailableBus(int currentBusIndex, int seatNumber) {
        for (int i = currentBusIndex + 1; i < buses.size(); i++) {
            Bus bus = buses.get(i);
            if (bus.isSeatAvailable(seatNumber)) {
                return bus; // Return the next bus with available seat
            }
        }
        return null; // No available buses found
    }

    // Additional methods for bus scheduling can be added here
}
