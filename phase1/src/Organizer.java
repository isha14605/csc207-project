import java.util.ArrayList;

public class Organizer extends Attendee {
    protected ArrayList<Event> eventsOrganizing;

    Organizer(String name, String email, String password) {
        super(name, password, email);
        this.eventsOrganizing = new ArrayList<Event>();
    }

    public ArrayList<Event> getEventsOrganizing() {
        return eventsOrganizing;
    }

    protected void addEvent(Event event) {
        this.eventsOrganizing.add(event);
    }

    protected char userType(){
        return 'O';
    }
}