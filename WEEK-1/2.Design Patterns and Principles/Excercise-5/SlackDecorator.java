public class SlackDecorator implements Notifier {
    private Notifier wrappedNotifier;

    public SlackDecorator(Notifier notifier) {
        this.wrappedNotifier = notifier;
    }

    @Override
    public void send(String message, Receiver receiver) {
        wrappedNotifier.send(message, receiver);
        System.out.println("Sending Slack message to " + receiver.getName() + ": " + message);
    }
}