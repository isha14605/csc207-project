package UseCase;

import Entities.Conference;
import Entities.Event;
import Gateway.EventSave;
import Gateway.UserSave;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Manages conferences and their functionality
 * @author Chevoy Ingram & Tanya Thaker
 * @version 1.0
 */
public class ConferenceManager implements Serializable {
    public static ArrayList<Conference> conferences;
    EventManager em;
    UserManager um;

    /** constructor*/
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
     * @param e the event we wish to add to the conference
     * @return true if event was successfully added
     */
    public boolean addEvent(Conference c, Integer e){
        // haven't checked if event exists
        Event event = em.findEvent(e);
        if (!(c.getConfDate() == event.getEventDate())){
            return false;
        }
        if (c.getEventIds().contains(event.getEventId())){
            return false; //couldnt add event
        }
        for(Integer i: c.getEventIds()){
            if (em.timeConflict(em.findEvent(i), event)){
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
     * @param e the event that the user wishes to cancel
     * @return true if the event was successfully cancelled
     */

    public boolean cancelEvent(Conference c, Integer e){
        Event event = em.findEvent(e);
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

    /**
     * Returns true if the conference has a VIP event
     * @param c the conference
     * @return true if the conference has a VIP event
     */

    public boolean isVIP(Conference c){
        for(Integer id: c.getEventIds()){
            if (em.findEvent(id).isVipOnly()){
                return true;
            }
        }
        return false;
    }

    /**
     * returns true if event is in the conference
     * @param event id of the event we wish to verify is in a conference
     * @return true if event in conference
     */

    public Conference eventInConference(Integer event){
        for (Conference c: conferences){
            if (c.getEventIds().contains(event)){
                return c;
            }
        }
        return null;
    }

    public ArrayList<Event> nonVipEvents(Conference c){
        ArrayList<Event> events = new ArrayList<Event>();
        for(int e: c.getEventIds()){
            if (!(em.findEvent(e).isVipOnly())){
                events.add(em.findEvent(e));
            }
        }
        return events;
    }

    public ArrayList<Event> vipEvents(Conference c){
        ArrayList<Event> events = new ArrayList<Event>();
        for(int e: c.getEventIds()){
            if (em.findEvent(e).isVipOnly()){
                events.add(em.findEvent(e));
            }
        }
        return events;
    }

    // NEED TO FIX - Tanya
    public boolean addAttendeesToConference(Conference c, String name){
        Entities.Attendee u = (Entities.Attendee) um.findUser(name);
        ArrayList<Event> vip = vipEvents(c);
        ArrayList<Event> nonvip = nonVipEvents(c);
        boolean flag = false;
        for(Event e: nonvip) {
            if (e.getAttendeeCapacity() < e.getAttendeeEmails().size()) {
                u.attendEvent(e.getEventId());
                e.addAttendee(u.getEmail());
                flag = true;
            }
            if (u.userType() == 'V'){
                Entities.VIP v = (Entities.VIP) u;
                v.addPoints(10);
            }
        }
        if (u.userType() == 'V'){
            for(Event event: vip){
                if(event.getAttendeeCapacity() < event.getAttendeeEmails().size()){
                    Entities.VIP v = (Entities.VIP) u;
                    v.attendVipEvent(event.getEventId());
                    event.addAttendee(u.getEmail());
                    v.addPoints(50);
                    flag = true;
                }
            }

        }
        return flag;
    }

    // NEED TO FIX - Tanya
    public  boolean removeAttendeeConference(Conference c, String name){
        Entities.Attendee u = (Entities.Attendee) um.findUser(name);
        ArrayList<Event> vip = vipEvents(c);
        ArrayList<Event> nonvip = nonVipEvents(c);
        boolean flag = false;
        for(Event e: nonvip) {
            if (u.getEventsAttending().contains(e.getEventId())) {
                u.removeEvent(e.getEventId());
                e.removeAttendee(u.getEmail());
                flag = true;
            }
            if (u.userType() == 'V'){
                Entities.VIP v = (Entities.VIP) u;
                v.removePoints(10);
            }
        }
        if (u.userType() == 'V'){
            for(Event event: vip){
                if(u.getEventsAttending().contains(event.getEventId())){
                    Entities.VIP v = (Entities.VIP) u;
                    v.removeVipEvent(event.getEventId());
                    event.removeAttendee(u.getEmail());
                    v.removePoints(50);
                    flag = true;
                }
            }
        }
        return flag;
    }

    public ArrayList<String> getConferenceList() {
        ArrayList<String> conferenceNames = new ArrayList<>();
        if(conferences.size()==0){
            conferenceNames.add("None");
            return conferenceNames;
        }
        for(Conference booked: conferences){
            conferenceNames.add(booked.getName());
        }
        return conferenceNames;
    }

    public ArrayList<Integer> getEvents(Conference c){
        return c.getEventIds();
    }

    //Need to commit

}
