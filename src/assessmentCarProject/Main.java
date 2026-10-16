//Mark Gonzales 
//CPSC-39-12704
//10-13-26

package assessmentCarProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        // Load all cars from the CSV
        ArrayList<Car> cars = loadCars("Car_Data.csv");

        // Create working list of first 2000 cars
        ArrayList<Car> working =
                new ArrayList<>(cars.subList(0, 2000));

        System.out.println("Working list contains "
                + working.size() + " cars.");
        
        // Test insertion sort
        insertionSort(working);

        System.out.println("\nFirst 10 sorted cars:");

        for (int i = 0; i < 10; i++) {
            System.out.println(working.get(i));
        }

        // Test binary search
        System.out.println("\nSearching for Toyota:");
        searchBrand(working, "Toyota");

        // Test a brand that does not exist
        System.out.println("\nSearching for nonexistent brand:");
        searchBrand(working, "NonexistentBrand");
    }

    // Reads the CSV file and creates Car objects
    public static ArrayList<Car> loadCars(String filename) {

        ArrayList<Car> cars = new ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(filename))) {

            String line;

            // Skip CSV header
            reader.readLine();

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",", -1);

                if (data.length != 7) {
                    continue;
                }

                try {

                	String id = data[0].trim();
                    String brand = data[1].trim();
                    String model = data[2].trim();
                    int year = Integer.parseInt(data[3].trim());
                    String fuel = data[4].trim();
                    String color = data[5].trim();
                    double mileage =
                            Double.parseDouble(data[6].trim());

                    Car car = new Car(id, brand, model,
                            year, fuel, color, mileage);

                    cars.add(car);

                } catch (NumberFormatException e) {

                    // Skip malformed numeric data
                    continue;
                }
            }

        } catch (IOException e) {

            System.out.println("Error reading file: "
                    + e.getMessage());
        }

        System.out.println("Total cars loaded: " + cars.size());

        return cars;
    }
    
    // Sorts cars alphabetically by brand
    public static void insertionSort(ArrayList<Car> working) {

        for (int i = 1; i < working.size(); i++) {

            Car key = working.get(i);

            int j = i - 1;

            while (j >= 0 &&
                    working.get(j).getBrand()
                    .compareToIgnoreCase(key.getBrand()) > 0) {

                working.set(j + 1, working.get(j));

                j--;
            }

            working.set(j + 1, key);
        }
    }

    // Searches for a brand using binary search
    public static int binarySearch(
            ArrayList<Car> working, String target) {

        int low = 0;
        int high = working.size() - 1;

        while (low <= high) {

            int mid = low + (high - low) / 2;

            int comparison = working.get(mid).getBrand()
                    .compareToIgnoreCase(target);

            if (comparison == 0) {

                return mid;

            } else if (comparison < 0) {

                low = mid + 1;

            } else {

                high = mid - 1;
            }
        }

        return -1;
    }

    // Displays all cars matching a brand
    public static void searchBrand(
            ArrayList<Car> working, String brand) {

        int index = binarySearch(working, brand);

        if (index == -1) {

            System.out.println("No cars found for brand: "
                    + brand);

            return;
        }

        // Move backward to the first matching car
        int start = index;

        while (start > 0 &&
                working.get(start - 1).getBrand()
                .equalsIgnoreCase(brand)) {

            start--;
        }

        // Display all matching cars
        int count = 0;

        while (start < working.size() &&
                working.get(start).getBrand()
                .equalsIgnoreCase(brand)) {

            System.out.println(working.get(start));

            count++;
            start++;
        }

        System.out.println("Total matching cars: " + count);
    }
}

