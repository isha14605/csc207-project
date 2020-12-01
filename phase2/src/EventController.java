import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class EventController implements Serializable{
    ConferenceManager cm = new ConferenceManager();
    EventManager em = new EventManager();
    RoomManager rm = new RoomManager(em);
    UserManager um = new UserManager();

    /**
     * EventController Constructor
     */
    public EventController() throws ClassNotFoundException, IOException {
        rm = rm.readFile("RoomSave.ser");

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
    public boolean add_event(String type, String name, String description, String start, String end, String date,
                             int capacity, boolean event_only) throws IOException {
        if(em.not_valid_format(em.date_formatting_time(start))|| em.not_valid_format(em.date_formatting_time(end))
        || em.not_valid_format(em.date_formatting_date(date))) {
            return false;
        }else {
            em.create_event(type, name, description, em.date_formatting_time(start),
                    em.date_formatting_time(end), em.date_formatting_date(date), capacity, event_only);
            em.writeToFile("EventSave.ser");
            return true;
        }
    }

    /**
     * Returns existing Events
     *
     * @return ArrayList of Events
     */
    public boolean add_room(String name, Integer capacity, String start, String end) throws IOException {
        if(em.not_valid_format(em.date_formatting_time(start))|| em.not_valid_format(em.date_formatting_time(end))) {
            return false;
        }else {
            rm.create_room(name, capacity, rm.date_formatting_time(start), rm.date_formatting_time(end));
            rm.writeToFile("RoomSave.ser");
            return true;
        }
    }


    /**
     * Adds Talk to an existing Event
     *
     * @return true if talk is added successfully
     */
    public boolean addEventToConference(String conferenceName,Integer eventId) throws IOException {
        for(Integer schedule: cm.findConference(conferenceName).getEventIds()){
            if(em.time_conflict(em.find_event(schedule), em.find_event(eventId))){
                return false;
            }
            else{
                cm.findConference(conferenceName).addEvent(eventId);
                cm.findConference(conferenceName).addEventName(cm.findConference(conferenceName).getName());
                return true;
            }
        }
        return false;
    }

    /**
     * Schedules a Room for an existing Event
     */
    public void schedule_room(String room_name, Integer eventId) throws IOException {
        Event event = em.find_event(eventId);
        Room room = rm.find_room(room_name);
        if(room == null){
            System.out.println("Room doesn't exit");
            return;
        }
        if(!rm.is_room_booked(room, event) && event.getEventRoom() == null && rm.can_fit_event(event,room)){
            rm.schedule_room(room, event);
            room.addBookings(event, em.get_localDateTime(event.getEventDate(),event.getStartTime()),
                    em.get_localDateTime(event.getEventDate(),event.getEndTime()));
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
    public boolean schedule_speaker(String speakerEmail, Integer eventId){
        Event event = em.find_event(eventId);
        String eventType = event.eventType();
        Speaker s = (Speaker) um.findUser(speakerEmail);
        if(em.can_schedule_speaker(event,s)){
            s.addTalk(event.getEventId());
            switch (eventType){
                case "Panel":
                    event.setSpeaker(speakerEmail);

                case "Talk":
                    event.setSpeaker(speakerEmail);

                case "Party":

            }
            return true;
        }
        return false;
    }


}
