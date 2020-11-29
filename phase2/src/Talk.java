import java.time.LocalDate;
import java.time.LocalTime;

public class Talk extends Event{
    private String speakerEmail;

    /**
     * Creates a Talk event Event with the specified name, description, start time, end time, date of event, event capacity
     * and indicator if the event is VIP only.
     *
     * @param name             the name of the specified User
     * @param eventDescription the description of the event
     * @param startTime        the time when the event starts
     * @param endTime          the time when the event ends
     * @param eventDate        the date when the event is happening
     * @param attendeeCapacity the maximum number of attendees that can participate in the event
     * @param vipOnly          indicates if  the event is only for VIP attendees or not
     */
    public Talk(String name, String eventDescription, LocalTime startTime, LocalTime endTime, LocalDate eventDate,
                int attendeeCapacity, boolean vipOnly) {
        super(name, eventDescription, startTime, endTime, eventDate, attendeeCapacity, vipOnly);
        this.speakerEmail = null;
    }
    /* getters */

    /**
     * gets the email of the speaker of this talk.
     * @return the email of the speaker.
     */
    public String getSpeakerEmail() {return this.speakerEmail;}

    /* setters */

    /**
     * sets the email of the speaker of this talk.
     * @param speaker the speaker of this talk
     */
    public void setSpeaker(Speaker speaker) {
        this.speakerEmail = speaker.getEmail();
    }

}