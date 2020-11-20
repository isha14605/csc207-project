import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Controls the behaviour of event, rooms and talks and how the are updated and used.
 *
 * @author Chevoy Ingram
 * @version 1.0
 */

public class EventController implements Serializable{

    EventManager em = new EventManager();
    RoomManager rm = new RoomManager();
    TalkManager tm = new TalkManager();

    public EventController() throws ClassNotFoundException, IOException {
        em.events = em.readFile("EventSave.ser");
        rm = rm.readFile("RoomSave.ser");
        tm = tm.readFile("TalkSave.ser");
    }

    public void save() throws IOException {
        em.writeToFile("EventSave.ser");
        System.out.println("changes have been saved");
    }

    /**
     * Adds events to the system
     */
    public void add_event(String name, String description, String start, String end, String date) throws IOException {
        if(em.not_valid_format(em.date_formatting_time(start))|| em.not_valid_format(em.date_formatting_time(end))
        || em.not_valid_format(em.date_formatting_date(date))) {
            System.out.println("Invalid time or date! Event not added.");
        }else {
            em.create_event(name, description, em.date_formatting_time(start),
                    em.date_formatting_time(end), em.date_formatting_date(date));
            System.out.println("Event was added.");
            System.out.println();
        }
    }


    public ArrayList<Event> get_events(){
        return em.getEvents();
    }

    /**
     * Adds events to the system
     */
    public void add_room(String name, Integer capacity, String start, String end) throws IOException {
        if(em.not_valid_format(em.date_formatting_time(start))|| em.not_valid_format(em.date_formatting_time(end))) {
            System.out.println("Invalid time!");
        }else {
            rm.create_room(name, capacity, rm.date_formatting_time(start), rm.date_formatting_time(end));
            rm.writeToFile("RoomSave.ser");
            System.out.println("Room was added");
        }
    }

    /**
     * Adds events to the system
     */
    public ArrayList<Room> get_rooms(){return rm.getRooms();}

    /**
     * Adds talks to the system
     */
    public boolean add_talk(String start, String end, Integer event_id) throws IOException {

        if(em.date_formatting_time(start) == null || em.date_formatting_time(end) == null ){
            System.out.println("Invalid time");
            return false;
        }

        if(!em.event_exist(event_id)){
            System.out.println("Event doesn't exit");
            return false;
        }

        Talk talk = tm.create_talk(tm.date_formatting_time(start),
                tm.date_formatting_time(end), em.find_event(event_id));

        if (!rm.time_conflict(talk, em.find_event(event_id)) || em.within_event(talk,em.find_event(event_id))) {
            tm.add_talk(talk, em.find_event(event_id));
            tm.writeToFile("TalkSave.ser");
            return true;

        } else {
            System.out.println("Talk was not added due to time conflict");
            return false;
        }
    }

    /**
     * Assigns a room to event if it fits event parameters
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
            System.out.println("Room has Been Booked for event");
            em.writeToFile("EventSave");
        }else{
            System.out.println("Room Scheduled Due to time conflict");
        }
    }

    /**
     * Adds speaker to a talk and updates talk and speaker to the change
     */
    public void schedule_speaker(Speaker speaker, Talk talk, Event event){
        if(em.can_schedule_speaker(event,talk,speaker) && tm.speaker_can_be_scheduled(talk)){
            speaker.addTalk(talk);
            talk.setSpeaker(speaker);
        }
    }


}
