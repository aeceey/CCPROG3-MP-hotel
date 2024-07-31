import javax.swing.*;
import java.awt.*;

/**
 * The MainView class represents the main window of the Hotel Reservation System.
 * It provides the user interface components for creating, viewing, managing, and simulating bookings for hotels.
 */
public class MainView extends JFrame {
    /**
     * Text Field for entering the hotel name
     */
    private JTextField hotelNameField;
    /**
     * Button for creating a new hotel
     */
    private JButton createHotelButton;
    /**
     * List of the hotels
     */
    private JList<String> hotelList;
    /**
     * Button to view the hotel information
     */
    private JButton viewHotelButton;
    /**
     * Button to manage the hotel
     */
    private JButton manageHotelButton;
    /**
     * Button to simulate the booking for a selected hotel
     */
    private JButton simulateBookingButton;

    /**
     * This method initializes the compnents of the main window
     */
    public MainView() {
        setTitle("Hotel Reservation System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        mainWindow();
        layoutComponents();
    }

    /**
     * This method initializes the components of the main window
     */
    public void mainWindow() {
        hotelNameField = new JTextField(20);
        createHotelButton = new JButton("Create Hotel");
        hotelList = new JList<>();
        viewHotelButton = new JButton("View Hotel");
        manageHotelButton = new JButton("Manage Hotel");
        simulateBookingButton = new JButton("Simulate Booking");
    }

    /**
     * This method lays out the components in the main window
     */
    public void layoutComponents() {
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Hotel Name:"));
        topPanel.add(hotelNameField);
        topPanel.add(createHotelButton);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new JScrollPane(hotelList), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(viewHotelButton);
        bottomPanel.add(manageHotelButton);
        bottomPanel.add(simulateBookingButton);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

        /**
     * Returns the JTextField for entering the hotel name.
     *
     * @return the JTextField for the hotel name
     */
    public JTextField getHotelNameField() { return hotelNameField; }

    /**
     * Returns the JButton for creating a new hotel.
     *
     * @return the JButton for creating a hotel
     */
    public JButton getCreateHotelButton() { return createHotelButton; }

    /**
     * Returns the JList for displaying the list of hotels.
     *
     * @return the JList for hotel names
     */
    public JList<String> getHotelList() { return hotelList; }

    /**
     * Returns the JButton for viewing the selected hotel's information.
     *
     * @return the JButton for viewing a hotel
     */
    public JButton getViewHotelButton() { return viewHotelButton; }

    /**
     * Returns the JButton for managing the selected hotel.
     *
     * @return the JButton for managing a hotel
     */
    public JButton getManageHotelButton() { return manageHotelButton; }

    /**
     * Returns the JButton for simulating a booking for the selected hotel.
     *
     * @return the JButton for simulating a booking
     */
    public JButton getSimulateBookingButton() { return simulateBookingButton; }

    /**
     * Updates the list of hotels displayed in the JList.
     *
     * @param hotelNames - the array of hotel names to be displayed
     */
    public void updateHotelList(String[] hotelNames) {
        hotelList.setListData(hotelNames);
    }

   
}
