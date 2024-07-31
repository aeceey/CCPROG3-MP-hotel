import javax.swing.*;
import java.time.LocalDate;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The HRSController class is the interface between the model and the view of the program. It gets the
 * action done by the user and updates the model and view.
 */
public class HRSController {
    private HRS model;
    private MainView mainView;


    /**
     * This is the constructor that creates an HRSController given an model and view.
     * 
     * @param model - the HRS model
     * @param view - the main view
     */
    public HRSController(HRS model, MainView view) {
        this.model = model;
        this.mainView = view;
        
        initListeners();
    }

    /**
     * This method initializes the listeners for the buttons in the MainView.
     */
    public void initListeners() {
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


    /**
     * This method creates a hotel based on the information entered by the user and also updates the list of hotels displayed.
     */
    public void createHotel() {
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
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(mainView, "Invalid room numbers. Please enter integers.");
        }
    }


    /**
     * This method displays the information of the selected hotel.
     */
    public void viewHotel() {
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

    /**
     * This method removes a hotel given the name.
     * 
     * @param name - the name of the hotel to be removed
     */
    public void removeHotel(String name) {
        model.removeHotel(name);
    }

    /**
     * This method opens the management view of the selected hotel.
     */
    public void manageHotel() {
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

    /**
     * This method opens the booking simulation view for the hotel specified byt the user.
     */
    public void simulateBooking() {
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

    /**
     * This method handles the updates of the list of the hotel in the main view using the names of the hotel in the model.
     */
    public void updateHotelList() {
        mainView.updateHotelList(model.getHotelNames());
    }

    /**
     * This method changes the Hotel Name nd updates the hotel list in the view.
     * @param hotel - the hotel to be renamed
     * @param newName - the new name for the hotel
     */
    public void changeHotelName(Hotel hotel, String newName) {
        try {
            hotel.setName(newName);
            updateHotelList();
            JOptionPane.showMessageDialog(mainView, "Hotel name updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(mainView, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method adds a room given the hotel and the room type.
     * 
     * @param hotel -  the hotel to which the room will be added
     * @param type - the type of room to be added
     */
    public void addRoom(Hotel hotel, Room.RoomType type) {
        hotel.addRoom(type);
    }


    /**
     * This method removes a room from a hotel given its name.
     * 
     * @param hotel - the hotel from which the room will be removed
     * @param roomName - the name of the room to be removed
     */
    public void removeRoom(Hotel hotel, String roomName) {
        hotel.removeRoom(roomName);
    }

    /**
     * This method updates the base price of the hotel only if there are no exisitng bookings.
     * 
     * @param hotel - the hotel whose base price will be updated
     * @param newBasePrice - the new base price of the hotel
     */
    public void updateBasePrice(Hotel hotel, double newBasePrice) {
        if (!hotel.getReservations().isEmpty()) {
            throw new IllegalStateException("Cannot update base price as there are existing bookings in the hotel.");
        }
        hotel.setBasePrice(newBasePrice);
    }
    
    /**
     * This method simulates the booking given a hotel
     * 
     * @param hotel - the hotel where the booking will be simulated
     * @param guestName - the name of the guest 
     * @param checkInDate - the check-in date for the booking
     * @param checkOutDate - the check-out date for the booking
     * @param roomType -  the type of room for the booking
     * @param discountCode - the discount code to be applied, if any
     * @return the simulated reservation
     */
    public Reservation simulateBooking(Hotel hotel, String guestName, LocalDate checkInDate, LocalDate checkOutDate, Room.RoomType roomType, String discountCode) {
        return hotel.simulateBooking(guestName, checkInDate, checkOutDate, roomType, discountCode);
    }

    /**
     * This method removes a reservation for a given hotel, guest name, and check-in date.
     * 
     * @param hotel - the hotel where the reservation will be removed
     * @param guestName - the name of the guest
     * @param checkInDate - the check-in date of the reservation
     * @return the message indicating the result of the removal
     * 
     */
    public String removeReservation(Hotel hotel, String guestName, LocalDate checkInDate) {
        try {
            return hotel.removeReservation(guestName, checkInDate);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("No reservation found for the given guest and check-in date.");
        }
    }
}
