import java.io.*;
import java.util.*;

class Inventory implements Serializable {
    private Map<String, Integer> stock = new HashMap<>();

    Inventory() {
        stock.put("Single Room", 2);
        stock.put("Double Room", 2);
        stock.put("Suite Room", 1);
    }

    int get(String roomType) {
        return stock.getOrDefault(roomType, 0);
    }

    void decrease(String roomType) {
        stock.put(roomType, stock.getOrDefault(roomType, 0) - 1);
    }

    void increase(String roomType) {
        stock.put(roomType, stock.getOrDefault(roomType, 0) + 1);
    }

    Map<String, Integer> getStock() {
        return stock;
    }

    void setStock(Map<String, Integer> stock) {
        this.stock = stock;
    }

    void print() {
        System.out.println(stock);
    }
}

class BookingHistory implements Serializable {
    List<String> history = new ArrayList<>();

    void add(String record) {
        history.add(record);
    }

    void print() {
        System.out.println(history);
    }

    List<String> getHistory() {
        return history;
    }

    void setHistory(List<String> history) {
        this.history = history;
    }
}

class PersistenceService {

    private static final String FILE = "booking_data.dat";

    void save(Inventory inventory, BookingHistory history) {

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE))) {

            out.writeObject(inventory);
            out.writeObject(history);

        } catch (Exception e) {
            System.out.println("Save failed");
        }
    }

    Object[] load() {

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE))) {

            Inventory inventory = (Inventory) in.readObject();
            BookingHistory history = (BookingHistory) in.readObject();

            return new Object[]{inventory, history};

        } catch (Exception e) {
            return null;
        }
    }
}

public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App v12.0 =====");

        PersistenceService service = new PersistenceService();

        Object[] data = service.load();

        Inventory inventory;
        BookingHistory history;

        if (data != null) {
            inventory = (Inventory) data[0];
            history = (BookingHistory) data[1];
            System.out.println("System restored from file");
        } else {
            inventory = new Inventory();
            history = new BookingHistory();
            System.out.println("Fresh system started");
        }

        String booking = "Arun booked Single Room";

        if (inventory.get("Single Room") > 0) {
            inventory.decrease("Single Room");
            history.add(booking);
        }

        history.print();
        inventory.print();

        service.save(inventory, history);
    }
}