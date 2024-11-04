package application;

import domain.Bus;
import java.util.ArrayList;

public class BusScheduler {
    private ArrayList<Bus> buses;

    public BusScheduler(ArrayList<Bus> buses) {
        this.buses = buses;
    }

    public Bus findNextAvailableBus(int currentBusIndex, int seatNumber) {
        for (int i = currentBusIndex + 1; i < buses.size(); i++) {
            Bus bus = buses.get(i);
            if (bus.isSeatAvailable(seatNumber)) {
                return bus;
            }
        }
        return null;
    }
}
