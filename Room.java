import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The Room class represents a hotel room with specific attributes and reservations.
 * It includes methods to manage room details and calculate the total price for a stay.
 */
public class Room {

    /**
     * The name of the room.
     */
    private String name;
    /**
     * The price per night of the room.
     */
    private double price;
     /**
     * The list of reservations for the room.
     */
    private List<Reservation> reservations;
    /**
     * The type of the room (STANDARD, DELUXE, EXECUTIVE).
     */
    private RoomType type;

    /**
     * This method helps to categorize rooms into standard, deluxe, and executive types.
     */
    public enum RoomType {
        /**
         * A standard room type
         */
        STANDARD, 
        /**
         * A deluxe room type
         */
        DELUXE, 
        /**
         * An executive room type
         */
        EXECUTIVE
    }

    /**
     * The constructor for the Room class. It creates a Room object 
     * with the given name base price, and type.
     *
     * @param name - the name of the room
     * @param basePrice - the base price of the room
     * @param type - the type of the room
     */
    public Room(String name, double basePrice, RoomType type) {
        this.name = name;
        this.type = type;
        setPrice(basePrice);
        this.reservations = new ArrayList<>();
    }

    /**
     * A getter that gets the type of the room.
     * 
     * @return the room type
     */
    public RoomType getType() {
        return type;
    }


    /**
     * A getter that gets the name of the room.
     * 
     * @return the name of the room
     */
    public String getName() {
        return name;
    }

    /**
     * A setter that sets a new name for the room.
     *
     * @param name - the new name of the room
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * A getter that gets the price per night of the room.
     * 
     * @return the price per night for the room
     */
    public double getPrice() {
        return price;
    }

    /**
     * A setter that sets a new price per night for the room, based on the type of room.
     *
     * @param basePrice - the new price per night
     */
    public void setPrice(double basePrice) {
        switch (type) {
            case STANDARD:
                this.price = basePrice;
                break;
            case DELUXE:
                this.price = basePrice * 1.2;
                break;
            case EXECUTIVE:
                this.price = basePrice * 1.35;
                break;
        }
    }

    /**
     * A getter that gets the list of reservations for the room.
     *
     * @return the list of reservations
     */
    public List<Reservation> getReservations() {
        return reservations;
    }

     /**
     * This method adds a reservation to the room.
     *
     * @param reservation - the reservation to add
     */
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

     /**
     * This method adds a reservation to the room.
     *
     * @param reservation - the reservation to add
     */
    public void removeReservation(Reservation reservation) {
        this.reservations.remove(reservation);
    }

    /** Checks if the room is available on a specified date.
     * If room has no reservations on said date, then it is available.
     *
     * @param date the date to check
     * @return true if the room is available, false otherwise
     */
    public boolean isAvailable(LocalDate date) {
        for (Reservation res : this.reservations) {
            if (!date.isBefore(res.getCheckInDate()) && date.isBefore(res.getCheckOutDate())) {
                return false;
            }
        }
        return true;
    }

    /**
     * A method that alculates the total price for a stay from check-in to check-out dates, applying the DatePriceMultiplier.
     * 
     * @param checkIn - the check-in date
     * @param checkOut - the check-out date
     * @param basePrice - the base price of the room
     * @return the total price for the stay
     */

    public double calculateTotalPrice(LocalDate checkIn, LocalDate checkOut, double basePrice) {
        double total = 0.0;
        LocalDate currentDate = checkIn;
    
        while (currentDate.isBefore(checkOut)) {
            double multiplier = getMultiplierForDate(currentDate);
            double dailyPrice = basePrice * multiplier;
            total += dailyPrice;
            currentDate = currentDate.plusDays(1);
        }
    
        return total;
    }

    /**
     * A method that gets the price multiplier for a specific date based on the day of the month.
     * 
     * @param date - the date to get the multiplier for
     * @return the price multiplier
     */
    public double getMultiplierForDate(LocalDate date) {
        int dayOfMonth = date.getDayOfMonth();
        
        if ((dayOfMonth >= 5 && dayOfMonth <= 7) ||
            (dayOfMonth >= 12 && dayOfMonth <= 14) ||
            (dayOfMonth >= 19 && dayOfMonth <= 21) ||
            (dayOfMonth >= 26 && dayOfMonth <= 28)) {
            return 1.20; // 120%
        } else if (dayOfMonth == 1 || dayOfMonth == 8 || dayOfMonth == 15 || dayOfMonth == 22 || dayOfMonth == 29) {
            return 0.80; // 80%
        } else if (dayOfMonth == 2 || dayOfMonth == 9 || dayOfMonth == 16 || dayOfMonth == 23 || dayOfMonth == 30) {
            return 0.90; // 90%
        } else {
            return 1.00; // 100%
        }
    }
    
}
