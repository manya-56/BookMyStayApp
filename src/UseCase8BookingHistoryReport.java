import java.util.*;

class Reservation {
    String guestName;
    String roomType;
    String roomId;

    Reservation(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }
}

class BookingHistory {

    private List<Reservation> history = new ArrayList<>();

    void addBooking(Reservation r) {
        history.add(r);
    }

    List<Reservation> getHistory() {
        return history;
    }
}

class ReportService {

    void printReport(List<Reservation> history) {

        System.out.println("===== Booking History Report =====");

        for (Reservation r : history) {
            System.out.println(
                    r.guestName + " | " +
                            r.roomType + " | " +
                            r.roomId
            );
        }

        System.out.println();
        System.out.println("Total Bookings: " + history.size());
    }
}

public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        BookingHistory bookingHistory = new BookingHistory();
        ReportService reportService = new ReportService();

        System.out.println("===== Book My Stay App v8.0 =====");

        Reservation r1 = new Reservation("Arun", "Single Room", "S-101");
        Reservation r2 = new Reservation("Meena", "Double Room", "D-205");
        Reservation r3 = new Reservation("Kiran", "Suite Room", "U-501");

        bookingHistory.addBooking(r1);
        bookingHistory.addBooking(r2);
        bookingHistory.addBooking(r3);

        reportService.printReport(bookingHistory.getHistory());
    }
}