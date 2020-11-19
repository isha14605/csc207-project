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
    TalkManager tm = new TalkManager();

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

    public boolean sendAttendeeMessage(User from, User to, String message) {
        return um.message(from, to, message);
    }


    public void sendMessageSpeaker(Speaker from, ArrayList<String> talks, String message) {
        ArrayList<Event> events = new ArrayList<Event>();
        for (String s: talks){
            if (!(events.contains(tm.findTalk(s)))){
                events.add(tm.findTalk(s));
            }
        }
        for (Event e: events){
            for(User u: e.getAttendees()){
                um.message(from, u, message);
            }
        }
    }


    public void sendMessageOrganizer(Organizer from, String message) {
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

        } else {
            um.message(from, um.findUser(recipient.get(1)), message);
        }

    }
}

