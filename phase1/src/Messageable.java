public interface Messageable {
    char userType();
    void sendMessage(User who, String message);
    void receiveMessage(User who, String message);

}
