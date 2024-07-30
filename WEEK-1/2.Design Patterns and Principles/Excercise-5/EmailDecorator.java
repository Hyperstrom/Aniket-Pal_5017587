public class EmailDecorator implements Notifier {
    private Notifier wrappedNotifier;

    public EmailDecorator(Notifier notifier) {
        this.wrappedNotifier = notifier;
    }

    @Override
    public void send(String message, Receiver receiver) {
        wrappedNotifier.send(message, receiver);
        if (receiver.getEmail() != null && !receiver.getEmail().isEmpty()) {
            System.out.println("Sending Email to " + receiver.getEmail() + ": " + message);
        } else {
            System.out.println("Email address not provided for " + receiver.getName());
        }
    }
}