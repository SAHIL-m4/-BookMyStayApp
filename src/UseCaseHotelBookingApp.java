import java.util.*;

class BookingException extends Exception {
    public BookingException(String message) {
        super(message);
    }
}

class RoomInventory {
    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        roomAvailability.put("Single", 2);
        roomAvailability.put("Double", 1);
    }
    public void validateAndDecrement(String roomType) throws BookingException {
        if (!roomAvailability.containsKey(roomType)) {
            throw new BookingException("Error: Room type '" + roomType + "' does not exist.");
        }
        int currentCount = roomAvailability.get(roomType);
        if (currentCount <= 0) {
            throw new BookingException("Error: No availability for " + roomType + " rooms.");
        }
        roomAvailability.put(roomType, currentCount - 1);
    }
    public int getCount(String roomType) {
        return roomAvailability.getOrDefault(roomType, 0);
    }
}
class BookingValidator {
    public static void validateGuestName(String name) throws BookingException {
        if (name == null || name.trim().isEmpty()) {
            throw new BookingException("Error: Guest name cannot be empty.");
        }
    }
}
public class UseCaseHotelBookingApp {
    public static void main(String[] args) {
        System.out.println("System Validation & Error Handling\n");
        RoomInventory inventory = new RoomInventory();
        String[][] testCases = {
                {"Abhi", "Single"},
                {"lolo", "Single"},
                {"Subha", "Penthouse"},
                {"Vanmathi", "Double"},
                {"Kavin", "Double"}
        };

        for (String[] test : testCases) {
            String name = test[0];
            String type = test[1];

            try {
                System.out.println("Processing: " + name + " for " + type);
                BookingValidator.validateGuestName(name);
                inventory.validateAndDecrement(type);
                System.out.println("Result: Booking Successful!\n");
            } catch (BookingException e) {
                System.err.println("Result: " + e.getMessage() + "\n");
            }
        }

        System.out.println("Final Inventory Status:");
        System.out.println("Single: " + inventory.getCount("Single"));
        System.out.println("Double: " + inventory.getCount("Double"));
    }
}