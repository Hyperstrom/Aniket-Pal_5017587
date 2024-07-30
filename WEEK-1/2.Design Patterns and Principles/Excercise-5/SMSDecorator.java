public class SMSDecorator implements Notifier {
    private Notifier wrappedNotifier;

    public SMSDecorator(Notifier notifier) {
        this.wrappedNotifier = notifier;
    }

    @Override
    public void send(String message, Receiver receiver) {
        wrappedNotifier.send(message, receiver);
        if (receiver.getPhoneNumber() != null && !receiver.getPhoneNumber().isEmpty()) {
            System.out.println("Sending SMS to " + receiver.getPhoneNumber() + ": " + message);
        } else {
            System.out.println("Phone number not provided for " + receiver.getName());
        }
    }
}