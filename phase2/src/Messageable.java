/*Potentially delete later */

/**
 * Represents an Interface for determining the type of users, sending and receiving messages among users.
 * @author Tanya Thaker
 * @version 1.0
 */

public interface Messageable {
    char userType();
    void sendMessage(User who, String message);
    void receiveMessage(User who, String message);

}
