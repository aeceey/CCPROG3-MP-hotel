import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The Reservation class represents a reservation for a room in a hotel.
 */
public class Reservation {
    /**
     * The name of the guest making the reservation.
     */
    private String guestName;

    /**
     * The check-in date for the reservation.
     */
    private LocalDate checkInDate;

    /**
     * The check-out date for the reservation.
     */
    private LocalDate checkOutDate;

    /**
     * The room reserved for the guest.
     */
    private Room room;

    /**
     * The total price for the reservation.
     */
    private double totalPrice;

    /**
     * The list of costs per night for the reservation.
     */
    private List<Double> breakdownCost; 

    /**
     * This is the constructor for the Reservation class
     * that creates the object given the  guest name, check-in date, 
     * check-out date, and room.
     *
     * @param guestName - the name of the guest
     * @param checkInDate - the check-in date for the reservation
     * @param checkOutDate - the check-out date for the reservation
     * @param room - the room reserved for the guest
     */
    public Reservation(String guestName, LocalDate checkInDate, LocalDate checkOutDate, Room room) {
        this.guestName = guestName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.room = room;
        this.breakdownCost = new ArrayList<>();
        calculateTotalPrice();
    }

    /**
     * A getter that gets the name of the guest.
     *
     * @return the name of the guest
     */
    public String getGuestName() {
        return guestName;
    }

    /**
     * A getter that gets the check-in date for the reservation.
     *
     * @return the check-in date for the reservation
     */
    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    /**
     * Gets the check-out date for the reservation.
     *
     * @return the check-out date for the reservation
     */
    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

     /**
     * A getter that gets the room reserved for the guest.
     *
     * @return the room reserved for the guest
     */
    public Room getRoom() {
        return room;
    }

    /**
     * A getter that gets the total price for the reservation.
     *
     * @return the total price for the reservation
     */    public double getTotalPrice() {
        return totalPrice;
    }

     /**
     * A getter that gets the breakdown of costs per night for the reservation.
     *
     * @return the list of costs per night
     */
    public List<Double> getBreakdownCost() {
        return breakdownCost;
    }


     /**
     * This method calculates the total price for the reservation
     * based on the nightly rate and number of nights.
     */
    private void calculateTotalPrice() {
        int nights = calculateNightsBetween(checkInDate, checkOutDate);
        double nightlyRate = room.getPrice();
        totalPrice = 0;

        for (int i = 0; i < nights; i++) {
            breakdownCost.add(nightlyRate);
            totalPrice += nightlyRate;
        }
    }

     /**
     * This method calculates the number of nights between two dates.
     *
     * @param startDate - the start date
     * @param endDate - the end date
     * @return the number of nights between the start and end dates
     */
    private int calculateNightsBetween(LocalDate startDate, LocalDate endDate) {
        return (int) startDate.until(endDate).getDays();
    }
}
