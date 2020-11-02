import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Room {
    private String name;
    private int room_capacity;
    private LocalTime open_time;
    private LocalTime close_time;
    /* The values in bookings should contain start_time and end_time of event consecutively. Addition to
    * bookings should be made through Event Manager when a room is added to an Event*/
    protected HashMap<Event, ArrayList<LocalDateTime>> bookings;

    public Room(String name, Integer room_capacity, LocalTime open_time, LocalTime close_time) {
        this.name = name;
        this.room_capacity = room_capacity;
        this.open_time = open_time;
        this.close_time = close_time;
        this.bookings = new HashMap<Event, ArrayList<LocalDateTime>>();
    }

    /*Getters*/
    public String getName() {return name;}

    public int getRoom_capacity() {return room_capacity;}

    public LocalTime getOpen_time() {return open_time;}

    public LocalTime getClose_time() {return close_time;}

    public HashMap<Event, ArrayList<LocalDateTime>> getBookings() {return bookings;}
}
