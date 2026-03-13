import java.util.*;
class RoomInventory {
    private Map<String, Integer> roomAvailability;
    public RoomInventory() {
        roomAvailability = new HashMap<>();
        roomAvailability.put("Single", 5);
    }
    public void incrementInventory(String roomType) {
        roomAvailability.put(roomType, roomAvailability.get(roomType) + 1);
    }
    public int getCount(String roomType) {
        return roomAvailability.getOrDefault(roomType, 0);
    }
}
class CancellationService {
    private Stack<String> releasedRoomIds;
    private Map<String, String> activeBookings;
    public CancellationService() {
        releasedRoomIds = new Stack<>();
        activeBookings = new HashMap<>();
        activeBookings.put("RES101", "Single-1");
        activeBookings.put("RES102", "Single-2");
    }
    public void cancelBooking(String reservationId, String roomType, RoomInventory inventory) {
        if (!activeBookings.containsKey(reservationId)) {
            System.out.println("Error: Reservation " + reservationId + " not found.");
            return;
        }
        String roomId = activeBookings.remove(reservationId);
        releasedRoomIds.push(roomId);
        inventory.incrementInventory(roomType);
        System.out.println("Cancellation Successful: " + reservationId);
        System.out.println("Room " + roomId + " added to rollback stack.");
    }
    public void displayRollbackStatus() {
        System.out.println("Current Rollback Stack (Recently Released): " + releasedRoomIds);
    }
}
public class UseCaseHotelBookingApp {
    public static void main(String[] args) {
        System.out.println("Booking Cancellation & Inventory Rollback\n");
        RoomInventory inventory = new RoomInventory();
        CancellationService cancellationService = new CancellationService();
        System.out.println("Initial Single Inventory: " + inventory.getCount("Single"));
        cancellationService.cancelBooking("RES102", "Single", inventory);
        cancellationService.cancelBooking("RES101", "Single", inventory);
        cancellationService.cancelBooking("RES999", "Single", inventory);
        System.out.println("\nFinal Single Inventory: " + inventory.getCount("Single"));
        cancellationService.displayRollbackStatus();
    }
}