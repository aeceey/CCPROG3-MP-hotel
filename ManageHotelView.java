import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * The ManageHotelView class provides the GUI for managing a hotel's details, rooms, and reservations.
 */
public class ManageHotelView extends JFrame {
        /**
     * The hotel being managed.
     */
    private Hotel hotel;

    /**
     * The controller to handle actions.
     */
    private HRSController controller;

    /**
     * Text field for the new hotel name.
     */
    private JTextField newNameField;

    /**
     * A drop down that allows for selecting room type to add.
     */
    private JComboBox<Room.RoomType> roomTypeCombo;

    /**
     * Text field for entering the name of the room to be removed.
     */
    private JTextField roomNameField;

    /**
     * Text field for entering the new base price of the hotel.
     */
    private JTextField basePriceField;

    /**
     * Text field for entering the guest name to remove a reservation.
     */
    private JTextField guestNameField;

    /**
     * Text field for entering the check-in date to remove a reservation.
     */
    private JTextField checkInDateField;


    /**
     * The constructor that creates a ManageHotelView instance.
     * 
     * @param hotel - the hotel to be managed
     * @param controller - the controller who handles the actions
     */
    public ManageHotelView(Hotel hotel, HRSController controller) {
        this.hotel = hotel;
        this.controller = controller;

        setTitle("Manage Hotel: " + hotel.getName());
        setSize(400, 400);
        setLayout(new GridLayout(9, 3));

        mainComponents();
    }

    /**
     * This method initialized the main components of the view.
     */
    public void mainComponents() {
        add(new JLabel("New Hotel Name:"));
        newNameField = new JTextField(hotel.getName());
        add(newNameField);

        add(new JLabel("Room Type to Add:"));
        roomTypeCombo = new JComboBox<>(Room.RoomType.values());
        add(roomTypeCombo);

        add(new JLabel("Room Name to Remove:"));
        roomNameField = new JTextField();
        add(roomNameField);

        JButton addRoomButton = new JButton("Add Room");
        addRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRoom();
            }
        });
        add(addRoomButton);

        JButton removeRoomButton = new JButton("Remove Room");
        removeRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeRoom();
            }
        });
        add(removeRoomButton);

        add(new JLabel("Guest Name to Remove:"));
        guestNameField = new JTextField();
        add(guestNameField);

        add(new JLabel("Check-In Date (YYYY-MM-DD):"));
        checkInDateField = new JTextField();
        add(checkInDateField);

        JButton removeReservationButton = new JButton("Remove Reservation");
        removeReservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeReservation();
            }
        });
        add(removeReservationButton);

        add(new JLabel("Update Base Price:"));
        basePriceField = new JTextField(String.valueOf(hotel.getBasePrice()));
        add(basePriceField);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateHotel();
            }
        });
        add(updateButton);

        JButton removeHotelButton = new JButton("Remove Hotel");
        removeHotelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            removeHotel();
            }
        });
        add(removeHotelButton);
    }

    /**
     * This method adds rooms to the hotel
     */
    public void addRoom() {
        if (hotel.getRoomCount() >= 50) {
            JOptionPane.showMessageDialog(this, 
                "Maximum room limit (50) reached. Cannot add more rooms.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Room.RoomType selectedType = (Room.RoomType) roomTypeCombo.getSelectedItem();
        try {
            controller.addRoom(hotel, selectedType);
            JOptionPane.showMessageDialog(this, "Room added successfully.");
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this, 
                e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * This method removes rooms from the hotel
     */
    public void removeRoom() {
        String roomToRemove = roomNameField.getText();
        if (!roomToRemove.isEmpty()) {
            try {
                controller.removeRoom(hotel, roomToRemove);
                JOptionPane.showMessageDialog(this, "Room removed successfully.");
            } catch (IllegalStateException e) {
                JOptionPane.showMessageDialog(this, "Cannot remove room as it has an existing reservation.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a room name to remove.");
        }
    }

    /**
     * This method removes a hotel
     */
    public void removeHotel() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to remove this hotel?", 
            "Confirm Removal", 
            JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            controller.removeHotel(hotel.getName());
            dispose(); 
        }
    }

    /**
     * This method removes a reservation
     */
    public void removeReservation() {
        String guestName = guestNameField.getText();
        String checkInDateStr = checkInDateField.getText();

        if (guestName.isEmpty() || checkInDateStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both guest name and check-in date.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            LocalDate checkInDate = LocalDate.parse(checkInDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
            
            try {
                String result = controller.removeReservation(hotel, guestName, checkInDate);
                JOptionPane.showMessageDialog(this, result, "Success", JOptionPane.INFORMATION_MESSAGE);
                
                guestNameField.setText("");
                checkInDateField.setText("");
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method updates the name and the base price of the hotel
     */
    public void updateHotel() {
        String newName = newNameField.getText();
        if (!newName.equals(hotel.getName())) {
            try {
                controller.changeHotelName(hotel, newName);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    
        try {
            double newBasePrice = Double.parseDouble(basePriceField.getText());
            if (newBasePrice != hotel.getBasePrice()) {
                try {
                    controller.updateBasePrice(hotel, newBasePrice);
                    JOptionPane.showMessageDialog(this, "Base price updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (IllegalStateException ex) {
                    JOptionPane.showMessageDialog(this, "Cannot update base price as there are existing bookings in the hotel.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid base price. Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        dispose(); 
    }
    
}


