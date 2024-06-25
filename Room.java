import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Room {
    private String name; // Should be unique within a hotel
    private double price;
    private List<Reservation> reservations;

    public Room(String name, double price) {
        this.name = name;
        this.price = price;
        this.reservations = new ArrayList<>();
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

    public void setPrice(double price) {
        if (price < 100.0) {
            System.out.println("Room price must be greater than or equal to 100");
            return;
        }
        this.price = price;
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

  // Update the isAvailable method to check against the room's reservations
    public boolean isAvailable(LocalDate date) {
            for (Reservation res : this.reservations) {
                if (!date.isBefore(res.getCheckInDate()) && !date.isAfter(res.getCheckOutDate())) {
                    return false;
                }
            }
        return true;
    }
    
    }
