import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Room {

private String name;
    private double price;
    private List<Reservation> reservations;
    private RoomType type;


    public enum RoomType {
        STANDARD, DELUXE, EXECUTIVE
    }


    public Room(String name, double basePrice, RoomType type) {
        this.name = name;
        this.type = type;
        setPrice(basePrice);
        this.reservations = new ArrayList<>();
    }


    public RoomType getType() {
        return type;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public double getPrice() {
        return price;
    }


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


    public List<Reservation> getReservations() {
        return reservations;
    }


    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }


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

    private double getMultiplierForDate(LocalDate date) {
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
    
    private boolean isInDateRange(int dayOfMonth, int start, int end) {
        return dayOfMonth >= start && dayOfMonth <= end;
    }
    

    private double getBasePriceForType(double basePrice) {
        switch (type) {
            case DELUXE:
                return basePrice * 1.2;
            case EXECUTIVE:
                return basePrice * 1.35;
            default:
                return basePrice;
        }
    }
}
