import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageHotelView extends JFrame {
    private Hotel hotel;
    private HRSController controller;

    private JTextField newNameField;
    private JComboBox<Room.RoomType> roomTypeCombo;
    private JTextField roomNameField;
    private JTextField basePriceField;

    public ManageHotelView(Hotel hotel, HRSController controller) {
        this.hotel = hotel;
        this.controller = controller;

        setTitle("Manage Hotel: " + hotel.getName());
        setSize(400, 300);
        setLayout(new GridLayout(6, 2));

        mainComponents();
    }

    private void mainComponents() {
        add(new JLabel("New Hotel Name:"));
        newNameField = new JTextField(hotel.getName());
        add(newNameField);

        add(new JLabel("Room Type:"));
        roomTypeCombo = new JComboBox<>(Room.RoomType.values());
        add(roomTypeCombo);

        add(new JLabel("Room Name "));
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

        JButton removeRoomButton = new JButton("Remove Room:");
        removeRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeRoom();
            }
        });
        add(removeRoomButton);

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
    }

    private void addRoom() {
        Room.RoomType selectedType = (Room.RoomType) roomTypeCombo.getSelectedItem();
        controller.addRoom(hotel, selectedType);
        JOptionPane.showMessageDialog(this, "Room added successfully.");
    }

    private void removeRoom() {
        String roomToRemove = roomNameField.getText();
        if (!roomToRemove.isEmpty()) {
            controller.removeRoom(hotel, roomToRemove);
            JOptionPane.showMessageDialog(this, "Room removed successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a room name to remove.");
        }
    }

    private void updateHotel() {
        boolean nameChanged = false;
        boolean basePriceChanged = false;
    
        String newName = newNameField.getText();
        if (!newName.equals(hotel.getName())) {
            try {
                controller.changeHotelName(hotel, newName);
                nameChanged = true;
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return; // Exit early if there is an error
            }
        }
    
        try {
            double newBasePrice = Double.parseDouble(basePriceField.getText());
            if (newBasePrice != hotel.getBasePrice()) {
                try {
                    controller.updateBasePrice(hotel, newBasePrice);
                    basePriceChanged = true;
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return; // Exit early if there is an error
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid base price. Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return; // Exit early if there is an error
        }
    
        dispose(); // Close the dialog
    }
    
}


