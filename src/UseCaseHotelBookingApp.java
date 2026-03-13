import java.util.*;
class AddOnService {
    private String name;
    private double price;
    public AddOnService(String name, double price) {
        this.name = name;
        this.price = price;
    }
    public String getName() { return name; }
    public double getPrice() { return price; }
}
class AddOnServiceManager {
    private Map<String, List<AddOnService>> reservationAddOns;
    public AddOnServiceManager() {
        reservationAddOns = new HashMap<>();
    }
    public void addServiceToReservation(String reservationId, AddOnService service) {
        reservationAddOns.computeIfAbsent(reservationId, k -> new ArrayList<>()).add(service);
    }
    public double calculateTotalAddOnCost(String reservationId) {
        List<AddOnService> services = reservationAddOns.getOrDefault(reservationId, Collections.emptyList());
        double total = 0;
        for (AddOnService service : services) {
            total += service.getPrice();
        }
        return total;
    }

    public void displayAddOns(String reservationId) {
        List<AddOnService> services = reservationAddOns.getOrDefault(reservationId, Collections.emptyList());
        if (services.isEmpty()) {
            System.out.println("No add-ons for Reservation: " + reservationId);
            return;
        }
        System.out.println("Add-ons for Reservation " + reservationId + ":");
        for (AddOnService service : services) {
            System.out.println("- " + service.getName() + " ($" + service.getPrice() + ")");
        }
    }
}

public class UseCaseHotelBookingApp {
    public static void main(String[] args) {
        System.out.println("Add-On Service Selection\n");

        AddOnServiceManager manager = new AddOnServiceManager();

        AddOnService wifi = new AddOnService("High-Speed WiFi", 500.0);
        AddOnService breakfast = new AddOnService("Buffet Breakfast", 1200.0);
        AddOnService spa = new AddOnService("Spa Treatment", 3500.0);

        String resId = "Single-1";

        manager.addServiceToReservation(resId, wifi);
        manager.addServiceToReservation(resId, breakfast);
        manager.addServiceToReservation(resId, spa);

        manager.displayAddOns(resId);
        System.out.println("Total Add-On Cost: $" + manager.calculateTotalAddOnCost(resId));
    }
}
