import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Party extends Event {
    private ArrayList<String> activities;

    /**
     * Creates a Party Event with the specified name, description, start time, end time, date of event, event capacity
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
    public Party(String name, String eventDescription, LocalTime startTime, LocalTime endTime, LocalDate eventDate,
                 int attendeeCapacity, boolean vipOnly) {
        super(name, eventDescription, startTime, endTime, eventDate, attendeeCapacity, vipOnly);
        this.activities = new ArrayList<String>();

    }

    /* getters */

    /**
     * Returns an ArrayList of String objects that are activities in the party.
     * @return an ArrayList of String objects that are activities in the party.
     */
    public ArrayList<String> getActivities() {return this.activities;}

    /* setters */

    /**
     * adds an activity to this party.
     * @param activity an activity in this party.
     */
    public void addActivity(String activity) {
        activities.add(activity);
    }
}
