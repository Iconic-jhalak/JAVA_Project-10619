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
    private static final double RATE_SLAB_1 = 1.5;
    private static final double RATE_SLAB_2 = 2.5;
    private static final double RATE_SLAB_3 = 4.0;
    private static final double RATE_SLAB_4 = 6.0;

    public double generateBill(Consumer consumer) {
        int units = consumer.getUnitsConsumed();
        
        if (units <= 100) return units * RATE_SLAB_1;
        if (units <= 200) return (100 * RATE_SLAB_1) + ((units - 100) * RATE_SLAB_2);
        if (units <= 300) return (100 * RATE_SLAB_1) + (100 * RATE_SLAB_2) + ((units - 200) * RATE_SLAB_3);
        
        return (100 * RATE_SLAB_1) + (100 * RATE_SLAB_2) + (100 * RATE_SLAB_3) + ((units - 300) * RATE_SLAB_4);
    }
}

public class ElectricityBillingSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TariffCalculator calculator = new TariffCalculator();

        System.out.print("Enter the number of consumers to process: ");
        int totalConsumers = sc.nextInt();
        
        // Initializing the Consumer object array
        Consumer[] consumers = new Consumer[totalConsumers];

        // Population loop for the array
        for (int i = 0; i < consumers.length; i++) {
            System.out.println("\n--- Entering Details for Consumer #" + (i + 1) + " ---");
            
            System.out.print("Enter Consumer ID: ");
            int id = sc.nextInt();
            sc.nextLine(); // Clear buffer

            System.out.print("Enter Consumer Name: ");
            String name = sc.nextLine().trim();

            System.out.print("Enter Units Consumed: ");
            int units = sc.nextInt();

            // Store object inside our array
            consumers[i] = new Consumer(id, name, units);
        }

        // Processing & Displaying the final Consolidated Report
        System.out.println("\n======================================================================");
        System.out.println("                      CONSOLIDATED ELECTRICITY BILLS                  ");
        System.out.println("======================================================================");
        System.out.printf("%-12s | %-22s | %-14s | %-12s\n", "Consumer ID", "Name", "Units Consumed", "Total Bill");
        System.out.println("----------------------------------------------------------------------");

        for (Consumer consumer : consumers) {
            double totalBill = calculator.generateBill(consumer);
            System.out.printf("%-12d | %-22s | %-14d | Rs. %-10.2f\n", 
                consumer.getId(), 
                consumer.getName(), 
                consumer.getUnitsConsumed(), 
                totalBill
            );
        }
        System.out.println("======================================================================");

        sc.close();
    }
}
