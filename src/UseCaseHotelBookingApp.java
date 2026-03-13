import java.util.*;

class Reservation {
    private String guestName;
    private String roomType;
    private String roomId;
    public Reservation(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    } 
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
    public String getRoomId() { return roomId; }
}
class BookingHistory {
    private List<Reservation> confirmedBookings;

    public BookingHistory() {
        confirmedBookings = new ArrayList<>();
    }
    public void addRecord(Reservation reservation) {
        confirmedBookings.add(reservation);
    }
    public List<Reservation> getHistory() {
        return Collections.unmodifiableList(confirmedBookings);
    }
}
class BookingReportService {
    public void generateSummaryReport(BookingHistory history) {
        List<Reservation> records = history.getHistory();
        System.out.println("--- Booking Summary Report ---");
        System.out.println("Total Bookings: " + records.size());
        Map<String, Integer> countsByType = new HashMap<>();
        for (Reservation res : records) {
            countsByType.put(res.getRoomType(), countsByType.getOrDefault(res.getRoomType(), 0) + 1);
        }
        countsByType.forEach((type, count) ->
                System.out.println(type + " Rooms Allocated: " + count));
        System.out.println("------------------------------\n");
    }
    public void displayDetailedHistory(BookingHistory history) {
        System.out.println("--- Detailed Booking Logs ---");
        for (Reservation res : history.getHistory()) {
            System.out.println("Guest: " + res.getGuestName() +
                    " | Room: " + res.getRoomId() +
                    " (" + res.getRoomType() + ")");
        }
        System.out.println("------------------------------\n");
    }
}
public class UseCaseHotelBookingApp {
    public static void main(String[] args) {
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();
        history.addRecord(new Reservation("Abhi", "Single", "Single-1"));
        history.addRecord(new Reservation("Subha", "Double", "Double-1"));
        history.addRecord(new Reservation("Vanmathi", "Single", "Single-2"));
        reportService.displayDetailedHistory(history);
        reportService.generateSummaryReport(history);
    }
}
