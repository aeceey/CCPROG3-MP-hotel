import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The Hotel class represents a hotel, managing the rooms and reservations.
 */
public class Hotel {
    /**
     * The name of the hotel.
     */
    private String name;

    /**
     * The number of rooms in the hotel.
     */
    private int roomCount;

    /**
     * The base price for the rooms in the hotel.
     */
    private double basePrice;

    /**
     * The list of rooms in the hotel.
     */
    private ArrayList<Room> rooms;

     /**
     * The list of reservations in the hotel.
     */
    private ArrayList<Reservation> reservations;

    /**
     * The next room number to be assigned.
     */
    private int nextRoomNumber;
    /**
     * The reference to the HRS (Hotel Reservation System).
     */
    private HRS hrs;

    /**
     * The number of standard rooms in the hotel.
     */
    private int standardRoomCount;

    /**
     * The number of deluxe rooms in the hotel.
     */
    private int deluxeRoomCount;

    /**
     * The number of executive rooms in the hotel.
     */
    private int executiveRoomCount;

    /**
     * This is the constructor for the Hotel class. It will create a Hotel based on the given parameters.
     * 
     * @param name the name of the hotel
     * @param standardRoomCount the number of standard rooms
     * @param deluxeRoomCount the number of deluxe rooms
     * @param executiveRoomCount the number of executive rooms
     * @param hrs the reference to the HRS
     */
    public Hotel(String name, int standardRoomCount, int deluxeRoomCount, int executiveRoomCount, HRS hrs) {
        this.name = name;
        this.roomCount = standardRoomCount + deluxeRoomCount + executiveRoomCount;
        this.basePrice = 1299.0;
        this.rooms = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.nextRoomNumber = 1;
        this.hrs = hrs;
        this.standardRoomCount = standardRoomCount;
        this.deluxeRoomCount = deluxeRoomCount;
        this.executiveRoomCount = executiveRoomCount;


        addRooms(Room.RoomType.STANDARD, standardRoomCount);
        addRooms(Room.RoomType.DELUXE, deluxeRoomCount);
        addRooms(Room.RoomType.EXECUTIVE, executiveRoomCount);

    }

    /**
     * A getter that gets the name of the hotel.
     *
     * @return the name of the hotel
     */

    public String getName() {
        return name;
    }

    /**
     * Sets the name of the hotel. Throws an exception if a hotel with the same name already exists.
     *
     * @param name the new name of the hotel
     * 
    public void setName(String name) {
        if (hrs.isHotelNameTaken(name)) {
            throw new IllegalArgumentException("A hotel with this name already exists.");
        }
        this.name = name;
    }

    /**
     * A Getter that gets the number of rooms in the hotel.
     *
     * @return the number of rooms in the hotel
     */
    public int getRoomCount() {
        return roomCount;
    }

    /**
     * Sets a new room count for the hotel, making sure that it
     * implements the limitation that it should not exceed 50 rooms.
     *
     * @param roomCount the new room count
     */
    public void setRoomCount(int roomCount) {
        if (roomCount > 50) {
            System.out.println("Cannot set room count more than 50.");
            return;
        }
        this.roomCount = roomCount;
    }

     /**
     * A getter that gets the base price for the rooms in the hotel.
     *
     * @return the base price for the rooms
     */
    public double getBasePrice() {
        return basePrice;
    }

    /**
     * A setter that sets a new base price for the rooms. It ensures that there are no existing reservations
     * and the price is at least 100.0.
     *
     * @param basePrice the new base price for the rooms
     */
    public void setBasePrice(double basePrice) {
        if (!reservations.isEmpty()) {
            throw new IllegalStateException("Cannot update base price as there are existing bookings in the hotel.");
        }
        if (basePrice < 100.0) {
            throw new IllegalArgumentException("Base price must be greater than or equal to 100.0");
        }
        this.basePrice = basePrice;
        for (Room room : rooms) {
            room.setPrice(basePrice);
        }
    }

    /**
     * A getter that gets the list of rooms in the hotel.
     *
     * @return the list of rooms
     */
    public ArrayList<Room> getRooms() {
        return rooms;
    }

    /**
     * A getter that gets the list of reservations in the hotel.
     *
     * @return the list of reservations
     */
    public ArrayList<Reservation> getReservations() {
        return reservations;
    }
   

    /**
     * Adds a new room to the hotel with the specified type.
     * 
     * @param type the type of the room to add
     * @return a message indicating the room was added
     * 
     */

    public String addRoom(Room.RoomType type) {
        if (rooms.size() >= 50) {
            throw new IllegalStateException("Room count cannot be more than 50.");
        }
        String newRoomName = name + String.format("%02d", nextRoomNumber++);
        rooms.add(new Room(newRoomName, basePrice, type));
        roomCount++;
        
        switch (type) {
            case STANDARD:
                standardRoomCount++;
                break;
            case DELUXE:
                deluxeRoomCount++;
                break;
            case EXECUTIVE:
                executiveRoomCount++;
                break;
        }
        
        return "Room added: " + newRoomName + " - " + type;
    }
    
