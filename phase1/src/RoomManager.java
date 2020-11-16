import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class RoomManager {
    private ArrayList<Room> rooms = new ArrayList<Room>();

    public RoomManager(){}

    protected void create_room(String name, Integer room_capacity, LocalTime open_time, LocalTime close_time){
        Room r = new Room(name, room_capacity, open_time, close_time);
        rooms.add(r);
    }
    protected void add_room(Room room, Event event){
        if (rooms.contains(room)) {
            return;
        }
        rooms.add(room);
    }
    protected void schedule_room(Room room, Event event){
        event.setEventRoom(room);
    }
    protected Room find_room(String name){
        for(Room room: rooms){
            if(room.getName().equals(name)){
                return room;
            }
        }
        return null;
    }
    protected boolean is_room_open(Talk talk, Room room){
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
    protected boolean is_room_booked(Room room, Event unbooked){
        for(Event booked: room.getBookings().keySet()){
            if(booked.getEventDate().equals(unbooked.getEventDate()))
                if(time_conflict(unbooked, booked)){
                    return true;
                }
        }
        return false;
    }
    protected ArrayList<Room> getRooms(){
        return rooms;
    }

    protected String roomToString(Room room){
        return new String("Room Name: " + room.getName() + ", open from " + room.getOpenTime() + " to "
                + room.getCloseTime());
    }

    protected boolean time_conflict(Talk scheduling, Event event){
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
    protected boolean time_conflict(Event event1, Event event2) {
        if (event1.getStartTime().equals((event2.getStartTime()))) {
            return true;
        } else if (event1.getStartTime().isAfter(event2.getStartTime()) &&
                event1.getStartTime().isBefore(event2.getEndTime())) {
            return true;
        } else return event1.getEndTime().isAfter(event2.getStartTime()) &&
                event1.getEndTime().isBefore(event2.getEndTime());
    }

    protected LocalDateTime get_localDateTime(LocalDate date, LocalTime time){
        return LocalDateTime.of(date, time);
    }
    protected LocalDateTime date_formatting_DT(String date){
        int len = date.length();
        if(len == 16){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return LocalDateTime.parse(date, formatter);
        }
        return null;
    }
    protected LocalDate date_formatting_date(String date){
        int len = date.length();
        if(len == 10){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(date, formatter);
        }
        return null;
    }
    protected LocalTime date_formatting_time(String date){
        int len = date.length();
        if(len == 5){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return LocalTime.parse(date, formatter);
        }
        return null;
    }
}
