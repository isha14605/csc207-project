import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Room {
    private String name;
    private int roomCapacity;
    private LocalTime openTime;
    private LocalTime closeTime;
    /* The values in bookings contain start_time and end_time of event consecutively*/
    private HashMap<Event, ArrayList<LocalDateTime>> bookings;

    public Room(String name, Integer roomCapacity, LocalTime openTime, LocalTime closeTime) {
        this.name = name;
        this.roomCapacity = roomCapacity;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.bookings = new HashMap<Event, ArrayList<LocalDateTime>>();
    }

    /*Getters*/
    public String getName() {return name;}

    public int getRoomCapacity() {return roomCapacity;}

    public LocalTime getOpenTime() {return openTime;}

    public LocalTime getCloseTime() {return closeTime;}

    public HashMap<Event, ArrayList<LocalDateTime>> getBookings() {return bookings;}

    /*This method should be called by Event Manager/Room Manager when a room is added to an Event*/
    protected void addBookings(Event event, LocalDateTime start_time, LocalDateTime end_time) {
        ArrayList<LocalDateTime> time = new ArrayList<LocalDateTime>();
        time.add(start_time);
        time.add(end_time);
        this.bookings.put(event, time);
    }
}
