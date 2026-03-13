import java.util.*;
class RoomInventory {
    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        roomAvailability.put("Single", 2);
    }
    public synchronized boolean checkAndDecrement(String roomType) {
        int available = roomAvailability.getOrDefault(roomType, 0);
        if (available > 0) {
            roomAvailability.put(roomType, available - 1);
            return true;
        }
        return false;
    }
    public int getCount(String roomType) {
        return roomAvailability.getOrDefault(roomType, 0);
    }
}
class BookingProcessor implements Runnable {
    private String guestName;
    private String roomType;
    private RoomInventory inventory;
    public BookingProcessor(String guestName, String roomType, RoomInventory inventory) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.inventory = inventory;
    }
    @Override
    public void run() {
        System.out.println(guestName + " is attempting to book a " + roomType + " room...");

        if (inventory.checkAndDecrement(roomType)) {
            System.out.println("SUCCESS: Room allocated for " + guestName);
        } else {
            System.out.println("FAILURE: No rooms left for " + guestName);
        }
    }
}
public class UseCaseHotelBookingApp {
    public static void main(String[] args) {
        System.out.println("Concurrent Booking Simulation\n");
        RoomInventory sharedInventory = new RoomInventory();
        Thread t1 = new Thread(new BookingProcessor("Alice", "Single", sharedInventory));
        Thread t2 = new Thread(new BookingProcessor("Bob", "Single", sharedInventory));
        Thread t3 = new Thread(new BookingProcessor("Charlie", "Single", sharedInventory));
        Thread t4 = new Thread(new BookingProcessor("Diana", "Single", sharedInventory));
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\nFinal Inventory Status for Single: " + sharedInventory.getCount("Single"));
    }
}