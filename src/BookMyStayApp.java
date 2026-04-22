import java.io.*;
import java.util.*;

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Welcome to the Hotel Booking Management System");

        HashMap<String, Integer> inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);

        List<String> bookingHistory = new ArrayList<>();
        bookingHistory.add("SingleRoom-1");
        bookingHistory.add("DoubleRoom-2");

        // Save data to file
        saveData(inventory, bookingHistory);

        // Clear data (simulate restart)
        inventory.clear();
        bookingHistory.clear();

        System.out.println("\nSystem Restarting...\n");

        // Load data from file
        loadData(inventory, bookingHistory);

        // Display restored data
        System.out.println("--- Restored Inventory ---");
        for (String type : inventory.keySet()) {
            System.out.println(type + " Available: " + inventory.get(type));
        }

        System.out.println("\n--- Restored Booking History ---");
        for (String booking : bookingHistory) {
            System.out.println(booking);
        }
    }

    // Save data
    public static void saveData(HashMap<String, Integer> inventory, List<String> history) {

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt"));

            // Save inventory
            for (String key : inventory.keySet()) {
                writer.write("INV:" + key + ":" + inventory.get(key));
                writer.newLine();
            }

            // Save bookings
            for (String booking : history) {
                writer.write("BOOK:" + booking);
                writer.newLine();
            }

            writer.close();

            System.out.println("Data saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    // Load data
    public static void loadData(HashMap<String, Integer> inventory, List<String> history) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("data.txt"));

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.startsWith("INV:")) {
                    String[] parts = line.split(":");
                    inventory.put(parts[1], Integer.parseInt(parts[2]));
                }

                if (line.startsWith("BOOK:")) {
                    String[] parts = line.split(":");
                    history.add(parts[1]);
                }
            }

            reader.close();

            System.out.println("Data loaded successfully.");

        } catch (IOException e) {
            System.out.println("No previous data found.");
        }
    }
}