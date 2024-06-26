import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *  The HRS class, short for Hotel Reservation System, manages a list of Hotels.
 *  Creation, Management, Viewing, and Booking of Hotels happen in this class.
 *  
 */

public class HRS {
    /**
     * The list of hotels managed by the HRS.
     */
    private ArrayList<Hotel> hotels;


    /**
     *  This is a constructor for the HRS class which initializes the list of hotels.
     */
    public HRS() {
        this.hotels = new ArrayList<>(); // Initialize list of hotels
    }

    /**
     * This method ceates a new Hotel following the specified name and room count.
     * 
     * @param name - the name of the hotel
     * @param roomCount - the number of rooms in the hotel
     * @param scanner  the scanner object for user input
     */
    public void createHotel(String name, int roomCount, Scanner scanner) {
        if (roomCount < 1 || roomCount > 50) {
            System.out.println("Room count must be between 1 and 50.");
            return;
        }
        if (isHotelNameTaken(name)) {
            System.out.println("A hotel with this name already exists.");
            return;
        }

        Hotel newHotel = new Hotel(name, roomCount, this); 
        hotels.add(newHotel);
        System.out.println("Hotel created: " + name + " with " + roomCount + " rooms and base price 1299");
    }

    /**
     * This method checks if the name of the hotel already exists as it has to be unique
     * 
     * @param name - the name of hotel to be checked
     * @return true if the name is indeed taken, false if not
     */
    public boolean isHotelNameTaken(String name) {
        for (Hotel hotel : hotels) {
            if (hotel.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }


    /**
     * This method removes the hotel name given if it exists and has no reservations under it.
     * 
     * @param name - the name of hotel to be removed
     */
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


    /**
     * This method displays the information about a given hotel.
     * 
     * @param name - the name of the hotel to be viewed
     */
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
                scanner.nextLine(); 

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

    /**
     * This method is responsible for managing the hotel. It can do serveral functions such as 
     * changing its name, adding and removing hotel rooms, updating the base price, removing reservations,
     * viewing the room names, and removing the hotel itself.
     * 
     * @param name - the name of the hotel to be managed
     * @param scanner - the scanner object for the user input
     */
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
            scanner.nextLine(); 
    
            switch (choice) {
                case 1:
                    System.out.print("Enter new hotel name: ");
                    String newName = scanner.nextLine();
                    hotel.setName(newName); 
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
                    scanner.nextLine(); 
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
                            return; 
                        } else {
                            System.out.println("Hotel removal canceled.");
                        }
                    }
                    break;
                case 8:
                    return; 
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    /**
     * This method is responsible for the booking process. It prompts the user to enter the
     * name of the guest, the date to be booked, and create the reservation.
     * 
     * @param scanner - the scanner object for the user input
     */
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

    /**
     * This method tells the user to input a valid date.
     * 
     * @param dateType - the type of date, if it is for check-in or check-out
     * @return validated LocalDate or null if invalid
     */
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

    /**
     * This method chekcs the provided date if it is in the range
     * of the defined booking perio
     * 
     * @param date - the date to be checked
     * @return true if the date is within the booking period, false if it does not
     */
    private boolean isValidBookingDate(LocalDate date) {
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1); // Start of the current month
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth()); // End of the current month

        return !date.isBefore(startOfMonth) && !date.isAfter(endOfMonth);
    }

    /**
     * This method finds a hotel in the list using its name.
     * 
     * @param name - the name of the hotel to be searched
     * @return the Hotel if it is found or null if note found
     */
    private Hotel findHotelByName(String name) {
        for (Hotel hotel : hotels) {
            if (hotel.getName().equals(name)) {
                return hotel;
            }
        }
        return null;
    }
}
