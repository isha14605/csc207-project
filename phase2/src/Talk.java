import java.io.Serializable;
import java.time.LocalTime;

/**
 * Represents a talk.
 *
 * @version 1.0
 */
public class Talk implements Serializable {
    private LocalTime startTime;
    private LocalTime endTime;
    private String speakerEmail;
    private Event event;
    private int id;

   /* Call this constructor only if there's no overlapping between talks of a particular event and
   this talk happens 9-5. */
    /**
     * Creates a talk
     * @param startTime start time of the talk
     * @param endTime end time of the talk
     * @param event event this talk belongs to
     */
    public Talk(LocalTime startTime, LocalTime endTime, Event event)
    {
        this.startTime = startTime;
        this.endTime = endTime;
        this.event = event;
        this.speakerEmail = null;
    }

    /* getters */
    /**
     * gets the end time of this talk.
     * @return the end time.
     */
    public LocalTime getEndTime() {return this.endTime;}
    /**
     * gets the start time of this talk.
     * @return the start time.
     */
    public LocalTime getStartTime() {return this.startTime;}
    /**
     * gets the email of the speaker of this talk.
     * @return the email of the speaker.
     */
    public String getSpeakerEmail() {return this.speakerEmail;}
    /**
     * gets the event of this talk.
     * @return the event.
     */
    public Event getEvent() {return this.event;}

    /**
     * gets the id of this talk
     */
    public int getId() {
        return id;
    }

    /**
     * sets the email of the speaker of this talk.
     * @param speaker the speaker of this talk
     */
    public void setSpeaker(Speaker speaker) {
        this.speakerEmail = speaker.getEmail();
    }

    /**
     * sets the id of this talk.
     * @param id the speaker of this talk
     */
    public void setId(int id){
        this.id = id;
    }
}
