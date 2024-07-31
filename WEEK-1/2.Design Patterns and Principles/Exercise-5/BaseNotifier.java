public class BaseNotifier implements Notifier {
    @Override
    public void send(String message, Receiver receiver) {
        System.out.println("Base Notification to " + receiver.getName() + ": " + message);
    }
}