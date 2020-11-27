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
    private ArrayList<String> attendeeEmails;
    private ArrayList<String> organizerEmails;
    private ArrayList<Talk> talks;
    private Room eventRoom;
    private int attendeeCapacity;

    // Constructor for Event
    /**
     * Creates an Event with the specified name, description, start time, end time and date of event.
     * @param name the name of the specified User
     * @param eventDescription the description of the event
     * @param startTime the time when the event starts
     * @param endTime the time when the event ends
     * @param eventDate the date when the event is happening
     * @param attendeeCapacity the maximum number of attendees that can participate in the event
     */
    public Event(String name, String eventDescription, LocalTime startTime, LocalTime endTime, LocalDate eventDate,
                 int attendeeCapacity){
        numOfEvents += 1;
        this.eventId = numOfEvents;
        this.name = name;
        this.eventDescription = eventDescription;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventDate = eventDate;
        this.attendeeCapacity = attendeeCapacity;
        // Contains email address of attendees
        this.attendeeEmails = new ArrayList<String>();
        // Contains email address of Organizers
        this.organizerEmails = new ArrayList<String>();
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
     * Returns a int that is the maximum Attendee Capacity of this event.
     * @return a int that is the maximum Attendee Capacity of this event.
     */
    public int getAttendeeCapacity() {return attendeeCapacity;}

    /**
     * Returns an ArrayList of String objects that are emails of Attendees of this event.
     * @return an ArrayList of String objects that are emails of Attendees of this event.
     */
    public ArrayList<String> getAttendeeEmails() {return attendeeEmails;}

    /**
     * Returns an ArrayList of String objects that are emails of Organizers of this event.
     * @return an ArrayList of String objects that are emails of Organizers of this event.
     */
    public ArrayList<String> getOrganizerEmails() {return organizerEmails;}

    /**
     * Returns an ArrayList of Talk objects that are included in this event.
     * @return an ArrayList of Talk objects that are included in this event.
     */
    public ArrayList<Talk> getTalks() {return talks;}

    /**
     * Returns an eventRoom object that is the designated room for this event.
     * @return an eventRoom object that is the designated room for this event.
     */
    public Room getEventRoom() {return eventRoom;}

    /**
     * Returns an ArrayList of Speaker objects that are speaking in talks of this event.
     * @return an ArrayList of Speaker objects that are speaking in talks of this event.
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
     * Should be called by RoomManager. RoomManager will be responsible for checking room availability, ensuring that
     * room capacity is more than or equal to attendeeCapacity of event and booking the room for
     * required time as per start_time ,end_time and eventDate. An event can only be assigned to 1 room.
     * @param room the room for this event.
     */
    protected void setEventRoom(Room room) {this.eventRoom = room;}

    /**
     * Adds an Attendee's email to this event
     * @param attendee an attendee that will attend this event.
     */
    protected void addAttendee(Attendee attendee) {
        attendeeEmails.add(attendee.getEmail());
    }

    /**
     * Removes an Attendee's email from this event
     * @param attendee an attendee that will no longer attend this event.
     */
    protected void removeAttendee(Attendee attendee) {
        attendeeEmails.remove(attendee.getEmail());
    }

    /**
     * Adds an Organizer's email to this event
     * @param organizer an Organizer that will organize this event.
     */
    protected void addOrganizer(Organizer organizer) {
        organizerEmails.add(organizer.getEmail());
    }

    /**
     * Adds a Talk object to this event
     * @param talk a talk that will be included in this event.
     */
    protected void addTalk(Talk talk) {
        talks.add(talk);
    }
}
