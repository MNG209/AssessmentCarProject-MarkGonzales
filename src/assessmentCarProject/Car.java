//Mark Gonzales 
//CPSC-39-12704
//10-13-26

package assessmentCarProject;

public class Car {
	// Private fields
    private String carID;
    private String brand;
    private String model;
    private int year;
    private String fuelType;
    private String color;
    private double mileage;

    // Constructor
    public Car(String carID, String brand, String model,
               int year, String fuelType, String color,
               double mileage) {

        this.carID = carID;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.fuelType = fuelType;
        this.color = color;
        this.mileage = mileage;
    }

    // Getter methods
    public String getCarID() {
        return carID;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getColor() {
        return color;
    }

    public double getMileage() {
        return mileage;
    }

    // Displays car information
    @Override
    public String toString() {

        return "ID: " + carID
                + " | Brand: " + brand
                + " | Model: " + model
                + " | Year: " + year
                + " | Fuel: " + fuelType
                + " | Color: " + color
                + " | Mileage: " + mileage;
    }
}
