import java.util.*;

class AddOnService {
    String name;
    int cost;

    AddOnService(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }
}

class AddOnServiceManager {

    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    void addService(String reservationId, AddOnService service) {

        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
    }

    List<AddOnService> getServices(String reservationId) {
        return serviceMap.getOrDefault(reservationId, new ArrayList<>());
    }

    int calculateTotalCost(String reservationId) {

        int total = 0;

        for (AddOnService s : getServices(reservationId)) {
            total += s.cost;
        }

        return total;
    }

    void printServices(String reservationId) {

        List<AddOnService> list = getServices(reservationId);

        System.out.println("Services for " + reservationId + ":");

        for (AddOnService s : list) {
            System.out.println(s.name + " - " + s.cost);
        }

        System.out.println("Total Cost: " + calculateTotalCost(reservationId));
    }
}

public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        System.out.println("===== Book My Stay App v7.0 =====");

        AddOnServiceManager manager = new AddOnServiceManager();

        String reservationId = "R-101";

        manager.addService(reservationId, new AddOnService("Breakfast", 200));
        manager.addService(reservationId, new AddOnService("Airport Pickup", 500));
        manager.addService(reservationId, new AddOnService("Spa Access", 800));

        manager.printServices(reservationId);
    }
}