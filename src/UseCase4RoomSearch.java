import java.util.HashMap;

class Room {
    String type;
    int beds;
    double price;

    Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    void display() {
        System.out.println(type + " | Beds: " + beds + " | Price: " + price);
    }
}

class RoomInventory {

    private HashMap<String, Integer> inventory;

    RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }
}

class SearchService {

    private RoomInventory inventory;

    SearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    void search(Room[] rooms) {

        System.out.println("===== Available Rooms =====");

        for (Room room : rooms) {

            if (inventory.getAvailability(room.type) > 0) {
                room.display();
                System.out.println("Available: " + inventory.getAvailability(room.type));
                System.out.println();
            }
        }
    }
}

public class UseCase4RoomSearch {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        Room[] rooms = {
                new Room("Single Room", 1, 1000),
                new Room("Double Room", 2, 1800),
                new Room("Suite Room", 3, 3000)
        };

        SearchService searchService = new SearchService(inventory);

        System.out.println("===== Book My Stay App v4.0 =====");

        searchService.search(rooms);
    }
}