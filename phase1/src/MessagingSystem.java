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
     * Sends an Entities.Attendee message
     * @param from the Entities.User sending the message
     * @param to the Entities.User receiving the message
     * @param message content of the message to be sent
     * @return true if the message was sent successfully
     */
    public boolean sendAttendeeMessage(User from, User to, String message) {
        return um.message(from, to, message);
    }


    /**
     * Sends a Entities.Speaker message to all Users signed up for an event
     * @param from the Entities.Speaker sending the message
     * @param event_name name of the event whose users are being messaged
     * @param message content of the message to be sent
     */
    public void sendMessageSpeaker(Speaker from, Event event_name, String message) {
        for(Attendee u: event_name.getAttendees()){
            um.message(from, u, message);
        }
    }


    /**
     * Sends an Entities.Organizer message to all the Speakers or all the Attendees signed up for an event
     * @param from the Entities.Organizer sending the message
     * @param to type of the Users receiving the message
     * @param event_id id of the event whose users are being messaged
     * @param message content of the message to be sent
     */
    public void sendMessageOrganizer(Organizer from, String to, int event_id, String message) {
        if (to.equals("Entities.Attendee")){
            Event event = em.find_event(event_id);
            for(Attendee a: event.getAttendees()){
                um.message(from, a, message);
            }

        } else if (to.equals("Entities.Speaker")) {
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

