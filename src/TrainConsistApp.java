import java.util.*;
import java.util.stream.Collectors;

public class TrainConsistApp {

    public static void main(String[] args) {

        System.out.println("Train Consist Management System - UC20");

        // Empty bogie list
        String[] bogieIds = {};  // empty array

        String searchKey = "BG101";

        try {
            searchBogie(bogieIds, searchKey);
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\nProgram continues safely...");
    }

    // Search method with validation
    public static void searchBogie(String[] bogieIds, String key) {

        // Defensive check
        if (bogieIds.length == 0) {
            throw new IllegalStateException("No bogies available for search");
        }

        // Linear search
        boolean found = false;

        for (String id : bogieIds) {
            if (id.equals(key)) {
                found = true;
                break;
            }
        }

        if (found) {
            System.out.println("Bogie found: " + key);
        } else {
            System.out.println("Bogie NOT found");
        }
    }
}