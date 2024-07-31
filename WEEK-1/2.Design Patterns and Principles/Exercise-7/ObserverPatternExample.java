import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Observer Interface
interface Observer {
    void update(double stockPrice);
}

// Subject Interface
interface Stock {
    void register(Observer o);
    void deregister(Observer o);
    void notifyObservers();
}

// Concrete Subject
class StockMarket implements Stock {
    private List<Observer> observers;
    private double stockPrice;

    public StockMarket() {
        observers = new ArrayList<>();
    }

    public void setStockPrice(double stockPrice) {
        this.stockPrice = stockPrice;
        notifyObservers();
    }

    public double getStockPrice() {
        return stockPrice;
    }

    @Override
    public void register(Observer o) {
        observers.add(o);
    }

    @Override
    public void deregister(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(stockPrice);
        }
    }
}

// Concrete Observer: MobileApp
class MobileApp implements Observer {
    private String name;

    public MobileApp(String name) {
        this.name = name;
    }

    @Override
    public void update(double stockPrice) {
        System.out.println(name + " MobileApp: Stock price updated to " + stockPrice);
    }
}

// Concrete Observer: WebApp
class WebApp implements Observer {
    private String name;

    public WebApp(String name) {
        this.name = name;
    }

    @Override
    public void update(double stockPrice) {
        System.out.println(name + " WebApp: Stock price updated to " + stockPrice);
    }
}

// Main class to test the Observer Pattern
public class ObserverPatternExample {
    public static void main(String[] args) {
        StockMarket stockMarket = new StockMarket();
        Scanner scanner = new Scanner(System.in);

        // Registering observers
        System.out.println("Enter the number of MobileApp observers: ");
        int numMobileApps = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (int i = 0; i < numMobileApps; i++) {
            System.out.println("Enter name for MobileApp observer " + (i + 1) + ": ");
            String name = scanner.nextLine();
            Observer mobileApp = new MobileApp(name);
            stockMarket.register(mobileApp);
        }

        System.out.println("Enter the number of WebApp observers: ");
        int numWebApps = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (int i = 0; i < numWebApps; i++) {
            System.out.println("Enter name for WebApp observer " + (i + 1) + ": ");
            String name = scanner.nextLine();
            Observer webApp = new WebApp(name);
            stockMarket.register(webApp);
        }

        // Updating stock prices
        while (true) {
            System.out.println("Enter new stock price (or 'exit' to quit): ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }
            try {
                double newPrice = Double.parseDouble(input);
                stockMarket.setStockPrice(newPrice);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid stock price.");
            }
        }

        scanner.close();
    }
}
