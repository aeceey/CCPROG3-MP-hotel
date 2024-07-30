import javax.swing.*;
import java.time.LocalDate;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HRSController {
    private HRS model;
    private MainView mainView;

    public HRSController(HRS model, MainView view) {
        this.model = model;
        this.mainView = view;
        
        initListeners();
    }

    private void initListeners() {
        mainView.getCreateHotelButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createHotel();
            }
        });
    
        mainView.getViewHotelButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewHotel();
            }
        });
    
        mainView.getManageHotelButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                manageHotel();
            }
        });
    
        mainView.getSimulateBookingButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                simulateBooking();
            }
        });
    }

    private void createHotel() {
        String name = mainView.getHotelNameField().getText();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(mainView, "Please enter a hotel name.");
            return;
        }

        String standardRoomsStr = JOptionPane.showInputDialog(mainView, "Enter the number of Standard rooms:");
        String deluxeRoomsStr = JOptionPane.showInputDialog(mainView, "Enter the number of Deluxe rooms:");
        String executiveRoomsStr = JOptionPane.showInputDialog(mainView, "Enter the number of Executive rooms:");

        try {
            int standardRooms = Integer.parseInt(standardRoomsStr);
            int deluxeRooms = Integer.parseInt(deluxeRoomsStr);
            int executiveRooms = Integer.parseInt(executiveRoomsStr);

            model.createHotel(name, standardRooms, deluxeRooms, executiveRooms);
            updateHotelList();
            // JOptionPane.showMessageDialog(mainView, "Hotel created successfully.");
           // JOptionPane.showMessageDialog(mainView, "Hotel created successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(mainView, "Invalid room numbers. Please enter integers.");
        }
    }

    private void viewHotel() {
        String selectedHotel = mainView.getHotelList().getSelectedValue();
        if (selectedHotel == null) {
            JOptionPane.showMessageDialog(mainView, "Please select a hotel to view.");
            return;
        }

        Hotel hotel = model.findHotelByName(selectedHotel);
        if (hotel != null) {
            new HotelInfoView(hotel).setVisible(true);
        }
    }

    private void manageHotel() {
        String selectedHotel = mainView.getHotelList().getSelectedValue();
        if (selectedHotel == null) {
            JOptionPane.showMessageDialog(mainView, "Please select a hotel to manage.");
            return;
        }

        Hotel hotel = model.findHotelByName(selectedHotel);
        if (hotel != null) {
            new ManageHotelView(hotel, this).setVisible(true);
        }
    }

    private void simulateBooking() {
        String selectedHotel = mainView.getHotelList().getSelectedValue();
        if (selectedHotel == null) {
            JOptionPane.showMessageDialog(mainView, "Please select a hotel for booking simulation.");
            return;
        }

        Hotel hotel = model.findHotelByName(selectedHotel);
        if (hotel != null) {
            new BookingSimulationView(hotel, this).setVisible(true);
        }
    }

    public void updateHotelList() {
        mainView.updateHotelList(model.getHotelNames());
    }

    public void changeHotelName(Hotel hotel, String newName) {
        try {
            hotel.setName(newName);
            updateHotelList();
            JOptionPane.showMessageDialog(mainView, "Hotel name updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(mainView, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addRoom(Hotel hotel, Room.RoomType type) {
        hotel.addRoom(type);
    }

    public void removeRoom(Hotel hotel, String roomName) {
        hotel.removeRoom(roomName);
    }

    public void updateBasePrice(Hotel hotel, double newBasePrice) {
        if (!hotel.getReservations().isEmpty()) {
            throw new IllegalStateException("Cannot update base price as there are existing bookings in the hotel.");
        }
        hotel.setBasePrice(newBasePrice);
    }
    

    public void removeReservation(Hotel hotel, String guestName, LocalDate checkInDate) {
        hotel.removeReservation(guestName, checkInDate);
    }

    public Reservation simulateBooking(Hotel hotel, String guestName, LocalDate checkInDate, LocalDate checkOutDate, Room.RoomType roomType, String discountCode) {
        return hotel.simulateBooking(guestName, checkInDate, checkOutDate, roomType, discountCode);
    }
}
