package Entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a room where events and talk will take place.
 * @author Farhad Siddique
 * @version 1.0
 */

public class Room implements Serializable {
    private final ArrayList<String> techAvailable;
    private String name;
    private int roomCapacity;
    private LocalTime openTime;
    private LocalTime closeTime;
    /* bookings is a HashMap where each key is an Entities.Event object and corresponding value is an ArrayList
     * containing startTime and endTime of the event consecutively.*/
    private HashMap<Integer, ArrayList<LocalDateTime>> bookings;

    // Constructor for Entities.Room
    /**
     * Creates a Entities.Room with the specified name, room capacity, room open time and room close time.
     * @param name the name of this room
     * @param roomCapacity the capacity of this room
     * @param openTime the time when the room opens
     * @param closeTime the time when the room closes
     */
    public Room(String name, Integer roomCapacity, LocalTime openTime, LocalTime closeTime) {
        this.name = name;
        this.roomCapacity = roomCapacity;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.bookings = new HashMap<Integer, ArrayList<LocalDateTime>>();
        this.techAvailable = new ArrayList<>();
    }

    // Getters
    /**
     * Returns a String that is the name of this room.
     * @return a String which is the name of this room.
     */
    public String getName() {return name;}

    /**
     * Returns an int that is the capacity of this room.
     * @return an int that is the capacity of this room.
     */
    public int getRoomCapacity() {return roomCapacity;}

    /**
     * Returns a LocalTime that is the open time of this room.
     * @return a LocalTime that is the open time of this room.
     */
    public LocalTime getOpenTime() {return openTime;}

    /**
     * Returns a LocalTime that is the close time of this room.
     * @return a LocalTime that is the close time of this room.
     */
    public LocalTime getCloseTime() {return closeTime;}

    /**
     * Returns a LocalTime that is the close time of this room.
     * @return a LocalTime that is the close time of this room.
     */
    public ArrayList<String> getTechAvailable() {
        return techAvailable;
    }

    /**
     * Returns all bookings i.e. a HashMap where each key is an Entities.Event object and corresponding value is an ArrayList
     * containing startTime and endTime of the event consecutively.
     * @return A HashMap where each key is an Entities.Event object and corresponding value is an ArrayList containing startTime
     * and endTime of the event consecutively.
     */
    public HashMap<Integer, ArrayList<LocalDateTime>> getBookings() {return bookings;}

    /**
     * Adds a booking to this room. This method should be called by Entities.Room Manager
     * when a room is added to an Entities.Event after checking availability.
     * @param eventId Id of an Entities.Event object for which this room is being booked for.
     * @param startTime a LocalDateTime which specifies start time and event date of booking/event.
     * @param endTime a LocalDateTime which specifies end time and event date of booking/event.
     */
    public void addBookings(Integer eventId, LocalDateTime startTime, LocalDateTime endTime) {
        ArrayList<LocalDateTime> time = new ArrayList<LocalDateTime>();
        time.add(startTime);
        time.add(endTime);
        this.bookings.put(eventId, time);
    }

    public void addTechRequirement(String tech){
        this.techAvailable.add(tech);
    }

    public void removeTech(String tech){
        this.techAvailable.remove(tech);
    }

    public void setRoomCapacity(int roomCapacity) {
        this.roomCapacity = roomCapacity;
    }
}
