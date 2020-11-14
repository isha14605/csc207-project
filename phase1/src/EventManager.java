import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class EventManager {
    protected static ArrayList<Event> events = new ArrayList<Event>();
    protected static ArrayList<Room> rooms = new ArrayList<Room>();
    protected static ArrayList<Talk> talks = new ArrayList<>();

    protected EventManager() { }

    /* code use case classes that directly add/ create new entities */
    protected void addEvent(String name, String desc, LocalDateTime start, LocalDateTime end) {

        Event event = new Event(name, desc, start, end);
        if (events.contains(event)) {
            return;
        }
        for(Event scheduled: events){
            if (event.getStartTime().equals(scheduled.getStartTime())){
                return;
            }
        }
        events.add(event);
    }
    protected Event findEvent(Integer eventId){
        for(Event event: events){
            if(eventId == event.getEventId()){
                return event;
            }
        }
        return null;
    }

    protected Talk createTalk(LocalDateTime startTime, LocalDateTime endTime, Event event){
        Talk talk = new Talk(startTime, endTime, event);
        talks.add(talk);
        return talk;
    }
    protected void addTalk(Talk talk, Event event) {
        for (Talk scheduled : event.getTalks()) {
            if (talk.getStartTime().equals(scheduled.getStartTime())) {
                return;
            }
        }
        event.addTalk(talk);
    }

    protected void scheduleSpeaker(Speaker speaker, Talk talk, Event event){
        if(talk.getSpeaker() != null || Objects.equals(talk.getSpeaker(), speaker)){
            return;
        }
        for(Talk scheduled: getTalksAt(talk.getStartTime(), event)){
            if(speaker.equals(scheduled.getSpeaker())){
                return;
            }
        }
        speaker.addTalk(talk);
        talk.setSpeaker(speaker);
    }


    protected Room createRoom(String name, Integer roomCapacity, LocalTime openTime, LocalTime closeTime){
        return new Room(name, roomCapacity, openTime, closeTime);
    }
    protected void addRoom(Room room, Event event){
        if (rooms.contains(room)) {
            return;
        }
        rooms.add(room);
    }
    protected boolean removeRoom(Room room, Event event){
        if (rooms.contains(room)) {
            rooms.remove(room);
            return true;
        }
        return false;
    }
    protected void scheduleRoom(Room room, Event event){
        event.setEventRoom(room);
    }
    protected Room findRoom(String name){
        for(Room room: rooms){
            if(room.getName().equals(name)){
                return room;
            }
        }
        return null;
    }
    protected boolean isRoomOpen(Talk talk, Room room){
        if(talk.getStartTime().toLocalTime().isAfter(room.getOpenTime()) &&
                talk.getEndTime().toLocalTime().isBefore(room.getCloseTime())){
            return true;
        }
        else if(talk.getStartTime().toLocalTime().equals(room.getOpenTime()) &&
                talk.getEndTime().toLocalTime().isBefore(room.getCloseTime())){
            return true;
        }
        return talk.getStartTime().toLocalTime().isAfter(room.getOpenTime()) &&
                talk.getEndTime().toLocalTime().equals(room.getCloseTime());
    }
    protected boolean isRoomBooked(Room room, Event unbooked){
        for(Event booked: room.getBookings().keySet()){
            if(booked.getEventDate().equals(unbooked.getEventDate()))
            if(timeConflict(unbooked, booked)){
                return true;
            }
        }
        return false;
    }

    protected boolean timeConflict(Talk scheduling, Event event){
        for(Talk scheduled: event.getTalks()){
            if(scheduling.getStartTime().isEqual(scheduled.getStartTime())){
                return true;
            }
            else if(scheduling.getStartTime().isAfter(scheduled.getStartTime()) &&
                    scheduling.getStartTime().isBefore(scheduled.getEndTime())){
                return true;
            }
            else if(scheduling.getEndTime().isAfter(scheduled.getStartTime()) &&
                    scheduling.getEndTime().isBefore(scheduled.getStartTime())){
                return true;
            }
        }
        return false;
    }

    protected boolean timeConflict(Event event1, Event event2) {
       if (event1.getStartTime().equals((event2.getStartTime()))) {
            return true;
       } else if (event1.getStartTime().isAfter(event2.getStartTime()) &&
                    event1.getStartTime().isBefore(event2.getEndTime())) {
            return true;
        } else return event1.getEndTime().isAfter(event2.getStartTime()) &&
                    event1.getEndTime().isBefore(event2.getEndTime());
    }
    protected LocalDateTime getLocalDateTime(LocalDate date, LocalTime time){
        return LocalDateTime.of(date, time);
    }

    protected ArrayList<Talk> getTalksAt(LocalDateTime time, Event event){
        ArrayList<Talk> sameTime = new ArrayList<>();
        for(Talk talk: event.getTalks()){
            if(talk.getStartTime().equals(time)){
                sameTime.add(talk);
            }
        }
        return sameTime;
    }

    protected LocalDateTime dateFormattingDT(String date){
        int len = date.length();
        if(len == 16){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return LocalDateTime.parse(date, formatter);
        }
        return null;
    }
    protected LocalDate dateFormattingDate(String date){
        int len = date.length();
        if(len == 10){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(date, formatter);
        }
        return null;
    }
    protected LocalTime dateFormattingTime(String date){
        int len = date.length();
        if(len == 5){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return LocalTime.parse(date, formatter);
        }
        return null;
    }

}