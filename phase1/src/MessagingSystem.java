import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles the messaging functionality of Users.
 * @author Tanya Thaker
 * @version 1.0
 */

public class MessagingSystem {

    UserManager um = new UserManager();

    public MessagingSystem(){};

    public String[] readMessage() {
        Scanner scanner = new Scanner(System.in);
        String[] record;
        record = scanner.nextLine().split("\n");
        scanner.close();
        return record;
    }

    public String readRecipient() {
        Scanner scanner = new Scanner(System.in);
        String[] record;
        record = scanner.nextLine().split("\n");
        scanner.close();
        return record.toString();
    }

    public void sendOnemessage(User from, User to, String message){
        message = readMessage().toString();
        String recipient = readRecipient();
        um.message(from, um.findUser(recipient), message);
    }

    /* call only if users says "All"*/
    public void sendBunchmessage(User from ,Talk[] talks, String message){
        message = readMessage().toString();
        for(Talk t: talks){
            for (User u: t.getEvent().getAttendees()){
                um.message(from, u, message);
            }
        }
    }


}
