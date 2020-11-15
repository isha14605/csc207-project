import java.io.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventManager implements Serializable {
    private static final Logger logger = Logger.getLogger(EventManager.class.getName());

    protected final ArrayList<Event> events = new ArrayList<>();

    /** creates a empty Event Manager **/
    public EventManager() { }

    Event event = new Event("test","test",LocalTime.of(9,0),
            LocalTime.of(10,0), LocalDate.of(2020,10, 3));

    /** code use case classes that directly add/ create new entities **/
    protected Event create_event(String name, String desc, LocalTime start, LocalTime end, LocalDate date) {
        Event event = new Event(name, desc, start, end, date);
        events.add(event);
        return event;
    }
    protected Event find_event(Integer event_id){
        for(Event event: events){
            if(event_id == event.getEventId()){
                return event;
            }
        }
        return null;
    }
    protected void addEvent(Event event){
        this.events.add(event);
    }

    /** getters **/
    protected ArrayList<Event> getEvents(){
        return this.events;
    }
    protected ArrayList<Talk> get_talks_in(Event event){
        return event.getTalks();
    }
    protected ArrayList<Event> get_events_on(LocalDate date){
        ArrayList<Event> on_same_day = new ArrayList<>();
        for(Event scheduled: events){
            if(scheduled.getEventDate().equals(date)){
                on_same_day.add(scheduled);
            }
        }
        return on_same_day;
    }

    /** checks if user can join event **/
    protected boolean can_join_event(Attendee user, Event event){
        for(Event attending: user.getEventsAttending()){
            if(time_conflict(attending, event)){
                return false;
            }
            if(user.getEventsAttending().contains(event)){
                return false;
            }
            if(event.getAttendees().size() + event.getOrganizers().size() < event.getEventRoom().getRoomCapacity()){
                return false;
            }
        }
        return true;
    }
    protected boolean can_join_event(Organizer user, Event event){
        for(Event attending: user.getEventsAttending()){
            if(time_conflict(attending, event)){
                return false;
            }
            if(user.getEventsAttending().contains(event)){
                return false;
            }
            if(event.getAttendees().size() + event.getOrganizers().size() < event.getEventRoom().getRoomCapacity()){
                return false;
            }
        }
        return true;
    }
    protected boolean can_schedule_speaker(Event event, Talk talk, Speaker speaker){
        ArrayList<Event> events_on_day = get_events_on(event.getEventDate());
        events_on_day.remove(event);
        for(Event scheduled: events_on_day){
            for (Talk talk1: speaker.getTalksSpeaking()) {
                if (time_conflict(talk1, scheduled) && time_conflict(talk, talk1)) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Converts event information to string */
    protected String eventToString(Event event){
        String room = new String();
        if(event.getEventRoom() == null){
            room = "None";
        }
        else{
            room = event.getEventRoom().getName();
        }
        return new String("Event id:" + event.getEventId() + " " + event.getName() +
                " Room: " + room);
    }

    /** adds user to events list of users **/
    protected void add_user(Attendee user, Event event){
        event.addAttendee(user);
    }
    protected void add_user(Organizer user, Event event){
        event.addOrganizer(user);
    }

    /** helper functions **/
    protected boolean time_conflict(Event event1, Event event2) {
        if (event1.getStartTime().equals((event2.getStartTime()))) {
            return true;
        } else if (event1.getStartTime().isAfter(event2.getStartTime()) &&
                event1.getStartTime().isBefore(event2.getEndTime())) {
            return true;
        } else return event1.getEndTime().isAfter(event2.getStartTime()) &&
                event1.getEndTime().isBefore(event2.getEndTime());
    }
    protected boolean time_conflict(Talk scheduling, Event event){
        for(Talk scheduled: event.getTalks()){
            if(scheduling.getStartTime().isEqual(scheduled.getStartTime())){
                return true;
            }
            else if(scheduling.getStartTime().isAfter(scheduled.getStartTime()) &&
                    scheduling.getStartTime().isBefore(scheduled.getEndTime())){
                return true;
            }
            else if(scheduling.getEndTime().isAfter(scheduled.getStartTime()) &&
                    scheduling.getEndTime().isBefore(scheduled.getStartTime())){
                return true;
            }
        }
        return false;
    }
    protected boolean time_conflict(Talk event1, Talk event2) {
        if (event1.getStartTime().equals((event2.getStartTime()))) {
            return true;
        } else if (event1.getStartTime().isAfter(event2.getStartTime()) &&
                event1.getStartTime().isBefore(event2.getEndTime())) {
            return true;
        } else return event1.getEndTime().isAfter(event2.getStartTime()) &&
                event1.getEndTime().isBefore(event2.getEndTime());
    }

    protected boolean not_valid_format(Object check){
        return check == null;
    }
    protected LocalDateTime get_localDateTime(LocalDate date, LocalTime time){
        return LocalDateTime.of(date, time);
    }
    protected LocalDateTime date_formatting_DT(String date){
        int len = date.length();
        if(len == 16){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return LocalDateTime.parse(date, formatter);
        }
        return null;
    }
    protected LocalDate date_formatting_date(String date){
        int len = date.length();
        try {
            if (len == 10) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return LocalDate.parse(date, formatter);
            }
        }catch (Exception e){
            System.out.println("Date is not a valid date");
        }
        return null;
    }
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


}