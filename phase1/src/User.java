import java.util.ArrayList;

public class User {
    private String name;
    private String password;
    private String email;
    private ArrayList<String> messages_sent;
    private ArrayList<String> messages_received;
    private ArrayList<String> events_attending;

    User(String name, String password, String email){
        this.name = name;
        this.password = password;
        this.email = email;
        this.messages_sent = new ArrayList<String>();
        this.messages_received = new ArrayList<String>();
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

    /* To send a message to another user*/
    protected void send_message(String message){
        this.messages_sent.add(message);
    }

    /* The message a user is supposed to receive*/
    protected void receive_message(String message){
        this.messages_received.add(message);
    }

    /* To add an event user is attending*/
    protected void add_event(String event){
        this.events_attending.add(event);
    }




}
