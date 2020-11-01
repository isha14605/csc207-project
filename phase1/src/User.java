import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String name;
    private String password;
    private String email;
    private Map<User, ArrayList<String>> messages_sent ;
    /*For messages_sent, The key is the user they are sending a message to, value is the message*/
    private Map<User, ArrayList<String>> messages_received;
    /*For messages_received, The key is the user who sent them a message, value is the message*/
    private ArrayList<String> events_attending;
    private ArrayList<String> contacts;

    User(String name, String password, String email){
        this.name = name;
        this.password = password;
        this.email = email;
        this.messages_sent = new HashMap<User, ArrayList<String>>();
        this.messages_received = new HashMap<User, ArrayList<String>>();
        this.contacts = new ArrayList<String>();
        this.events_attending = new ArrayList<String>();
    }

    public String getName(){
        return this.name;
    }

    public String getPassword(){
        return this.password;
    }

    public String getEmail(){
        return this.email;
    }

    public ArrayList<String> getEvents_attending(){
        return this.events_attending;
    }

    protected void add_contact(User user){
        this.contacts.add(user.getName());
        this.messages_sent.put(user, new ArrayList<String>());
        this.messages_received.put(user, new ArrayList<String>());
    }

    /* To send a message to another user*/
    protected void send_message(User who, String message){
        ArrayList<String> x = this.messages_sent.get(who);
        x.add(message);
        this.messages_sent.replace(who, x);
    }

    /* The message a user is supposed to receive*/
    protected void receive_message(User who, String message){
        ArrayList<String> x = this.messages_received.get(who);
        x.add(message);
        this.messages_sent.replace(who, x);
    }

    /* To add an event user is attending*/
    protected void add_event(String event){
        this.events_attending.add(event);
    }


}
