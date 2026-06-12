import java.util.InputMismatchException;
import java.util.Scanner;

// ==========================================
// 1. ENCAPSULATION & INHERITANCE
// ==========================================
abstract class Consumer {
    private final int id;
    private final String name;
    protected final int unitsConsumed;

    public Consumer(int id, String name, int unitsConsumed) {
        this.id = id;
        this.name = name;
        this.unitsConsumed = unitsConsumed;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getUnitsConsumed() { return unitsConsumed; }

    // Abstract method to be overridden by subclasses (Polymorphism)
    public abstract double calculateBill();
}

// Residential Consumer with slab-based pricing
class ResidentialConsumer extends Consumer {
    public ResidentialConsumer(int id, String name, int unitsConsumed) {
        super(id, name, unitsConsumed);
    }

    @Override
    public double calculateBill() {
        int units = this.unitsConsumed;
        if (units <= 100) return units * 1.5;
        if (units <= 200) return (100 * 1.5) + ((units - 100) * 2.5);
        if (units <= 300) return (100 * 1.5) + (100 * 2.5) + ((units - 200) * 4.0);
        return (100 * 1.5) + (100 * 2.5) + (100 * 4.0) + ((units - 300) * 6.0);
    }
}

// Commercial Consumer with a flat rate + base commercial tax
class CommercialConsumer extends Consumer {
    private static final double FLAT_RATE = 7.5;
    private static final double COMMERCIAL_TAX = 250.00;

    public CommercialConsumer(int id, String name, int unitsConsumed) {
        super(id, name, unitsConsumed);
    }

    @Override
    public double calculateBill() {
        return (this.unitsConsumed * FLAT_RATE) + COMMERCIAL_TAX;
    }
}

// ==========================================
// 2. CORE SYSTEM DRIVER
// ==========================================
public class ElectricityBillingSystem {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Commercial & Residential Electricity Billing System ===");
        
        int totalConsumers = readValidInt("Enter total number of consumers to process: ", 1, 100);
        
        // ARRAY IMPLEMENTATION: Storing heterogeneous subclass objects in a base class array
        Consumer[] consumers = new Consumer[totalConsumers];

        for (int i = 0; i < consumers.length; i++) {
            System.out.println("\n--- Entering Details for Consumer #" + (i + 1) + " ---");
            
            int type = readValidInt("Select Category (1 for Residential, 2 for Commercial): ", 1, 2);
            int id = readValidInt("Enter Consumer ID: ", 1, Integer.MAX_VALUE);
            
            System.out.print("Enter Consumer Name: ");
            String name = sc.nextLine().trim();
            while (name.isEmpty()) {
                System.out.print("Name cannot be empty. Re-enter: ");
                name = sc.nextLine().trim();
            }

            int units = readValidInt("Enter Units Consumed: ", 0, Integer.MAX_VALUE);

            // Polymorphic instantiation based on selection
            if (type == 1) {
                consumers[i] = new ResidentialConsumer(id, name, units);
            } else {
                consumers[i] = new CommercialConsumer(id, name, units);
            }
        }

        // ==========================================
        // 3. GENERATE CONSOLIDATED MANAGEMENT REPORT
        // ==========================================
        System.out.println("\n=================================================================================");
        System.out.println("                           CONSOLIDATED BILLING LEDGER                           ");
        System.out.println("=================================================================================");
        System.out.printf("%-12s | %-20s | %-15s | %-14s | %-12s\n", "ID", "Name", "Type", "Units Consumed", "Total Bill");
        System.out.println("---------------------------------------------------------------------------------");

        for (Consumer consumer : consumers) {
            String typeStr = (consumer instanceof ResidentialConsumer) ? "Residential" : "Commercial";
            System.out.printf("%-12d | %-20s | %-15s | %-14d | Rs. %-10.2f\n", 
                consumer.getId(), 
                consumer.getName(), 
                typeStr,
                consumer.getUnitsConsumed(), 
                consumer.calculateBill()
            );
        }
        System.out.println("=================================================================================");

        sc.close();
    }

    private static int readValidInt(String prompt, int min, int max) {
        int value;
        while (true) {
            System.out.print(prompt);
            try {
                value = sc.nextInt();
                sc.nextLine(); // Clear scanner buffer cleanly
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("Input out of range! Please enter a value between %d and %d.\n", min, max);
            } catch (InputMismatchException e) {
                System.out.println("Invalid entry! Please enter a valid numerical whole number.");
                sc.nextLine(); // Clear bad buffer string completely
            }
        }
    }
}
