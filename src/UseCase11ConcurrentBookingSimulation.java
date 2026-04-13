import java.util.*;

class BookingRequest {
    String guest;
    String roomType;

    BookingRequest(String guest, String roomType) {
        this.guest = guest;
        this.roomType = roomType;
    }
}

class Inventory {

    private Map<String, Integer> stock = new HashMap<>();

    Inventory() {
        stock.put("Single Room", 2);
        stock.put("Double Room", 2);
    }

    synchronized boolean allocate(String roomType) {

        int available = stock.getOrDefault(roomType, 0);

        if (available > 0) {
            stock.put(roomType, available - 1);
            return true;
        }

        return false;
    }

    synchronized void print() {
        System.out.println(stock);
    }
}

class BookingProcessor {

    private Queue<BookingRequest> queue = new LinkedList<>();
    private Inventory inventory;

    BookingProcessor(Inventory inventory) {
        this.inventory = inventory;
    }

    synchronized void addRequest(BookingRequest r) {
        queue.add(r);
    }

    void process() {

        while (true) {

            BookingRequest r;

            synchronized (this) {
                if (queue.isEmpty()) return;
                r = queue.poll();
            }

            boolean success = inventory.allocate(r.roomType);

            if (success) {
                System.out.println(r.guest + " booked " + r.roomType);
            } else {
                System.out.println(r.guest + " failed for " + r.roomType);
            }
        }
    }
}

public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) throws Exception {

        System.out.println("===== Book My Stay App v11.0 =====");

        Inventory inventory = new Inventory();
        BookingProcessor processor = new BookingProcessor(inventory);

        processor.addRequest(new BookingRequest("Arun", "Single Room"));
        processor.addRequest(new BookingRequest("Meena", "Single Room"));
        processor.addRequest(new BookingRequest("Kiran", "Single Room"));
        processor.addRequest(new BookingRequest("Rahul", "Double Room"));
        processor.addRequest(new BookingRequest("Divya", "Double Room"));

        Thread t1 = new Thread(() -> processor.process());
        Thread t2 = new Thread(() -> processor.process());

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        inventory.print();
    }
}