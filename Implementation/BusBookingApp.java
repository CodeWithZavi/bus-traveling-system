import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BusBookingApp extends JFrame {
    // GRASP: BusBookingApp is the Controller
    // It handles user interaction, coordinates between buses, seats, and bookings.
    private ArrayList<Bus> buses = new ArrayList<>();
    private JComboBox<String> busComboBox;
    private JTextArea seatArea;
    private JTextField seatNumberField;
    private JLabel priceLabel, departureLabel, arrivalLabel;
    private JButton bookButton;
    private JTextArea receiptArea;
    private BusScheduler busScheduler;

    // Constructor: Initializes buses and GUI components
    public BusBookingApp() {
        buses.add(new Bus("Daewoo", "Islamabad - Lahore", 1500.0, 10, "10:00 AM", "2:00 PM"));
        buses.add(new Bus("Daewoo", "Islamabad - Karachi", 2500.0, 15, "11:00 AM", "9:00 PM"));
        buses.add(new Bus("Daewoo", "Rawalpindi - Multan", 1800.0, 20, "12:00 PM", "6:00 PM"));
        buses.add(new Bus("Daewoo", "Peshawar - Lahore", 1300.0, 12, "9:00 AM", "1:00 PM"));

        // Initialize the bus scheduler with the list of buses
        busScheduler = new BusScheduler(buses);

        setTitle("Bus Seat Booking System");
        setSize(600, 700); // Increased size of the GUI
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel busLabel = new JLabel("Select Bus:");
        busLabel.setBounds(20, 20, 100, 25);
        add(busLabel);

        busComboBox = new JComboBox<>();
        for (Bus bus : buses) {
            busComboBox.addItem(bus.getBusName() + " (" + bus.getRoute() + ")");
        }
        busComboBox.setBounds(150, 20, 300, 25);
        add(busComboBox);

        seatArea = new JTextArea();
        seatArea.setBounds(20, 60, 500, 150); // Increased text area size
        add(seatArea);

        JLabel seatNumberLabel = new JLabel("Enter Seat Number:");
        seatNumberLabel.setBounds(20, 230, 150, 25);
        add(seatNumberLabel);

        seatNumberField = new JTextField();
        seatNumberField.setBounds(170, 230, 50, 25);
        add(seatNumberField);

        JLabel priceText = new JLabel("Price:");
        priceText.setBounds(20, 270, 100, 25);
        add(priceText);

        priceLabel = new JLabel("");
        priceLabel.setBounds(150, 270, 100, 25);
        add(priceLabel);

        JLabel departureText = new JLabel("Departure:");
        departureText.setBounds(20, 310, 100, 25);
        add(departureText);

        departureLabel = new JLabel("");
        departureLabel.setBounds(150, 310, 200, 25);
        add(departureLabel);

        JLabel arrivalText = new JLabel("Arrival:");
        arrivalText.setBounds(20, 350, 100, 25);
        add(arrivalLabel);

        arrivalLabel = new JLabel("");
        arrivalLabel.setBounds(150, 350, 200, 25);
        add(arrivalLabel);

        bookButton = new JButton("Book Seat");
        bookButton.setBounds(20, 390, 150, 30);
        add(bookButton);

        receiptArea = new JTextArea();
        receiptArea.setBounds(20, 440, 500, 200); // Increased receipt area size
        add(receiptArea);

        // Event handlers
        busComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayAvailableSeats();
            }
        });

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookSeat();
            }
        });

        displayAvailableSeats();
    }

    // GRASP: Controller - BusBookingApp manages the system event of displaying
    // available seats
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

    // GRASP: Controller - BusBookingApp handles the booking process
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
                // If seat is not available, find the next bus with available seat
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BusBookingApp app = new BusBookingApp();
            app.setVisible(true);
        });
    }
}
