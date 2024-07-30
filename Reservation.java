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
     * The type of room reserved for the guest.
     */
    private Room.RoomType roomType;

    /**
     * The discount code applied to the reservation, if any.
     */
    private String discountCode;


    /**
     * This is the constructor for the Reservation class
     * that creates the object given the  guest name, check-in date, 
     * check-out date, room, and discount code.
     *  
     * @param guestName - the name of the guest
     * @param checkInDate - the check-in date for the reservation
     * @param checkOutDate - the check-out date for the reservation
     * @param room - the room reserved for the guest
     * @param discountCode - the discount code applied to the reservation, if there is
     */
    public Reservation(String guestName, LocalDate checkInDate, LocalDate checkOutDate, Room room, String discountCode) {
        this.guestName = guestName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.room = room;
        this.roomType = room.getType();
        this.breakdownCost = new ArrayList<>();
        this.discountCode = discountCode;
        this.totalPrice = room.calculateTotalPrice(checkInDate, checkOutDate, room.getPrice());
        applyDiscount();
    }

    /**
     * Gets the type of room reserved for the guest.
     *
     * @return the type of room reserved for the guest
     */
    public Room.RoomType getRoomType() {
        return roomType;
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
     * A getter that gets the discount code applied to the reservation, if there is.
     *
     * @return the discount code applied to the reservation
     */
    public String getDiscountCode() {
        return discountCode;
    }

    /**
     * Calculates the total price for the reservation, taking into account the room price, number of nights,
     * and any applicable discount codes.
     */
    private void calculateTotalPrice() {
        int nights = (int) checkInDate.until(checkOutDate).getDays();
        double nightlyRate = room.getPrice();
        totalPrice = 0;

        for (int i = 0; i < nights; i++) {
            double dailyRate = nightlyRate;
            if (discountCode != null && discountCode.equals("STAY4_GET1") && i == 0 && nights >= 5) {
                dailyRate = 0; // First day is free
            }
            breakdownCost.add(dailyRate);
            totalPrice += dailyRate;
        }

        applyDiscount();
    }


    /**
     * This method applies any applicable discount based on the discount code enrtered.
     */
    private void applyDiscount() {
        if (discountCode == null) {
            return;
        }

        switch (discountCode) {
            case "I_WORK_HERE":
                totalPrice *= 0.9; // 10% discount
                break;
            case "PAYDAY":
                if ((checkInDate.getDayOfMonth() == 15 || checkInDate.getDayOfMonth() == 30) &&
                    checkOutDate.getDayOfMonth() != 15 && checkOutDate.getDayOfMonth() != 30) {
                    totalPrice *= 0.93; // 7% discount
                }
                break;
            // STAY4_GET1 is handled in calculateTotalPrice
        }
    }

}

