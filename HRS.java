import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class HRS {
    private ArrayList<Hotel> hotels;

    public HRS() {
        this.hotels = new ArrayList<>(); // Initialize list of hotels
    }

    public void createHotel(String name, int roomCount, Scanner scanner) {
        if (roomCount < 1 || roomCount > 50) {
            System.out.println("Room count must be between 1 and 50.");
            return;
        }
        if (isHotelNameTaken(name)) {
            System.out.println("A hotel with this name already exists.");
            return;
        }

        Hotel newHotel = new Hotel(name, roomCount, this); // Pass this HRS instance to the hotel constructor
        hotels.add(newHotel);
        System.out.println("Hotel created: " + name + " with " + roomCount + " rooms and base price 1299");
    }

    public boolean isHotelNameTaken(String name) {
        for (Hotel hotel : hotels) {
            if (hotel.getName().equals(name)) {
                return true;
            }
        }
        return false;
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
        } else if (!hotelToRemove.getReservations().isEmpty()) {
            System.out.println("Cannot remove hotel as it has existing reservations.");
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
            Scanner scanner = new Scanner(System.in);
            System.out.println("High-level Hotel Information:");
            System.out.println("Hotel Name: " + hotelToView.getName());
            System.out.println("Total Rooms: " + hotelToView.getRoomCount());
            System.out.println("Estimated Earnings for the Month: " + hotelToView.calculateEarnings());

            while (true) {
                System.out.println("\nAvailable Low-level Information:");
                System.out.println("1. Total number of available and booked rooms for a selected date");
                System.out.println("2. Information about a selected room");
                System.out.println("3. Information about a selected reservation");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter date (yyyy-mm-dd): ");
                        String dateStr = scanner.nextLine();
                        LocalDate date = LocalDate.parse(dateStr);
                        hotelToView.getRoomAvailability(date);
                        break;
                    case 2:
                        System.out.print("Enter room name: ");
                        String roomName = scanner.nextLine();
                        hotelToView.getRoomInfo(roomName);
                        break;
                    case 3:
                        System.out.print("Enter guest name: ");
                        String guestName = scanner.nextLine();
                        System.out.print("Enter check-in date (yyyy-mm-dd): ");
                        String checkInDateStr = scanner.nextLine();
                        LocalDate checkInDate = LocalDate.parse(checkInDateStr);
                        hotelToView.getReservationInfo(guestName, checkInDate);
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

    public void manageHotel(String name, Scanner scanner) {
        Hotel hotel = findHotelByName(name);
        if (hotel == null) {
            System.out.println("No hotel exists with the given name.");
            return;
        }
    
        while (true) {
            System.out.println("\nManage Hotel: " + hotel.getName());
            System.out.println("[1] Change Hotel Name");
            System.out.println("[2] Add Room");
            System.out.println("[3] Remove Room");
            System.out.println("[4] Update Base Price");
            System.out.println("[5] Remove Reservation");
            System.out.println("[6] View Room Names");
            System.out.println("[7] Remove Hotel");
            System.out.println("[8] Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
    
            switch (choice) {
                case 1:
                    System.out.print("Enter new hotel name: ");
                    String newName = scanner.nextLine();
                    hotel.setName(newName); // setName will handle the message
                    break;
                case 2:
                    System.out.println(hotel.addRoom());
                    break;
                case 3:
                    System.out.print("Enter room name to remove: ");
                    String roomName = scanner.nextLine();
                    System.out.println(hotel.removeRoom(roomName));
                    break;
                case 4:
                    System.out.print("Enter new base price: ");
                    double newBasePrice = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    hotel.setBasePrice(newBasePrice);
                    break;
                case 5:
                    System.out.print("Enter guest name: ");
                    String guestName = scanner.nextLine();
                    System.out.print("Enter check-in date (yyyy-mm-dd): ");
                    String checkInDateStr = scanner.nextLine();
                    LocalDate checkInDate = LocalDate.parse(checkInDateStr);
                    System.out.println(hotel.removeReservation(guestName, checkInDate));
                    break;
                case 6:
                    hotel.displayRooms();
                    break;
                case 7:
                    if (!hotel.getReservations().isEmpty()) {
                        System.out.println("Cannot remove the hotel as it has existing reservations.");
                    } else {
                        System.out.print("Are you sure you want to remove the hotel? (yes/no): ");
                        String confirmation = scanner.nextLine();
                        if (confirmation.equalsIgnoreCase("yes")) {
                            removeHotel(name);
                            return; // Exit the manage menu after removing the hotel
                        } else {
                            System.out.println("Hotel removal canceled.");
                        }
                    }
                    break;
                case 8:
                    return; // Exit the manage menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void simulateBooking(Scanner scanner) {
        System.out.print("Enter hotel name: ");
        String hotelName = scanner.nextLine();
        Hotel hotel = findHotelByName(hotelName);
        if (hotel == null) {
            System.out.println("No hotel exists with the given name.");
            return;
        }

        System.out.print("Enter guest name: ");
        String guestName = scanner.nextLine();

        LocalDate checkInDate = promptForDate("check-in");
        if (checkInDate == null || !isValidBookingDate(checkInDate)) {
            System.out.println("Invalid check-in date. Bookings cannot be made outside of the defined period for the month.");
            return;
        }

        LocalDate checkOutDate = promptForDate("check-out");
        if (checkOutDate == null || !isValidBookingDate(checkOutDate)) {
            System.out.println("Invalid check-out date. Bookings cannot be made outside of the defined period for the month.");
            return;
        }

        Reservation reservation = hotel.simulateBooking(guestName, checkInDate, checkOutDate);
        if (reservation != null) {
            System.out.println("Booking successful!");
        } else {
            System.out.println("No available rooms for the selected dates.");
        }
    }

    // Method to prompt user for a valid date input
    private LocalDate promptForDate(String dateType) {
        Scanner scanner = new Scanner(System.in);
        LocalDate date = null;
        boolean isValid = false;

        while (!isValid) {
            System.out.print("Enter " + dateType + " date (yyyy-mm-dd): ");
            String dateStr = scanner.nextLine();

            try {
                date = LocalDate.parse(dateStr);
                isValid = true;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter date in yyyy-mm-dd format.");
            }
        }
        
        return date;
    }

    // Method to check if a booking date is within the defined period for the month
    private boolean isValidBookingDate(LocalDate date) {
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1); // Start of the current month
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth()); // End of the current month

        return !date.isBefore(startOfMonth) && !date.isAfter(endOfMonth);
    }

    // Helper method to find a hotel by name
    private Hotel findHotelByName(String name) {
        for (Hotel hotel : hotels) {
            if (hotel.getName().equals(name)) {
                return hotel;
            }
        }
        return null;
    }
}
