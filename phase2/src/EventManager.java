import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Manages Events and their functionality.
 *
 * @author Chevoy Ingram & Farhad Siddique
 * @version 1.0
 */

public class EventManager implements Serializable {

    public ArrayList<Event> events;

    /** creates a empty Event Manager **/
    public EventManager(){
        events = new ArrayList<>();
    }

    /** Allows a user to create a new account by checking if anyone with the same email id has already been registered.
     * @param name      the nam e of the user.
     * @param desc      description of the event.
     * @param start     start of the event in localtime.
     * @param end       end of the event in localtime.
     * @param date      date of the event of event in local time.
     * @param capacity  the amount of people allowed in event
     * @param event_only if the event only allows events
     */
    protected boolean create_event(String eventType, String name, String desc, LocalTime start, LocalTime end,
                                   LocalDate date, int capacity, boolean event_only) {
        for(Event booked: events){
            if(booked.getName().equals(name)){
                return false;
            }
        }
        switch (eventType){
            case "panel":
                events.add(new Panel(name,desc,start,end,date,capacity,event_only));
                return true;

            case "talk":
                events.add(new Talk(name,desc,start,end,date,capacity,event_only));
                return true;

            case "party":
                events.add(new Party(name,desc,start,end,date,capacity,event_only));
                return true;

        }
        return false;
    }


    /** Finds the event with the event id .
     * @param event_id a integer that is connected to a event.
     @return  returns the event that has the event_id value if event doesn't exist returns null */
    protected Event find_event(Integer event_id){
        for(Event event: events){
            if(event_id.equals(event.getEventId())){
                return event;
            }
        }
        return null;
    }

    /** Gets all event on a certain date.
     * @param date date of the event of event in local time.
     @return  event */
    protected ArrayList<Event> get_events_on(LocalDate date){
        ArrayList<Event> on_same_day = new ArrayList<>();
        for(Event scheduled: events){
            if(scheduled.getEventDate().equals(date)){
                on_same_day.add(scheduled);
            }
        }
        return on_same_day;
    }

    /** Checks if speaker can be scheduled for a talk and a certain event.
     * @param event event that is wanted to checked
     * @param speaker Speaker that is being checked if they can join
     @return  boolean - If speaker doesn't have a time conflict */
    protected boolean can_schedule_speaker(Event event, Speaker speaker){
        for(Integer scheduled: speaker.getTalksSpeaking()){
            if(time_conflict(event, find_event(scheduled))){
                return false;
            }
        }
        return true;
    }



    /** Checks if there is a time conflict between two events
     * @param event1 date of the event of event in local time.
     * @param event2 date of the event of event in local time.
     @return  return true if there is a time conflict */
    protected boolean time_conflict(Event event1, Event event2) {
        if (event1.getStartTime().equals((event2.getStartTime()))) {
            return true;
        } else if (event1.getStartTime().isAfter(event2.getStartTime()) &&
                event1.getStartTime().isBefore(event2.getEndTime())) {
            return true;
        } else return event1.getEndTime().isAfter(event2.getStartTime()) &&
                event1.getEndTime().isBefore(event2.getEndTime());
    }

    /** Checks if a value was in valid format
     * @param check check can be anything making sure the value isn't null,
     @return  event */
    protected boolean not_valid_format(Object check){
        return check == null;
    }

    /** Gets the local date time of a date and time
     *@param date local time of date
     * @param time local time
     @return  the local date time  */
    protected LocalDateTime get_localDateTime(LocalDate date, LocalTime time){
        return LocalDateTime.of(date, time);
    }

    /** Allows a user to create a new account by checking if anyone with the same email id has already been registered.
     * @param date the string representation of a date
     @return  the local date of this string*/
    protected LocalDate date_formatting_date(String date){
        int len = date.length();
        try {
            if (len == 10) {
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return LocalDate.parse(date);
            }
        }catch (Exception e){
            System.out.println("Date is not a valid date");
        }
        return null;
    }

    /** Allows a user to create a new account by checking if anyone with the same email id has already been registered.
     * @param date the string representation of a time
     @return  the local time of this string representation  */
    protected LocalTime date_formatting_time(String date){
        int len = date.length();
        try {
            if (len == 5) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                return LocalTime.parse(date, formatter);
            }
        } catch (Exception e){
            System.out.println("time not within hours of the day. Please chose a time using " +
                    "the 24 hour format between 00-23 with format of hour:min");
        }
        return null;
    }

    public void writeToFile(String fileName) throws IOException {
        OutputStream file = new FileOutputStream(fileName);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);

        output.writeObject(events);
        output.close();

    }

    public ArrayList<Event> readFile(String fileName) throws IOException, ClassNotFoundException {
        try {
            InputStream file = new FileInputStream(fileName);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            ArrayList<Event> events1 = (ArrayList<Event>) input.readObject();
            input.close();
            return events1;
        } catch (IOException | ClassNotFoundException ignored) {
            System.out.println("couldn't read file.");
        }
        return events;
    }


}

class ConferenceManager {
    private static ArrayList<Conference> conferences;
    EventManager em = new EventManager();

    ConferenceManager(){
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
    protected void addConference(String name, String confDescription, LocalTime startTime, LocalTime endTime,
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
    protected boolean addEvent(Conference c, Event event){
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

    protected boolean cancelEvent(Conference c, Event event){
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
    protected Conference findConference(String name){
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
    protected boolean conferenceExists(String name){
        Conference c = findConference(name);
        if (c == null){
            return false;
        }
        return conferences.contains(c);
    }


}