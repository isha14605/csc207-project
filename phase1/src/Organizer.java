import java.util.ArrayList;

public class Organizer extends Attendee {
    protected ArrayList<Event> events_organizing;

    Organizer(String name, String email, String password) {
        super(name, password, email);
        this.events_organizing = new ArrayList<Event>();
    }

    public ArrayList<Event> getEvents_organizing() {
        return events_organizing;
    }

    protected void add_event(Event event) {
        this.events_organizing.add(event);
    }
}
