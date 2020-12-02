package Entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents an Entities.Event.
 * @author Farhad Siddique
 * @version 1.0
 */

public abstract class Event implements Serializable {
    private static AtomicInteger numOfEvents = new AtomicInteger(0);
    private Integer eventId;
    private String name;
    private String eventDescription;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate eventDate;
    private ArrayList<String> attendeeEmails;
    private ArrayList<String> organizerEmails;
    private String roomName;
    private int attendeeCapacity;
    private boolean vipOnly;
    private String themes;
    private ArrayList<String> techRequirements;

    // Constructor for Entities.Event
    /**
     * Creates an Entities.Event with the specified name, description, start time, end time, date of event, event capacity
     * and indicator if the event is Entities.VIP only.
     * @param name the name of the specified Entities.User
     * @param eventDescription the description of the event
     * @param startTime the time when the event starts
     * @param endTime the time when the event ends
     * @param eventDate the date when the event is happening
     * @param attendeeCapacity the maximum number of attendees that can participate in the event
     * @param vipOnly indicates if  the event is only for Entities.VIP attendees or not
     */
    public Event(String name, String eventDescription, LocalTime startTime, LocalTime endTime, LocalDate eventDate,
                 int attendeeCapacity, boolean vipOnly){
        this.eventId = numOfEvents.incrementAndGet();
        this.name = name;
        this.eventDescription = eventDescription;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventDate = eventDate;
        this.attendeeCapacity = attendeeCapacity;
        this.vipOnly = vipOnly;
        // Contains email address of attendees
        this.attendeeEmails = new ArrayList<String>();
        // Contains email address of Organizers
        this.organizerEmails = new ArrayList<String>();
        this.roomName = null;
        this.themes = null;
        this.techRequirements = new ArrayList<>();
    }
    public abstract String eventType();

    public abstract void setSpeaker(String speakerEmail);

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
    public Integer getEventId() {return eventId;}

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
     * Returns a int that is the maximum Entities.Attendee Capacity of this event.
     * @return a int that is the maximum Entities.Attendee Capacity of this event.
     */
    public int getAttendeeCapacity() {return attendeeCapacity;}

    /**
     * Returns a boolean, that if True indicates event is for Entities.VIP attendees only, else event is open to all attendees.
     * @return a boolean that if True indicates event is for Entities.VIP attendees only, else event is open to all attendees.
     */
    public boolean isVipOnly() {return vipOnly;}

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
     * Returns an eventRoom object that is the designated room for this event.
     * @return a String object that is the name of the designated room for this event.
     */
    public String getRoomName() {return roomName;}

    /**
     * Returns a list of tech requirements of this event.
     * @return an arraylist that contains tech requirements of this event.
     */
    public ArrayList<String> getTechRequirements() {
        return techRequirements;
    }

    // Setters

    /**
     * sets the designated room for this event.
     * Should be called by UseCase.RoomManager. UseCase.RoomManager will be responsible for checking room availability, ensuring that
     * room capacity is more than or equal to attendeeCapacity of event and booking the room for
     * required time as per start_time ,end_time and eventDate. An event can only be assigned to 1 room.
     * @param roomName the room for this event.
     */
    protected void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    /**
     * Adds an Entities.Attendee's email to this event
     * UseCase.UserManager must ensure that only Entities.VIP attendees can sign up for Entities.VIP events.
     * @param email the email of an attendee that will attend this event.
     */
    public void addAttendee(String email) {
        attendeeEmails.add(email);
    }

    /**
     * Removes an Entities.Attendee's email from this event
     * @param email the email of an attendee that will no longer attend this event.
     */
    public void removeAttendee(String email) {
        attendeeEmails.remove(email);
    }

    /**
     * Adds an Entities.Organizer's email to this event
     * @param email the email of an Entities.Organizer that will organize this event.
     */
    public void addOrganizer(String email) {
        organizerEmails.add(email);
    }

    /**
     * Resets the Entities.Attendee Capacity of the event. Should be called by an Entities.Organizer of the event.
     * Entities.Organizer must ensure that attendeeCapacity is less than or equal to RoomCapacity of the room
     * assigned to this event, before calling this method.
     * @param attendeeCapacity a int that is the maximum Entities.Attendee Capacity of this event.
     */
    public void setAttendeeCapacity(int attendeeCapacity) {
        this.attendeeCapacity = attendeeCapacity;
    }
}
