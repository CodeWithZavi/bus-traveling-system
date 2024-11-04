package infrastructure;

import domain.*;

public class Booking {
    private Bus bus;
    private int seatNumber;
    private double price;

    public Booking(Bus bus, int seatNumber) {
        this.bus = bus;
        this.seatNumber = seatNumber;
        this.price = bus.getPrice();
    }

    public String getReceipt() {
        return "Booking Receipt\n" +
                "Bus: " + bus.getBusName() + "\n" +
                "Route: " + bus.getRoute() + "\n" +
                "Seat: " + seatNumber + "\n" +
                "Price: $" + price;
    }
}
