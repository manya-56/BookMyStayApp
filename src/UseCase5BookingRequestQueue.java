import java.util.LinkedList;
import java.util.Queue;

class Reservation {
    String guestName;
    String roomType;

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    void display() {
        System.out.println(guestName + " requested " + roomType);
    }
}

class BookingQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    void addRequest(Reservation r) {
        queue.add(r);
    }

    void showRequests() {
        System.out.println("===== Booking Queue (FIFO) =====");

        for (Reservation r : queue) {
            r.display();
        }
    }

    Reservation processNext() {
        return queue.poll();
    }
}

public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        BookingQueue bookingQueue = new BookingQueue();

        System.out.println("===== Book My Stay App v5.0 =====");

        bookingQueue.addRequest(new Reservation("Arun", "Single Room"));
        bookingQueue.addRequest(new Reservation("Meena", "Double Room"));
        bookingQueue.addRequest(new Reservation("Kiran", "Suite Room"));
        bookingQueue.addRequest(new Reservation("Sara", "Single Room"));

        bookingQueue.showRequests();

        System.out.println();
        System.out.println("Processing Requests in FIFO Order:");
        System.out.println();

        Reservation r1 = bookingQueue.processNext();
        System.out.println("Processed: " + r1.guestName);

        Reservation r2 = bookingQueue.processNext();
        System.out.println("Processed: " + r2.guestName);
    }
}