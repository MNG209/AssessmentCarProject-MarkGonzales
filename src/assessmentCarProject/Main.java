//Mark Gonzales
//CPSC-39-12704
//10-13-26

package assessmentCarProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        // Load complete CSV dataset
        ArrayList<Car> cars = loadCars("Car_Data.csv");

        // Make sure at least 2000 cars were loaded
        if (cars.size() < 2000) {

            System.out.println("Not enough valid cars loaded.");
            input.close();
            return;
        }

        // Create working list of first 2000 cars
        ArrayList<Car> working =
                new ArrayList<>(cars.subList(0, 2000));

        System.out.println("Working list contains "
                + working.size() + " cars.");

        boolean sorted = false;
        boolean running = true;

        // Keep displaying menu until user exits
        while (running) {

            System.out.println("\n===== CAR BRAND FINDER =====");
            System.out.println("1. Sort cars by Brand");
            System.out.println("2. Search for cars by Brand");
            System.out.println("3. Display statistics");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            String choice = input.nextLine();

            switch (choice) {

                case "1":

                    // Sort cars alphabetically by brand
                    insertionSort(working);
                    sorted = true;

                    System.out.println("\nCars sorted by Brand.");
                    System.out.println("First 10 cars:");

                    for (int i = 0; i < 10; i++) {

                        System.out.println(working.get(i));
                    }

                    break;

                case "2":

                    // Automatically sort before binary search
                    if (!sorted) {

                        insertionSort(working);
                        sorted = true;

                        System.out.println("Cars automatically sorted.");
                    }

                    System.out.print("Enter Brand: ");

                    String brand = input.nextLine().trim();

                    searchBrand(working, brand);

                    break;

                case "3":

                    // Display average mileage and fuel counts
                    displayStatistics(working);

                    break;

                case "4":

                    System.out.println("Exiting program.");
                    running = false;

                    break;

                default:

                    System.out.println("Invalid choice. Try again.");
            }
        }

        input.close();
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

    // Sorts cars alphabetically by brand using insertion sort
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

    // Displays average mileage and fuel type counts
    public static void displayStatistics(ArrayList<Car> working) {

        double totalMileage = 0;

        ArrayList<String> fuelTypes = new ArrayList<>();
        ArrayList<Integer> fuelCounts = new ArrayList<>();

        // Calculate total mileage and count fuel types
        for (Car car : working) {

            totalMileage += car.getMileage();

            String fuel = car.getFuelType();

            int index = fuelTypes.indexOf(fuel);

            if (index == -1) {

                fuelTypes.add(fuel);
                fuelCounts.add(1);

            } else {

                fuelCounts.set(index, fuelCounts.get(index) + 1);
            }
        }

        // Calculate average mileage
        double average = totalMileage / working.size();

        System.out.printf("Average mileage: %.2f km/l%n",
                average);

        System.out.println("\nCars by fuel type:");

        // Display number of cars for each fuel type
        for (int i = 0; i < fuelTypes.size(); i++) {

            System.out.println(fuelTypes.get(i)
                    + ": " + fuelCounts.get(i));
        }
    }
}