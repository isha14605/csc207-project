package Entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class Talk extends Event {
    private String speakerEmail;

    /**
     * Creates a Entities.Talk event Entities.Event with the specified name, description, start time, end time, date of event, event capacity
     * and indicator if the event is Entities.VIP only. A talk can have one speaker.
     *
     * @param name             the name of the specified Entities.User
     * @param eventDescription the description of the event
     * @param startTime        the time when the event starts
     * @param endTime          the time when the event ends
     * @param eventDate        the date when the event is happening
     * @param attendeeCapacity the maximum number of attendees that can participate in the event
     * @param vipOnly          indicates if  the event is only for Entities.VIP attendees or not
     */
    public Talk(String name, String eventDescription, LocalTime startTime, LocalTime endTime, LocalDate eventDate,
                int attendeeCapacity, boolean vipOnly) {
        super(name, eventDescription, startTime, endTime, eventDate, attendeeCapacity, vipOnly);
        this.speakerEmail = null;
    }

    @Override
    public String eventType() {
        return "Talk";
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
     * @param speakerName the speaker of this talk
     */
    @Override
    public void setSpeaker(String speakerName) {
        this.speakerEmail = speakerName;
    }

    /**
     * removes the email of the speaker of this talk.
     */
    public void removeSpeaker() {
        this.speakerEmail = null;
    }

}
