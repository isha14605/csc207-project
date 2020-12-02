package UseCase;

import Entities.Conference;
import Entities.Event;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ConferenceManager {
    public static ArrayList<Conference> conferences;
    EventManager em = new EventManager();

    public ConferenceManager(){
        conferences = new ArrayList<>();
    }

    /**
     * creates a new conference
     * @param name string representing name of the conference
     * @param confDescription string representing description of the conference
     * @param startTime the start time of the conference
     * @param endTime the end time of the conference
     * @param confDate the date of the conference
     */
    public void addConference(String name, String confDescription, LocalTime startTime, LocalTime endTime,
                              LocalDate confDate){
        Conference c = new Conference(name, confDescription, startTime, endTime, confDate);
        conferences.add(c);
    }

    /**
     * adds an event joining the conference
     * @param c the conference to which an event is to be added
     * @param event the event we wish to add to the conference
     * @return true if event was successfully added
     */
    public boolean addEvent(Conference c, Event event){
        // haven't checked if event exists
        if (!(c.getConfDate() == event.getEventDate())){
            return false;
        }
        if (c.getEventIds().contains(event.getEventId())){
            return false; //couldnt add event
        }
        for(Integer i: c.getEventIds()){
            if (em.time_conflict(em.find_event(i), event)){
                return false; //couldn't add event
            }
        }
        c.addEvent(event.getEventId());
        c.addEventName(event.getName());
        return true;
    }

    /**
     * cancels event joining the conference
     * @param c the conference wherein the user wishes to cancel the event
     * @param event the event that the user wishes to cancel
     * @return true if the event was successfully cancelled
     */

    public boolean cancelEvent(Conference c, Event event){
        if (!(c.getEventIds().contains(event.getEventId()))){
            return false; // event doesn't exist in this conference
        }
        c.removeEvent(event.getEventId());
        c.removeEventName(event.getName());
        return true;
    }

    /**
     * finds the object of the conference
     * @param name string representing the object for the name of the conference we want
     * @return conference object
     */
    public Conference findConference(String name){
        for(Conference scheduled: conferences){
            if(scheduled.getName().equals(name)){
                return scheduled;
            }
        }
        return null;
    }

    /**
     * checks if the conference exists in the system
     * @param name the name of the conference
     * @return true if the confernce exists
     */
    public boolean conferenceExists(String name){
        Conference c = findConference(name);
        if (c == null){
            return false;
        }
        return conferences.contains(c);
    }

}
