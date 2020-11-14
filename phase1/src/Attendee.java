import java.util.ArrayList;

public class Attendee extends User{
    private ArrayList<Event> eventsAttending;

    // Constructor for Attendee
    Attendee(String name, String email, String password) {
        super(name, password, email);
        this.eventsAttending = new ArrayList<Event>();
    }

    // Getters
    // To allow an Attendee to see all the events that they are attending
    public ArrayList<Event> getEventsAttending(){
        return this.eventsAttending;
    }

    // Setters
    // To allow Attendee to sign up for an Event
    protected void attendEvent(Event event){
        this.eventsAttending.add(event);
    }

    // To allow Attendee to cancel attendance at an Event
    protected void removeEvent(Event event) {
        this.eventsAttending.remove(event);
    }

    protected char userType(){
        return 'A';
    }
}
