package Controllers;

import Entities.Attendee;
import Entities.Event;
import UseCase.ConferenceManager;
import UseCase.EventManager;
import UseCase.UserManager;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Handles signing up functionality for events.
 * @author Isha Sharma and Anushka Saini
 * @version 1.0
 */
public class SignUpSystem {

    UserManager uM = new UserManager(); // New instance of UseCase.UserManager
    EventManager eM = new EventManager(); // New instance of UseCase.EventManager
    ConferenceManager cM = new ConferenceManager(); // New instance of UseCase.ConferenceManager

    /**
     * Returns a list of events that an Entities.Attendee can attend, based on a specified date, start time, and end time. Checks
     * to see that there are events occurring on the specified date between the times provided.
     * @param date the date that the Entities.Attendee is interested in viewing events for
     * @return a list containing potential events that the Entities.Attendee is interested in attending for further perusal
     */
    // Allows the Entities.Attendee to browse the events and decide which ones they want to see based on date and time
    public ArrayList<Event> browseEvents(EventManager eM, LocalDate date){ // need to discuss
        return eM.getEventsOn(date); // Return the list of events so the Entities.Attendee can browse
    }

    /**
     * Allows an Entities.Attendee to sign up for an Entities.Event.
     * @param email the Entities.Attendee that wants to sign up for an an Entities.Event.
     * @param event the Entities.Event that the Entities.Attendee wants to be signed up for.
     * @see UserManager#signUpEvent(Attendee, Integer)
     * @see EventManager#findEvent(Integer)
     * @see UserManager#checkUserExists(String)
     */
    // Method to sign up an Entities.Attendee for an Entities.Event
    public boolean signUpEvent(String email, Integer event){
        if (uM.checkUserExists(email) && !(eM.findEvent(event) == null)){
            return uM.signUpEvent((Attendee) uM.findUser(email),event);
        }
        return false;
    }

    /**
     *
     * @param email the attendee who wants to cancel Registration for an event
     * @param event the event if that the user wants to cancel registration from
     * @return true if succesfully cancelled
     * @see UserManager#cancelRegistrationEvent(Attendee, Integer)
     * @see EventManager#findEvent(Integer)
     * @see UserManager#checkUserExists(String)

     */
    public boolean cancelRegEvent(String email, Integer event){
        if (uM.checkUserExists(email) && !(eM.findEvent(event) == null)){
            return uM.cancelRegistrationEvent((Attendee) uM.findUser(email), event);
        }
        return false;
    }

    /**
     * Returns true if the user succesfully signed up for a conference
     * @param conference the name of Entities.conference Entities.Attendee wishes to join
     * @param email the email of the Entities.Attendee who wishes to join the conference
     * @return true if attendee successfully joined the conference
     */
    public boolean attendConf(String conference, String email){
        if(uM.checkUserExists(email) && !(cM.findConference(conference) == null)){
            Attendee a = (Attendee) uM.findUser(email);
            uM.signUpConference(a, conference);
            return true;
        }
        return false;
    }

    /**
     * Returns true if the user successfully cancelled registration for a conference
     * @param conference the name of Entities.conference Entities.Attendee wishes to cancel registration for
     * @param email the email of the Entities.Attendee who wishes to cancel registration
     * @return true if attendee successfully cancelled registration for the conference
     */
    public  boolean cancelRegConf(String conference, String email){
        if(uM.checkUserExists(email) && !(cM.findConference(conference) == null)){
            Attendee a = (Attendee) uM.findUser(email);
            uM.cancelRegistrationConference(a, conference);
            return true;
        }
        return false;
    }

}
