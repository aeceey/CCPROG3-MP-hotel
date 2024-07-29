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
    private Room.RoomType roomType;
    private String discountCode;


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


    public Room.RoomType getRoomType() {
        return roomType;
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


    public String getDiscountCode() {
        return discountCode;
    }


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


    private int calculateNightsBetween(LocalDate startDate, LocalDate endDate) {
        return (int) startDate.until(endDate).getDays();
    }

}

