import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Handles signing up functionality for events.
 * @author Isha Sharma and Anushka Saini
 * @version 1.0
 */
public class SignUpSystem {

    UserManager uM = new UserManager(); // New instance of UserManager
    EventManager eM = new EventManager(); // New instance of EventManager

    /**
     * Returns a list of events that an Attendee can attend, based on a specified date, start time, and end time. Checks
     * to see that there are events occurring on the specified date between the times provided.
     * @param date the date that the Attendee is interested in viewing events for
     * @return a list containing potential events that the Attendee is interested in attending for further perusal
     */
    // Allows the Attendee to browse the events and decide which ones they want to see based on date and time
    public ArrayList<Event> browseEvents(EventManager eM, LocalDate date){ // need to discuss
        return eM.get_events_on(date); // Return the list of events so the Attendee can browse
    }

    /**
     * Allows an Attendee to sign up for an Event.
     * @param email the Attendee that wants to sign up for an an Event.
     * @param event the Event that the Attendee wants to be signed up for.
     * @see UserManager#signUpEvent(Attendee, Event)
     */
    // Method to sign up an Attendee for an Event
    public boolean signUpEvent(String email, Integer event){
        // Sign the Attendee up for the Event
        return uM.signUpEvent((Attendee) uM.findUser(email), eM.find_event(event));
    }

    /**
     *
     * @param email the attendee who wants to cancel Registration for an event
     * @param event the event if that the user wants to cancel registration from
     * @return true if succesfully cancelled
     */
    public boolean cancelRegEvent(String email, Integer event){
        return uM.cancelRegistrationEvent((Attendee) uM.findUser(email), event);
    }

}
