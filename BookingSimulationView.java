import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * The class BookingSimulationView represents the user interface that simulates
 * booking a room in a hotel. To simulate the booking, the user will have to input booking details 
 * such as guest name, check-in and check-out dates, room type, and a discount code, if there is any.
 * and discount code.
 */

public class BookingSimulationView extends JFrame {
    /**
     * The hotel for which the booking is to be simulated.
     */
    private Hotel hotel;

    /**
     * The controller that handles the booking simulation logic.
     */
    private HRSController controller;

    /**
     * The text field for entering the guest's name.
     */
    private JTextField guestNameField;

    /**
     * The text field for entering the check-in date in yyyy-MM-dd format.
     */
    private JTextField checkInField;

    /**
     * The text field for entering the check-out date in yyyy-MM-dd format.
     */
    private JTextField checkOutField;

    /**
     * This will display the drop-down that will let the user choose the room type.
     */
    private JComboBox<Room.RoomType> roomTypeCombo;

    /**
     * The text field for entering a discount code.
     */
    private JTextField discountCodeField;


    /**
     * The constructor that will create a new BookingSimulationView given
     *  the hotel and the controller.
     * 
     * @param hotel - the hotel that will be used to simulate the booking
     * @param controller - handles the logic of the booking simulation
     */
    public BookingSimulationView(Hotel hotel, HRSController controller) {
        this.hotel = hotel;
        this.controller = controller;

        setTitle("Booking Simulation: " + hotel.getName());
        setSize(400, 300);
        setLayout(new GridLayout(6, 2));

        initComponents();
    }

    /**
     * This method handles the compnents of the user interface that the user will interact
     * with and adds it into the window
     */
    public void initComponents() {
        add(new JLabel("Guest Name:"));
        guestNameField = new JTextField();
        add(guestNameField);

        add(new JLabel("Check-in Date (yyyy-MM-dd):"));
        checkInField = new JTextField();
        add(checkInField);

        add(new JLabel("Check-out Date (yyyy-MM-dd):"));
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


    /**
     * The simulateBooking method simulates a booking based on the information that the user entered.
     * It handles the parsing of the input dates, getting the selected room type, and applying the discount code if any.
     * It will also display the error or the success dialog after the process.
     */
    public void simulateBooking() {
        String guestName = guestNameField.getText();
        LocalDate checkInDate, checkOutDate;
        try {
            checkInDate = LocalDate.parse(checkInField.getText());
            checkOutDate = LocalDate.parse(checkOutField.getText());
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use yyyy-MM-dd.");
            return;
        }

        Room.RoomType roomType = (Room.RoomType) roomTypeCombo.getSelectedItem();
        String discountCode = discountCodeField.getText();

        Reservation reservation = controller.simulateBooking(hotel, guestName, checkInDate, checkOutDate, roomType, discountCode);

        if (reservation != null) {
            StringBuilder message = new StringBuilder();
            message.append("Booking successful!\n");
            message.append("Guest: ").append(reservation.getGuestName()).append("\n");
            message.append("Room: ").append(reservation.getRoom().getName()).append("\n");
            message.append("Check-in: ").append(reservation.getCheckInDate()).append("\n");
            message.append("Check-out: ").append(reservation.getCheckOutDate()).append("\n");
            message.append("Total Price: ").append(String.format("%.2f", reservation.getTotalPrice())).append("\n\n");

            JOptionPane.showMessageDialog(this, message.toString());
        } else {
            JOptionPane.showMessageDialog(this, "Booking failed. No available rooms of the selected type for the given dates.");
        }
    }
}