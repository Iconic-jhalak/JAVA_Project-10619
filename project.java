import java.util.InputMismatchException;
import java.util.Scanner;

class Consumer {
    private final int id;
    private final String name;
    private final int unitsConsumed;

    public Consumer(int id, String name, int unitsConsumed) {
        this.id = id;
        this.name = name;
        this.unitsConsumed = unitsConsumed;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getUnitsConsumed() { return unitsConsumed; }
}

class TariffCalculator {
    // Definable rates and slab thresholds for easier maintenance
    private static final double RATE_SLAB_1 = 1.5;
    private static final double RATE_SLAB_2 = 2.5;
    private static final double RATE_SLAB_3 = 4.0;
    private static final double RATE_SLAB_4 = 6.0;

    public double generateBill(Consumer consumer) {
        int units = consumer.getUnitsConsumed();
        double bill = 0;

        if (units <= 100) {
            bill = units * RATE_SLAB_1;
        } 
        else if (units <= 200) {
            bill = (100 * RATE_SLAB_1) + ((units - 100) * RATE_SLAB_2);
        } 
        else if (units <= 300) {
            bill = (100 * RATE_SLAB_1) + (100 * RATE_SLAB_2) + ((units - 200) * RATE_SLAB_3);
        } 
        else {
            bill = (100 * RATE_SLAB_1) + (100 * RATE_SLAB_2) + (100 * RATE_SLAB_3) + ((units - 300) * RATE_SLAB_4);
        }

        return bill;
    }
}

public class ElectricityBillingSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int id = 0;
        String name = "";
        int units = 0;

        System.out.println("=== Welcome to the Electricity Billing System ===");

        // Robust Input Validation Loop for ID
        while (true) {
            try {
                System.out.print("Enter Consumer ID: ");
                id = sc.nextInt();
                if (id <= 0) {
                    System.out.println("ID must be a positive integer. Please try again.");
                    continue;
                }
                sc.nextLine(); // Clear scanner buffer
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! ID must be a numeric value.");
                sc.nextLine(); // Clear invalid token
            }
        }

        // Input Name
        while (true) {
            System.out.print("Enter Consumer Name: ");
            name = sc.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Name cannot be left blank.");
            } else {
                break;
            }
        }

        // Robust Input Validation Loop for Units
        while (true) {
            try {
                System.out.print("Enter Units Consumed: ");
                units = sc.nextInt();
                if (units < 0) {
                    System.out.println("Units consumed cannot be negative.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Units must be a whole number.");
                sc.nextLine(); 
            }
        }

        // Calculations
        Consumer consumer = new Consumer(id, name, units);
        TariffCalculator calculator = new TariffCalculator();
        double totalBill = calculator.generateBill(consumer);
        
        System.out.println("         ELECTRICITY BILL DETAILS       ");
        System.out.printf("%-20s : %d\n", "Consumer ID", consumer.getId());
        System.out.printf("%-20s : %s\n", "Consumer Name", consumer.getName());
        System.out.printf("%-20s : %d units\n", "Units Consumed", consumer.getUnitsConsumed());
        System.out.printf("%-20s : Rs. %.2f\n", "Total Bill Due", totalBill);

        sc.close();
    }
}
