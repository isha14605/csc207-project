import java.util.ArrayList;

public class EventController {
    private final EventManager em;
    private final RoomManager rm;
    private final TalkManager tm;

    public EventController(){
        this.em = new EventManager();
        this.rm = new RoomManager();
        this.tm = new TalkManager();
    }

    public void add_event(String name, String description, String start, String end, String date){
        if(em.not_valid_format(em.date_formatting_time(start))|| em.not_valid_format(em.date_formatting_time(end))
        || em.not_valid_format(em.date_formatting_date(date))) {
            System.out.println("Invalid time or date!");
        }
        em.create_event(name, description, em.date_formatting_time(start),
                em.date_formatting_time(end), em.date_formatting_date(date));
        System.out.println("Event was added.");
    }

    public ArrayList<Event> get_events(){
        return em.getEvents();
    }

    public void add_room(String name, Integer capacity, String start, String end){
        if(em.not_valid_format(em.date_formatting_time(start))|| em.not_valid_format(em.date_formatting_time(end))) {
            System.out.println("Invalid time!");
        }
        rm.create_room(name, capacity, rm.date_formatting_time(start), rm.date_formatting_time(end));
        System.out.println("Room was added");

    }

    public ArrayList<Room> get_rooms(){return rm.getRooms();}

    public void add_talk(String start, String end, Integer event_id){
        Talk talk = tm.create_talk(tm.date_formatting_DT(start),
                tm.date_formatting_DT(end), em.find_event(event_id));
        if(!rm.time_conflict(talk,em.find_event(event_id)) && rm.is_room_open(talk,
                em.find_event(event_id).getEventRoom())){
            tm.add_talk(talk, em.find_event(event_id));
            System.out.println("Talk was added to event");
        }
        System.out.println("Talk was not added due to time conflict");
    }

    public void schedule_room(String room_name, Integer event_id){
        Event event = em.find_event(event_id);
        Room room = rm.find_room(room_name);
        if(!rm.is_room_booked(room, event) && event.getEventRoom() == null){
            rm.schedule_room(room, event);
            room.addBookings(event, em.get_localDateTime(event.getEventDate(),event.getStartTime()),
                    em.get_localDateTime(event.getEventDate(),event.getEndTime()));
            System.out.println("Room has Been Booked for event");
        }
    }

    public void schedule_speaker(Speaker speaker, Talk talk, Event event){
        if(em.can_schedule_speaker(event,talk,speaker) && tm.speaker_can_be_scheduled(talk)){
            speaker.addTalk(talk);
            talk.setSpeaker(speaker);
        }
    }
}
