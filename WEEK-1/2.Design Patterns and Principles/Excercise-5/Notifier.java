public interface Notifier {
    void send(String message, Receiver receiver);
}