import java.util.ArrayList;
import java.time.LocalDate;

public class Hotel {
    private String name;
    private int roomCount;
    private double basePrice;
    private ArrayList<Room> rooms;
    private ArrayList<Reservation> reservations;
    private int nextRoomNumber; // New field to track the next room number
    private boolean hasBookings;
    private HRS hrs; // Reference to HRS instance

    public Hotel(String name, int roomCount, HRS hrs) {
        this.name = name;
        this.roomCount = roomCount;
        this.basePrice = 1299.0; // Automatically assigning base price to 1299
        this.rooms = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.nextRoomNumber = 1; // Initialize to 1 for sequential room numbering
        this.hrs = hrs; // Set the HRS instance reference

        for (int i = 1; i <= roomCount; i++) {
            String roomName = name + String.format("%02d", nextRoomNumber++);
            this.rooms.add(new Room(roomName, basePrice));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (hrs.isHotelNameTaken(name)) {
            System.out.println("A hotel with this name already exists.");
        } else {
            this.name = name;
            System.out.println("Hotel name changed to: " + name);
        }
    }
    

    public int getRoomCount() {
        return roomCount;
    }
        public void setRoomCount(int roomCount) {
            if (roomCount > 50) {
                System.out.println("Cannot set room count more than 50" );
                return;
            }
            this.roomCount = roomCount;
        }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        if (!reservations.isEmpty()) {
            System.out.println("Cannot update base price as there are existing bookings in the hotel.");
            return;
        }
        if (basePrice < 100.0) {
            System.out.println("Base price must be greater than or equal to 100.0");
            return;
        }
        this.basePrice = basePrice;
        // Update the price of all rooms
        for (Room room : rooms) {
            room.setPrice(basePrice);
        }
        System.out.println("Base price updated successfully to " + basePrice);
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    /**
     * ADD ROOM METHOD
     * @return
     */
    public String addRoom() {
        if (rooms.size() >= 50) {
            return "Room count cannot be more than 50.";
        }
        String newRoomName = name + String.format("%02d", nextRoomNumber++);
        rooms.add(new Room(newRoomName, basePrice));
        roomCount++;
        return "Room added: " + newRoomName;
    }

    /**
     * REMOVE ROOM METHOD
     * @return
     */
    public String removeRoom(String roomName) {
        Room roomToRemove = null;
        for (Room room : rooms) {
            if (room.getName().equals(roomName)) {
                roomToRemove = room;
                break;
            }
        }
        if (roomToRemove == null) {
            return "No room exists with the given name.";
        } else {
            rooms.remove(roomToRemove);
            roomCount--;
            return "Room removed: " + roomName;
        }
    }

    public String removeReservation(String guestName, LocalDate checkInDate) {
        Reservation reservationToRemove = null;
        for (Reservation res : reservations) {
            if (res.getGuestName().equals(guestName) && res.getCheckInDate().equals(checkInDate)) {
                reservationToRemove = res;
                break;
            }
        }
        if (reservationToRemove == null) {
            return "No reservation found for the given guest and check-in date.";
        } else {
            // Remove the reservation from the hotel's list
            reservations.remove(reservationToRemove);
            
            // Remove the reservation from the room
            Room room = reservationToRemove.getRoom();
            room.removeReservation(reservationToRemove);
            
            return "Reservation removed for " + guestName + " on " + checkInDate;
        }
    }
    

    public void displayRooms() {
        for (Room room : rooms) {
            System.out.println(room.getName());
        }
    }

    /**
     * CALCULATES EARNING per?
     * @return
     */
    public double calculateEarnings() {
        double earnings = 0;
        LocalDate now = LocalDate.now();
        for (Reservation res : reservations) {
            if (res.getCheckInDate().getMonth() == now.getMonth()) {
                earnings += res.getTotalPrice();
            }
        }
        return earnings;
    }

    // Get total number of available and booked rooms for a selected date
    public void getRoomAvailability(LocalDate date) {
        System.out.println("Availability for the date: " + date);
        for (Room room : rooms) {
            System.out.print("Room Name: " + room.getName());
            if (room.isAvailable(date)) {
                System.out.println(" - Available");
            } else {
                System.out.println(" - Booked");
            }
        }
    }
    

    // Get information about a specific room
    public void getRoomInfo(String roomName) {
        Room room = null;
        for (Room r : rooms) {
            if (r.getName().equals(roomName)) {
                room = r;
                break;
            }
        }
        if (room == null) {
            System.out.println("No room exists with the given name.");
            return;
        }
        System.out.println("Room Name: " + room.getName());
        System.out.println("Price per Night: " + room.getPrice());
        System.out.println("Availability for the month:");
        LocalDate now = LocalDate.now();
        for (int i = 1; i <= now.lengthOfMonth(); i++) {
            LocalDate date = now.withDayOfMonth(i);
            boolean isAvailable = true;
            for (Reservation res : reservations) {
                if (res.getRoom().equals(room) && !date.isBefore(res.getCheckInDate()) && !date.isAfter(res.getCheckOutDate())) {
                    isAvailable = false;
                    break;
                }
            }
            System.out.println(date + ": " + (isAvailable ? "Available" : "Booked"));
        }
    }

    // Get information about a specific reservation
    public void getReservationInfo(String guestName, LocalDate checkInDate) {
        Reservation reservation = null;
        for (Reservation res : reservations) {
            if (res.getGuestName().equals(guestName) && res.getCheckInDate().equals(checkInDate)) {
                reservation = res;
                break;
            }
        }
        if (reservation == null) {
            System.out.println("No reservation found for the given guest and check-in date.");
            return;
        }
        System.out.println("Guest Name: " + reservation.getGuestName());
        System.out.println("Room Name: " + reservation.getRoom().getName());
        System.out.println("Check-In Date: " + reservation.getCheckInDate());
        System.out.println("Check-Out Date: " + reservation.getCheckOutDate());
        System.out.println("Total Price: " + reservation.getTotalPrice());
        System.out.println("Price Breakdown per Night:");
        LocalDate date = reservation.getCheckInDate();
        while (!date.isAfter(reservation.getCheckOutDate())) {
            System.out.println(date + ": " + reservation.getRoom().getPrice());
            date = date.plusDays(1);
        }
    }


    public boolean isValidDateRange(LocalDate checkInDate, LocalDate checkOutDate) {
        // Ensure check-out is not on the 1st of the month
        if (checkOutDate.getDayOfMonth() == 1)
            return false;
        
        // Ensure check-in is not on the 31st of the month
        if (checkInDate.getDayOfMonth() == 31)
            return false;
    
        return !checkOutDate.isBefore(checkInDate);
    }
    

    private boolean isRoomAvailable(Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        for (Reservation res : reservations) {
            if (res.getRoom().equals(room) &&
                !(checkOutDate.isBefore(res.getCheckInDate()) || checkInDate.isAfter(res.getCheckOutDate()))) {
                return false;
            }
        }
        return true;
    }

    public Reservation simulateBooking(String guestName, LocalDate checkInDate, LocalDate checkOutDate) {
        if (!isValidDateRange(checkInDate, checkOutDate)) {
            System.out.println("Invalid date range. Check-out date must be after check-in date.");
            return null;
        }
    
        for (Room room : rooms) {
            if (isRoomAvailable(room, checkInDate, checkOutDate)) {
                Reservation reservation = new Reservation(guestName, checkInDate, checkOutDate, room);
                reservations.add(reservation);
                room.addReservation(reservation);
                return reservation;
            }
        }
        return null; // No available rooms
    }

    public boolean hasBookings() {
        return !reservations.isEmpty();
    }
    
    public void setHasBookings(boolean hasBookings) {
        this.hasBookings = hasBookings;
    }
    

}
