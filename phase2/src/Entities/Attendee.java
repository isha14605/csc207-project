package Entities;

import java.util.ArrayList;

/**
 * Represents an Entities.Attendee. This class is a subclass of Entities.User.
 *
 * @author Isha Sharma
 * @version 1.0
 * @see User
 */
public class Attendee extends User {
    private ArrayList<Integer> eventsAttending;
    private ArrayList<String> conferenceAttending;

    // Constructor for Entities.Attendee
    /**
     * Creates an Entities.Attendee with the specified name, email, and password.
     * @param name the name of the specified Entities.Attendee
     * @param email the email of the specified Entities.Attendee
     * @param password the password of the specified Entities.Attendee
     */
    public Attendee(String name, String password, String email) {
        super(name, password, email);
        this.eventsAttending = new ArrayList<Integer>();
        this.conferenceAttending = new ArrayList<String>();
    }

    // Getters
    /**
     * Returns an ArrayList of Event objects, allowing an Entities.Attendee to view all the events that they are attending.
     * @return a list containing each Event that the specified Entities.Attendee is attending
     * @see Event
     */
    public ArrayList<Integer> getEventsAttending(){
        return this.eventsAttending;
    }

    /**
     * Returns an array of String that represents the name of the conference the user is attending
     * @return an arraylist of string representing the conferences they are attending
     */
    public ArrayList<String> getConferenceAttending(){return this.conferenceAttending;}

    /**
     * Returns a string that represents the type of Entities.User this object is, eg. A for Entities.Attendee.
     * @return a string representing the type of Entities.User this object is
     */
    public char userType(){
        return 'A';
    }



    // Setters
    /**
     * Adds an Event to an Entities.Attendee's list of events that they are attending.
     * @param event the event that the Entities.Attendee would like to attend
     */
    public void attendEvent(Integer event){
        this.eventsAttending.add(event);
    }

    /**
     * Removes an Event from an Entities.Attendee's list of events that they are attending.
     * @param event the event that the Entities.Attendee would no longer like to attend
     */
    public void removeEvent(Integer event) {
        this.eventsAttending.remove(event);
    }

    /**
     * Removes a conference from an Entities.Attendee's list of conferences that they are attending
     * @param conference a string that represent the conference that the Entities.Attendee no longer wants to attend
     */
    public void removeConference(String conference){this.conferenceAttending.remove(conference);}

}
