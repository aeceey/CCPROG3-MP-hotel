import java.util.ArrayList;

public class Hotel {
    private String name;
    private ArrayList<Room> rooms;
    private ArrayList<Reservation> reservations;
    private double basePrice; // try to commit .... pull

    public Hotel(String name, int roomCount) {
        this.name = name;

        this.rooms = new ArrayList<>();
        
        this.reservations = new ArrayList<>();
        this.basePrice = 1299.0;

    }

    public String getName(){
        return name;
    }

        public void setName(String name) {
            this.name = name;
        }


    public double getBasePrice() {
        return basePrice;
    }
}
