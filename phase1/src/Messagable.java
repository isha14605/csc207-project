public interface Messagable {
    char userType();
    void send_message(User who, String message);
    void receive_message(User who, String message);

}
