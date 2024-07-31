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
     * The type of room reserved.
     */
    private Room.RoomType roomType;

    /**
     * The discount code applied to the reservation.
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
     * @param discountCode - the discount code applied to the reservation
     */
    public Reservation(String guestName, LocalDate checkInDate, LocalDate checkOutDate, Room room, String discountCode) {
        this.guestName = guestName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.room = room;
        this.roomType = room.getType();
        this.breakdownCost = new ArrayList<>();
        this.discountCode = discountCode;
        calculateTotalPrice();
        applyDiscount();
    }

    /**
     * Returns the type of the room.
     *
     * @return the type of the room as an enum value
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
     * Gets the discount code applied to the reservation.
     * 
     * @return the discount code
     */
    public String getDiscountCode() {
        return discountCode;
    }


    /**
     * This method calculates the total price for the reservation, including applying any discounts.
     * The discount code "STAY4_GET1" is applied in this method, making the first day free
     * if the stay is 5 days or more.
     */
    private void calculateTotalPrice() {
        int nights = (int) (checkOutDate.toEpochDay() - checkInDate.toEpochDay());
        double nightlyRate = room.getPrice();
        totalPrice = 0;
        breakdownCost.clear();

        for (int i = 0; i < nights; i++) {
            LocalDate currentDate = checkInDate.plusDays(i);
            double dailyRate = nightlyRate * room.getMultiplierForDate(currentDate);
            if (discountCode != null && discountCode.equals("STAY4_GET1") && i == 0 && nights >= 5) {
                dailyRate = 0; // First day is free if stay is 5 days or more
            }
            breakdownCost.add(dailyRate);
            totalPrice += dailyRate;
        }
    }

    /**
     * Applies the discount based on the discount code provided.
     * The code "I_WORK_HERE" will provide a 10% discount while "PAYDAY" will provide a 7% discount.
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
        }
    }
}
