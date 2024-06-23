import java.time.LocalDate;
import java.util.ArrayList;

public class Hotel {
    private String name;
    private ArrayList<Room> rooms;
    private ArrayList<Reservation> reservations;
    private double basePrice;
    private int roomCount;

    public Hotel(String name, int roomCount) {
        if (roomCount < 1 || roomCount > 50) {
            throw new IllegalArgumentException("Room count must be between 1 and 50.");
        } 

        this.name = name;
        this.rooms = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.basePrice = 1299.0;
        this.roomCount = roomCount;

        for (int i = 1; i <= roomCount; i++) {
            rooms.add(new Room("Room" + i, basePrice));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        if (basePrice < 100.0) {
            System.out.println("Base price should be greater than or equal to 100.");
            return;
        }

        this.basePrice = basePrice;
        for (int i = 0; i < rooms.size(); i++) {
            rooms.get(i).setPrice(basePrice);
        }
        System.out.println("Base price updated");
    }

    public int getRoomCount() {
        return roomCount;
    }

    public String addRoom(String roomName) {
        if (rooms.size() >= 50) {
            return "Room count cannot be more than 50.";
        }
    
        for (Room room : rooms) {
            if (room.getName().equals(roomName)) {
                return "Room name already exists.";
            }
        }
    
        rooms.add(new Room(roomName, basePrice));
        return "Room added.";
    }
    

    public String removeRoom(String roomName) {
        if (rooms.size() <= 1) {
            return "Room count cannot be less than 1.";
        }

        Room roomToRemove = null;
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getName().equals(roomName)) {
                roomToRemove = rooms.get(i);
                break;
            }
        }

        if (roomToRemove == null) {
            return "Room does not exist.";
        }

        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getRoom().equals(roomToRemove)) {
                return "Room cannot be removed as it has active reservations.";
            }
        }

        rooms.remove(roomToRemove);
        roomCount--;
        return "Room deleted.";
    }

    public String removeReservation(String guestName, LocalDate checkInDate) {
        Reservation toRemove = null;
        for (Reservation res : reservations) {
            if (res.getGuestName().equals(guestName) && res.getCheckInDate().equals(checkInDate)) {
                toRemove = res;
                break;
            }
        }
        if (toRemove != null) {
            reservations.remove(toRemove);
            return "Reservation removed.";
        } else {
            return "Reservation not found.";
        }
    }

    public String viewRoom(String roomName) {
        for (Room room : rooms) {
            if (room.getName().equals(roomName)) {
                return room.toString();
            }
        }
        return "Room not found.";
    }

    public String viewReservation(String guestName, LocalDate checkInDate) {
        for (Reservation res : reservations) {
            if (res.getGuestName().equals(guestName) && res.getCheckInDate().equals(checkInDate)) {
                return res.toString();
            }
        }
        return "Reservation not found.";
    }
}

