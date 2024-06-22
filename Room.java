public class Room {
    private String name; // Should be unique within a hotel
    private double price;

    public Room(String name, double price) {
        this.name = name;
        this.price = price;
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
        this.price = price;
    }

    public String toString() {
        return "Room{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
