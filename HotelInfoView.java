import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.List;

/**
 * The HotelInfoView extends JFrame and displays information about the hotel.
 * The user will be able to choose between high-level and low-level information about the hotel, its
 * rooms, availability, and reservations. 
 * 
 */
public class HotelInfoView extends JFrame {
    /**
     * The hotel object associated with this view.
     */
    private Hotel hotel;
    /**
     * The text area that is used to display the hotel information to user.
     */
    private JTextArea infoArea;
    /**
     * The panel containing the buttons that the user will interact with for the hotel view
     */
    private JPanel buttonPanel;

    /**
     * This is the constructor that constructs a HotelInfoView, given a hotel.
     * 
     * @param hotel - the hotel whose information will be displayed
     */
    public HotelInfoView(Hotel hotel) {
        this.hotel = hotel;
        setTitle("Hotel Information: " + hotel.getName());
        setSize(500, 400);
        setLayout(new BorderLayout());

        infoArea = new JTextArea();
        infoArea.setEditable(false);
        add(new JScrollPane(infoArea), BorderLayout.CENTER);

        buttonPanel = new JPanel();
        JButton highLevelButton = new JButton("High-level Info");
        JButton lowLevelButton = new JButton("Low-level Info");

        highLevelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showHighLevelInfo();
            }
        });

        lowLevelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showLowLevelOptions();
            }
        });

        buttonPanel.add(highLevelButton);
        buttonPanel.add(lowLevelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * This method will handle the display of the high-level information of the hotel.
     * It will include the name, the number of rooms, the earnings, and the room details. 
     */
    public void showHighLevelInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Hotel Name: ").append(hotel.getName()).append("\n");
        info.append("Total Rooms: ").append(hotel.getRoomCount()).append("\n");
        info.append("Estimated Earnings for the Month: ").append(hotel.calculateEarnings()).append("\n\n");
        info.append("Room Information:\n");
        for (Room room : hotel.getRooms()) {
            info.append(room.getName()).append(" - ").append(room.getType()).append("\n");
        }
        infoArea.setText(info.toString());
    }
    
    /**
     * This method will handle the display of the low-level information of the hotel.
     * It will include the room availability, the room information, and the reservation information. 
     */
    public void showLowLevelOptions() {
        buttonPanel.removeAll();
        
        JButton availabilityButton = new JButton("Room Availability");
        JButton roomInfoButton = new JButton("Room Info");
        JButton reservationInfoButton = new JButton("Reservation Info");
        JButton backButton = new JButton("Back");

        availabilityButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String dateStr = JOptionPane.showInputDialog("Enter date (yyyy-MM-dd):");
                try {
                    LocalDate date = LocalDate.parse(dateStr);
                    showRoomAvailability(date);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Invalid date format");
                }
            }
        });

        roomInfoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String roomName = JOptionPane.showInputDialog("Enter room name:");
                showRoomInfo(roomName);
            }
        });

        reservationInfoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String guestName = JOptionPane.showInputDialog("Enter guest name:");
                String checkInDateStr = JOptionPane.showInputDialog("Enter check-in date (yyyy-MM-dd):");
                try {
                    LocalDate checkInDate = LocalDate.parse(checkInDateStr);
                    showReservationInfo(guestName, checkInDate);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Invalid date format");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonPanel.removeAll();
                buttonPanel.add(new JButton("High-level Info"));
                buttonPanel.add(new JButton("Low-level Info"));
                buttonPanel.revalidate();
                buttonPanel.repaint();
            }
        });

        buttonPanel.add(availabilityButton);
        buttonPanel.add(roomInfoButton);
        buttonPanel.add(reservationInfoButton);
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }

    /**
     * This method handles the display of the room availability, given a specified date.
     * 
     * @param date - the date to check the room availability
     */
    public void showRoomAvailability(LocalDate date) {
        StringBuilder info = new StringBuilder();
        info.append("Room Availability for ").append(date).append(":\n");
        for (Room room : hotel.getRooms()) {
            info.append(room.getName()).append(" - ");
            if (room.isAvailable(date)) {
                info.append("Available\n");
            } else {
                info.append("Booked\n");
            }
        }
        infoArea.setText(info.toString());
    }


    /**
     * This method displays the information of a room given the room name.
     * 
     * @param roomName - the name of the room whose infromation will be displayed.
     */
    public void showRoomInfo(String roomName) {
        Room room = null;
        for (Room r : hotel.getRooms()) {
            if (r.getName().equals(roomName)) {
                room = r;
                break;
            }
        }
        if (room == null) {
            infoArea.setText("No room exists with the given name.");
            return;
        }
    
        StringBuilder info = new StringBuilder();
        info.append("Room Name: ").append(room.getName()).append("\n");
        info.append("Price per Night: ").append(room.getPrice()).append("\n");
        info.append("Availability for the month:\n");
    
        LocalDate now = LocalDate.now();
        for (int i = 1; i <= now.lengthOfMonth(); i++) {
            LocalDate date = now.withDayOfMonth(i);
            boolean isAvailable = room.isAvailable(date);
            info.append(date).append(": ");
            if (isAvailable) {
                info.append("Available");
            } else {
                info.append("Booked");
            }
            info.append("\n");
        }
        infoArea.setText(info.toString());
    }

    /**
     * This method displays the information about the reservation made given a guest name and the check-in date.
     * 
     * @param guestName
     * @param checkInDate
     */
    public void showReservationInfo(String guestName, LocalDate checkInDate) {
        Reservation reservation = null;
        for (Reservation res : hotel.getReservations()) {
            if (res.getGuestName().equals(guestName) && res.getCheckInDate().equals(checkInDate)) {
                reservation = res;
                break;
            }
        }
        if (reservation == null) {
            infoArea.setText("No reservation found for the given guest and check-in date.");
            return;
        }
    
        StringBuilder info = new StringBuilder();
        info.append("Guest Name: ").append(reservation.getGuestName()).append("\n");
        info.append("Room Name: ").append(reservation.getRoom().getName()).append("\n");
        info.append("Check-In Date: ").append(reservation.getCheckInDate()).append("\n");
        info.append("Check-Out Date: ").append(reservation.getCheckOutDate()).append("\n");
    
        List<Double> breakdownCost = reservation.getBreakdownCost();
        double totalPrice = reservation.getTotalPrice();
        LocalDate date = reservation.getCheckInDate();
    
        info.append("Price Breakdown per Night:\n");
        for (Double dailyPrice : breakdownCost) {
            double multiplier = reservation.getRoom().getMultiplierForDate(date);
            info.append(date).append(": ").append(String.format("%.2f", dailyPrice))
                .append(" (Multiplier: ").append(String.format("%.2f", multiplier)).append(")\n");
            date = date.plusDays(1);
        }
    
        info.append("Total Price: ").append(String.format("%.2f", totalPrice)).append("\n");
        infoArea.setText(info.toString());
    }
    
    
}