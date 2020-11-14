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
            if (event.getStart_time().equals(scheduled.getStart_time())){
                return;
            }
        }
        events.add(event);
    }
    protected Event findEvent(Integer event_id){
        for(Event event: events){
            if(event_id == event.getEvent_id()){
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
        event.add_talk(talk);
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
        speaker.add_talk(talk);
        talk.setSpeaker(speaker);
    }


    protected Room createRoom(String name, Integer room_capacity, LocalTime open_time, LocalTime close_time){
        return new Room(name, room_capacity, open_time, close_time);
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
        event.setEvent_room(room);
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
        if(talk.getStartTime().toLocalTime().isAfter(room.getOpen_time()) &&
                talk.getEndTime().toLocalTime().isBefore(room.getClose_time())){
            return true;
        }
        else if(talk.getStartTime().toLocalTime().equals(room.getOpen_time()) &&
                talk.getEndTime().toLocalTime().isBefore(room.getClose_time())){
            return true;
        }
        return talk.getStartTime().toLocalTime().isAfter(room.getOpen_time()) &&
                talk.getEndTime().toLocalTime().equals(room.getClose_time());
    }
    protected boolean isRoomBooked(Room room, Event unbooked){
        for(Event booked: room.getBookings().keySet()){
            if(booked.getEvent_date().equals(unbooked.getEvent_date()))
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
       if (event1.getStart_time().equals((event2.getStart_time()))) {
            return true;
       } else if (event1.getStart_time().isAfter(event2.getStart_time()) &&
                    event1.getStart_time().isBefore(event2.getEnd_time())) {
            return true;
        } else return event1.getEnd_time().isAfter(event2.getStart_time()) &&
                    event1.getEnd_time().isBefore(event2.getEnd_time());
    }
    protected LocalDateTime getLocalDateTime(LocalDate date, LocalTime time){
        return LocalDateTime.of(date, time);
    }

    protected ArrayList<Talk> getTalksAt(LocalDateTime time, Event event){
        ArrayList<Talk> same_time = new ArrayList<>();
        for(Talk talk: event.getTalks()){
            if(talk.getStartTime().equals(time)){
                same_time.add(talk);
            }
        }
        return same_time;
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