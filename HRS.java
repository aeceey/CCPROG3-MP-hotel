import java.util.ArrayList;
import javax.swing.JOptionPane;

public class HRS {
    private ArrayList<Hotel> hotels;

    public HRS() {
        this.hotels = new ArrayList<>();
    }

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
        JOptionPane.showMessageDialog(null, "Hotel created successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean isHotelNameTaken(String name) {
        for (Hotel hotel : hotels) {
            if (hotel.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

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

    public Hotel findHotelByName(String name) {
        for (Hotel hotel : hotels) {
            if (hotel.getName().equals(name)) {
                return hotel;
            }
        }
        JOptionPane.showMessageDialog(null, "Error: No hotel found with the given name.", "Hotel Not Found", JOptionPane.ERROR_MESSAGE);
        return null;
    }

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