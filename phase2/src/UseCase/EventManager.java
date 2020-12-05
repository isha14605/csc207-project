package UseCase;

import Entities.*;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Manages Events and their functionality.
 *
 * @author Chevoy Ingram & Farhad Siddique
 * @version 1.0
 */

public class EventManager implements Serializable {

    public ArrayList<Event> events;

    /** creates a empty Entities.Event Manager **/
    public EventManager(){
        events = new ArrayList<>();
    }

    /** Allows a user to create a new account by checking if anyone with the same email id has already been registered.
     * @param name      the name of the user.
     * @param desc      description of the event.
     * @param start     start of the event in localtime.
     * @param end       end of the event in localtime.
     * @param date      date of the event of event in local time.
     * @param capacity  the amount of people allowed in event
     * @param event_only if the event only allows events
     */
    public boolean createEvent(String eventType, String name, String desc, LocalTime start, LocalTime end,
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
    public Event findEvent(Integer event_id){
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
    public ArrayList<Event> getEventsOn(LocalDate date){
        ArrayList<Event> on_same_day = new ArrayList<>();
        for(Event scheduled: events){
            if(scheduled.getEventDate().equals(date)){
                on_same_day.add(scheduled);
            }
        }
        return on_same_day;
    }

    /** Checks if speaker can be scheduled for a talk and a certain event.
     * @param event talk that you want to schedule speaker for
     * @param speakerEmail email of the speaker you want to schedule
     * @return  returns a boolean that indicates whether speaker can be scheduled */
    public boolean canScheduleSpeaker(Event event, String speakerEmail){
        if (event.eventType().equals("Entities.Talk")) {
            Talk t = (Talk) event;
            return !t.getSpeakerEmail().equals(speakerEmail);
        } else if (event.eventType().equals("Entities.Panel")) {
            Panel p = (Panel) event;
            boolean canAdd = false;
            for (String s: p.getSpeakerEmails()) {
                if (s.equals(speakerEmail)) {
                    canAdd = true;
                    break;
                }
            }
            return canAdd;
        }
        return false;
    }


    /** Checks if there is a time conflict between two events
     * @param event1 date of the event of event in local time.
     * @param event2 date of the event of event in local time.
     @return  return true if there is a time conflict */
    public boolean timeConflict(Event event1, Event event2) {
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
    public boolean notValidFormat(Object check){
        return check == null;
    }

    /** Gets the local date time of a date and time
     *@param date local time of date
     * @param time local time
     @return  the local date time  */
    public LocalDateTime getLocalDateTime(LocalDate date, LocalTime time){
        return LocalDateTime.of(date, time);
    }

    //Shouldn't all of the methods below in UseCase.EventManager be inside the EventController?
    /** Allows a user to create a new account by checking if anyone with the same email id has already been registered.
     * @param date the string representation of a date
     @return  the local date of this string*/
    public LocalDate dateFormattingDate(String date){
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
    public LocalTime dateFormattingTime(String date){
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

    public ArrayList<String> getEventString() {
        ArrayList<String> eventNames = new ArrayList<>();
        if(events.size()==0){
            eventNames.add("None");
            return eventNames;
        }
        for(Event booked: events){
            eventNames.add("Event Id: " + booked.getEventId() + " - " + booked.getName());
        }
        return eventNames;
    }
}