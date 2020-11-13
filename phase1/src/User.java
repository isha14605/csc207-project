import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    protected String name;
    protected String password;
    protected String email;
    protected Map<User, ArrayList<String>> messages_sent ;
    /*For messages_sent, The key is the user they are sending a message to, value is the message*/
    protected Map<User, ArrayList<String>> messages_received;
    /*For messages_received, The key is the user who sent them a message, value is the message*/
    protected ArrayList<User> contacts;

    User(String name, String password, String email){
        this.name = name;
        this.password = password;
        this.email = email;
        this.messages_sent = new HashMap<User, ArrayList<String>>();
        this.messages_received = new HashMap<User, ArrayList<String>>();
        this.contacts = new ArrayList<User>();
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

    public ArrayList<User> getContacts(){return this.contacts;}


    protected void add_contact(User user){
        this.contacts.add(user);
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

    protected char userType(){
        return 'N';
    };


}
