import java.util.Scanner;

public class HRS {
    private Hotel hotel;

    public HRS() {
        this.hotel = null; // Initialize hotel to null, indicating no hotel is present initially
    }

    public void createHotel(String name, int roomCount) {
        if (hotel != null) {
            System.out.println("A hotel already exists. Remove the existing hotel before creating a new one.");
        } else {
            hotel = new Hotel(name, roomCount);
            System.out.println("Hotel created: " + name + " with " + roomCount + " rooms.");
        }
    }

    public void removeHotel() {
        if (hotel == null) {
            System.out.println("No hotel exists to remove.");
        } else {
            hotel = null;
            System.out.println("Hotel removed.");
        }
    }

    public void viewHotel() {
        if (hotel == null) {
            System.out.println("No hotel exists to view.");
        } else {
            System.out.println("Hotel Name: " + hotel.getName());
            System.out.println("Total Rooms: " + hotel.getRoomCount());
            System.out.println("Base Price per Night: " + hotel.getBasePrice());
            System.out.println("Reservations: " + hotel.getReservations().size());
        }
    }

    // Other methods to manage hotel, rooms, and reservations can be added here

    public static void main(String[] args) {
        HRS hrs = new HRS();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nHotel Reservation System (HRS)");
            System.out.println("1. Create Hotel");
            System.out.println("2. View Hotel");
            System.out.println("3. Remove Hotel");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter hotel name: ");
                    scanner.nextLine(); 
                    String name = scanner.nextLine();
                    System.out.print("Enter number of rooms: ");
                    int roomCount = scanner.nextInt();
                    hrs.createHotel(name, roomCount);
                    break;
                case 2:
                    hrs.viewHotel();
                    break;
                case 3:
                    hrs.removeHotel();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
