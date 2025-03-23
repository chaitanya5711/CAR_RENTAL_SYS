package hmm;
import java.util.*;
public class CarRental {

    // 1. Car Class
    public static class Car {
        private String carBrand;
        private int id;
        private int basePrice;
        private int model;
        private boolean isAvailable;  

        public Car(String carBrand, int id, int basePrice, int model) {
            this.carBrand = carBrand;
            this.id = id;
            this.basePrice = basePrice;
            this.model = model;
            this.isAvailable = true;
        }

        
     //these all are the getter methos through this methods private variables are accesible
        public String getCarBrand() {
            return carBrand;
        }

        public int getId() {
            return id;
        }

        public int getModel() {
            return model;
        }

        public boolean isAvailable() {
            return isAvailable;
        }

        //to calculate the rent
        public double calculateRentalCost(int rentalDays) {
            return basePrice * rentalDays;
        }

        public void rent() {
            isAvailable = false;
        }

        public void returnCar() {
            isAvailable = true;
        }
    }

    // 2. Customer Class
    public static class Customer {
        private String name;
        private int id;

        public Customer(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        
        public String toString() {
            return "Customer ID: " + id + ", Name: " + name;
        }
    }

    // 3. Rental Class
    public static class Rental {
        private Car car;
        private Customer customer;
        private int rentalDays;

        public Rental(Car car, Customer customer, int rentalDays) {
            this.car = car;
            this.customer = customer;
            this.rentalDays = rentalDays;
        }

        public Car getCar() {
            return car;
        }

        public Customer getCustomer() {
            return customer;
        }

        public int getRentalDays() {
            return rentalDays;
        }
    }

    // 4. Car Rental System
    private List<Car> cars = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private List<Rental> rentals = new ArrayList<>();

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int rentalDays) {
        rentals.add(new Rental(car, customer, rentalDays));
        car.rent();
    }

    public void returnCar(Car car) {
        for (Rental rental : rentals) {
            if (rental.getCar().equals(car)) {
                car.returnCar();
                rentals.remove(rental);
                break;
            }
        }
    }

    
    //menu class
    public void menu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Car Rental System =====");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            if (choice == 1) {
                System.out.println("\n== Rent a Car ==\n");
                System.out.print("Enter your name: ");
                String customerName = scanner.nextLine();

                System.out.println("\nAvailable Cars:");
                for (Car car : cars) {
                    if (car.isAvailable()) {
                        System.out.println(car.getId() + " - " + car.getCarBrand() + " " + car.getModel());
                    }
                }

                System.out.print("\nEnter the car ID you want to rent: ");
                int carId = scanner.nextInt();

                System.out.print("Enter the number of days for rental: ");
                int rentalDays = scanner.nextInt();
                scanner.nextLine(); 

                Customer newCustomer = new Customer(customerName, customers.size() + 1);
                addCustomer(newCustomer);

                Car selectedCar = null;
                for (Car car : cars) {
                    if (car.getId() == carId && car.isAvailable()) {
                        selectedCar = car;
                        break;
                    }
                }

                if (selectedCar != null) {
                    double totalPrice = selectedCar.calculateRentalCost(rentalDays);
                    System.out.println("\n== Rental Information ==\n");
                    System.out.println("Customer ID: " + newCustomer.getId());
                    System.out.println("Customer Name: " + newCustomer.getName());
                    System.out.println("Car: " + selectedCar.getCarBrand() + " " + selectedCar.getModel());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.printf("Total Price: $%.2f%n", totalPrice);

                    System.out.print("\nConfirm rental (Y/N): ");
                    String confirm = scanner.nextLine();

                    if (confirm.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.println("\nCar rented successfully.");
                    } else {
                        System.out.println("\nRental canceled.");
                    }
                } else {
                    System.out.println("\nInvalid car selection or car not available for rent.");
                }
            } else if (choice == 2) {
                System.out.println("\n== Return a Car ==\n");
                System.out.print("Enter the car ID you want to return: ");
                int carId = scanner.nextInt();
                scanner.nextLine(); 

                Car carToReturn = null;
                for (Car car : cars) {
                    if (car.getId() == carId && !car.isAvailable()) {
                        carToReturn = car;
                        break;
                    }
                }

                if (carToReturn != null) {
                    returnCar(carToReturn);
                    System.out.println("Car returned successfully.");
                } else {
                    System.out.println("Invalid car ID or car is not rented.");
                }
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }

        System.out.println("\nThank you for using the Car Rental System!");
        scanner.close();
    }

    public static void main(String[] args) {
        CarRental rentalSystem = new CarRental();

        Car car1 = new Car("Toyota", 1, 60, 2021);
        Car car2 = new Car("Honda", 2, 70, 2022);
        Car car3 = new Car("Mahindra", 3, 150, 2023);
        
        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);

        rentalSystem.menu();
    }
}
