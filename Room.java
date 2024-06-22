public class Room {
    private String name;  // should be unique? 
    private double price;

    public Room(String name, double price){
        this.name = name;
        this.price = price;
    }

    public String getName(){
        return name;
    }
        public void setName(String name){
            this.name = name;
        }

    public double getPrice(){
       return price;
    }
    

}
