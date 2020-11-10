import java.util.ArrayList;

public class Attendee extends User{
    private ArrayList<Event> events_attending;

//  Constructor for Attendee
    Attendee(String name, String email, String password) {
        super(name, password, email);
        this.events_attending = new ArrayList<Event>();
    }

    public ArrayList<Event> getEvents_attending(){
        return this.events_attending;
    }

    /* To add an event user is attending*/
    protected void attend_event(Event event){
        this.events_attending.add(event);
    }

//  To remove an Event that the Attendee is attending
    protected void remove_event(Event event) {
        this.events_attending.remove(event);
    }
}
