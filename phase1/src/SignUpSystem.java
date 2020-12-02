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

    /**
     * Returns a list of events that an Entities.Attendee can attend, based on a specified date, start time, and end time. Checks
     * to see that there are events occurring on the specified date between the times provided.
     * @param date the date that the Entities.Attendee is interested in viewing events for
     * @param startTime the start time that the Entities.Attendee is interested in viewing events for
     * @param endTime the end time that the Entities.Attendee is interested in viewing events for
     * @return a list containing potential events that the Entities.Attendee is interested in attending for further perusal
     */
    // Allows the Entities.Attendee to browse the events and decide which ones they want to see based on date and time
    public ArrayList<Event> browseEvents(EventManager eM, LocalDate date){
//        ArrayList<Event> interestList = new ArrayList<>();
//        ArrayList<Event> allEventsList = eM.getEvents();
//        for (Event event: allEventsList) {
//            if (event.getEventDate().equals(date)){
//
////                    && event.getStartTime().equals(startTime)
////                    && (endTime.isBefore(event.getEndTime()) || event.getEndTime().equals(endTime)))
//                interestList.add(event);
//            }
//        }
        return eM.get_events_on(date); // Return the list of events so the Entities.Attendee can browse
    }

    /**
     * Allows an Entities.Attendee to sign up for an Event.
     * @param a the Entities.Attendee that wants to sign up for an an Event.
     * @param e the Event that the Entities.Attendee wants to be signed up for.
     * @see UserManager#signUp(Attendee, Event)
     */
    // Method to sign up an Entities.Attendee for an Event
    public boolean signUpEvent(Attendee a, Event e){
        // Sign the Entities.Attendee up for the Event
        return uM.signUp(a,e);
    }

}
