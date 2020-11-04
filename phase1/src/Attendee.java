import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Attendee extends User{
    private Map<User, ArrayList<String>> messages_sent;
    private Map<User, ArrayList<String>> messages_received;
    private ArrayList<User> contacts;
    private ArrayList<Event> events_attending;

    Attendee(String name, String email, String password) {
        super(name, password, email);
        this.messages_sent = new HashMap<User, ArrayList<String>>();
        this.messages_received = new HashMap<User, ArrayList<String>>();
        this.contacts = new ArrayList<User>();
        this.events_attending = new ArrayList<Event>();
    }


}
