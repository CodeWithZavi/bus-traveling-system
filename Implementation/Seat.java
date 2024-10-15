public class Seat {
    private int seatNumber;
    private boolean available;

    public Seat(int seatNumber) {
        this.seatNumber = seatNumber;
        this.available = true; // Initially, the seat is available
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available; // Set the availability status
    }

    // Additional method to represent seat information
    @Override
    public String toString() {
        return "Seat " + seatNumber + (available ? " is available." : " is booked.");
    }
}
