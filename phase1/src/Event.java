import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Event {
    private static int numOfEvents; /*Will delete if not needed */
    private int eventId; /*Will delete if not needed */
    private String name;
    private String eventDescription;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate eventDate;
    private ArrayList<Attendee> attendees;
    private ArrayList<Organizer> organizers;
    private ArrayList<Talk> talks;
    private Room eventRoom;

    public Event(String name, String eventDescription, LocalTime startTime, LocalTime endTime, LocalDate eventDate){
        numOfEvents += 1;
        this.eventId = numOfEvents;
        this.name = name;
        this.eventDescription = eventDescription;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventDate = eventDate;
        this.attendees = new ArrayList<Attendee>();
        this.organizers = new ArrayList<Organizer>();
        this.talks = new ArrayList<Talk>();
    }

    public Event(String name, String desc, LocalDateTime start, LocalDateTime end) {
    }

    /*Getters*/
    public String getName() {return name;}

    public String getEventDescription() {return eventDescription;}

    public int getEventId() {return eventId;}

    public LocalTime getStartTime() {return startTime;}

    public LocalTime getEndTime() {return endTime;}

    public LocalDate getEventDate() {return eventDate;}

    public ArrayList<Attendee> getAttendees() {return attendees;}

    public ArrayList<Organizer> getOrganizers() {return organizers;}

    public ArrayList<Talk> getTalks() {return talks;}

    public Room getEventRoom() {return eventRoom;}

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
    protected void setEventRoom(Room room) {this.eventRoom = room;}


    protected void addAttendee(Attendee attendee) {
        attendees.add(attendee);
    }

    protected void removeAttendee(Attendee attendee) {
        attendees.remove(attendee);
    }

    protected void addOrganizer(Organizer organizer) {
        organizers.add(organizer);
    }

    protected void addTalk(Talk talk) {
        talks.add(talk);
    }
}
