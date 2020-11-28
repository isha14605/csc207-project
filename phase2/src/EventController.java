import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class EventController implements Serializable{

    EventManager em = new EventManager();
    RoomManager rm = new RoomManager();
    TalkManager tm = new TalkManager();

    /**
     * EventController Constructor
     */
    public EventController() throws ClassNotFoundException, IOException {
        em.events = em.readFile("EventSave.ser");
        rm = rm.readFile("RoomSave.ser");
        tm.talks = tm.readFile("TalkSave.ser");
    }

    /**
     * Saves EventController data to file
     */
    public void save() throws IOException {
        em.writeToFile("EventSave.ser");
        System.out.println("changes have been saved");
    }

    /**
     * Adds Event according to given parameters
     * @param name the name of the event
     * @param description description of the event
     * @param start start time of the event
     * @param end end time of the event
     * @param date date of the event
     * @param capacity
     * @param event_only
     * @return
     */
    public boolean add_event(String name, String description, String start, String end, String date, int capacity, boolean event_only) throws IOException {
        if(em.not_valid_format(em.date_formatting_time(start))|| em.not_valid_format(em.date_formatting_time(end))
        || em.not_valid_format(em.date_formatting_date(date))) {
            System.out.println("Invalid time or date! Event not added.");
            return false;
        }else {
            em.create_event(name, description, em.date_formatting_time(start),
                    em.date_formatting_time(end), em.date_formatting_date(date), capacity, event_only);
            em.writeToFile("EventSave.ser");
            System.out.println("Event was added.");
            System.out.println();
            return true;
        }

    }

    /**
     * Returns existing Events
     *
     * @return ArrayList of Events
     */
    public ArrayList<Event> get_events(){
        return em.getEvents();
    }

    public boolean add_room(String name, Integer capacity, String start, String end) throws IOException {
        if(em.not_valid_format(em.date_formatting_time(start))|| em.not_valid_format(em.date_formatting_time(end))) {
            System.out.println("Invalid time!");
            return false;
        }else {
            rm.create_room(name, capacity, rm.date_formatting_time(start), rm.date_formatting_time(end));
            rm.writeToFile("RoomSave.ser");
            System.out.println("Room was added");
            return true;
        }
    }

    /**
     * Returns existing Rooms
     *
     * @return ArrayList of Rooms
     */
    public ArrayList<Room> get_rooms(){return rm.getRooms();}

    /**
     * Adds Talk to an existing Event
     *
     * @return true if talk is added successfully
     */
    public boolean add_talk(String talkName, String start, String end, Integer event_id) throws IOException {

        if(em.date_formatting_time(start) == null || em.date_formatting_time(end) == null ){
            System.out.println("Invalid time");
            return false;
        }

        if(!em.event_exist(event_id)){
            System.out.println("Event doesn't exit");
            return false;
        }

        Talk talk = tm.create_talk(talkName, tm.date_formatting_time(start),
                tm.date_formatting_time(end), em.find_event(event_id));

        if (!rm.time_conflict(talk, em.find_event(event_id)) && em.within_event(talk,em.find_event(event_id))) {
            tm.add_talk(talk, em.find_event(event_id));
            tm.writeToFile("TalkSave.ser");
            return true;

        } else {
            System.out.println("Talk was not added due to time conflict");
            return false;
        }
    }

    /**
     * Schedules a Room for an existing Event
     */
    public void schedule_room(String room_name, Integer event_id) throws IOException {
        Event event = em.find_event(event_id);
        Room room = rm.find_room(room_name);
        if(room == null){
            System.out.println("Room doesn't exit");
            return;
        }
        if(!rm.is_room_booked(room, event) && event.getEventRoom() == null && rm.can_fit_event(event,room)){
            rm.schedule_room(room, event);
            room.addBookings(event, em.get_localDateTime(event.getEventDate(),event.getStartTime()),
                    em.get_localDateTime(event.getEventDate(),event.getEndTime()));
            System.out.println("Room has been booked for event");
            em.writeToFile("EventSave");
        }else{
            System.out.println("Room not scheduled due to time conflict");
        }
    }

    /**
     * Schedules a Speaker for an existing Event
     *
     * @return true if Speaker is scheduled successfully
     */
    public boolean schedule_speaker(Speaker speaker, Talk talk, Event event){
        if(em.can_schedule_speaker(event,talk,speaker) && tm.speaker_can_be_scheduled(talk)){
            speaker.addTalk(talk);
            talk.setSpeaker(speaker);
            return true;
        }
        return false;
    }


}
