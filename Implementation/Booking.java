public class Booking {
    private Bus bus;
    private int seatNumber;
    private double price;

    // Creator Principle: Booking is instantiated with bus and seat details
    public Booking(Bus bus, int seatNumber) {
        this.bus = bus;
        this.seatNumber = seatNumber;
        this.price = bus.getPrice(); // Get the price from the bus information
    }

    public String getReceipt() {
        return "Booking Receipt\n" +
                "Bus: " + bus.getBusName() + "\n" +
                "Route: " + bus.getRoute() + "\n" +
                "Seat: " + seatNumber + "\n" +
                "Price: $" + price;
    }

    // Additional methods related to booking can be added here
}
