import java.util.*;

class InvalidBookingException extends Exception {
    InvalidBookingException(String message) {
        super(message);
    }
}

class RoomValidator {

    private Set<String> validRooms = new HashSet<>();

    RoomValidator() {
        validRooms.add("Single Room");
        validRooms.add("Double Room");
        validRooms.add("Suite Room");
    }

    void validate(String roomType, int availableCount) throws InvalidBookingException {

        if (!validRooms.contains(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        if (availableCount <= 0) {
            throw new InvalidBookingException("No availability for: " + roomType);
        }
    }
}

class Inventory {

    private Map<String, Integer> stock = new HashMap<>();

    Inventory() {
        stock.put("Single Room", 2);
        stock.put("Double Room", 1);
        stock.put("Suite Room", 0);
    }

    int getAvailability(String roomType) {
        return stock.getOrDefault(roomType, 0);
    }
}

class BookingService {

    private Inventory inventory;
    private RoomValidator validator;

    BookingService(Inventory inventory, RoomValidator validator) {
        this.inventory = inventory;
        this.validator = validator;
    }

    void book(String guest, String roomType) {

        try {

            int available = inventory.getAvailability(roomType);

            validator.validate(roomType, available);

            System.out.println(guest + " successfully booked " + roomType);

        } catch (InvalidBookingException e) {

            System.out.println("Booking failed for " + guest + ": " + e.getMessage());
        }
    }
}

public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        Inventory inventory = new Inventory();
        RoomValidator validator = new RoomValidator();
        BookingService service = new BookingService(inventory, validator);

        System.out.println("===== Book My Stay App v9.0 =====");

        service.book("Arun", "Single Room");
        service.book("Meena", "Suite Room");
        service.book("Kiran", "Deluxe Room");
    }
}
