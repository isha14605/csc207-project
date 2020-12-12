package Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Entities.User who can be an Entities.Attendee or an Entities.Organizer.
 * @author Tanya Thaker
 * @version 1.0
 */

public abstract class User implements Serializable {
    private String name;
    private String password;
    private String email;
    private HashMap<String, ArrayList<String>> messagesSent;
    /*For messages_sent, The key is the user they are sending a message to, value is the message*/
    private HashMap<String, ArrayList<String>> messagesReceived;
    /*For messages_received, The key is the user who sent them a message, value is the message*/
    private ArrayList<String> contacts;

    // Constructor for Entities.User
    /**
     * Creates an Entities.User with the specified name, email, and password.
     * @param name the name of the specified Entities.User
     * @param email the email of the specified Entities.User
     * @param password the password of the specified Entities.User
     */

    public User(String name, String password, String email){
        this.name = name;
        this.password = password;
        this.email = email;
        this.messagesSent = new HashMap<String, ArrayList<String>>();
        this.messagesReceived = new HashMap<String, ArrayList<String>>();
        this.contacts = new ArrayList<String>();
    }

    // Getters for Entities.User
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
     * Returns an ArrayList of Entities.User objects who are in present in this users contacts.
     * @return an ArrayList of Entities.User objects who are in present in this users contacts.
     */
    public ArrayList<String> getContacts(){return this.contacts;}

    public Map<String, ArrayList<String>> getMessagesSent(){return this.messagesSent;};

    public Map<String, ArrayList<String>> getMessagesReceived(){return this.messagesReceived;};


    /**
     * Adds a contact to the users contact list.
     * @param email of the user that is added to their contact.
     */
    public void addContact(String email){
        if(!(this.contacts.contains(email))){
            this.contacts.add(email);
            this.messagesSent.put(email, new ArrayList<String>());
            this.messagesReceived.put(email, new ArrayList<String>());
        }
    }

    /**
     * Sends a message to their contact.
     * @param email the recipient of the message the user wants to send.
     * @param  message is the content of the message they want to send.
     */
    public void sendMessage(String email, String message){
        ArrayList<String> x = this.messagesSent.get(email);
        if(x== null){
            x = new ArrayList<>();
        }
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
    public void receiveMessage(String email, String message){
        this.messagesReceived.computeIfAbsent(email, k -> new ArrayList<String>(Collections.singleton(message)));
        ArrayList<String> x = this.messagesReceived.get(email);
        if(x==null){
            x =  new ArrayList<>();
        }
        ArrayList<String> y = new ArrayList<String>(x);
        y.add(message);
        this.messagesReceived.replace(email, y);
    }

    public void updateMessage(String user,Integer message, String updatedMessage){
        this.getMessagesReceived().get(user).set(message, updatedMessage);
    }

    /**
     * Returns a character that determines what kind of a user this is.
     * @return a character that determines if this user is an attendee/ speaker.
     */
    public abstract char userType();

    /**
     * Removes contact of user
     * @param who the email of the user who this user can no longer message.
     */
    public void removeContact(String who){
        this.contacts.remove(who);
        this.messagesReceived.remove(who);
        this.messagesSent.remove(who);
    }

}
