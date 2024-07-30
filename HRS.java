import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *  The HRS class, short for Hotel Reservation System, manages a list of Hotels.
 *  Creation, Management, Viewing, and Booking of Hotels happen in this class.
 *  
 */
public class HRS {
    /**
     * The list of hotels managed by the HRS.
     */
    private ArrayList<Hotel> hotels;


    /**
     *  This is a constructor for the HRS class which initializes the list of hotels.
     */
    public HRS() {
        this.hotels = new ArrayList<>(); // Initialize list of hotels
    }

    /**
     * Creates a new hotel with the specified parameters and adds it to the list of hotels.
     * 
     * @param name the name of the new hotel
     * @param standardRoomCount the number of standard rooms in the hotel
     * @param deluxeRoomCount the number of deluxe rooms in the hotel
     * @param executiveRoomCount the number of executive rooms in the hotel
     */
    public void createHotel(String name, int standardRoomCount, int deluxeRoomCount, int executiveRoomCount) {
        int totalRoomCount = standardRoomCount + deluxeRoomCount + executiveRoomCount;
        if (totalRoomCount < 1 || totalRoomCount > 50) {
            JOptionPane.showMessageDialog(null, "Error: Total room count must be between 1 and 50.", "Invalid Room Count", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (isHotelNameTaken(name)) {
            JOptionPane.showMessageDialog(null, "Error: A hotel with this name already exists.", "Duplicate Hotel Name", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Hotel newHotel = new Hotel(name, standardRoomCount, deluxeRoomCount, executiveRoomCount, this);
        hotels.add(newHotel);
       
    }

    /**
     * This method checks if the name of the hotel already exists as it has to be unique
     * 
     * @param name - the name of hotel to be checked
     * @return true if the name is indeed taken, false if not
     */
    public boolean isHotelNameTaken(String name) {
        for (Hotel hotel : hotels) {
            if (hotel.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a hotel with the specified name from the list of hotels.
     * 
     * @param name the name of the hotel to remove
     */
    public void removeHotel(String name) {
        boolean removed = false;
        for (int i = hotels.size() - 1; i >= 0; i--) {
            Hotel hotel = hotels.get(i);
            if (hotel.getName().equals(name)) {
                if (hotel.getReservations().isEmpty()) {
                    hotels.remove(i);
                    removed = true;
                    JOptionPane.showMessageDialog(null, "Hotel removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Error: Cannot remove hotel. It has existing reservations.", "Removal Failed", JOptionPane.ERROR_MESSAGE);
                }
                return;
            }
        }
        if (!removed) {
            JOptionPane.showMessageDialog(null, "Error: No hotel found with the given name.", "Hotel Not Found", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Finds and returns a hotel with the specified name from the list of hotels.
     * 
     * @param name the name of the hotel to find
     * @return the hotel with the given name, or null if no such hotel exists
     */
    public Hotel findHotelByName(String name) {
        for (Hotel hotel : hotels) {
            if (hotel.getName().equals(name)) {
                return hotel;
            }
        }
        JOptionPane.showMessageDialog(null, "Error: No hotel found with the given name.", "Hotel Not Found", JOptionPane.ERROR_MESSAGE);
        return null;
    }

    /**
     * Returns an array of names of all the hotels in the system.
     * 
     * @return an array of hotel names, or an empty array if no hotels exist
     */
    public String[] getHotelNames() {
        if (hotels.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Note: No hotels currently exist in the system.", "No Hotels", JOptionPane.INFORMATION_MESSAGE);
            return new String[0];
        }
        String[] names = new String[hotels.size()];
        for (int i = 0; i < hotels.size(); i++) {
            names[i] = hotels.get(i).getName();
        }
        return names;
    }
}