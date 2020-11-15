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
        event.setEvent_room(room);
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
    protected boolean is_room_booked(Room room, Event unbooked){
        for(Event booked: room.getBookings().keySet()){
            if(booked.getEvent_date().equals(unbooked.getEvent_date()))
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
        return new String("Room Name: " + room.getName() + ", open from " + room.getOpen_time() + " to "
                + room.getClose_time());
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
        if (event1.getStart_time().equals((event2.getStart_time()))) {
            return true;
        } else if (event1.getStart_time().isAfter(event2.getStart_time()) &&
                event1.getStart_time().isBefore(event2.getEnd_time())) {
            return true;
        } else return event1.getEnd_time().isAfter(event2.getStart_time()) &&
                event1.getEnd_time().isBefore(event2.getEnd_time());
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
