import java.util.ArrayList;

public class Organizer extends Attendee {
    protected ArrayList<String> events_organizing;

    Organizer(String name, String email, String password) {
        super(name, password, email);
        this.events_organizing = new ArrayList<String>();
    }

    public ArrayList<String> getEvents_organizing() {
        return events_organizing;
    }

    protected void add_event(String event) {
        this.events_organizing.add(event);
    }
}