    /**
     * Adds multiple rooms of the specified type to the hotel.
     * 
     * @param type the type of rooms to add
     * @param count the number of rooms to add
     */
    private void addRooms(Room.RoomType type, int count) {
        for (int i = 0; i < count; i++) {
            String roomName = name + String.format("%02d", nextRoomNumber++);
            this.rooms.add(new Room(roomName, basePrice, type));
        }
    }

    /**
     * This method removes a room from the hotel by its name.
     * 
     * @param roomName the name of the room to remove
     * @return a message indicating the room was removed
     * 
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
            throw new IllegalArgumentException("No room exists with the given name.");
        } else if (!roomToRemove.getReservations().isEmpty()) {
            throw new IllegalStateException("Cannot remove room as it has an existing reservation.");
        } else {
            rooms.remove(roomToRemove);
            roomCount--;
            return "Room removed: " + roomName;
        }
    }

    /**
     * 
     * Removes a reservation from the hotel by guest name and check-in date.
     * @param guestName the name of the guest
     * @param checkInDate the check-in date of the reservation
     * @return a message indicating the reservation was removed
     */
    public String removeReservation(String guestName, LocalDate checkInDate) {
        Reservation reservationToRemove = null;
        for (Reservation res : reservations) {
            if (res.getGuestName().equals(guestName) && res.getCheckInDate().equals(checkInDate)) {
                reservationToRemove = res;
                break;
            }
        }
        if (reservationToRemove == null) {
            throw new IllegalArgumentException("No reservation found for the given guest and check-in date.");
        } else {
            reservations.remove(reservationToRemove);
            Room room = reservationToRemove.getRoom();
            room.removeReservation(reservationToRemove);
            return "Reservation removed for " + guestName + " on " + checkInDate;
        }
    }

    /**
     * This method displays the names of all rooms in the hotel.
     */
    public void displayRooms() {
        for (Room room : rooms) {
            System.out.println(room.getName() + " - " + room.getType());
        }
    }


     /**
     * This method calculates the total earnings for the current month based on reservations.
     *
     * @return the total earnings for the current month
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


     /**
     * This method isplays the availability of rooms for a given date.
     *
     * @param date - the date to check availability for
     */
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

    /**
     * This method displays information about a room by its name.
     *
     * @param roomName the name of the room to display information for
     */
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
            if (isAvailable) {
                System.out.println(date + ": Available");
            } else {
                System.out.println(date + ": Booked");
            }
        }
    }


    /**
     * This method displays information about a reservation given a guest name and check-in date.
     *
     * @param guestName - the name of the guest
     * @param checkInDate - the check-in date of the reservation
     */
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
        while (date.isBefore(reservation.getCheckOutDate())) {
            System.out.println(date + ": " + reservation.getRoom().getPrice());
            date = date.plusDays(1);
        }
    }

    /**
     * This method validates the date range for a reservation.
     *
     * @param checkInDate - the check-in date
     * @param checkOutDate - the check-out date
     * @return true if the date range is valid, false if not
     */
    public boolean isValidDateRange(LocalDate checkInDate, LocalDate checkOutDate) {
        if (checkOutDate.getDayOfMonth() == 1)
            return false;
        if (checkInDate.getDayOfMonth() == 31)
            return false;
        return !checkOutDate.isBefore(checkInDate);
    }


    /**
     * This method checks if a room is available for the given date range.
     *
     * @param room - the room to check availability for
     * @param checkInDate -  the check-in date
     * @param checkOutDate - the check-out date
     * @return true if the room is available, false if not
     */
    private boolean isRoomAvailable(Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        for (Reservation res : reservations) {
            if (res.getRoom().equals(room)) {
                if (!(checkInDate.compareTo(res.getCheckOutDate()) >= 0 ||
                        checkOutDate.compareTo(res.getCheckInDate()) <= 0)) {
                    return false;
                }
            }
        }
        return true;
    }


     /**
     * Simulates a booking process and returns a reservation if successful.
     * 
     * @param guestName the name of the guest
     * @param checkInDate the check-in date
     * @param checkOutDate the check-out date
     * @param roomType the type of room to book
     * @param discountCode an optional discount code
     * @return the reservation if a room is successfully booked, null otherwise
     */
    public Reservation simulateBooking(String guestName, LocalDate checkInDate, LocalDate checkOutDate, Room.RoomType roomType, String discountCode) {
        if (!isValidDateRange(checkInDate, checkOutDate)) {
            throw new IllegalArgumentException("Invalid date range. Check-out date must be after check-in date.");
        }
    
        Room availableRoom = null;
        for (Room room : rooms) {
            if (room.getType() == roomType && isRoomAvailable(room, checkInDate, checkOutDate)) {
                availableRoom = room;
                break;
            }
        }
    
        if (availableRoom != null) {
            Reservation reservation = new Reservation(guestName, checkInDate, checkOutDate, availableRoom, discountCode);
            reservations.add(reservation);
            availableRoom.addReservation(reservation);
            return reservation;
        }
        
        return null;
    }
}
