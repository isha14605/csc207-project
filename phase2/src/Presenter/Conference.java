package Presenter;

import Controllers.EventSystem;

import java.io.IOException;

public class Conference {
    private EventSystem es;

    public Conference(EventSystem e){
        this.es = e;
    }

    public String addingEvents(String type, String name, String description, String start, String end, String date,
                               int capacity, boolean event_only, String c) throws IOException {
        if (es.add_event(type,  name, description, start, end, date, capacity,  event_only,  c)){
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
        if (es.add_room(name, capacity, start, end)){
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
}
