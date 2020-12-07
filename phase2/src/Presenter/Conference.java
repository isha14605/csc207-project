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
}
