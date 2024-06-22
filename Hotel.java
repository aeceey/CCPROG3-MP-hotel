import java.util.ArrayList;

public class Hotel {
    private String name;
    private ArrayList<Room> rooms;
    private ArrayList<Reservation> reservations;
    private double basePrice; // try to commit .... pull
    private int roomCount;


    public Hotel(String name, int roomCount) {
        this.name = name;
        this.rooms = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.basePrice = 1299.0;

        if (roomCount < 1 || roomCount > 50)
            throw new IllegalArgumentException("Room count must be between 1 and 50."); // is there another way to execute this
        //  apparently this ^^ stops the execution and prints out the string 
        this.roomCount = roomCount;
  
    }

    public String getName(){
        return name;
    }

        public void setName(String name) {
            this.name = name;
        }

    
    public ArrayList<Room> getRooms() {
         return rooms;
    }
    
    public ArrayList<Reservation> getReservations() {
         return reservations;
    }

    public double getBasePrice() {
        return basePrice;
    } // base price cannot be edited iirc

    public int getRoomCount(){
        return roomCount;
    }


    public String addRoom(){
        if (rooms.size() >= 50){
            return "Room count cannot be more than 50";
        }

        for (Room r : rooms){
            if (r.getName().equals(name));
                return "Room name already exists";
        }

        rooms.add(new Room(name, basePrice));
        return "Room added";
    }


    public String removeRoom(){
        if (rooms.size() <= 1){
            return "Room count cannot be less than 1";
        }

        for (Room r : rooms){
            if (r.getName().equals(name));
                rooms.remove(r);
                return "Room deleted";
        }

        return "Room does not exist";
    }


}
