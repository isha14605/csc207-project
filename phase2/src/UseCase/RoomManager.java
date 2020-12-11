package UseCase;

import Entities.Conference;
import Entities.Event;
import Entities.Room;
import Gateway.EventSave;

import javax.swing.border.TitledBorder;
import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Manages Rooms and functionality
 *
 * @author Chevoy Ingram
 * @version 1.0
 */

public class RoomManager implements Serializable {
    private final ArrayList<Room> rooms = new ArrayList<>();
    EventManager em;
    private ArrayList<String> techOptions;

    public RoomManager(EventManager em){
        techOptions = new ArrayList<>();
        techOptions.add("None");
        techOptions.add("Smart Board");
        techOptions.add("Projector");
        techOptions.add("Demo Kits");
        this.em = em;
    }

/** Creates a new room entity based on parameters set below
 *@param name Name of the event.
 * @param room_capacity Number of uses allowed to join the room
 * @param open_time Time the room is open in LocalTime
 * @param close_time Time the room closes in LocalTime
 * */
    public void create_room(String name, Integer room_capacity, LocalTime open_time, LocalTime close_time){
        Room r = new Room(name, room_capacity, open_time, close_time);
        rooms.add(r);
    }

    /** Sets The event parameter of room entity
     * @param room room entity
     * @param event event entity
     * */
    public void schedule_room(Room room, Event event){
        event.setRoomName(room.getName());
    }

    /** Finds and returns room by getting the name
     * @param name name of the room
     * @return returns the Entities.Room if it exists returns null if room doesn't exist*/
    public Room find_room(String name){
        for(Room room: rooms){
            if(room.getName().equals(name)){
                return room;
            }
        }
        return null;
    }

    /** Checks if event can be scheduled for this room;
     * @param event event that room is be scheduled to.
     * @param room room that is being scheduled.
     * @return a list of all the rooms that have been saved*/
    public boolean can_fit_event(Event event, Room room){
        if(event.getStartTime().isBefore(room.getOpenTime())){
            return false;
        }
        else return !event.getEndTime().isAfter(room.getCloseTime());
    }

    /** Gets a list of rooms that user has access to.
     * @return a list of all the rooms that have been saved*/
    public ArrayList<Room> getRooms(){
        return rooms;
    }

    /** Takes a room and converts it into readable strings
     * @param room
     * @return String representation of the room*/
    public String roomToString(Room room){
        return "Room Name: " + room.getName() + ", open from " + room.getOpenTime() + " to "
                + room.getCloseTime() + "\n";
    }


    public String bookingToString(Room room){
        if(room.getBookings().size()==0){
            return "No Bookings for this room";
        }
        String m = "";
        for(Integer event: room.getBookings().keySet()){
            m = m + "\nEvent Id - " + event + "\n Booked from: " + room.getBookings().get(event);
        }
        return m;
    }

    /** Checks if there is a time conflict between a event and talk that wants to be scheduled
     * @param event1 the talk the is being scheduled
     * @param event2 the event the talk wants to be added to.
     * @return true if there is a time conflict within events*/
    public boolean time_conflict(Event event1, Event event2) {

        if (event1.getStartTime().equals((event2.getStartTime()))) {
            return true;
        } else if (event1.getStartTime().isAfter(event2.getStartTime()) &&
                event1.getStartTime().isBefore(event2.getEndTime())) {
            return true;
        } else return event1.getEndTime().isAfter(event2.getStartTime()) &&
                event1.getEndTime().isBefore(event2.getEndTime());
    }

    public boolean time_conflict(Room room, Event event1) {
        for (Integer event : room.getBookings().keySet()) {
            Event event2 = em.findEvent(event);
            if(event2==null){
                break;
            }
            if (event1.getStartTime().equals((event2.getStartTime()))) {
                return true;
            } else if (event1.getStartTime().isAfter(event2.getStartTime()) &&
                    event1.getStartTime().isBefore(event2.getEndTime())) {
                return true;
            }
            else if(event1.getEndTime().isAfter(event2.getStartTime()) &&
                    event1.getEndTime().isBefore(event2.getEndTime())){
                return true;
            }
        }
        return false;
    }

    /** Filters user input by checking and converting input into a readable LocalTime
     * @param date the string representation of a time
     @return  the local time of this string representation  */
    public LocalTime date_formatting_time(String date){
        int len = date.length();
        if(len == 5){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return LocalTime.parse(date, formatter);
        }
        return null;
    }

    public void addTechOptions(String tech){
        techOptions.add(tech);
    }

    public ArrayList<String> getTechOptions(){
        return techOptions;
    }

    public boolean meetsRequirements(String roomName,Integer eventId){
        return find_room(roomName).getTechAvailable().containsAll(em.findEvent(eventId).getTechRequirements());
    }

    public void removeTech(Room r,String tech){
        removeTech(r,tech);
    }

    public ArrayList<String> getRoomsString() {
        ArrayList<String> conferenceNames = new ArrayList<>();
        if(rooms.size()==0){
            conferenceNames.add("None");
            return conferenceNames;
        }
        for(Room booked: rooms){
            conferenceNames.add(booked.getName());
        }
        return conferenceNames;
    }
}
