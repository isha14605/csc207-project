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


    /**
     * Sends an Attendee message
     *
     * @return true if the message was sent successfully
     */
    public boolean sendAttendeeMessage(User from, User to, String message) {
        return um.message(from, to, message);
    }


    /**
     * Sends an Speaker message to all Attendees signed up for an event
     */
    public void sendMessageSpeaker(Speaker from, Event event_name, String message) {
        for(Attendee u: event_name.getAttendees()){
            um.message(from, u, message);
        }
    }


    /**
     * Sends an Organizer message to all the Speakers or all the Attendees signed up for an event
     */
    public void sendMessageOrganizer(Organizer from, String to, int event_id, String message) {
        if (to.equals("Attendee")){
            Event event = em.find_event(event_id);
            for(Attendee a: event.getAttendees()){
                um.message(from, a, message);
            }

        } else if (to.equals("Speaker")) {
            Event event = em.find_event(event_id);
            ArrayList<Talk> talks = new ArrayList<Talk>();
            for (Talk t: event.getTalks()){
                talks.add(t);
            }
            ArrayList<Speaker> speakers = new ArrayList<Speaker>();
            for (Talk s: talks){
                speakers.add(s.getSpeaker());
            }
            for (Speaker s: speakers){
                um.message(from, s ,message);
            }
        }

    }
}

