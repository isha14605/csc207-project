import java.util.ArrayList;

/**
 * Represents an Organizer. This class is a subclass of User.
 *
 * @author Silvia Guerrero
 * @version 1.0
 * @see User
 */

public class Organizer extends User{
    protected ArrayList<Integer> eventsOrganizing;

    // Constructor method for Organizer
    /**
     * Creates an Organizer with the specified name, email, and password.
     * Initiates ArrayList for eventsOrganizing.
     * @param name the name of the Organizer
     * @param email the email of the Organizer
     * @param password the password of the Organizer
     */
    Organizer(String name, String password, String email) {
        super(name, password, email);
        this.eventsOrganizing = new ArrayList<Integer>();
    }

    // Getters
    /**
     * Returns a string that represents the type of User this object is, eg. O for Organizer.
     * @return a string representing the type of User this object is
     */
    protected char userType(){
        return 'O';
    }

    /**
     * Returns an ArrayList of Events that holds all of the Events that the Organizer is organizing.
     * @return a list of all Events that the Organizer is organizing.
     * @see Event
     */
    public ArrayList<Integer> getEventsOrganizing() {
        return eventsOrganizing;
    }

    // Setters
    /**
     * Adds an Event to the Organizer's ArrayList of Events they are organizing.
     * @param event the Event that the Organizer is to be organizing.
     * @see Event
     */
    protected void addEvent(Integer event) {
        this.eventsOrganizing.add(event);
    }

}