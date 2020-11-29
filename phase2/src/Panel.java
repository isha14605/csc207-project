import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Panel extends Event{
    private ArrayList<String> speakerEmails;

    /**
     * Creates a Panel event with the specified name, description, start time, end time, date of event, event capacity
     * and indicator if the event is VIP only. A Panel can have multiple speakers.
     *
     * @param name             the name of the specified User
     * @param eventDescription the description of the event
     * @param startTime        the time when the event starts
     * @param endTime          the time when the event ends
     * @param eventDate        the date when the event is happening
     * @param attendeeCapacity the maximum number of attendees that can participate in the event
     * @param vipOnly          indicates if  the event is only for VIP attendees or not
     */
    public Panel(String name, String eventDescription, LocalTime startTime, LocalTime endTime, LocalDate eventDate,
                 int attendeeCapacity, boolean vipOnly) {
        super(name, eventDescription, startTime, endTime, eventDate, attendeeCapacity, vipOnly);
        this.speakerEmails = new ArrayList<String>();
    }

    /* getters */

    /**
     * Returns an ArrayList of String objects that are emails of Speakers of this panel.
     * @return an ArrayList of String objects that are emails of Speakers of this panel.
     */
    public ArrayList<String> getSpeakerEmails() {return this.speakerEmails;}

    /* setters */

    /**
     * adds the email of a speaker of this panel.
     * @param speaker a speaker of this panel.
     */
    public void addSpeaker(Speaker speaker) {
        speakerEmails.add(speaker.getEmail());
    }
}
