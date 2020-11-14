import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    protected String name;
    protected String password;
    protected String email;
    protected Map<User, ArrayList<String>> messagesSent;
    /*For messages_sent, The key is the user they are sending a message to, value is the message*/
    protected Map<User, ArrayList<String>> messagesReceived;
    /*For messages_received, The key is the user who sent them a message, value is the message*/
    protected ArrayList<User> contacts;

    User(String name, String password, String email){
        this.name = name;
        this.password = password;
        this.email = email;
        this.messagesSent = new HashMap<User, ArrayList<String>>();
        this.messagesReceived = new HashMap<User, ArrayList<String>>();
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


    protected void addContact(User user){
        this.contacts.add(user);
        this.messagesSent.put(user, new ArrayList<String>());
        this.messagesReceived.put(user, new ArrayList<String>());
    }

    /* To send a message to another user*/
    protected void sendMessage(User who, String message){
        ArrayList<String> x = this.messagesSent.get(who);
        x.add(message);
        this.messagesSent.replace(who, x);
    }

    /* The message a user is supposed to receive*/
    protected void receiveMessage(User who, String message){
        ArrayList<String> x = this.messagesReceived.get(who);
        x.add(message);
        this.messagesSent.replace(who, x);
    }

    protected char userType(){
        return 'N';
    };


}
