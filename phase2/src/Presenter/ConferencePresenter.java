package Presenter;

import Controllers.EventSystem;

import java.io.IOException;

public class ConferencePresenter {
    private EventSystem es;

    public ConferencePresenter(EventSystem e){
        this.es = e;
    }

    public String addingEvents(String type, String name, String description, String start, String end, String date,
                               int capacity, boolean event_only) throws IOException {
        if (es.addEvent(type,  name, description, start, end, date, capacity,  event_only)){
            return "Event added";
        } else {
            return "Wrong date/ time format. Try Again.";
        }

    }

    public String cancellingEvents(Integer id){
        if(es.cancel_event(id)){
            return "Event sucessfully cancelled.";
        } else {
            return "Try again! Event couldn't be deleted";
        }
    }

    public String addingRoom(String name, Integer capacity, String start, String end) throws IOException {
        if (es.addRoom(name, capacity, start, end)){
            return "Room successfully added.";
        } else {
            return "Room was not added. Try Again!";
        }
    }

    public String addingEventToConf(String conferenceName,Integer eventId) throws IOException {
        if (es.addEventToConference(conferenceName, eventId)){
            return "Event was successfully added to the conference";
        } else {
            return "Event couldn't be added to the conference";
        }
    }

    public String addConf(String name, String confDescription, String startTime, String endTime, String confDate){
        if (es.addConference(name, confDescription, startTime, endTime, confDate)){
            return "Conference was succesfully created!";
        } else {
            return "Conference was not created. Try Again!";
        }
    }

    public String cancellingEventInConf(String conferenceName,Integer eventId) throws IOException {
        if(es.cancelEventInConference(conferenceName, eventId)){
            return "Event in conference was succesfully cancelled.";
        } else {
            return "Event in conference was not cancelled";
        }
    }

    public String scheduleRoom(String room_name, Integer eventId) throws IOException {
        if(es.schedule_room(room_name, eventId)){
            return "Room was successfully scheduled for the event";
        } else {
            return "Room hasn't been created or Room in use";
        }
    }

    public String scheduleSpeaker(String speakerEmail, Integer eventId){
        if (es.scheduleSpeaker(speakerEmail, eventId)){
            return "Speaker succesfully scheduled for this event!";
        } else{
            return "Speaker can't be schedule";
        }
    }
}
