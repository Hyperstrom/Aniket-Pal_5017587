import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// CustomerRepository interface
interface CustomerRepository {
    Customer findCustomerById(int id);
}

// CustomerRepositoryImpl class
class CustomerRepositoryImpl implements CustomerRepository {
    private Map<Integer, Customer> customers = new HashMap<>();

    public CustomerRepositoryImpl() {
        // Initialize with some dummy data
        customers.put(1, new Customer(1, "Alice"));
        customers.put(2, new Customer(2, "Bob"));
        customers.put(3, new Customer(3, "Charlie"));
    }

    @Override
    public Customer findCustomerById(int id) {
        return customers.get(id);
    }
}

// Customer class
class Customer {
    private int id;
    private String name;

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Customer{id=" + id + ", name='" + name + "'}";
    }
}

// CustomerService class
class CustomerService {
    private CustomerRepository repository;

    // Constructor injection
    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer findCustomer(int id) {
        return repository.findCustomerById(id);
    }
}

// Main class
public class DependencyInjectionExample {
    public static void main(String[] args) {
        // Create repository
        CustomerRepository repository = new CustomerRepositoryImpl();

        // Create service with injected repository
        CustomerService service = new CustomerService(repository);

        // User input
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.print("Enter customer ID to search (or 'exit' to quit): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            try {
                int customerId = Integer.parseInt(input);
                Customer customer = service.findCustomer(customerId);

                if (customer != null) {
                    System.out.println("Found customer: " + customer);
                } else {
                    System.out.println("Customer not found with ID: " + customerId);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid customer ID.");
            }

            System.out.println();
        }

        scanner.close();
        System.out.println("Thank you for using the Customer Management System!");
    }
}