import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import application.BusScheduler;
import domain.Bus;
import domain.Seat;
import infrastructure.Booking;

public class BusBookingApp extends JFrame {
    // Declaring components
    private ArrayList<Bus> buses;
    private JComboBox<String> busComboBox;
    private JTextArea seatArea;
    private JTextField seatNumberField;
    private JLabel priceLabel, departureLabel, arrivalLabel;
    private JButton bookButton;
    private JTextArea receiptArea;
    private BusScheduler busScheduler;

    // Constructor: Initializes buses and GUI components
    public BusBookingApp() {
        // Initialize buses and scheduler
        buses = new ArrayList<>();
        buses.add(new Bus("Daewoo", "Islamabad - Lahore", 1500.0, 10, "10:00 AM", "2:00 PM"));
        buses.add(new Bus("Daewoo", "Islamabad - Karachi", 2500.0, 15, "11:00 AM", "9:00 PM"));
        buses.add(new Bus("Daewoo", "Rawalpindi - Multan", 1800.0, 20, "12:00 PM", "6:00 PM"));
        buses.add(new Bus("Daewoo", "Peshawar - Lahore", 1300.0, 12, "9:00 AM", "1:00 PM"));

        busScheduler = new BusScheduler(buses); // Low coupling by using BusScheduler

        // Initialize GUI components before adding them
        setTitle("Bus Seat Booking System");
        setSize(600, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // Bus selection combo box
        JLabel busLabel = new JLabel("Select Bus:");
        busLabel.setBounds(20, 20, 100, 25);
        add(busLabel); // Ensure the component is added after initialization

        busComboBox = new JComboBox<>();
        for (Bus bus : buses) {
            busComboBox.addItem(bus.getBusName() + " (" + bus.getRoute() + ")");
        }
        busComboBox.setBounds(150, 20, 300, 25);
        add(busComboBox); // Adding initialized JComboBox

        // Seat availability area
        seatArea = new JTextArea();
        seatArea.setBounds(20, 60, 500, 150);
        add(seatArea);

        // Seat number input field
        JLabel seatNumberLabel = new JLabel("Enter Seat Number:");
        seatNumberLabel.setBounds(20, 230, 150, 25);
        add(seatNumberLabel);

        seatNumberField = new JTextField();
        seatNumberField.setBounds(170, 230, 50, 25);
        add(seatNumberField);

        // Price label
        JLabel priceText = new JLabel("Price:");
        priceText.setBounds(20, 270, 100, 25);
        add(priceText);

        priceLabel = new JLabel("");
        priceLabel.setBounds(150, 270, 100, 25);
        add(priceLabel);

        // Departure label
        JLabel departureText = new JLabel("Departure:");
        departureText.setBounds(20, 310, 100, 25);
        add(departureText);

        departureLabel = new JLabel("");
        departureLabel.setBounds(150, 310, 200, 25);
        add(departureLabel);

        // Arrival label
        JLabel arrivalText = new JLabel("Arrival:");
        arrivalText.setBounds(20, 350, 100, 25);
        add(arrivalText);

        arrivalLabel = new JLabel("");
        arrivalLabel.setBounds(150, 350, 200, 25);
        add(arrivalLabel);

        // Book seat button
        bookButton = new JButton("Book Seat");
        bookButton.setBounds(20, 390, 150, 30);
        add(bookButton);

        // Receipt area
        receiptArea = new JTextArea();
        receiptArea.setBounds(20, 440, 500, 200);
        add(receiptArea);

        // Action listener for bus selection
        busComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayAvailableSeats();
            }
        });

        // Action listener for booking the seat
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookSeat();
            }
        });

        // Initial seat display
        displayAvailableSeats();
    }

    // Display available seats for the selected bus
    private void displayAvailableSeats() {
        Bus selectedBus = buses.get(busComboBox.getSelectedIndex());
        StringBuilder seatsInfo = new StringBuilder();
        for (Seat seat : selectedBus.getSeats()) {
            seatsInfo.append("Seat ").append(seat.getSeatNumber()).append(": ")
                    .append(seat.isAvailable() ? "Available" : "Booked").append("\n");
        }
        seatArea.setText(seatsInfo.toString());
        priceLabel.setText("$" + selectedBus.getPrice());
        departureLabel.setText(selectedBus.getDepartureTime());
        arrivalLabel.setText(selectedBus.getArrivalTime());
    }

    // Handle the seat booking process
    private void bookSeat() {
        try {
            int seatNumber = Integer.parseInt(seatNumberField.getText());
            int selectedBusIndex = busComboBox.getSelectedIndex();
            Bus selectedBus = buses.get(selectedBusIndex);

            if (selectedBus.isSeatAvailable(seatNumber)) {
                selectedBus.bookSeat(seatNumber);
                Booking booking = new Booking(selectedBus, seatNumber);
                receiptArea.setText(booking.getReceipt());
                displayAvailableSeats(); // Update seat availability display
            } else {
                // Find the next bus with available seats
                Bus nextAvailableBus = busScheduler.findNextAvailableBus(selectedBusIndex, seatNumber);
                if (nextAvailableBus != null) {
                    int choice = JOptionPane.showConfirmDialog(this,
                            "Seat not available on this bus. Would you like to book on the next available bus: "
                                    + nextAvailableBus.getBusName() + "?",
                            "Next Available Bus",
                            JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        nextAvailableBus.bookSeat(seatNumber);
                        Booking booking = new Booking(nextAvailableBus, seatNumber);
                        receiptArea.setText(booking.getReceipt());
                        displayAvailableSeats(); // Update seat availability display
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No seats available on other buses.", "No Availability",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid seat number.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Main method to run the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BusBookingApp app = new BusBookingApp();
            app.setVisible(true);
        });
    }
}