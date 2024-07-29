import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class BookingSimulationView extends JFrame {
    private Hotel hotel;
    private HRSController controller;

    private JTextField guestNameField;
    private JTextField checkInField;
    private JTextField checkOutField;
    private JComboBox<Room.RoomType> roomTypeCombo;
    private JTextField discountCodeField;

    public BookingSimulationView(Hotel hotel, HRSController controller) {
        this.hotel = hotel;
        this.controller = controller;

        setTitle("Booking Simulation: " + hotel.getName());
        setSize(400, 300);
        setLayout(new GridLayout(6, 2));

        initComponents();
    }

    private void initComponents() {
        add(new JLabel("Guest Name:"));
        guestNameField = new JTextField();
        add(guestNameField);

        add(new JLabel("Check-in Date (yyyy-mm-dd):"));
        checkInField = new JTextField();
        add(checkInField);

        add(new JLabel("Check-out Date (yyyy-mm-dd):"));
        checkOutField = new JTextField();
        add(checkOutField);

        add(new JLabel("Room Type:"));
        roomTypeCombo = new JComboBox<>(Room.RoomType.values());
        add(roomTypeCombo);

        add(new JLabel("Discount Code:"));
        discountCodeField = new JTextField();
        add(discountCodeField);

        JButton simulateButton = new JButton("Simulate Booking");
        simulateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulateBooking();
            }
        });
        add(simulateButton);
    }

    private void simulateBooking() {
        String guestName = guestNameField.getText();
        LocalDate checkInDate, checkOutDate;
        try {
            checkInDate = LocalDate.parse(checkInField.getText());
            checkOutDate = LocalDate.parse(checkOutField.getText());
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use yyyy-mm-dd.");
            return;
        }

        Room.RoomType roomType = (Room.RoomType) roomTypeCombo.getSelectedItem();
        String discountCode = discountCodeField.getText();

        Reservation reservation = controller.simulateBooking(hotel, guestName, checkInDate, checkOutDate, roomType, discountCode);

        if (reservation != null) {
            JOptionPane.showMessageDialog(this, 
                "Booking successful!\n" +
                "Guest: " + reservation.getGuestName() + "\n" +
                "Room: " + reservation.getRoom().getName() + "\n" +
                "Total Price: " + reservation.getTotalPrice());
        } else {
            JOptionPane.showMessageDialog(this, "Booking failed. No available rooms of the selected type for the given dates.");
        }
    }
}