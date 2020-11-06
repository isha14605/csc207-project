import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

public class Event_Manager{
    protected static ArrayList<Event> events = new ArrayList<Event>();
    protected static ArrayList<Room> rooms = new ArrayList<Room>();

    protected Event_Manager() { }

    /* code use case classes that directly add/ create new entities */
    protected boolean add_Event(String name, String desc, LocalDateTime start, LocalDateTime end) {

        Event event = new Event(name, desc, start, end);
        if (events.contains(event)) {
            return false;
        }
        for(Event scheduled: events){
            if (event.getStart_time().equals(scheduled.getStart_time())){
                return false;
            }
        }
        events.add(event);
        return true;
    }

    private Talk create_talk(String date, String startTime, String endTime, Event event){
        return new Talk(date, startTime, endTime, event);
    }
    protected boolean add_talk(Talk talk, Event event) {
        for (Talk scheduled : event.getTalks()) {
            if (talk.getStartTime().equals(scheduled.getStartTime())) {
                return false;
            }
        }
        event.add_talk(talk);
        return true;
    }
    protected boolean schedule_speaker(Speaker speaker, Talk talk, Event event){
        if(talk.getSpeaker() != null || Objects.equals(talk.getSpeaker(), speaker)){
            return false;
        }
        for(Talk scheduled: get_talks_at(talk.getStartTime(), event)){
            if(speaker.equals(scheduled.getSpeaker())){
                return false;
            }
        }
        speaker.setTalks_speaking(talk);
        talk.setSpeaker(speaker);
        return true;
    }

    private Room create_room(String name, Integer room_capacity, LocalTime open_time, LocalTime close_time){
        return new Room(name, room_capacity, open_time, close_time);
    }
    protected boolean add_room(Room room, Event event){
        if (rooms.contains(room)) {
            return false;
        }
        rooms.add(room);
        return true;
    }
    protected boolean remove_room(Room room, Event event){
        if (rooms.contains(room)) {
            rooms.remove(room);
            return true;
        }
        return false;
    }
    protected void schedule_room(Talk talk, Room room, Event event){ }

    protected void send_all(User organizer, Event event, String message) {
        for(Attendee attendant: event.getAttendees()) {
            attendant.receive_message(organizer, message);
        }
    }

    protected ArrayList<Talk> get_talks_at(String time, Event event){
        ArrayList<Talk> same_time = new ArrayList<>();
        for(Talk talk: event.getTalks()){
            if(talk.getStartTime().equals(time)){
                same_time.add(talk);
            }
        }
        return same_time;
    }

}