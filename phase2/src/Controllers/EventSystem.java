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
import java.util.ArrayList;

public class EventSystem{
    private ConferenceManager cm;
    private EventManager em;
    private RoomManager rm;
    private UserManager um;

    /**
     * Creates an EventSystem with a ConferenceManager, EventManager, RoomManager, and UserManager.
     */
    public EventSystem() throws ClassNotFoundException, IOException {
        cm = new ConferenceSave().read();
        em = new EventSave().read();
        rm = new RoomSave().read();
        um = new UserSave().read();
    }

    public ConferenceManager getCm() {
        return cm;
    }

    public EventManager getEm() {
        return em;
    }

    public RoomManager getRm() {
        return rm;
    }

    public UserManager getUm() {
        return um;
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
     * @return true if event was created
     */
    public boolean addEvent(String type, String name, String description, String start, String end, String date,
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
     * Cancels Entities.Event
     * @param id the id of the Entities.Event to be cancelled
     * @return true if successful
     */
    public boolean cancel_event(Integer id) { //NOT FULLY IMPLEMENTED
        Event e = em.findEvent(id);
        if(e == null) {
            return false;
        }
        Conference c = cm.eventInConference(id);
        // cancel all attendee registrations
        ArrayList<User> attendees = um.findUsers(e.getAttendeeEmails());
        for(User a : attendees) {
            um.cancelRegistrationEvent((Attendee) a, e, c);
        }
        // cancel all organizers
        ArrayList<User> organizers = um.findUsers(e.getOrganizerEmails());
        for(User o : organizers) {
            um.cancelRegistrationEventOrganizer((Organizer) o, e);
        }
        // cancel all speakers
        if (e instanceof Talk) {
            Speaker speaker = (Speaker) um.findUser(((Talk) e).getSpeakerEmail());
            um.cancelRegistrationEventSpeaker(speaker, e);
        } else if (e instanceof Panel) {
            ArrayList<User> speakers = um.findUsers(((Panel) e).getSpeakerEmails());
            for(User s : speakers) {
                um.cancelRegistrationEventSpeaker((Speaker) s, e);
            }
        }
        // delete from any conference if applicable
        if(!cm.cancelEvent(c, id)) {
            return false;
        }
        // delete from em
        return em.deleteEvent(id);
    }

    /**
     * Returns true if Entities.Conference was created according to parameters
     * @param name of the conference
     * @param confDescription description of the conference
     * @param startTime the start time of the conference
     * @param endTime the end time of the conference
     * @param confDate the date of the conference
     * @return true if conference was created
     */
    public boolean addConference(String name, String confDescription, String startTime, String endTime, String confDate){
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
    public boolean schedule_room(String room_name, Integer eventId) throws IOException {
        Event event = em.findEvent(eventId);
        Room room = rm.find_room(room_name);
        if(room == null){
            return false;
        }
        if(!rm.is_room_booked(room, event) && event.getRoomName() == null && rm.can_fit_event(event,room)){
            rm.schedule_room(room, event);
            room.addBookings(event.getEventId(), em.getLocalDateTime(event.getEventDate(),event.getStartTime()),
                    em.getLocalDateTime(event.getEventDate(),event.getEndTime()));
            return true;
        }else{
            return false;
        }
    }

    /**
     * Schedules a Entities.Speaker for an existing Entities.Event
     * @param eventID the ID of the event that the Organizer would like to schedule a speaker for
     * @param speakerEmail the email of the Speaker that the Organizer wants to schedule for the event
     * @return true if Entities.Speaker is scheduled successfully and false otherwise
     */
    public boolean speakerSchedule(Integer eventID, String speakerEmail) {
        Entities.Event event = em.findEvent(eventID);
        String eventType = event.eventType();
        Entities.Speaker s = (Entities.Speaker) um.findUser(speakerEmail);
        if(em.canScheduleSpeaker(event,speakerEmail)){
            s.addEvent(eventID);
            switch (eventType){
                case "Entities.Panel":
                    event.setSpeaker(speakerEmail);

                case "Entities.Talk":
                    event.setSpeaker(speakerEmail);

                case "Entities.Party":
                    event.setSpeaker(speakerEmail);
            }
            return true;
        }
        return false;
    }


}
