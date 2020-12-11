package Controllers;

import Entities.Attendee;
import Entities.Conference;
import Entities.Event;
import Gateway.ConferenceSave;
import Gateway.EventSave;
import Gateway.UserSave;
import UseCase.ConferenceManager;
import UseCase.EventManager;
import UseCase.UserManager;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Handles signing up functionality for events.
 * @author Isha Sharma and Anushka Saini
 * @version 1.0
 */
public class SignUpSystem {

    public UserManager uM = new UserSave().read(); // New instance of UseCase.UserManager
    public EventManager eM = new EventSave().read(); // New instance of UseCase.EventManager
    public ConferenceManager cM = new ConferenceSave().read(); // New instance of UseCase.ConferenceManager

    public SignUpSystem() throws IOException {
    }

    /**
     * Returns a list of events that an Entities.Attendee can attend, based on a specified date, start time, and end
     * time. Checks to see that there are events occurring on the specified date between the times provided.
     * @param date the date that the Entities.Attendee is interested in viewing events for
     * @return a list containing potential events that the Entities.Attendee is interested in attending for further
     * perusal
     */
    // Allows the Entities.Attendee to browse the events and decide which ones they want to see based on date and time
    public ArrayList<Event> browseEvents(EventManager eM, LocalDate date){ // need to discuss
        return eM.getEventsOn(date); // Return the list of events so the Entities.Attendee can browse
    }

    /**
     * Allows an Entities.Attendee to sign up for an Entities.Event.
     * @param email the Entities.Attendee that wants to sign up for an an Entities.Event.
     * @param event the Entities.Event that the Entities.Attendee wants to be signed up for.
     * @see UserManager#signUpEvent(Attendee, Event, Conference)
     * @see EventManager#findEvent(Integer)
     * @see UserManager#checkUserExists(String)
     */
//     Method to sign up an Entities.Attendee for an Entities.Event
    public boolean signUpEvent(String email, Integer event) throws IOException {
        System.out.println("here1");
        System.out.println(uM.checkUserExists(email));
        System.out.println(eM.findEvent(event) == null);

        if (uM.checkUserExists(email) && !(eM.findEvent(event) == null)){
            if (uM.findUser(email).userType() == 'V'){
                uM.signUpVip((Entities.VIP) uM.findUser(email), eM.findEvent(event), cM.eventInConference(event));
                return true;
            }
            System.out.println("here2");
            if(uM.signUpEvent((Attendee) uM.findUser(email), eM.findEvent(event), cM.eventInConference(event))){
                new ConferenceSave().save(cM);
                new UserSave().save(uM);

                return true;
            }
        }
        System.out.println("here3");
        return false;
    }


    /**
     *
     * @param email the attendee who wants to cancel Registration for an event
     * @param event the event if that the user wants to cancel registration from
     * @return true if succesfully cancelled
     * @see UserManager#cancelRegistrationEvent(Attendee, Event, Conference)
     * @see EventManager#findEvent(Integer)
     * @see UserManager#checkUserExists(String)

     */
    public boolean cancelRegEvent(String email, Integer event) throws IOException {
        if (uM.checkUserExists(email) && !(eM.findEvent(event) == null)){
            if (uM.findUser(email).userType() == 'V'){
                uM.cancelVip((Entities.VIP) uM.findUser(email), eM.findEvent(event), cM.eventInConference(event));
                return true;
            }
            else if(uM.cancelRegistrationEvent((Attendee) uM.findUser(email), eM.findEvent(event),
                    cM.eventInConference(event))) {
                new ConferenceSave().save(cM);
                new UserSave().save(uM);
                return true;
            }
        }
        return false;
    }
    /**
     * Returns true if the user successfully signed up for a conference
     * @param conference the name of Entities.conference Entities.Attendee wishes to join
     * @param email the email of the Entities.Attendee who wishes to join the conference
     * @return true if attendee successfully joined the conference
     */
    public boolean attendConf(String conference, String email) throws IOException {
        if(uM.checkUserExists(email) && !(cM.findConference(conference) == null)){
            Attendee a = (Attendee) uM.findUser(email);
            ArrayList<Event> e = new ArrayList<Event>();
            if(a.getConferenceAttending().contains(conference)){
                return false;
            }
            for(Integer i: cM.getEvents(cM.findConference(conference))){
                e.add(eM.findEvent(i));
            }
            cM.addAttendeesToConference(cM.findConference(conference), a.getEmail());
            uM.attendConference(a,e, cM.findConference(conference));
            new ConferenceSave().save(cM);
            new UserSave().save(uM);
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
    public  boolean cancelRegConf(String conference, String email) throws IOException {
        if(uM.checkUserExists(email) && !(cM.findConference(conference) == null)){
            Attendee a = (Attendee) uM.findUser(email);
            ArrayList<Event> e = new ArrayList<Event>();
            for(Integer i: cM.getEvents(cM.findConference(conference))){
                e.add(eM.findEvent(i));
            }
            cM.removeAttendeeConference(cM.findConference(conference), a.getEmail());
            uM.cancelRegistrationConference(a,e, cM.findConference(conference));
            new ConferenceSave().save(cM);
            new UserSave().save(uM);
            return true;
        }
        return false;
    }


}
