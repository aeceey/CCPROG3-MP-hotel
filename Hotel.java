import java.time.LocalDate;
import java.util.ArrayList;

public class Hotel {

    private String name;
    private int roomCount;
    private double basePrice;
    private ArrayList<Room> rooms;
    private ArrayList<Reservation> reservations;
    private int nextRoomNumber;
    private HRS hrs;
    private int standardRoomCount;
    private int deluxeRoomCount;
    private int executiveRoomCount;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (hrs.isHotelNameTaken(name)) {
            throw new IllegalArgumentException("A hotel with this name already exists.");
        }
        this.name = name;
    }

    public int getRoomCount() {
        return roomCount;
    }


    public void setRoomCount(int roomCount) {
        if (roomCount > 50) {
            System.out.println("Cannot set room count more than 50.");
            return;
        }
        this.roomCount = roomCount;
    }


    public double getBasePrice() {
        return basePrice;
    }


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
    public ArrayList<Room> getRooms() {
        return rooms;
    }


    public ArrayList<Reservation> getReservations() {
        return reservations;
    }
   

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

    private void addRooms(Room.RoomType type, int count) {
        for (int i = 0; i < count; i++) {
            String roomName = name + String.format("%02d", nextRoomNumber++);
            this.rooms.add(new Room(roomName, basePrice, type));
        }
    }

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

    public void displayRooms() {
        for (Room room : rooms) {
            System.out.println(room.getName() + " - " + room.getType());
        }
    }


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


    public boolean isValidDateRange(LocalDate checkInDate, LocalDate checkOutDate) {
        if (checkOutDate.getDayOfMonth() == 1)
            return false;
        if (checkInDate.getDayOfMonth() == 31)
            return false;
        return !checkOutDate.isBefore(checkInDate);
    }


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
