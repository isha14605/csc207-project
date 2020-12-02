import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Represents an Event.
 * @author Farhad Siddique
 * @version 1.0
 */

public class Event implements Serializable {
    private static int numOfEvents;
    private int eventId;
    private String name;
    private String eventDescription;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate eventDate;
    private ArrayList<Attendee> attendees;
    private ArrayList<Organizer> organizers;
    private ArrayList<Talk> talks;
    private Room eventRoom;

    // Constructor for Event
    /**
     * Creates an Event with the specified name, description, start time, end time and date of event.
     * @param name the name of the specified Entities.User
     * @param eventDescription the description of the event
     * @param startTime the time when the event starts
     * @param endTime the time when the event ends
     * @param eventDate the date when the event is happening
     */
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

    // Getters

    /**
     * Returns a String that is the name of this event.
     * @return a String which is the name of this event.
     */
    public String getName() {return name;}

    /**
     * Returns a String that is the description of this event.
     * @return a String which is the description of this event.
     */
    public String getEventDescription() {return eventDescription;}

    /**
     * Returns an int that is the id of this event.
     * @return an int that is the id of this event.
     */
    public int getEventId() {return eventId;}

    /**
     * Returns an int that is the id of this event.
     * @return an int that is the id of this event.
     */
    public void setEventId(int eventId) {this.eventId = eventId;}

    /**
     * Returns a LocalTime that is the start time of this event.
     * @return a LocalTime that is the start time of this event.
     */
    public LocalTime getStartTime() {return startTime;}

    /**
     * Returns a LocalTime that is the end time of this event.
     * @return a LocalTime that is the end time of this event.
     */
    public LocalTime getEndTime() {return endTime;}

    /**
     * Returns a LocalDate that is the date of this event.
     * @return a LocalDate that is the date of this event.
     */
    public LocalDate getEventDate() {return eventDate;}

    /**
     * Returns an ArrayList of Entities.Attendee objects who are attending this event.
     * @return an ArrayList of Entities.Attendee objects who are attending this event.
     */
    public ArrayList<Attendee> getAttendees() {return attendees;}

    /**
     * Returns an ArrayList of Entities.Organizer objects who are organizing this event.
     * @return an ArrayList of Entities.Organizer objects who are organizing this event.
     */
    public ArrayList<Organizer> getOrganizers() {return organizers;}

    /**
     * Returns an ArrayList of Entities.Talk objects that are included in this event.
     * @return an ArrayList of Entities.Talk objects that are included in this event.
     */
    public ArrayList<Talk> getTalks() {return talks;}

    /**
     * Returns an eventRoom object that is the designated room for this event.
     * @return an eventRoom object that is the designated room for this event.
     */
    public Room getEventRoom() {return eventRoom;}

    /**
     * Returns an ArrayList of Entities.Speaker objects that are speaking in talks of this event.
     * @return an ArrayList of Entities.Speaker objects that are speaking in talks of this event.
     */
    public ArrayList<Speaker> getSpeakers() {
        ArrayList<Speaker> x = new ArrayList<Speaker>();
        for (int i = 0; i < talks.size(); i++) {
            x.add(talks.get(i).getSpeaker());
        }
        return x;
    }

    // Setters

    /**
     * sets the designated room for this event.
     * Should be called by UseCase.RoomManager. UseCase.RoomManager will be responsible for checking room availability and booking
     * the room for required time as per start_time ,end_time and eventDate. An event can only be assigned to 1 room.
     * @param room the room for this event.
     */
    protected void setEventRoom(Room room) {this.eventRoom = room;}

    /**
     * Adds an Entities.Attendee object to this event
     * @param attendee an attendee that will attend this event.
     */
    protected void addAttendee(Attendee attendee) {
        attendees.add(attendee);
    }

    /**
     * Removes an Entities.Attendee object from this event
     * @param attendee an attendee that will no longer attend this event.
     */
    protected void removeAttendee(Attendee attendee) {
        attendees.remove(attendee);
    }

    /**
     * Adds an Entities.Organizer object to this event
     * @param organizer an Entities.Organizer that will organize this event.
     */
    protected void addOrganizer(Organizer organizer) {
        organizers.add(organizer);
    }

    /**
     * Adds a Entities.Talk object to this event
     * @param talk a talk that will be included in this event.
     */
    protected void addTalk(Talk talk) {
        talks.add(talk);
    }
}
