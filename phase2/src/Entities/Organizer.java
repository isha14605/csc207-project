package Entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents an Entities.Organizer. This class is a subclass of Entities.User.
 *
 * @author Silvia Guerrero
 * @version 1.0
 * @see User
 */

public class Organizer extends User implements Serializable {
    private ArrayList<Integer> eventsOrganizing;

    // Constructor method for Entities.Organizer
    /**
     * Creates an Entities.Organizer with the specified name, email, and password.
     * Initiates ArrayList for eventsOrganizing.
     * @param name the name of the Entities.Organizer
     * @param email the email of the Entities.Organizer
     * @param password the password of the Entities.Organizer
     */
    public Organizer(String name, String password, String email) {
        super(name, password, email);
        this.eventsOrganizing = new ArrayList<Integer>();
    }

    // Getters
    /**
     * Returns a string that represents the type of Entities.User this object is, eg. O for Entities.Organizer.
     * @return a string representing the type of Entities.User this object is
     */
    public char userType(){
        return 'O';
    }

    /**
     * Returns an ArrayList of Events that holds all of the Events that the Entities.Organizer is organizing.
     * @return a list of all Events that the Entities.Organizer is organizing.
     * @see Event
     */
    public ArrayList<Integer> getEventsOrganizing() {
        return eventsOrganizing;
    }

    // Setters
    /**
     * Adds an Event to the Entities.Organizer's ArrayList of Events they are organizing.
     * @param event the Event that the Entities.Organizer is to be organizing.
     * @see Event
     */
    public void addEvent(Integer event) {
        this.eventsOrganizing.add(event);
    }

}