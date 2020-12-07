package Entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a Entities.Speaker. This is a subclass of the Entities.User class.
 * @author Anushka Saini
 * @version 1.0
 * @see User
 */

public class Speaker extends User{
    private ArrayList<Integer> eventsSpeaking;

    /**
     * Creates a Entities.Speaker with the provided name, password, and email.
     * @param name the name of this Entities.Speaker.
     * @param password the password of this Entities.Speaker.
     * @param email the email of this Entities.Speaker.
     */
    // Constructor Method for Entities.Speaker
    public Speaker(String name, String password, String email) {
        super(name, password, email);
        this.eventsSpeaking = new ArrayList<Integer>();
    }

    /**
     * Returns an ArrayList of Talks that holds all of the Events that the Entities.Speaker is speaking at.
     * @return a list of all Talks that the Entities.Speaker is speaking at.
     */
    // Getter: Retrieves a List of Talks that the Entities.Speaker is talking at.
    public ArrayList<Integer> getTalksSpeaking(){
        return this.eventsSpeaking;
    }

    /**
     * Adds an event to the Entities.Speaker's list of events that they are talking at.
     * @param event the Entities.Talk that the Entities.Speaker would like to speak at.
     */
    // Setter: Add a talk that this Entities.Speaker is talking at to the list talks_speaking.
    public void addEvent(Integer event){
        this.eventsSpeaking.add(event);
    }

    /**
     * Returns a string that represents the type of Entities.User this object is, eg. S for Entities.Speaker.
     * @return a string representing the type of Entities.User this object is
     */
    public char userType(){
        return 'S';
    }
}
