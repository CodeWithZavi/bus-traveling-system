package domain;

public class Seat {
    private int seatNumber;
    private boolean available;

    public Seat(int seatNumber) {
        this.seatNumber = seatNumber;
        this.available = true;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Seat " + seatNumber + (available ? " is available." : " is booked.");
    }
}
