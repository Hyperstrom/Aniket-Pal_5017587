import java.util.Scanner;

public class UserInterface {
    private Scanner scanner;
    private DataStorage dataStorage;
    private ForecastCalculator calculator;

    public UserInterface() {
        this.scanner = new Scanner(System.in);
        this.dataStorage = new DataStorage();
        this.calculator = new ForecastCalculator();
    }

    public void start() {
        System.out.println("Welcome to Financial Forecasting!");
        runMenu();
    }

    private void runMenu() {
        displayMenu();
        int choice = getValidChoice(1, 4);
        processChoice(choice);
    }

    private void displayMenu() {
        System.out.println("\n1. Enter new data");
        System.out.println("2. View stored data");
        System.out.println("3. Calculate future value");
        System.out.println("4. Exit");
        System.out.print("Choose an option: ");
    }

    private int getValidChoice(int min, int max) {
        int choice = scanner.nextInt();
        if (choice < min || choice > max) {
            System.out.println("Invalid option. Please try again.");
            return getValidChoice(min, max);
        }
        return choice;
    }

    private void processChoice(int choice) {
        switch (choice) {
            case 1:
                enterNewData();
                break;
            case 2:
                viewStoredData();
                break;
            case 3:
                calculateFutureValue();
                break;
            case 4:
                exit();
                return;
        }
        runMenu();
    }

    private void enterNewData() {
        System.out.print("Enter initial value: ");
        double initialValue = scanner.nextDouble();
        System.out.print("Enter growth rate (as decimal): ");
        double growthRate = scanner.nextDouble();
        dataStorage.addData(initialValue, growthRate);
    }

    private void viewStoredData() {
        dataStorage.displayData();
    }

    private void calculateFutureValue() {
        if (dataStorage.hasData()) {
            System.out.print("Enter number of years to forecast: ");
            int years = scanner.nextInt();
            double result = calculator.calculateFutureValue(
                dataStorage.getLatestValue(), dataStorage.getLatestGrowthRate(), years);
            System.out.printf("Forecasted value after %d years: %.2f%n", years, result);
        } else {
            System.out.println("No data available. Please enter data first.");
        }
    }

    private void exit() {
        System.out.println("Thank you for using Financial Forecasting. Goodbye!");
        scanner.close();
    }
} 
