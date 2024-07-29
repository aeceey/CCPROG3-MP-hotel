import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private JTextField hotelNameField;
    private JButton createHotelButton;
    private JList<String> hotelList;
    private JButton viewHotelButton;
    private JButton manageHotelButton;
    private JButton simulateBookingButton;

    public MainView() {
        setTitle("Hotel Reservation System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        mainWindow();
        layoutComponents();
    }

    private void mainWindow() {
        hotelNameField = new JTextField(20);
        createHotelButton = new JButton("Create Hotel");
        hotelList = new JList<>();
        viewHotelButton = new JButton("View Hotel");
        manageHotelButton = new JButton("Manage Hotel");
        simulateBookingButton = new JButton("Simulate Booking");
    }

    private void layoutComponents() {
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

    public JTextField getHotelNameField() { return hotelNameField; }
    public JButton getCreateHotelButton() { return createHotelButton; }
    public JList<String> getHotelList() { return hotelList; }
    public JButton getViewHotelButton() { return viewHotelButton; }
    public JButton getManageHotelButton() { return manageHotelButton; }
    public JButton getSimulateBookingButton() { return simulateBookingButton; }

    public void updateHotelList(String[] hotelNames) {
        hotelList.setListData(hotelNames);
    }
}
