

public class Attendee extends User{

//  Constructor for Attendee
    Attendee(String name, String email, String password) {
        super(name, password, email);
    }

//  To remove an Event that the Attendee is attending
    protected void remove_event(String event) {
        this.events_attending.remove(event);
    }
}
