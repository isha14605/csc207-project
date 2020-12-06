package Controllers;

import Entities.Event;
import Entities.Room;
import Gateway.ConferenceSave;
import Gateway.EventSave;
import Gateway.RoomSave;
import Gateway.UserSave;
import UseCase.ConferenceManager;
import UseCase.EventManager;
import UseCase.RoomManager;
import UseCase.UserManager;

import java.io.IOException;

public class EventSystem{
    private ConferenceManager cm;
    private EventManager em;
    private RoomManager rm;
    private UserManager um;

    /**
     * EventController Constructor
     */
    public EventSystem() throws ClassNotFoundException, IOException {
        this.cm = new ConferenceSave().read();
        this.em = new EventSave().read();
        this.rm = new RoomSave().read();
        this.um = new UserSave().read();

    }

    /**
     * Adds Entities.Event according to given parameters
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
        if(em.notValidFormat(em.dateFormattingTime(start))|| em.notValidFormat(em.dateFormattingTime(end))
        || em.notValidFormat(em.dateFormattingDate(date))) {
            return false;
        }else {
            em.createEvent(type, name, description, em.dateFormattingTime(start),
                    em.dateFormattingTime(end), em.dateFormattingDate(date), capacity, event_only);
            return true;
        }
    }

    /**
     * Returns existing Events
     *
     * @return ArrayList of Events
     */
    public boolean add_room(String name, Integer capacity, String start, String end) throws IOException {
        if(em.notValidFormat(em.dateFormattingTime(start))|| em.notValidFormat(em.dateFormattingTime(end))) {
            return false;
        }else {
            rm.create_room(name, capacity, rm.date_formatting_time(start), rm.date_formatting_time(end));
            return true;
        }
    }


    /**
     * Returns true if the event was added to the conference
     * @param conferenceName the name of the conference
     * @param eventId the id of the event  to be added to the conference
     * @return true if event was added to conference
     * @throws IOException
     */
    public boolean addEventToConference(String conferenceName,Integer eventId) throws IOException {
        if (cm.conferenceExists(conferenceName) && em.findEvent(eventId) == null){
            return false;
        }
        return cm.addEvent(cm.findConference(conferenceName),eventId);
    }

    /**
     * Returns true if event was successfully cancelled from the conference
     * @param conferenceName the name of the conference
     * @param eventId the id of the event
     * @return true if event was successfully cancelled
     * @throws IOException
     */
    public boolean cancelEventInConference(String conferenceName,Integer eventId) throws IOException{
        if (cm.conferenceExists(conferenceName) && em.findEvent(eventId) == null){
            return false;
        }
        return cm.cancelEvent(cm.findConference(conferenceName),eventId);
    }

    /**
     * Schedules a Entities.Room for an existing Entities.Event
     */
    public void schedule_room(String room_name, Integer eventId) throws IOException {
        Event event = em.findEvent(eventId);
        Room room = rm.find_room(room_name);
        if(room == null){
            System.out.println("Entities.Room doesn't exit");
            return;
        }
        if(!rm.is_room_booked(room, event) && event.getRoomName() == null && rm.can_fit_event(event,room)){
            rm.schedule_room(room, event);
            room.addBookings(event.getEventId(), em.getLocalDateTime(event.getEventDate(),event.getStartTime()),
                    em.getLocalDateTime(event.getEventDate(),event.getEndTime()));
        }else{
            System.out.println("Entities.Room not scheduled due to time conflict");
        }
    }

    /**
     * Schedules a Entities.Speaker for an existing Entities.Event
     *
     * @return true if Entities.Speaker is scheduled successfully
     */
    // Need to fix - Isha
    public boolean schedule_speaker(String speakerEmail, Integer eventId){
        Entities.Event event = em.findEvent(eventId);
        String eventType = event.eventType();
        Entities.Speaker s = (Entities.Speaker) um.findUser(speakerEmail);
        if(em.canScheduleSpeaker(event,speakerEmail)){
            s.addEvent(event.getEventId()); // fix calling method on entity
            switch (eventType){
                // fix this
                case "Entities.Panel":
                    event.setSpeaker(speakerEmail);

                case "Entities.Talk":
                    event.setSpeaker(speakerEmail);

                case "Entities.Party":

            }
            return true;
        }
        return false;
    }


}
