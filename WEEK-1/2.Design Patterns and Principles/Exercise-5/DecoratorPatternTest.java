import java.util.Scanner;

public class DecoratorPatternTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nNotification System");
            System.out.println("1. Send Notification");
            System.out.println("2. Exit");
            System.out.print("Enter your choice (1-2): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 2) {
                System.out.println("Exiting the program. Goodbye!");
                break;
            }

            if (choice != 1) {
                System.out.println("Invalid choice. Please try again.");
                continue;
            }

            // Get receiver details
            System.out.print("Enter receiver's name: ");
            String name = scanner.nextLine();
            System.out.print("Enter receiver's email (or press Enter to skip): ");
            String email = scanner.nextLine();
            System.out.print("Enter receiver's phone number (or press Enter to skip): ");
            String phoneNumber = scanner.nextLine();

            Receiver receiver = new Receiver(name, email, phoneNumber);

            Notifier notifier = new BaseNotifier();

            System.out.println("Choose notification channels:");
            System.out.println("1. Email");
            System.out.println("2. SMS");
            System.out.println("3. Slack");
            System.out.print("Enter your choices (e.g., 123 for all, 12 for Email and SMS): ");
            String channels = scanner.nextLine();

            if (channels.contains("1")) {
                notifier = new EmailDecorator(notifier);
            }
            if (channels.contains("2")) {
                notifier = new SMSDecorator(notifier);
            }
            if (channels.contains("3")) {
                notifier = new SlackDecorator(notifier);
            }

            System.out.print("Enter your message: ");
            String message = scanner.nextLine();

            notifier.send(message, receiver);
        }

        scanner.close();
    }
}