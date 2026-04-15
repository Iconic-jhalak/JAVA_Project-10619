```java
class Consumer {
    private int id;
    private String name;
    private int unitsConsumed;

    // Constructor
    public Consumer(int id, String name, int unitsConsumed) {
        this.id = id;
        this.name = name;
        this.unitsConsumed = unitsConsumed;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getUnitsConsumed() {
        return unitsConsumed;
    }
}

class TariffCalculator {

    public double generateBill(Consumer consumer) {
        int units = consumer.getUnitsConsumed();
        double bill = 0;

        // Slab based calculation
        if (units <= 100) {
            bill = units * 1.5;
        } 
        else if (units <= 200) {
            bill = (100 * 1.5) + ((units - 100) * 2.5);
        } 
        else if (units <= 300) {
            bill = (100 * 1.5) + (100 * 2.5) + ((units - 200) * 4.0);
        } 
        else {
            bill = (100 * 1.5) + (100 * 2.5) + (100 * 4.0) + ((units - 300) * 6.0);
        }

        return bill;
    }
}

import java.util.Scanner;

public class project {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Input Consumer Details
        System.out.print("Enter Consumer ID: ");
        int id = sc.nextInt();
        sc.nextLine(); // consume newline

        System.out.print("Enter Consumer Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Units Consumed: ");
        int units = sc.nextInt();

        // Create Consumer Object
        Consumer consumer = new Consumer(id, name, units);

        // Create TariffCalculator Object
        TariffCalculator calculator = new TariffCalculator();

        // Generate Bill
        double totalBill = calculator.generateBill(consumer);

        // Display Bill Details
        System.out.println("\n------ Electricity Bill Details ------");
        System.out.println("Consumer ID      : " + consumer.getId());
        System.out.println("Consumer Name    : " + consumer.getName());
        System.out.println("Units Consumed   : " + consumer.getUnitsConsumed());
        System.out.println("Total Bill (Rs.) : " + totalBill);
        System.out.println("--------------------------------------");

        sc.close();
    }
}




```