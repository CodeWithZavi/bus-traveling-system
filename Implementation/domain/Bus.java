package domain;

import java.util.ArrayList;

public class Bus {
    private String busName;
    private String route;
    private ArrayList<Seat> seats;
    private double price;
    private String departureTime;
    private String arrivalTime;

    public Bus(String busName, String route, double price, int totalSeats, String departureTime, String arrivalTime) {
        this.busName = busName;
        this.route = route;
        this.price = price;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.seats = new ArrayList<>();
        for (int i = 1; i <= totalSeats; i++) {
            seats.add(new Seat(i));
        }
    }

    public String getBusName() {
        return busName;
    }

    public String getRoute() {
        return route;
    }

    public double getPrice() {
        return price;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public ArrayList<Seat> getSeats() {
        return seats;
    }

    public Seat getSeat(int seatNumber) {
        for (Seat seat : seats) {
            if (seat.getSeatNumber() == seatNumber) {
                return seat;
            }
        }
        return null;
    }

    public boolean isSeatAvailable(int seatNumber) {
        Seat seat = getSeat(seatNumber);
        return seat != null && seat.isAvailable();
    }

    public void bookSeat(int seatNumber) {
        Seat seat = getSeat(seatNumber);
        if (seat != null && seat.isAvailable()) {
            seat.setAvailable(false);
        }
    }
}
