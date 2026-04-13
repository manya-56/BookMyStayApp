import java.util.*;

class Reservation {
    String guest;
    String roomType;
    String roomId;

    Reservation(String guest, String roomType, String roomId) {
        this.guest = guest;
        this.roomType = roomType;
        this.roomId = roomId;
    }
}

class Inventory {

    private Map<String, Integer> stock = new HashMap<>();

    Inventory() {
        stock.put("Single Room", 2);
        stock.put("Double Room", 1);
        stock.put("Suite Room", 1);
    }

    int get(String roomType) {
        return stock.getOrDefault(roomType, 0);
    }

    void increase(String roomType) {
        stock.put(roomType, stock.getOrDefault(roomType, 0) + 1);
    }

    void decrease(String roomType) {
        stock.put(roomType, stock.getOrDefault(roomType, 0) - 1);
    }

    void print() {
        System.out.println(stock);
    }
}

class CancellationService {

    private Map<String, Reservation> activeBookings = new HashMap<>();
    private Stack<String> rollbackStack = new Stack<>();
    private Inventory inventory;

    CancellationService(Inventory inventory) {
        this.inventory = inventory;
    }

    void addBooking(Reservation r) {
        activeBookings.put(r.roomId, r);
    }

    void cancel(String roomId) {

        if (!activeBookings.containsKey(roomId)) {
            System.out.println("Cancellation failed: Invalid booking " + roomId);
            return;
        }

        Reservation r = activeBookings.get(roomId);

        rollbackStack.push(roomId);

        inventory.increase(r.roomType);

        activeBookings.remove(roomId);

        System.out.println("Cancelled booking: " + roomId);
    }

    void printActive() {
        System.out.println(activeBookings.keySet());
    }

    void printRollback() {
        System.out.println(rollbackStack);
    }
}

public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        Inventory inventory = new Inventory();
        CancellationService service = new CancellationService(inventory);

        System.out.println("===== Book My Stay App v10.0 =====");

        Reservation r1 = new Reservation("Arun", "Single Room", "S-101");
        Reservation r2 = new Reservation("Meena", "Double Room", "D-201");
        Reservation r3 = new Reservation("Kiran", "Suite Room", "U-301");

        service.addBooking(r1);
        service.addBooking(r2);
        service.addBooking(r3);

        service.printActive();

        service.cancel("D-201");
        service.cancel("S-101");

        service.printActive();
        service.printRollback();

        inventory.print();
    }
}