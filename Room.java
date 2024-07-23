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
}
