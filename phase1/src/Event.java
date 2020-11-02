import java.time.LocalDateTime;
import java.util.ArrayList;

public class Event {
    private static int num_of_events; /*Will delete if not needed */
    private int event_id; /*Will delete if not needed */
    private String name;
    private String event_description;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private ArrayList<Attendee> attendees;
    private ArrayList<Organizer> organizers;
    private ArrayList<Talk> talks;
    /*Event Manager will be responsible for checking room availability and booking the room for required time as
    * per start_time and end_time of event. An event can only be assigned to 1 room.*/
    protected Room event_room;

    public Event(String name, String event_description, LocalDateTime start_time, LocalDateTime end_time){
        num_of_events += 1;
        this.event_id = num_of_events;
        this.name = name;
        this.event_description = event_description;
        this.start_time = start_time;
        this.end_time = end_time;
        this.attendees = new ArrayList<Attendee>();
        this.organizers = new ArrayList<Organizer>();
        this.talks = new ArrayList<Talk>();
    }

    /*Getters*/
    public String getName() {return name;}

    public String getEvent_description() {return event_description;}

    public int getEvent_id() {return event_id;}

    public LocalDateTime getStart_time() {return start_time;}

    public LocalDateTime getEnd_time() {return end_time;}

    public ArrayList<Attendee> getAttendees() {return attendees;}

    public ArrayList<Organizer> getOrganizers() {return organizers;}

    public ArrayList<Talk> getTalks() {return talks;}

    public Room getEvent_room() {return event_room;}

    /* To get a list of all speakers in the event */
    public ArrayList<Speaker> getSpeakers() {
        ArrayList<Speaker> x = new ArrayList<Speaker>();
        for (int i = 0; i < talks.size(); i++) {
            x.add(talks.get(i).getSpeaker());
        }
        return x;
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
