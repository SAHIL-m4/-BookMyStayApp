import java.io.*;
import java.util.*;
class RoomInventory implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Integer> availability;
    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single", 5);
        availability.put("Double", 3);
    }
    public void updateCount(String type, int count) {
        availability.put(type, count);
    }
    public void display() {
        System.out.println("Current Inventory: " + availability);
    }
}
class PersistenceService {
    private static final String FILE_NAME = "hotel_state.dat";
    public void saveData(RoomInventory inventory) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(inventory);
            System.out.println("System state persisted successfully.");
        } catch (IOException e) {
            System.err.println("Failed to save state: " + e.getMessage());
        }
    }
    public RoomInventory loadData() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No saved state found. Starting fresh.");
            return new RoomInventory();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (RoomInventory) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Recovery failed: " + e.getMessage());
            return new RoomInventory();
        }
    }
}
public class UseCaseHotelBookingApp {
    public static void main(String[] args) {
        PersistenceService persistence = new PersistenceService();
        System.out.println("--- System Restarting ---");
        RoomInventory inventory = persistence.loadData();
        inventory.display();
        System.out.println("\n--- Processing Booking ---");
        inventory.updateCount("Single", 4);
        inventory.display();
        System.out.println("\n--- System Shutting Down ---");
        persistence.saveData(inventory);
        System.out.println("\nNote: Run the program again to see the recovered state.");
    }
}