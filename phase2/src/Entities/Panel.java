package Entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Panel extends Event implements Serializable {
    private ArrayList<String> speakerEmails;

    /**
     * Creates a Entities.Panel event with the specified name, description, start time, end time, date of event, event capacity
     * and indicator if the event is Entities.VIP only. A Entities.Panel can have multiple speakers.
     *
     * @param name             the name of the specified Entities.User
     * @param eventDescription the description of the event
     * @param startTime        the time when the event starts
     * @param endTime          the time when the event ends
     * @param eventDate        the date when the event is happening
     * @param attendeeCapacity the maximum number of attendees that can participate in the event
     * @param vipOnly          indicates if  the event is only for Entities.VIP attendees or not
     */
    public Panel(String name, String eventDescription, LocalTime startTime, LocalTime endTime, LocalDate eventDate,
                 int attendeeCapacity, boolean vipOnly) {
        super(name, eventDescription, startTime, endTime, eventDate, attendeeCapacity, vipOnly);
        this.speakerEmails = new ArrayList<String>();
    }

    @Override
    public String eventType() {
        return "Panel";
    }

    @Override
    public void setSpeaker(String  speakerEmail) {
        if(this.speakerEmails.contains(speakerEmail)) {
            this.speakerEmails.add(speakerEmail);
        }
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
     * @param email is the email of a speaker of this panel.
     */
    public void addSpeaker(String email) {
        speakerEmails.add(email);
    }

    /**
     * removes the email of the speakers of this panel.
     */
    public void removeSpeakers() {
        this.speakerEmails = null;
    }
}
