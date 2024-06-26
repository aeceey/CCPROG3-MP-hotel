import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The Room class represents a room in a hotel.
 */
public class Room {
    /**
     * The name of the room.
     */
    private String name;
    /**
     * The price per night for the room.
     */
    private double price;
    /**
     * The list of reservations for the room.
     */
    private List<Reservation> reservations;

     /**
     * The constructor for the Room class. It creates a Room object 
     * with the given name and price.
     *
     * @param name - the name of the room
     * @param price - the price per night for the room
     */
    public Room(String name, double price) {
        this.name = name;
        this.price = price;
        this.reservations = new ArrayList<>();
    }

    /**
     * A getter that gets the name of the room.
     * 
     * @return the name of hte room
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
     * @return the price per night for the room
     */
    public double getPrice() {
        return price;
    }

    /**
     * A setter that sets a new price per night for the room.
     *
     * @param price - the new price per night
     */
    public void setPrice(double price) {
        if (price < 100.0) {
            System.out.println("Room price must be greater than or equal to 100");
            return;
        }
        this.price = price;
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

    public boolean isAvailable(LocalDate date) {
        for (Reservation res : this.reservations) {
            if (!date.isBefore(res.getCheckInDate()) && date.isBefore(res.getCheckOutDate())) {
                return false;
            }
        }
        return true;
    }
}
