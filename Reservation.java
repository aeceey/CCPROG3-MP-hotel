import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Reservation {

    private String guestName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Room room;
    private double totalPrice;
    private List<Double> breakdownCost; 

    public Reservation(String guestName, LocalDate checkInDate, LocalDate checkOutDate, Room room) {
        this.guestName = guestName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.room = room;
        this.breakdownCost = new ArrayList<>();
        calculateTotalPrice();
    }

    public String getGuestName() {
        return guestName;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public Room getRoom() {
        return room;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public List<Double> getBreakdownCost() {
        return breakdownCost;
    }

    private void calculateTotalPrice() {
        int nights = calculateNightsBetween(checkInDate, checkOutDate);
        double nightlyRate = room.getPrice();
        totalPrice = 0;

        for (int i = 0; i < nights; i++) {
            breakdownCost.add(nightlyRate);
            totalPrice += nightlyRate;
        }
    }

    private int calculateNightsBetween(LocalDate startDate, LocalDate endDate) {
        return (int) startDate.until(endDate).getDays();
    }
}
