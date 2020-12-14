package Controllers;

import Entities.*;
import Gateway.ConferenceSave;
import Gateway.EventSave;
import Gateway.RoomSave;
import Gateway.UserSave;
import UseCase.ConferenceManager;
import UseCase.EventManager;
import UseCase.RoomManager;
import UseCase.UserManager;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class EventSystem{
    private ConferenceManager cm;
    private EventManager em;
    private RoomManager rm;
    private final UserManager um;

    /**
     * EventController Constructor
     */
    public EventSystem() throws IOException {
        cm = new ConferenceSave().read();
        em = new EventSave().read();
        rm = new RoomSave().read();
        um = new UserSave().read();
    }

    public EventManager getEm() {
        return em;
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
    public boolean addEvent(String type, String name, String description, String start, String end, String date,
                            int capacity, boolean event_only) throws IOException {
        if(em.notValidFormat(em.dateFormattingTime(start))|| em.notValidFormat(em.dateFormattingTime(end))
        || em.notValidFormat(em.dateFormattingDate(date))) {
            return false;
        }
        if(em.dateFormattingDate(date).isBefore(LocalDate.now())){
            return false;
        }
        else {
            em.createEvent(type, name, description, em.dateFormattingTime(start),
                    em.dateFormattingTime(end), em.dateFormattingDate(date), capacity, event_only);
            new EventSave().save(em);
            return true;
        }
    }

    /**
     * Cancels Entities.Event
     * @param id the id of the Entities.Event to be cancelled
     * @return true if successful
     */

    public boolean cancel_event(Integer id) throws IOException { //NOT FULLY IMPLEMENTED
        Event e = em.findEvent(id);
        if(e == null) {
            return false;
        }
        Conference c = cm.eventInConference(id);
        // cancel all attendee registrations
        ArrayList<User> attendees = um.findUsers(e.getAttendeeEmails());
        for(User a : attendees) {
            um.cancelRegistrationEvent((Attendee) a, e, c);
            new UserSave().save(um);
        }
        // cancel all organizers
        ArrayList<User> organizers = um.findUsers(e.getOrganizerEmails());
        for(User o : organizers) {
            um.cancelRegistrationEventOrganizer((Organizer) o, e);
            new UserSave().save(um);
        }
        // cancel all speakers
        if (e instanceof Talk) {
            Speaker speaker = (Speaker) um.findUser(((Talk) e).getSpeakerEmail());
            um.cancelRegistrationEventSpeaker(speaker, e);
            new UserSave().save(um);
        } else if (e instanceof Panel) {
            ArrayList<User> speakers = um.findUsers(((Panel) e).getSpeakerEmails());
            for(User s : speakers) {
                um.cancelRegistrationEventSpeaker((Speaker) s, e);
            }
            new UserSave().save(um);
        }
        // delete from any conference if applicable
        if(!(c==null)&&!cm.cancelEvent(c, id)) {
            return false;
        }
        // delete from em
         if(em.deleteEvent(id)){
             new EventSave().save(em);
             return true;
         }
         return false;
    }



    public boolean addConference(String name, String confDescription, String startTime, String endTime, String confDate) throws IOException {
        for(String con: cm.getConferenceList()){
            if(con.equals(name)){
                return false;
            }
        }
        if(em.dateFormattingTime(endTime).isBefore(em.dateFormattingTime(startTime))){
            return false;
        }
        cm.addConference(name,confDescription,em.dateFormattingTime(startTime),
                em.dateFormattingTime(endTime),em.dateFormattingDate(confDate));
        new ConferenceSave().save(cm);
        new UserSave().save(um);
        return true;

    }
    /**
     * Returns existing Events
     *
     * @return ArrayList of Events
     */
    public boolean addRoom(String name, Integer capacity, String start, String end) throws IOException {
        if(em.notValidFormat(em.dateFormattingTime(start))|| em.notValidFormat(em.dateFormattingTime(end))) {
            return false;
        }if(!em.checkValidTime(start,end)){
            return false;
        }
        rm.create_room(name,capacity,em.dateFormattingTime(start),em.dateFormattingTime(end));
        new RoomSave().save(rm);
        return true;
    }

    public void addTech(String roomName, String tech) throws IOException {
        rm.find_room(roomName).addTechRequirement(tech);
        new RoomSave().save(rm);
    }

    public void addTechEvent(Integer event, String tech) throws IOException {

        if(!tech.equals("None")) {
            em = new EventSave().read();
            em.findEvent(event).addTechRequirements(tech);
            new EventSave().save(em);
        }
    }

    public void removeTech(String roomName, String tech) throws IOException {
        rm.find_room(roomName).removeTech(tech);
        new RoomSave().save(rm);
    }

    public void changeCap(String roomName, int new_cap) throws IOException {
        rm.find_room(roomName).setRoomCapacity(new_cap);
        new RoomSave().save(rm);
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
        em = new EventSave().read();
        cm = new ConferenceSave().read();
        if(em.findEvent(eventId).getStartTime().isBefore(cm.findConference(conferenceName).getStartTime())||
                em.findEvent(eventId).getEndTime().isAfter(cm.findConference(conferenceName).getEndTime())){
            return false;
        }
        if(cm.addEvent(cm.findConference(conferenceName),em.findEvent(eventId))){
            new ConferenceSave().save(cm);
            new EventSave().save(em);
            return true;
        }
        return false;
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
         if(cm.cancelEvent(cm.findConference(conferenceName),eventId)){
             new ConferenceSave().save(cm);
             new EventSave().save(em);
             return true;
         }
         return false;
    }

    /**
     * Schedules a Entities.Room for an existing Entities.Event
     */
    public boolean schedule_room(String room_name, Integer eventId) throws IOException {
        Event event = em.findEvent(eventId);
        Room room = rm.find_room(room_name);
        if(room == null){
            return false;
        }
        if(room.getRoomCapacity()<event.getAttendeeCapacity()){
            return false;
        }
        if(!event.getTechRequirements().isEmpty() &&!room.getTechAvailable().containsAll(event.getTechRequirements())){
            return false;
        }
        if(!is_room_booked(room, event) && event.getRoomName() == null && rm.can_fit_event(event,room)){
            rm.schedule_room(room, event);
            room.addBookings(event.getEventId(), em.getLocalDateTime(event.getEventDate(),event.getStartTime()),
                    em.getLocalDateTime(event.getEventDate(),event.getEndTime()));
            new EventSave().save(em);
            new RoomSave().save(rm);
            return true;
        }else{
            return false;
        }
    }

    /** Checks if room is booked and can be scheduled for a unbooked event
     * @param room room entity
     * @param unbooked Unbooked room Entity
     * */
    public boolean is_room_booked(Room room, Event unbooked) throws IOException {
        for(Integer booked: room.getBookings().keySet()){
            em = new EventSave().read();
            rm = new RoomSave().read();
            if(em.findEvent(booked).getEventDate().equals(unbooked.getEventDate()))
                if(rm.time_conflict(unbooked, em.findEvent(booked))){
                    return true;
                }
        }
        return false;
    }

    /**
     * Schedules a Entities.Speaker for an existing Entities.Event
     *
     * @return true if Entities.Speaker is scheduled successfully
     */

    public boolean speakerSchedule(Integer eventID, String speakerEmail) throws IOException {
        Event event = em.findEvent(eventID);
        String eventType = event.eventType();
        Speaker s = um.findSpeaker(speakerEmail);
        if(speakerTC(event,s)){
            return false;
        }
        if(em.canScheduleSpeaker(event,speakerEmail)){
            s.addEvent(eventID);
            switch (eventType){
                case "Panel":
                    event.setSpeaker(speakerEmail);
                    s.addEvent(eventID);

                case "Talk":
                    event.setSpeaker(speakerEmail);
                    s.addEvent(eventID);

                case "Party":
                    event.setSpeaker(speakerEmail);
            }
            new UserSave().save(um);
            new EventSave().save(em);
            return true;
        }
        return false;
    }

    public boolean speakerTC(Event event,Speaker speaker){
        for(Integer speaking: speaker.getEventsSpeaking()){
            if(em.timeConflict(event,em.findEvent(speaking))){
                return true;
            }
        }
        return false;
    }


    public ArrayList<String> usableRooms(Integer event) throws IOException {
        ArrayList<String> usable = new ArrayList<>();
        for(String rooms: rm.getRoomsString()){
            rm = new RoomSave().read();
            em = new EventSave().read();
            if(!rooms.equals("None")) {
                if (rm.can_fit_event(em.findEvent(event), rm.find_room(rooms)) &&
                        !rm.time_conflict(rm.find_room(rooms), em.findEvent(event))) {
                    usable.add(rooms);
                }
            }

        }
        return usable;
    }


}
