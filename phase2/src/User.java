import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a User who can be an Attendee or an Organizer.
 * @author Tanya Thaker
 * @version 1.0
 */

public class User {
    protected String name;
    protected String password;
    protected String email;
    protected HashMap<User, ArrayList<String>> messagesSent;
    /*For messages_sent, The key is the user they are sending a message to, value is the message*/
    protected HashMap<User, ArrayList<String>> messagesReceived;
    /*For messages_received, The key is the user who sent them a message, value is the message*/
    protected ArrayList<User> contacts;

    // Constructor for User
    /**
     * Creates an User with the specified name, email, and password.
     * @param name the name of the specified User
     * @param email the email of the specified User
     * @param password the password of the specified User
     */

    User(String name, String password, String email){
        this.name = name;
        this.password = password;
        this.email = email;
        this.messagesSent = new HashMap<User, ArrayList<String>>();
        this.messagesReceived = new HashMap<User, ArrayList<String>>();
        this.contacts = new ArrayList<User>();
    }

    // Getters for User
    /**
     * Returns an String that is the name of this user.
     * @return a string which is the name of this user.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Returns an String that is the password of this users account.
     * @return a string which is the password of this users account.
     */
    public String getPassword(){
        return this.password;
    }

    /**
     * Returns an String that is the email of this users account.
     * @return a string which is the email of this users account.
     */
    public String getEmail(){
        return this.email;
    }

    /**
     * Returns an ArrayList of User objects who are in present in this users contacts.
     * @return an ArrayList of User objects who are in present in this users contacts.
     */
    public ArrayList<User> getContacts(){return this.contacts;}

    public Map<User, ArrayList<String>> getMessagesSent(){return this.messagesSent;};

    public Map<User, ArrayList<String>> getMessagesReceived(){return this.messagesReceived;};


    /**
     * Adds a contact to the users contact list.
     * @param user the user that is added to their contact.
     */
    protected void addContact(User user){
        if (!(this.contacts.contains(user))){
            this.contacts.add(user);
            this.messagesSent.put(user, new ArrayList<String>());
            this.messagesReceived.put(user, new ArrayList<String>());
            user.add_opp_contact(this);
        }
    }

    private void add_opp_contact(User user){
        this.contacts.add(user);
        this.messagesSent.put(user, new ArrayList<String>());
        this.messagesReceived.put(user, new ArrayList<String>());
    }

    /* To send a message to another user*/
    /**
     * Sends a message to their contact.
     * @param who the recipient of the message the user wants to send.
     * @param  message is the content of the message they want to send.
     */
    protected void sendMessage(User who, String message){
        ArrayList<String> x = this.messagesSent.get(who);
        ArrayList<String> y = new ArrayList<String>(x);
        y.add(message);
        this.messagesSent.replace(who, y);
//        if (!(x == null)){
//            ArrayList<String> y = new ArrayList<String>(x);
//            y.add(message);
//            this.messagesSent.replace(who, y);
//        } else {
//            ArrayList<String> m = new ArrayList<String>();
//            m.add(message);
//            this.messagesSent.put(who, m);
//        }

    }

    /* The message a user is supposed to receive*/
    /**
     * A message is received from another user.
     *@param who the sender of the message.
     *@param  message is the content of the message they received.
     */
    protected void receiveMessage(User who, String message){
        ArrayList<String> x = this.messagesReceived.get(who);
        ArrayList<String> y = new ArrayList<String>(x);
        y.add(message);
        this.messagesReceived.replace(who, y);
//        if (!(x == null)){
//            ArrayList<String> y = new ArrayList<String>(x);
//            y.add(message);
//            this.messagesReceived.replace(who, y);
//        } else {
//            ArrayList<String> m = new ArrayList<String>();
//            m.add(message);
//            this.messagesReceived.put(who, m);
//        }
    }

    /**
     * Returns a character that determines what kind of a user this is.
     * @return a character that determines if this user is an attendee/ speaker.
     */
    protected char userType(){
        return 'N';
    };


}