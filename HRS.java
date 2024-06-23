import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class HRS {
    private ArrayList<Hotel> hotels;

    public HRS() {
        this.hotels = new ArrayList<>(); // Initialize list of hotels
    }

    public void createHotel(String name, int roomCount, Scanner scanner) {
        for (Hotel hotel : hotels) {
            if (hotel.getName().equals(name)) {
                System.out.println("A hotel with this name already exists.");
                return;
            }
        }

        Hotel newHotel = new Hotel(name, roomCount);
        for (int i = 0; i < roomCount; i++) {
            System.out.print("Enter name for room " + (i + 1) + ": ");
            String roomName = scanner.nextLine();
            newHotel.addRoom(roomName);
        }
        hotels.add(newHotel);
        System.out.println("Hotel created: " + name + " with " + roomCount + " rooms.");
    }

    public void removeHotel(String name) {
        Hotel hotelToRemove = null;
        for (Hotel hotel : hotels) {
            if (hotel.getName().equals(name)) {
                hotelToRemove = hotel;
                break;
            }
        }
        if (hotelToRemove == null) {
            System.out.println("No hotel exists with the given name.");
        } else {
            hotels.remove(hotelToRemove);
            System.out.println("Hotel removed: " + name);
        }
    }

    public void viewHotel(String name) {
        Hotel hotelToView = null;
        for (Hotel hotel : hotels) {
            if (hotel.getName().equals(name)) {
                hotelToView = hotel;
                break;
            }
        }
        if (hotelToView == null) {
            System.out.println("No hotel exists with the given name.");
        } else {
            System.out.println("Hotel Name: " + hotelToView.getName());
            System.out.println("Total Rooms: " + hotelToView.getRoomCount());
            System.out.println("Base Price per Night: " + hotelToView.getBasePrice());
            System.out.println("Reservations: " + hotelToView.getReservations().size());
        }
    }

    public void manageHotel(String hotelName) {
        Hotel hotelToManage = null;
        for (Hotel hotel : hotels) {
            if (hotel.getName().equals(hotelName)) {
                hotelToManage = hotel;
                break;
            }
        }
        if (hotelToManage == null) {
            System.out.println("No hotel exists with the given name.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nManage Hotel: " + hotelToManage.getName());
            System.out.println("1. Change Hotel Name");
            System.out.println("2. Add Room");
            System.out.println("3. Remove Room");
            System.out.println("4. Update Base Price");
            System.out.println("5. Remove Reservation");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter new hotel name: ");
                    scanner.nextLine(); // consume newline
                    String newName = scanner.nextLine();
                    hotelToManage.setName(newName);
                    System.out.println("Hotel name updated to: " + newName);
                    break;
                case 2:
                    System.out.print("Enter room name: ");
                    scanner.nextLine(); // consume newline
                    String roomName = scanner.nextLine();
                    System.out.println(hotelToManage.addRoom(roomName));
                    break;
                case 3:
                    System.out.print("Enter room name: ");
                    scanner.nextLine(); // consume newline
                    String roomNameToRemove = scanner.nextLine();
                    System.out.println(hotelToManage.removeRoom(roomNameToRemove));
                    break;
                case 4:
                    System.out.print("Enter new base price: ");
                    double newBasePrice = scanner.nextDouble();
                    hotelToManage.setBasePrice(newBasePrice);
                    break;
                case 5:
                    System.out.print("Enter guest name: ");
                    scanner.nextLine(); // consume newline
                    String guestName = scanner.nextLine();
                    System.out.print("Enter check-in date (yyyy-MM-dd): ");
                    String checkIn = scanner.nextLine();
                    LocalDate checkInDate = LocalDate.parse(checkIn);
                    System.out.println(hotelToManage.removeReservation(guestName, checkInDate));
                    break;
                case 6:
                    System.out.println("Exiting Manage Hotel menu.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void addReservation(String hotelName, String guestName, LocalDate checkInDate, LocalDate checkOutDate, String roomName) {
        Hotel hotel = null;
        for (Hotel h : hotels) {
            if (h.getName().equals(hotelName)) {
                hotel = h;
                break;
            }
        }
        if (hotel == null) {
            System.out.println("No hotel exists with the given name.");
            return;
        }

        Room roomToBook = null;
        for (Room room : hotel.getRooms()) {
            if (room.getName().equals(roomName)) {
                roomToBook = room;
                break;
            }
        }

        if (roomToBook == null) {
            System.out.println("Room not found.");
            return;
        }

        for (Reservation res : hotel.getReservations()) {
            if (res.getRoom().equals(roomToBook) && !(checkOutDate.isBefore(res.getCheckInDate()) || checkInDate.isAfter(res.getCheckOutDate()))) {
                System.out.println("Room is not available for the selected dates.");
                return;
            }
        }

        Reservation newReservation = new Reservation(guestName, checkInDate, checkOutDate, roomToBook);
        hotel.getReservations().add(newReservation);
        System.out.println("Reservation added for " + guestName + " from " + checkInDate + " to " + checkOutDate);
    }

    public void removeReservation(String hotelName, String guestName, LocalDate checkInDate) {
        Hotel hotel = null;
        for (Hotel h : hotels) {
            if (h.getName().equals(hotelName)) {
                hotel = h;
                break;
            }
        }
        if (hotel == null) {
            System.out.println("No hotel exists with the given name.");
            return;
        }

        String result = hotel.removeReservation(guestName, checkInDate);
        System.out.println(result);
    }

    public static void main(String[] args) {
        HRS hrs = new HRS();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nHotel Reservation System (HRS)");
            System.out.println("1. Create Hotel");
            System.out.println("2. View Hotel");
            System.out.println("3. Remove Hotel");
            System.out.println("4. Manage Hotel");
            System.out.println("5. Add Reservation");
            System.out.println("6. Remove Reservation");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter hotel name: ");
                    scanner.nextLine();
                    String name = scanner.nextLine();
                    System.out.print("Enter number of rooms: ");
                    int roomCount = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    hrs.createHotel(name, roomCount, scanner);
                    break;
                case 2:
                    System.out.print("Enter hotel name: ");
                    scanner.nextLine();
                    String hotelToView = scanner.nextLine();
                    hrs.viewHotel(hotelToView);
                    break;
                case 3:
                    System.out.print("Enter hotel name: ");
                    scanner.nextLine();
                    String hotelToRemove = scanner.nextLine();
                    hrs.removeHotel(hotelToRemove);
                    break;
                case 4:
                    System.out.print("Enter hotel name: ");
                    scanner.nextLine();
                    String hotelToManage = scanner.nextLine();
                    hrs.manageHotel(hotelToManage);
                    break;
                case 5:
                    System.out.print("Enter hotel name: ");
                    scanner.nextLine();
                    String hotelNameForRes = scanner.nextLine();
                    System.out.print("Enter guest name: ");
                    String guestName = scanner.nextLine();
                    System.out.print("Enter check-in date (yyyy-MM-dd): ");
                    String checkIn = scanner.nextLine();
                    System.out.print("Enter check-out date (yyyy-MM-dd): ");
                    String checkOut = scanner.nextLine();
                    System.out.print("Enter room name: ");
                    String roomName = scanner.nextLine();
                    LocalDate checkInDate = LocalDate.parse(checkIn);
                    LocalDate checkOutDate = LocalDate.parse(checkOut);
                    hrs.addReservation(hotelNameForRes, guestName, checkInDate, checkOutDate, roomName);
                    break;
                case 6:
                    System.out.print("Enter hotel name: ");
                    scanner.nextLine();
                    String hotelNameForRemoval = scanner.nextLine();
                    System.out.print("Enter guest name: ");
                    String guestToRemove = scanner.nextLine();
                    System.out.print("Enter check-in date (yyyy-MM-dd): ");
                    String checkInToRemove = scanner.nextLine();
                    LocalDate checkInDateToRemove = LocalDate.parse(checkInToRemove);
                    hrs.removeReservation(hotelNameForRemoval, guestToRemove, checkInDateToRemove);
                    break;
                case 7:
                    System.out.println("Exiting HRS.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
