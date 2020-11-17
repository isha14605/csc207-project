import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles the messaging functionality of Users.
 * @author Tanya Thaker
 * @version 1.0
 */

public class MessagingSystem {

    UserManager um = new UserManager();
    EventManager em = new EventManager();

    public MessagingSystem() {
    }

    ;

    /**
     * Returns a list of string of content of the message the user wants to send from the console
     *
     * @return a list of string of content of message.
     */
    public String[] readMessage() {
        Scanner scanner = new Scanner(System.in);
        String[] record;
        record = scanner.nextLine().split("\n");
        scanner.close();
        return record;
    }

    /**
     * Returns the recipient (i.e. who the user wants to send the message to) name from console
     *
     * @return a string of recipient name
     */
    public ArrayList<String> readRecipient() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> inputs = new ArrayList<String>();
        String record = scanner.nextLine();
        if (!(record.equals("All"))) {
            inputs.add(record);
            while (scanner.hasNextLine()) {
                record = scanner.nextLine();
                inputs.add(record);
            }
        }
        inputs.add("All");
        return inputs;
    }

    public void sendOnemessage(User from, String message) {
        message = readMessage().toString();
        ArrayList<String> recipient = readRecipient();
        um.message(from, um.findUser(recipient.get(0)), message);
    }


    public void sendmessage_speaker(Speaker from, String message) {
        message = readMessage().toString();
        ArrayList<String> recipient = readRecipient();
        ArrayList<Talk> talks;
        if (recipient.get(0).equals("All") && from.userType() == 'S') {
            talks = from.getTalksSpeaking();
            for (Talk t : talks) {
                for (User u : t.getEvent().getAttendees()) {
                    um.message(from, u, message);
                }
            }

        } else {
            sendOnemessage(from, message);
        }
    }


    public void sendmessage_organizer(Organizer from, String message) {
        message = readMessage().toString();
        ArrayList<String> recipient = readRecipient();
        if (recipient.get(2) == "All") {
            Event event = em.find_event(Integer.parseInt(recipient.get(1)));
            if (recipient.get(0) == "Speakers") {
                for (Talk t : event.getTalks()) {
                    um.message(from, t.getSpeaker(), message);

                }
            } else {
                for (Attendee a : event.getAttendees()) {
                    um.message(from, a, message);
                }
            }

        }

    }
}

