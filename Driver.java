import java.time.LocalDate;
import java.util.Scanner;


public class Driver {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HRS hrs = new HRS();

        while (true) {
            System.out.println("\nHotel Reservation System");
            System.out.println("[1] Create Hotel");
            System.out.println("[2] View Hotel");
            System.out.println("[3] Manage Hotel");
            System.out.println("[4] Simulate Booking");
            System.out.println("[5] Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Enter hotel name: ");
                    String name = scanner.nextLine();
                    hrs.createHotel(name, scanner);
                    break;
                case 2:
                    System.out.print("Enter hotel name: ");
                    String nameToView = scanner.nextLine();
                    hrs.viewHotel(nameToView);
                    break;
                case 3:
                    System.out.print("Enter hotel name: ");
                    String nameToManage = scanner.nextLine();
                    hrs.manageHotel(nameToManage, scanner);
                    break;
                case 4:
                    hrs.simulateBooking(scanner);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void testPriceCalculation() {
    Room room = new Room("TestRoom", 1299.0, Room.RoomType.STANDARD);
    LocalDate checkIn = LocalDate.of(2024, 7, 1);  // Assuming July 1st, 2024
    LocalDate checkOut = LocalDate.of(2024, 7, 10); // Checkout on 10th
    
    double totalPrice = room.calculateTotalPrice(checkIn, checkOut, room.getPrice());
    
    System.out.printf("Total price for stay from %s to %s: %.2f%n", checkIn, checkOut, totalPrice);
}
}