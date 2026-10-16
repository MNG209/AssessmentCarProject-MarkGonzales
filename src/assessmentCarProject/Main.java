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
}

