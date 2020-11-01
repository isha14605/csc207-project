import java.time.LocalDateTime;
import java.util.ArrayList;

public class Event {
    private static int num_of_events;
    private int event_id;
    private String name;
    private String event_description;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private ArrayList<Attendee> attendees;
    private ArrayList<Organizer> organizers;
    private ArrayList<Talk> talks;
    /*Speakers stored inside Talks? */

    public Event(String name, String event_description, LocalDateTime start_time, LocalDateTime end_time){
        num_of_events += 1;
        this.name = name;
        this.event_description = event_description;
        this.start_time = start_time;
        this.end_time = end_time;
        this.attendees = new ArrayList<Attendee>();
        this.organizers = new ArrayList<Organizer>();
        this.talks = new ArrayList<Talk>();
    }

    public String getName() {return name;}

    public String getEvent_description() {return event_description;}

    public int getEvent_id() {return event_id;}

    public LocalDateTime getStart_time() {return start_time;}

    public LocalDateTime getEnd_time() {return end_time;}

    public ArrayList<Attendee> getAttendees() {return attendees;}

    public ArrayList<Organizer> getOrganizers() {return organizers;}

    public ArrayList<Talk> getTalks() {return talks;}

    public ArrayList<Speakers> getSpeakers() {
        ArrayList x = new ArrayList();
        /* to be implemented later depending on class Talk */
        return x
    }
    protected void add_attendee(Attendee attendee) {
        attendees.add(attendee);
    }

    protected void add_organizer(Organizer organizer) {
        organizers.add(organizer);
    }

    protected void add_talk(Talk talk) {
        talks.add(talk);
    }
}
