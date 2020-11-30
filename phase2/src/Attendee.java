import java.util.ArrayList;

/**
 * Represents an Attendee. This class is a subclass of User.
 *
 * @author Isha Sharma
 * @version 1.0
 * @see User
 */
public class Attendee extends User{
    private ArrayList<Integer> eventsAttending;

    // Constructor for Attendee
    /**
     * Creates an Attendee with the specified name, email, and password.
     * @param name the name of the specified Attendee
     * @param email the email of the specified Attendee
     * @param password the password of the specified Attendee
     */
    Attendee(String name, String password, String email) {
        super(name, password, email);
        this.eventsAttending = new ArrayList<Integer>();
    }

    // Getters
    /**
     * Returns an ArrayList of Event objects, allowing an Attendee to view all the events that they are attending.
     * @return a list containing each Event that the specified Attendee is attending
     * @see Event
     */
    public ArrayList<Integer> getEventsAttending(){
        return this.eventsAttending;
    }

    /**
     * Returns a string that represents the type of User this object is, eg. A for Attendee.
     * @return a string representing the type of User this object is
     */
    protected char userType(){
        return 'A';
    }

    // Setters
    /**
     * Adds an Event to an Attendee's list of events that they are attending.
     * @param event the event that the Attendee would like to attend
     * @see Event
     */
    protected void attendEvent(Integer event){
        this.eventsAttending.add(event);
    }

    /**
     * Removes an Event from an Attendee's list of events that they are attending.
     * @param event the event that the Attendee would no longer like to attend
     * @see Event
     */
    protected void removeEvent(Integer event) {
        this.eventsAttending.remove(event);
    }
}
