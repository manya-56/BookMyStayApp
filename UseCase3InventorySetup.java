import java.util.HashMap;

class RoomInventory {

    private HashMap<String, Integer> inventory;

    RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    void displayInventory() {
        System.out.println("===== Current Room Inventory =====");
        System.out.println(inventory);
    }

    void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

public class UseCase3InventorySetup {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        System.out.println("===== Book My Stay App v3.1 =====");

        inventory.displayInventory();

        inventory.updateAvailability("Single Room", 4);

        System.out.println("Updated Single Room Availability: " +
                inventory.getAvailability("Single Room"));
    }
}