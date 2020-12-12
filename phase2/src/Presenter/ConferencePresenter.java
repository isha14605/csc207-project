package Presenter;

import Controllers.EventSystem;
import Gateway.ConferenceSave;
import Gateway.EventSave;
import Gateway.RoomSave;
import Gateway.UserSave;
import UseCase.ConferenceManager;
import UseCase.EventManager;
import UseCase.RoomManager;
import UseCase.UserManager;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalDate;

public class ConferencePresenter {
    private ConferenceManager cm;
    private EventManager em;
    private RoomManager rm;
    private final UserManager um;
    private EventSystem es;

    public ConferencePresenter(EventSystem e) throws IOException {
        cm = new ConferenceManager();
        em = new EventManager();
        rm = new RoomManager(em);
        um = new UserManager();
        this.es = e;
    }

    public void addingEvents(String type, String name, String description, String start, String end, String date,
                               int capacity, boolean event_only){
        if(em.notValidFormat(em.dateFormattingTime(start))|| em.notValidFormat(em.dateFormattingTime(end))
                || em.notValidFormat(em.dateFormattingDate(date))) {
            JOptionPane.showMessageDialog(null,"Invalid Date Format");
        }
        if(em.dateFormattingDate(date).isBefore(LocalDate.now())){
            JOptionPane.showMessageDialog(null,"Invalid Date Format, No dates before today's date!");
        }
        else{
            JOptionPane.showMessageDialog(null, "Event has been added");
        }

    }


    public void addingRoom(String name, Integer capacity, String start, String end){
        if(em.notValidFormat(em.dateFormattingTime(start))|| em.notValidFormat(em.dateFormattingTime(end))) {
            JOptionPane.showMessageDialog(null,"Invalid Date Format");
        }if(!em.checkValidTime(start,end)){
            JOptionPane.showMessageDialog(null,"Invalid Date Format, Start time cannot be after " +
                    "end time");
        }
    }

    public String addingEventToConf(String conferenceName,Integer eventId) throws IOException {
        if (es.addEventToConference(conferenceName, eventId)){
            return "Event was successfully added to the conference";
        } else {
            return "Event couldn't be added to the conference";
        }
    }

    public String addConf(String name, String confDescription, String startTime, String endTime, String confDate) throws IOException {
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

    public String scheduleSpeaker(String speakerEmail, Integer eventId) throws IOException {
        if (es.speakerSchedule(eventId,speakerEmail)){
            return "Speaker succesfully scheduled for this event!";
        } else{
            return "Speaker can't be schedule";
        }
    }
}
