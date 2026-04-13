import java.util.*;

class Reservation {
    String guestName;
    String roomType;

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

class InventoryService {

    Map<String, Integer> inventory = new HashMap<>();
    Map<String, Set<String>> allocatedRooms = new HashMap<>();
    Set<String> usedRoomIds = new HashSet<>();

    InventoryService() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);

        allocatedRooms.put("Single Room", new HashSet<>());
        allocatedRooms.put("Double Room", new HashSet<>());
        allocatedRooms.put("Suite Room", new HashSet<>());
    }

    boolean isAvailable(String type) {
        return inventory.getOrDefault(type, 0) > 0;
    }

    String generateRoomId(String type) {
        String id;
        do {
            id = type.substring(0, 1).toUpperCase() + "-" + (int) (Math.random() * 1000);
        } while (usedRoomIds.contains(id));
        return id;
    }

    String allocate(String type) {

        if (!isAvailable(type)) return null;

        String roomId = generateRoomId(type);

        usedRoomIds.add(roomId);
        allocatedRooms.get(type).add(roomId);
        inventory.put(type, inventory.get(type) - 1);

        return roomId;
    }

    void showInventory() {
        System.out.println("===== Inventory =====");
        System.out.println(inventory);
    }

    void showAllocated() {
        System.out.println("===== Allocated Rooms =====");
        System.out.println(allocatedRooms);
    }
}

class BookingService {

    Queue<Reservation> queue = new LinkedList<>();
    InventoryService inventoryService;

    BookingService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    void addRequest(Reservation r) {
        queue.add(r);
    }

    void processAll() {

        System.out.println("===== Processing Reservations =====");

        while (!queue.isEmpty()) {

            Reservation r = queue.poll();

            if (inventoryService.isAvailable(r.roomType)) {

                String roomId = inventoryService.allocate(r.roomType);

                System.out.println(r.guestName + " confirmed → " +
                        r.roomType + " | Room ID: " + roomId);

            } else {
                System.out.println(r.guestName + " failed → No availability for " + r.roomType);
            }
        }
    }
}

public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        InventoryService inventory = new InventoryService();
        BookingService bookingService = new BookingService(inventory);

        System.out.println("===== Book My Stay App v6.0 =====");

        bookingService.addRequest(new Reservation("Arun", "Single Room"));
        bookingService.addRequest(new Reservation("Meena", "Double Room"));
        bookingService.addRequest(new Reservation("Kiran", "Suite Room"));
        bookingService.addRequest(new Reservation("Sara", "Single Room"));
        bookingService.addRequest(new Reservation("John", "Single Room"));

        bookingService.processAll();

        System.out.println();
        inventory.showInventory();
        inventory.showAllocated();
    }
}
