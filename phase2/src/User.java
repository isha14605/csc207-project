import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a User who can be an Attendee or an Organizer.
 * @author Tanya Thaker
 * @version 1.0
 */

public abstract class User{
    protected String name;
    protected String password;
    protected String email;
    protected HashMap<String, ArrayList<String>> messagesSent;
    /*For messages_sent, The key is the user they are sending a message to, value is the message*/
    protected HashMap<String, ArrayList<String>> messagesReceived;
    /*For messages_received, The key is the user who sent them a message, value is the message*/
    protected ArrayList<String> contacts;

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
        this.messagesSent = new HashMap<String, ArrayList<String>>();
        this.messagesReceived = new HashMap<String, ArrayList<String>>();
        this.contacts = new ArrayList<String>();
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
    public ArrayList<String> getContacts(){return this.contacts;}

    public Map<String, ArrayList<String>> getMessagesSent(){return this.messagesSent;};

    public Map<String, ArrayList<String>> getMessagesReceived(){return this.messagesReceived;};


    /**
     * Adds a contact to the users contact list.
     * @param email of the user that is added to their contact.
     */
    protected void addContact(String email){
        this.contacts.add(email);
        this.messagesSent.put(email, new ArrayList<String>());
        this.messagesReceived.put(email, new ArrayList<String>());
    }

    /**
     * Sends a message to their contact.
     * @param email the recipient of the message the user wants to send.
     * @param  message is the content of the message they want to send.
     */
    protected void sendMessage(String email, String message){
        ArrayList<String> x = this.messagesSent.get(email);
        ArrayList<String> y = new ArrayList<String>(x);
        y.add(message);
        this.messagesSent.replace(email, y);
    }

    /* The message a user is supposed to receive*/
    /**
     * A message is received from another user.
     *@param email the sender of the message.
     *@param  message is the content of the message they received.
     */
    protected void receiveMessage(String email, String message){
        ArrayList<String> x = this.messagesReceived.get(email);
        ArrayList<String> y = new ArrayList<String>(x);
        y.add(message);
        this.messagesReceived.replace(email, y);
    }

    /**
     * Returns a character that determines what kind of a user this is.
     * @return a character that determines if this user is an attendee/ speaker.
     */
    protected abstract char userType();

}
