import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Event {
    private static int num_of_events; /*Will delete if not needed */
    private int event_id; /*Will delete if not needed */
    private String name;
    private String event_description;
    private LocalTime start_time;
    private LocalTime end_time;
    private LocalDate event_date;
    private ArrayList<Attendee> attendees;
    private ArrayList<Organizer> organizers;
    private ArrayList<Talk> talks;
    private Room event_room;

    public Event(String name, String event_description, LocalTime start_time, LocalTime end_time, LocalDate event_date){
        num_of_events += 1;
        this.event_id = num_of_events;
        this.name = name;
        this.event_description = event_description;
        this.start_time = start_time;
        this.end_time = end_time;
        this.event_date = event_date;
        this.attendees = new ArrayList<Attendee>();
        this.organizers = new ArrayList<Organizer>();
        this.talks = new ArrayList<Talk>();
    }

    /*Getters*/
    public String getName() {return name;}

    public String getEvent_description() {return event_description;}

    public int getEvent_id() {return event_id;}

    public LocalTime getStart_time() {return start_time;}

    public LocalTime getEnd_time() {return end_time;}

    public LocalDate getEvent_date() {return event_date;}

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

    /*Setters*/

    /* setEvent_room should be called by EventManager/RoomManager to set a room for an event.
     * Event Manager/Room Manager will be responsible for checking room availability and booking the room for required
     * time as per start_time and end_time of event. An event can only be assigned to 1 room.*/
    protected void setEvent_room(Room room) {this.event_room = room;}


    protected void add_attendee(Attendee attendee) {
        attendees.add(attendee);
    }

    protected void remove_attendee(Attendee attendee) {
        attendees.remove(attendee);
    }

    protected void add_organizer(Organizer organizer) {
        organizers.add(organizer);
    }

    protected void add_talk(Talk talk) {
        talks.add(talk);
    }
}
