import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventManager implements Serializable {
    private static final Logger logger = Logger.getLogger(EventManager.class.getName());

    public ArrayList<Event> events;

    /** creates a empty Event Manager **/
    public EventManager(){
        events = new ArrayList<>();
    }

    /** Allows a user to create a new account by checking if anyone with the same email id has already been registered.
     * @param name the name of the user.
     * @param desc description of the event.
     * @param start start of the event in localtime.
     * @param end end of the event in localtime.
     * @param date date of the event of event in local time.
     */
    protected void create_event(String name, String desc, LocalTime start, LocalTime end, LocalDate date) throws IOException {
        Event event = new Event(name, desc, start, end, date);
        if(event.getEventId() <= events.size()){
            event.setEventId(events.size()+1);
        }
        events.add(event);
    }


    /** Finds the event with the event id .
     * @param event_id a integer that is connected to a event.
     @return  returns the event that has the event_id value if event doesn't exist returns null */
    protected Event find_event(Integer event_id){
        for(Event event: events){
            if(event_id == event.getEventId()){
                return event;
            }
        }
        return null;
    }

    /** Allows a user to create a new account by checking if anyone with the same email id has already been registered.
     * @param event date of the event of event in local time.
     */
    protected void addEvent(Event event){
        this.events.add(event);
    }

    /** Allows a user to create a new account by checking if anyone with the same email id has already been registered.
     @return  event */
    protected ArrayList<Event> getEvents(){
        return this.events;
    }

    /** gets events that are .
     * @param event date of the event of event in local time.
     @return  event */
    protected ArrayList<Talk> get_talks_in(Event event){
        return event.getTalks();
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

    protected boolean event_exist(int event_id){ return event_id > 0 && event_id <= events.size();
    }

    protected void print_events(){
        for (Event event : events) {
            System.out.println(this.eventToString(event));
        }
    }

    /** Allows a user to create a new account by checking if anyone with the same email id has already been registered.
     * @param user the
     * @param event
     @return  event */
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

    /** Allows a user to create a new account by checking if anyone with the same email id has already been registered.
     * @param event
     * @param user
     @return  event */
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

    /** Checks if speaker can be scheduled for a talk and a certain event.
     * @param event event that is wanted to checked
     * @param speaker Speaker that is being checked if they can join
     * @param talk Talk that speaker is be scheduled to.
     @return  boolean - If speaker doesn't have a time conflict */
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

    /** Converts event into a readable string.
     * @param event event that will be turned into string
     @return  String of event */
    protected String eventToString(Event event){
        String room = new String();
        if(event.getEventRoom() == null){
            room = "None";
        }
        else{
            room = event.getEventRoom().getName();
        }
        return new String("Event id: " + event.getEventId() + " | Event name: " + event.getName() +
                " | Room: " + room + "\nEvent Starts from " + event.getStartTime() + " to " +
                event.getEndTime() + " on " + event.getEventDate() + " Talks " + event.getTalks() + "\n");
    }

    /** Allows a user to create a new account by checking if anyone with the same email id has already been registered.
     * @param event date of the event of event in local time.
     * @param user User that is trying to join event */
    protected void add_user(Attendee user, Event event){
        event.addAttendee(user);
    }
    protected void add_user(Organizer user, Event event){
        event.addOrganizer(user);
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

    /** Checks if there is a time conflict between a talk and other talks within event
     * @param event event that being checked
     * @param scheduling talk being checked
     @return  return true if there is a time conflict */
    protected boolean time_conflict(Talk scheduling, Event event){
        for(Talk scheduled: event.getTalks()){
            if(scheduling.getStartTime().equals(scheduled.getStartTime())){
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

    /** Checks if there is a time conflict between two talks
     * @param event1 talk being checked
     * @param event2 talk being checked
     @return  return true if there is a time conflict */
    protected boolean time_conflict(Talk event1, Talk event2) {
        if (event1.getStartTime().equals((event2.getStartTime()))) {
            return true;
        } else if (event1.getStartTime().isAfter(event2.getStartTime()) &&
                event1.getStartTime().isBefore(event2.getEndTime())) {
            return true;
        } else return event1.getEndTime().isAfter(event2.getStartTime()) &&
                event1.getEndTime().isBefore(event2.getEndTime());
    }

    protected boolean within_event(Talk talk, Event event){
            if(talk.getStartTime().isBefore(event.getStartTime())){
                return false;
            }
            else return !talk.getEndTime().isAfter(talk.getEndTime());

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

    /** Checks if the formatting on date is correct
     * @param date of the event of event in local time.
     @return  local date time of this string representation */
    protected LocalDateTime date_formatting_DT(String date){
        int len = date.length();
        if(len == 16){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return LocalDateTime.parse(date, formatter);
        }
        return null;
    }

    /** Allows a user to create a new account by checking if anyone with the same email id has already been registered.
     * @param date the string representation of a date
     @return  the local date of this string*/
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